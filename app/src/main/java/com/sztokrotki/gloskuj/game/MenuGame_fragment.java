package com.sztokrotki.gloskuj.game;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.sztokrotki.gloskuj.R;
import com.sztokrotki.gloskuj.game.cups.CupsActivity;

public class MenuGame_fragment extends Fragment{

    private View view;

    private TextView textView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.menugame_layout, container, false);

        textView = (TextView) view.findViewById(R.id.textview);
        Button button = (Button) view.findViewById(R.id.Graj);
        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(getActivity().getApplicationContext(), CupsActivity.class));
            }
        });
        return view;
    }
}
