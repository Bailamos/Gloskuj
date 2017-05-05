package com.sztokrotki.gloskuj.game.cups;


import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.sztokrotki.gloskuj.MainActivity;

import java.util.Random;

public class Letter extends Object {

    private boolean type;

    public Letter(Bitmap res){
        image= res;
        width=image.getWidth();
        height=image.getHeight();
        y=-2*height;
        dy=MainActivity.screenHeight/1000;
        Random rand = new Random();
        x= rand.nextInt(MainActivity.screenWidth-2*width)+width;
        type=rand.nextBoolean();
    }

    public void update(){

        y=y+dy;
    }
    public void draw(Canvas canvas){canvas.drawBitmap(image, x, y, null);}
}
