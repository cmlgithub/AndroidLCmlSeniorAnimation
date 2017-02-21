package com.cml.androidlcmlsenioranimation.activity_package;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.cml.androidlcmlsenioranimation.R;

/**
 *      三角函数模拟网站
 *          https://www.desmos.com/calculator
 *
 *      绘制波浪效果的两种方式
 *          1.利用三角函数
 *          2.利用Bezier曲线(这里采用这种)
 *
 */
public class WaveActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wave);
    }
}
