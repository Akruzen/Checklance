package com.akruzen.checklance;

import static com.akruzen.checklance.constants.Methods.addCardViewToLayout;
import static com.akruzen.checklance.constants.Methods.applyCustomTheme;
import static com.akruzen.checklance.constants.Methods.doInitSetup;
import static com.akruzen.checklance.constants.Methods.fadeOutAndReplaceText;
import static com.akruzen.checklance.constants.Methods.jsonFileExists;
import static com.akruzen.checklance.constants.Methods.readJSONFile;
import static com.akruzen.checklance.constants.Methods.saveAsJSONFile;
import static com.akruzen.checklance.constants.Variables.getBiometricKey;
import static com.akruzen.checklance.constants.Variables.getIsBiometricTemporarilyOffKey;
import static com.akruzen.checklance.constants.Variables.getLaunchedBefore;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.akruzen.checklance.classes.BankDetails;
import com.akruzen.checklance.lib.TinyDB;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    LinearLayout desertLinearLayout, showCardLinearLayout, scrollLinearLayout;
    ExtendedFloatingActionButton cornerFAB;
    TinyDB tinyDB;
    NavigationView navigationView;
    TextView headingTextView;
    ImageView refreshImageView;
    boolean cardSetupComplete = false, biometricAuth = false, needsAuth;
    BiometricPrompt biometricPrompt;


    @Override
    protected void onResume() {
        super.onResume();
        if (!cardSetupComplete) setUpCardViews();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Object Creation
        tinyDB = new TinyDB(this);
        needsAuth = tinyDB.getInt(getBiometricKey()) == 1;
        setContentView(R.layout.activity_main);
        // Find View
        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.navigationView);
        desertLinearLayout = findViewById(R.id.mainLinearLayout);
        showCardLinearLayout = findViewById(R.id.showCardLinearLayout);
        scrollLinearLayout = findViewById(R.id.scrollLinearLayout);
        cornerFAB = findViewById(R.id.cornerFAB);
        headingTextView = findViewById(R.id.headingTextView);
        refreshImageView = findViewById(R.id.refreshImageView);
        // Method Calls
        setUpNavigationDrawer();
        doInitSetup(this);
        applyCustomTheme(tinyDB);
        new Handler().postDelayed(() -> {
            fadeOutAndReplaceText("Checklance", headingTextView);
        }, 2000);
    }

    public void openNavDrawer (View view) {
        drawerLayout.setDrawerTitle(GravityCompat.START, "Options");
        drawerLayout.openDrawer(GravityCompat.START);
    }

    public void addBankFAB (View view) {
        Intent intent = new Intent(this, AddBankActivity.class);
        startActivity(intent);
    }

    public void refreshImageViewTapped (View view) {
        tinyDB.putBoolean(getIsBiometricTemporarilyOffKey(), true);
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    private void setUpNavigationDrawer () {
        navigationView.setNavigationItemSelectedListener(item -> {
            int id = item.getItemId();
            Intent intent = new Intent(this, SettingsActivity.class); // Default
            if (id == R.id.menu_settings) {
                drawerLayout.close();
            }
            startActivity(intent);
            return true;
        });
    }

    private void setUpCardViews() {
        cardSetupComplete = true;
        if (tinyDB.getBoolean(getIsBiometricTemporarilyOffKey())) {
            // This indicates that the theme has been changed, so no need to authenticate
            tinyDB.putBoolean(getIsBiometricTemporarilyOffKey(), false);
            /* Since we have already asserted cardSetupComplete as true,
            it will need to be manually reloaded */
            populateCardView();
        } else {
            if (!biometricAuth) {
                // If already authenticated, no need to authenticate again
                if (needsAuth) {
                    // Set up fingerprint authentication if user has enabled fingerprint
                    addFingerprintAuth();
                    final BiometricPrompt.PromptInfo promptInfo = new BiometricPrompt.PromptInfo.Builder()
                            .setTitle("Fingerprint Required")
                            .setDescription("Unlock Checklance with your fingerprint")
                            .setNegativeButtonText("Cancel")
                            .build();
                    biometricPrompt.authenticate(promptInfo);
                } else {
                    // If user has disabled fingerprint reader, consider fingerprint authenticated
                    biometricAuth = true;
                }
            }
        }
        if (tinyDB.getBoolean(getLaunchedBefore()) && biometricAuth) {
            populateCardView();
        } else {
            tinyDB.putBoolean(getLaunchedBefore(), true);
        }
    }

    private void setVisibilities(boolean cardsExist) {
        ScrollView.LayoutParams layoutParams = new ScrollView.LayoutParams(
                ScrollView.LayoutParams.MATCH_PARENT,
                ScrollView.LayoutParams.WRAP_CONTENT
        );
        if (cardsExist) {
            desertLinearLayout.setVisibility(View.GONE);
            refreshImageView.setVisibility(View.VISIBLE);
            showCardLinearLayout.setVisibility(View.VISIBLE);
            cornerFAB.setVisibility(View.VISIBLE);
            layoutParams.gravity = Gravity.TOP;
        } else {
            desertLinearLayout.setVisibility(View.VISIBLE);
            refreshImageView.setVisibility(View.INVISIBLE);
            showCardLinearLayout.setVisibility(View.GONE);
            cornerFAB.setVisibility(View.GONE);
            layoutParams.gravity = Gravity.CENTER;
        }
        scrollLinearLayout.setLayoutParams(layoutParams);
    }

    private void populateCardView() {
        if (jsonFileExists(this)) {
            List<BankDetails> detailsList = readJSONFile(this);
            for (BankDetails detail : detailsList) {
                addCardViewToLayout(this, detail, showCardLinearLayout);
            }
            setVisibilities(true);
        } else {
            setVisibilities(false);
        }
    }

    private void addFingerprintAuth() {
        // creating a variable for our Executor
        Executor executor = ContextCompat.getMainExecutor(this);
        biometricPrompt = new BiometricPrompt(
                MainActivity.this, executor, new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
            }
            @Override
            public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                biometricAuth = true;
                setUpCardViews();
            }
            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
            }
        }
        );
    }

}