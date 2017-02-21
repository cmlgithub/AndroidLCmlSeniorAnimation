package com.cml.androidlcmlsenioranimation.custome_package;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.BounceInterpolator;

/**
 * author：cml on 2017/2/20
 * github：https://github.com/cmlgithub
 */


public class PathMorphingBezierView extends View implements View.OnClickListener {

    private Paint mPaintLine;
    private Paint mPaintText;
    private Paint mPaintBezier;
    private Paint mPaintPoint;
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

    private ValueAnimator mValueAnimator;

    public PathMorphingBezierView(Context context) {
        super(context);
    }

    public PathMorphingBezierView(Context context, AttributeSet attrs) {
        super(context, attrs);
        //初始化画笔
        mPaintBezier = new Paint(Paint.ANTI_ALIAS_FLAG);//抗锯齿
        mPaintBezier.setStrokeWidth(8);
        mPaintBezier.setStyle(Paint.Style.STROKE);//实心线

        mPaintPoint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintPoint.setStrokeWidth(20);
        mPaintPoint.setColor(Color.BLUE);
        mPaintPoint.setStyle(Paint.Style.STROKE);

        mPaintText = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintText.setTextSize(50);
        mPaintText.setColor(Color.RED);
        mPaintText.setStyle(Paint.Style.STROKE);

        mPaintLine = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintLine.setStrokeWidth(5);
        mPaintLine.setColor(Color.DKGRAY);
        mPaintLine.setStyle(Paint.Style.STROKE);
    }

    public PathMorphingBezierView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onSizeChanged(int screenWidth, int screenHeight, int oldw, int oldh) {
        super.onSizeChanged(screenWidth, screenHeight, oldw, oldh);

        //起点
        mStartPointX = screenWidth/4;//屏幕宽度的1/4
        mStartPointY = screenHeight/2 - 200;

        //终点
        mEndPointX = screenWidth * 3 / 4;//屏幕宽度的3/4
        mEndPointY = screenHeight/2 - 200;

        //控制点1
        mFlagPointOneX = mStartPointX;
        mFlagPointOneY = mStartPointY;

        //控制点2
        mFlagPointTwoX = mEndPointX;
        mFlagPointTwoY = mEndPointY;

        //初始化绘制相应的数据
        mPath = new Path();

        mValueAnimator = ValueAnimator.ofFloat(mStartPointY,screenHeight);
        mValueAnimator.setInterpolator(new BounceInterpolator());
        mValueAnimator.setDuration(3000);
        mValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mFlagPointOneY = (float) animation.getAnimatedValue();
                mFlagPointTwoY = (float) animation.getAnimatedValue();
                invalidate();
            }
        });

        setOnClickListener(this);

//        mValueAnimator.start();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mPath.reset();
        mPath.moveTo(mStartPointX,mStartPointY);

        /**
         * mPath.cubicTo() ; 中的坐标是绝对坐标
         * mPath.cubicTo(); 中的坐标是相对坐标
         */
        mPath.cubicTo(mFlagPointOneX, mFlagPointOneY,mFlagPointTwoX,mFlagPointTwoY,mEndPointX,mEndPointY);//四个参数分别为控制点的坐标X,Y.终点的坐标X,Y
        //绘制辅助点
        canvas.drawText("起点",mStartPointX,mStartPointY,mPaintText);
        canvas.drawPoint(mStartPointX,mStartPointY,mPaintPoint);
        canvas.drawText("终点",mEndPointX,mEndPointY,mPaintText);
        canvas.drawPoint(mEndPointX,mEndPointY,mPaintPoint);
        canvas.drawText("控制点1", mFlagPointOneX, mFlagPointOneY,mPaintText);
        canvas.drawPoint(mFlagPointOneX, mFlagPointOneY,mPaintPoint);
        canvas.drawText("控制点2", mFlagPointTwoX, mFlagPointTwoY,mPaintText);
        canvas.drawPoint(mFlagPointTwoX, mFlagPointTwoY,mPaintPoint);

        //绘制辅助线
        canvas.drawLine(mStartPointX,mStartPointY, mFlagPointOneX, mFlagPointOneY,mPaintLine);
        canvas.drawLine(mFlagPointOneX, mFlagPointOneY, mFlagPointTwoX, mFlagPointTwoY,mPaintLine);
        canvas.drawLine(mFlagPointTwoX, mFlagPointTwoY,mEndPointX,mEndPointY,mPaintLine);

        //绘制曲线
        canvas.drawPath(mPath,mPaintBezier);
    }

    @Override
    public void onClick(View v) {
        mValueAnimator.start();
    }
}
