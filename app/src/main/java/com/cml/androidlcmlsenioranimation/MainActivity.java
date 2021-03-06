package com.cml.androidlcmlsenioranimation;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.cml.androidlcmlsenioranimation.activity_package.AddShoppingCartActivity;
import com.cml.androidlcmlsenioranimation.activity_package.LoadingPathMeasureActivity;
import com.cml.androidlcmlsenioranimation.activity_package.PathMorphingBezierActivity;
import com.cml.androidlcmlsenioranimation.activity_package.SecondBezierActivity;
import com.cml.androidlcmlsenioranimation.activity_package.TenAnimationActivity;
import com.cml.androidlcmlsenioranimation.activity_package.ThirdBezierActivity;
import com.cml.androidlcmlsenioranimation.activity_package.TwoPointSmoothDealActivity;
import com.cml.androidlcmlsenioranimation.activity_package.VectorDrawableActivity;
import com.cml.androidlcmlsenioranimation.activity_package.WaveActivity;
import com.cml.androidlcmlsenioranimation.activity_package.demo.SelfTenAnimationActivity;

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
    public void pathMorphingBezier(View view){
        startActivity(new Intent(this,PathMorphingBezierActivity.class));
    }
    public void waveBezier(View view){
        startActivity(new Intent(this,WaveActivity.class));
    }
    public void addShoppingCart(View view){
        startActivity(new Intent(this,AddShoppingCartActivity.class));
    }
    public void loadingPathMeasure(View view){
        startActivity(new Intent(this,LoadingPathMeasureActivity.class));
    }
    public void tenAnimation(View view){
        startActivity(new Intent(this,TenAnimationActivity.class));
    }
    public void selfTenAnimation(View view){
        startActivity(new Intent(this,SelfTenAnimationActivity.class));
    }
}
