package com.xingshi.mine.adapter;

import android.content.Context;

import com.xingshi.adapter.MyRecyclerAdapter;
import com.xingshi.adapter.RecyclerViewHolder;
import com.xingshi.entity.BaseRecImageAndTextBean;
import com.xingshi.module_home.R;

import java.util.List;

public class MyToolAdapter extends MyRecyclerAdapter<BaseRecImageAndTextBean> {

    public MyToolAdapter(Context context, List<BaseRecImageAndTextBean> mList, int mLayoutId) {
        super(context, mList, mLayoutId);
    }

    @Override
    public void convert(RecyclerViewHolder holder, BaseRecImageAndTextBean data, int position) {
        holder.setText(R.id.rv_mytool_txt, data.getName())
                .setImageResource(R.id.rv_mytool_img, data.getImage());

        viewOnClickListener.ViewOnClick(holder.getView(R.id.rv_mytool_parent), position);
    }
}
