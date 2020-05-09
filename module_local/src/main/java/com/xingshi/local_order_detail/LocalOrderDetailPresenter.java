package com.xingshi.local_order_detail;

import android.content.Context;

import com.xingshi.mvp.BasePresenter;

public class LocalOrderDetailPresenter extends BasePresenter<LocalOrderDetailView> {
    public LocalOrderDetailPresenter(Context context) {
        super(context);
    }

    @Override
    protected void onViewDestroy() {

    }
}
