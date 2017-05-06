package com.sztokrotki.gloskuj.game.cups;


import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.sztokrotki.gloskuj.MainActivity;

import java.util.Random;

public class Letter extends Object {

    private boolean type;
    private int dy_diversity=5;
    private int level=1;

    public Letter(Bitmap res){
        Random rand = new Random();
        image= res;
        width=image.getWidth();
        height=image.getHeight();
        y=-2*height;
        dy=rand.nextInt(dy_diversity)*level*MainActivity.screenHeight/500;
        x= rand.nextInt(MainActivity.screenWidth-2*width)+width;
        type=rand.nextBoolean();
    }

    public void update(){

        y=y+dy;
    }
    public void draw(Canvas canvas){canvas.drawBitmap(image, x, y, null);}

    public int getLevel(){return level;  }

    public void setLevel(int lvl){ level=lvl;  }
}
