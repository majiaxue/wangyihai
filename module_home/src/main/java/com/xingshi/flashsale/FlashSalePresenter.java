package com.xingshi.flashsale;

import android.content.Context;

import com.xingshi.mvp.BasePresenter;

public class FlashSalePresenter extends BasePresenter<FlashSaleView> {

    public FlashSalePresenter(Context context) {
        super(context);
    }

    @Override
    protected void onViewDestroy() {

    }
}
