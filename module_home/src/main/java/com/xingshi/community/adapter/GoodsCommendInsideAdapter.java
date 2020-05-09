package com.xingshi.community.adapter;

import android.content.Context;

import com.xingshi.adapter.MyRecyclerAdapter;
import com.xingshi.adapter.RecyclerViewHolder;
import com.xingshi.module_home.R;

import java.util.List;

public class GoodsCommendInsideAdapter extends MyRecyclerAdapter<String> {
    public GoodsCommendInsideAdapter(Context context, List<String> mList, int mLayoutId) {
        super(context, mList, mLayoutId);
    }

    @Override
    public void convert(RecyclerViewHolder holder, String data, int position) {
        holder.setImageUrl(R.id.rv_goods_commend_inside_img, data);
    }
}
