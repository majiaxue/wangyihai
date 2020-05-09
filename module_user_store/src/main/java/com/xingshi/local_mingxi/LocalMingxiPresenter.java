package com.xingshi.local_mingxi;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.xingshi.adapter.MyRecyclerAdapter;
import com.xingshi.local_detail.LocalDetailActivity;
import com.xingshi.local_mingxi.adapter.LocalMingxiAdapter;
import com.xingshi.mvp.BasePresenter;
import com.xingshi.user_store.R;

import java.util.ArrayList;
import java.util.List;

public class LocalMingxiPresenter extends BasePresenter<LocalMingxiView> {

    private LocalMingxiAdapter adapter;

    public LocalMingxiPresenter(Context context) {
        super(context);
    }

    @Override
    protected void onViewDestroy() {

    }

    public void loadData() {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add("a");
        }

        adapter = new LocalMingxiAdapter(mContext, list, R.layout.rv_local_mingxi_rv);
        if (getView() != null) {
            getView().loadRv(adapter);
        }

        adapter.setOnItemClick(new MyRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerView parent, View view, int position) {
                Intent intent = new Intent(mContext, LocalDetailActivity.class);
                mContext.startActivity(intent);
            }
        });
    }
}
