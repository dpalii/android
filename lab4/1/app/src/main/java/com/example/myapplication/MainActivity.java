package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnUkraine = (Button)findViewById(R.id.btn_ukraine);
        Button btnPoland = (Button)findViewById(R.id.btn_poland);
        Button btnTurkey = (Button)findViewById(R.id.btn_turkey);

        btnUkraine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent();
                in.setClass(MainActivity.this, UkraineActivity.class);
                startActivity(in);
            }
        });

        btnPoland.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent();
                in.setClass(MainActivity.this, PolandActivity.class);
                startActivity(in);
            }
        });

        btnTurkey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent();
                in.setClass(MainActivity.this, TurkeyActivity.class);
                startActivity(in);
            }
        });
    }
}