package com.akruzen.checklance;

import static com.akruzen.checklance.constants.Methods.isEndIconTapped;
import static com.akruzen.checklance.constants.Methods.showMaterialDialog;
import static com.akruzen.checklance.constants.Methods.showSnackBar;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;

public class MessageDetailsActivity extends AppCompatActivity {

    TextInputEditText senderEditText, creditEditText, debitEditText;
    ConstraintLayout constraintLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_details);
        // Find view by ID
        senderEditText = findViewById(R.id.senderNameEditText);
        creditEditText = findViewById(R.id.creditKeysEditText);
        debitEditText = findViewById(R.id.debitKeysEditText);
        constraintLayout = findViewById(R.id.messageDetailsConstraintLayout);

        setOnClickListeners(this);

    }

    public void nextTapped (View view) {
        if (fieldsAreVerified()) {
            Intent intent = new Intent(this, BalanceDetailsActivity.class);
            String accNo, bank, otherBank;
            accNo = getIntent().getStringExtra("accNo");
            bank = getIntent().getStringExtra("bank");
            otherBank = getIntent().getStringExtra("otherBank");
            intent.putExtra("sender", Objects.requireNonNull(senderEditText.getText()).toString());
            intent.putExtra("creditKeys", Objects.requireNonNull(creditEditText.getText()).toString());
            intent.putExtra("debitKeys", Objects.requireNonNull(debitEditText.getText()).toString());
            intent.putExtra("accNo", accNo);
            intent.putExtra("bank", bank);
            intent.putExtra("otherBank", otherBank);
            Log.i("MessageDetailsActivity", "sender: " + senderEditText.getText().toString() + " credit: " + creditEditText.getText().toString() + " debit: " + debitEditText.getText().toString());
            startActivity(intent);
        } else {
            showSnackBar(constraintLayout, "All fields are mandatory");
        }
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

    private boolean fieldsAreVerified() {
        String sender = Objects.requireNonNull(senderEditText.getText()).toString().trim();
        String credit = Objects.requireNonNull(creditEditText.getText()).toString().trim();
        String debit = Objects.requireNonNull(debitEditText.getText()).toString().trim();
        if (sender.isEmpty() || credit.isEmpty() || debit.isEmpty()) {
            return false;
        }
        return true;
    }

}