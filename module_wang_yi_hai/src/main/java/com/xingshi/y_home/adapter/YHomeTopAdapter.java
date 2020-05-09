package com.xingshi.y_home.adapter;

import android.content.Context;

import com.xingshi.adapter.MyRecyclerAdapter;
import com.xingshi.adapter.RecyclerViewHolder;
import com.xingshi.entity.BaseRecImageAndTextBean;
import com.xingshi.y_main.R;

import java.util.List;

/**
 * Created by cuihaohao on 2019/5/15
 * Describe:
 */
public class YHomeTopAdapter extends MyRecyclerAdapter<BaseRecImageAndTextBean> {

    public YHomeTopAdapter(Context context, List<BaseRecImageAndTextBean> mList, int mLayoutId) {
        super(context, mList, mLayoutId);
    }

    @Override
    public void convert(RecyclerViewHolder holder, BaseRecImageAndTextBean data, int position) {
        holder.setText(R.id.y_home_top_rec_text, data.getName());
        holder.setImageResource(R.id.y_home_top_rec_image,data.getImage());

    }



}
