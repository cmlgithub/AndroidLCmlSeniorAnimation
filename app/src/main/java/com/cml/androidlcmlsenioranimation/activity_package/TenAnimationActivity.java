package com.cml.androidlcmlsenioranimation.activity_package;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.cml.androidlcmlsenioranimation.R;

public class TenAnimationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ten_animation);
    }

    public void loveRandom(View view){
        startActivity(new Intent(this,LoveRandomActivity.class));
    }
}
