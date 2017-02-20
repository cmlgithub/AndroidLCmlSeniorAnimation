package com.cml.androidlcmlsenioranimation.custome_package;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * author：cml on 2017/2/20
 * github：https://github.com/cmlgithub
 */

/**
 *  分析:首先二阶Bezier需要三个点来控制(起始点和控制点)
 *
 *  1.在onSizeChanged中确定起始点和控制点
 *  2.绘制
 */
public class SecondBezierView extends View {

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
    private float mFlagPointX;
    private float mFlagPointY;
    private Path mPath;



    public SecondBezierView(Context context) {
        super(context);
    }

    public SecondBezierView(Context context, AttributeSet attrs) {
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
        mPaintBezier.setStrokeWidth(5);
        mPaintLine.setColor(Color.DKGRAY);
        mPaintLine.setStyle(Paint.Style.STROKE);
    }

    public SecondBezierView(Context context, AttributeSet attrs, int defStyleAttr) {
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

        //控制点
        mFlagPointX = screenWidth / 2;
        mFlagPointY = screenHeight/2 - 300;

        //初始化绘制相应的数据
        mPath = new Path();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mPath.reset();
        mPath.moveTo(mStartPointX,mStartPointY);

        /**
         * mPath.quadTo() ; 中的坐标是绝对坐标
         * mPath.rQuadTo(); 中的坐标是相对坐标
         */
        mPath.quadTo(mFlagPointX,mFlagPointY,mEndPointX,mEndPointY);//四个参数分别为控制点的坐标X,Y.终点的坐标X,Y
        //绘制辅助点
        canvas.drawText("起点",mStartPointX,mStartPointY,mPaintText);
        canvas.drawPoint(mStartPointX,mStartPointY,mPaintPoint);
        canvas.drawText("终点",mEndPointX,mEndPointY,mPaintText);
        canvas.drawPoint(mEndPointX,mEndPointY,mPaintPoint);
        canvas.drawText("控制点",mFlagPointX,mFlagPointY,mPaintText);
        canvas.drawPoint(mFlagPointX,mFlagPointY,mPaintPoint);

        //绘制辅助线
        canvas.drawLine(mStartPointX,mStartPointY,mFlagPointX,mFlagPointY,mPaintLine);
        canvas.drawLine(mFlagPointX,mFlagPointY,mEndPointX,mEndPointY,mPaintLine);

        //绘制曲线
        canvas.drawPath(mPath,mPaintBezier);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_MOVE:
                mFlagPointX = event.getX();
                mFlagPointY = event.getY();
                invalidate();
                break;
        }
        return true;
    }
}
