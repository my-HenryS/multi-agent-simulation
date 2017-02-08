package org.socialforce.strategy.impl;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by sunjh1999 on 2017/2/8.
 */
public class ComplexECStrategyTest {
    ComplexECStrategy complexECStrategy;
    @Before
    public void setUp() throws Exception {
        complexECStrategy = new ComplexECStrategy();
    }

    @Test
    public void tostring() throws Exception{
        System.out.println(complexECStrategy.toString());
    }
}