package com.arvatel.photoeditor.algorithms;

import android.graphics.Bitmap;

import com.arvatel.photoeditor.Progress;

public class Scaling {
    //http://courses.cs.vt.edu/~masc1044/L17-Rotation/ScalingNN.html
    public  static Bitmap nearestNeighborScaling(Bitmap oldImage, int newWidth, int newHeight, Progress progress) {
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
            if(x%100==0)
                progress.report((double)x/newWidth);
        }
        return newImage;
    }

}
