package com.example.calculatordemo;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Unit tests for the Calculator class
 */
public class CalculatorTest {
    
    private Calculator calculator;
    
    @Before
    public void setUp() {
        calculator = new Calculator();
    }
    
    @Test
    public void testAddition() {
        double result = calculator.calculate(5, 3, Calculator.OPERATION_ADD);
        assertEquals(8, result, 0);
        
        // Test with negative numbers
        result = calculator.calculate(-5, 3, Calculator.OPERATION_ADD);
        assertEquals(-2, result, 0);
        
        // Test with decimal numbers
        result = calculator.calculate(5.5, 3.3, Calculator.OPERATION_ADD);
        assertEquals(8.8, result, 0.001);
    }
    
    @Test
    public void testSubtraction() {
        double result = calculator.calculate(5, 3, Calculator.OPERATION_SUBTRACT);
        assertEquals(2, result, 0);
        
        // Test with negative result
        result = calculator.calculate(3, 5, Calculator.OPERATION_SUBTRACT);
        assertEquals(-2, result, 0);
        
        // Test with decimal numbers
        result = calculator.calculate(5.5, 3.3, Calculator.OPERATION_SUBTRACT);
        assertEquals(2.2, result, 0.001);
    }
    
    @Test
    public void testMultiplication() {
        double result = calculator.calculate(5, 3, Calculator.OPERATION_MULTIPLY);
        assertEquals(15, result, 0);
        
        // Test with zero
        result = calculator.calculate(5, 0, Calculator.OPERATION_MULTIPLY);
        assertEquals(0, result, 0);
        
        // Test with negative numbers
        result = calculator.calculate(-5, 3, Calculator.OPERATION_MULTIPLY);
        assertEquals(-15, result, 0);
        
        // Test with decimal numbers
        result = calculator.calculate(5.5, 2, Calculator.OPERATION_MULTIPLY);
        assertEquals(11, result, 0.001);
    }
    
    @Test
    public void testDivision() {
        double result = calculator.calculate(6, 3, Calculator.OPERATION_DIVIDE);
        assertEquals(2, result, 0);
        
        // Test with decimal result
        result = calculator.calculate(5, 2, Calculator.OPERATION_DIVIDE);
        assertEquals(2.5, result, 0.001);
        
        // Test with negative numbers
        result = calculator.calculate(-6, 3, Calculator.OPERATION_DIVIDE);
        assertEquals(-2, result, 0);
    }
    
    @Test(expected = ArithmeticException.class)
    public void testDivisionByZero() {
        calculator.calculate(5, 0, Calculator.OPERATION_DIVIDE);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testInvalidOperator() {
        calculator.calculate(5, 3, "invalid");
    }
    
    @Test
    public void testPercentage() {
        double result = calculator.percentage(50);
        assertEquals(0.5, result, 0);
        
        result = calculator.percentage(200);
        assertEquals(2, result, 0);
        
        result = calculator.percentage(0);
        assertEquals(0, result, 0);
    }
    
    @Test
    public void testNegate() {
        double result = calculator.negate(5);
        assertEquals(-5, result, 0);
        
        result = calculator.negate(-5);
        assertEquals(5, result, 0);
        
        result = calculator.negate(0);
        assertEquals(0, result, 0);
    }
}
