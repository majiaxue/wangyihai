package com.xingshi.new_guide;

import android.content.Context;

import com.xingshi.mvp.BasePresenter;

public class NewGuidePresenter extends BasePresenter<NewGuideView> {
    public NewGuidePresenter(Context context) {
        super(context);
    }

    @Override
    protected void onViewDestroy() {

    }
}
