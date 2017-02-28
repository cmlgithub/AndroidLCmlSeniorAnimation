package com.cml.androidlcmlsenioranimation.activity_package.demo.demo1_self;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.cml.androidlcmlsenioranimation.R;

public class SelfDemo1Activity extends AppCompatActivity {

    private LoveViewInViewGroup mLoveViewRelativeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_self_demo1);

        mLoveViewRelativeLayout = (LoveViewInViewGroup) findViewById(R.id.loveViewRelativeLayout);
        mLoveViewRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLoveViewRelativeLayout.addHeart();
            }
        });
    }
}
