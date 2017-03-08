package com.cml.androidlcmlsenioranimation.activity_package.demo.demo2_self;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;

/**
 * author：cml on 2017/3/8
 * github：https://github.com/cmlgithub
 */

public class CircleBounceView extends View {

    private Paint mCirclePaint ;
    private int measuredHeight;
    private int measuredWidth;
    private float totalDistance = 200;
    private float totalOvalDistance = 3;
    private float totalOvalLengthDistance = 30;
    private float flagDistance ;
    private ValueAnimator mValueAnimator;
    private Paint mOvalPaint;
    private float flagOvalDistance;
    private float flagOvalLengthDistance;


    public CircleBounceView(Context context) {
        this(context,null);
    }

    public CircleBounceView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CircleBounceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init();
    }

    private void init() {
        mCirclePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mCirclePaint.setStyle(Paint.Style.FILL);
        mCirclePaint.setColor(Color.RED);

        mOvalPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mOvalPaint.setStyle(Paint.Style.FILL);
        mOvalPaint.setColor(Color.DKGRAY);

        mValueAnimator = ValueAnimator.ofFloat(0, 1);
        setValueAnimatorAttr();
    }
    private boolean flag ;
    private void setValueAnimatorAttr(){
        mValueAnimator.setDuration(500);
//        mValueAnimator.setInterpolator(new LinearInterpolator());
        mValueAnimator.setInterpolator(new DecelerateInterpolator());
        mValueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        mValueAnimator.addListener(new AnimatorListenerAdapter() {

            @Override
            public void onAnimationRepeat(Animator animation) {
                super.onAnimationRepeat(animation);
                flag = !flag;
                if(flag){
                    mValueAnimator.setInterpolator(new AccelerateInterpolator());
                }else {
                    mValueAnimator.setInterpolator(new DecelerateInterpolator());
                }
            }

        });
        mValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float animatedFraction = animation.getAnimatedFraction();
                if(flag){
                    flagDistance = totalDistance - (animatedFraction * totalDistance);
                    flagOvalDistance = animatedFraction * totalOvalDistance;
                    flagOvalLengthDistance = animatedFraction * totalOvalLengthDistance;
                }else {
                    flagDistance = animatedFraction * totalDistance;
                }

                invalidate();
            }
        });
        mValueAnimator.start();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        measuredHeight = getMeasuredHeight();
        measuredWidth = getMeasuredWidth();

        canvas.drawCircle(measuredWidth/2,measuredHeight/2 - flagDistance,50,mCirclePaint);
        if(flag){
            canvas.drawOval(measuredWidth/2 - flagOvalLengthDistance,measuredHeight/2 +totalOvalDistance- flagOvalDistance+50,measuredWidth/2 + flagOvalLengthDistance,measuredHeight/2 +totalOvalDistance + totalOvalDistance+ flagOvalDistance+50,mOvalPaint);
        }

    }

    public void destory(){
        mValueAnimator.pause();
    }

}
