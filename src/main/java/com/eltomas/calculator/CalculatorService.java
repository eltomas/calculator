package com.eltomas.calculator;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * Calculator Service.
 */
@Service
public class CalculatorService {

    @Cacheable("sum")
    public int sum(int a, int b) {
        return a + b;
    }
}
