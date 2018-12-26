package com.example.android.makershunt.my_submission_screen;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.android.makershunt.Event;
import com.example.android.makershunt.R;
import com.example.android.makershunt.Task;

import java.util.ArrayList;
import java.util.List;

public class SubmittedTaskAdapter extends ArrayAdapter<Task> {

    Context context;
    ArrayList<Task> tasks ;

    public SubmittedTaskAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Task> tasks) {
        super(context, resource, tasks);
        this.context = context;
        this.tasks = tasks;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if(listItem == null){
            listItem = LayoutInflater.from(context).inflate(R.layout.selected_task_list_item,parent,false);

            Task task = tasks.get(position);

            TextView taskTitle = listItem.findViewById(R.id.txt_task_submitted);
            TextView taskPoints = listItem.findViewById(R.id.txt_submission_points);

            taskTitle.setText(task.getTitle());
            taskPoints.setText(""+task.getPoints());

            Log.v("Adapter: ", "Setting Value -> " + task.getTitle());
        }
        return listItem;
    }
}
