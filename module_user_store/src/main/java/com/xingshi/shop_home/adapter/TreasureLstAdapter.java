package com.xingshi.shop_home.adapter;

import android.content.Context;

import com.xingshi.adapter.MyRecyclerAdapter;
import com.xingshi.adapter.RecyclerViewHolder;
import com.xingshi.entity.BabyRecBean;
import com.xingshi.user_store.R;

import java.util.List;

public class TreasureLstAdapter extends MyRecyclerAdapter<BabyRecBean> {
    public TreasureLstAdapter(Context context, List<BabyRecBean> mList, int mLayoutId) {
        super(context, mList, mLayoutId);
    }

    @Override
    public void convert(RecyclerViewHolder holder, BabyRecBean data, int position) {
        holder.setImageResource(R.id.baby_image, data.getImage()).
                setText(R.id.baby_name, data.getName())
                .setText(R.id.baby_price, data.getPrice())
                .setText(R.id.baby_payment_amount, data.getPayment_amount())
                .setText(R.id.baby_good_reputation, data.getGood_reputation())
                .setText(R.id.baby_shop, data.getShop());
    }
}
