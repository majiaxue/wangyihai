package com.xingshi.type_detail.adapter;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.xingshi.adapter.MyRecyclerAdapter;
import com.xingshi.adapter.RecyclerViewHolder;
import com.xingshi.bean.HotSaleBean;
import com.xingshi.user_store.R;

import java.util.List;

public class TypeDetailWaterfallAdapter extends MyRecyclerAdapter<HotSaleBean.DataBean> {
    public TypeDetailWaterfallAdapter(Context context, List<HotSaleBean.DataBean> mList, int mLayoutId) {
        super(context, mList, mLayoutId);
    }

    @Override
    public void convert(RecyclerViewHolder holder, HotSaleBean.DataBean data, int position) {
        if (position == 0) {
            ImageView img = holder.getView(R.id.rv_commend_img);
            ViewGroup.LayoutParams params = img.getLayoutParams();
            params.height = (int) context.getResources().getDimension(R.dimen.dp_142);
            img.setLayoutParams(params);
        }

        holder.setText(R.id.rv_commend_name, data.getName())
                .setText(R.id.rv_commend_price, "￥" + data.getPrice())
                .setText(R.id.rv_commend_count, data.getSale() + "人付款")
                .setText(R.id.rv_commend_shop, data.getSellerName())
                .setImageFresco(R.id.rv_commend_img, data.getPic());

        if (viewOnClickListener != null) {
            viewOnClickListener.ViewOnClick(holder.getView(R.id.rv_commend_btn), position);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }
}
