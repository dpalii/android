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

public class Task1Fragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate (R.layout.fragment_task1, container, false);

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inMutable = true;
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.picture1, options);

        int w = bitmap.getWidth();
        int h =  bitmap.getHeight();

        int[] pixels = new int[w * h];
        bitmap.getPixels(pixels, 0, w, 0, 0, w, h);

        for (int x = 0; x < bitmap.getWidth(); x++) {
            for (int y = 0; y < bitmap.getHeight(); y++) {
                int pos = x + y * w;

                int originalColor = pixels[pos];

                int alpha = Color.alpha(originalColor);
                int r = 255 - Color.red(originalColor);
                int g = 255 - Color.green(originalColor);
                int b = 255 - Color.blue(originalColor);

                pixels[pos] = Color.argb(alpha, r, g, b);
            }
        }

        bitmap.setPixels(pixels, 0, w, 0, 0, w, h);

        ImageView modified = view.findViewById(R.id.result);
        modified.setImageBitmap(bitmap);

        return view;
    }
}