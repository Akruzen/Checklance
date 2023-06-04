package com.akruzen.checklance;

import static com.akruzen.checklance.Constants.Variables.getAddBankFragmentTag;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

public class MainActivity extends AppCompatActivity {
    public void addBankFAB (View view) {
        Intent intent = new Intent(this, AddBankActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Fragment prev = getSupportFragmentManager().findFragmentByTag(getAddBankFragmentTag());
        if (prev != null) {
            DialogFragment df = (DialogFragment) prev;
            df.dismiss();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}