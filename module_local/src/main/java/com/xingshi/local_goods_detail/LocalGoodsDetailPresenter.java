package com.xingshi.local_goods_detail;

import android.content.Context;

import com.xingshi.mvp.BasePresenter;

public class LocalGoodsDetailPresenter extends BasePresenter<LocalGoodsDetailView> {
    public LocalGoodsDetailPresenter(Context context) {
        super(context);
    }

    @Override
    protected void onViewDestroy() {

    }
}
