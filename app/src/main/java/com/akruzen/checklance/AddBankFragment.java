package com.akruzen.checklance;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.akruzen.checklance.Constants.Common;

import java.util.Objects;

public class AddBankFragment extends DialogFragment implements DialogInterface.OnCancelListener, DialogInterface.OnDismissListener {

    AutoCompleteTextView bankAutoCompleteTextView;

    public AddBankFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NO_FRAME, R.style.Base_Theme_Checklance);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_bank, container, false);
        bankAutoCompleteTextView = view.findViewById(R.id.bankAutoCompleteTextView);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this.getContext(), android.R.layout.simple_list_item_1, Common.getBankList());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        bankAutoCompleteTextView.setAdapter(adapter);
        return view;
    }

    @Override
    public void onCancel(@NonNull DialogInterface dialog) {
        dialog.cancel();
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        dialog.cancel();
    }
}