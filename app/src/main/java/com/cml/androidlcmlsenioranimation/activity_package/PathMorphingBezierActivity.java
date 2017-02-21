package com.cml.androidlcmlsenioranimation.activity_package;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.cml.androidlcmlsenioranimation.R;

/**
 *  路径变换动画效果
 *      可以用来替代VectorDrawable在Android L pre 下的兼容性问题的解决方案
 */

public class PathMorphingBezierActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_path_morthing_bezier);
    }
}
