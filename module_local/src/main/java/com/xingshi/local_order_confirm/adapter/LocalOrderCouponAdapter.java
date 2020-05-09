package com.xingshi.local_order_confirm.adapter;

import android.content.Context;
import android.text.TextUtils;

import com.xingshi.adapter.MyRecyclerAdapter;
import com.xingshi.adapter.RecyclerViewHolder;
import com.xingshi.bean.RedPackageBean;

import java.util.List;

public class LocalOrderCouponAdapter extends MyRecyclerAdapter<RedPackageBean> {
    public LocalOrderCouponAdapter(Context context, List<RedPackageBean> mList, int mLayoutId) {
        super(context, mList, mLayoutId);
    }

    @Override
    public void convert(RecyclerViewHolder holder, RedPackageBean data, int position) {
        holder.setText(com.xingshi.module_base.R.id.rv_coupon_wallet_money, data.getMoney())
                .setText(com.xingshi.module_base.R.id.rv_coupon_wallet_count, TextUtils.isEmpty(data.getCount()) ? "1" : data.getCount());
    }
}
