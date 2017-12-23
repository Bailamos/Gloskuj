package com.sztokrotki.gloskuj.game.cups;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.sztokrotki.gloskuj.MainActivity;

class Lives {
    private int number;
    private int textSize;
    private Bitmap image;

    public Lives(Bitmap res, int textSize){
        number=3;
        image=res;
        this.textSize=textSize;
    }

    protected void addLive(){number++;}

    protected void removeLive(){number--;}

    protected boolean isAlive(){return number>0;}

    protected void kill() {number=0;}

    public void draw(Canvas canvas){
        for(int i=0;i<number;i++) {
            canvas.drawBitmap(image, (float)(0.01* MainActivity.screenWidth+i*1.1*image.getWidth()), 2*textSize, null);
        }
    }
}
