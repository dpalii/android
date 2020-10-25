package com.example.lab1;

import androidx.appcompat.app.AppCompatActivity;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    enum Operation {
        DIV,
        MUL,
        ADD,
        SUB,
        NONE
    }
    Button NumButtons[], BtAdd, BtSub, BtMul, BtDiv, BtEq, BtC, BtDot;
    TextView tview;
    String input = "0";
    Operation op = Operation.NONE;
    Boolean opChosen = false;
    Boolean hasDot = false;
    Double result = 0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        NumButtons = new Button[10];

        NumButtons[0] = (Button) findViewById(R.id.btn0);
        NumButtons[1] = (Button) findViewById(R.id.btn1);
        NumButtons[2] = (Button) findViewById(R.id.btn2);
        NumButtons[3] = (Button) findViewById(R.id.btn3);
        NumButtons[4] = (Button) findViewById(R.id.btn4);
        NumButtons[5] = (Button) findViewById(R.id.btn5);
        NumButtons[6] = (Button) findViewById(R.id.btn6);
        NumButtons[7] = (Button) findViewById(R.id.btn7);
        NumButtons[8] = (Button) findViewById(R.id.btn8);
        NumButtons[9] = (Button) findViewById(R.id.btn9);

        BtAdd = (Button) findViewById(R.id.btnAdd);
        BtSub = (Button) findViewById(R.id.btnSub);
        BtMul = (Button) findViewById(R.id.btnMul);
        BtDiv = (Button) findViewById(R.id.btnDiv);
        BtC = (Button) findViewById(R.id.btnC);
        BtEq = (Button) findViewById(R.id.btnEq);
        BtDot = (Button) findViewById(R.id.btnDot);
        tview = (TextView) findViewById(R.id.textView);

        tview.setText(input);

        View.OnClickListener numOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Button currButton = (Button) view;
                inputNumber((String)currButton.getText());
            }
        };

        for (Button btn: NumButtons) {
            btn.setOnClickListener(numOnClickListener);
        }

        BtDot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!hasDot) {
                    if (input.equals("0")) inputNumber("0.");
                    else inputNumber(".");
                    hasDot = true;
                }
            }
        });

        BtC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetInput();
                result = 0.0;
                tview.setText(input);
                op = Operation.NONE;
            }
        });

        BtAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                executeOperation();
                op = Operation.ADD;
                opChosen = true;
            }
        });


        BtSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                executeOperation();
                op = Operation.SUB;
                opChosen = true;
            }
        });

        BtDiv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                executeOperation();
                op = Operation.DIV;
                opChosen = true;
            }
        });

        BtMul.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                executeOperation();
                op = Operation.MUL;
                opChosen = true;
            }
        });

        BtEq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                executeOperation();
            }
        });
    }

    private void inputNumber(String num) {
        if (opChosen) {
            resetInput();
        }
        if (input.equals("0")) input = "";
        input += num;
        tview.setText(input);
    }

    private void resetInput() {
        opChosen = false;
        hasDot = false;
        input = "0";
    }

    private void executeOperation() {
        Double num = Double.parseDouble(this.input);
        switch (op) {
            case ADD: {
                result += num;
                break;
            }
            case DIV: {
                if (num == 0) {
                    Toast toast = Toast.makeText(getApplicationContext(),
                            "Can't divide by 0", Toast.LENGTH_SHORT);
                    toast.show();
                    resetInput();
                    input = result.toString();
                    tview.setText(input);
                    break;
                }
                result /= num;
                break;
            }
            case MUL: {
                result *= num;
                break;
            }
            case SUB: {
                result -= num;
                break;
            }
            case NONE: {
                result = num;
                break;
            }
        }
        op = Operation.NONE;
        opChosen = false;
        hasDot = true;
        input = result.toString();
        tview.setText(input);
    }
}