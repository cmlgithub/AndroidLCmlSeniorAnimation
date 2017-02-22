package com.cml.androidlcmlsenioranimation.custome_package;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.cml.androidlcmlsenioranimation.BezierEvalutor;

/**
 * author：cml on 2017/2/22
 * github：https://github.com/cmlgithub
 */

public class LoveRandomView extends View implements Animator.AnimatorListener, View.OnClickListener {


    private Paint mPaintBezierDismiss;
    private Path mPathBezierSave;
    private Paint mPaintBezier;
    private Path mPathBezier;
    private Paint mPaintBroderDismiss;
    private Path mPathDismiss;
    private Paint mPaintBroder;
    private int screenWidth;
    private int screenHeight;
    private Paint mPaintCircle;
    private ValueAnimator mValueAnimator;
    private float flag;
    private Path mPath;
    private int  isDrawCircle  = 0;//0:画圆 ,1:画线 ,2:移动
    private PathMeasure mPathMeasure;

    private float[] pos = new float[2];
    private float[] tan = new float[2];
    private int mStartPointX;
    private int mStartPointY;
    private int mEndPointX;
    private int mEndPointY;
    private int mMovePointX;
    private int mMovePointY;
    private int mFlagPointX;
    private int mFlagPointY;
    private ValueAnimator mBezierValueAnimator;


    public LoveRandomView(Context context) {
        super(context);
    }

