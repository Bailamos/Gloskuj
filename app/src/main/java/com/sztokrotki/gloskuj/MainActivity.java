package com.sztokrotki.gloskuj;

import android.graphics.Point;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;

import com.sztokrotki.gloskuj.main.MenuMain_fragment;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity{

    private int frame_layout;
    private FragmentManager fragmentManager;
    private HashMap<Integer, Fragment> fragmentHashMap;

    public static int screenWidth;
    public static int screenHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initClassVariables();
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        screenWidth = size.x;
        screenHeight = size.y;

        fragmentManager.beginTransaction().replace(frame_layout, new MenuMain_fragment()).commit();
    }


    private void initClassVariables(){
        fragmentManager = getSupportFragmentManager();
        frame_layout = R.id.Frame_Layout;
    }

}
