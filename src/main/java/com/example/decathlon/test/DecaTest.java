package com.example.decathlon.test;

import com.example.decathlon.deca.Deca100M;
import org.junit.Assert;
import org.junit.Test;

public class DecaTest {

    @Test
    public void deca100M10Seconds() {
        Deca100M deca100M = new Deca100M();
        Assert.assertEquals(deca100M.calculateResult(10), 1096);
    }

}
