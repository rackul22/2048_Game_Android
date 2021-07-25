package com.example.a2048gameandroid;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import com.example.a2048gameandroid.Board.Board;
import com.example.a2048gameandroid.Board.Tile;

import java.util.ArrayList;
import java.util.HashMap;

public class TilesManager implements TileManagerInterface, Board {

    private Resources res;
    private int stdSize,sWidth,sHeight;
    private Tile tile;
    private ArrayList<Integer> drawables = new ArrayList<>();
    // All the possible bitmaps we can have.
    private HashMap<Integer,Bitmap> tilesBitmap = new HashMap<>() ;

    public TilesManager(Resources resources, int stdSize, int sWidth,int sHeight) {
        this.res = resources;
        this.stdSize = stdSize;
        this.sWidth = sWidth;
        this.sHeight = sHeight;
        initilizeBitmaps();

       tile = new Tile(stdSize,sWidth,sHeight,this);
    }

    // Instantiate the bitmaps and retreives them into a list.
    private void initilizeBitmaps(){
        // Add all the possible drawables in the Arraylist of the drawables like 2,4,8,26,32,64...so on upto 2048
        drawables.add(R.drawable.one);
        drawables.add(R.drawable.two);
        drawables.add(R.drawable.three);
        drawables.add(R.drawable.four);
        drawables.add(R.drawable.five);
        drawables.add(R.drawable.six);
        drawables.add(R.drawable.seven);
        drawables.add(R.drawable.eight);
        drawables.add(R.drawable.nine);
        drawables.add(R.drawable.ten);
        drawables.add(R.drawable.eleven);
        drawables.add(R.drawable.twelve);
        drawables.add(R.drawable.thirteen);
        drawables.add(R.drawable.fourteen);
        drawables.add(R.drawable.fifteen);
        drawables.add(R.drawable.sixteen);


        for(int i=1;i<=16;i++){
            Bitmap bitmap = BitmapFactory.decodeResource(res,drawables.get(i-1));
            Bitmap tBitmip = Bitmap.createScaledBitmap(bitmap,stdSize,stdSize,false);
            tilesBitmap.put(i,tBitmip);
        }

    }

    @Override
    public void draw(Canvas canvas) {
        tile.draw(canvas);
    }

    @Override
    public void update() {

    }

    // Method which gives the bitmap based on the count variable
    @Override
    public Bitmap getBitmap(int count) {
        return tilesBitmap.get(count);
    }
}
