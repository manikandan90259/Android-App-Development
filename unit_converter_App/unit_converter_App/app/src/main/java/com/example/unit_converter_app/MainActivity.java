package com.example.unit_converter_app;

import android.os.Bundle;import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Locale;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    // Declare the UI elements
    private EditText inputValue;
    private Spinner unitSpinner;
    private Button convertButton;
    private TextView resultText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize the UI elements by finding their IDs from the XML
        inputValue = findViewById(R.id.inputValue);
        unitSpinner = findViewById(R.id.unitSpinner);
        convertButton = findViewById(R.id.convertButton);
        resultText = findViewById(R.id.resultText);

        // Define the conversion options for our dropdown
        String[] conversionTypes = {
                "Centimeters to Meters",
                "Meters to Centimeters",
                "Grams to Kilograms",
                "Kilograms to Grams"
        };

        // Create an adapter to populate the Spinner with our options
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                conversionTypes
        );
        unitSpinner.setAdapter(adapter);

        // Listen for when the user clicks the "Convert" button
        convertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performConversion();
            }
        });
    }

    private void performConversion() {
        // Get the text from the input field
        String inputStr = inputValue.getText().toString();

        // Check if the user left the input blank to prevent the app from crashing
        if (inputStr.isEmpty()) {
            Toast.makeText(this, "Please enter a value first!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Convert the string input into a decimal number (double)
        double input = Double.parseDouble(inputStr);
        double result = 0.0;

        // Get the currently selected conversion type from the Spinner
        String selectedConversion = unitSpinner.getSelectedItem().toString();
        String resultUnit = "";

        // Perform the correct math based on the selection
        switch (selectedConversion) {
            case "Centimeters to Meters":
                result = input / 100.0;
                resultUnit = " m";
                break;
            case "Meters to Centimeters":
                result = input * 100.0;
                resultUnit = " cm";
                break;
            case "Grams to Kilograms":
                result = input / 1000.0;
                resultUnit = " kg";
                break;
            case "Kilograms to Grams":
                result = input * 1000.0;
                resultUnit = " g";
                break;
        }

        // Display the final result formatted to 2 decimal places
        resultText.setText(String.format(Locale.getDefault(), "Result: %.2f%s", result, resultUnit));
    }
}