package com.example.bamboo.fragment.ui.adapter;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import com.example.bamboo.R;

public class MyDialog extends AlertDialog {
    public MyDialog(Context context) {
        super(context);
    }

    public MyDialog(Context context, int themeResId) {
        super(context, themeResId);
    }
}
