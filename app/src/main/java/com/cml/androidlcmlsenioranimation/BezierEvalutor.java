package com.cml.androidlcmlsenioranimation;

import android.animation.TypeEvaluator;
import android.graphics.PointF;

/**
 * author：cml on 2017/2/21
 * github：https://github.com/cmlgithub
 */

/**
 * Bezier 计算器
 */

public class BezierEvalutor implements TypeEvaluator<PointF> {

    private PointF mFlagPointF;//控制点的坐标

    public BezierEvalutor(PointF flagPoint) {
        mFlagPointF = flagPoint;
    }

    /**
     *
     * @param fraction : 当前的完成比例
     * @param startValue :起点
     * @param endValue : 终点
     * @return : 对应在Bezier曲线上的点
     */
    @Override
    public PointF evaluate(float fraction, PointF startValue, PointF endValue) {
        return BezierUtil.CalculateBezierPointForQuadratic(fraction,startValue,mFlagPointF,endValue);
    }
}
