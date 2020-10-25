package com.example.lab1;

import androidx.appcompat.app.AppCompatActivity;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button[][] Buttons;
    String currPlayer = "X";
    int tilesActivated = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Buttons = new Button[4][4];

        Buttons[0][0] = findViewById(R.id.btn00);
        Buttons[0][1] = findViewById(R.id.btn01);
        Buttons[0][2] = findViewById(R.id.btn02);
        Buttons[0][3] = findViewById(R.id.btn03);
        Buttons[1][0] = findViewById(R.id.btn10);
        Buttons[1][1] = findViewById(R.id.btn11);
        Buttons[1][2] = findViewById(R.id.btn12);
        Buttons[1][3] = findViewById(R.id.btn13);
        Buttons[2][0] = findViewById(R.id.btn20);
        Buttons[2][1] = findViewById(R.id.btn21);
        Buttons[2][2] = findViewById(R.id.btn22);
        Buttons[2][3] = findViewById(R.id.btn23);
        Buttons[3][0] = findViewById(R.id.btn30);
        Buttons[3][1] = findViewById(R.id.btn31);
        Buttons[3][2] = findViewById(R.id.btn32);
        Buttons[3][3] = findViewById(R.id.btn33);

        View.OnClickListener buttonClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Button currButton = (Button) view;
                int x = -1, y = -1;
                for (int i = 0; i < 4; i++)
                    for (int j = 0; j < 4; j++) {
                        if (Buttons[i][j] == currButton) {
                            x = i;
                            y = j;
                        }
                    }
                currButton.setText(currPlayer);
                currButton.setEnabled(false);
                tilesActivated += 1;

                if ((Buttons[x][0].getText() == Buttons[x][1].getText() && Buttons[x][1].getText() == Buttons[x][2].getText() && Buttons[x][2].getText() == Buttons[x][3].getText()) ||
                    (Buttons[0][y].getText() == Buttons[1][y].getText() && Buttons[1][y].getText() == Buttons[2][y].getText() && Buttons[2][y].getText() == Buttons[3][y].getText()) ||
                    (x == 3 - y && Buttons[0][3].getText() == Buttons[1][2].getText() && Buttons[1][2].getText() == Buttons[2][1].getText() && Buttons[2][1].getText() == Buttons[3][0].getText()) ||
                    (x == y && Buttons[0][0].getText() == Buttons[1][1].getText() && Buttons[1][1].getText() == Buttons[2][2].getText() && Buttons[2][2].getText() == Buttons[3][3].getText())) {
                        Toast.makeText(getApplicationContext(), "Player " + currPlayer + " won!", Toast.LENGTH_SHORT).show();
                        resetField();
                }
                else
                if (tilesActivated == 16) {
                    Toast.makeText(getApplicationContext(), "Draw!", Toast.LENGTH_SHORT).show();
                    resetField();
                }

                currPlayer = currPlayer.equals("X") ? "O" : "X";
            }
        };

        for (int i = 0; i < 4; i++)
            for (int j = 0; j < 4; j++)
                Buttons[i][j].setOnClickListener(buttonClickListener);
    }

    private void resetField() {
        for (int i = 0; i < 4; i++)
            for (int j = 0; j < 4; j++){
                Buttons[i][j].setText("");
                Buttons[i][j].setEnabled(true);
            }

        tilesActivated = 0;
    }
}