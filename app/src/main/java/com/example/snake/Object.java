package com.example.snake;

import android.graphics.Bitmap;
import android.graphics.Rect;

public class Object {

    protected float x,y;
    protected int width, height;
    protected Bitmap bitMap;
    protected Rect rec;
    public Object(){

    }

    public Object(float x, float y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public float getX() {
        return x;
    }

    public float setX(float x) {
        this.x = x;
        return x;
    }

    public float getY() {
        return y;
    }

    public float setY(float y) {
        this.y = y;
        return y;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public Bitmap getBitMap() {
        return bitMap;
    }

    public void setBitMap(Bitmap bitMap) {
        this.bitMap = bitMap;
    }

    public Rect getRec() {
        return new Rect((int) this.x, (int)this.y, (int)this.x + this.width, (int)this.y + this.height);
    }

    public void setRec(Rect rec) {
        this.rec = rec;
    }
}
