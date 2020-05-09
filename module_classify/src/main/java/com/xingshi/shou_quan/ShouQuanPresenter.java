package com.xingshi.shou_quan;

import android.content.Context;

import com.xingshi.mvp.BasePresenter;

public class ShouQuanPresenter extends BasePresenter<ShouQuanView> {
    public ShouQuanPresenter(Context context) {
        super(context);
    }

    @Override
    protected void onViewDestroy() {

    }
}
