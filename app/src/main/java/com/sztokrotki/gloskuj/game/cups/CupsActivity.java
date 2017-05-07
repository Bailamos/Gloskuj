package com.sztokrotki.gloskuj.game.cups;

import android.app.Activity;
import android.content.Context;
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

/**
 * Klasa tworzona po uruchomieniu aplikacji.
 * Dziedziczy po klasie Activity oraz implementuje interfejs SensorEventListener.
 * @author Krystian Szutowicz
 */
public class CupsActivity extends Activity implements SensorEventListener {

    /**
     * Wartosc wychylenia zyroskopu.
     * Pole static - widoczne dla wszystkich klas.
     */
    public static float gyroX;
    private MediaPlayer music;
    private SoundPool soundPool;

    /**
     * Sczytywanie wartosci wychylenia zyroskopu.
     * @param event Obiekt otrzymywany z zyroskopu do dalszego przetwarzania.
     */
    @Override
    public void onSensorChanged(SensorEvent event) {

        gyroX = event.values[0];
    }

    /**
     * Nadpisana metoda w celu implementacji interfejsu SensorEventListener.
     * Nie jest wykorzystywana.
     * @param sensor Parametr nie wykorzystywany.
     * @param accuracy Parametr nie wykorzystywany.
     */
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {}

    /**
     * Podstawowa metoda klasy Activity.
     * Uruchamiana po stworzeniu obiektu tej klasy.
     * @param savedInstanceState Obiekt klasy Bundle.
     */
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

        //wyłączenie tytułu w oknie
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //włączenie tryby fullscreen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //obsługa żyroskopu
        SensorManager mng = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        Sensor acc = mng.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mng.registerListener(this, acc, SensorManager.SENSOR_DELAY_NORMAL);

        //ropoczecie gry
        setContentView(new Cups(this, soundPool, soundIds));
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        music.stop();
        soundPool.release();
    }
}
