package com.akruzen.checklance;

import static com.akruzen.checklance.constants.Methods.saveAsJSONFile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.akruzen.checklance.classes.BankDetails;

public class BalanceDetailsActivity extends AppCompatActivity {

    public void finishTapped (View view) {
        BankDetails details = new BankDetails(
                4451, "Maharashtra Bank"
        );
        saveAsJSONFile(details);
        Toast.makeText(this, "Created Successfully", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, MainActivity.class);
        // Clears the back stack and brings any current running instance to top
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        BalanceDetailsActivity.this.finish();
    }

    public void cancelTapped (View view) {
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_balance_details);
    }
}