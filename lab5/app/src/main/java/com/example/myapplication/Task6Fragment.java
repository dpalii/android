package com.example.myapplication;

import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.Arrays;

public class Task6Fragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View fragment = inflater.inflate (R.layout.fragment_task6, container, false);
        final int layer = 1;
        final ImageView result = fragment.findViewById(R.id.result);

        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inMutable = true;
        final Bitmap watermark = BitmapFactory.decodeResource(getResources(), R.drawable.watermark, options);

        final int wmh = watermark.getHeight();
        final int wmw = watermark.getWidth();

        final int[] wmPixels = new int[wmh * wmh];
        watermark.getPixels(wmPixels, 0, wmw, 0, 0, wmw, wmh);

        for(int i = 0; i < wmPixels.length; i++) {
            wmPixels[i] = Color.blue(wmPixels[i]) < 128 ? 0 : 1;
        }

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bitmap picture;
                if (view.getId() == R.id.btn_extract) picture = ((BitmapDrawable)result.getDrawable()).getBitmap();
                else picture = BitmapFactory.decodeResource(getResources(), R.drawable.picture1);

                int ph = picture.getHeight();
                int pw = picture.getWidth();

                int[] picPixels = new int[ph * pw];
                picture.getPixels(picPixels, 0, pw, 0, 0, pw, ph);

                for (int x = 0; x < pw; x++) {
                    for (int y = 0; y < ph; y++) {
                        int index = x + y * pw;
                        int wmIndex = (x % wmw) + (y % wmh) * wmw;

                        int pixel = Color.blue(picPixels[index]);

                        char[] binary = new char[8];
                        Arrays.fill(binary, '0');

                        int pos = 0;
                        while(pixel > 0 && pos < binary.length) {
                            binary[pos] = (char)(pixel % 2 + '0');
                            pixel /= 2;
                            pos += 1;
                        }

                        pos = layer - 1;
                        int bit = binary[pos] - '0';
                        int newPixel = Color.BLACK;

                        switch (view.getId()) {
                            case R.id.btn_embed: {
                                bit = wmPixels[wmIndex];
                                binary[pos] = (char) (bit + '0');

                                pixel = 0;
                                for (int i = 0; i < binary.length; i++) {
                                    bit = binary[i] - '0';
                                    pixel += bit == 1 ? Math.pow(2, i) : 0;
                                }
                                newPixel = Color.argb(
                                        Color.alpha(picPixels[index]),
                                        Color.red(picPixels[index]),
                                        Color.green(picPixels[index]),
                                        pixel
                                );
                                break;
                            }
                            case R.id.btn_extract: {
                                newPixel = bit * Color.BLUE;
                                break;
                            }
                        }

                        picPixels[index] = newPixel;
                    }
                }

                result.setImageBitmap(Bitmap.createBitmap(picPixels, pw, ph, Bitmap.Config.ARGB_8888));
            }
        };

        fragment.findViewById(R.id.btn_extract).setOnClickListener(listener);
        fragment.findViewById(R.id.btn_embed).setOnClickListener(listener);
        return fragment;
    }
}