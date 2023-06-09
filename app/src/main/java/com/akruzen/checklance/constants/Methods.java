package com.akruzen.checklance.constants;

import android.content.Context;
import android.view.MotionEvent;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;

public class Methods {

    public static MaterialAlertDialogBuilder showMaterialDialog (Context context, String title, String content) {
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(context);
        builder.setTitle(title);
        builder.setMessage(content);
        builder.setPositiveButton("Got it", (dialog, which) -> dialog.dismiss());
        builder.show();
        return builder;
    }

    public static boolean isEndIconTapped (MotionEvent event, TextInputEditText editText) {
        // Returns true if the end drawable of edit text is tapped. Else returns false
        if(event.getAction() == MotionEvent.ACTION_UP) {
            return event.getRawX() >= (editText.getRight() - editText.getCompoundDrawables()[2/*DRAWABLE_RIGHT*/].getBounds().width());
        }
        return false;
    }

}
