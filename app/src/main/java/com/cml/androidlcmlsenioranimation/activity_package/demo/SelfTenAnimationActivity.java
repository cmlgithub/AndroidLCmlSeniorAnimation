package com.cml.androidlcmlsenioranimation.activity_package.demo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.cml.androidlcmlsenioranimation.R;
import com.cml.androidlcmlsenioranimation.activity_package.demo.demo1_self.SelfDemo1Activity;
import com.cml.androidlcmlsenioranimation.activity_package.demo.demo2_self.SelfDemo2Activity;

public class SelfTenAnimationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_self_ten_animation);
    }

    public void selfDemo1(View view){
        startActivity(new Intent(this, SelfDemo1Activity.class));
    }
    public void selfDemo2(View view){
        startActivity(new Intent(this, SelfDemo2Activity.class));
    }
}
