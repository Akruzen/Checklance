package com.akruzen.checklance;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.akruzen.checklance.Constants.Variables;

public class AddBankActivity extends AppCompatActivity {

    AutoCompleteTextView bankAutoCompleteTextView;
    Button nextButton;

    public void nextTapped () {
        Intent intent = new Intent(this, MessageDetailsActivity.class);
        startActivity(intent);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_bank);

        bankAutoCompleteTextView = findViewById(R.id.bankAutoCompleteTextView);
        nextButton = findViewById(R.id.nextButton);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this, android.R.layout.simple_list_item_1, Variables.getBankList());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        bankAutoCompleteTextView.setAdapter(adapter);

        nextButton.setOnClickListener(v -> nextTapped());
    }

}