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

import java.util.Arrays;

public class Task5Fragment  extends Fragment {

    enum Mode {
        CONVOLUTION,
        EROSION,
        MEDIAN,
        BUILDUP
    }

    static private void applyMaskAndSort(int[] subImg, double[] mask, int mr, int ml, int mb, int mt, int w) {
        for (int i = ml; i < mr; i++) {
            for (int j = mb; j < mt; j++) {

                int pos = (i - ml) + (j - mb) * w;
                int pix = subImg[pos];
                double mul = mask[i + j * (mr - ml)];

                int alpha = Color.alpha(pix);
                int newR = (int)(Color.red(pix) * mul);
                int newG = (int)(Color.green(pix) * mul);
                int newB = (int)(Color.blue(pix) * mul);

                subImg[pos] = Color.argb(alpha, newR, newG, newB);
            }
        }
        Arrays.sort(subImg);
    }
    static private int[] applyMatrix(Bitmap bitmap, double[] matrix, int mw, int mh, Mode mode) {
        int w = bitmap.getWidth();
        int h =  bitmap.getHeight();
        int radiusX = mw / 2;
        int radiusY = mh / 2;

        int[] original = new int[w * h];
        int[] modified = new int[w * h];

        bitmap.getPixels(original, 0, w, 0, 0, w, h);

        for (int x = 0; x < w; x++) {
            for (int y = 0; y < h; y++) {
                int pos = y * w + x;
                int newPixel = Color.WHITE;

                int l = Math.max(x - radiusX, 0);
                int b = Math.max(y - radiusY, 0);
                int r = Math.min(x + radiusX + 1, w);
                int t = Math.min(y + radiusY + 1, h);

                int ml = Math.max(radiusX - x, 0);
                int mb = Math.max(radiusY - y, 0);
                int mr = Math.min(w - x + radiusX, mw);
                int mt = Math.min(h - y + radiusY, mh);

                int[] subImg = new int[(r - l) * (t - b)];
                bitmap.getPixels(subImg, 0, r - l, l, b, r - l, t - b);

                switch (mode) {
                    case CONVOLUTION: {
                        double newR = 0.0;
                        double newG = 0.0;
                        double newB = 0.0;

                        for (int i = ml; i < mr; i++) {
                            for (int j = mb; j < mt; j++) {
                                int pix = subImg[(i - ml) + (j - mb) * (r - l)];
                                double mul = matrix[i + j * (mr - ml)];
                                newR += Color.red(pix) * mul;
                                newG += Color.green(pix) * mul;
                                newB += Color.blue(pix) * mul;
                            }
                        }

                        if (newR > 255) newR = 255.0;
                        if (newG > 255) newG = 255.0;
                        if (newB > 255) newB = 255.0;

                        if (newR < 0) newR = 0.0;
                        if (newG < 0) newG = 0.0;
                        if (newB < 0) newB = 0.0;

                        newPixel = Color.argb(Color.alpha(original[pos]), (int)newR, (int)newG, (int)newB);
                        break;
                    }
                    case EROSION: {
                        applyMaskAndSort(subImg, matrix, mr, ml, mb, mt, r - l);
                        int index = 0;
                        for (int i = ml; i < mr; i++) {
                            for (int j = mb; j < mt; j++) {
                                int xy = i + j * mw;

                                if (matrix[xy] == 0) index ++;
                            }
                        }
                        if (index >= subImg.length) index = subImg.length - 1;
                        newPixel = subImg[index];
                        break;
                    }
                    case MEDIAN: {
                        applyMaskAndSort(subImg, matrix, mr, ml, mb, mt, r - l);

                        newPixel = subImg[subImg.length / 2];
                        break;
                    }
                    case BUILDUP: {
                        applyMaskAndSort(subImg, matrix, mr, ml, mb, mt, r - l);

                        newPixel = subImg[subImg.length - 1];
                        break;
                    }
                }
                modified[pos] = newPixel;
            }
        }

        return modified;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View fragment = inflater.inflate (R.layout.fragment_task5, container, false);

        View.OnClickListener matrixListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inMutable = true;

                Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.picture1, options);
                int w = bitmap.getWidth();
                int h = bitmap.getHeight();

