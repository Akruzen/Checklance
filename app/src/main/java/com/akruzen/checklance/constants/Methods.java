package com.akruzen.checklance.constants;

import static com.akruzen.checklance.constants.Variables.getThemeKey;
import static com.akruzen.checklance.constants.Variables.theme;

import android.content.Context;
import android.content.res.Configuration;
import android.view.MotionEvent;
import android.view.View;

import androidx.appcompat.app.AppCompatDelegate;

import com.akruzen.checklance.lib.TinyDB;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

public class Methods {

    public static MaterialAlertDialogBuilder showMaterialDialog (Context context, String title, String content) {
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(context);
        builder.setTitle(title);
        builder.setMessage(content);
        builder.setPositiveButton("Got it", (dialog, which) -> dialog.dismiss());
        builder.show();
        return builder;
    }

    public static boolean isEndIconTapped (MotionEvent event, TextInputEditText editText) {
        // Returns true if the end drawable of edit text is tapped. Else returns false
        if(event.getAction() == MotionEvent.ACTION_UP) {
            return event.getRawX() >= (editText.getRight() - editText.getCompoundDrawables()[2/*DRAWABLE_RIGHT*/].getBounds().width());
        }
        return false;
    }

    public static void applyCustomTheme(TinyDB tinyDB) {
        switch (theme) {
            case 0:
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                break;
            default:
            case 1:
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
                break;
            case 2:
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                break;
        }
        tinyDB.putInt(getThemeKey(), theme);
    }

    public static void doInitSetup (Context context) {
        TinyDB tinyDB = new TinyDB(context);
        theme = tinyDB.getInt(getThemeKey());
    }

    public static void showSnackBar (View view, String content) {
        final Snackbar snackbar = Snackbar.make(view, content, Snackbar.LENGTH_LONG);
        snackbar.setAction("Got It", v -> snackbar.dismiss());
        snackbar.show();
    }

}
