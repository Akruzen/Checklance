package com.akruzen.checklance;

import static com.akruzen.checklance.constants.Methods.addCardViewToLayout;
import static com.akruzen.checklance.constants.Methods.applyCustomTheme;
import static com.akruzen.checklance.constants.Methods.doInitSetup;
import static com.akruzen.checklance.constants.Methods.jsonFileExists;
import static com.akruzen.checklance.constants.Methods.readJSONFile;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.akruzen.checklance.classes.BankDetails;
import com.akruzen.checklance.lib.TinyDB;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    LinearLayout mainLinearLayout;
    TinyDB tinyDB;
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Object Creation
        tinyDB = new TinyDB(this);
        // Find View
        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.navigationView);
        mainLinearLayout = findViewById(R.id.mainLinearLayout);
        // Method Calls
        setUpNavigationDrawer();
        doInitSetup(this);
        applyCustomTheme(tinyDB);
        // Add Bank CardViews
        setUpCardViews();
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
        if (jsonFileExists()) {
            BankDetails details = readJSONFile(this);
            Toast.makeText(this, "Acc: " + details.getAccNo() + "\nName: " + details.getBank(), Toast.LENGTH_SHORT).show();
            addCardViewToLayout(this, details, mainLinearLayout);
        }
    }

}