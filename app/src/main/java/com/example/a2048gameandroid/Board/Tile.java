package com.example.a2048gameandroid.Board;

import android.graphics.Canvas;

import com.example.a2048gameandroid.TileManagerInterface;

public class Tile implements Board {

    private int standardSize,screenWidth,screenHeight;
    private TileManagerInterface callback;
    //to indicate the level the tile is on
    private int count =4;


    public Tile(int stdsize,int sWidth, int sHeight, TileManagerInterface callback) {
        this.standardSize = stdsize;
        this.screenWidth = sWidth;
        this.screenHeight = sHeight;
        this.callback = callback;
    }

    @Override
    public void draw(Canvas canvas) {
        //to draw the tile we need to get the Bitmap
        canvas.drawBitmap(callback.getBitmap(count),screenWidth/2 - 2*standardSize,screenHeight/2 - 2*standardSize,null);

    }

    @Override
    public void update() {

    }
}
