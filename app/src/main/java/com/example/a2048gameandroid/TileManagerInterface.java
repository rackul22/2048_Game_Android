package com.example.a2048gameandroid;

import android.graphics.Bitmap;

import com.example.a2048gameandroid.Board.Tile;

public interface TileManagerInterface {

   Bitmap getBitmap(int count);
   void finishedMoving(Tile t);
   void updateScore(int delta);
   void reached2048();
}
