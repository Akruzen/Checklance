package com.akruzen.checklance;

import static com.akruzen.checklance.constants.Methods.showSnackBar;
import static com.akruzen.checklance.constants.Variables.getBankList;
import static com.akruzen.checklance.constants.Variables.getOtherBankStr;

import android.content.Intent;
import android.opengl.Visibility;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.akruzen.checklance.constants.Variables;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

public class AddBankActivity extends AppCompatActivity {

    AutoCompleteTextView bankAutoCompleteTextView;
    TextInputLayout bankNameTextInput, accNoTextInput;
    ConstraintLayout constraintLayout;

    public void nextTapped (View view) {
        if (fieldsAreVerified()) {
            Intent intent = new Intent(this, MessageDetailsActivity.class);
            intent.putExtra("accNo", Objects.requireNonNull(accNoTextInput.getEditText()).getText().toString());
            intent.putExtra("bank", bankAutoCompleteTextView.getText().toString());
            intent.putExtra("otherBank", Objects.requireNonNull(bankNameTextInput.getEditText()).getText().toString());
            startActivity(intent);
        }
        else {
            showSnackBar(constraintLayout, "All fields are mandatory");
        }
    }

    public void cancelTapped (View view) {
        finish();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_bank);
        // Find Views
        accNoTextInput = findViewById(R.id.accNoTextField);
        bankAutoCompleteTextView = findViewById(R.id.bankAutoCompleteTextView);
        bankNameTextInput = findViewById(R.id.bankNameTextInput);
        constraintLayout = findViewById(R.id.addBankConstraintLayout);
        // Set up init
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this, android.R.layout.simple_list_item_1, getBankList());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        bankAutoCompleteTextView.setAdapter(adapter);
        setOnClickListeners();
    }

    private boolean fieldsAreVerified() {
        String accNo = Objects.requireNonNull(accNoTextInput.getEditText()).getText().toString().trim();
        String bank = bankAutoCompleteTextView.getText().toString().trim();
        String otherBank = Objects.requireNonNull(bankNameTextInput.getEditText()).getText()
                .toString().trim();
        if (accNo.isEmpty() || accNo.length() < 4 /* Last 4 digits required */) {
            Log.i("Fields Missing", "Account Number");
            return false;
        }
        if (bank.isEmpty()) {
            Log.i("Fields Missing", "Bank Name");
            return false;
        }
        if (bank.equals(getOtherBankStr()) && otherBank.isEmpty()) {
            Log.i("Fields Missing", "Other Bank Name");
            return false;
        }
        return true;
    }

    private void setOnClickListeners() {
        bankAutoCompleteTextView.setOnItemClickListener((parent, view, position, id) -> {
            if (position == getBankList().size() - 1 /*last item*/) {
                // Last Item is "Others..."
                bankNameTextInput.setVisibility(View.VISIBLE);
            } else {
                bankNameTextInput.setVisibility(View.GONE);
            }
        });
    }

}