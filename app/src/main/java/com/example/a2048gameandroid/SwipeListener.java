package com.example.a2048gameandroid;

import android.content.Context;
import android.view.GestureDetector;
import android.view.MotionEvent;

public class SwipeListener implements GestureDetector.OnGestureListener {

    private GestureDetector detector;
    private SwipeCallBack callBack;


    public SwipeListener(Context context, SwipeCallBack callBack){
      this.callBack = callBack;
      detector = new GestureDetector(context,this);
    }

    public void onTouchEvent(MotionEvent e){
        detector.onTouchEvent(e);
    }

    @Override
    public boolean onDown(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent motionEvent) {

    }

    //method tells us when the motion is flinged
    /*
    Based on the velocity of the v and v1 on the x and y axis we will decide if we need to swipe up or down
    Based on vx/vy we need to move the tile to the right
     */
    @Override
    public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent1, float vx, float vy) {
        if(Math.abs(vx)>Math.abs(vy)){
            //horizontal axis
            if(vx>0){
                callBack.onSwipe(SwipeCallBack.Direction.RIGHT);
            }else{
                callBack.onSwipe(SwipeCallBack.Direction.LEFT);
            }
        }else{
            if(vy>0){
                callBack.onSwipe(SwipeCallBack.Direction.UP);
            }else{
                callBack.onSwipe(SwipeCallBack.Direction.DOWN);
            }
        }
        return false;
    }
}
