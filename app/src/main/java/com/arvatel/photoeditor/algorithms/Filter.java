package com.arvatel.photoeditor.algorithms;

import android.graphics.Bitmap;
import android.graphics.Color;

import androidx.core.graphics.ColorUtils;

import org.opencv.core.Mat;

public class Filter {
     static final int HIGHEST_COLOR_VALUE = 255;
     static final int LOWEST_COLOR_VALUE = 0;

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
        int newRed = intensity;
        int newBlue = intensity;
        int newGreen = intensity;

        // applying new pixel values from above to newBitmap
        return Color.argb(oldAlpha, newRed, newGreen, newBlue);
    }

    public  static Bitmap applySpeiaFilter(Bitmap oldBitmap) {
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
                int newPixel = getSpiaPixel(oldPixel);
                newBitmap.setPixel(i, j, newPixel);
            }
        }
        return newBitmap;
    }

    private static int getSpiaPixel(int oldPixel) {
        // so, getting current values of pixel
        int oldRed = Color.red(oldPixel);
        int oldBlue = Color.blue(oldPixel);
        int oldGreen = Color.green(oldPixel);
        int oldAlpha = Color.alpha(oldPixel);



        // Algorithm for SEPIA FILTER
        int newRed = truncate(0.393 * oldRed + 0.769 * oldGreen + 0.189 * oldBlue);
        int newGreen = truncate(0.349 * oldRed + 0.686 * oldGreen + 0.168 * oldBlue);
        int newBlue = truncate(0.272 * oldRed + 0.534 * oldGreen + 0.131 * oldBlue);


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
        int height = oldBitmap.getWidth();
        int width = oldBitmap.getHeight();
        while (k < height && l < width) {
        /* Print the first row from
               the remaining rows */
            for (i = l; i < width; ++i) {
                // getting each pixel
                int oldPixel = oldBitmap.getPixel(k, i);
                int newPixel = getSpiaPixel(oldPixel);
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
                    int newPixel = getSpiaPixel(oldPixel);
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


    /*********************************/


    public static Bitmap applySharpining(Bitmap oldBitmap) {//https://stackoverflow.com/questions/2938162/how-does-an-unsharp-mask-work
        Bitmap bluredImage = getBluredImage(oldBitmap);
        Bitmap unsharpMask = subBluredImage(bluredImage, oldBitmap);
        Bitmap contrastedImage = increaseContrast(oldBitmap);
        return theActualAlgorithm(oldBitmap, unsharpMask, contrastedImage);
//        return contrastedImage;
    }

    private static Bitmap theActualAlgorithm(Bitmap oldBitmap, Bitmap unsharpMask, Bitmap contrastedImage) {
        Bitmap newBitmap = oldBitmap.copy(Bitmap.Config.ARGB_8888, true);

        for (int i = 0; i < oldBitmap.getWidth(); i++) {
            for (int j = 0; j < oldBitmap.getHeight(); j++) {
                // getting each pixel
                int unsharpMaskPixel = unsharpMask.getPixel(i, j);
                int contrastPixel = contrastedImage.getPixel(i, j);
                int ogriginalPixel = oldBitmap.getPixel(i, j);
                int newPixel = getUnsharpMaskPixel(unsharpMaskPixel, contrastPixel, ogriginalPixel);
                newBitmap.setPixel(i, j, newPixel);
            }
        }
        return newBitmap;
    }

    private static int getUnsharpMaskPixel(int unsharpMaskPixel, int highContrast, int original) {
        int unsharpRed = Color.red(unsharpMaskPixel);
        int unsharpBlue = Color.blue(unsharpMaskPixel);
        int unsharpGreen = Color.green(unsharpMaskPixel);
        int unsharpAlpha = Color.alpha(unsharpMaskPixel);
//        float[] unsharpHsv = new float[3];
//        ColorUtils.RGBToHSL(unsharpRed, unsharpGreen, unsharpBlue, unsharpHsv);


        int contRed = Color.red(highContrast);
        int contBlue = Color.blue(highContrast);
        int contGreen = Color.green(highContrast);
//        float[] constHsv = new float[3];
//        ColorUtils.RGBToHSL(contRed, contGreen, contBlue, constHsv);


        int oldRed = Color.red(original);
        int oldBlue = Color.blue(original);
        int oldGreen = Color.green(original);
        int oldAlpha = Color.alpha(original);
//        float[] oldHsv = new float[3];
//        ColorUtils.RGBToHSL(oldRed, oldGreen, oldBlue, oldHsv);

        /***************/
//        float difHSV = constHsv[1] - oldHsv[1];
//        float deltaHSV = difHSV*unsharpHsv[2];
//        if(Math.abs(deltaHSV)>=0)
//            oldHsv[1]+=deltaHSV;
        /***************/

        // calc luminance in range 0.0 to 1.0; using SRGB luminance constants
        float luminance = calculateBrightness(unsharpRed, unsharpGreen, unsharpBlue);
        //make this using hsl instead of rgp then go back
        float diffR = contRed - oldRed;
        float diffG = contGreen - oldGreen;
        float diffB = contBlue - oldBlue;

        float deltaR = diffR * (luminance);
        float deltaG = diffG * (luminance);
        float deltaB = diffB * (luminance);

//        if (Math.abs(deltaR) > 0)//2
            oldRed += deltaR;
//        if (Math.abs(deltaG) > 0)
            oldGreen += deltaG;
//        if (Math.abs(deltaB) > 0)
            oldBlue += deltaB;

//        return ColorUtils.HSLToColor(oldHsv);
        // applying new pixel values from above to newBitmap
        return Color.argb(oldAlpha, truncate(oldRed), truncate(oldGreen), truncate(oldBlue));
    }

    private static float calculateBrightness(int sR, int sG, int sB) {

        return (((sR + sG + sB) / 3f) / 255f) * 100;
//        float vR = sR / 255f;
//        float vG = sG / 255f;
//        float vB = sB / 255f;
//
//        return (float) (0.2126 * sRGBtoLin(vR) + 0.7152 * sRGBtoLin(vG) + 0.0722 * sRGBtoLin(vB));
    }

//    private static float sRGBtoLin(float colorChannel) {
//        // Send this function a decimal sRGB gamma encoded color value
//        // between 0.0 and 1.0, and it returns a linearized value.
//
//        if (colorChannel <= 0.04045) {
//            return colorChannel / 12.92f;
//        } else {
//            return (float) Math.pow(((colorChannel + 0.055) / 1.055f), 2.4);
//        }
//    }

    private static Bitmap increaseContrast(Bitmap original) {

        Bitmap newBitmap = original.copy(Bitmap.Config.ARGB_8888, true);

        float contrastLevel = 0f;
        float f = (259f * (contrastLevel + 255f)) / (255f * (259f - contrastLevel));

        for (int i = 0; i < original.getWidth(); i++) {
            for (int j = 0; j < original.getHeight(); j++) {
                //blur
                int originalPixel = original.getPixel(i, j);
                int pixelValueR = truncate(changeContrast(Color.red(originalPixel), f));
                int pixelValueG = truncate(changeContrast(Color.green(originalPixel), f));
                int pixelValueB = truncate(changeContrast(Color.blue(originalPixel), f));

                int newPixel = Color.argb(Color.alpha(originalPixel), pixelValueR, pixelValueG, pixelValueB);
                newBitmap.setPixel(i, j, newPixel);
            }
        }
        return newBitmap;
    }

    private static double changeContrast(int color, float f) {
        return f * (color - 128f) + 128f;
    }

    private static Bitmap subBluredImage(Bitmap bluredImage, Bitmap originalImage) {
        Bitmap newBitmap = originalImage.copy(Bitmap.Config.ARGB_8888, true);

        for (int i = 0; i < originalImage.getWidth(); i++) {
            for (int j = 0; j < originalImage.getHeight(); j++) {
                //blur
                int bluredPixel = bluredImage.getPixel(i, j);
                int pixelValueR = Color.red(bluredPixel);
                int pixelValueG = Color.green(bluredPixel);
                int pixelValueB = Color.blue(bluredPixel);

                //original
                int originalPixel = originalImage.getPixel(i, j);
                int pixelValueRo = Color.red(originalPixel);
                int pixelValueGo = Color.green(originalPixel);
                int pixelValueBo = Color.blue(originalPixel);

                //new sharp
                int newPixelValueR = truncate(-1 * (pixelValueR - pixelValueRo));
                int newPixelValueG = truncate(-1 * (pixelValueG - pixelValueGo));
                int newPixelValueB = truncate(-1 * (pixelValueB - pixelValueBo));

                int newPixel = Color.argb(Color.alpha(originalPixel), newPixelValueR, newPixelValueG, newPixelValueB);
                newBitmap.setPixel(i, j, newPixel);
            }
        }
        return newBitmap;
    }


    private static float[][] kernelGuasanBlur = {//https://en.wikipedia.org/wiki/Kernel_(image_processing)
            {1 / 16f, 1 / 8f, 1 / 16f},
            {1 / 8f, 1 / 4f, 1 / 8f},
            {1 / 16f, 1 / 8f, 1 / 16f}
    };

    private static Bitmap getBluredImage(Bitmap oldBitmap) {
        Bitmap newBitmap = oldBitmap.copy(Bitmap.Config.ARGB_8888, true);
        //a nice pseudo code
        //https://stackoverflow.com/questions/1696113/how-do-i-gaussian-blur-an-image-without-using-any-in-built-gaussian-functions
        for (int i = 1; i < oldBitmap.getWidth() - 1; i++) {
            for (int j = 1; j < oldBitmap.getHeight() - 1; j++) {
                float newPixelValueR = 0;
                float newPixelValueG = 0;
                float newPixelValueB = 0;
                int alpha = Color.alpha(oldBitmap.getPixel(i, j));

                for (int xk = i - 1, kx = 0; xk <= i + 1; xk++, kx++) {
                    for (int yk = j - 1, ky = 0; yk <= j + 1; yk++, ky++) {

                        int oldPixel = oldBitmap.getPixel(xk, yk);
                        float pixelValueR = Color.red(oldPixel);
                        float pixelValueG = Color.green(oldPixel);
                        float pixelValueB = Color.blue(oldPixel);
                        newPixelValueR += kernelGuasanBlur[kx][ky] * pixelValueR;
                        newPixelValueG += kernelGuasanBlur[kx][ky] * pixelValueG;
                        newPixelValueB += kernelGuasanBlur[kx][ky] * pixelValueB;
                    }
                }
                int newPixel = Color.argb(alpha, truncate(newPixelValueR), truncate(newPixelValueG), truncate(newPixelValueB));
                newBitmap.setPixel(i, j, newPixel);
            }
        }
        return newBitmap;
    }

    private static int truncate(double value) {
        if (value < 0)
            value = 0;
        else if (value > 255)
            value = 255;
        return (int) value;
    }
}
