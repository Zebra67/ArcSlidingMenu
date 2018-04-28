package com.example.jiangsj.arcdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.jiangsj.arcdemo.view.ArcSlidingMenu;

public class MainActivity extends AppCompatActivity implements ArcSlidingMenu.OnZoomValueChangedListener {
    private static final String TAG = "MainActivity";
    private ArcSlidingMenu menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        menu = findViewById(R.id.arc_menu);
        menu.setOnZoomValueChangedListener(this);


    }


    @Override
    public void onZoomValueChanged(int v) {
        Log.d(TAG,"zoom value: "+v);
    }
}
