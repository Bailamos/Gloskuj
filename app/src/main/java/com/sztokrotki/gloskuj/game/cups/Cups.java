package com.sztokrotki.gloskuj.game.cups;

import android.content.Context;
import android.graphics.Canvas;
import android.view.SurfaceHolder;
import android.view.SurfaceView;


public class Cups extends SurfaceView implements SurfaceHolder.Callback{

    private CupsThread thread;

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

    public void update() {}

    @Override
    public void draw(Canvas canvas){}
}
