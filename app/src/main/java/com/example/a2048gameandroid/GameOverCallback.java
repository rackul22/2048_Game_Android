package com.example.a2048gameandroid;

public interface GameOverCallback {
    void gameOver();
    void updateScore(int delta);
    void reached2048();
}
