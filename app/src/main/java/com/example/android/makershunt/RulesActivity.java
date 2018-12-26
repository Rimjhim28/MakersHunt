package com.example.android.makershunt;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.android.makershunt.description_screen.Description2Activity;
import com.example.android.makershunt.submit_screen.Submit2Activity;

public class RulesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rules);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(RulesActivity.this, Main2Activity.class));
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    @Override
    public boolean onSupportNavigateUp() {
        startActivity(new Intent(RulesActivity.this, Main2Activity.class));
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        return super.onSupportNavigateUp();
    }
}
