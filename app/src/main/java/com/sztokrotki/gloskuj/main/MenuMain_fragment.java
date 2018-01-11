package com.sztokrotki.gloskuj.main;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.sztokrotki.gloskuj.R;
import com.sztokrotki.gloskuj.game.MenuGame_fragment;

public class MenuMain_fragment extends Fragment implements View.OnClickListener {

    private View view;

    private TextView textView;
    private int frame_layout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.menumain_layout, container, false);
        initClassVariables();

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
           case R.id.goToGames: {
               getActivity()
                       .getSupportFragmentManager()
                        .beginTransaction()
                        .replace(frame_layout, new MenuGame_fragment()).commit();
               break;
            }
           case R.id.exitApp: {
               AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
               builder.setMessage("Czy chcesz wyjść?")
                       .setPositiveButton("Tak", new DialogInterface.OnClickListener() {
                           public void onClick(DialogInterface dialog, int id) {
                               System.exit(0);
                           }
                       })
                       .setNegativeButton("Nie", new DialogInterface.OnClickListener() {
                           public void onClick(DialogInterface dialog, int id) {

                           }
                       });
               builder.create().show();
           }
       }
    }

    private void initClassVariables(){
        textView = (TextView) view.findViewById(R.id.textview);
        frame_layout = R.id.Frame_Layout;
        Button button_MenuGame = (Button) view.findViewById(R.id.goToGames);
        button_MenuGame.setOnClickListener(this);
        Button button_exitApp = (Button) view.findViewById(R.id.exitApp);
        button_exitApp.setOnClickListener(this);
    }
}
