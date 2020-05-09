package com.xingshi.y_mine.y_balance_account.adapter;

import android.content.Context;

import com.xingshi.adapter.MyRecyclerAdapter;
import com.xingshi.adapter.RecyclerViewHolder;
import com.xingshi.bean.YBalanceAccountBean;
import com.xingshi.y_main.R;

import java.util.List;

public class YBalanceAccountAdapter extends MyRecyclerAdapter<YBalanceAccountBean.RecordsBean> {

    public YBalanceAccountAdapter(Context context, List<YBalanceAccountBean.RecordsBean> mList, int mLayoutId) {
        super(context, mList, mLayoutId);
    }

    @Override
    public void convert(RecyclerViewHolder holder, YBalanceAccountBean.RecordsBean data, int position) {
        holder.setText(R.id.y_contribution_value_rec_name, data.getBz())
                .setText(R.id.y_contribution_value_rec_time, data.getCreateTime());
        if (1 == data.getType()) {
            holder.setText(R.id.y_contribution_value_rec_price, "-" + data.getPrice());
        } else {
            holder.setText(R.id.y_contribution_value_rec_price, "+" + data.getPrice());
        }
    }
}
