package com.example.pendulumthroughcanvas;

import static android.opengl.ETC1.getHeight;
import static android.opengl.ETC1.getWidth;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.AttributeSet;
import android.view.View;

public class PendulumViewSensor extends View implements SensorEventListener {
    private Paint mPaint;
    private Path mPath;
    private float mAngle;
    private float mLength;
    private float mBobX;
    private float mBobY;
    private final float mGravity = 9.81f;
    private final float mDamping = 0.1f;
    private SensorManager mSensorManager;
    private Sensor mAccelerometer;

    public PendulumViewSensor(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint();
        mPath = new Path();
        mPaint.setColor(Color.RED);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setStrokeWidth(5);
        mAngle = (float) Math.PI / 4;
        mLength = 380;
        mBobX = 0;
        mBobY = 0;
        mSensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.BLACK);
        float pivotX = getWidth() / 2;
        float pivotY = getHeight() / 8;
        canvas.drawLine(pivotX, pivotY, pivotX +25+ mBobX, pivotY + mBobY, mPaint);
        //canvas.drawCircle(pivotX + mBobX, pivotY + mBobY, 40, mPaint);
        canvas.drawLine(0,600, getWidth(), 600,mPaint);
        canvas.drawLine(getWidth()/2,600, getWidth()/2, 560,mPaint);



        drawTriangle(pivotX+mBobX, pivotY+mBobY, 50, 50, true, mPaint, canvas);
    }
    private void drawTriangle(float x, float y, int width, int height, boolean inverted, Paint paint, Canvas canvas){

        Point p1 = new Point((int) x,(int) y);
        float pointX = x + width/2;
        float pointY = inverted?  y + height : y - height;

        Point p2 = new Point((int) pointX,(int) pointY);
        Point p3 = new Point((int) x+width,(int) y);


        Path path = new Path();
        path.setFillType(Path.FillType.EVEN_ODD);
        path.moveTo(p1.x,p1.y);
        path.lineTo(p2.x,p2.y);
        path.lineTo(p3.x,p3.y);
        path.close();

        canvas.drawPath(path, paint);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            float ax = event.values[0];
            float ay = event.values[1];

            mAngle = (float) Math.atan2(-ax, ay);
            mBobX = (float) (mLength * Math.sin(mAngle));
            mBobY = (float) (mLength * Math.cos(mAngle));
            invalidate();
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // not used
    }


    public void start() {
        mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_UI);
    }

    public void stop() {
    }
}
