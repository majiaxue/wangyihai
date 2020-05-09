package com.xingshi.y_task.adapter;

import android.content.Context;

import com.xingshi.adapter.MyRecyclerAdapter;
import com.xingshi.adapter.RecyclerViewHolder;
import com.xingshi.bean.YTaskBean;
import com.xingshi.y_main.R;

import java.util.List;

public class YTaskAdapter extends MyRecyclerAdapter<YTaskBean> {

    public YTaskAdapter(Context context, List<YTaskBean> mList, int mLayoutId) {
        super(context, mList, mLayoutId);
    }

    @Override
    public void convert(RecyclerViewHolder holder, YTaskBean data, int position) {
        holder.setText(R.id.y_task_rec_grade, data.getTaskLevel())
                .setText(R.id.y_task_rec_num, "(" + data.getConsume() + "个币)")
                .setText(R.id.y_task_rec_earnings, "收益:" + data.getProfit() + "个币    周期:" + data.getCycle() + "天")
                .setText(R.id.y_task_text, "任务:每天查看" + data.getContent() + "个商品")
                .setImageFresco(R.id.y_task_rec_image, data.getPicture() == null ? "" : data.getPicture());

        viewOnClickListener.ViewOnClick(holder.getView(R.id.y_task_conversion), position);
    }
}
