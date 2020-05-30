package com.arvatel.photoeditor.algorithms;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;

import com.arvatel.photoeditor.R;

import org.opencv.android.Utils;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class OpenCvUtil {

    private static CascadeClassifier initializeOpenCVDependencies(Context context) {
        CascadeClassifier cascadeClassifier = new CascadeClassifier();

        try {
            // Copy the resource into a temp file so OpenCV can load it
            InputStream is = context.getResources().openRawResource(R.raw.haarcascade_frontalface_alt);
            File cascadeDir = context.getDir("cascade", Context.MODE_PRIVATE);
            File mCascadeFile = new File(cascadeDir, "lbpcascade_frontalface.xml");
            FileOutputStream os = new FileOutputStream(mCascadeFile);


            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = is.read(buffer)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
            is.close();
            os.close();

            // Load the cascade classifier
            cascadeClassifier = new CascadeClassifier(mCascadeFile.getAbsolutePath());
        } catch (Exception e) {
            Log.e("OpenCVActivity", "Error loading cascade", e);
        }

        return cascadeClassifier;
    }

    public static Bitmap searchForFaces(Bitmap imageBitmap, Context context) {
        // Face detector creation by loading source cascade xml file
        // using CascadeClassifier.
        // the file:
        // https://github.com/opencv/opencv/blob/master/data/haarcascades/
        // haarcascade_frontalface_alt.xml
        CascadeClassifier faceDetector  = initializeOpenCVDependencies(context);


        // Input image
        Mat image = new Mat();
        Utils.bitmapToMat(imageBitmap, image);

        // Detecting faces
        MatOfRect faceDetections = new MatOfRect();
        faceDetector.detectMultiScale(image, faceDetections);

        // Creating a rectangular box showing faces detected
        for (Rect rect : faceDetections.toArray()) {
            Imgproc.rectangle(image, new Point(rect.x, rect.y),
                    new Point(rect.x + rect.width, rect.y + rect.height),
                    new Scalar(0, 255, 0));
        }

        Bitmap b = Bitmap.createBitmap(imageBitmap);
        //Converting Mat back to Bitmap
        Utils.matToBitmap(image, b);
        return b;

    }
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
                        Imgproc.drawContours(originalMat, contourList, i, new Scalar(0, 0, 0), 10);
                }
            }
        }


        Bitmap b = Bitmap.createBitmap(imageBitmap);
        //Converting Mat back to Bitmap
        Utils.matToBitmap(originalMat, b);
        return b;
    }


}
