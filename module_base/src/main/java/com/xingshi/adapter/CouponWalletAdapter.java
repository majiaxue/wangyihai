package com.xingshi.adapter;

import android.content.Context;
import android.text.TextUtils;

import com.xingshi.bean.RedPackageBean;
import com.xingshi.module_base.R;

import java.util.List;

public class CouponWalletAdapter extends MyRecyclerAdapter<RedPackageBean> {
    public CouponWalletAdapter(Context context, List<RedPackageBean> mList, int mLayoutId) {
        super(context, mList, mLayoutId);
    }

    @Override
    public void convert(RecyclerViewHolder holder, RedPackageBean data, int position) {
        holder.setText(R.id.rv_coupon_wallet_money, (int) (Double.valueOf(data.getMoney()) / Double.valueOf(data.getCount())) + "")
                .setText(R.id.rv_coupon_wallet_count, TextUtils.isEmpty(data.getCount()) ? "1" : data.getCount());
    }
}
