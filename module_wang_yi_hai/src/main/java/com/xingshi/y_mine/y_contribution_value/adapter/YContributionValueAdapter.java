package com.xingshi.y_mine.y_contribution_value.adapter;

import android.content.Context;

import com.xingshi.adapter.MyRecyclerAdapter;
import com.xingshi.adapter.RecyclerViewHolder;
import com.xingshi.bean.CurrencyBalanceHistoryBean;
import com.xingshi.y_main.R;

import java.util.List;

public class YContributionValueAdapter extends MyRecyclerAdapter<CurrencyBalanceHistoryBean.RecordsBean> {

    public YContributionValueAdapter(Context context, List<CurrencyBalanceHistoryBean.RecordsBean> mList, int mLayoutId) {
        super(context, mList, mLayoutId);
    }

    @Override
    public void convert(RecyclerViewHolder holder, CurrencyBalanceHistoryBean.RecordsBean data, int position) {
        holder.setText(R.id.y_contribution_value_rec_name, data.getText())
                .setText(R.id.y_contribution_value_rec_time, data.getCreateTime())
                .setText(R.id.y_contribution_value_rec_price, data.getNum());
    }
}
