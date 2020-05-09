package com.xingshi.y_deal.appeal.adapter;

import android.content.Context;

import com.xingshi.adapter.MyRecyclerAdapter;
import com.xingshi.adapter.RecyclerViewHolder;
import com.xingshi.bean.AppealBean;
import com.xingshi.y_main.R;

import java.util.List;

public class AppealAdapter extends MyRecyclerAdapter<AppealBean.ComplaintImgBean> {

    public AppealAdapter(Context context, List<AppealBean.ComplaintImgBean> mList, int mLayoutId) {
        super(context, mList, mLayoutId);
    }

    @Override
    public void convert(RecyclerViewHolder holder, AppealBean.ComplaintImgBean data, int position) {
        holder.setImageFresco(R.id.item_image, data.getImgUrl() == null ? "" : data.getImgUrl());
    }
}
