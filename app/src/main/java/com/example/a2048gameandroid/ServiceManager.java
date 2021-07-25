package com.example.a2048gameandroid;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

import com.example.a2048gameandroid.Board.GameGrid;

public class ServiceManager extends SurfaceView implements SurfaceHolder.Callback {

    private GameMainThread thread;
    private GameGrid grid;

    private int screenWidth,screenHeight;
    private int stdSize;

    public ServiceManager(Context context, AttributeSet attrs){
        super(context,attrs);
        getHolder().addCallback(this);

        //to get the screen width and height to pass to the Grid
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        screenWidth = displayMetrics.widthPixels;
        screenHeight = displayMetrics.heightPixels;
        //to define the size of the grid to occupy the whole screen nearly 88%
        stdSize = (int) (screenWidth *.88)/4;

        grid = new GameGrid(getResources(),screenWidth,screenHeight,stdSize);
    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder surfaceHolder) {
        thread = new GameMainThread(surfaceHolder,this);
        thread.setRunning(true);
        thread.start();
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder surfaceHolder, int i, int i1, int i2) {
        thread.setSurfaceHolder(surfaceHolder);
    }

    //called when app is put to background and thread is destroyed.
    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder surfaceHolder) {
        boolean retry = true;
        while(retry){
            try {
                thread.setRunning(false);
                thread.join();
                retry = false;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    public void update() {

    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        canvas.drawRGB(253,255,245);
        grid.draw(canvas);
    }
}
