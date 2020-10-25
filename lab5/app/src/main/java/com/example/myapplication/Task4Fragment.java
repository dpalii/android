package com.example.myapplication;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

public class Task4Fragment  extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View fragment = inflater.inflate (R.layout.fragment_task4, container, false);
        final TextView alphaVal = fragment.findViewById(R.id.alpha_val);

        alphaVal.setText(getString(R.string.a, 0.0));

        View.OnClickListener btnListener = new View.OnClickListener() {

            private double lastAlpha = 0.0;

            @Override
            public void onClick(View view) {

                final Handler handler = new Handler();

                double alpha = this.lastAlpha;
                double alphaMax = ((SeekBar)fragment.findViewById(R.id.alpha_slider)).getProgress() / 100.0;

                class MyRunnable implements Runnable {
                    private Handler handler;
                    private View view;
                    private double alphaMax;
                    private double alpha;

                    private double range;
                    private double sign;
                    private double delta;

                    public MyRunnable(Handler handler, View view, double alpha, double alphaMax) {
                        this.handler = handler;
                        this.view = view;
                        this.alpha = alpha;
                        this.alphaMax = alphaMax;

                        this.range = alphaMax - alpha;
                        this.sign = Math.abs(range) / range;
                        this.delta = Math.abs(range) / 10.0;
                    }
                    @Override
                    public void run() {
                        BitmapFactory.Options options = new BitmapFactory.Options();
                        options.inMutable = true;

                        Bitmap img1 = BitmapFactory.decodeResource(getResources(), R.drawable.picture1, options);
                        Bitmap img2 = BitmapFactory.decodeResource(getResources(), R.drawable.picture2, options);

                        int w1 = img1.getWidth();
                        int h1 =  img1.getHeight();

                        int w2 = img2.getWidth();
                        int h2 =  img2.getHeight();

                        int[] pixels1 = new int[w1 * h1];
                        img1.getPixels(pixels1, 0, w1, 0, 0, w1, h1);

                        int[] pixels2 = new int[w2 * h2];
                        img2.getPixels(pixels2, 0, w2, 0, 0, w2, h2);


                        for (int x = 0; x < img1.getWidth() && x < img2.getWidth(); x++) {
                            for (int y = 0; y < img1.getHeight() && y < img2.getHeight(); y++) {
                                int pos = x + y * w1;

                                int pixel1 = pixels1[pos];
                                int pixel2 = pixels2[pos];

                                int newPixel = Color.argb(
                                        (int) (this.alpha * Color.alpha(pixel2) + (1 - this.alpha) * Color.alpha(pixel1)),
                                        (int) (this.alpha * Color.red(pixel2) + (1 - this.alpha) * Color.red(pixel1)),
                                        (int) (this.alpha * Color.green(pixel2) + (1 - this.alpha) * Color.green(pixel1)),
                                        (int) (this.alpha * Color.blue(pixel2) + (1 - this.alpha) * Color.blue(pixel1))
                                );

                                pixels1[pos] = newPixel;
                            }
                        }


                        ImageView result = fragment.findViewById(R.id.result_img);
                        img1.setPixels(pixels1, 0, w1, 0, 0, w1, h1);
                        result.setImageBitmap(img1);
                        this.alpha += sign * delta;

                        if (Math.abs(alpha - alphaMax) > delta) handler.postDelayed(this, 100);
                    }

                }
                handler.post(new MyRunnable(handler, view, alpha, alphaMax));

                this.lastAlpha = alphaMax;
            }
        };

        fragment.findViewById(R.id.confirm).setOnClickListener(btnListener);

        ((SeekBar)fragment.findViewById(R.id.alpha_slider)).setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

                double alpha = seekBar.getProgress() / 100.0;
                alphaVal.setText(getString(R.string.a, alpha));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
        return fragment;
    }
}