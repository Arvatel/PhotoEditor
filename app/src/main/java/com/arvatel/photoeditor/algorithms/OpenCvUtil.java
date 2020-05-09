package com.arvatel.photoeditor.algorithms;

import android.graphics.Bitmap;

import org.opencv.android.Utils;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.opencv.imgproc.Moments;

import java.util.ArrayList;
import java.util.List;

public class OpenCvUtil {
    public static Bitmap processImage(Bitmap imageBitmap) {

        //convert to Mat: represents an n-dimensional dense numerical single-channel or multi-channel array
        //single channel means each element in the matrix has only one value
        Mat originalMat = new Mat();
        Utils.bitmapToMat(imageBitmap, originalMat);

        Mat grayMat = new Mat();
        Mat cannyEdges = new Mat();
        Mat hierarchy = new Mat();

        List<MatOfPoint> contourList = new ArrayList<>(); //A list to store all the contours

        //Converting the image to grayscale
//        Imgproc.cvtColor(originalMat, grayMat, Imgproc.COLOR_BGR2GRAY);
//
//        if (false) {
//            Bitmap b = Bitmap.createBitmap(imageBitmap);
//            //Converting Mat back to Bitmap
//            Utils.matToBitmap(grayMat, b);
//            return b;
//        }

        Imgproc.Canny(originalMat, cannyEdges, 10, 100);

        if (false) {
            Bitmap b = Bitmap.createBitmap(imageBitmap);
            //Converting Mat back to Bitmap
            Utils.matToBitmap(cannyEdges, b);
            return b;
        }
        //finding contours: a curve joining all the continuous points (along the boundary), having same color or intensity.
        Imgproc.findContours(cannyEdges, contourList, hierarchy, Imgproc.RETR_LIST, Imgproc.CHAIN_APPROX_SIMPLE);

        //Drawing contours on a new image
        Mat contours = new Mat();
        contours.create(cannyEdges.rows(), cannyEdges.cols(), CvType.CV_8UC3);
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
                        Imgproc.drawContours(originalMat, contourList, i, new Scalar(0, 0, 0), 50);
                }
            }
        }


        Bitmap b = Bitmap.createBitmap(imageBitmap);
        //Converting Mat back to Bitmap
        Utils.matToBitmap(originalMat, b);


        return b;
    }


}
