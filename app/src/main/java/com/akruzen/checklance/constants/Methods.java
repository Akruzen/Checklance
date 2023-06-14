package com.akruzen.checklance.constants;

import static com.akruzen.checklance.constants.Variables.getThemeKey;
import static com.akruzen.checklance.constants.Variables.theme;

import android.content.Context;
import android.os.Environment;
import android.view.MotionEvent;
import android.view.View;

import androidx.appcompat.app.AppCompatDelegate;

import com.akruzen.checklance.classes.BankDetails;
import com.akruzen.checklance.lib.TinyDB;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;
import com.google.gson.stream.JsonWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Methods {

    // Show dialog when question mark icon at end of text input edit text is tapped
    public static MaterialAlertDialogBuilder showMaterialDialog (Context context, String title, String content) {
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(context);
        builder.setTitle(title);
        builder.setMessage(content);
        builder.setPositiveButton("Got it", (dialog, which) -> dialog.dismiss());
        builder.show();
        return builder;
    }

    // If the question mark icon at end of text input edit text is tapped or not
    public static boolean isEndIconTapped (MotionEvent event, TextInputEditText editText) {
        // Returns true if the end drawable of edit text is tapped. Else returns false
        if(event.getAction() == MotionEvent.ACTION_UP) {
            return event.getRawX() >= (editText.getRight() - editText.getCompoundDrawables()[2/*DRAWABLE_RIGHT*/].getBounds().width());
        }
        return false;
    }

    // Apply Dark, Light or System Theme
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

    // Do this everytime app starts
    public static void doInitSetup (Context context) {
        TinyDB tinyDB = new TinyDB(context);
        theme = tinyDB.getInt(getThemeKey());
    }

    // Show custom SnackBar
    public static void showSnackBar (View view, String content) {
        final Snackbar snackbar = Snackbar.make(view, content, Snackbar.LENGTH_LONG);
        snackbar.setAction("Got It", v -> snackbar.dismiss());
        snackbar.show();
    }

    public static void saveAsJSONFile(BankDetails details) {
        Gson gson = new Gson();
        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), "/Checklance/output.json");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        try {
            FileWriter fileWriter = new FileWriter(file);
            JsonWriter jsonWriter = new JsonWriter(fileWriter);
            gson.toJson(details, BankDetails.class, jsonWriter);
            jsonWriter.close();
            fileWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
