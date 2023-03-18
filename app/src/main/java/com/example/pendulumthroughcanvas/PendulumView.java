package com.example.pendulumthroughcanvas;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class PendulumView extends View {


        private Paint mPaint;
        private float mAngle;
        private float mLength;
        private float mBobX;
        private float mBobY;
        private final float mGravity = 9.81f;
        private final float mDamping = 0.1f;

        public PendulumView(Context context, AttributeSet attrs) {
            super(context, attrs);
            mPaint = new Paint();
            mPaint.setColor(Color.BLACK);
            mPaint.setStyle(Paint.Style.STROKE);
            mPaint.setStrokeWidth(5);
            mAngle = (float) Math.PI / 4;
            mLength = 200;
            mBobX = 0;
            mBobY = 0;
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            float pivotX = getWidth() / 2;
            float pivotY = getHeight() / 2;
            canvas.drawLine(pivotX, pivotY, pivotX + mBobX, pivotY + mBobY, mPaint);
            canvas.drawCircle(pivotX + mBobX, pivotY + mBobY, 20, mPaint);
        }

        public void update(float deltaTime) {
            float angularAcceleration = -(mGravity / mLength) * (float) Math.sin(mAngle);
            mAngle += deltaTime * (mDamping * angularAcceleration);
            mBobX = (float) (mLength * Math.sin(mAngle));
            mBobY = (float) (mLength * Math.cos(mAngle));
            invalidate();
        }
}
