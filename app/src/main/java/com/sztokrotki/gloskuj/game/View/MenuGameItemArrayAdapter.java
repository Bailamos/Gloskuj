package com.sztokrotki.gloskuj.game.View;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.sztokrotki.gloskuj.R;

import java.util.ArrayList;
import java.util.List;

public class MenuGameItemArrayAdapter extends ArrayAdapter<MenuGameItem> {
    private Context context;
    private List<MenuGameItem> menuGameItems;

    public MenuGameItemArrayAdapter(Context context, int resource, ArrayList<MenuGameItem> objects) {
        super(context, resource, objects);
        this.context = context;
        this.menuGameItems = objects;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        MenuGameItem menuGameItem = menuGameItems.get(position);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.menugame_item, null);

        TextView name = (TextView) view.findViewById(R.id.nazwa_value);
        ImageView overview = (ImageView) view.findViewById(R.id.image);

        name.setText("      ".concat(menuGameItem.gameName));
        overview.setImageResource((menuGameItem.gameName.equals("Kubek")) ? R.drawable.cups_cup : R.mipmap.ic_launcher);

        return view;
    }
}
