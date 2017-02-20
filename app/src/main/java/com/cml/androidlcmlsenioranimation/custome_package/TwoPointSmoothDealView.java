package com.cml.androidlcmlsenioranimation.custome_package;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * author：cml on 2017/2/20
 * github：https://github.com/cmlgithub
 *
 * 利用绘图板来实现两点间的圆滑处理的效果演示
 *      原理:
 *
 */

public class TwoPointSmoothDealView extends View {

    private Paint mPaint;
    private Path mPath;
    private float mX;
    private float mY;


    public TwoPointSmoothDealView(Context context) {
        super(context);
    }

    public TwoPointSmoothDealView(Context context, AttributeSet attrs) {
        super(context, attrs);

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);//抗锯齿
        mPaint.setStrokeWidth(8);
        mPaint.setStyle(Paint.Style.STROKE);//实心线

        mPath = new Path();
    }

    public TwoPointSmoothDealView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                mPath.reset();
                mX = event.getX();
                mY = event.getY();
                mPath.moveTo(mX,mY);
                break;
            case MotionEvent.ACTION_MOVE:
                float x1 = event.getX();
                float y1 = event.getY();

                float cx = (x1 + mX)/2;
                float cY = (y1 + mY)/2;

//                mPath.lineTo(x1,y1);
                mPath.quadTo(mX,mY,cx,cY);

                mX = x1;
                mY = y1;
                break;
        }
        invalidate();
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawPath(mPath,mPaint);
    }
}
