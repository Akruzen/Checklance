package com.akruzen.checklance.constants;

import static androidx.core.app.ActivityCompat.shouldShowRequestPermissionRationale;
import static com.akruzen.checklance.constants.Variables.SMS_PERMISSION_REQUEST_CODE;
import static com.akruzen.checklance.constants.Variables.getJsonFileName;
import static com.akruzen.checklance.constants.Variables.getThemeKey;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.biometric.BiometricManager;
import androidx.biometric.BiometricPrompt;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import com.akruzen.checklance.MainActivity;
import com.akruzen.checklance.R;
import com.akruzen.checklance.classes.BankDetails;
import com.akruzen.checklance.lib.TinyDB;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonWriter;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.concurrent.Executor;

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
        if (file.exists() && readJSONFile(context).size() > 0) {
            return true;
        }
        return false;
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
        MaterialButton deleteButton = cardView.findViewById(R.id.deleteCardBtn);
        MaterialButton editButton = cardView.findViewById(R.id.editCardBtn);

        // Set the content for the TextViews
        String bankName = details.getBank() + " (" + details.getAccNo() + ")";
        bankNameTextView.setText(bankName);
        balanceTextView.setText(String.valueOf(details.getCurrBal()));
        deleteButton.setOnClickListener(v -> {
            showAlertDialogBox(context, details);
        });


        // Add the card view to your LinearLayout
        layout.addView(cardView);
    }

    private static void showAlertDialogBox (Context context, BankDetails bankDetails) {
        AlertDialog alertDialog = new MaterialAlertDialogBuilder(context).create();
        alertDialog.setTitle("Delete Account");
        alertDialog.setMessage("Delete selected account? Remember this action is permanent and cannot be undone!");
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Delete",
                (dialog, which) -> {
                    if (jsonFileExists(context)) {
                        List<BankDetails> bankDetailsList = readJSONFile(context);
                        boolean flag = false;
                        for (BankDetails detail : bankDetailsList) {
                            if (detail.getAccNo() == (bankDetails.getAccNo()) &&
                                    detail.getBank().equals(bankDetails.getBank())) {
                                flag = true;
                                bankDetailsList.remove(detail);
                                saveAsJSONFile(bankDetailsList, context);
                                Toast.makeText(context, "Bank Account removed", Toast.LENGTH_SHORT).show();
                            }
                        }
                        if (flag) {
                            Intent intent = new Intent(context, MainActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                            context.startActivity(intent);
                        }
                    }
                });
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancel", (dialog, which) -> dialog.dismiss());
        alertDialog.show();
    }

    public static void fadeOutAndReplaceText(final String newText, TextView textView) {
        Animation fadeOut = new AlphaAnimation(1, 0);
        fadeOut.setDuration(500);  // milliseconds
        fadeOut.setFillAfter(true);
        fadeOut.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                // Do nothing
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                textView.setText(newText);
                textView.setTypeface(textView.getTypeface(), Typeface.BOLD);
                fadeIn(textView);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        textView.startAnimation(fadeOut);
    }

    private static void fadeIn(TextView textView) {
        Animation fadeIn = new AlphaAnimation(0, 1);
        fadeIn.setDuration(500);
        fadeIn.setFillAfter(true);
        textView.startAnimation(fadeIn);
    }

    public static boolean isBiometricExist(Context context) {
        BiometricManager biometricManager = androidx.biometric.BiometricManager.from(context);
        switch (biometricManager.canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_WEAK)) {
            case BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE:
            case BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE:
            case BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED:
                return false;
            default:
                return true;
        }
    }

    public static boolean checkSMSPermission(Context context) {
        return ContextCompat.checkSelfPermission(context, Manifest.permission.RECEIVE_SMS) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(context, Manifest.permission.READ_SMS) == PackageManager.PERMISSION_GRANTED;
    }

    public static void requestSmsPermission(Activity activity) {
        // Check if the user has previously denied the permissions
        boolean shouldExplain = shouldShowRequestPermissionRationale(activity, Manifest.permission.RECEIVE_SMS) ||
                shouldShowRequestPermissionRationale(activity, Manifest.permission.READ_SMS);

        if (shouldExplain) {
            // Display an explanation to the user before requesting permissions
            ActivityCompat.requestPermissions(
                    activity,
                    new String[]{Manifest.permission.RECEIVE_SMS, Manifest.permission.READ_SMS},
                    SMS_PERMISSION_REQUEST_CODE
            );
        } else {
            // No need for explanation, directly request the permissions
            ActivityCompat.requestPermissions(
                    activity,
                    new String[]{Manifest.permission.RECEIVE_SMS, Manifest.permission.READ_SMS},
                    SMS_PERMISSION_REQUEST_CODE
            );
        }

    }

}
