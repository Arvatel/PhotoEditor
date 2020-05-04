package com.arvatel.photoeditor;

import android.graphics.Bitmap;

public class Scalling {

    public static Bitmap progressiveScaling(Bitmap before, Integer longestSideLength) {

        Integer w = before.getWidth();
        Integer h = before.getHeight();

        double ratio = h > w ? longestSideLength.doubleValue() / h : longestSideLength.doubleValue() / w;

        //Multi Step Rescale operation
        //This technique is describen in Chris Campbell’s blog The Perils of Image.getScaledInstance(). As Chris mentions, when downscaling to something less than factor 0.5, you get the best result by doing multiple downscaling with a minimum factor of 0.5 (in other words: each scaling operation should scale to maximum half the size).
        while (ratio < 0.5) {
            Bitmap tmp = scale(before, 0.5);
            before = tmp;
            w = before.getWidth();
            h = before.getHeight();
            ratio = h > w ? longestSideLength.doubleValue() / h : longestSideLength.doubleValue() / w;
        }
        Bitmap after = scale(before, ratio);
        return after;
    }

    private static Bitmap scale(Bitmap imageToScale, Double ratio) {
        int dWidth = ((Double) (imageToScale.getWidth() * ratio)).intValue();
        int dHeight = ((Double) (imageToScale.getHeight() * ratio)).intValue();
        Bitmap scaledImage = Bitmap.createBitmap(dWidth, dHeight, Bitmap.Config.ARGB_8888);

        return scaledImage;
    }

    static Bitmap nearestNeighborScaling(Bitmap oldImage, int newWidth, int newHeight) {
        Bitmap newImage = Bitmap.createBitmap(newWidth, newHeight, oldImage.getConfig());
        int oldWidth = oldImage.getWidth();
        int oldHeight = oldImage.getHeight();

        for (int x = 0; x < newWidth; x++) {
            for (int y = 0; y < newHeight; y++) {
                int srcX = Math.round((float) x / (float)newWidth *(float) oldWidth);
                int srcY = Math.round((float)y / (float)newHeight * (float)oldHeight);
                srcX = Math.min(srcX, oldWidth - 1);
                srcY = Math.min(srcY, oldHeight - 1);
                newImage.setPixel(x, y, oldImage.getPixel(srcX, srcY));
            }
        }
        return newImage;
    }
    static Bitmap nearestNeighborScalingRatio(Bitmap oldImage, double ratio) {
        int newWidth = ((Double) (oldImage.getWidth() * ratio)).intValue();
        int newHeight = ((Double) (oldImage.getHeight() * ratio)).intValue();
        Bitmap newImage = Bitmap.createBitmap(newWidth, newHeight, oldImage.getConfig());
        int oldWidth = oldImage.getWidth();
        int oldHeight = oldImage.getHeight();

        for (int x = 0; x < newWidth; x++) {
            for (int y = 0; y < newHeight; y++) {
                int srcX = Math.round((float) x / (float)newWidth *(float) oldWidth);
                int srcY = Math.round((float)y / (float)newHeight * (float)oldHeight);
                srcX = Math.min(srcX, oldWidth - 1);
                srcY = Math.min(srcY, oldHeight - 1);
                newImage.setPixel(x, y, oldImage.getPixel(srcX, srcY));
            }
        }
        return newImage;
    }
}
