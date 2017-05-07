package com.sztokrotki.gloskuj;

import android.graphics.Point;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.MenuItem;

import com.sztokrotki.gloskuj.game.MenuGame_fragment;
import com.sztokrotki.gloskuj.main.MenuMain_fragment;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private int frame_layout;
    private NavigationView navigationView;;
    private DrawerLayout drawer;
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

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        fragmentManager.beginTransaction().replace(frame_layout, fragmentHashMap.get(id)).commit();
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    private void initClassVariables(){
        fragmentManager = getSupportFragmentManager();
        frame_layout = R.id.Frame_Layout;
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView.setNavigationItemSelectedListener(this);
        fragmentHashMap = new HashMap<>();
        fragmentHashMap.put(R.id.nav_opcja1, new MenuGame_fragment());
        fragmentHashMap.put(R.id.nav_opcja2, new MenuMain_fragment());
    }

}
