package com.xingshi.local_shop_map;


import android.content.Context;

import com.xingshi.mvp.BasePresenter;

public class LocalShopMapPresenter extends BasePresenter<LocalShopMapView> {

    public LocalShopMapPresenter(Context context) {
        super(context);
    }

    @Override
    protected void onViewDestroy() {

    }
}
