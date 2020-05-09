package com.xingshi.y_mine.y_my_task.adapter;

import android.content.Context;

import com.xingshi.adapter.MyRecyclerAdapter;
import com.xingshi.adapter.RecyclerViewHolder;
import com.xingshi.bean.YMyTaskBean;
import com.xingshi.y_main.R;

import java.util.List;

public class YMyTaskAdapter extends MyRecyclerAdapter<YMyTaskBean.RecordsBean> {

    public YMyTaskAdapter(Context context, List<YMyTaskBean.RecordsBean> mList, int mLayoutId) {
        super(context, mList, mLayoutId);
    }

    @Override
    public void convert(RecyclerViewHolder holder, YMyTaskBean.RecordsBean data, int position) {
        if (data.getSurplusNumber() != 0) {
            holder.setText(R.id.y_task_conversion, "去完成");
        } else {
            int day = data.getSurplusDay() + data.getCompleteDay();
            if (40 == day) {
                holder.setText(R.id.y_task_conversion, "已完成");
            } else {
                holder.setText(R.id.y_task_conversion, "补签");
            }
        }

        holder.setText(R.id.y_task_rec_grade, data.getTaskName())
                .setText(R.id.y_task_rec_num, "(" + data.getConsume() + "个币)")
                .setText(R.id.y_task_rec_earnings, "收益:" + data.getProfit() + "个币    周期:" + data.getCycle() + "天")
                .setText(R.id.y_task_text, "任务:每天查看" + data.getTaskContent() + "个商品")
                .setImageFresco(R.id.y_task_rec_image, data.getPicture() == null ? "" : data.getPicture());

        viewOnClickListener.ViewOnClick(holder.getView(R.id.y_task_conversion), position);
    }
}
