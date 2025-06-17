package com.example.calculatordemo;

/**
 * Calculator class that handles the core calculation logic
 * This class is responsible for performing arithmetic operations
 * and other calculator functions
 */
public class Calculator {
    
    // Constants for operation types
    public static final String OPERATION_ADD = "+";
    public static final String OPERATION_SUBTRACT = "-";
    public static final String OPERATION_MULTIPLY = "×";
    public static final String OPERATION_DIVIDE = "÷";
    
    /**
     * Performs the calculation based on the provided operands and operator
     * 
     * @param firstOperand The first operand
     * @param secondOperand The second operand
     * @param operator The operator to apply
     * @return The result of the calculation
     * @throws ArithmeticException if division by zero is attempted
     * @throws IllegalArgumentException if an unknown operator is provided
     */
    public double calculate(double firstOperand, double secondOperand, String operator) {
        switch (operator) {
            case OPERATION_ADD:
                return add(firstOperand, secondOperand);
            case OPERATION_SUBTRACT:
                return subtract(firstOperand, secondOperand);
            case OPERATION_MULTIPLY:
                return multiply(firstOperand, secondOperand);
            case OPERATION_DIVIDE:
                if (secondOperand == 0) {
                    throw new ArithmeticException("Division by zero");
                }
                return divide(firstOperand, secondOperand);
            default:
                throw new IllegalArgumentException("Unknown operator: " + operator);
        }
    }
    
    /**
     * Adds two numbers
     * 
     * @param a First number
     * @param b Second number
     * @return Sum of a and b
     */
    private double add(double a, double b) {
        return a + b;
    }
    
    /**
     * Subtracts the second number from the first
     * 
     * @param a First number
     * @param b Second number
     * @return Difference of a and b
     */
    private double subtract(double a, double b) {
        return a - b;
    }
    
    /**
     * Multiplies two numbers
     * 
     * @param a First number
     * @param b Second number
     * @return Product of a and b
     */
    private double multiply(double a, double b) {
        return a * b;
    }
    
    /**
     * Divides the first number by the second
     * 
     * @param a First number (dividend)
     * @param b Second number (divisor)
     * @return Quotient of a and b
     * @throws ArithmeticException if divisor is zero
     */
    private double divide(double a, double b) {
        if (b == 0) {
            throw new ArithmeticException("Division by zero");
        }
        return a / b;
    }
    
    /**
     * Calculates the percentage of a number
     * 
     * @param value The number to calculate percentage of
     * @return The value divided by 100
     */
    public double percentage(double value) {
        return value / 100.0;
    }
    
    /**
     * Negates a number (changes its sign)
     * 
     * @param value The number to negate
     * @return The negated value
     */
    public double negate(double value) {
        return -value;
    }
}
