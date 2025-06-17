package com.example.calculatordemo;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    
    private TextView displayResult;
    private CalculatorViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        displayResult = findViewById(R.id.display_result);
        
        // Initialize the ViewModel
        viewModel = new ViewModelProvider(this).get(CalculatorViewModel.class);
        
        // Observe changes to the display text
        viewModel.getDisplayText().observe(this, text -> {
            displayResult.setText(text);
        });
        
        // Initialize the display with 0
        viewModel.onClearClicked();
    }
    
    public void onNumberClick(View view) {
        Button button = (Button) view;
        String buttonText = button.getText().toString();
        viewModel.onNumberClicked(buttonText);
    }
    
    public void onOperatorClick(View view) {
        Button button = (Button) view;
        String operator = button.getText().toString();
        viewModel.onOperatorClicked(operator);
    }
    
    public void onEqualsClick(View view) {
        viewModel.onEqualsClicked();
    }
    
    public void onClearClick(View view) {
        viewModel.onClearClicked();
    }
    
    public void onDecimalClick(View view) {
        viewModel.onDecimalClicked();
    }
    
    public void onPercentClick(View view) {
        viewModel.onPercentClicked();
    }
    
    public void onPlusMinusClick(View view) {
        viewModel.onPlusMinusClicked();
    }
}
