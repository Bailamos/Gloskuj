package com.sztokrotki.gloskuj.game.cups;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.Display;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.sztokrotki.gloskuj.MainActivity;
import com.sztokrotki.gloskuj.R;

import java.util.ArrayList;


public class Cups extends SurfaceView implements SurfaceHolder.Callback{

    private CupsThread thread;
    private Cup cup;
    private ArrayList<Letter> letters;
    private int score;
    private int level;
    private boolean gameType;
    private final int textSize=MainActivity.screenHeight/20;
    private Paint scorePaint;

    //settings
    private final int spawnConst=5;
    private final int scorePerLetter=100;
    private final int scorePerLevel=1000;
    private final int dy_diversity=4;
    private final int dy_divider=500;
    private final int maxIndex=2;

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
        score=0;
        level=1;
        gameType=true;
        scorePaint = new Paint();
        scorePaint.setTextSize(textSize);
        scorePaint.setColor(Color.RED);
        cup= new Cup(BitmapFactory.decodeResource(getResources(), R.drawable.cup));
        letters=new ArrayList<>();
        letters.add(new Letter(BitmapFactory.decodeResource(getResources(), R.drawable.bp), maxIndex, level, dy_diversity, dy_divider));
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
        for (int i = 0; i < letters.size(); i++) {
            //Kazdy element listy jest odswiezany i sprawdzany pod katem kolizji oraz dalszej przydatnosci.
            letters.get(i).update();

            if (letters.get(i).getY() > MainActivity.screenHeight + letters.get(i).getHeight()) {
                //Gdy pietro wychodzi poza widoczny obszar jest usuwane
                letters.remove(i);
            }
            if (letters.get(letters.size()-1).getY() > (spawnConst/level)*letters.get(letters.size()-1).getHeight()) {
                letters.add(new Letter(BitmapFactory.decodeResource(getResources(), R.drawable.bp), maxIndex, level, dy_diversity, dy_divider));
            }

            if(Rect.intersects(letters.get(i).getRect(), cup.getRect())&&
                    cup.getY()+0.2*cup.getHeight() > letters.get(i).getY()+letters.get(i).getHeight()){
                if(letters.get(i).getType()==gameType){
                    score=score+scorePerLetter;
                }
                else{
                    score=score-scorePerLetter;
                }
                letters.remove(i);
                System.out.println(score);
            }
        }

        if((double)score/scorePerLevel>=level) level++;
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        // zmienne sluzace do przeskalowania obrazkow tak, aby mialy jednakowa wielkosc na kazdym urzadzeniu
        final float scaleX = getWidth() / (MainActivity.screenWidth * 1.f);
        final float scaleY = getHeight() / (MainActivity.screenHeight * 1.f);

        if (canvas != null) {
            //po narysowaniu elementow chcemy przywrocic stan poczatkowy aby uniknac skalowania w nieskonczonosc
            //final int stock = canvas.save();
            //canvas.scale(scaleX, scaleY);

            cup.draw(canvas);

            for(Letter letter: letters){
                letter.draw(canvas);
            }

            canvas.drawText("Wynik: "+Integer.toString(score), (float)0.5*MainActivity.screenWidth, (float)1.5*textSize, scorePaint);

            //canvas.restoreToCount(stock);
        }
    }
}
