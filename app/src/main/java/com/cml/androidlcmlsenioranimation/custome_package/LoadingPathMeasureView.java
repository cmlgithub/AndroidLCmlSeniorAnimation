package com.cml.androidlcmlsenioranimation.custome_package;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

/**
 * author：cml on 2017/2/21
 * github：https://github.com/cmlgithub
 */

public class LoadingPathMeasureView extends View {


    private float mLength;
    private Paint mPaint;
    private Path mPath;
    private Path mDst;
    private PathMeasure mPathMeasure;
    private ValueAnimator mValueAnimator;
    private float mAnimValue;

    public LoadingPathMeasureView(Context context) {
        super(context);
    }

    public LoadingPathMeasureView(Context context, AttributeSet attrs) {
        super(context, attrs);

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(5);

        mPath = new Path();
        mDst = new Path();

        mPath.addCircle(400,400,100, Path.Direction.CW);

        mPathMeasure = new PathMeasure();
        mPathMeasure.setPath(mPath,true);

        mLength = mPathMeasure.getLength();

        mValueAnimator = ValueAnimator.ofFloat(0, 1);
        mValueAnimator.setDuration(1000);
        mValueAnimator.setInterpolator(new LinearInterpolator());
        mValueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        mValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mAnimValue = (float) animation.getAnimatedValue();
                invalidate();
            }
        });
        mValueAnimator.start();
    }

    public LoadingPathMeasureView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mDst.reset();
        mDst.lineTo(0,0);//Android bug :  在使用硬件加速的时候 ,如果没有mDst.lineTo(0,0);,可能导致mPathMeasure.getSegment()失效
        float stop = mLength * mAnimValue;
        /**
         * 前50%-->start == 0
         * 后50 -->start 迅速的向stop靠拢
         */
        float start = (float)(stop -((0.5 - Math.abs(mAnimValue - 0.5))*mLength));

        mPathMeasure.getSegment(start,stop,mDst,true);

        canvas.drawPath(mDst,mPaint);
    }
}
