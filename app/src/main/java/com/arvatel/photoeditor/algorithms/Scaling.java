package com.arvatel.photoeditor.algorithms;

import android.graphics.Bitmap;

public class Scaling {

    public  static Bitmap nearestNeighborScaling(Bitmap oldImage, int newWidth, int newHeight) {
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
    public static Bitmap nearestNeighborScalingRatio(Bitmap oldImage, double ratio) {
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
