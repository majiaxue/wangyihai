package com.xingshi.message_list;

import android.content.Context;

import com.xingshi.entity.MessageListBean;
import com.xingshi.message_list.adapter.MessageListAdapter;
import com.xingshi.module_mine.R;
import com.xingshi.mvp.BasePresenter;

import java.util.ArrayList;
import java.util.List;

public class MessageListPresenter extends BasePresenter<MessageListView> {

    private List<MessageListBean> dataList;

    public MessageListPresenter(Context context) {
        super(context);
    }

    @Override
    protected void onViewDestroy() {

    }

    public void loadData() {
        dataList = new ArrayList<>();
        dataList.add(new MessageListBean("进账提醒", "您好，您已进账288元", "2018-1-1"));
        dataList.add(new MessageListBean("进账提醒", "您好，您已进账288元", "2018-1-1"));
        dataList.add(new MessageListBean("进账提醒", "您好，您已进账288元", "2018-1-1"));
        dataList.add(new MessageListBean("进账提醒", "您好，您已进账288元", "2018-1-1"));
        dataList.add(new MessageListBean("进账提醒", "您好，您已进账288元", "2018-1-1"));
        dataList.add(new MessageListBean("进账提醒", "您好，您已进账288元", "2018-1-1"));
        MessageListAdapter listAdapter = new MessageListAdapter(mContext, dataList, R.layout.rv_message_list);
        if (getView() != null) {
            getView().loadUI(listAdapter);
        }
    }
}
