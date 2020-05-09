package com.xingshi.shop_home.adapter;

import android.content.Context;

import com.xingshi.adapter.MyRecyclerAdapter;
import com.xingshi.adapter.RecyclerViewHolder;
import com.xingshi.bean.ParmsBean;
import com.xingshi.user_store.R;
import com.xingshi.view.AutoScaleTextView;

import java.util.List;

public class FirstCouponAdapter extends MyRecyclerAdapter<ParmsBean> {
    public FirstCouponAdapter(Context context, List<ParmsBean> mList, int mLayoutId) {
        super(context, mList, mLayoutId);
    }

    @Override
    public void convert(RecyclerViewHolder holder, ParmsBean data, int position) {
        ((AutoScaleTextView) holder.getView(R.id.rv_shop_first_money)).setText(data.getKey());
        ((AutoScaleTextView) holder.getView(R.id.rv_shop_first_factor)).setText(data.getValue());
    }
}
