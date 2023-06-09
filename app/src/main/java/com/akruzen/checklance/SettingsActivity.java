package com.akruzen.checklance;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButtonToggleGroup;

public class SettingsActivity extends AppCompatActivity {

    MaterialButtonToggleGroup themeToggleBtn;
    TextView themeDescTxtView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        // Find View
        themeToggleBtn = findViewById(R.id.themeToggleButton);
        themeDescTxtView = findViewById(R.id.themeDescTxt);
        // Method Calls
        setThemeToggleBtn();
    }

    private void setThemeToggleBtn () {
        themeToggleBtn.addOnButtonCheckedListener((group, checkedId, isChecked) -> {
            if (checkedId == R.id.segBtnSys) themeDescTxtView.setText(getString(R.string.sys_theme_desc));
            if (checkedId == R.id.segBtnLight) themeDescTxtView.setText(getString(R.string.light_theme_desc));
            if (checkedId == R.id.segBtnDark) themeDescTxtView.setText(getString(R.string.dark_theme_desc));
        });
    }
}