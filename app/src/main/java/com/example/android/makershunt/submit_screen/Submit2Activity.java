package com.example.android.makershunt.submit_screen;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.makershunt.Main2Activity;
import com.example.android.makershunt.R;
import com.example.android.makershunt.Task;
import com.example.android.makershunt.User;
import com.example.android.makershunt.Utils;
import com.example.android.makershunt.description_screen.Description2Activity;
import com.example.android.makershunt.my_submission_screen.EditTaskActivity;
import com.example.android.makershunt.my_submission_screen.MySubmissionActivity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Submit2Activity extends AppCompatActivity {

    Button btnFinalSubmit;
    TextView txtWeek;
    EditText txtTitle, txtDescription, txtLink;
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference ref = database.getReference();
    int task;
    String taskKey;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit);

        btnFinalSubmit = findViewById(R.id.btn_final_submit);
        txtTitle = findViewById(R.id.txt_title);
        txtDescription = findViewById(R.id.txt_desp);
        txtLink = findViewById(R.id.txt_link);
        txtWeek = findViewById(R.id.txt_week);

        task = getIntent().getIntExtra("task", 0);
        taskKey = "task_"+task;
        txtWeek.setText("Week " + task);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btnFinalSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean allFilled = checkForNull();
                if(allFilled) {
                    showDialogBox();
                }
                else{
                    Toast.makeText(Submit2Activity.this,"One or more fields are empty", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void showDialogBox(){
        AlertDialog.Builder builder = new AlertDialog.Builder(Submit2Activity.this);
        builder.setTitle("Are You Sure?")
                .setMessage("Your entries will be submitted to database");

        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                    writeInDatabase();
                    Toast.makeText(Submit2Activity.this,"Successfully Submitted", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Submit2Activity.this, Main2Activity.class);
                try {
                    new Thread().sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });

        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void writeInDatabase(){
        Task task = new Task(txtTitle.getText().toString(), txtDescription.getText().toString(), txtLink.getText().toString(), 0);
        User user = new User(task, Utils.userName, 0 );
        DatabaseReference usersRef = ref;
        usersRef.child("users").child(Utils.userName).child(taskKey).setValue(user.getTask());
    }

    public boolean checkForNull(){
        return !txtTitle.getText().toString().equals("") && !txtDescription.getText().toString().equals("") && !txtLink.getText().toString().equals("");
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(Submit2Activity.this, Main2Activity.class));
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }


    @Override
    public boolean onSupportNavigateUp() {
        startActivity(new Intent(Submit2Activity.this, Main2Activity.class));
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        return super.onSupportNavigateUp();
    }
}
