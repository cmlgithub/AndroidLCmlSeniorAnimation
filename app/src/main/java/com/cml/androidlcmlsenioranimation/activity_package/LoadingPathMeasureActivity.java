package com.cml.androidlcmlsenioranimation.activity_package;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.cml.androidlcmlsenioranimation.R;

/**
 *      PathMeasure:
 *          初始化: PathMeasure pathMeasure = new PathMeasure();
 *          实用方法:
 *
 *              1.pathMeasure.setPath(Path path,boolean forceClose);
 *                  forceClose : 是否强制闭合路径
 *
 *              2.pathMeasure.getLength()
 *
 *              3.pathMeasure.getSegment(float startD,float stopD,Path dst,boolean startWithMoveTo);
 *                  截取PathMeasure的片段
 *                  startD:开始点
 *                  stopD:终止点
 *                  dst:截取的路径保存的位置
 *                  startWithMoveTo:是否从上一次截取的终点开始截取
 *
 *               4.pathMeasure.getPosTan(float distance,float[] pos,float tan);
 *                  用于获取路径上某点的坐标及其切线的坐标
 *
 *                  (Math.atan2(tan[1],tan[0] * 180.0 / Math.PI)
 *                      用于获取路径上某点的切线的角度
 */
public class LoadingPathMeasureActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading_path_measure);
    }
}
