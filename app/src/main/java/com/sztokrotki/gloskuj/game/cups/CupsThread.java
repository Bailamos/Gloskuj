package com.sztokrotki.gloskuj.game.cups;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

class CupsThread extends Thread{

    private SurfaceHolder surfaceHolder;
    private Cups game;
    private boolean running;
    public static Canvas canvas;

    public CupsThread(SurfaceHolder surfaceHolder, Cups game )
    {
        super();
        this.game=game;
        this.surfaceHolder=surfaceHolder;
    }

    @Override
    public void run()
    {
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

    public void setRunning(boolean running)
    {

        this.running=running;
    }
}
