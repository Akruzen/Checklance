package com.akruzen.checklance;

import static com.akruzen.checklance.constants.Methods.applyCustomTheme;
import static com.akruzen.checklance.constants.Methods.showSnackBar;
import static com.akruzen.checklance.constants.Variables.getShowSnackBarKey;
import static com.akruzen.checklance.constants.Variables.getThemeKey;
import static com.akruzen.checklance.constants.Variables.theme;

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
        // Method Calls
        setThemeToggleBtn();
        setThemeTextView();
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
        tinyDB.putBoolean(getShowSnackBarKey(), true);
        applyCustomTheme(tinyDB);
        setApplyButtonState();
        String themeStr = theme == 1 ? "System" : theme == 0 ? "Light" : "Dark";
        Toast.makeText(this, themeStr + " Theme Applied", Toast.LENGTH_SHORT).show();
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
                theme = 1;
            }
            if (checkedId == R.id.segBtnLight) {
                theme = 0;
            }
            if (checkedId == R.id.segBtnDark) {
                theme = 2;
            }
            setThemeTextView();
            setApplyButtonState();
        });
    }

    private void setThemeTextView () {
        switch (theme) {
            case 0: themeDescTxtView.setText(getString(R.string.light_theme_desc)); break;
            default: case 1: themeDescTxtView.setText(getString(R.string.sys_theme_desc)); break;
            case 2: themeDescTxtView.setText(getString(R.string.dark_theme_desc)); break;
        }
    }
}