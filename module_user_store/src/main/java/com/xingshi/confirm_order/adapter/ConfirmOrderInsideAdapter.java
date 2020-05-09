package com.xingshi.confirm_order.adapter;

import android.content.Context;

import com.xingshi.adapter.MyRecyclerAdapter;
import com.xingshi.adapter.RecyclerViewHolder;
import com.xingshi.bean.CartBean;
import com.xingshi.user_store.R;

import java.util.List;

public class ConfirmOrderInsideAdapter extends MyRecyclerAdapter<CartBean.RecordsBean.ItemsBean> {
    public ConfirmOrderInsideAdapter(Context context, List<CartBean.RecordsBean.ItemsBean> mList, int mLayoutId) {
        super(context, mList, mLayoutId);
    }

    @Override
    public void convert(RecyclerViewHolder holder, CartBean.RecordsBean.ItemsBean data, int position) {
        holder.setText(R.id.confirm_order_count, data.getQuantity() + "")
                .setImageUrl(R.id.confirm_order_img, data.getProductPic())
                .setText(R.id.confirm_order_goods, data.getProductName())
                .setText(R.id.confirm_order_color, data.getProductAttr())
                .setText(R.id.confirm_order_price, "￥" + data.getPrice());

        if (viewTwoOnClickListener != null) {
            viewTwoOnClickListener.ViewTwoOnClick(holder.getView(R.id.confirm_order_minus), holder.getView(R.id.confirm_order_add), position);
        }
    }
}
