package com.xingshi.local_order_confirm.adapter;

import android.content.Context;

import com.xingshi.adapter.MyRecyclerAdapter;
import com.xingshi.adapter.RecyclerViewHolder;
import com.xingshi.bean.LocalOrderBean;
import com.xingshi.module_local.R;

import java.util.List;

public class LocalOrderConfirmAdapter extends MyRecyclerAdapter<LocalOrderBean.LocalOrderItemListBean> {
    public LocalOrderConfirmAdapter(Context context, List<LocalOrderBean.LocalOrderItemListBean> mList, int mLayoutId) {
        super(context, mList, mLayoutId);
    }

    @Override
    public void convert(RecyclerViewHolder holder, LocalOrderBean.LocalOrderItemListBean data, int position) {
        holder.setImageUrl(R.id.rv_local_order_confirm_img, data.getGoodsPic())
                .setText(R.id.rv_local_order_confirm_title, data.getGoodsName())
                .setText(R.id.rv_local_order_confirm_count, "X" + data.getGoodsNum())
                .setText(R.id.rv_local_order_confirm_money, "￥" + data.getGoodsPrice());
    }
}
