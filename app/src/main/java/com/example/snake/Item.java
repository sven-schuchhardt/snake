package com.example.snake;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

public class Item {
    private Bitmap bm;
    private int x,y;
    private Rect rec;

    public Item(Bitmap bm, int x, int y){
        this.bm = bm;
        this.x = x;
        this.y = y;
    }

    public Bitmap getBm() {
        return bm;
    }

    public void setBm(Bitmap bm) {
        this.bm = bm;
    }

    public int getX() {
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

    public Rect getRec() {
        return new Rect(this.x, this.y, this.x+GameView.sizeOfMap, this.y+GameView.sizeOfMap);
    }

    public void setRec(Rect rec) {
        this.rec = rec;
    }

    public void draw(Canvas canvas) {
        canvas.drawBitmap(bm, x, y, null);
    }

    public void reset(int rx, int ry){
        this.x = rx;
        this.y = ry;
    }
}
