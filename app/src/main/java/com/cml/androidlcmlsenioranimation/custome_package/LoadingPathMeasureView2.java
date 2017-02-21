package com.cml.androidlcmlsenioranimation.custome_package;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.graphics.PathMeasure;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

/**
 * author：cml on 2017/2/21
 * github：https://github.com/cmlgithub
 */

/**
 * LoadingPathMeasureView 的另一种实现思路
 *
 *      通过画笔的风格来实现
 *
 *      绘制三角形
 */
public class LoadingPathMeasureView2 extends View {


    private float mLength;
    private Paint mPaint;
    private Path mPath;
    private PathMeasure mPathMeasure;
    private ValueAnimator mValueAnimator;
    private float mAnimValue;
    private PathEffect mEffect;

    public LoadingPathMeasureView2(Context context) {
        super(context);
    }

    public LoadingPathMeasureView2(Context context, AttributeSet attrs) {
        super(context, attrs);

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(5);

        mPath = new Path();

        mPath.moveTo(100,100);
        mPath.lineTo(100,500);
        mPath.lineTo(400,300);
        mPath.close();

        mPathMeasure = new PathMeasure();
        mPathMeasure.setPath(mPath,true);

        mLength = mPathMeasure.getLength();

        mValueAnimator = ValueAnimator.ofFloat(1, 0);
        mValueAnimator.setDuration(2000);
        mValueAnimator.setInterpolator(new LinearInterpolator());
        mValueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        mValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mAnimValue = (float) animation.getAnimatedValue();
                /**
                 *      原理:虚线和实线的交替变换
                 */
                mEffect = new DashPathEffect(new float[]{mLength,mLength},mLength*mAnimValue);
                mPaint.setPathEffect(mEffect);
                invalidate();
            }
        });
        mValueAnimator.start();
    }

    public LoadingPathMeasureView2(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawPath(mPath,mPaint);
    }
}
