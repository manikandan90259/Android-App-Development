package com.example.simplecalculator;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button buttonAdd, buttonSub, buttonMul, buttonDiv;
    EditText editTextN1, editTextN2;
    TextView textView;
    int num1, num2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        buttonAdd = findViewById(R.id.button_add01);
        buttonSub = findViewById(R.id.button_sup01);
        buttonMul = findViewById(R.id.button_mul01);
        buttonDiv = findViewById(R.id.button_div01);
        editTextN1 = findViewById(R.id.number01);
        editTextN2 = findViewById(R.id.number02);
        textView = findViewById(R.id.answer);

        buttonAdd.setOnClickListener(this);
        buttonSub.setOnClickListener(this);
        buttonMul.setOnClickListener(this);
        buttonDiv.setOnClickListener(this);
    }

    public int getIntFromEditText(EditText editText) {
        String text = editText.getText().toString();
        if (text.isEmpty()) {
            Toast.makeText(this, "Enter Number", Toast.LENGTH_SHORT).show();

            return 0;
        }
        return Integer.parseInt(text);
    }

    @Override
    public void onClick(View view) {
        num1 = getIntFromEditText(editTextN1);
        num2 = getIntFromEditText(editTextN2);
        int id = view.getId();

        if (id == R.id.button_add01) {
            textView.setText(String.valueOf(num1 + num2));
        } else if (id == R.id.button_sup01) {
            textView.setText(String.valueOf(num1 - num2));
        } else if (id == R.id.button_mul01) {
            textView.setText(String.valueOf(num1 * num2));
        } else if (id == R.id.button_div01) {
            if (num2 != 0) {
                textView.setText(String.valueOf((float) num1 / (float) num2));
            } else {
                Toast.makeText(this, "Cannot divide by zero", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
