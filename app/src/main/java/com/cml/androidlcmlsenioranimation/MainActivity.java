package com.cml.androidlcmlsenioranimation;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.cml.androidlcmlsenioranimation.activity_package.SecondBezierActivity;
import com.cml.androidlcmlsenioranimation.activity_package.ThirdBezierActivity;
import com.cml.androidlcmlsenioranimation.activity_package.TwoPointSmoothDealActivity;
import com.cml.androidlcmlsenioranimation.activity_package.VectorDrawableActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void vectorDrawable(View view){
        startActivity(new Intent(this,VectorDrawableActivity.class));
    }
    public void secondBezier(View view){
        startActivity(new Intent(this,SecondBezierActivity.class));
    }
    public void thirdBezier(View view){
        startActivity(new Intent(this,ThirdBezierActivity.class));
    }
    public void twoPointSmoothDeal(View view){
        startActivity(new Intent(this,TwoPointSmoothDealActivity.class));
    }
}