    public LoveRandomView(Context context, AttributeSet attrs) {
        super(context, attrs);

        mPaintCircle = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintCircle.setStrokeWidth(5);
        mPaintCircle.setColor(Color.RED);
        mPaintCircle.setStyle(Paint.Style.FILL_AND_STROKE);

        mPaintBroder = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintBroder.setStrokeWidth(5);
        mPaintBroder.setColor(Color.BLACK);
        mPaintBroder.setStyle(Paint.Style.FILL_AND_STROKE);

        mPaintBezier = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintBezier.setStrokeWidth(5);
        mPaintBezier.setColor(Color.BLACK);
        mPaintBezier.setStyle(Paint.Style.STROKE);

        mPaintBezierDismiss = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintBezierDismiss.setStrokeWidth(5);
        mPaintBezierDismiss.setColor(Color.WHITE);
        mPaintBezierDismiss.setStyle(Paint.Style.STROKE);

        mPaintBroderDismiss = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintBroderDismiss.setStrokeWidth(5);
        mPaintBroderDismiss.setColor(Color.WHITE);
        mPaintBroderDismiss.setStyle(Paint.Style.FILL_AND_STROKE);


        mValueAnimator = new ValueAnimator().ofFloat(0, 1);
        mValueAnimator.setInterpolator(new LinearInterpolator());
        mValueAnimator.setDuration(1000);
        mValueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        mValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                flag = (float) animation.getAnimatedValue();
                invalidate();
            }
        });
        mValueAnimator.addListener(this);
        mValueAnimator.start();

        mPath = new Path();
        mPathDismiss = new Path();
        mPathBezier = new Path();
        mPathBezierSave = new Path();

        mPathMeasure = new PathMeasure();

        setOnClickListener(this);

    }

    public LoveRandomView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        screenWidth = w;
        screenHeight = h;

        mStartPointX = w/2;
        mStartPointY = h-100;

        mEndPointX = w/2;
        mEndPointY = 100;

        mFlagPointX = 0;
        mFlagPointY = screenHeight /2;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if(isDrawCircle == 0){
            drawLoveWithFlag(canvas);//图形逐渐放大的效果
        }else if(isDrawCircle == 1){

            drawLove(canvas);//放大后的最终图形保持不动在底部

            drawStraightLineWithFlag(canvas);//画出要移动的直线轨迹

//            drawBezier();//得到要移动的Bezier曲线
//            drawBezierWithFlag(canvas);//画出要移动的Bezier曲线轨迹


        }else if(isDrawCircle == 2){

            drawStraightLine(canvas);//画出要移动的直线的轨迹

            drawDismissStraightLineWithFlag(canvas);//通过逐渐画白色的线来覆盖已经存在的直线轨迹

            moveCircleWithFlag(canvas);//移动图形

        }else if(isDrawCircle == 3){

            canvas.drawCircle(mMovePointX,mMovePointY,55, mPaintBroder);
            canvas.drawCircle(mMovePointX,mMovePointY,50, mPaintCircle);

            mPathBezier.reset();
            mPathBezier.moveTo(screenWidth/2,100);
            mPathBezier.quadTo(0,screenHeight /2,screenWidth/2,screenHeight - 100);

            mPathBezierSave.reset();
            mPathBezierSave.moveTo(screenWidth/2,100);
            mPathMeasure.setPath(mPathBezier,false);
            mPathMeasure.getSegment(0,mPathMeasure.getLength()*flag,mPathBezierSave,false);



            mPath.reset();
            mPath.moveTo(mStartPointX,mStartPointY);

            mPath.quadTo(mFlagPointX,mFlagPointY,mEndPointX,mEndPointY);

            canvas.drawPath(mPath,mPaintBezier);

            canvas.drawPath(mPathBezierSave, mPaintBezierDismiss);
        }else if(isDrawCircle == 4){
            mPathBezier.reset();
            mPathBezier.moveTo(screenWidth/2,100);
            mPathBezier.quadTo(0,screenHeight /2,screenWidth/2,screenHeight - 100);

            mPathBezierSave.reset();
            mPathBezierSave.moveTo(screenWidth/2,100);
            mPathMeasure.setPath(mPathBezier,false);
            mPathMeasure.getSegment(0,mPathMeasure.getLength()*flag,mPathBezierSave,false);
            canvas.drawPath(mPathBezierSave, mPaintBezier);

            canvas.drawCircle(screenWidth/2,100,55, mPaintBroder);
            canvas.drawCircle(screenWidth/2,100,50, mPaintCircle);

//            drawBezier();//得到要移动的Bezier曲线
//            drawBezierWithFlag(canvas);//画出要移动的Bezier曲线轨迹
        } else if(isDrawCircle == 5){
            canvas.drawCircle(screenWidth/2,screenHeight-100,(1-flag)*55, mPaintBroder);
            canvas.drawCircle(screenWidth/2,screenHeight-100,(1-flag)*50, mPaintCircle);
        }

    }

    private void moveCircleWithFlag(Canvas canvas) {
        canvas.drawCircle(screenWidth/2,screenHeight - (flag *(screenHeight-200)+100),55, mPaintBroder);
        canvas.drawCircle(screenWidth/2,screenHeight - (flag *(screenHeight-200)+100),50, mPaintCircle);
    }

    private void drawDismissStraightLineWithFlag(Canvas canvas) {
        mPathDismiss.reset();
        mPathDismiss.moveTo(screenWidth/2,screenHeight-100);
        mPathDismiss.lineTo(screenWidth/2,screenHeight - (flag *(screenHeight-200)+100));
        canvas.drawPath(mPathDismiss,mPaintBroderDismiss);
    }

    private void drawStraightLine(Canvas canvas) {
        mPath.moveTo(screenWidth/2,screenHeight-100);
        mPath.lineTo(screenWidth/2,screenHeight - (1 *(screenHeight-200)+100));
        canvas.drawPath(mPath,mPaintBroder);
    }

    private void drawBezierWithFlag(Canvas canvas) {
        mPathBezierSave.reset();
        mPathBezierSave.moveTo(screenWidth/2,screenHeight-100);
        mPathMeasure.setPath(mPathBezier,false);
        mPathMeasure.getSegment(0,mPathMeasure.getLength()*flag,mPathBezierSave,false);
        canvas.drawPath(mPathBezierSave, mPaintBezier);
    }

    private void drawBezier() {
        mPathBezier.reset();
        mPathBezier.moveTo(screenWidth/2,screenHeight-100);
        mPathBezier.quadTo(0,screenHeight /2,screenWidth/2,screenHeight - (1 *(screenHeight-200)+100));
    }

    private void drawStraightLineWithFlag(Canvas canvas) {
        mPath.reset();
        mPath.moveTo(screenWidth/2,screenHeight-100);
        mPath.lineTo(screenWidth/2,screenHeight - (flag *(screenHeight-200)+100));
        canvas.drawPath(mPath,mPaintBroder);
    }

    private void drawLove(Canvas canvas) {
        canvas.drawCircle(screenWidth/2,screenHeight-100,55, mPaintBroder);
        canvas.drawCircle(screenWidth/2,screenHeight-100,50, mPaintCircle);
    }

    private void drawLoveWithFlag(Canvas canvas) {
        canvas.drawCircle(screenWidth/2,screenHeight-100,flag*55, mPaintBroder);
        canvas.drawCircle(screenWidth/2,screenHeight-100,flag*50, mPaintCircle);
    }

    @Override
    public void onAnimationStart(Animator animation) {
    }

    @Override
    public void onAnimationEnd(Animator animation) {

    }

    @Override
    public void onAnimationCancel(Animator animation) {

    }

    @Override
    public void onAnimationRepeat(Animator animation) {
        if(isDrawCircle == 0){
            isDrawCircle = 1;
        }else if(isDrawCircle == 1){
            isDrawCircle = 2;
        }else if(isDrawCircle == 4){
            isDrawCircle = 3;
            BezierEvalutor evalutor = new BezierEvalutor(new PointF(mFlagPointX, mFlagPointY));
            mBezierValueAnimator = ValueAnimator.ofObject(evalutor, new PointF(mStartPointX, 100), new PointF(mEndPointX, screenHeight-100));
            mBezierValueAnimator.setDuration(1000);
            mBezierValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    PointF pointF = (PointF) animation.getAnimatedValue();
                    mMovePointX = (int) pointF.x;
                    mMovePointY = (int) pointF.y;
//                    invalidate();
                }
            });
            mBezierValueAnimator.start();
        } else if(isDrawCircle == 3){
            isDrawCircle = 5;
        } else if(isDrawCircle == 2){
            isDrawCircle = 4;
        } else if(isDrawCircle == 5){
            isDrawCircle = 0;
        }
    }

    @Override
    public void onClick(View v) {
    }
}
