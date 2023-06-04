package com.akruzen.checklance;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

public class DebitMessageActivity extends AppCompatActivity {

    public void nextTapped (View view) {
        /*Intent intent = new Intent(this, CreditMessageActivity.class);
        startActivity(intent);*/
    }

    public void cancelTapped (View view) {
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credit_message);
    }
}