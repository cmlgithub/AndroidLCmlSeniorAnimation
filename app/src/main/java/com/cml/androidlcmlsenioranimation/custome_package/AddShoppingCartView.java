package com.cml.androidlcmlsenioranimation.custome_package;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

import com.cml.androidlcmlsenioranimation.BezierEvalutor;

/**
 * author：cml on 2017/2/21
 * github：https://github.com/cmlgithub
 */

/**
 *      加入购物车动画轨迹的效果
 *
 *      Android API 并没有提供获取Bezier曲线上任意一个点的坐标的方法,所以需要自己实现,借助BezierUtil
 */
public class AddShoppingCartView extends View implements View.OnClickListener {

    private int mStartPointX;
    private int mStartPointY;

    private int mEndPointX;
    private int mEndPointY;

    private int mFlagPointX;
    private int mFlagPointY;

    private int mMovePointX;
    private int mMovePointY;

    private Path mPath;
    private Paint mPaintPath;
    private Paint mPaintCircle;

    public AddShoppingCartView(Context context) {
        super(context);
    }

    public AddShoppingCartView(Context context, AttributeSet attrs) {
        super(context, attrs);

        mStartPointX = 100;
        mStartPointY = 100;

        mMovePointX = mStartPointX;
        mMovePointY = mStartPointY;

        mEndPointX = 600;
        mEndPointY = 600;

        mFlagPointX = 500;
        mFlagPointY = 0;

        mPath = new Path();

        mPaintPath = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintPath.setStyle(Paint.Style.STROKE);
        mPaintPath.setStrokeWidth(8);

        mPaintCircle = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintCircle.setColor(Color.GREEN);

        setOnClickListener(this);
    }

    public AddShoppingCartView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawCircle(mStartPointX,mStartPointY,20,mPaintCircle);//绘制物品起点
        canvas.drawCircle(mEndPointX,mEndPointY,20,mPaintCircle);//绘制物品终点
        canvas.drawCircle(mMovePointX,mMovePointY,20,mPaintCircle);//绘制物品移动的点

        mPath.reset();
        mPath.moveTo(mStartPointX,mStartPointY);

        mPath.quadTo(mFlagPointX,mFlagPointY,mEndPointX,mEndPointY);

        canvas.drawPath(mPath,mPaintPath);
    }

    @Override
    public void onClick(View v) {
        BezierEvalutor evalutor = new BezierEvalutor(new PointF(mFlagPointX, mFlagPointY));
        final ValueAnimator valueAnimator = ValueAnimator.ofObject(evalutor, new PointF(mStartPointX, mStartPointY), new PointF(mEndPointX, mEndPointY));
        valueAnimator.setDuration(600);
        valueAnimator.setInterpolator(new AccelerateDecelerateInterpolator());//加速插值器
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                PointF pointF = (PointF) animation.getAnimatedValue();
                mMovePointX = (int) pointF.x;
                mMovePointY = (int) pointF.y;
                invalidate();
            }
        });

        valueAnimator.start();
    }
}
