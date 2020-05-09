package com.xingshi.y_mine.y_welfare_center.commission_task_details.adapter;

import android.content.Context;

import com.xingshi.adapter.MyRecyclerAdapter;
import com.xingshi.adapter.RecyclerViewHolder;
import com.xingshi.bean.CommissionTaskDetailsBean;
import com.xingshi.y_main.R;

import java.util.List;

public class CommissionTaskDetailsImageAdapter extends MyRecyclerAdapter<CommissionTaskDetailsBean.ImgBean> {

    public CommissionTaskDetailsImageAdapter(Context context, List<CommissionTaskDetailsBean.ImgBean> mList, int mLayoutId) {
        super(context, mList, mLayoutId);
    }

    @Override
    public void convert(RecyclerViewHolder holder, CommissionTaskDetailsBean.ImgBean data, int position) {
        holder.setImageFresco(R.id.item_image, data.getImgUrl() == null ? "" : data.getImgUrl());
    }
}
