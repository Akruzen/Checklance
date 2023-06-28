package com.akruzen.checklance;

import static com.akruzen.checklance.constants.Methods.saveAsJSONFile;
import static com.akruzen.checklance.constants.Methods.showSnackBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.akruzen.checklance.classes.BankDetails;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class BalanceDetailsActivity extends AppCompatActivity {

    TextInputEditText balanceEditText;
    ConstraintLayout constraintLayout;
    String sender, credit, debit, accNo, bank, otherBank;

    public void finishTapped (View view) {

        if (fieldsAreVerified()) {
            if (otherBank != null && !otherBank.isEmpty()) {
                // This means user did not select pre-specified banks
                bank = otherBank;
            }
            BankDetails details = new BankDetails(
                    Integer.parseInt(accNo), bank, sender, credit, debit,
                    Double.parseDouble(Objects.requireNonNull(balanceEditText.getText()).toString())
            );
            saveAsJSONFile(details, this);
            Toast.makeText(this, "Created Successfully", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, MainActivity.class);
            // Clears the back stack and brings any current running instance to top
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            BalanceDetailsActivity.this.finish();
        } else {
            showSnackBar(constraintLayout, "All fields are mandatory");
        }
    }

    public void cancelTapped (View view) {
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_balance_details);
        // Find view by ID
        balanceEditText = findViewById(R.id.balanceEditText);
        constraintLayout = findViewById(R.id.balanceDetailsConstraintLayout);
        // Logger
        Intent currIntent = getIntent();
        sender = currIntent.getStringExtra("sender");
        credit = currIntent.getStringExtra("creditKeys");
        debit =  currIntent.getStringExtra("debitKeys");
        accNo = currIntent.getStringExtra("accNo");
        bank = currIntent.getStringExtra("bank");
        otherBank = currIntent.getStringExtra("otherBank");
        Log.i("BalanceDetailsActivity", "sender: " + sender + " credit: " + credit + " debit: " + debit + " accNo: " + accNo + " bank: " + bank + " otherBank: " + otherBank);
    }

    private boolean fieldsAreVerified() {
        if (Objects.requireNonNull(balanceEditText.getText()).toString().isEmpty()) {
            return false;
        }
        return true;
    }
}