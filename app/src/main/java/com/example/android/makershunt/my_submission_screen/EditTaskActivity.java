package com.example.android.makershunt.my_submission_screen;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import com.example.android.makershunt.submit_screen.Submit2Activity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EditTaskActivity extends AppCompatActivity {

    Button btnFinalSubmit;
    TextView txtWeek;
    EditText txtTitle, txtDescription, txtLink;
    String taskKey, title, description, link;
    int key;
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference ref = database.getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit);

        btnFinalSubmit = findViewById(R.id.btn_final_submit);
        txtTitle = findViewById(R.id.txt_title);
        txtDescription = findViewById(R.id.txt_desp);
        txtLink = findViewById(R.id.txt_link);
        txtWeek = findViewById(R.id.txt_week);

        key = getIntent().getIntExtra("task_key", 0);
        title = getIntent().getStringExtra("task_title");
        description = getIntent().getStringExtra("task description");
        Log.v("DESCRIPTION taking:: ", description);
        link = getIntent().getStringExtra("task_link");
        taskKey = "task_" + key;

        txtWeek.setText("Week " + key);
        txtTitle.setText(title);
        txtDescription.setText(description);
        txtLink.setText(link);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btnFinalSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean allFilled = checkForNull();
                if(allFilled) {
                    showDialogBox();
                }
                else{
                    Toast.makeText(EditTaskActivity.this,"One or more fields are empty", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void showDialogBox(){
        AlertDialog.Builder builder = new AlertDialog.Builder(EditTaskActivity.this);
        builder.setTitle("Are You Sure?")
                .setMessage("Submit to database");

        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                writeInDatabase();
                Toast.makeText(EditTaskActivity.this,"Successfully Submitted", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(EditTaskActivity.this, Main2Activity.class);
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

    public boolean checkForNull(){
        return !txtTitle.getText().toString().equals("") && !txtDescription.getText().toString().equals("") && !txtLink.getText().toString().equals("");
    }

    public void writeInDatabase(){
        Task task = new Task(txtTitle.getText().toString(), txtDescription.getText().toString(), txtLink.getText().toString(), 0);
        User user = new User(task, Utils.userName, 0 );
        DatabaseReference usersRef = ref.child(user.getMail());
        usersRef.child(taskKey).setValue(user.getTask());
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(EditTaskActivity.this, MySubmissionActivity.class);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        startActivity(intent);
    }

    @Override
    public boolean onSupportNavigateUp() {
        Intent intent = new Intent(EditTaskActivity.this, MySubmissionActivity.class);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        startActivity(intent);
        return super.onSupportNavigateUp();
    }
}
