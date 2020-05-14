package com.xingshi.y_mine.auditing.adapter;

import android.content.Context;

import com.xingshi.adapter.MyRecyclerAdapter;
import com.xingshi.adapter.RecyclerViewHolder;
import com.xingshi.bean.AuditingBean;
import com.xingshi.y_main.R;

import java.util.List;

public class AudutingAdapter extends MyRecyclerAdapter<AuditingBean> {
    public AudutingAdapter(Context context, List<AuditingBean> mList, int mLayoutId) {
        super(context, mList, mLayoutId);
    }

    @Override
    public void convert(RecyclerViewHolder holder, AuditingBean data, int position) {
        holder.setImageUrlCircular(R.id.img,data.getIcon())
                .setText(R.id.tv_biaoti,data.getTaskName())
                .setText(R.id.tv_time,data.getEndTime());
    }
}
