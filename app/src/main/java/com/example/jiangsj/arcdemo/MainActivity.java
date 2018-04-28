package com.example.jiangsj.arcdemo;

import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;

import com.example.jiangsj.arcdemo.view.ArcSlidingMenu;

public class MainActivity extends AppCompatActivity  {
    private ArcSlidingMenu menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        menu = findViewById(R.id.arc_menu);


    }


}
