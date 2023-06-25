package com.akruzen.checklance;

import static com.akruzen.checklance.constants.Methods.addCardViewToLayout;
import static com.akruzen.checklance.constants.Methods.applyCustomTheme;
import static com.akruzen.checklance.constants.Methods.doInitSetup;
import static com.akruzen.checklance.constants.Methods.jsonFileExists;
import static com.akruzen.checklance.constants.Methods.readJSONFile;
import static com.akruzen.checklance.constants.Variables.getLaunchedBefore;
import static com.akruzen.checklance.constants.Variables.getThemeKey;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.akruzen.checklance.classes.BankDetails;
import com.akruzen.checklance.lib.TinyDB;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    LinearLayout desertLinearLayout, showCardLinearLayout, scrollLinearLayout;
    ExtendedFloatingActionButton cornerFAB;
    TinyDB tinyDB;
    NavigationView navigationView;
    boolean cardSetupComplete = false;


    @Override
    protected void onResume() {
        super.onResume();
        // if (!cardSetupComplete) setUpCardViews();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Object Creation
        tinyDB = new TinyDB(this);
        // Find View
        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.navigationView);
        desertLinearLayout = findViewById(R.id.mainLinearLayout);
        showCardLinearLayout = findViewById(R.id.showCardLinearLayout);
        scrollLinearLayout = findViewById(R.id.scrollLinearLayout);
        cornerFAB = findViewById(R.id.cornerFAB);
        // Method Calls
        setUpNavigationDrawer();
        doInitSetup(this);
        applyCustomTheme(tinyDB);
    }

    public void openNavDrawer (View view) {
        drawerLayout.setDrawerTitle(GravityCompat.START, "Options");
        drawerLayout.openDrawer(GravityCompat.START);
    }

    public void addBankFAB (View view) {
        Intent intent = new Intent(this, AddBankActivity.class);
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
        if (tinyDB.getBoolean(getLaunchedBefore())) {
            if (jsonFileExists()) {
                BankDetails details = readJSONFile(this);
                Toast.makeText(this, "Acc: " + details.getAccNo() + "\nName: " + details.getBank(), Toast.LENGTH_SHORT).show();
                addCardViewToLayout(this, details, showCardLinearLayout);

                /* Add code here */

                setVisibilities(true);
            } else {
                setVisibilities(false);
            }
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
            showCardLinearLayout.setVisibility(View.VISIBLE);
            cornerFAB.setVisibility(View.VISIBLE);
            layoutParams.gravity = Gravity.TOP;
        } else {
            desertLinearLayout.setVisibility(View.VISIBLE);
            showCardLinearLayout.setVisibility(View.GONE);
            cornerFAB.setVisibility(View.GONE);
            layoutParams.gravity = Gravity.CENTER;
        }
        scrollLinearLayout.setLayoutParams(layoutParams);
    }

}