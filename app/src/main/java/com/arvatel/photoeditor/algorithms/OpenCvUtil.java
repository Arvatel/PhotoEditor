package com.arvatel.photoeditor.algorithms;

import android.graphics.Bitmap;

import org.opencv.android.Utils;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;
import java.util.List;

public class OpenCvUtil {
    public static Bitmap searchForShapes(Bitmap imageBitmap) {

        //convert to Mat: represents an n-dimensional dense numerical single-channel or multi-channel array
        //single channel means each element in the matrix has only one value
        Mat originalMat = new Mat();
        Utils.bitmapToMat(imageBitmap, originalMat);

        Mat cannyEdges = new Mat();//find edges in the photo
        Mat hierarchy = new Mat();//Optional output vector, containing information about the image topology. #contours

        List<MatOfPoint> contourList = new ArrayList<>(); //A list to store all the contours

        // - An image gradient is a directional change in the intensity or color in an image.
        // - A threshold is a value which has two regions on its either side i.e.
        // below the threshold or above the threshold.
        // - Canny does use two thresholds (upper and lower): If a pixel gradient
        // is higher than the upper threshold, the pixel is accepted as an edge.
        // If a pixel gradient value is below the lower threshold, then it is rejected.
        Imgproc.Canny(originalMat, cannyEdges, 10, 100);

        //finding contours: a curve joining all the continuous points (along the boundary), having same color or intensity.
        Imgproc.findContours(cannyEdges, contourList, hierarchy, Imgproc.RETR_LIST, Imgproc.CHAIN_APPROX_SIMPLE);

        for (int i = 0; i < contourList.size(); i++) {

            double epsilon = 0.1 * Imgproc.arcLength(new MatOfPoint2f(contourList.get(i).toArray()), true);
            MatOfPoint2f approx = new MatOfPoint2f();
            Imgproc.approxPolyDP(new MatOfPoint2f(contourList.get(i).toArray()), approx, epsilon, true);

           //Moments m = Imgproc.moments(contourList.get(i));//to get usefull info about the contour like area m.m00
            //get big enough contour to draw it, instead of getting very small contours that will make points in the more complex photos
            if (Imgproc.contourArea(contourList.get(i))> 1000.0) {//another way of getting the area {in pixel}
                switch ((int) approx.total()) {//total: Returns the total number of array elements.
                    case 5://pentagon
                    case 3://triangle
                    case 4://square
                    case 9://half-circle
                    case 15://circle
                        Imgproc.drawContours(originalMat, contourList, i, new Scalar(0, 0, 0), 30);
                }
            }
        }


        Bitmap b = Bitmap.createBitmap(imageBitmap);
        //Converting Mat back to Bitmap
        Utils.matToBitmap(originalMat, b);
        return b;
    }


}
