package com.example.a2048gameandroid;


public interface SwipeCallBack {

    void onSwipe(Direction direction);

    enum Direction{
        LEFT,
        RIGHT,
        UP,
        DOWN
    }

}
