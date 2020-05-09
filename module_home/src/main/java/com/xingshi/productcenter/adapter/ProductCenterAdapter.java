package com.xingshi.productcenter.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.xingshi.adapter.MyRecyclerAdapter;
import com.xingshi.adapter.RecyclerViewHolder;
import com.xingshi.bean.ProductCenterBean;
import com.xingshi.module_home.R;

import java.util.List;

public class ProductCenterAdapter extends MyRecyclerAdapter<ProductCenterBean.RecordsBean> {

    public ProductCenterAdapter(Context context, List<ProductCenterBean.RecordsBean> mList, int mLayoutId) {
        super(context, mList, mLayoutId);
    }

    @Override
    public void convert(RecyclerViewHolder holder, ProductCenterBean.RecordsBean data, int position) {
        TextView ranking = holder.getView(R.id.product_center_rec_ranking);
        if (data.getSort() == 1) {
            ranking.setText("" + data.getSort());
            ranking.setVisibility(View.VISIBLE);
            ranking.setBackground(context.getResources().getDrawable(R.drawable.cpzx_icon_1));
        } else if (data.getSort() == 2) {
            ranking.setText("" + data.getSort());
            ranking.setVisibility(View.VISIBLE);
            ranking.setBackground(context.getResources().getDrawable(R.drawable.cpzx_icon_2));
        } else if (data.getSort() == 3) {
            ranking.setText("" + data.getSort());
            ranking.setVisibility(View.VISIBLE);
            ranking.setBackground(context.getResources().getDrawable(R.drawable.cpzx_icon_3));
        } else {
            ranking.setVisibility(View.INVISIBLE);
        }
        holder.setImageFresco(R.id.product_center_rec_pic, data.getLogo())
                .setText(R.id.product_center_rec_name, data.getTitle())
                .setText(R.id.product_center_rec_price, "￥" + data.getPrice())
                .setText(R.id.product_center_rec_intro, data.getMessage());
    }
}
