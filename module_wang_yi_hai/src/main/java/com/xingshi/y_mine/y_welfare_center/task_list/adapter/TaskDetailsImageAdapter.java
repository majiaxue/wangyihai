package com.xingshi.y_mine.y_welfare_center.task_list.adapter;

import android.content.Context;

import com.xingshi.adapter.MyRecyclerAdapter;
import com.xingshi.adapter.RecyclerViewHolder;
import com.xingshi.bean.TaskListDetailsBean;
import com.xingshi.y_main.R;

import java.util.List;

public class TaskDetailsImageAdapter extends MyRecyclerAdapter<TaskListDetailsBean.ImgBean> {

    public TaskDetailsImageAdapter(Context context, List<TaskListDetailsBean.ImgBean> mList, int mLayoutId) {
        super(context, mList, mLayoutId);
    }

    @Override
    public void convert(RecyclerViewHolder holder, TaskListDetailsBean.ImgBean data, int position) {
        holder.setImageFresco(R.id.item_image, data.getImgUrl() == null ? "" : data.getImgUrl());
    }
}
