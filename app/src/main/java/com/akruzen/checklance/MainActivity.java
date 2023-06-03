package com.akruzen.checklance;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    public void addBankFAB (View view) {
        AddBankFragment fragment = new AddBankFragment();
        fragment.show(getSupportFragmentManager(), "MyFragment");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}