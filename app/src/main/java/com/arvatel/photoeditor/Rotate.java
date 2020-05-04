package com.arvatel.photoeditor;

import android.graphics.Bitmap;


public class Rotate {

    public static Bitmap rotate(Bitmap img) {
        int width = img.getWidth();
        int height = img.getHeight();
        Bitmap newImage = Bitmap.createBitmap(height, width, img.getConfig());
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                newImage.setPixel(height - 1 - j, i, img.getPixel(i, j));
            }
        }
        return newImage;
    }

    public static Bitmap rotate(Bitmap img, double degrees) {
        int width = img.getWidth();
        int height = img.getHeight();
        Bitmap newImage =Bitmap.createBitmap(width, height, img.getConfig());

        double angle = Math.toRadians(degrees);
        double sin = Math.sin(angle);
        double cos = Math.cos(angle);
        double x0 = 0.5 * (width - 1);     // point to rotate about
        double y0 = 0.5 * (height - 1);     // center of image

        // rotation
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                double a = x - x0;
                double b = y - y0;
                int xx = (int) (+a * cos - b * sin + x0);
                int yy = (int) (+a * sin + b * cos + y0);

                if (xx >= 0 && xx < width && yy >= 0 && yy < height) {
                    newImage.setPixel(x, y, img.getPixel(xx, yy));
                }
            }
        }

        return newImage;
    }
}