package com.example.calculatordemo;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

/**
 * ViewModel class that manages calculator state and operations
 * This class follows the MVVM architecture pattern and serves as
 * an intermediary between the UI (MainActivity) and the model (Calculator)
 */
public class CalculatorViewModel extends ViewModel {
    
    private final Calculator calculator;
    
    private final MutableLiveData<String> displayText = new MutableLiveData<>("0");
    private String currentInput = "";
    private String operator = "";
    private double firstOperand = 0;
    private boolean isOperatorClicked = false;
    private boolean isCalculationPerformed = false;
    private boolean isError = false;
    
    // Constants for error messages
    private static final String ERROR_DIVIDE_BY_ZERO = "Error: ÷ by 0";
    private static final String ERROR_GENERIC = "Error";
    private static final int MAX_DIGITS = 10;
    
    public CalculatorViewModel() {
        calculator = new Calculator();
    }
    
    /**
     * Returns the display text as LiveData for observation
     * 
     * @return LiveData containing the display text
     */
    public LiveData<String> getDisplayText() {
        return displayText;
    }
    
    /**
     * Processes a number button click
     * 
     * @param number The number that was clicked
     */
    public void onNumberClicked(String number) {
        if (isError) {
            clearError();
        }
        
        if (isOperatorClicked) {
            currentInput = number;
            isOperatorClicked = false;
        } else if (isCalculationPerformed) {
            // If a calculation was just performed, start a new calculation
            currentInput = number;
            isCalculationPerformed = false;
        } else {
            // If current input is "0", replace it with the new digit
            // unless the new digit is also "0"
            if (currentInput.equals("0") && !number.equals("0")) {
                currentInput = number;
            } else if (!currentInput.equals("0")) {
                // Only add the digit if it won't exceed the maximum length
                if (currentInput.length() < MAX_DIGITS) {
                    currentInput += number;
                }
            }
        }
        
        updateDisplay(currentInput);
    }
    
    /**
     * Processes an operator button click
     * 
     * @param newOperator The operator that was clicked
     */
    public void onOperatorClicked(String newOperator) {
        if (isError) {
            clearError();
        }
        
        if (!currentInput.isEmpty()) {
            if (!operator.isEmpty() && !isOperatorClicked) {
                // If an operator was already selected, perform the calculation first
                calculateResult();
                
                // If an error occurred during calculation, don't proceed
                if (isError) {
                    return;
                }
            }
            firstOperand = Double.parseDouble(currentInput);
        }
        
        operator = newOperator;
        isOperatorClicked = true;
        isCalculationPerformed = false;
    }
    
    /**
     * Processes the equals button click
     */
    public void onEqualsClicked() {
        if (isError) {
            clearError();
            return;
        }
        
        if (operator.isEmpty() || isOperatorClicked) {
            return;
        }
        
        calculateResult();
        operator = "";
        isCalculationPerformed = true;
    }
    
    /**
     * Processes the clear button click
     */
    public void onClearClicked() {
        currentInput = "";
        operator = "";
        firstOperand = 0;
        isOperatorClicked = false;
        isCalculationPerformed = false;
        isError = false;
        updateDisplay("0");
    }
    
    /**
     * Processes the decimal point button click
     */
    public void onDecimalClicked() {
        if (isError) {
            clearError();
        }
        
        if (isOperatorClicked || isCalculationPerformed) {
            currentInput = "0.";
            isOperatorClicked = false;
            isCalculationPerformed = false;
        } else if (!currentInput.contains(".")) {
            if (currentInput.isEmpty()) {
                currentInput = "0.";
            } else {
                currentInput += ".";
            }
        }
        
        updateDisplay(currentInput);
    }
    
    /**
     * Processes the percentage button click
     */
    public void onPercentClicked() {
        if (isError) {
            clearError();
        }
        
        if (!currentInput.isEmpty()) {
            try {
                double value = Double.parseDouble(currentInput);
                value = calculator.percentage(value);
                currentInput = formatResult(value);
                updateDisplay(currentInput);
            } catch (NumberFormatException e) {
                setError(ERROR_GENERIC);
            }
        }
    }
    
    /**
     * Processes the plus/minus button click
     */
    public void onPlusMinusClicked() {
        if (isError) {
            clearError();
        }
        
        if (!currentInput.isEmpty() && !currentInput.equals("0")) {
            if (currentInput.charAt(0) == '-') {
                currentInput = currentInput.substring(1);
            } else {
                currentInput = "-" + currentInput;
            }
            updateDisplay(currentInput);
        }
    }
    
    /**
     * Calculates the result of the current operation
     */
    private void calculateResult() {
        if (currentInput.isEmpty()) {
            return;
        }
        
        double secondOperand;
        try {
            secondOperand = Double.parseDouble(currentInput);
        } catch (NumberFormatException e) {
            setError(ERROR_GENERIC);
            return;
        }
        
        double result;
        
        try {
            result = calculator.calculate(firstOperand, secondOperand, operator);
            currentInput = formatResult(result);
            updateDisplay(currentInput);
        } catch (ArithmeticException e) {
            setError(ERROR_DIVIDE_BY_ZERO);
        } catch (Exception e) {
            setError(ERROR_GENERIC);
        }
    }
    
    /**
     * Updates the display with the given text
     * 
     * @param text The text to display
     */
    private void updateDisplay(String text) {
        displayText.setValue(text);
    }
    
    /**
     * Sets an error state and displays the error message
     * 
     * @param errorMessage The error message to display
     */
    private void setError(String errorMessage) {
        isError = true;
        currentInput = "";
        updateDisplay(errorMessage);
    }
    
    /**
     * Clears the error state
     */
    private void clearError() {
        isError = false;
        currentInput = "";
        updateDisplay("0");
    }
    
    /**
     * Formats a result to a string, removing unnecessary decimal zeros
     * and handling large numbers
     * 
     * @param result The result to format
     * @return A formatted string representation of the result
     */
    private String formatResult(double result) {
        // Check if the result is too large or small
        if (Double.isInfinite(result) || Double.isNaN(result)) {
            setError(ERROR_GENERIC);
            return "";
        }
        
        // Format the result
        String formattedResult;
        if (result == (long) result) {
            formattedResult = String.format("%d", (long) result);
        } else {
            formattedResult = String.format("%s", result);
        }
        
        // Check if the result is too long
        if (formattedResult.length() > MAX_DIGITS) {
            // Try to use scientific notation
            formattedResult = String.format("%.6e", result);
            
            // If still too long, show error
            if (formattedResult.length() > MAX_DIGITS) {
                setError(ERROR_GENERIC);
                return "";
            }
        }
        
        return formattedResult;
    }
}
