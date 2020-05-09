package com.xingshi.y_mine.y_welfare_center.pay_a_tip.adapter;

import android.content.Context;
import android.view.View;

import com.xingshi.adapter.MyRecyclerAdapter;
import com.xingshi.adapter.RecyclerViewHolder;
import com.xingshi.bean.PayATipBean;
import com.xingshi.y_main.R;

import java.util.List;

public class PayATipAdapter extends MyRecyclerAdapter<PayATipBean.RecordsBean> {

    public PayATipAdapter(Context context, List<PayATipBean.RecordsBean> mList, int mLayoutId) {
        super(context, mList, mLayoutId);
    }

    @Override
    public void convert(RecyclerViewHolder holder, PayATipBean.RecordsBean data, int position) {

        holder.getView(R.id.pay_a_tip_rec_name).setVisibility(View.INVISIBLE);
        holder.getView(R.id.pay_a_tip_rec_places).setVisibility(View.INVISIBLE);
        holder.getView(R.id.pay_a_tip_rec_text).setVisibility(View.INVISIBLE);

        holder.setImageFresco(R.id.pay_a_tip_rec_pic, data.getIcon() == null ? "" : data.getIcon())
                .setText(R.id.pay_a_tip_rec_title, "标题：" + data.getTitle())
                .setText(R.id.pay_a_tip_rec_common, "普通" + data.getOrdinaryPrice())
                .setText(R.id.pay_a_tip_rec_shareholder, "股东" + data.getShareholderPrice());
    }
}
