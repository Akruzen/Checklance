package com.akruzen.checklance.constants;

import static com.akruzen.checklance.constants.Variables.getJsonFileName;
import static com.akruzen.checklance.constants.Variables.getThemeKey;

import android.content.Context;
import android.content.ContextWrapper;
import android.os.Environment;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.cardview.widget.CardView;

import com.akruzen.checklance.R;
import com.akruzen.checklance.classes.BankDetails;
import com.akruzen.checklance.lib.TinyDB;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonWriter;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

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
        int theme = tinyDB.getInt(getThemeKey());
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
        // theme = tinyDB.getInt(getThemeKey());
    }

    // Show custom SnackBar
    public static void showSnackBar (View view, String content) {
        final Snackbar snackbar = Snackbar.make(view, content, Snackbar.LENGTH_LONG);
        snackbar.setAction("Got It", v -> snackbar.dismiss());
        snackbar.show();
    }

    // Writes BankDetails object as JSON file
    public static void saveAsJSONFile(List<BankDetails> bankDetailsList, Context context) {
        Gson gson = new Gson();
        ContextWrapper cw = new ContextWrapper(context);
        File directory = cw.getDir("configDir", Context.MODE_PRIVATE);
        File file = new File(directory, getJsonFileName());
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
            gson.toJson(bankDetailsList, new TypeToken<List<BankDetails>>() {}.getType(), jsonWriter);
            // gson.toJson(details, BankDetails.class, jsonWriter);
            jsonWriter.close();
            fileWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // Checks if JSON file exists
    public static boolean jsonFileExists (Context context) {
        ContextWrapper cw = new ContextWrapper(context);
        File directory = cw.getDir("configDir", Context.MODE_PRIVATE);
        File file = new File(directory, getJsonFileName());
        return file.exists();
    }

    // Read the JSON file and return the original object
    public static List<BankDetails> readJSONFile (Context context) {
        try {
            ContextWrapper cw = new ContextWrapper(context);
            File directory = cw.getDir("configDir", Context.MODE_PRIVATE);
            File file = new File(directory, getJsonFileName());
            FileReader fileReader = new FileReader(file);
            Gson gson = new Gson();
            Type bankDetailsListType = new TypeToken<List<BankDetails>>() {}.getType();
            List<BankDetails> bankDetailsList = gson.fromJson(fileReader, bankDetailsListType);
            fileReader.close();
            return bankDetailsList;
        } catch (IOException e) {
            Toast.makeText(context, "Cannot read file", Toast.LENGTH_SHORT).show();
            throw new RuntimeException(e);
        }
    }

    public static void addCardViewToLayout(Context context, BankDetails details, LinearLayout layout) {
        LayoutInflater inflater = LayoutInflater.from(context); // Replace 'this' with your activity or context

        // Inflate the card view layout
        CardView cardView = (CardView) inflater.inflate(R.layout.bank_card_view, null);

        // Set margins for the card view
        LinearLayout.LayoutParams cardViewParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        cardView.setLayoutParams(cardViewParams);
        ViewGroup.MarginLayoutParams layoutParams =
                (ViewGroup.MarginLayoutParams) cardView.getLayoutParams();
        layoutParams.setMargins(0, 0, 0, 32);
        cardView.requestLayout();

        // Find the TextViews inside the inflated layout
        TextView bankNameTextView = cardView.findViewById(R.id.bankNameTextView);
        TextView balanceTextView = cardView.findViewById(R.id.balanceTextView);

        // Set the content for the TextViews
        String bankName = details.getBank() + " (" + details.getAccNo() + ")";
        bankNameTextView.setText(bankName);
        balanceTextView.setText(String.valueOf(details.getCurrBal()));

        // Add the card view to your LinearLayout
        layout.addView(cardView);
    }

}
