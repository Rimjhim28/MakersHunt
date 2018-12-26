package com.example.android.makershunt.leader_screen;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.example.android.makershunt.Main2Activity;
import com.example.android.makershunt.Points;
import com.example.android.makershunt.R;
import com.example.android.makershunt.description_screen.Description2Activity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;

public class LeaderBoardActivity extends AppCompatActivity {

    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference ref = database.getReference();
    ArrayList<Points> points  = new ArrayList<>();
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leader_board);
        progressBar = findViewById(R.id.progress_bar_leader);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        readFromDatabase();
    }

    private void readFromDatabase(){
        ref.child("points").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                Iterable<DataSnapshot> snapshotIterator = dataSnapshot.getChildren();
                Iterator<DataSnapshot> iterator = snapshotIterator.iterator();
                Log.v("LeaderActivity: ", "Before While Loop");
                int i = 0;
                while (iterator.hasNext()) {
                    dataSnapshot = iterator.next();
                    Integer val = dataSnapshot.getValue(Integer.class);
                    points.add(new Points(dataSnapshot.getKey(), val));
                    Log.v("Leader Board: ", "User: " + dataSnapshot.getKey() + " Points: " + val);
                }
                Log.v("LeaderActivity: ", "After While Loop");
                formLeaderBoard();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.v("Leader Board Activity: ", "Error Occured");
            }
        });
    }

    public void formLeaderBoard(){
        Points temp;
        for(int i = 0; i < points.size(); i++){
            temp = points.get(i);
            for(int j = i+1; j < points.size(); j++){
                if(temp.getPoints() < points.get(j).getPoints()){
                    temp = points.get(j);
                    points.add(j, points.get(i));
                    points.add(i, temp);
                }
            }
        }

        for(int i = 0; i< points.size(); i++){
            Log.v("FORMATTION COMPLETE", "User: " + points.get(i).getPoints() + "Points: " + points.get(i).getName());
        }

        LeaderAdapter adapter = new LeaderAdapter(LeaderBoardActivity.this, 0,points);
        ListView listView = findViewById(R.id.leader_list_view);
        progressBar.setAlpha(0);
        listView.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(LeaderBoardActivity.this, Main2Activity.class);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        startActivity(intent);
    }

    @Override
    public boolean onSupportNavigateUp() {
        Intent intent = new Intent(LeaderBoardActivity.this, Main2Activity.class);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        startActivity(intent);
        return super.onSupportNavigateUp();
    }
}
