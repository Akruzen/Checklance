package com.akruzen.checklance;

import static com.akruzen.checklance.constants.Methods.applyCustomTheme;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.akruzen.checklance.lib.TinyDB;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
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
        // Method Calls
        setUpNavigationDrawer();
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

}