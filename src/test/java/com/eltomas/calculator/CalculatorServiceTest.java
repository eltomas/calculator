package com.eltomas.calculator;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Main Spring Application.
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