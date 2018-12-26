package com.example.android.makershunt;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class EventAdapter extends ArrayAdapter<Event> {

    Context context;
    ArrayList<Event> events;

    EventAdapter(@Nullable Context context, ArrayList<Event> events){
        super(context, 0, events);
        this.context = context;
        this.events = events;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if(listItem == null){
            listItem = LayoutInflater.from(context).inflate(R.layout.list_item,parent,false);
        }

        Event event = events.get(position);

        TextView week = listItem.findViewById(R.id.txt_week);
        TextView txtStartDate = listItem.findViewById(R.id.txt_start_date);
        TextView txtEndDate = listItem.findViewById(R.id.txt_end_date);
        Log.v("Event Adapter", "Value of Position" + position);
        if(position == 0 || position == 2 || position == 4 || position == 6)
            week.setBackgroundResource(R.color.colorPrimaryDark);
        Log.v("Adapter:: ", "Value = " + event.getWeek());
        week.setText(event.getWeek());
        txtStartDate.setText(event.getStartDate());
        txtEndDate.setText(event.getEndDate());
        return listItem;
    }
}
