package com.example.a2048gameandroid;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

public class GameMainThread extends Thread {

    private SurfaceHolder surfaceHolder;
    private ServiceManager serviceManager;
    private int tFramesPerSecond = 60;
    private boolean running;
    private Canvas canvas;

    public GameMainThread(SurfaceHolder surfaceHolder,ServiceManager serviceManager){
        super();
        this .surfaceHolder = surfaceHolder;
        this.serviceManager = serviceManager;
    }

    public void setRunning(Boolean isRunning){
        running = isRunning;
    }

    public void setSurfaceHolder(SurfaceHolder surfaceHolder) {
        this.surfaceHolder = surfaceHolder;
    }


    @Override
    public void run() {
        long startTime,timeInMillis,waitTime;
        long targetTime = 1000/tFramesPerSecond;

        while(running){
            startTime = System.nanoTime();
            canvas = null;
            try{
                //Gives the canvas which is locked only from our thread to access.
                canvas = surfaceHolder.lockCanvas();
                //to ensure the canvas is locked
                synchronized (surfaceHolder){
                    serviceManager.update();
                    serviceManager.draw(canvas);
                }
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                //when app is put to background, we do not want the thread to be locked ,
                // hence to unlock the canvas call this method
                if(canvas != null){
                    try{
                        surfaceHolder.unlockCanvasAndPost(canvas);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }

            //to prevent the calling of game manager several times,until the new frame is created
            timeInMillis = (System.nanoTime() - startTime)/ 1000000;
            waitTime = targetTime -timeInMillis;

            try {
                if(waitTime>0){
                    sleep(waitTime);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}
