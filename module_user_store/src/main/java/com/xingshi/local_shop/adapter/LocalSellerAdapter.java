package com.xingshi.local_shop.adapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.xingshi.adapter.MyRecyclerAdapter;
import com.xingshi.adapter.RecyclerViewHolder;
import com.xingshi.bean.LocalShopBean;
import com.xingshi.user_store.R;
import com.xingshi.utils.ArithUtil;
import com.xingshi.utils.SpaceItemDecoration;
import com.xingshi.view.RatingBarView;

import java.util.List;

public class LocalSellerAdapter extends MyRecyclerAdapter<LocalShopBean> {

    public LocalSellerAdapter(Context context, List<LocalShopBean> mList, int mLayoutId) {
        super(context, mList, mLayoutId);
    }

    @Override
    public void convert(RecyclerViewHolder holder, LocalShopBean data, int position) {
        holder.setImageUrl(R.id.rv_local_seller_img, data.getSeller_logo())
                .setText(R.id.rv_local_seller_name, data.getSeller_shop_name())
                .setText(R.id.rv_local_seller_type, data.getSeller_category_name())
                .setText(R.id.rv_local_seller_address, data.getSeller_addredd());
        RatingBarView star = holder.getView(R.id.rv_local_seller_star);
        star.setClickable(false);
        star.setStar(Integer.valueOf(data.getStar()), false);
        if (data.getDistance() != null) {
            Integer integer = Integer.valueOf(data.getDistance().split("\\.")[0]);
            if (integer >= 1000) {
                holder.setText(R.id.rv_local_seller_distance, ArithUtil.div(integer * 1.0, 1000.0, 1) + "千米");
            } else {
                holder.setText(R.id.rv_local_seller_distance, integer + "米");
            }
        }
        RecyclerView rv = holder.getView(R.id.rv_local_seller_rv);
        GridLayoutManager layoutManager = new GridLayoutManager(context, 3);
        rv.setLayoutManager(layoutManager);
        if (rv.getItemDecorationCount() == 0) {
            rv.addItemDecoration(new SpaceItemDecoration(0, (int) context.getResources().getDimension(R.dimen.dp_10), 0, (int) context.getResources().getDimension(R.dimen.dp_10)));
        }

        ManJianAdapter adapter = new ManJianAdapter(context, data.getCouponList(), R.layout.rv_local_seller_inside);
        rv.setAdapter(adapter);
    }
}
