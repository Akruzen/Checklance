package com.akruzen.checklance;

import static com.akruzen.checklance.constants.Methods.isEndIconTapped;
import static com.akruzen.checklance.constants.Methods.showMaterialDialog;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

public class MessageDetailsActivity extends AppCompatActivity {

    TextInputEditText senderEditText;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_details);

        senderEditText = findViewById(R.id.senderNameEditText);
        setOnClickListeners(this);

    }

    public void nextTapped (View view) {
        Intent intent = new Intent(this, BalanceDetailsActivity.class);
        startActivity(intent);
    }

    public void cancelTapped (View view) {
        finish();
    }

    @SuppressLint("ClickableViewAccessibility")
    private void setOnClickListeners (Context context) {
        senderEditText.setOnTouchListener((v, event) -> {
            if (isEndIconTapped(event, senderEditText)) {
                showMaterialDialog(context, "Sender Name", getString(R.string.sender_name));
                return true;
            }
            return false;
        });
    }

}