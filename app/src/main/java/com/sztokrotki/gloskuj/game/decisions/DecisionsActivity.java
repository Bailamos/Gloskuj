package com.sztokrotki.gloskuj.game.decisions;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.sztokrotki.gloskuj.R;

import java.util.ArrayList;
import java.util.Random;

public class DecisionsActivity extends Activity implements ChooseGameKindFragment.OnFragmentInteractionListener{

    private FragmentManager fm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_decisions);

        fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(R.id.placeholder, new ChooseGameKindFragment());
        ft.commit();
    }

    private void startGame(boolean azGameEnabled, String message, String randomWordsOrder){
        FragmentTransaction ft = fm.beginTransaction();
        GameFragment gameFragment = GameFragment.newInstance(azGameEnabled, message, randomWordsOrder);
        ft.replace(R.id.placeholder, gameFragment);
        ft.commit();
    }

    @Override
    public void onFragmentInteraction(boolean azGameEnabled, String message, String randomWordsOrder) {
        startGame(azGameEnabled, message, randomWordsOrder);
    }
}
