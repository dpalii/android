package com.example.myapplication;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Printer;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final FragmentManager fragmentManager = getFragmentManager();
        final Fragment task1Fragment = new Task1Fragment();
        final Fragment task2Fragment = new Task2Fragment();
        final Fragment task3Fragment = new Task3Fragment();
        final Fragment task4Fragment = new Task4Fragment();
        final Fragment task5Fragment = new Task5Fragment();
        final Fragment task6Fragment = new Task6Fragment();

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                switch(v.getId()) {
                    case R.id.btn_1: {
                        fragmentTransaction.replace(R.id.task_container, task1Fragment);
                        break;
                    }
                    case R.id.btn_2: {
                        fragmentTransaction.replace(R.id.task_container, task2Fragment);
                        break;
                    }
                    case R.id.btn_3: {
                        fragmentTransaction.replace(R.id.task_container, task3Fragment);
                        break;
                    }
                    case R.id.btn_4: {
                        fragmentTransaction.replace(R.id.task_container, task4Fragment);
                        break;
                    }
                    case R.id.btn_5: {
                        fragmentTransaction.replace(R.id.task_container, task5Fragment);
                        break;
                    }
                    case R.id.btn_6: {
                        fragmentTransaction.replace(R.id.task_container, task6Fragment);
                        break;
                    }
                }
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        };

        findViewById(R.id.btn_1).setOnClickListener(listener);
        findViewById(R.id.btn_2).setOnClickListener(listener);
        findViewById(R.id.btn_3).setOnClickListener(listener);
        findViewById(R.id.btn_4).setOnClickListener(listener);
        findViewById(R.id.btn_5).setOnClickListener(listener);
        findViewById(R.id.btn_6).setOnClickListener(listener);
    }
}