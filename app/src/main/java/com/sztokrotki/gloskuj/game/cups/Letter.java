package com.sztokrotki.gloskuj.game.cups;


import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.sztokrotki.gloskuj.MainActivity;

import java.util.Random;

class Letter extends Object {

    private boolean type;
    private boolean isLetter;
    private int prevY;

    public Letter(Bitmap res, boolean isLetter, int maxIndex, int level, int dy_diversity, int dy_divider, int speedConst){
        Random rand = new Random();
        if (!isLetter){
            int index=rand.nextInt(maxIndex);
            if(index<25) type=true;
            else type=false;
            image= Bitmap.createBitmap(res, index*res.getHeight(), 0, res.getHeight(), res.getHeight());
            width=image.getWidth();
            height=image.getHeight();
        }
        else{
            this.isLetter=isLetter;
            image=res;
            width=image.getWidth();
            height=image.getHeight();
        }

        y=-2*height;
        dy=(rand.nextInt(dy_diversity)+speedConst)*level*MainActivity.screenHeight/dy_divider;
        x= rand.nextInt(MainActivity.screenWidth-2*width)+width;
    }

    public Letter (Bitmap res, boolean type, int level, int dy_diversity, int dy_divider, int speedConst){
        int index;
        Random rand = new Random();
        if (type)
            index=rand.nextInt(25);
        else
            index=25+rand.nextInt(10);
        this.type=type;
        image= Bitmap.createBitmap(res, index*res.getHeight(), 0, res.getHeight(), res.getHeight());
        width=image.getWidth();
        height=image.getHeight();
        y=-2*height;
        dy=(rand.nextInt(dy_diversity)+speedConst)*level*MainActivity.screenHeight/dy_divider;
        x= rand.nextInt(MainActivity.screenWidth-2*width)+width;
    }

    public void update(){
        prevY=y;
        y=y+dy;
    }
    public void draw(Canvas canvas){canvas.drawBitmap(image, x, y, null);}

    public boolean getType(){return type;}

    public boolean getIsLetter(){return isLetter;}

    public int getPrevY() {return prevY;}
}