                switch (view.getId()) {
                    case R.id.btn_embed: {
                        double[] matrix = new double[]{
                                0.028087,
                                0.23431,
                                0.475207,
                                0.23431,
                                0.028087
                        };
                        int[] modified = Task5Fragment.applyMatrix(bitmap, matrix, matrix.length, 1, Mode.CONVOLUTION);
                        bitmap.setPixels(modified, 0, w, 0, 0, w, h);
                        modified = Task5Fragment.applyMatrix(bitmap, matrix, matrix.length, 1, Mode.CONVOLUTION);
                        bitmap.setPixels(modified, 0, w, 0, 0, w, h);
                        break;
                    }
                    case R.id.btn_extract: {
                        double[] matrix = new double[]{
                                -1, -1, -1,
                                -1, 9, -1,
                                -1, -1, -1
                        };
                        int[] modified = Task5Fragment.applyMatrix(bitmap, matrix, 3, 3, Mode.CONVOLUTION);
                        bitmap.setPixels(modified, 0, w, 0, 0, w, h);
                        break;
                    }
                    case R.id.btn_sobel: {
                        double[] matrix1 = new double[]{
                                -1, -2, -1,
                                0, 0, 0,
                                1, 2, 1
                        };
                        double[] matrix2 = new double[]{
                                -1, 0, 1,
                                -2, 0, 2,
                                -1, 0, 1
                        };
                        int[] modified1 = Task5Fragment.applyMatrix(bitmap, matrix1, 3, 3, Mode.CONVOLUTION);
                        int[] modified2 = Task5Fragment.applyMatrix(bitmap, matrix2, 3, 3, Mode.CONVOLUTION);

                        for (int i = 0; i < modified1.length && i < modified2.length; i++) {
                            int alpha = Color.alpha(modified1[i]);

                            int r1 = Color.red(modified1[i]);
                            int r2 = Color.red(modified2[i]);
                            int g1 = Color.green(modified1[i]);
                            int g2 = Color.green(modified2[i]);
                            int b1 = Color.blue(modified1[i]);
                            int b2 = Color.blue(modified2[i]);

                            double r = Math.sqrt(Math.pow(r1, 2) + Math.pow(r2, 2));
                            double g = Math.sqrt(Math.pow(g1, 2) + Math.pow(g2, 2));
                            double b = Math.sqrt(Math.pow(b1, 2) + Math.pow(b2, 2));
                            modified1[i] = Color.argb(alpha, (int) r, (int) g, (int) b);
                        }
                        bitmap.setPixels(modified1, 0, w, 0, 0, w, h);
                        break;
                    }
                    case R.id.btn_median: {
                        double[] matrix = new double[]{
                                1, 1, 1, 1, 1,
                                1, 1, 1, 1, 1,
                                1, 1, 1, 1, 1,
                                1, 1, 1, 1, 1,
                                1, 1, 1, 1, 1
                        };
                        int[] modified = Task5Fragment.applyMatrix(bitmap, matrix, 5, 5, Mode.MEDIAN);
                        bitmap.setPixels(modified, 0, w, 0, 0, w, h);
                        break;
                    }
                    case R.id.btn_erosion: {
                        double[] matrix = new double[]{
                                0, 0, 1, 0, 0,
                                0, 1, 1, 1, 0,
                                1, 1, 1, 1, 1,
                                0, 1, 1, 1, 0,
                                0, 0, 1, 0, 0
                        };
                        int[] modified = Task5Fragment.applyMatrix(bitmap, matrix, 5, 5, Mode.EROSION);
                        bitmap.setPixels(modified, 0, w, 0, 0, w, h);
                        break;
                    }
                    case R.id.btn_buildup: {
                        double[] matrix = new double[]{
                                0, 0, 1, 0, 0,
                                0, 1, 1, 1, 0,
                                1, 1, 1, 1, 1,
                                0, 1, 1, 1, 0,
                                0, 0, 1, 0, 0
                        };
                        int[] modified = Task5Fragment.applyMatrix(bitmap, matrix, 5, 5, Mode.BUILDUP);
                        bitmap.setPixels(modified, 0, w, 0, 0, w, h);
                        break;
                    }
                }

                ImageView result = fragment.findViewById(R.id.result);
                result.setImageBitmap(bitmap);

            }
        };

        fragment.findViewById(R.id.btn_median).setOnClickListener(matrixListener);
        fragment.findViewById(R.id.btn_embed).setOnClickListener(matrixListener);
        fragment.findViewById(R.id.btn_extract).setOnClickListener(matrixListener);
        fragment.findViewById(R.id.btn_sobel).setOnClickListener(matrixListener);
        fragment.findViewById(R.id.btn_erosion).setOnClickListener(matrixListener);
        fragment.findViewById(R.id.btn_buildup).setOnClickListener(matrixListener);

        return fragment;
    }
}