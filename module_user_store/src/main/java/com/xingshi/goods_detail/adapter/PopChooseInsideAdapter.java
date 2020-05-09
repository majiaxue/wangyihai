package com.xingshi.goods_detail.adapter;

import android.content.Context;

import com.xingshi.adapter.MyRecyclerAdapter;
import com.xingshi.adapter.RecyclerViewHolder;
import com.xingshi.bean.ChooseInsideBean;

import java.util.List;

public class PopChooseInsideAdapter extends MyRecyclerAdapter<ChooseInsideBean> {
    public PopChooseInsideAdapter(Context context, List<ChooseInsideBean> mList, int mLayoutId) {
        super(context, mList, mLayoutId);
    }

    @Override
    public void convert(RecyclerViewHolder holder, ChooseInsideBean data, int position) {

    }
}
