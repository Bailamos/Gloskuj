package com.sztokrotki.gloskuj.game.cups;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.media.SoundPool;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.sztokrotki.gloskuj.MainActivity;
import com.sztokrotki.gloskuj.R;

import java.util.ArrayList;
import java.util.Random;


class Cups extends SurfaceView implements SurfaceHolder.Callback{

    //settings
    private final int textSize=MainActivity.screenHeight/20;
    private final int spawnConst=4; //smaller = more letters
    private final int scorePerLetter=10;
    private final int scorePerLevel=100;
    private final int dy_diversity=2;  //number of letter's speed per level
    private final int dy_divider=1800;   //smaller = faster letters
    private final int speedConst=5;
    private final int maxIndex=35;       //number of letters in sprite
    private final int gyroSensitivity=5;

    private Context context;
    public CupsThread thread;
    private Cup cup;
    private ArrayList<Letter> letters;
    private Random rand;

    private int score;
    private int level;
    private boolean gameType;

    private Paint scorePaint;
    private Bitmap background;
    private Rect restartButton;
    private Rect exitButton;
    private SoundPool soundPool;

    private int soundIds[] = new int[10];
    private int frames;

    public Cups (Context context, SoundPool soundPool, int[] soundIds)
    {
        super(context);
        this.context=context;
        getHolder().addCallback(this);
        setFocusable(true);
        this.soundPool=soundPool;
        this.soundIds=soundIds;
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        rand = new Random();
        score=0;
        level=1;
        frames=0;
        gameType=rand.nextBoolean();
        background=BitmapFactory.decodeResource(getResources(), R.drawable.cups_background);
        scorePaint = new Paint();
        scorePaint.setTextSize(textSize);
        scorePaint.setColor(Color.RED);
        cup= new Cup(BitmapFactory.decodeResource(getResources(), R.drawable.cups_cup),
                BitmapFactory.decodeResource(getResources(), R.drawable.cups_heart), gyroSensitivity, textSize);
        letters=new ArrayList<>();
        letters.add(new Letter(BitmapFactory.decodeResource(getResources(), R.drawable.cups_sprite), false, maxIndex, level, dy_diversity, dy_divider, speedConst));
        //inicjalizacja watku glownego
        thread=new CupsThread(getHolder(), this);
        thread.setRunning(true);
        thread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) { }

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

    public boolean onTouchEvent(MotionEvent event) {

        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (!cup.lives.isAlive()) {
                if(restartButton.contains((int) event.getX(), (int) event.getY())){
                    restart();
                }
                if(exitButton.contains((int) event.getX(), (int) event.getY())){
                    ((Activity) context).finish();
                }
            }
        }
        return super.onTouchEvent(event);
    }

    private void restart(){
        score=0;
        level=1;
        frames=0;
        gameType=rand.nextBoolean();
        letters.clear();
        letters.add(new Letter(BitmapFactory.decodeResource(getResources(), R.drawable.cups_sprite), false, maxIndex, level, dy_diversity, dy_divider, speedConst));
        cup.lives.addLive();
        cup.lives.addLive();
        cup.lives.addLive();
    }

    private void levelUp(){
        level++;
        scorePaint.setARGB(255, rand.nextInt(256), rand.nextInt(256), rand.nextInt(256));
        letters.add(new Letter(BitmapFactory.decodeResource(getResources(), R.drawable.cups_heart), true, maxIndex, level, dy_diversity, dy_divider, speedConst));
        soundPool.play(soundIds[2], 1, 1, 1, 0, 1);
    }

    public void update() {
        if (cup.lives.isAlive()) {
            cup.update();
            for (int i = 0; i < letters.size(); i++) {
                letters.get(i).update();

                if (letters.get(i).getY() > MainActivity.screenHeight + letters.get(i).getHeight()) {
                    letters.remove(i);
                }
                if (letters.get(letters.size()-1).getY() > (spawnConst)*letters.get(letters.size()-1).getHeight()) {
                    letters.add(new Letter(BitmapFactory.decodeResource(getResources(), R.drawable.cups_sprite), false, maxIndex, level, dy_diversity, dy_divider, speedConst));
                }

                if(Rect.intersects(letters.get(i).getRect(), cup.getRect())&& //TODO przerobic drugi warunek tak zeby lapalo tez pomiedy FPSami
                        cup.getY()+0.2*cup.getHeight() > letters.get(i).getY()+letters.get(i).getHeight()){
                    if(!letters.get(i).getIsLetter()){
                        if(letters.get(i).getType()==gameType){
                            score=score+scorePerLetter;
                            if((double)score/scorePerLevel>=level) levelUp();
                            else soundPool.play(soundIds[0], 1, 1, 1, 0, 1);
                        }
                        else{
                            cup.lives.removeLive();
                            soundPool.play(soundIds[1], 1, 1, 1, 0, 1);
                        }

                    }
                    else{
                        cup.lives.addLive();
                        soundPool.play(soundIds[4], 1, 1, 1, 0, 1);
                    }
                    letters.remove(i);
                }
            }
        }

    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);

        if (canvas != null) {
            canvas.drawBitmap(background, 0, 0, null);
            cup.draw(canvas);
            cup.lives.draw(canvas);

            for(Letter letter: letters){
                letter.draw(canvas);
            }

            if(frames<150){
                frames++;
                if(gameType)
                    canvas.drawText("Łap dźwięczne!", (float)0.2*MainActivity.screenWidth,
                            (float)0.5*MainActivity.screenHeight, scorePaint);
                else
                    canvas.drawText("Łap bezdźwięczne!", (float)0.12*MainActivity.screenWidth,
                            (float)0.5*MainActivity.screenHeight, scorePaint);
            }
            if (cup.lives.isAlive()){
                canvas.drawText("Wynik: "+Integer.toString(score), (float)0.5*MainActivity.screenWidth, (float)1.5*textSize, scorePaint);
                canvas.drawText("Poziom: "+Integer.toString(level), (float)0.01*MainActivity.screenWidth, (float)1.5*textSize, scorePaint);
            }
            else
            {
                canvas.drawText("Koniec gry!", (float)0.3*MainActivity.screenWidth, MainActivity.screenHeight/2-3*textSize, scorePaint);
                canvas.drawText("Wynik: "+Integer.toString(score), (float)0.3*MainActivity.screenWidth, MainActivity.screenHeight/2-textSize, scorePaint);
                canvas.drawText("Poziom: "+Integer.toString(level), (float)0.3*MainActivity.screenWidth, MainActivity.screenHeight/2, scorePaint);

                restartButton= new Rect((int)(0.1*MainActivity.screenWidth),(MainActivity.screenHeight/2+3*textSize),
                        (int)(0.6*MainActivity.screenWidth),(int)(MainActivity.screenHeight/2+4.25*textSize));
                exitButton= new Rect((int)(0.7*MainActivity.screenWidth),(MainActivity.screenHeight/2+3*textSize),
                        (int)(0.95*MainActivity.screenWidth),(int)(MainActivity.screenHeight/2+4.25*textSize));
                canvas.drawRect(restartButton, scorePaint);
                canvas.drawRect(exitButton, scorePaint);
                scorePaint.setColor(Color.BLACK);
                canvas.drawText("Jeszcze raz!", (float)0.1*MainActivity.screenWidth, MainActivity.screenHeight/2+4*textSize, scorePaint);
                canvas.drawText("Wyjdz", (float)0.7*MainActivity.screenWidth, MainActivity.screenHeight/2+4*textSize, scorePaint);
                scorePaint.setColor(Color.RED);
            }
        }
    }
}
