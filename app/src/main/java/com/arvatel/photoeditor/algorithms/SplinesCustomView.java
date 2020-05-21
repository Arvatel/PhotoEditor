package com.arvatel.photoeditor.algorithms;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class SplinesCustomView extends View {
    Paint paint;
    List<Point> points;
    private boolean makeStraitLine = false;
    private boolean makeCubicSpline = false;

    public SplinesCustomView(Context context) {
        super(context);
        init(null);//in case i make instance in the fragment
        //the others in case it's included into a layout file they will be called
        //so not to duplicate code
    }

    public SplinesCustomView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);

    }

    public SplinesCustomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);

    }

    public SplinesCustomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs);
    }


    public void init(@Nullable AttributeSet attributeSet) {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG/*not pixelated*/);//to set the color and styling

        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeWidth(5f);
        paint.setColor(Color.WHITE);
        points = new ArrayList<>();


    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (makeStraitLine) {
            if (!points.isEmpty() && points.size() >1) {
                points.sort(Comparator.comparing(Point::getX));
                //make the equations and draw the line
                for (int i = 1; i < points.size(); i++) {
                    points.get(i).calculate(points.get(i - 1));
                    for (float j = points.get(i - 1).x; j <= points.get(i).x; j += .01) {
                        canvas.drawPoint(j, points.get(i).func(j), paint);
                    }
                }
            }
            makeStraitLine = false;
        }
        if (makeCubicSpline) {
            if (!points.isEmpty() && points.size() > 2) {
                points.sort(Comparator.comparing(Point::getX));

                //calculate the k0
                points.get(0).calculateK0(points.get(1));
                //calculate the ki
                for (int i = 1; i < points.size() - 1; i++) {
                    points.get(i).calculateK(points.get(i + 1), points.get(i - 1));
                }
                //calculate the kn
                points.get(points.size() - 1).calculateKn(points.get(points.size() - 2));

                //make the equations and draw the curve
                for (int i = 1; i < points.size(); i++) {
                    for (float j = points.get(i - 1).x; j <= points.get(i).x; j += .01) {
                        canvas.drawPoint(j, points.get(i).cubicFunc(points.get(i - 1), j), paint);
                    }
                }

            }
            makeCubicSpline = false;
        }
//        Paint cirlcles = new Paint();
//        cirlcles.setColor(Color.RED)
        for (Point p : points) {
            canvas.drawCircle(p.x, p.y,10, paint);
            System.out.println(p.toString());
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean value = super.onTouchEvent(event);
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            points.add(new Point(event.getX(), event.getY()));
            postInvalidate();
            return true;
        }
        return value;
    }

    public void callOnDraw(boolean drawCubicLine, boolean drawLine) {
        makeCubicSpline = drawCubicLine;
        makeStraitLine = drawLine;
        postInvalidate();

    }

    public void clean() {
        points.clear();
        postInvalidate();
    }
}

class Point {
    float x, y;
    private float xCoefficient, free;//for strait line
    private float k, b, a;//for cubic spline

    Point(Float x, Float y) {
        this.x = x;
        this.y = y;
    }

    float getX() {
        return x;
    }

    //region strait line calculations
    //https://www.math.uh.edu/~jingqiu/math4364/spline.pdf
    //cacluates the equation values y = xCoefficient*x - free
    void calculate(Point prev) {
        float b = x - prev.x;
        float a = prev.x - x;
        xCoefficient = (b * prev.y + a * y) / (b * a);
        free = (prev.y * b * x + a * y * prev.x) / (b * a);
    }

    //returns y value
    float func(float x) {
        return xCoefficient * x - free;
    }
    //endregion

    //region cubic spline calculations
    //https://en.wikipedia.org/wiki/Spline_interpolation#math_9
    void calculateK(Point iPlus1, Point iMinus1) {
        k = (float) 3 * ((y - iMinus1.y) / ((x - iMinus1.x) * (x - iMinus1.x)) +
                (iPlus1.y - y) / ((iPlus1.x - x) * (iPlus1.x - x)));
    }

    void calculateK0(Point iPlus1) {
        k = (float) 3 * ((iPlus1.y - y) / ((iPlus1.x - x) * (iPlus1.x - x)));
    }

    void calculateKn(Point iMinus1) {
        k = (float) 3 * ((y - iMinus1.y) / ((x - iMinus1.x) * (x - iMinus1.x)));
    }

    private void calculateAB(Point prev) {
        a = prev.k * (x - prev.x) - (y - prev.y);
        b = -k * (x - prev.x) + (y - prev.y);
    }

    float cubicFunc(Point prev, float xProvided) {
        calculateAB(prev);
        float t = (xProvided - prev.x) / (x - prev.x);
        return ((float) 1 - t) * prev.y + t * y + t * ((float) 1 - t) * (((float) 1 - t) * a + t * b);
    }
    //endregion

}
