package com.akruzen.checklance;

import static com.akruzen.checklance.constants.Variables.getBankList;

import android.content.Intent;
import android.opengl.Visibility;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import androidx.appcompat.app.AppCompatActivity;

import com.akruzen.checklance.constants.Variables;
import com.google.android.material.textfield.TextInputLayout;

public class AddBankActivity extends AppCompatActivity {

    AutoCompleteTextView bankAutoCompleteTextView;
    TextInputLayout bankNameTextInput;

    public void nextTapped (View view) {
        Intent intent = new Intent(this, MessageDetailsActivity.class);
        startActivity(intent);
    }

    public void cancelTapped (View view) {
        finish();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_bank);

        bankAutoCompleteTextView = findViewById(R.id.bankAutoCompleteTextView);
        bankNameTextInput = findViewById(R.id.bankNameTextInput);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this, android.R.layout.simple_list_item_1, getBankList());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        bankAutoCompleteTextView.setAdapter(adapter);
        setOnClickListeners();
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