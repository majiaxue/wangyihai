package com.xingshi.y_mine.auditing.adapter;

import android.content.Context;

import com.xingshi.adapter.MyRecyclerAdapter;
import com.xingshi.adapter.RecyclerViewHolder;
import com.xingshi.bean.TaskListDetailsBean;
import com.xingshi.y_main.R;

import java.util.List;

public class ImgAdapter extends MyRecyclerAdapter<TaskListDetailsBean.ImgBean> {
    public ImgAdapter(Context context, List<TaskListDetailsBean.ImgBean> mList, int mLayoutId) {
        super(context, mList, mLayoutId);
    }

    @Override
    public void convert(RecyclerViewHolder holder, TaskListDetailsBean.ImgBean data, int position) {
        holder.setImageUrl(R.id.img,data.getImgUrl());
    }
}
