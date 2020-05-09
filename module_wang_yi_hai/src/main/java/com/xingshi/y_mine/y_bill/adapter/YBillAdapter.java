package com.xingshi.y_mine.y_bill.adapter;

import android.content.Context;

import com.xingshi.adapter.MyRecyclerAdapter;
import com.xingshi.adapter.RecyclerViewHolder;
import com.xingshi.bean.YBillBean;
import com.xingshi.y_main.R;

import java.util.List;

public class YBillAdapter extends MyRecyclerAdapter<YBillBean.RecordsBean> {

    public YBillAdapter(Context context, List<YBillBean.RecordsBean> mList, int mLayoutId) {
        super(context, mList, mLayoutId);
    }

    @Override
    public void convert(RecyclerViewHolder holder, YBillBean.RecordsBean data, int position) {
        if (0 == data.getType()) {//0是收入
            holder.setText(R.id.y_contribution_value_rec_price, "+" + data.getPrice());
        } else {
            //1是支出
            holder.setText(R.id.y_contribution_value_rec_price, "-" + data.getPrice());
        }
        holder.setText(R.id.y_contribution_value_rec_name, data.getNote())
                .setText(R.id.y_contribution_value_rec_time, data.getCreateTime());

    }
}
