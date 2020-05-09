package com.xingshi.y_mine.y_my_task.take_records.adapter;

import android.content.Context;

import com.xingshi.adapter.MyRecyclerAdapter;
import com.xingshi.adapter.RecyclerViewHolder;
import com.xingshi.bean.TakeRecordsBean;
import com.xingshi.y_main.R;

import java.util.List;

public class TakeRecordsAdapter extends MyRecyclerAdapter<TakeRecordsBean.RecordsBean> {

    public TakeRecordsAdapter(Context context, List<TakeRecordsBean.RecordsBean> mList, int mLayoutId) {
        super(context, mList, mLayoutId);
    }

    @Override
    public void convert(RecyclerViewHolder holder, TakeRecordsBean.RecordsBean data, int position) {
        holder.setText(R.id.take_records_rec_name, data.getTaskName())
                .setText(R.id.take_records_rec_time, data.getCreateTime());
        if (0 == data.getType()) {
            holder.setText(R.id.take_records_rec_type, "赠送");
        } else if (1 == data.getType()) {
            holder.setText(R.id.take_records_rec_type, "兑换");
        }
    }
}
