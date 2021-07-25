package com.example.a2048gameandroid.Board;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import com.example.a2048gameandroid.R;

public class GameOver implements Board {

    private int sWidth,sHeight;
    private Bitmap bitmap;

    public  GameOver(Resources res, int sWidth, int sHeight){
        this.sWidth = sWidth;
        this.sHeight = sHeight;

        int endgameWidth = (int) res.getDimension(R.dimen.endgame_width);
        int endgameHeight = (int) res.getDimension(R.dimen.endgame_height);

        Bitmap b = BitmapFactory.decodeResource(res, R.drawable.gameover);
        bitmap = Bitmap.createScaledBitmap(b, endgameWidth, endgameHeight, false);
    }
    @Override
    public void draw(Canvas canvas) {
        canvas.drawBitmap(bitmap, sWidth / 2 - bitmap.getWidth() / 2, sHeight / 2 - bitmap.getHeight() / 2, null);
    }

    @Override
    public void update() {

    }
}
