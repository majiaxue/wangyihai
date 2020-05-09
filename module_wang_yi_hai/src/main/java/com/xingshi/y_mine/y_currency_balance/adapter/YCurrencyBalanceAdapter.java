package com.xingshi.y_mine.y_currency_balance.adapter;

import android.content.Context;

import com.xingshi.adapter.MyRecyclerAdapter;
import com.xingshi.adapter.RecyclerViewHolder;
import com.xingshi.bean.CurrencyBalanceHistoryBean;
import com.xingshi.y_main.R;

import java.util.List;

public class YCurrencyBalanceAdapter extends MyRecyclerAdapter<CurrencyBalanceHistoryBean.RecordsBean> {

    public YCurrencyBalanceAdapter(Context context, List<CurrencyBalanceHistoryBean.RecordsBean> mList, int mLayoutId) {
        super(context, mList, mLayoutId);
    }

    @Override
    public void convert(RecyclerViewHolder holder, CurrencyBalanceHistoryBean.RecordsBean data, int position) {
        if (0 == data.getStatus()) {
            holder.setImageResource(R.id.y_currency_balance_rec_pic, R.drawable.gomai);
        } else {
            holder.setImageResource(R.id.y_currency_balance_rec_pic, R.drawable.maichu);
        }
        holder.setText(R.id.y_currency_balance_rec_name, data.getText())
                .setText(R.id.y_currency_balance_rec_time, data.getCreateTime())
                .setText(R.id.y_currency_balance_rec_price, data.getNum())
                .setText(R.id.y_currency_balance_rec_aliPay_account, data.getOpenid() == null ? "" : data.getOpenid());
    }
}
