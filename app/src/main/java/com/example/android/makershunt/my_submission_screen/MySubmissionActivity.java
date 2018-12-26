package com.example.android.makershunt.my_submission_screen;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.makershunt.Main2Activity;
import com.example.android.makershunt.R;
import com.example.android.makershunt.Task;
import com.example.android.makershunt.TaskView;
import com.example.android.makershunt.User;
import com.example.android.makershunt.Utils;
import com.example.android.makershunt.description_screen.Description2Activity;
import com.example.android.makershunt.submit_screen.Submit2Activity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import static android.view.View.GONE;

public class MySubmissionActivity extends AppCompatActivity {

    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference ref = database.getReference();
    ProgressBar progressBar;
    boolean isEditable;
    TextView txtNoSubmissions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_submission);
        progressBar = findViewById(R.id.progress_bar);
        txtNoSubmissions = findViewById(R.id.txt_no_submissions);
        showSearchResults();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        long date = System.currentTimeMillis();
        Log.v("Current Time", "Value -> " +date);
    }

    void showSearchResults(){
        final ArrayList<Task> tasks  = new ArrayList<>();
        ref.child("users").child(Utils.userName).addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                Iterable<DataSnapshot> snapshotIterator = dataSnapshot.getChildren();
                Iterator<DataSnapshot> iterator = snapshotIterator.iterator();
                while (iterator.hasNext()) {
                    Task task = iterator.next().getValue(Task.class);
                    tasks.add(task);
                    Log.v("Activity: ", "Adding Value -> " + tasks.get(0).getPoints());
                }
                if(tasks.size() == 0)
                    txtNoSubmissions.setAlpha(1);

                SubmittedTaskAdapter adapter = new SubmittedTaskAdapter(MySubmissionActivity.this, 0,tasks);
                ListView listView = findViewById(R.id.submission_list_view);
                progressBar.setAlpha(0);
                listView.setAdapter(adapter);

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        //getTaskTime(i+1);
                        showInfo(tasks.get(i), (i+1));
                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("Not able to read data", "Failed to read value.", error.toException());
            }
        });
    }

    void showInfo(final Task task, final int key){

            String taskKey = "task_" + key;
            ref.child("tasks").child(taskKey).addValueEventListener(new ValueEventListener() {

                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Log.v("Submission Activity","Current Time -> " + System.currentTimeMillis());
                    long startDate =  Long.parseLong(dataSnapshot.child("start_date").getValue().toString());
                    long endDate =  Long.parseLong(dataSnapshot.child("end_date").getValue().toString());
                    long currentTime = System.currentTimeMillis();
                    isEditable = currentTime > startDate && currentTime < endDate;
                    Log.v("Submission Activity", "Is Editable CALLED ");
                    Log.v("Submission Activity", "SETTING VALUE Is Editable -> " + isEditable);
                    showDialogBox(task, key);
                }

                @Override
                public void onCancelled(DatabaseError error) {
                    // Failed to read value
                    Log.w("Not able to read data", "Failed to read value.", error.toException());
                }
            });
    }

    public void showDialogBox(final Task task, final int key){
        AlertDialog.Builder builder = new AlertDialog.Builder(MySubmissionActivity.this);
        builder.setTitle("Task Description")
                .setMessage(task.getDescription());

        Log.v("Submission Activity", "Is Editable -> " + isEditable);
        if(isEditable) {
            builder.setPositiveButton("Edit", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    Intent intent = new Intent(MySubmissionActivity.this, EditTaskActivity.class);
                    intent.putExtra("task_key", key);
                    intent.putExtra("task_title", task.getTitle());
                    intent.putExtra("task description", task.getDescription());
                    Log.v("DESCRIPTION giving:: ", task.getDescription());
                    intent.putExtra("task_link", task.getLink());
                    startActivity(intent);
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                }
            });
        }

        builder.setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(MySubmissionActivity.this, Main2Activity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    @Override
    public boolean onSupportNavigateUp() {
        Intent intent = new Intent(MySubmissionActivity.this, Main2Activity.class);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        startActivity(intent);
        return super.onSupportNavigateUp();
    }
}
