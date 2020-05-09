package com.xingshi.y_deal.trading_center.adapter;

import android.content.Context;

import com.xingshi.adapter.MyRecyclerAdapter;
import com.xingshi.adapter.RecyclerViewHolder;
import com.xingshi.bean.TradingCenterBean;
import com.xingshi.y_main.R;

import java.util.List;

public class TradingCenterSellAdapter extends MyRecyclerAdapter<TradingCenterBean.RecordsBean> {

    public TradingCenterSellAdapter(Context context, List<TradingCenterBean.RecordsBean> mList, int mLayoutId) {
        super(context, mList, mLayoutId);
    }

    @Override
    public void convert(RecyclerViewHolder holder, TradingCenterBean.RecordsBean data, int position) {
        holder.setImageFresco(R.id.y_deal_rec_pic, data.getIcon() == null ? "" : data.getIcon())
                .setText(R.id.y_deal_rec_name, data.getUserName())
                .setText(R.id.y_deal_rec_id, "ID：" + data.getUserCode())
                .setText(R.id.y_deal_rec_content, "买入" + data.getNumber() + "币种")
                .setText(R.id.y_deal_rec_sell, "买入");

        viewOnClickListener.ViewOnClick(holder.getView(R.id.y_deal_rec_sell), position);
    }
}
