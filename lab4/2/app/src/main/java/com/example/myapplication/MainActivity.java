package com.example.myapplication;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnUkraine = (Button)findViewById(R.id.btn_ukraine);
        Button btnPoland = (Button)findViewById(R.id.btn_poland);
        Button btnTurkey = (Button)findViewById(R.id.btn_turkey);

        final FragmentManager fragmentManager = getFragmentManager();
        final Fragment polandFragment = new PolandFragment();
        final Fragment ukraineFragment = new UkraineFragment();
        final Fragment turkeyFragment = new TurkeyFragment();

        btnUkraine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.country_details, ukraineFragment);
                fragmentTransaction.addToBackStack (null);
                fragmentTransaction.commit ();
            }
        });

        btnPoland.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.country_details, polandFragment);
                fragmentTransaction.addToBackStack (null);
                fragmentTransaction.commit ();
            }
        });

        btnTurkey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.country_details, turkeyFragment);
                fragmentTransaction.addToBackStack (null);
                fragmentTransaction.commit ();
            }
        });
    }
}