package com.arvatel.photoeditor.algorithms;

import android.graphics.Bitmap;
import android.graphics.Color;

public class Filter {
     private static final int HIGHEST_COLOR_VALUE = 255;
     private static final int LOWEST_COLOR_VALUE = 0;

    public static Bitmap applySketchFilter(Bitmap oldBitmap) {
        // copying to newBitmap for manipulation
        Bitmap newBitmap = oldBitmap.copy(Bitmap.Config.ARGB_8888, true);

        // height and width of Image
        int imageHeight = newBitmap.getHeight();
        int imageWidth = newBitmap.getWidth();

        // traversing each pixel in Image as an 2D Array
        for (int i = 0; i < imageWidth; i++) {

            for (int j = 0; j < imageHeight; j++) {

                // getting each pixel
                int oldPixel = oldBitmap.getPixel(i, j);

                int newPixel = getSketchPixel(oldPixel);

                newBitmap.setPixel(i, j, newPixel);
            }
        }
        return newBitmap;

    }

    private static int getSketchPixel(int oldPixel) {
        // each pixel is made from RED_BLUE_GREEN_ALPHA
        // so, getting current values of pixel
        int oldRed = Color.red(oldPixel);
        int oldBlue = Color.blue(oldPixel);
        int oldGreen = Color.green(oldPixel);
        int oldAlpha = Color.alpha(oldPixel);



        int intensity = (oldRed + oldBlue + oldGreen) / 3;

        // applying new pixel value to newBitmap
        // condition for Sketch
        int newPixel = 0;
        int INTENSITY_FACTOR = 120;

        if (intensity > INTENSITY_FACTOR) {
            // apply white color
            newPixel = Color.argb(oldAlpha, HIGHEST_COLOR_VALUE, HIGHEST_COLOR_VALUE, HIGHEST_COLOR_VALUE);

        } else if (intensity > 100) {
            // apply grey color
            newPixel = Color.argb(oldAlpha, 150, 150, 150);
        } else {
            // apply black color
            newPixel = Color.argb(oldAlpha, LOWEST_COLOR_VALUE, LOWEST_COLOR_VALUE, LOWEST_COLOR_VALUE);
        }
        return newPixel;
    }

    public  static Bitmap applyGreyFilter(Bitmap oldBitmap) {
        // copying to newBitmap for manipulation
        Bitmap newBitmap = oldBitmap.copy(Bitmap.Config.ARGB_8888, true);

        // height and width of Image
        int imageHeight = newBitmap.getHeight();
        int imageWidth = newBitmap.getWidth();

        // traversing each pixel in Image as an 2D Array
        for (int i = 0; i < imageWidth; i++) {

            for (int j = 0; j < imageHeight; j++) {

                // getting each pixel
                int oldPixel = oldBitmap.getPixel(i, j);
                int newPixel = getGreyPixel(oldPixel);
                newBitmap.setPixel(i, j, newPixel);
            }
        }
        return newBitmap;
    }

    private static int getGreyPixel(int oldPixel) {
        // each pixel is made from RED_BLUE_GREEN_ALPHA
        // so, getting current values of pixel
        int oldRed = Color.red(oldPixel);
        int oldBlue = Color.blue(oldPixel);
        int oldGreen = Color.green(oldPixel);
        int oldAlpha = Color.alpha(oldPixel);


        int intensity = (oldRed + oldBlue + oldGreen) / 3;

        // applying new pixel values from above to newBitmap
        return Color.argb(oldAlpha, intensity, intensity, intensity);
    }

    public  static Bitmap applySepiaFilter(Bitmap oldBitmap) {
        // copying to newBitmap for manipulation
        Bitmap newBitmap = oldBitmap.copy(Bitmap.Config.ARGB_8888, true);

        // height and width of Image
        int imageHeight = newBitmap.getHeight();
        int imageWidth = newBitmap.getWidth();

        // traversing each pixel in Image as an 2D Array
        for (int i = 0; i < imageWidth; i++) {

            for (int j = 0; j < imageHeight; j++) {

                // getting each pixel
                int oldPixel = oldBitmap.getPixel(i, j);
                int newPixel = getSepiaPixel(oldPixel);
                newBitmap.setPixel(i, j, newPixel);
            }
        }
        return newBitmap;
    }

