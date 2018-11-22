package com.eltomas.calculator;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Calculator Service Test
 */
public class CalculatorServiceTest {

    private CalculatorService service;

    @Before
    public void setUp() {
        service = new CalculatorService();
    }

    @Test
    public void testSum() {
        assertEquals(5, service.sum(2, 3));
    }
}