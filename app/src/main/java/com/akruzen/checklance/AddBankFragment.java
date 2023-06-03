package com.akruzen.checklance;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

public class AddBankFragment extends DialogFragment implements DialogInterface.OnCancelListener, DialogInterface.OnDismissListener {
    public AddBankFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NO_FRAME, com.google.android.material.R.style.Theme_Material3_DynamicColors_DayNight);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_bank, container, false);
    }

    @Override
    public void onCancel(@NonNull DialogInterface dialog) {

    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {

    }
}