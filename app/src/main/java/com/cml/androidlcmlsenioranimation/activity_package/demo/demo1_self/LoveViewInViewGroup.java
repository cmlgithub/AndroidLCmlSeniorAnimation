package com.cml.androidlcmlsenioranimation.activity_package.demo.demo1_self;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.widget.RelativeLayout;

import com.cml.androidlcmlsenioranimation.demo.demo.demo1.BezierEvaluator;

import java.util.Random;

/**
 * author：cml on 2017/2/27
 * github：https://github.com/cmlgithub
 */

public class LoveViewInViewGroup extends RelativeLayout {

    private Random mRandom = new Random();
    private int mWidth;
    private int mHeight;

    private int mLoveViewWidth = 200;
    private int mLoveViewHeight = 200;

    private Interpolator line = new LinearInterpolator();//线性
    private Interpolator acc = new AccelerateInterpolator();//加速
    private Interpolator dce = new DecelerateInterpolator();//减速
    private Interpolator accdec = new AccelerateDecelerateInterpolator();//先加速后减速
    private Interpolator[] interpolators;

    private Context mContext ;

    public LoveViewInViewGroup(Context context) {
        this(context,null);
    }

    public LoveViewInViewGroup(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public LoveViewInViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        init(context);

    }

    private void init(Context con) {
        interpolators = new Interpolator[]{line,acc,dce,accdec};
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth = getMeasuredWidth();
        mHeight = getMeasuredHeight();
    }

    public void addHeart(){
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(mLoveViewWidth, mLoveViewHeight);
        lp.addRule(CENTER_HORIZONTAL,TRUE);
        lp.addRule(ALIGN_PARENT_BOTTOM,TRUE);
        LoveView loveView = new LoveView(mContext);
        this.addView(loveView,lp);


        AnimatorSet animator = getAnimator(loveView);
        animator.start();
//        AnimatorSet animator = getAnimator(loveView);
//        animator.addListener(new AnimatorEndListener(loveView));
//        animator.start();
    }

    public AnimatorSet getAnimator(final View loveView){
        AnimatorSet set = getEnterAnimator(loveView);
        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                ValueAnimator bezierValueAnimator = getBezierValueAnimator(loveView);
                AnimatorSet animatorSet = new AnimatorSet();
                animatorSet.playSequentially(bezierValueAnimator);
                animatorSet.setInterpolator(interpolators[mRandom.nextInt(4)]);
                animatorSet.setTarget(loveView);
                animatorSet.addListener(new AnimatorEndListener(loveView));
                animatorSet.start();
            }
        });
//        ValueAnimator bezierValueAnimator = getBezierValueAnimator(loveView);
//        AnimatorSet animatorSet = new AnimatorSet();
//        animatorSet.playSequentially(set);
//        animatorSet.playSequentially(bezierValueAnimator);
//        animatorSet.setInterpolator(interpolators[mRandom.nextInt(4)]);
//        animatorSet.setTarget(loveView);
//        return animatorSet;
        return set;
    }


    private ValueAnimator getBezierValueAnimator(View target) {
        BezierEvaluator bezierEvaluator = new BezierEvaluator(getPointF(2), getPointF(1));
        ValueAnimator valueAnimator = ValueAnimator.ofObject(bezierEvaluator, new PointF((mWidth - mLoveViewWidth) / 2, mHeight - mLoveViewHeight), new PointF(mRandom.nextInt(getWidth()), 0));
        valueAnimator.addUpdateListener(new BezierListener(target));
        valueAnimator.setTarget(target);
        valueAnimator.setDuration(3000);
        return valueAnimator;
    }

    private PointF getPointF(int scale) {
        PointF pointF = new PointF();
        pointF.x = mRandom.nextInt(mWidth - 100);
        pointF.y = (mRandom.nextInt(mHeight - 100))/scale;
        return pointF;
    }

    private AnimatorSet getEnterAnimator(View target){
        ObjectAnimator alpha = ObjectAnimator.ofFloat(target, View.ALPHA, 0.2f, 1f);
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(target, View.SCALE_X, 0.2f, 1f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(target, View.SCALE_Y, 0.2f, 1f);
        AnimatorSet enter = new AnimatorSet();
        enter.setDuration(500);
        enter.setInterpolator(new LinearInterpolator());
        enter.playTogether(alpha,scaleX,scaleY);
        enter.setTarget(target);

        return enter;
    }

    private class ScaleListener extends AnimatorListenerAdapter{
        @Override
        public void onAnimationEnd(Animator animation) {

        }
    }

    private class BezierListener implements ValueAnimator.AnimatorUpdateListener{

        private View view;
        public BezierListener(View target){
            this.view = target;
        }

        @Override
        public void onAnimationUpdate(ValueAnimator animation) {
            PointF pointF = (PointF) animation.getAnimatedValue();
            view.setX(pointF.x);
            view.setY(pointF.y);
            view.setAlpha(1- animation.getAnimatedFraction());
        }
    }

    private class AnimatorEndListener extends AnimatorListenerAdapter {

        private View view;

        private AnimatorEndListener(View target){
            view = target;
        }

        @Override
        public void onAnimationEnd(Animator animation) {
            removeView(view);
        }

    }
}
