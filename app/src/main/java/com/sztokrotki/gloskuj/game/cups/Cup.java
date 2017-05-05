package com.sztokrotki.gloskuj.game.cups;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.sztokrotki.gloskuj.MainActivity;

public class Cup extends Object {

    public Cup(Bitmap res){

        image=res;
        width=image.getWidth();
        height=image.getHeight();
        x=MainActivity.screenWidth/2;
        y=MainActivity.screenHeight-height;//-getHeight()/20;
    }

    public void update(){

        //Sterowanie zyroskopem
        if (CupsActivity.gyroX > 0) {
            x = x + 15;
        }
        if (CupsActivity.gyroX < 0) {
            x = x - 15;
        }

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
