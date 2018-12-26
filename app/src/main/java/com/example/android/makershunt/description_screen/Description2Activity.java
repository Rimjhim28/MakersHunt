package com.example.android.makershunt.description_screen;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.android.makershunt.Main2Activity;
import com.example.android.makershunt.R;
import com.example.android.makershunt.Task;
import com.example.android.makershunt.TaskView;
import com.example.android.makershunt.Utils;
import com.example.android.makershunt.submit_screen.Submit2Activity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;

import static android.view.View.GONE;

public class Description2Activity extends AppCompatActivity {

    TextView txtDesp, txtAlreadySubmitted;
    Button btnSubmit;
    ProgressBar progressBar;
    int num, flag;
    private String taskKey, description;
    private TaskView task;
    boolean alreadySubmitted;
    private ArrayList<TaskView> tasks  = new ArrayList<>();

    //Creating Firebase objects for reading data
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference ref = database.getReference();



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description);

        btnSubmit = findViewById(R.id.btn_submit);
        progressBar = findViewById(R.id.progress_bar);
        txtAlreadySubmitted = findViewById(R.id.txt_already_submitted);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Description2Activity.this, Submit2Activity.class);
                intent.putExtra("task", num);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });


        txtDesp = findViewById(R.id.txt_task_desp);
        txtDesp.setAlpha(0);
        btnSubmit.setAlpha(0);
        num = getIntent().getIntExtra("event", 0);
        taskKey = "task_" + num;
        readUserSubmission();
        readFromDatabase();
    }

    public void readFromDatabase(){
        final ArrayList<TaskView> tasks  = new ArrayList<>();
        ref.child("tasks").addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                Iterable<DataSnapshot> snapshotIterator = dataSnapshot.getChildren();
                for (DataSnapshot aSnapshotIterator : snapshotIterator) {
                    TaskView task = aSnapshotIterator.getValue(TaskView.class);
                    tasks.add(task);
                }

                progressBar.setAlpha(0);
                txtDesp.setAlpha(1);
                description = tasks.get(num-1).getDescription();
                txtDesp.setText(description);
                Log.v("Activity: ", "Value -> " + description);
                flag = tasks.get(num-1).getFlag();
                Log.v("DESCRIPTION", "FLAG VALUE -> " + flag);
                if(flag == 0 &&!alreadySubmitted){
                    btnSubmit.setAlpha(0);
                    btnSubmit.setEnabled(false);
                    txtAlreadySubmitted.setAlpha(0);
                }
                else if(flag == 1 && !alreadySubmitted){
                    btnSubmit.setEnabled(true);
                    btnSubmit.setAlpha(1);
                    txtAlreadySubmitted.setAlpha(0);
                }
                else if(alreadySubmitted){
                    btnSubmit.setVisibility(GONE);
                    btnSubmit.setAlpha(0);
                    txtAlreadySubmitted.setAlpha(1);
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("Not able to read data", "Failed to read value.", error.toException());
            }
        });
    }

    public void readUserSubmission(){
            final ArrayList<Task> tasks  = new ArrayList<>();
            ref.child("users").child(Utils.userName).child(taskKey).addValueEventListener(new ValueEventListener() {

                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    // This method is called once with the initial value and again
                    // whenever data at this location is updated.
                    Iterable<DataSnapshot> snapshotIterator = dataSnapshot.getChildren();
                    Iterator<DataSnapshot> iterator = snapshotIterator.iterator();
                    if (iterator.hasNext()) {
                        Log.v("Task Description", "Task is submitted");
                        alreadySubmitted = true;
                    } else {
                        Log.v("Task Description", "Task is NOT submitted");
                        alreadySubmitted = false;
                    }
                }

                @Override
                public void onCancelled(DatabaseError error) {
                    // Failed to read value
                    Log.w("Not able to read data", "Failed to read value.", error.toException());
                }
            });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(Description2Activity.this, Main2Activity.class);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        startActivity(intent);
    }

    @Override
    public boolean onSupportNavigateUp() {
        Intent intent = new Intent(Description2Activity.this, Main2Activity.class);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        startActivity(intent);
        return super.onSupportNavigateUp();
    }

}
