package com.example.a2048gameandroid;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

import com.example.a2048gameandroid.Board.GameGrid;
import com.example.a2048gameandroid.Board.GameOver;
import com.example.a2048gameandroid.Board.Scores;

public class ServiceManager extends SurfaceView implements SurfaceHolder.Callback,SwipeCallBack,GameOverCallback {

    private  static  final  String APP_NAME = "2048";
    private GameMainThread thread;
    private GameGrid grid;

    private int screenWidth,screenHeight;
    private int stdSize;
    private TilesManager tilesManager;
    private SwipeListener swipeListener;
    private boolean endGame = false;
    private GameOver gameOver;
    private Scores scores;
    private Bitmap restartButton;
    private int restartButtonX, restartButtonY, restartButtonSize;

    public ServiceManager(Context context, AttributeSet attrs){
        super(context,attrs);
        //to intercept the ontouch events
        setLongClickable(true);

        getHolder().addCallback(this);
        swipeListener = new SwipeListener(getContext(),this);

        //to get the screen width and height to pass to the Grid
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        screenWidth = displayMetrics.widthPixels;
        screenHeight = displayMetrics.heightPixels;
        //to define the size of the grid to occupy the whole screen nearly 88%
        stdSize = (int) (screenWidth *.88)/4;

        grid = new GameGrid(getResources(),screenWidth,screenHeight,stdSize);
        tilesManager = new TilesManager(getResources(),stdSize,screenWidth,screenHeight,this);
        gameOver = new GameOver(getResources(),screenWidth,screenHeight);
        scores = new Scores(getResources(),screenWidth,screenHeight,stdSize,getContext().getSharedPreferences(APP_NAME,Context.MODE_PRIVATE));

        restartButtonSize = (int) getResources().getDimension(R.dimen.restart_button_size);
        Bitmap bmpRestart = BitmapFactory.decodeResource(getResources(), R.drawable.restart);
        restartButton = Bitmap.createScaledBitmap(bmpRestart, restartButtonSize, restartButtonSize, false);
        restartButtonX = screenWidth / 2 + 2 * stdSize - restartButtonSize;
        restartButtonY = screenHeight / 2 - 2 * stdSize - 3 * restartButtonSize / 2;
    }

    public void initGame() {
        endGame = false;
        tilesManager.initGame();
        scores = new Scores(getResources(), screenWidth, screenHeight, stdSize, getContext().getSharedPreferences(APP_NAME, Context.MODE_PRIVATE));
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
        if(!endGame) {
            tilesManager.update();
        }

    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);

        canvas.drawRGB(253,255,245);
        grid.draw(canvas);
        tilesManager.draw(canvas);
        scores.draw(canvas);
        canvas.drawBitmap(restartButton,restartButtonX,restartButtonY,null);
        if(endGame){
          gameOver.draw(canvas);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(endGame){
            if(event.getAction() == MotionEvent.ACTION_DOWN){
             initGame();
            }
        }else{
            float eventX = event.getAxisValue(MotionEvent.AXIS_X);
            float eventY = event.getAxisValue(MotionEvent.AXIS_Y);
            if(event.getAction() == MotionEvent.ACTION_DOWN &&
                    eventX > restartButtonX && eventX < restartButtonX + restartButtonSize &&
                    eventY > restartButtonY && eventY < restartButtonY + restartButtonSize) {
                initGame();
            } else {
                swipeListener.onTouchEvent(event);
            }
        }
        return super.onTouchEvent(event);
    }

    @Override
    public void onSwipe(Direction direction) {
        tilesManager.onSwipe(direction);
    }

    @Override
    public void gameOver() {
        endGame = true;
    }

    @Override
    public void updateScore(int delta) {
        scores.updateScore(delta);
    }

    @Override
    public void reached2048() {
        scores.reached2048();
    }
}
