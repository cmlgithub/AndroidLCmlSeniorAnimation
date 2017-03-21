package com.cml.androidlcmlsenioranimation.activity_package.demo.demo3_self;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

/**
 * author：cml on 2017/3/21
 * github：https://github.com/cmlgithub
 */

public class TwoBallView extends View {

    private Ball mOneBall;
    private final static int DEFAULT_MAX_RADIUS = 75;
    private final static int DEFAULT_MIN_RADIUS = 25;
    private final static int DEFAULT_DISTANCE = 100;
    private final static int DEFAULT_ANIMATOR_DURATION = 1000;
    private final static int DEFAULT_ONE_BALL_COLOR = Color.parseColor("#40df73");
    private final static int DEFAULT_TWO_BALL_COLOR = Color.parseColor("#ffdf3e");
    private float maxRadius = DEFAULT_MAX_RADIUS;
    private float minRadius = DEFAULT_MIN_RADIUS;
    private int distance = DEFAULT_DISTANCE;

    private float mCenterX;
    private float mCenterY;
    private Paint mPaint;
    private AnimatorSet animatorSet;
    private Ball mTwoBall;

    public TwoBallView(Context context) {
        this(context,null);
    }

    public TwoBallView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public TwoBallView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init();
    }

    private void init() {
        mOneBall = new Ball();
        mOneBall.setColors(DEFAULT_ONE_BALL_COLOR);
        mTwoBall = new Ball();
        mTwoBall.setColors(DEFAULT_TWO_BALL_COLOR);

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

        configAnimator();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mCenterX = w / 2;
        mCenterY = h / 2;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mCenterX = getWidth() / 2;
        mCenterY = getHeight() / 2;
    }

    private void configAnimator() {
        float centerRadius = (maxRadius + minRadius) * 0.5f;
        ObjectAnimator oneScaleAnimator = ObjectAnimator.ofFloat(mOneBall,"radius",centerRadius,maxRadius,centerRadius,minRadius,centerRadius);
        oneScaleAnimator.setRepeatCount(ValueAnimator.INFINITE);
        ValueAnimator oneCenterAnimator = ValueAnimator.ofFloat(-1, 0, 1, 0, -1);
        oneCenterAnimator.setRepeatCount(ValueAnimator.INFINITE);
        oneCenterAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (Float) animation.getAnimatedValue();
                float x = mCenterX + (distance) * value;
                mOneBall.setCenters(x);
                invalidate();
            }
        });

        ObjectAnimator twoScaleAnimator = ObjectAnimator.ofFloat(mTwoBall, "radius", centerRadius, minRadius,
                centerRadius, maxRadius, centerRadius);
        twoScaleAnimator.setRepeatCount(ValueAnimator.INFINITE);
        // 第二个小球位移动画
        ValueAnimator twoCenterAnimator = ValueAnimator.ofFloat(1, 0, -1, 0, 1);
        twoCenterAnimator.setRepeatCount(ValueAnimator.INFINITE);
        twoCenterAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (Float) animation.getAnimatedValue();
                float x = mCenterX + (distance) * value;
                mTwoBall.setCenters(x);
            }
        });

        // 属性动画集合
        animatorSet = new AnimatorSet();
        // 四个属性动画一块执行
        animatorSet.playTogether(oneScaleAnimator, oneCenterAnimator,twoScaleAnimator,twoCenterAnimator);
        // 动画一次运行时间
        animatorSet.setDuration(DEFAULT_ANIMATOR_DURATION);
        // 时间插值器，这里表示动画开始最快，结尾最慢
        animatorSet.setInterpolator(new DecelerateInterpolator());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mOneBall.getRadius() > mTwoBall.getRadius()) {
            mPaint.setColor(mTwoBall.getColors());
            canvas.drawCircle(mTwoBall.getCenters(), mCenterY, mTwoBall.getRadius(), mPaint);
            mPaint.setColor(mOneBall.getColors());
            canvas.drawCircle(mOneBall.getCenters(), mCenterY, mOneBall.getRadius(), mPaint);
        } else {
            mPaint.setColor(mOneBall.getColors());
            canvas.drawCircle(mOneBall.getCenters(), mCenterY, mOneBall.getRadius(), mPaint);
            mPaint.setColor(mTwoBall.getColors());
            canvas.drawCircle(mTwoBall.getCenters(), mCenterY, mTwoBall.getRadius(), mPaint);
        }
    }

    class Ball {
        private int colors;
        private float centers;
        private float radius;

        public void setColors(int colors) {
            this.colors = colors;
        }

        public void setCenters(float centers) {
            this.centers = centers;
        }

        public void setRadius(float radius) {
            this.radius = radius;
        }

        public int getColors() {
            return colors;
        }

        public float getCenters() {
            return centers;
        }

        public float getRadius() {
            return radius;
        }
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        startAnimator();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        stopAnimator();
    }

    /**
     * 开始动画
     */
    public void startAnimator() {
        if (getVisibility() != VISIBLE) return;

        if (animatorSet.isRunning()) return;

        if (animatorSet != null) {
            animatorSet.start();
        }
    }

    /**
     * 结束停止动画
     */
    public void stopAnimator() {
        if (animatorSet != null) {
            animatorSet.end();
        }
    }
}
