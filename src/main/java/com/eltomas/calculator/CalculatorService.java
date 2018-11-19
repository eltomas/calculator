package com.eltomas.calculator;

import org.springframework.stereotype.Service;

/**
 * Calculator Service.
 */
@Service
public class CalculatorService {

    int sum(int a, int b) {
        return a + b;
    }
}
