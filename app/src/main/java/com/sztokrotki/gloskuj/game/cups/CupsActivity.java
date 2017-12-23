package com.sztokrotki.gloskuj.game.cups;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.sztokrotki.gloskuj.R;

public class CupsActivity extends Activity implements SensorEventListener {

    public static float gyroX;
    private MediaPlayer music;
    private SoundPool soundPool;
    private Cups cups;

    @Override
    public void onSensorChanged(SensorEvent event) {

        gyroX = event.values[0];
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {}

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        music = MediaPlayer.create(this, R.raw.cups_music);
        music.setLooping(true);
        music.start();

        soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
        int soundIds[] = new int[5];
        soundIds[0] = soundPool.load(this, R.raw.cups_success, 1);
        soundIds[1] = soundPool.load(this, R.raw.cups_fail, 1);
        soundIds[2] = soundPool.load(this, R.raw.cups_levelup, 1);
        soundIds[3] = soundPool.load(this, R.raw.cups_gameover, 1);
        soundIds[4] = soundPool.load(this, R.raw.cups_life, 1);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        //obsługa żyroskopu
        SensorManager mng = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        Sensor acc = mng.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mng.registerListener(this, acc, SensorManager.SENSOR_DELAY_NORMAL);

        //ropoczecie gry
        cups=new Cups(this, soundPool, soundIds);
        setContentView(cups);
    }

    @Override
    protected void onResume(){
        super.onResume();
        music.start();
    }

    @Override
    protected void onPause(){
        super.onPause();
        music.pause();
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        soundPool.release();
        music.release();
    }
}
