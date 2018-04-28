package com.example.jiangsj.arcdemo.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Point;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.example.jiangsj.arcdemo.R;

public class ArcSlidingMenu extends ViewGroup implements View.OnClickListener {
    private static String TAG = "jjj";


    private int mRadius;
    private double mArc;
    private int mMarginTop;

    private int viewWidth;
    private int viewHeight;
    private Point mCenter;
    private int mOffset = 0;
    private int mState = 0;
    private int lastX;
    private int lastY;
    private boolean isPrePareDismiss = false;
    private View[] chidViews;
    private OnZoomValueChangedListener mOnZoomValueChangedListener;


    public interface OnZoomValueChangedListener {
        void onZoomValueChanged(int v);
    }


    @Override
    public void onClick(View v) {
        int i = -1;
        for(View view:chidViews){
            i++;
            if(view == v){
                mOffset = 0-i;
                requestLayout();
                mOnZoomValueChangedListener.onZoomValueChanged(Math.abs(mOffset)+1);
            }
        }

    }

    public  void setOnZoomValueChangedListener(OnZoomValueChangedListener l){
        mOnZoomValueChangedListener = l;
    }








    public ArcSlidingMenu(Context context) {
        this(context, null);
    }

    public ArcSlidingMenu(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ArcSlidingMenu(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mRadius = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 100, getResources().getDisplayMetrics());
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.ArcSlidingMenu, defStyleAttr, 0);
        mRadius = (int) a.getDimension(R.styleable.ArcSlidingMenu_radius, 0);
        DisplayMetrics dm = new DisplayMetrics();
        dm = getResources().getDisplayMetrics();
        mMarginTop = (int) dm.density*50;
        a.recycle();
        mCenter = new Point();

    }


    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int count = getChildCount();
        if(null == chidViews ){
            chidViews = new View[count];
            for(int i = 0;i<count;i++){
                chidViews[i] = getChildAt(i);
                chidViews[i].setOnClickListener(this);
            }
        }
        if(chidViews !=null && chidViews.length == count){

            Log.d(TAG, "onLayout() changed");
            viewWidth = this.getWidth();
            viewHeight = this.getHeight();

            mArc = Math.PI / 8;

            for (int i = 0 + mOffset; i < count + mOffset; i++) {
                View chid = chidViews[i-mOffset];
                int cl = (int) (mRadius * Math.sin(mArc * i));
                int ct = (int) (mRadius * Math.cos(mArc * i));
                int cWidth = chid.getMeasuredWidth();
                int cHeight = chid.getMeasuredHeight();
                viewHeight = mMarginTop+mRadius;
                chid.layout(cl + (viewWidth - cWidth) / 2, viewHeight - ct - cHeight / 2, cl + (viewWidth + cWidth) / 2, viewHeight - ct + cHeight / 2);
            }

            mCenter.set(viewWidth / 2, viewHeight);

        }


    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Log.d(TAG, "onMeasure()");
        int count = getChildCount();
        for (int i = 0; i < count; i++) {
            measureChild(getChildAt(i), widthMeasureSpec, heightMeasureSpec);
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    }

    public Point getCenter() {
        return mCenter;
    }


    private void refreshLayout(int offsetX, int offsetY) {
        Log.d(TAG, "offsetX: " + offsetX + " offsetY: " + offsetY);
        if (Math.abs(offsetX) - Math.abs(offsetY) > 50) {
            int state = -1;
            state = offsetX > 200 ? 1 : offsetX < -200 ? 0 : state;
            if (state == 1 && mOffset < 0) {
                mOffset++;
            }
            if (state == 0 && mOffset > -3) {
                mOffset--;
            }
            requestLayout();
        }
        mOnZoomValueChangedListener.onZoomValueChanged(Math.abs(mOffset)+1);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        isPrePareDismiss = false;
        int x = (int) event.getX();
        int y = (int) event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                lastX = x;
                lastY = y;
                break;
            case MotionEvent.ACTION_MOVE:
                int offsetX = x - lastX;
                int offsetY = y - lastY;
                if(Math.abs(offsetX) >200){
                    lastX = x;
                    lastY = y;
                }
                refreshLayout(offsetX, offsetY);
                break;
            case MotionEvent.ACTION_UP:
                isPrePareDismiss = true;

        }

        return true;
    }

    public void setZoomValue(int x){
        mOffset = -x+1;
        requestLayout();
    }

}
