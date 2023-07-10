package com.akruzen.checklance;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
    }

    public void onGitHubPressed (View view) {
        // Github logic
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/Akruzen"));
        startActivity(browserIntent);
    }

    public void onLinkedInPressed (View view) {
        // Github logic
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.linkedin.com/in/omkar-phadke-b97b741ba/"));
        startActivity(browserIntent);
    }

    public void onDiscordPressed (View view) {
        // Github logic
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://discordapp.com/users/akruzen#2652"));
        startActivity(browserIntent);
    }

    public void onSourceCodePressed (View view) {
        // Github logic
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/Akruzen/Checklance"));
        startActivity(browserIntent);
    }
}