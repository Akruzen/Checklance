package com.akruzen.checklance;

import static com.akruzen.checklance.constants.Methods.applyCustomTheme;
import static com.akruzen.checklance.constants.Variables.getThemeKey;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.akruzen.checklance.lib.TinyDB;
import com.google.android.material.button.MaterialButtonToggleGroup;

public class SettingsActivity extends AppCompatActivity {

    MaterialButtonToggleGroup themeToggleBtn;
    TextView themeDescTxtView;
    TinyDB tinyDB;
    int theme = 0;
    Button applyButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        // Object Creation
        tinyDB = new TinyDB(this);
        // Find View
        themeToggleBtn = findViewById(R.id.themeToggleButton);
        themeDescTxtView = findViewById(R.id.themeDescTxt);
        applyButton = findViewById(R.id.setThemeBtn);
        // Set variable values
        theme = tinyDB.getInt(getThemeKey());
        // Method Calls
        setToggleBtnState();
        setToggleBtnOnClick();
    }

    private void setApplyButtonState() {
        // If current theme is same as selected, no need to apply it again
        if (theme != tinyDB.getInt(getThemeKey())) {
            applyButton.setEnabled(true);
            return;
        }
        applyButton.setEnabled(false);
    }

    public void applyThemeTapped (View view) {
        tinyDB.putInt(getThemeKey(), theme);
        applyCustomTheme(tinyDB);
        setApplyButtonState();
        String themeStr = theme == 1 ? "System" : theme == 0 ? "Light" : "Dark";
        Toast.makeText(this, themeStr + " Theme Applied", Toast.LENGTH_SHORT).show();
    }

    private void setToggleBtnState() {
        switch (tinyDB.getInt(getThemeKey())) {
            case 0:
                themeToggleBtn.check(R.id.segBtnLight); break;
            default:
            case 1:
                themeToggleBtn.check(R.id.segBtnSys); break;
            case 2:
                themeToggleBtn.check(R.id.segBtnDark); break;
        }
        setThemeTextView();
    }

    private void setToggleBtnOnClick() {
        themeToggleBtn.addOnButtonCheckedListener((group, checkedId, isChecked) -> {
            // IMPORTANT: This method is also called when a button unchecks itself
            if (isChecked) {
                if (checkedId == R.id.segBtnLight) {
                    theme = 0;
                }
                else if (checkedId == R.id.segBtnSys) {
                    theme = 1;
                }
                else if (checkedId == R.id.segBtnDark) {
                    theme = 2;
                }
                setThemeTextView();
                setApplyButtonState();
            }
        });
    }

    private void setThemeTextView () {
        Log.i("Theme", "Selected Theme: " + theme);
        switch (theme) {
            case 0: themeDescTxtView.setText(getString(R.string.light_theme_desc)); break;
            case 1: themeDescTxtView.setText(getString(R.string.sys_theme_desc)); break;
            case 2: themeDescTxtView.setText(getString(R.string.dark_theme_desc)); break;
        }
    }
}