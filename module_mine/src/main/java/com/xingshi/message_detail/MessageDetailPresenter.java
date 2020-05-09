package com.xingshi.message_detail;

import android.content.Context;

import com.xingshi.mvp.BasePresenter;

public class MessageDetailPresenter extends BasePresenter<MessageDetailView> {
    public MessageDetailPresenter(Context context) {
        super(context);
    }

    @Override
    protected void onViewDestroy() {

    }
}
