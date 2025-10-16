package com.example.decathlon.gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

import com.example.decathlon.common.InvalidResultException;
import com.example.decathlon.deca.*;
import com.example.decathlon.heptathlon.*;

public class MainGUI {

    private enum Mode { DEC, HEP }

    private JFrame frame;
    private JTextField nameField;
    private JTextField resultField;
    private JComboBox<String> disciplineBox;
    private JTextArea outputArea;

    private JRadioButton decRadio;
    private JRadioButton hepRadio;

    private JTable scoreTable;
    private DefaultTableModel tableModel;

    // Kolumner för decathlon/heptathlon (etiketter måste matcha switch-case)
    private static final List<String> DEC_EVENTS = List.of(
            "100m", "400m", "1500m", "110m Hurdles",
            "Long Jump", "High Jump", "Pole Vault",
            "Discus Throw", "Javelin Throw", "Shot Put"
    );
    private static final List<String> HEP_EVENTS = List.of(
            "100m Hurdles", "High Jump", "Shot Put",
            "200m", "Long Jump", "Javelin Throw", "800m"
    );

    private Mode currentMode = Mode.DEC;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainGUI().createAndShowGUI());
    }

    private void createAndShowGUI() {
        frame = new JFrame("Track & Field Calculator (Decathlon / Heptathlon)");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 750);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout(10, 10));

        JPanel top = new JPanel(new GridBagLayout());
        top.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));
        GridBagConstraints gc = new GridBagConstraints();
        gc.insets = new Insets(5, 5, 5, 5);
        gc.anchor = GridBagConstraints.WEST;

        decRadio = new JRadioButton("Decathlon", true);
        hepRadio = new JRadioButton("Heptathlon");
        ButtonGroup modeGroup = new ButtonGroup();
        modeGroup.add(decRadio);
        modeGroup.add(hepRadio);

        decRadio.addActionListener(e -> switchMode(Mode.DEC));
        hepRadio.addActionListener(e -> switchMode(Mode.HEP));

        nameField = new JTextField(18);
        resultField = new JTextField(10);

        disciplineBox = new JComboBox<>(DEC_EVENTS.toArray(new String[0]));

        JButton calculateButton = new JButton("Calculate Score");
        calculateButton.addActionListener(new CalculateButtonListener());

        int col = 0; int row = 0;

        gc.gridx = col++; gc.gridy = row; top.add(new JLabel("Mode:"), gc);
        gc.gridx = col++; top.add(decRadio, gc);
        gc.gridx = col++; top.add(hepRadio, gc);

        col = 0; row++;
        gc.gridx = col++; gc.gridy = row; top.add(new JLabel("Competitor:"), gc);
        gc.gridx = col++; top.add(nameField, gc);

        col = 0; row++;
        gc.gridx = col++; gc.gridy = row; top.add(new JLabel("Discipline:"), gc);
        gc.gridx = col++; top.add(disciplineBox, gc);

        col = 0; row++;
        gc.gridx = col++; gc.gridy = row; top.add(new JLabel("Result:"), gc);
        gc.gridx = col++; top.add(resultField, gc);
        gc.gridx = col++; top.add(calculateButton, gc);

        frame.add(top, BorderLayout.NORTH);


        outputArea = new JTextArea(6, 60);
        outputArea.setEditable(false);
        JScrollPane logScroll = new JScrollPane(outputArea);
        logScroll.setBorder(BorderFactory.createTitledBorder("Log"));
        frame.add(logScroll, BorderLayout.CENTER);


        tableModel = buildTableModelForMode(currentMode);
        scoreTable = new JTable(tableModel);
        scoreTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        scoreTable.setRowHeight(24);
        scoreTable.getTableHeader().setReorderingAllowed(false);

        JScrollPane tableScroll = new JScrollPane(scoreTable);
        tableScroll.setBorder(BorderFactory.createTitledBorder("Scores Matrix"));
        frame.add(tableScroll, BorderLayout.SOUTH);


        UIManager.put("Label.font", new Font("SansSerif", Font.PLAIN, 14));
        UIManager.put("Button.font", new Font("SansSerif", Font.BOLD, 14));
        UIManager.put("TextField.font", new Font("SansSerif", Font.PLAIN, 14));
        UIManager.put("TextArea.font", new Font("SansSerif", Font.PLAIN, 13));
        UIManager.put("Table.font", new Font("SansSerif", Font.PLAIN, 13));
        UIManager.put("TableHeader.font", new Font("SansSerif", Font.BOLD, 13));

        frame.setVisible(true);
    }

    private void switchMode(Mode newMode) {
        if (newMode == currentMode) return;
        currentMode = newMode;


        disciplineBox.removeAllItems();
        List<String> list = (currentMode == Mode.DEC) ? DEC_EVENTS : HEP_EVENTS;
        for (String d : list) disciplineBox.addItem(d);


        Set<String> existingNames = new LinkedHashSet<>();
        int nameCol = 0;
        for (int r = 0; r < tableModel.getRowCount(); r++) {
            Object val = tableModel.getValueAt(r, nameCol);
            if (val != null) existingNames.add(val.toString());
        }

        tableModel = buildTableModelForMode(currentMode);
        scoreTable.setModel(tableModel);

        for (String nm : existingNames) {
            addRowIfMissing(nm);
        }
    }

    private DefaultTableModel buildTableModelForMode(Mode mode) {
        Vector<String> cols = new Vector<>();
        cols.add("Competitor");

        List<String> events = (mode == Mode.DEC) ? DEC_EVENTS : HEP_EVENTS;
        cols.addAll(events);
        cols.add("Total");

        return new DefaultTableModel(new Vector<>(), cols) {
            @Override public boolean isCellEditable(int row, int column) {
                return column == 0;
            }
        };
    }

    private void addRowIfMissing(String name) {
        int row = findRowByName(name);
        if (row == -1) {
            Vector<Object> v = new Vector<>();
            v.add(name);
            int eventCount = ((currentMode == Mode.DEC) ? DEC_EVENTS.size() : HEP_EVENTS.size());
            for (int i = 0; i < eventCount; i++) v.add(null);
            v.add(0); // total
            tableModel.addRow(v);
        }
    }

    private int findRowByName(String name) {
        for (int r = 0; r < tableModel.getRowCount(); r++) {
            Object val = tableModel.getValueAt(r, 0);
            if (val != null && val.toString().equalsIgnoreCase(name)) return r;
        }
        return -1;
    }

    private void setScoreInTable(String name, String discipline, int score) {
        addRowIfMissing(name);
        int row = findRowByName(name);
        int col = findDisciplineColumn(discipline);
        if (col == -1) return;

        tableModel.setValueAt(score, row, col);
        int total = 0;
        int firstEventCol = 1;
        int lastEventCol = tableModel.getColumnCount() - 2;
        for (int c = firstEventCol; c <= lastEventCol; c++) {
            Object v = tableModel.getValueAt(row, c);
            if (v instanceof Number) total += ((Number) v).intValue();
            else if (v != null) {
                try { total += Integer.parseInt(v.toString()); } catch (Exception ignored) {}
            }
        }
        tableModel.setValueAt(total, row, tableModel.getColumnCount() - 1);
    }

    private int findDisciplineColumn(String discipline) {
        for (int c = 0; c < tableModel.getColumnCount(); c++) {
            if (tableModel.getColumnName(c).equalsIgnoreCase(discipline)) return c;
        }
        return -1;
    }

    private class CalculateButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String name = nameField.getText().trim();
            String discipline = (String) disciplineBox.getSelectedItem();
            String resultText = resultField.getText().trim();

            if (name.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Please enter a competitor name.", "Missing name", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (discipline == null) {
                JOptionPane.showMessageDialog(frame, "Please select a discipline.", "Missing discipline", JOptionPane.WARNING_MESSAGE);
                return;
            }

            try {
                double result = Double.parseDouble(resultText);

                int score = calculateScore(discipline, result);
                outputArea.append("Competitor: " + name + "\n");
                outputArea.append("Discipline: " + discipline + "\n");
                outputArea.append("Result: " + result + "\n");
                outputArea.append("Score: " + score + "\n\n");

                setScoreInTable(name, discipline, score);

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Please enter a valid number for the result.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
            } catch (UnsupportedOperationException ex) {
                JOptionPane.showMessageDialog(frame, ex.getMessage(), "Not implemented", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Unexpected error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            } catch (InvalidResultException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Invalid Result", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private int calculateScore(String discipline, double result) {
        if (currentMode == Mode.DEC) {
            switch (discipline) {
                case "100m":         return new Deca100M().calculateResult(result);
                case "400m":         return new Deca400M().calculateResult(result);
                case "1500m":        return new Deca1500M().calculateResult(result);
                case "110m Hurdles": return new Deca110MHurdles().calculateResult(result);
                case "Long Jump":    return new DecaLongJump().calculateResult(result);
                case "High Jump":    return new DecaHighJump().calculateResult(result);
                case "Pole Vault":   return new DecaPoleVault().calculateResult(result);
                case "Discus Throw": return new DecaDiscusThrow().calculateResult(result);
                case "Javelin Throw":return new DecaJavelinThrow().calculateResult(result);
                case "Shot Put":     return new DecaShotPut().calculateResult(result);
            }
        } else {
            // HEPTATHLON (7 grenar)
            switch (discipline) {
                case "100m Hurdles": return new Hep100MHurdles().calculateResult(result);
                case "High Jump":    return new HeptHightJump().calculateResult(result);
                case "Shot Put":     return new HeptShotPut().calculateResult(result);
                case "200m":         return new Hep200M().calculateResult(result);
                case "Long Jump":    return new HeptLongJump().calculateResult(result);
                case "Javelin Throw":return new HeptJavelinThrow().calculateResult(result);
                case "800m":         return new Hep800M().calculateResult(result);
            }
        }
        throw new UnsupportedOperationException("Discipline not implemented: " + discipline);
    }
}
