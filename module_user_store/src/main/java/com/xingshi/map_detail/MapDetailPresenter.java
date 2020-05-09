package com.xingshi.map_detail;

import android.content.Context;

import com.xingshi.mvp.BasePresenter;

public class MapDetailPresenter extends BasePresenter<MapDetailView> {
    public MapDetailPresenter(Context context) {
        super(context);
    }

    @Override
    protected void onViewDestroy() {

    }
}
