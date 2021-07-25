package com.example.a2048gameandroid;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import com.example.a2048gameandroid.Board.Board;
import com.example.a2048gameandroid.Board.Tile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class TilesManager implements TileManagerInterface, Board {

    private Resources res;
    private int stdSize,sWidth,sHeight;
    private ArrayList<Integer> drawables = new ArrayList<>();
    // All the possible bitmaps we can have.
    private HashMap<Integer,Bitmap> tilesBitmap = new HashMap<>() ;
    private Tile[][] tileMatrix = new Tile[4][4];
    private boolean moving = false;
    private boolean toSpawn = false;
    private ArrayList<Tile> movingTiles ;
    private boolean endGame = false;
    private GameOverCallback callback;



    public TilesManager(Resources resources, int stdSize, int sWidth,int sHeight,GameOverCallback gameOverCallback) {
        this.res = resources;
        this.stdSize = stdSize;
        this.sWidth = sWidth;
        this.sHeight = sHeight;
        this.callback = gameOverCallback;
        initilizeBitmaps();

        initGame();

    }

    public void initGame() {
        tileMatrix = new Tile[4][4];
        movingTiles = new ArrayList<>();

        // start off with 2 elements on the screen
        for (int i = 0; i < 2; i++) {
            int x = new Random().nextInt(4);
            int y = new Random().nextInt(4);
            if (tileMatrix[x][y] == null) {
                Tile tile = new Tile(stdSize, sWidth, sHeight, this, x, y);
                tileMatrix[x][y] = tile;
            } else {
                i--;
            }
        }

        //        for (int i = 0; i < 4; i++) {
//            for (int j = 0; j < 4; j++) {
//                if(i != 3 || j != 3) {
//                    Tile t = new Tile(standardSize, screeWidth, screenHeight, this, i, j, 3 * i + j + 4);
//                    matrix[i][j] = t;
//                }
//            }
//        }

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
        for(int i =0;i<4;i++){
            for(int j=0;j<4;j++){
                if(tileMatrix[i][j] != null){
                    tileMatrix[i][j].draw(canvas);
                }
            }
        }
        if(endGame){
        callback.gameOver();
        }

    }

    @Override
    public void update() {
        for(int i =0;i<4;i++){
            for(int j=0;j<4;j++){
                if(tileMatrix[i][j] != null){
                    tileMatrix[i][j].update();
                }
            }
        }

    }

    // Method which gives the bitmap based on the count variable
    @Override
    public Bitmap getBitmap(int count) {
        return tilesBitmap.get(count);
    }

    //
    public void onSwipe(SwipeCallBack.Direction direction) {
        if (!moving) {
          //  moving = true;
            Tile[][] outputMatrix = new Tile[4][4];

            switch (direction) {

                case UP:
                    for (int i = 0; i < 4; i++) {
                        for (int j = 0; j < 4; j++) {
                            if (tileMatrix[i][j] != null) {
                                outputMatrix[i][j] = tileMatrix[i][j];

                                for (int k = i - 1; k >= 0; k--) {
                                    if (outputMatrix[k][j] == null) {
                                        outputMatrix[k][j] = tileMatrix[i][j];
                                        if (outputMatrix[k + 1][j] == tileMatrix[i][j]) {
                                            outputMatrix[k + 1][j] = null;
                                        }
                                    } else if (outputMatrix[k][j].getValue() == tileMatrix[i][j].getValue() && !outputMatrix[k][j].toIncrement()) {
                                        outputMatrix[k][j] = tileMatrix[i][j].increment();
                                        if (outputMatrix[k + 1][j] == tileMatrix[i][j]) {
                                            outputMatrix[k + 1][j] = null;
                                        }
                                    } else {
                                        break;
                                    }
                                }
                            }
                        }
                    }
                    for (int i = 0; i < 4; i++) {
                        for (int j = 0; j < 4; j++) {
                            Tile t = tileMatrix[i][j];
                            Tile newT = null;
                            int matrixX = 0;
                            int matrixY = 0;

                            for (int a = 0; a < 4; a++) {
                                for (int b = 0; b < 4; b++) {
                                    if (outputMatrix[a][b] == t) {
                                        newT = outputMatrix[a][b];
                                        matrixX = a;
                                        matrixY = b;
                                        break;
                                    }
                                }
                            }
                            if (newT != null) {
                                movingTiles.add(t);
                                t.move(matrixX, matrixY);
                            }
                        }
                    }
                    break;

                case DOWN:

                    for (int i = 3; i >= 0; i--) {
                        for (int j = 0; j < 4; j++) {
                            if (tileMatrix[i][j] != null) {
                                outputMatrix[i][j] = tileMatrix[i][j];
                                for (int k = i + 1; k < 4; k++) {
                                    if (outputMatrix[k][j] == null) {
                                        outputMatrix[k][j] = tileMatrix[i][j];
                                        if (outputMatrix[k - 1][j] == tileMatrix[i][j]) {
                                            outputMatrix[k - 1][j] = null;
                                        }
                                    } else if (outputMatrix[k][j].getValue() == tileMatrix[i][j].getValue() && !outputMatrix[k][j].toIncrement()) {
                                        outputMatrix[k][j] = tileMatrix[i][j].increment();
                                        if (outputMatrix[k - 1][j] == tileMatrix[i][j]) {
                                            outputMatrix[k - 1][j] = null;
                                        }
                                    } else {
                                        break;
                                    }
                                }
                            }
                        }
                    }

                    for (int i = 3; i >= 0; i--) {
                        for (int j = 0; j < 4; j++) {
                            Tile t = tileMatrix[i][j];
                            Tile newT = null;
                            int matrixX = 0;
                            int matrixY = 0;
                            for (int a = 0; a < 4; a++) {
                                for (int b = 0; b < 4; b++) {
                                    if (outputMatrix[a][b] == t) {
                                        newT = outputMatrix[a][b];
                                        matrixX = a;
                                        matrixY = b;
                                        break;
                                    }
                                }
                            }
                            if (newT != null) {
                                movingTiles.add(t);
                                t.move(matrixX, matrixY);
                            }
                        }
                    }
                    break;

                case LEFT:

                    for (int i = 0; i < 4; i++) {
                        for (int j = 0; j < 4; j++) {
                            if (tileMatrix[i][j] != null) {
                                outputMatrix[i][j] = tileMatrix[i][j];
                                for (int k = j - 1; k >= 0; k--) {
                                    if (outputMatrix[i][k] == null) {
                                        outputMatrix[i][k] = tileMatrix[i][j];
                                        if (outputMatrix[i][k + 1] == tileMatrix[i][j]) {
                                            outputMatrix[i][k + 1] = null;
                                        }
                                    } else if (outputMatrix[i][k].getValue() == tileMatrix[i][j].getValue() && !outputMatrix[i][k].toIncrement()) {
                                        outputMatrix[i][k] = tileMatrix[i][j].increment();
                                        if (outputMatrix[i][k + 1] == tileMatrix[i][j]) {
                                            outputMatrix[i][k + 1] = null;
                                        }
                                    } else {
                                        break;
                                    }
                                }
                            }
                        }
                    }

                    for (int i = 0; i < 4; i++) {
                        for (int j = 0; j < 4; j++) {
                            Tile t = tileMatrix[i][j];
                            Tile newT = null;
                            int matrixX = 0;
                            int matrixY = 0;
                            for (int a = 0; a < 4; a++) {
                                for (int b = 0; b < 4; b++) {
                                    if (outputMatrix[a][b] == t) {
                                        newT = outputMatrix[a][b];
                                        matrixX = a;
                                        matrixY = b;
                                        break;
                                    }
                                }
                            }
                            if (newT != null) {
                                movingTiles.add(t);
                                t.move(matrixX, matrixY);
                            }
                        }
                    }
                    break;

                case RIGHT:
                    for (int i = 0; i < 4; i++) {
                        for (int j = 3; j >= 0; j--) {
                            if (tileMatrix[i][j] != null) {
                                outputMatrix[i][j] = tileMatrix[i][j];
                                for (int k = j + 1; k < 4; k++) {
                                    if (outputMatrix[i][k] == null) {
                                        outputMatrix[i][k] = tileMatrix[i][j];
                                        if (outputMatrix[i][k - 1] == tileMatrix[i][j]) {
                                            outputMatrix[i][k - 1] = null;
                                        }
                                    } else if (outputMatrix[i][k].getValue() == tileMatrix[i][j].getValue() && !outputMatrix[i][k].toIncrement()) {
                                        outputMatrix[i][k] = tileMatrix[i][j].increment();
                                        if (outputMatrix[i][k - 1] == tileMatrix[i][j]) {
                                            outputMatrix[i][k - 1] = null;
                                        }
                                    } else {
                                        break;
                                    }

                                }
                            }
                        }
                    }


                    for (int i = 0; i < 4; i++) {
                        for (int j = 3; j >= 0; j--) {
                            Tile t = tileMatrix[i][j];
                            Tile newT = null;
                            int matrixX = 0;
                            int matrixY = 0;
                            for (int a = 0; a < 4; a++) {
                                for (int b = 0; b < 4; b++) {
                                    if (outputMatrix[a][b] == t) {
                                        newT = outputMatrix[a][b];
                                        matrixX = a;
                                        matrixY = b;
                                        break;
                                    }
                                }
                            }
                            if (newT != null) {
                                movingTiles.add(t);
                                t.move(matrixX, matrixY);
                            }
                        }
                    }
                    break;
            }
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    if (outputMatrix[i][j] != tileMatrix[i][j]) {
                        toSpawn = true;
                        break;
                    }
                }
            }
            tileMatrix = outputMatrix;
        }
    }

    @Override
    public void finishedMoving(Tile t) {
        movingTiles.remove(t);
        if (movingTiles.isEmpty()) {
            moving = false;
            spawnNewTile();
            checkEndgame();
        }
    }

    private void spawnNewTile() {
        if (toSpawn) {
            toSpawn = false;
            Tile t = null;
            while (t == null) {
                int x = new Random().nextInt(4);
                int y = new Random().nextInt(4);
                if (tileMatrix[x][y] == null) {
                    t = new Tile(stdSize, sWidth, sHeight, this, x, y);
                    tileMatrix[x][y] = t;
                }
            }
        }
    }

    @Override
    public void updateScore(int delta) {
        callback.updateScore(delta);
    }

    private void checkEndgame() {
        endGame = true;

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if(tileMatrix[i][j] == null) {
                    endGame = false;
                    break;
                }
            }
        }

        if(endGame) {

            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    if((i > 0 && tileMatrix[i-1][j].getValue() == tileMatrix[i][j].getValue()) ||
                            (i < 3 && tileMatrix[i+1][j].getValue() == tileMatrix[i][j].getValue()) ||
                            (j > 0 && tileMatrix[i][j-1].getValue() == tileMatrix[i][j].getValue()) ||
                            (j < 3 && tileMatrix[i][j+1].getValue() == tileMatrix[i][j].getValue())) {
                        endGame = false;
                    }
                }
            }
        }
    }

    @Override
    public void reached2048() {
    callback.reached2048();
    }
}
