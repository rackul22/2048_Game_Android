package com.example.a2048gameandroid.Board;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import com.example.a2048gameandroid.R;

public class GameGrid implements Board {

    private Bitmap grid;
    private int screenWidth,screenHeight;
    private int standardSize;

    public GameGrid(Resources res, int screenWidth, int screenHeight, int stdSize){
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.standardSize = stdSize;

        Bitmap bitmap = BitmapFactory.decodeResource(res, R.drawable.grid);
        grid = Bitmap.createScaledBitmap(bitmap,standardSize*4,standardSize*4,false);
    }

    @Override
    public void draw(Canvas canvas) {
        //to draw it in the centre
        canvas.drawBitmap(grid,screenWidth/2 - grid.getWidth()/2,screenHeight/2-grid.getHeight()/2,null);

    }

    @Override
    public void update() {

    }
}
