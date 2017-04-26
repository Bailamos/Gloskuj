package com.sztokrotki.gloskuj.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sztokrotki.gloskuj.R;

public class MenuMain_fragment extends Fragment {

    private View view;

    private TextView textView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.menumain_layout, container, false);

        textView = (TextView) view.findViewById(R.id.textview);

        return view;
    }

}
