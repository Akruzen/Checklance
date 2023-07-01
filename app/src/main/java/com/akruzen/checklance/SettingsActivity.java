package com.akruzen.checklance;

import static com.akruzen.checklance.constants.Methods.applyCustomTheme;
import static com.akruzen.checklance.constants.Methods.isBiometricExist;
import static com.akruzen.checklance.constants.Variables.getBiometricKey;
import static com.akruzen.checklance.constants.Variables.getIsBiometricTemporarilyOffKey;
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
import com.google.android.material.materialswitch.MaterialSwitch;

public class SettingsActivity extends AppCompatActivity {

    MaterialButtonToggleGroup themeToggleBtn;
    TextView themeDescTxtView;
    TinyDB tinyDB;
    int theme = 0;
    Button applyButton;
    MaterialSwitch biometricSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Log.i("SettingsActivity", "onCreate");
        // Object Creation
        tinyDB = new TinyDB(this);
        // Find View
        themeToggleBtn = findViewById(R.id.themeToggleButton);
        themeDescTxtView = findViewById(R.id.themeDescTxt);
        applyButton = findViewById(R.id.setThemeBtn);
        biometricSwitch = findViewById(R.id.biometricSwitch);
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
        if (biometricSwitch.isChecked()) {
            tinyDB.putBoolean(getIsBiometricTemporarilyOffKey(), true);
        }
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
        biometricSwitch.setEnabled(isBiometricExist(this));
        biometricSwitch.setChecked(tinyDB.getInt(getBiometricKey()) == 1);
        biometricSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isBiometricExist(getApplicationContext())) {
                tinyDB.putInt(getBiometricKey(), isChecked ? 1 : 0);
            } else {
                Toast.makeText(SettingsActivity.this, "Biometric Not Available", Toast.LENGTH_SHORT).show();
            }
        });
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