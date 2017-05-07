package com.sztokrotki.gloskuj.game.cups;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import com.sztokrotki.gloskuj.MainActivity;
import com.sztokrotki.gloskuj.R;

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

        //Ograniczenia poruszania sie na boki
        if(x<1) x=1; //od lewej
        if(x> MainActivity.screenWidth-1-width) x=MainActivity.screenWidth-1-width; //od prawej
    }

    /**
     * Rysowanie postaci.
     * @param canvas Canvasy na ktorych rysowana jest postac.
     */
    public void draw(Canvas canvas){canvas.drawBitmap(image, x, y, null);  }

}
