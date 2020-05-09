package com.xingshi.utils;

import android.view.View;
import android.widget.PopupWindow;

public interface OnConfirmPaymentListener {
    void setConfirmPayment(final PopupWindow pop, View passwordInput, View confirm);
}
