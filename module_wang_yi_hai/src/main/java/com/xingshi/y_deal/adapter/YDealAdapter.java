package com.xingshi.y_deal.adapter;

import android.content.Context;

import com.xingshi.adapter.MyRecyclerAdapter;
import com.xingshi.adapter.RecyclerViewHolder;
import com.xingshi.bean.YDealBean;
import com.xingshi.y_main.R;

import java.util.List;

public class YDealAdapter extends MyRecyclerAdapter<YDealBean.RecordsBean> {

    public YDealAdapter(Context context, List<YDealBean.RecordsBean> mList, int mLayoutId) {
        super(context, mList, mLayoutId);
    }

    @Override
    public void convert(RecyclerViewHolder holder, YDealBean.RecordsBean data, int position) {
        holder.setImageFresco(R.id.y_deal_rec_pic, data.getIcon() == null ? "" : data.getIcon())
                .setText(R.id.y_deal_rec_name, data.getUserName())
                .setText(R.id.y_deal_rec_id, "ID：" + data.getId())
                .setText(R.id.y_deal_rec_content, "买入" + data.getNumber() + "币种");

        viewOnClickListener.ViewOnClick(holder.getView(R.id.y_deal_rec_sell), position);
    }
}
