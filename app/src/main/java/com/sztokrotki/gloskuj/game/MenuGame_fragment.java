package com.sztokrotki.gloskuj.game;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.sztokrotki.gloskuj.R;
import com.sztokrotki.gloskuj.game.View.MenuGameItem;
import com.sztokrotki.gloskuj.game.View.MenuGameItemArrayAdapter;
import com.sztokrotki.gloskuj.game.cups.CupsActivity;
import com.sztokrotki.gloskuj.game.decisions.DecisionsActivity;

import java.util.ArrayList;

//public class MenuGame_fragment extends ListFragment implements AdapterView.OnItemClickListener {
//
//    private View view;
//    private ListView listView;
//
//    private ArrayList<MenuGameItem> menuGameItems = new ArrayList<>();
//    private ArrayAdapter<MenuGameItem> adapter;
//
//    @Nullable
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        view = inflater.inflate(R.layout.menugame_layout, container, false);
//
//        return view;
//    }
//
//    @Override
//    public void onActivityCreated(Bundle savedInstanceState){
//        super.onActivityCreated(savedInstanceState);
//        //TODO jakos mądrzej to ogarnąć
//        menuGameItems.add(new MenuGameItem("Kubek", CupsActivity.class));
//        menuGameItems.add(new MenuGameItem("Wybory", DecisionsActivity.class));
//        adapter = new MenuGameItemArrayAdapter(getActivity(), 0, menuGameItems);
//
//        setListAdapter(adapter);
//        getListView().setOnItemClickListener(this);
//    }
//
//    @Override
//    public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
//        MenuGameItem menuItem = menuGameItems.get(position);
//        startActivity(new Intent(getActivity().getApplicationContext(), menuItem.activity));
//    }
//}
public class MenuGame_fragment extends Fragment implements View.OnClickListener {

    private View view;

    private int frame_layout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.menugame_layout, container, false);
        initClassVariables();

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cup_button: {
                startActivity(new Intent(getActivity().getApplicationContext(),CupsActivity.class));
                break;
            }
            case R.id.choice_button: {
                startActivity(new Intent(getActivity().getApplicationContext(),DecisionsActivity.class));
                break;
            }
        }
    }

    private void initClassVariables(){
        frame_layout = R.id.Frame_Layout;
        Button button_Cup = (Button) view.findViewById(R.id.cup_button);
        button_Cup.setOnClickListener(this);
        Button button_Choice = (Button) view.findViewById(R.id.choice_button);
        button_Choice.setOnClickListener(this);
    }
}