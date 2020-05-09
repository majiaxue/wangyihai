package com.xingshi.shop_home.adapter;

import android.content.Context;
import android.view.ViewGroup;

import com.xingshi.adapter.MyRecyclerAdapter;
import com.xingshi.adapter.RecyclerViewHolder;
import com.xingshi.entity.BabyRecBean;
import com.xingshi.user_store.R;
import com.xingshi.utils.DisplayUtil;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

public class TreasureWaterfallAdapter extends MyRecyclerAdapter<BabyRecBean> {
    public TreasureWaterfallAdapter(Context context, List<BabyRecBean> mList, int mLayoutId) {
        super(context, mList, mLayoutId);
    }

    @Override
    public void convert(RecyclerViewHolder holder, BabyRecBean data, int position) {
        if (position == 0) {
            SimpleDraweeView simpleDraweeView = holder.getView(R.id.rec_staggered_image);
            ViewGroup.LayoutParams layoutParams = simpleDraweeView.getLayoutParams();
            layoutParams.height = DisplayUtil.dip2px(context, 142);
        } else {

        }
        holder.setImageResource(R.id.rec_staggered_image, data.getImage())
                .setText(R.id.rec_staggered_name, data.getName())
                .setText(R.id.rec_staggered_price, data.getPrice())
                .setText(R.id.rec_staggered_payment_amount, data.getPayment_amount())
                .setText(R.id.rec_staggered_shop, data.getShop());
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }
}
