package com.sztokrotki.gloskuj.game.cups;

import android.graphics.Bitmap;
import android.graphics.Rect;

abstract class Object {

    protected int x;
    protected int y;
    protected int dy;
    protected int width;
    protected int height;
    protected Bitmap image;


    public int getY(){return y;  }

    public int getHeight(){return height;  }

    public Rect getRect(){return new Rect(x, y, x+width, y+height);  }
}