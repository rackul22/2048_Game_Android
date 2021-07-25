package com.example.a2048gameandroid.Board;

import android.graphics.Canvas;

import com.example.a2048gameandroid.TileManagerInterface;

public class Tile implements Board {

    private int standardSize,screenWidth,screenHeight;
    private TileManagerInterface callback;
    //to indicate the level the tile is on
    private int count = 1;

    private int currentX,currentY;
    private int destX, destY;
    private  boolean isMoving = false;
    private int speed = 10;


    public Tile(int stdsize,int sWidth, int sHeight, TileManagerInterface callback,int mX, int mY) {
        this.standardSize = stdsize;
        this.screenWidth = sWidth;
        this.screenHeight = sHeight;
        this.callback = callback;

        //to calculate the postion of x and y on the screen
        currentX = destX = screenWidth / 2 - 2 * standardSize +  mY * standardSize;
        currentY = destY = screenHeight / 2 - 2 * standardSize +  mX * standardSize;
    }

    @Override
    public void draw(Canvas canvas) {
        //to draw the tile we need to get the Bitmap
        canvas.drawBitmap(callback.getBitmap(count),currentX,currentY,null);
        if(isMoving && currentX == destX && currentY == destY) {
            isMoving = false;
        }
    }

    //method to move the tile to a certain dest
    public void move(int mX, int mY){
        isMoving = true;
        destX = screenWidth/ 2 - 2 * standardSize +  mY *standardSize;
        destY = screenHeight/  2 - 2 * standardSize +  mX *standardSize;
    }

    @Override
    public void update() {
        // to move the tile on x-axis
        if(currentX<destX){
            if(currentX + speed >destX){
                currentX = destX;
            }else{
                currentX  += speed;
            }
        }else if(currentX>destX){
            if(currentX - speed <destX){
                currentX = destX;
            }else{
                currentX -= speed;
            }
        }

        // to move the tile on y-axis
        if(currentY<destY){
            if(currentY + speed >destY){
                currentY = destY;
            }else{
                currentY  += speed;
            }
        }else if(currentY>destY){
            if(currentY - speed <destY){
                currentY= destY;
            }else{
                currentY -= speed;
            }
        }


    }

}
