package com.sztokrotki.gloskuj.game.cups;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

public class CupsThread extends Thread{
    /** Panel, na ktorym rysowana jest gra. */
    private SurfaceHolder surfaceHolder;
    /** Obiekt gry */
    private Cups game;
    /** Zmienna odpowiedzialna za start petli. */
    private boolean running;
    /** Canvas, na ktorych rysowana jest gra. */
    public static Canvas canvas;

    /**
     * Konstruktor klasy Petla.
     * @param surfaceHolder Panel, na ktorym rysowana jest gra.
     * @param game Obiekt gry.
     */
    public CupsThread(SurfaceHolder surfaceHolder, Cups game )
    {
        super();
        this.game=game;
        this.surfaceHolder=surfaceHolder;
    }

    /**
     * Podstawowa metoda klasy dziedziczacej po klasie Thread.
     * Inicjowanie gry, obsluga bledow z tym zwiazanych.
     * Ograniczanie liczby wyswietlanych klatek na sekunde.
     */
    @Override
    public void run()
    {
        //zmienne służące do ograniczenia liczby klatek na sekundę do 30
        long startTime;
        long timeMillis;
        long waitTime;
        int frameCount =0;
        int FPS = 30;
        long targetTime = 1000/ FPS;

        //właściwa pętla gry
        while(running) {
            startTime = System.nanoTime();
            canvas = null;

            try {
                canvas = this.surfaceHolder.lockCanvas();
                synchronized (surfaceHolder) {
                    //dwie linijki, przez które wszystko działa
                    this.game.update();
                    this.game.draw(canvas);
                }
            } catch (Exception e) {
            }
            finally{
                if(canvas!=null)
                {
                    try {
                        surfaceHolder.unlockCanvasAndPost(canvas);
                    }
                    catch(Exception e){e.printStackTrace();}
                }
            }



            //ograniczenie liczby klatek na sekundę do 30
            timeMillis = (System.nanoTime() - startTime) / 1000000;
            waitTime = targetTime-timeMillis;

            try{
                this.sleep(waitTime);
            }catch(Exception e){}

            frameCount++;
            if(frameCount == FPS)
            {
                frameCount =0;
            }
        }
    }

    /**
     * Setter dla pola running.
     * @param czy Parametr z wartoscia dla pola running.
     */
    public void setRunning(boolean czy)
    {
        running=czy;
    }
}
