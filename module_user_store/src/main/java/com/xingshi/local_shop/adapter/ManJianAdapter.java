package com.xingshi.local_shop.adapter;

import android.content.Context;

import com.xingshi.adapter.MyRecyclerAdapter;
import com.xingshi.adapter.RecyclerViewHolder;
import com.xingshi.bean.UserCouponBean;
import com.xingshi.user_store.R;

import java.util.List;

public class ManJianAdapter extends MyRecyclerAdapter<UserCouponBean> {
    public ManJianAdapter(Context context, List<UserCouponBean> mList, int mLayoutId) {
        super(context, mList, mLayoutId);
    }

    @Override
    public void convert(RecyclerViewHolder holder, UserCouponBean data, int position) {
        if (data.getMinPoint() == 0) {
            holder.setText(R.id.rv_local_seller_rv_txt, "领券减" + data.getAmount() + "元");
        } else {
            holder.setText(R.id.rv_local_seller_rv_txt, "满" + data.getMinPoint() + "减" + data.getAmount());
        }
    }
}
