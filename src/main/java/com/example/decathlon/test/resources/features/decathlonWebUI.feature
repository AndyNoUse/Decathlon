Feature:Decathlon Web MVP - Competition Management

  Background:Given I navigate to the Decathlon Web MVP page

  Scenario: Add Love and assert Love is written
    Given I am on "http://localhost:8080"
    When I add my name
    Then I assert the name is "Love"

  Scenario Outline: Name
    Given I am on "http://localhost:8080"
    When I write <name> and click Add competitor
    Then Standings should update with <expected_result>
    Examples:
      | name                                                                                        | expected_result |
      | ??                                                                                          | error           |
      | Anna                                                                                        | accepted        |
      | anna                                                                                        | error           |
      | ANNA                                                                                        | accepted        |
      | Annnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnna | error           |
      | Anna!                                                                                       | error           |
      | 123                                                                                         | error           |
      | ^~.                                                                                         | error           |
      |                                                                                             | error           |

  Scenario Outline: Points compared to DesktopUI
    Given I am on "http://localhost:8080"
    When I add my name
    And I write same name down in Enter Result
    And I choose <event>
    And enter <eventResult>
    And I save score
    Then points should be <points>
    Examples:
      | event           | eventResult | points |
      | 100m (s)        | 10          | 1096   |
      | Long Jump (cm)  | 700         | 814    |
      | Shot Put (m)    | 14          | 728    |
      | High Jump (cm)  | 210         | 896    |
      | 400m (s)        | 45          | 1060   |
      | 110 Hurdles (s) | 15          | 850    |
      | Discus (m)      | 45          | 767    |
      | Pole Vault (cm) | 500         | 910    |
      | Javelin (m)     | 60          | 783     |
      | 1500m (s)       | 300         | 560    |

  Scenario Outline: Happy flow e2e Test C:^)
    Given I am on "http://localhost:8080"
    When I choose <mode>
    And I write <name> and click Add competitor
    And I write same name down in Enter Result
    And I choose <event>
    And enter <eventResult>
    And I save score
    Then Standings should update with the <result>
    Then I export CSV and it should match

    Examples:
      | mode       | name | event | eventResult | result |
      | Decathlon  | Anna | 100m  | 8           | Bajs   |
      | Heptathlon | Leif | 800m  | 150         | Bajs   |

  Scenario Outline: Unhappy flow  :(

    #Inspiration
    Examples:
      | mode       | action                                              | expected_behavior                                         |
      | Decathlon  | add a competitor without entering a name            | show an error message and not add competitor              |
      | Heptathlon | save a score without selecting a competitor         | show an alert or prevent saving the score                 |
      | Heptathlon | enter a non-numeric value (e.g. 'abc')              | reject input and display validation feedback              |
      | Decathlon  | enter a negative result (e.g. '-5')                 | reject input and show "Invalid result" message            |
      | Decathlon  | enter a result of zero (e.g. '0')                   | show warning that result must be greater than zero        |
      | Heptathlon | add the same competitor name twice                  | show "Duplicate competitor" error                         |
      | Decathlon  | submit result for an event that’s already completed | show "Result already saved" notification                  |
      | Heptathlon | enter extremely high number (e.g. '999999')         | display validation message and reject out-of-range value  |
      | Decathlon  | attempt to save without entering any result         | disable Save button or show "Enter a result first" alert  |
      | Heptathlon | switch event without saving current result          | warn user "Unsaved changes will be lost" before switching |
    Examples:

      | mode       | action             |
      | Decathlon  | EMPTY_NAME         |
      | Heptathlon | NO_COMPETITOR      |
      | Heptathlon | NON_NUMERIC_RESULT |
      | Decathlon  | NEGATIVE_RESULT    |
      | Decathlon  | ZERO_RESULT        |
      | Heptathlon | DUPLICATE_NAME     |
      | Decathlon  | DUPLICATE_RESULT   |
      | Heptathlon | EXTREME_VALUE      |
      | Decathlon  | EMPTY_RESULT       |
      | Heptathlon | UNSAVED_SWITCH     |

  Scenario Outline: Add competitor and enter valid result
    Given the user has opened the Decathlon Web MVP page
    And the competition mode "<mode>" is selected
    When the user enters "<name>" in the Add competitor field
    And clicks Add competitor
    Then the competitor "<name>" should appear in the standings table
    When the user selects "<event>" from the event dropdown
    And enters "<result>" as the result
    And clicks Save score
    Then the result "<result>" should be saved for "<name>" in the "<event>" column

    Examples:
      | mode       | name | event         | result |
      | Decathlon  | Erik | 100m (s)      | 11.23  |
      | Heptathlon | Anna | High Jump (m) | 1.78   |

  Scenario Outline: Prevent adding or saving invalid data
    Given the user has opened the Decathlon Web MVP page
    And the competition mode "<mode>" is selected
    When the user tries to "<action>"
    Then the system should "<expected_behavior>"

    Examples:
      | mode       | action                                      | expected_behavior                            |
      | Decathlon  | add a competitor without entering a name    | show an error message and not add competitor |
      | Heptathlon | save a score without selecting a competitor | show an alert or prevent saving the score    |
      | Heptathlon | enter a non-numeric value (e.g. 'abc')      | reject input and display validation feedback |


# Scenario Outline: Switch between competition modes
#    When I select "<mode>" from the mode dropdown
#    Then the mode dropdown should display "<mode>"
#    And the standings table should show appropriate columns for "<mode>"
#    Examples:
#
#      | mode       |
#    | Decathlon  |
#    | Heptathlon |
#
#  Scenario Outline: Add competitors successfully
#    Given the competition mode is set to "<mode>"
#    When I enter "<name>" in the competitor name field
#    And I click the "Add competitor" button
#    Then "<name>" should appear in the standings table
#    And the competitor name field should be cleared
#    Examples:
#
#      | mode       | name   |
#    | Decathlon  | Anna   |
#    | Decathlon  | Erik   |
#    | Decathlon  | Sofia  |
#    | Heptathlon | Marcus |
#    | Heptathlon | Linda |
#
#  Scenario: Add competitor with empty name should fail
#    When I leave the competitor name field empty
#    And I click the "Add competitor" button
#    Then no new competitor should be added to the standings
#
#  Scenario Outline: Record decathlon event results
#    Given the competition mode is set to "Decathlon"
#    And competitor "<name>" has been added
#    When I select "<name>" from the result name dropdown
#    And I select "<event>" from the event dropdown
#    And I enter "<result>" in the result field
#    And I click the "Save score" button
#    Then the result "<result>" should be displayed in the "<event>" column for "<name>"
#    And the total score for "<name>" should be updated
#
#    Examples:
#
#      | name | event        | result |
#    | Anna | 100m (s)     | 11.50  |
#    | Anna | Long Jump    | 6.25   |
#    | Anna | Shot Put     | 14.80  |
#    | Anna | High Jump    | 1.85   |
#    | Anna | 400m         | 52.30  |
#    | Anna | 110m Hurdles | 14.20  |
#    | Anna | Discus     | 45.50  |
#    | Anna | Pole Vault | 4.20   |
#    | Anna | Javelin | 58.70  |
#    | Anna | 1500m | 285.00 |
#
#  Scenario Outline: Record heptathlon event results
#    Given the competition mode is set to "Heptathlon"
#    And competitor "<name>" has been added
#    When I select "<name>" from the result name dropdown
#    And I select "<event>" from the event dropdown
#    And I enter "<result>" in the result field
#    And I click the "Save score" button
#    Then the result "<result>" should be displayed in the "<event>" column for "<name>"
#    And the total score for "<name>" should be updated
#
#    Examples:
#
#      | name  | event        | result |
#    | Sofia | 100m Hurdles | 13.20  |
#    | Sofia | High Jump | 1.75   |
#    | Sofia | Shot Put  | 13.50  |
#    | Sofia | 200m      | 24.80  |
#    | Sofia | Long Jump | 6.10   |
#    | Sofia | Javelin | 50.20  |
#    | Sofia | 800m | 135.00 |
#
#  Scenario: Record result without selecting competitor
#    Given the competition mode is set to "Decathlon"
#    When I select "100m (s)" from the event dropdown
#    And I enter "11.50" in the result field
#    And I click the "Save score" button
#    Then the result should not be saved
#
#  Scenario: Update existing result
#    Given the competition mode is set to "Decathlon"
#    And competitor "Anna" has been added
#    And "Anna" has a result of "11.50" in "100m (s)"
#    When I select "Anna" from the result name dropdown
#    And I select "100m (s)" from the event dropdown
#    And I enter "11.20" in the result field
#    And I click the "Save score" button
#    Then the result "11.20" should be displayed in the "100m (s)" column for "Anna"
#    And the total score for "Anna" should be recalculated
#
#  Scenario: Verify standings table columns for Decathlon
#    Given the competition mode is set to "Decathlon"
#    Then the standings table should have the following columns:
#
#      | Name         |
#    | 100m         |
#    | Long Jump    |
#    | Shot Put     |
#    | High Jump    |
#    | 400m         |
#    | 110m Hurdles |
#    | Discus     |
#    | Pole Vault |
#    | Javelin |
#    | 1500m |
#    | Total |
#
#  Scenario: Verify standings table columns for Heptathlon
#    Given the competition mode is set to "Heptathlon"
#    Then the standings table should have the following columns:
#
#      | Name         |
#    | 100m Hurdles |
#    | High Jump |
#    | Shot Put  |
#    | 200m      |
#    | Long Jump |
#    | Javelin |
#    | 800m  |
#    | Total |
#
#  Scenario: Export standings to CSV
#    Given the competition mode is set to "Decathlon"
#    And multiple competitors have been added with results
#    When I click the "Export CSV" button
#    Then a CSV file should be downloaded
#    And the CSV should contain all competitor data
#
#  Scenario Outline: Multiple competitors ranking
#    Given the competition mode is set to "Decathlon"
#    And the following competitors with complete results exist:
#      | name  | total_score |
#    | Anna  | 8500 |
#    | Erik  | 7800 |
#    | Sofia | 8200 |
#
#    Then the standings should display competitors in descending order by total score
#    And "Anna" should be ranked first
#    And "Sofia" should be ranked second
#    And "Erik" should be ranked third
#
#    Examples:
#      | mode      |
#    | Decathlon |
#
#Feature: Manage competitors and results in Decathlon Web MVP PART 2***
#
#  Scenario Outline: Add competitor and enter valid result
#    Given the user has opened the Decathlon Web MVP page
#    And the competition mode "<mode>" is selected
#    When the user enters "<name>" in the "Add competitor" field
#    And clicks "Add competitor"
#    Then the competitor "<name>" should appear in the standings table
#    When the user selects "<event>" from the event dropdown
#    And enters "<result>" as the result
#    And clicks "Save score"
#    Then the result "<result>" should be saved for "<name>" in the "<event>" column
#
#    Examples:
#      | mode       | name | event         | result |
#    | Decathlon  | Erik | 100m (s)      | 11.23 |
#    | Heptathlon | Anna | High Jump (m) | 1.78 |
#
#
#Feature: Input validation in Decathlon Web MVP
#  Scenario Outline: Prevent adding or saving invalid data
#    Given the user has opened the Decathlon Web MVP page
#    And the competition mode "<mode>" is selected
#    When the user tries to "<action>"
#    Then the system should "<expected_behavior>"
#    Examples:
#
#      | mode       | action                                      | expected_behavior                            |
#    | Decathlon  | add a competitor without entering a name    | show an error message and not add competitor |
#    | Heptathlon | save a score without selecting a competitor | show an alert or prevent saving the score    |
#    | Heptathlon | enter a non-numeric value (e.g. 'abc') | reject input and display validation feedback |

