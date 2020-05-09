package com.xingshi.adapter;

import android.content.Context;

import com.xingshi.bean.UserCouponBean;
import com.xingshi.module_base.R;

import java.util.List;

public class PopCouponAdapter extends MyRecyclerAdapter<UserCouponBean> {

    public PopCouponAdapter(Context context, List<UserCouponBean> mList, int mLayoutId) {
        super(context, mList, mLayoutId);
    }

    @Override
    public void convert(RecyclerViewHolder holder, UserCouponBean data, int position) {
        holder.setText(R.id.coupon_rec1_price, "￥" + data.getAmount());
        holder.setText(R.id.coupon_rec1_max_price, "满" + data.getMinPoint() + "使用");
        if (data.isHas()) {
            holder.setText(R.id.coupon_rec1_immediately_get, "已领取");
            holder.getView(R.id.coupon_rec1_immediately_get).setBackground(context.getResources().getDrawable(R.drawable.bg_15_999));
            holder.getView(R.id.coupon_rec1_immediately_get).setEnabled(false);
        } else {
            holder.setText(R.id.coupon_rec1_immediately_get, "立即领取");
            holder.getView(R.id.coupon_rec1_immediately_get).setBackground(context.getResources().getDrawable(R.drawable.bg_15_jianbian));
            holder.getView(R.id.coupon_rec1_immediately_get).setEnabled(true);
        }
        viewOnClickListener.ViewOnClick(holder.getView(R.id.coupon_rec1_immediately_get), position);
    }
}
