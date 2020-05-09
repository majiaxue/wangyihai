package com.xingshi.user_home.adapter;

import android.content.Context;

import com.xingshi.adapter.MyRecyclerAdapter;
import com.xingshi.adapter.RecyclerViewHolder;
import com.xingshi.bean.HotSaleBean;
import com.xingshi.user_store.R;

import java.util.List;

public class SaleHotAdapter extends MyRecyclerAdapter<HotSaleBean.DataBean> {
    public SaleHotAdapter(Context context, List<HotSaleBean.DataBean> mList, int mLayoutId) {
        super(context, mList, mLayoutId);
    }

    @Override
    public void convert(RecyclerViewHolder holder, HotSaleBean.DataBean data, int position) {
        holder.setImageUrl(R.id.rv_hot_image, data.getPic())
                .setText(R.id.rv_hot_name, data.getName())
                .setText(R.id.rv_hot_price_new, "￥" + data.getPrice())
                .setText(R.id.rv_hot_price_old, data.getPrice() + "")
                .setTextLine(R.id.rv_hot_price_old);
    }
}
