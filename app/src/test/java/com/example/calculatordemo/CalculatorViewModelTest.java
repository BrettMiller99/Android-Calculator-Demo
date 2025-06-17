package com.example.calculatordemo;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.LiveData;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Unit tests for the CalculatorViewModel class
 */
public class CalculatorViewModelTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    private CalculatorViewModel viewModel;

    @Before
    public void setUp() {
        viewModel = new CalculatorViewModel();
    }

    @Test
    public void testInitialState() {
        assertEquals("0", getDisplayValue());
    }

    @Test
    public void testNumberInput() {
        viewModel.onNumberClicked("1");
        assertEquals("1", getDisplayValue());

        viewModel.onNumberClicked("2");
        assertEquals("12", getDisplayValue());
    }

    @Test
    public void testLeadingZeroReplacement() {
        viewModel.onNumberClicked("0");
        assertEquals("0", getDisplayValue());

        viewModel.onNumberClicked("0");
        // Should still be "0", not "00"
        assertEquals("0", getDisplayValue());

        viewModel.onNumberClicked("5");
        // Should be "5", not "05"
        assertEquals("5", getDisplayValue());
    }

    @Test
    public void testDecimalInput() {
        viewModel.onDecimalClicked();
        assertEquals("0.", getDisplayValue());

        viewModel.onNumberClicked("5");
        assertEquals("0.5", getDisplayValue());

        // Test that multiple decimals are not allowed
        viewModel.onDecimalClicked();
        // Should still be "0.5", not "0.5."
        assertEquals("0.5", getDisplayValue());
    }

    @Test
    public void testAddition() {
        viewModel.onNumberClicked("5");
        viewModel.onOperatorClicked(Calculator.OPERATION_ADD);
        viewModel.onNumberClicked("3");
        viewModel.onEqualsClicked();

        assertEquals("8", getDisplayValue());
    }

    @Test
    public void testSubtraction() {
        viewModel.onNumberClicked("5");
        viewModel.onOperatorClicked(Calculator.OPERATION_SUBTRACT);
        viewModel.onNumberClicked("3");
        viewModel.onEqualsClicked();

        assertEquals("2", getDisplayValue());
    }

    @Test
    public void testMultiplication() {
        viewModel.onNumberClicked("5");
        viewModel.onOperatorClicked(Calculator.OPERATION_MULTIPLY);
        viewModel.onNumberClicked("3");
        viewModel.onEqualsClicked();

        assertEquals("15", getDisplayValue());
    }

    @Test
    public void testDivision() {
        viewModel.onNumberClicked("6");
        viewModel.onOperatorClicked(Calculator.OPERATION_DIVIDE);
        viewModel.onNumberClicked("3");
        viewModel.onEqualsClicked();

        assertEquals("2", getDisplayValue());
    }

    @Test
    public void testDivisionByZero() {
        viewModel.onNumberClicked("5");
        viewModel.onOperatorClicked(Calculator.OPERATION_DIVIDE);
        viewModel.onNumberClicked("0");
        viewModel.onEqualsClicked();

        assertEquals("Error: ÷ by 0", getDisplayValue());
    }

    @Test
    public void testClear() {
        viewModel.onNumberClicked("5");
        assertEquals("5", getDisplayValue());

        viewModel.onClearClicked();
        assertEquals("0", getDisplayValue());
    }

    @Test
    public void testPlusMinus() {
        viewModel.onNumberClicked("5");
        assertEquals("5", getDisplayValue());

        viewModel.onPlusMinusClicked();
        assertEquals("-5", getDisplayValue());

        viewModel.onPlusMinusClicked();
        assertEquals("5", getDisplayValue());
    }

    @Test
    public void testPercentage() {
        viewModel.onNumberClicked("50");
        assertEquals("50", getDisplayValue());
        
        viewModel.onPercentClicked();
        assertEquals("0.5", getDisplayValue());
    }

    @Test
    public void testMaxDigits() {
        // Enter more than MAX_DIGITS (10) digits
        viewModel.onNumberClicked("1");
        viewModel.onNumberClicked("2");
        viewModel.onNumberClicked("3");
        viewModel.onNumberClicked("4");
        viewModel.onNumberClicked("5");
        viewModel.onNumberClicked("6");
        viewModel.onNumberClicked("7");
        viewModel.onNumberClicked("8");
        viewModel.onNumberClicked("9");
        viewModel.onNumberClicked("0");
        
        // Verify that we have 10 digits
        assertEquals("1234567890", getDisplayValue());
        
        // Try to add one more digit
        viewModel.onNumberClicked("1");
        
        // Should still be 10 digits, not 11
        assertEquals("1234567890", getDisplayValue());
    }

    @Test
    public void testChainedOperations() {
        viewModel.onNumberClicked("5");
        viewModel.onOperatorClicked(Calculator.OPERATION_ADD);
        viewModel.onNumberClicked("3");
        viewModel.onOperatorClicked(Calculator.OPERATION_MULTIPLY);
        
        // 5 + 3 = 8, then prepare for multiplication
        assertEquals("8", getDisplayValue());
        
        viewModel.onNumberClicked("2");
        viewModel.onEqualsClicked();
        
        // 8 * 2 = 16
        assertEquals("16", getDisplayValue());
    }

    @Test
    public void testErrorRecovery() {
        // Cause a division by zero error
        viewModel.onNumberClicked("5");
        viewModel.onOperatorClicked(Calculator.OPERATION_DIVIDE);
        viewModel.onNumberClicked("0");
        viewModel.onEqualsClicked();
        
        assertEquals("Error: ÷ by 0", getDisplayValue());
        
        // Try to recover by entering a new number
        viewModel.onNumberClicked("7");
        
        // Should clear the error and show the new number
        assertEquals("7", getDisplayValue());
    }
    
    /**
     * Helper method to get the current value of the display LiveData
     */
    private String getDisplayValue() {
        LiveData<String> displayLiveData = viewModel.getDisplayText();
        return displayLiveData.getValue();
    }
}
