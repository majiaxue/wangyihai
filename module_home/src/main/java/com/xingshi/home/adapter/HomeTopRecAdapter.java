package com.xingshi.home.adapter;

import android.content.Context;

import com.xingshi.adapter.MyRecyclerAdapter;
import com.xingshi.adapter.RecyclerViewHolder;
import com.xingshi.entity.BaseRecImageAndTextBean;
import com.xingshi.module_home.R;

import java.util.List;

/**
 * Created by cuihaohao on 2019/5/15
 * Describe:
 */
public class HomeTopRecAdapter extends MyRecyclerAdapter<BaseRecImageAndTextBean> {

    public HomeTopRecAdapter(Context context, List mList, int mLayoutId) {
        super(context, mList, mLayoutId);
    }

    @Override
    public void convert(RecyclerViewHolder holder, BaseRecImageAndTextBean data, int position) {
        holder.setText(R.id.home_top_rec_text, data.getName());
        holder.setImageResource(R.id.home_top_rec_image,data.getImage());

    }



}
