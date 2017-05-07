package com.sztokrotki.gloskuj.game.cups;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.sztokrotki.gloskuj.MainActivity;

class Cup extends Object {

    protected Lives lives;
    private int gyroSensitivity;
    private int textSize;

    public Cup(Bitmap cup, Bitmap heart, int gyroSensitivity, int textSize){
        image=cup;
        width=image.getWidth();
        height=image.getHeight();
        x=MainActivity.screenWidth/2-width/2;
        y=MainActivity.screenHeight-height-getHeight()/20;
        this.gyroSensitivity=gyroSensitivity;
        this.textSize=textSize;
        lives= new Lives(heart, textSize);
    }

    public void update(){

        x = x - (int)((CupsActivity.gyroX *gyroSensitivity));

        if(x<1) x=1;
        if(x> MainActivity.screenWidth-1-width) x=MainActivity.screenWidth-1-width;
    }

    public void draw(Canvas canvas){canvas.drawBitmap(image, x, y, null);  }

}
