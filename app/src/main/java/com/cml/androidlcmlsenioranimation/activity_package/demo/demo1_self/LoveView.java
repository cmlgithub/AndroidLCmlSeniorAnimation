package com.cml.androidlcmlsenioranimation.activity_package.demo.demo1_self;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

import com.cml.androidlcmlsenioranimation.R;

/**
 * author：cml on 2017/2/23
 * github：https://github.com/cmlgithub
 *
 *
 *       

 心形自定义View
 http://blog.csdn.net/zhoudailiang/article/details/46431685
 心形公式
 blog.csdn.net/decting/article/details/8580634
 */

public class LoveView extends View {


    private Paint mInnerPaint;
    private Paint mOuterPaint;

    private int innerColor;
    private int outerColor;

    private int borderWidth;
    private Path mPath;
    private int screenWidthHalf;
    private int screenHeightHalf;

    private float rate = 5; // 半径变化率
    private AnimThread at;

    public LoveView(Context context) {
        this(context,null);
    }

    public LoveView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public LoveView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        // get attr
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.LoveView);
        innerColor = typedArray.getColor(R.styleable.LoveView_innerColor, Color.RED);
        outerColor = typedArray.getColor(R.styleable.LoveView_outerColor, Color.WHITE);
        borderWidth = typedArray.getInt(R.styleable.LoveView_borderWidth, 2);
        if(borderWidth < 4){
            borderWidth = 4;
        }

        init();

    }

    private void init() {

        at = new AnimThread();

        mInnerPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mInnerPaint.setStyle(Paint.Style.FILL);
        mInnerPaint.setStrokeWidth(borderWidth-2);
        mInnerPaint.setColor(innerColor);


        mOuterPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mOuterPaint.setStyle(Paint.Style.FILL);
        mOuterPaint.setStrokeWidth(borderWidth);
        mOuterPaint.setColor(outerColor);

        mPath = new Path();

        at.start();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mPath.reset();

        screenWidthHalf = getMeasuredWidth() / 2;
        screenHeightHalf = getMeasuredHeight() / 2;

        mPath.moveTo(screenWidthHalf, screenHeightHalf - 5 * rate);

        // 根据心形函数画图
        for (double i = 0; i <= 2 * Math.PI; i += 0.001) {
            float x = (float) (16 * Math.sin(i) * Math.sin(i) * Math.sin(i));
            float y = (float) (13 * Math.cos(i) - 5 * Math.cos(2 * i) - 2 * Math.cos(3 * i) - Math.cos(4 * i));
            x *= rate;
            y *= rate;
            x = screenWidthHalf - x;
            y = screenHeightHalf - y;
            mPath.lineTo(x, y);
        }

        canvas.drawPath(mPath, mOuterPaint);
        canvas.drawPath(mPath, mInnerPaint);
    }

    private class AnimThread extends Thread {
        public void run() {
            while (true) {
                rate += 0.05;
                if (rate > 20) { // 我的手机大于20后就很大了，为了不超过屏幕
                    rate = 5;
                }
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                // 刷新画布
                postInvalidate();
            }
        }
    };
}
