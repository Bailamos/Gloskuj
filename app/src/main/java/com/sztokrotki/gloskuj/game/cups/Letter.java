package com.sztokrotki.gloskuj.game.cups;


import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.sztokrotki.gloskuj.MainActivity;

import java.util.Random;

public class Letter extends Object {

    private boolean type;

    public Letter(Bitmap res, int maxIndex, int level, int dy_diversity, int dy_divider){
        Random rand = new Random();
        int index=rand.nextInt(maxIndex);
        if(index<1) type=true;
        else type=false;
        image= Bitmap.createBitmap(res, index*res.getWidth()/maxIndex, 0, res.getWidth()/maxIndex, res.getHeight());
        width=image.getWidth();
        height=image.getHeight();
        y=-2*height;
        dy=(rand.nextInt(dy_diversity)+1)*level*MainActivity.screenHeight/dy_divider;
        x= rand.nextInt(MainActivity.screenWidth-2*width)+width;
    }

    public void update(){

        y=y+dy;
    }
    public void draw(Canvas canvas){canvas.drawBitmap(image, x, y, null);}

    public boolean getType(){return type;}
}
