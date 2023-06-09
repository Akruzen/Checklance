package com.akruzen.checklance;

import static com.akruzen.checklance.constants.Methods.applyCustomTheme;
import static com.akruzen.checklance.constants.Variables.getThemeKey;
import static com.akruzen.checklance.constants.Variables.theme;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.akruzen.checklance.lib.TinyDB;
import com.google.android.material.button.MaterialButtonToggleGroup;

public class SettingsActivity extends AppCompatActivity {

    MaterialButtonToggleGroup themeToggleBtn;
    TextView themeDescTxtView;
    TinyDB tinyDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        // Object Creation
        tinyDB = new TinyDB(this);
        // Find View
        themeToggleBtn = findViewById(R.id.themeToggleButton);
        themeDescTxtView = findViewById(R.id.themeDescTxt);
        // Method Calls
        setThemeToggleBtn();
    }

    public void applyThemeTapped (View view) {
        applyCustomTheme(tinyDB);
    }

    private void setThemeToggleBtn () {
        switch (tinyDB.getInt(getThemeKey())) {
            case 0:
                themeToggleBtn.check(R.id.segBtnLight); break;
            default:
            case 1:
                themeToggleBtn.check(R.id.segBtnSys); break;
            case 2:
                themeToggleBtn.check(R.id.segBtnDark); break;
        }
        themeToggleBtn.addOnButtonCheckedListener((group, checkedId, isChecked) -> {
            if (checkedId == R.id.segBtnSys) {
                themeDescTxtView.setText(getString(R.string.sys_theme_desc));
                theme = 1;
            }
            if (checkedId == R.id.segBtnLight) {
                themeDescTxtView.setText(getString(R.string.light_theme_desc));
                theme = 0;
            }
            if (checkedId == R.id.segBtnDark) {
                themeDescTxtView.setText(getString(R.string.dark_theme_desc));
                theme = 2;
            }
        });
    }
}