    private static int getSepiaPixel(int oldPixel) {
        // so, getting current values of pixel
        int oldRed = Color.red(oldPixel);
        int oldBlue = Color.blue(oldPixel);
        int oldGreen = Color.green(oldPixel);
        int oldAlpha = Color.alpha(oldPixel);



        // Algorithm for SEPIA FILTER
        int newRed = (int) (0.393 * oldRed + 0.769 * oldGreen + 0.189 * oldBlue);
        int newGreen = (int) (0.349 * oldRed + 0.686 * oldGreen + 0.168 * oldBlue);
        int newBlue = (int) (0.272 * oldRed + 0.534 * oldGreen + 0.131 * oldBlue);

        newRed = Math.min(newRed, 255);
        newGreen = Math.min(newGreen, 255);
        newBlue = Math.min(newBlue, 255);

        // applying new pixel values from above to newBitmap
        return Color.argb(oldAlpha, newRed, newGreen, newBlue);
    }

    public static Bitmap applyCircularFilter(Bitmap oldBitmap) {
        Bitmap newBitmap = oldBitmap.copy(Bitmap.Config.ARGB_8888, true);
        int imageWidth = oldBitmap.getWidth();
        int imageHeight = oldBitmap.getHeight();

        int i, k = 0, l = 0;
        //divide the photo into four triangles
        //https://www.geeksforgeeks.org/print-a-given-matrix-in-spiral-form/
        //k- starting row index, m- ending row index, l- starting column index, n- ending column index
        //i - iterator
        //
        int height = oldBitmap.getHeight();
        int width = oldBitmap.getWidth();
        while (k < height && l < width) {
        /* Print the first row from
               the remaining rows */
            for (i = l; i < width; ++i) {
                // getting each pixel
                int oldPixel = oldBitmap.getPixel(k, i);
                int newPixel = getSepiaPixel(oldPixel);
                newBitmap.setPixel(k, i, newPixel);
            }
            k++;

        /* Print the last column
         from the remaining columns */
            for (i = k; i < height; ++i) {
                int oldPixel = oldBitmap.getPixel(i, width - 1);
                int newPixel = getGreyPixel(oldPixel);
                newBitmap.setPixel(i, width - 1, newPixel);
            }
            width--;

        /* Print the last row from
                the remaining rows */
            if (k < height) {
                for (i = width - 1; i >= l; --i) {
                    int oldPixel = oldBitmap.getPixel(height - 1, i);
                    int newPixel = getSepiaPixel(oldPixel);
                    newBitmap.setPixel(height - 1, i, newPixel);

                }
                height--;
            }

        /* Print the first column from
                   the remaining columns */
            if (l < width) {
                for (i = height - 1; i >= k; --i) {
                    int oldPixel = oldBitmap.getPixel(i, l);
                    int newPixel = getGreyPixel(oldPixel);
                    newBitmap.setPixel(i, l, newPixel);
                }
                l++;
            }
        }


        //make circle in the center
        //https://stackoverflow.com/questions/481144/equation-for-testing-if-a-point-is-inside-a-circle?rq=1
        int center_x = imageWidth / 2;
        int center_y = imageHeight / 2;
        int radius = width / 2;
        System.out.println(center_x+" "+center_y);
        System.out.println(radius);
        for (i = 0; i < imageHeight; i++) {
            for (int j = 0; j < imageWidth; j++) {
                if ((int)Math.pow(i - center_x, 2) + Math.pow(j - center_y, 2) < radius * radius) {
                    int oldPixel = oldBitmap.getPixel(i, j);
                    int newPixel = getGreyPixel(oldPixel);
                    newBitmap.setPixel(i, j, newPixel);
                }
            }
        }
        return newBitmap;
    }


}
