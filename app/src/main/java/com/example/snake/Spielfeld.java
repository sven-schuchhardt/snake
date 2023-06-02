package com.example.snake;

import android.graphics.Bitmap;

public class Spielfeld {
    private int x;
    private int y;
    private int height;
    private int width;
    private Bitmap bitMap;

    public Spielfeld(Bitmap bitMap, int x, int y, int height, int width){
        this.bitMap = bitMap;
        this.x = x;
        this.y = y;
        this.height = height;
        this.width = width;
    }
    public Bitmap getBitMap() {
        return bitMap;
    }

    public void setBitMap(Bitmap bitMap) {
        this.bitMap = bitMap;
    }
    public int getX(){
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

}
