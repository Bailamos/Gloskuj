package com.sztokrotki.gloskuj.game;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.sztokrotki.gloskuj.R;
import com.sztokrotki.gloskuj.game.View.MenuGameItem;
import com.sztokrotki.gloskuj.game.View.MenuGameItemArrayAdapter;
import com.sztokrotki.gloskuj.game.cups.CupsActivity;
import com.sztokrotki.gloskuj.game.decisions.Decisions;

import java.util.ArrayList;

public class MenuGame_fragment extends ListFragment implements AdapterView.OnItemClickListener {

    private View view;
    private ListView listView;

    private ArrayList<MenuGameItem> menuGameItems = new ArrayList<>();
    private ArrayAdapter<MenuGameItem> adapter;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.menugame_layout, container, false);

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        //TODO jakos mądrzej to ogarnąć
        menuGameItems.add(new MenuGameItem("Gra 1"));
        menuGameItems.add(new MenuGameItem("Gra 2"));
        adapter = new MenuGameItemArrayAdapter(getActivity(), 0, menuGameItems);

        setListAdapter(adapter);
        getListView().setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
        //TODO jakos mądrzej to ogarnąć
        MenuGameItem menuItem = menuGameItems.get(position);
        switch(menuItem.gameName){
            case "Gra 1":
                startActivity(new Intent(getActivity().getApplicationContext(), CupsActivity.class));
            case "Gra 2":
                startActivity(new Intent(getActivity().getApplicationContext(), Decisions.class));
        }

    }


}
