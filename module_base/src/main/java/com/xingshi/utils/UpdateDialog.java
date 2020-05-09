package com.xingshi.utils;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import com.xingshi.module_base.R;

public class UpdateDialog extends Dialog {
    public UpdateDialog(Context context) {
        super(context, R.style.MyDialog);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pop_geng_xin);
    }
}
