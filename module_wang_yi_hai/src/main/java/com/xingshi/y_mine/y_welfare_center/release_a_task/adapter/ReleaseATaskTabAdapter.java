package com.xingshi.y_mine.y_welfare_center.release_a_task.adapter;

import android.content.Context;

import com.xingshi.adapter.MyRecyclerAdapter;
import com.xingshi.adapter.RecyclerViewHolder;
import com.xingshi.bean.ReleaseATaskTabBean;
import com.xingshi.y_main.R;

import java.util.List;

public class ReleaseATaskTabAdapter extends MyRecyclerAdapter<ReleaseATaskTabBean> {

    public ReleaseATaskTabAdapter(Context context, List<ReleaseATaskTabBean> mList, int mLayoutId) {
        super(context, mList, mLayoutId);
    }

    @Override
    public void convert(RecyclerViewHolder holder, ReleaseATaskTabBean data, int position) {
        if (data.isClick()) {
            holder.setBackgroundResource(R.id.item_text, R.drawable.bg_4_fff);
        } else {
            holder.setBackgroundResource(R.id.item_text, 0);
        }

        holder.setText(R.id.item_text, data.getParamName());
    }
}
