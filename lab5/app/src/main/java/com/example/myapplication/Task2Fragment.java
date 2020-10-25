package com.example.myapplication;

import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class Task2Fragment  extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View fragment = inflater.inflate (R.layout.fragment_task2, container, false);

        final int shift = 32;

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inMutable = true;
                Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.picture1, options);

                int w = bitmap.getWidth();
                int h =  bitmap.getHeight();

                int[] pixels = new int[w * h];
                bitmap.getPixels(pixels, 0, w, 0, 0, w, h);

                int shiftR = 0;
                int shiftG = 0;
                int shiftB = 0;

                switch (view.getId()) {
                    case R.id.btn_embed: {
                        shiftR = shift;
                        shiftG = 0;
                        shiftB = 0;
                        break;
                    }
                    case R.id.btn_extract: {
                        shiftG = shift;
                        shiftR = 0;
                        shiftB = 0;
                        break;
                    }
                    case R.id.btn_median: {
                        shiftB = shift;
                        shiftR = 0;
                        shiftG = 0;
                        break;
                    }
                }

                for (int x = 0; x < bitmap.getWidth(); x++) {
                    for (int y = 0; y < bitmap.getHeight(); y++) {
                        int pos = x + y * w;

                        int originalColor = pixels[pos];

                        int alpha = Color.alpha(originalColor);
                        int r = Color.red(originalColor) + shiftR;
                        int g = Color.green(originalColor) + shiftG;
                        int b = Color.blue(originalColor) + shiftB;
                        if (r > 255) r = 255;
                        if (g > 255) g = 255;
                        if (b > 255) b = 255;

                        pixels[pos] = Color.argb(alpha, r, g, b);
                    }
                }

                bitmap.setPixels(pixels, 0, w, 0, 0, w, h);

                ImageView modified = fragment.findViewById(R.id.result);
                modified.setImageBitmap(bitmap);
            }
        };

        fragment.findViewById(R.id.btn_embed).setOnClickListener(listener);
        fragment.findViewById(R.id.btn_extract).setOnClickListener(listener);
        fragment.findViewById(R.id.btn_median).setOnClickListener(listener);
        return fragment;
    }
}