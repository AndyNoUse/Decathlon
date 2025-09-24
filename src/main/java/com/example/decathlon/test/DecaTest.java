package com.example.decathlon.test;

import com.example.decathlon.deca.*;
import com.example.decathlon.heptathlon.*;
import org.junit.Assert;
import org.junit.Test;

public class DecaTest {
    //TIO KAMP
    @Test
    public void deca100M10Seconds() {
        Deca100M deca100M = new Deca100M();
        Assert.assertEquals(deca100M.calculateResult(11), 861);
    }

    @Test
    public void deca110MHurdles15Seconds() {
        Deca110MHurdles deca110MHurdles = new Deca110MHurdles();
        Assert.assertEquals(deca110MHurdles.calculateResult(15), 850);
    }

    @Test
    public void deca400M45Seconds() {
        Deca400M deca400M = new Deca400M();
        Assert.assertEquals(deca400M.calculateResult(45), 1060);
    }

    @Test
    public void deca1500M300Seconds() {
        Deca1500M deca1500M = new Deca1500M();
        Assert.assertEquals(deca1500M.calculateResult(300), 560);
    }

    @Test
    public void decaDiscusThrow45M() {
        DecaDiscusThrow decaDiscusThrow = new DecaDiscusThrow();
        Assert.assertEquals(decaDiscusThrow.calculateResult(45), 767);
    }

    @Test
    public void decaHighJump210Cm() {
        DecaHighJump decaHighJump = new DecaHighJump();
        Assert.assertEquals(decaHighJump.calculateResult(210), 896);
    }

    @Test
    public void decaJavelinThrow60M() {
        DecaJavelinThrow decaJavelinThrow = new DecaJavelinThrow();
        Assert.assertEquals(decaJavelinThrow.calculateResult(60), 738);
    }

    @Test
    public void decaLongJump700cm() {
        DecaLongJump decaLongJump = new DecaLongJump();
        Assert.assertEquals(decaLongJump.calculateResult(700), 814);
    }

    @Test
    public void decaPoleVault500Cm() {
        DecaPoleVault decaPoleVault = new DecaPoleVault();
        Assert.assertEquals(decaPoleVault.calculateResult(500), 910);
    }

    @Test
    public void decaShotPut14M() {
        DecaShotPut decaShotPut = new DecaShotPut();
        Assert.assertEquals(decaShotPut.calculateResult(14), 794);
    }

    //SJU KAMP
    @Test
    public void hep100MHurdles15Seconds() {
        Hep100MHurdles hep100MHudrles = new Hep100MHurdles();
        Assert.assertEquals(hep100MHudrles.calculateResult(15), 842);
    }
    @Test
    public void hep200M26Seconds() {
        Hep200M hep200M = new Hep200M();
        Assert.assertEquals(hep200M.calculateResult(26 ), 797);
    }
    @Test
    public void hep800M136Seconds() {
        Hep800M hep800M = new Hep800M();
        Assert.assertEquals(hep800M.calculateResult(136), 879);
    }
    @Test
    public void heptHightJump171Cm() {
        HeptHightJump heptHightJump = new HeptHightJump();
        Assert.assertEquals(heptHightJump.calculateResult(171), 867);
    }
    @Test
    public void HeptJavelinThrow46M() {
        HeptJavelinThrow heptJavelinThrow = new HeptJavelinThrow();
        Assert.assertEquals(heptJavelinThrow.calculateResult(46), 783);
    }
    @Test
    public void HeptLongJump601Cm() {
        HeptLongJump heptLongJump = new HeptLongJump();
        Assert.assertEquals(heptLongJump.calculateResult(601), 853);
    }
    @Test
    public void HeptShotPut14M() {
        HeptShotPut heptShotPut = new HeptShotPut();
        Assert.assertEquals(heptShotPut.calculateResult(14), 794);
    }
}
