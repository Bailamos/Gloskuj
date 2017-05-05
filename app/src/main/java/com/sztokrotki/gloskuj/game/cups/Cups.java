package com.sztokrotki.gloskuj.game.cups;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.view.Display;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.sztokrotki.gloskuj.R;


public class Cups extends SurfaceView implements SurfaceHolder.Callback{

    protected static int width; //TODO
    protected static int height;

    private CupsThread thread;
    private Cup cup;

    public Cups (Context context)
    {
        super(context);
        getHolder().addCallback(this);
        thread=new CupsThread(getHolder(), this);
        setFocusable(true);

    }

    /**
     * Metoda wykonywana po utworzeniu panelu.
     * Ustawienie warunkow poczatkowych oraz inicjalizacja watku glownego.
     * @param holder
     */
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        cup= new Cup(BitmapFactory.decodeResource(getResources(), R.drawable.cup));
        //inicjalizacja watku glownego
        thread.setRunning(true);
        thread.start();
    }

    /**
     * Nadpisana metoda w celu implementacji interfejsu SurfaceHolder.Callback.
     * Nie jest wykorzystywana.
     * @param holder Parametr nie wykorzystywany.
     * @param format Parametr nie wykorzystywany.
     * @param width Parametr nie wykorzystywany.
     * @param height Parametr nie wykorzystywany.
     */
    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) { }

    /**
     * Metoda wykonywana w razie zniszczenia panelu.
     * Wykonuje restart watku glownego.
     * @param holder Uchwyt panelu.
     */
    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

        boolean retry = true;
        while(retry)
        {
            try{thread.setRunning(false);
                thread.join();
                retry = false;
            }catch(InterruptedException e){e.printStackTrace();}
        }
    }

    public void update() {
        cup.update();
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        // zmienne sluzace do przeskalowania obrazkow tak, aby mialy jednakowa wielkosc na kazdym urzadzeniu
        final float scaleX = getWidth() / (width * 1.f);
        final float scaleY = getHeight() / (height * 1.f);

        if (canvas != null) {
            //po narysowaniu elementow chcemy przywrocic stan poczatkowy aby uniknac skalowania w nieskonczonosc
            //final int stock = canvas.save();
            //canvas.scale(scaleX, scaleY);

            cup.draw(canvas);

            //canvas.restoreToCount(stock);
        }
    }
}
