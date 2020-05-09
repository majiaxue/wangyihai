package com.xingshi.first_page;

import android.content.Context;

import com.xingshi.mvp.BasePresenter;

public class FirstPagePresenter extends BasePresenter<FirstPageView> {
    public FirstPagePresenter(Context context) {
        super(context);
    }

    @Override
    protected void onViewDestroy() {

    }
}
