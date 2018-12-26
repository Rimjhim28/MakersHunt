package com.example.android.makershunt.leader_screen;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.android.makershunt.Points;
import com.example.android.makershunt.R;

import java.util.ArrayList;
import java.util.List;

public class LeaderAdapter extends ArrayAdapter<Points> {

    Context context;
    ArrayList<Points> points;

    public LeaderAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Points> objects) {
        super(context, resource, objects);
        points = objects;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if(listItem == null){
            listItem = LayoutInflater.from(context).inflate(R.layout.list_item_points,parent,false);

            Points point = points.get(position);

            TextView name = listItem.findViewById(R.id.txt_user_name_leader);
            TextView points = listItem.findViewById(R.id.txt_points_2_leader);
            name.setText(point.getName());
            points.setText(""+point.getPoints());
        }
        return listItem;
    }
}
