package com.cml.androidlcmlsenioranimation.custome_package;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

/**
 * author：cml on 2017/2/20
 * github：https://github.com/cmlgithub
 */


/**
 *      分析:
 *          利用二阶Bezier曲线来绘制波形,但是需要绘制两个曲线(一个口朝上,一个口朝下),所以需要两个控制点
 *
 *      步骤:
 *          1.绘制口朝下的第一个半圆,绘制口朝上的第二个半圆
 *          2.判断屏幕能容纳多少个这样的完整的波形
 *          3.需要给波形有偏移的动画效果(向右移动)-->起点,控制点,终点的横坐标向右增加即可
 *          4.在屏幕的左外侧也需要有一个完整的波形,这样移动的时候就会移动进入屏幕
 */
public class WaveBezierView extends View implements View.OnClickListener {

    private Paint mPaintBezier;
    //起点
    private float mStartPointX;
    private float mStartPointY;
    //终点
    private float mEndPointX;
    private float mEndPointY;
    //控制点
    private float mFlagPointOneX;
    private float mFlagPointOneY;
    private float mFlagPointTwoX;
    private float mFlagPointTwoY;

    private Path mPath;

    private int mWaveLength;//波长的长度
    private int mWaveCount;
    private int mScreenHeight;
    private int mScreenWidth;
    private int mCenterScreenHeight;

    private ValueAnimator mValueAnimator;
    private int mOffset;

    public WaveBezierView(Context context) {
        super(context);
    }

    public WaveBezierView(Context context, AttributeSet attrs) {
        super(context, attrs);
        //初始化画笔
        mPaintBezier = new Paint(Paint.ANTI_ALIAS_FLAG);//抗锯齿
        mPaintBezier.setColor(Color.LTGRAY);
        mPaintBezier.setStrokeWidth(8);
        mPaintBezier.setStyle(Paint.Style.FILL_AND_STROKE);

        mWaveLength = 800;

        mValueAnimator = ValueAnimator.ofInt(0,mWaveLength);
        mValueAnimator.setDuration(1000);
        mValueAnimator.setInterpolator(new LinearInterpolator());
        mValueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        mValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mOffset = (int) animation.getAnimatedValue();
                invalidate();
            }
        });

    }

    public WaveBezierView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onSizeChanged(int screenWidth, int screenHeight, int oldw, int oldh) {
        super.onSizeChanged(screenWidth, screenHeight, oldw, oldh);
        //初始化绘制相应的数据
        mPath = new Path();

        mScreenHeight = screenHeight;
        mScreenWidth  = screenWidth;
        mCenterScreenHeight = screenHeight/2;
        mWaveCount = (int)Math.round(mScreenWidth / mWaveLength + 1.5);//四舍五入,屏幕加上屏幕左外侧一个完整的波形的个数

        setOnClickListener(this);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPath.reset();
        mPath.moveTo(-mWaveLength + mOffset,mCenterScreenHeight);//首先将起点移动到屏幕左外侧一个完整波形长度的位置的起点

        //通过循环依次绘制每个波形 -->-sin(x)(在0,0)位置开始首先是口朝上
        for (int i = 0; i < mWaveCount; i++) {
            //绘制口朝上的波形
            mPath.quadTo(-mWaveLength *3/4 + i*mWaveLength+mOffset,mCenterScreenHeight+60,-mWaveLength/2+ i*mWaveLength+mOffset,mCenterScreenHeight);
            //绘制口朝下的波形
            mPath.quadTo(-mWaveLength /4 + i*mWaveLength+mOffset,mCenterScreenHeight-60, i*mWaveLength+mOffset,mCenterScreenHeight);
        }

        //划线去填充至屏幕底部
        mPath.lineTo(mScreenWidth,mScreenHeight);
        mPath.lineTo(0,mScreenHeight);
        mPath.close();

        //绘制曲线
        canvas.drawPath(mPath,mPaintBezier);
    }

    @Override
    public void onClick(View v) {
        mValueAnimator.start();
    }
}
