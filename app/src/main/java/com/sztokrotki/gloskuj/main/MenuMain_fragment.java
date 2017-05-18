package com.sztokrotki.gloskuj.main;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
            case R.id.Button_01: {
                getActivity()
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .replace(frame_layout, new MenuGame_fragment()).commit();
                break;
            }
            case R.id.Button_03: {
                String urlString="https://tinyurl.com/nagidaniel";
                Intent intent=new Intent(Intent.ACTION_VIEW, Uri.parse(urlString));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setPackage("com.android.chrome");
                try {
                    getContext().startActivity(intent);
                } catch (ActivityNotFoundException ex) {
                    intent.setPackage(null);
                    getContext().startActivity(intent);
                }
                break;
            }
        }
    }

    private void initClassVariables(){
        textView = (TextView) view.findViewById(R.id.textview);
        frame_layout = R.id.Frame_Layout;
        Button button_MenuGame = (Button) view.findViewById(R.id.Button_01);
        button_MenuGame.setOnClickListener(this);
        Button button_Mystery = (Button) view.findViewById(R.id.Button_03);
        button_Mystery.setOnClickListener(this);
    }
}
