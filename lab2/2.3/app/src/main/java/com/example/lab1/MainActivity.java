package com.example.lab1;

import androidx.appcompat.app.AppCompatActivity;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button EvalButton;
    EditText FirstNumberInput, SecondNumberInput;
    TextView ResultView;
    Spinner OpSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EvalButton = findViewById(R.id.evalButton);
        FirstNumberInput = findViewById(R.id.firstNumInput);
        SecondNumberInput = findViewById(R.id.secondNumInput);
        ResultView = findViewById(R.id.result);
        OpSpinner = findViewById(R.id.opSpinner);

        EvalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Double firstNum = Double.parseDouble(FirstNumberInput.getText().toString());
                Double secondNum = Double.parseDouble(SecondNumberInput.getText().toString());

                String op = OpSpinner.getSelectedItem().toString();

                Double result = 0.0;
                switch (op) {
                    case "+": {
                        result = firstNum + secondNum;
                        break;
                    }
                    case "/": {
                        if (secondNum == 0) {
                            Toast toast = Toast.makeText(getApplicationContext(),
                                    "Can't divide by 0", Toast.LENGTH_SHORT);
                            toast.show();
                            result = Double.POSITIVE_INFINITY;
                            break;
                        }
                        result = firstNum / secondNum;
                        break;
                    }
                    case "*": {
                        result = firstNum * secondNum;
                        break;
                    }
                    case "-": {
                        result = firstNum - secondNum;
                        break;
                    }
                }

                ResultView.setText(result.toString());
            }
        });
    }
}