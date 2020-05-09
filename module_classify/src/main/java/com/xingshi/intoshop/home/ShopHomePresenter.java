package com.xingshi.intoshop.home;

import android.content.Context;

import com.xingshi.mvp.BasePresenter;

/**
 * Created by cuihaohao on 2019/5/21
 * Describe:
 */
public class ShopHomePresenter extends BasePresenter<ShopHomeView> {

    public ShopHomePresenter(Context context) {
        super(context);
    }

    @Override
    protected void onViewDestroy() {

    }
}
