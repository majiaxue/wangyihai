package com.xingshi.y_mine.y_welfare_center.commission_task.adapter;

import android.content.Context;

import com.xingshi.adapter.MyRecyclerAdapter;
import com.xingshi.adapter.RecyclerViewHolder;
import com.xingshi.bean.CommissionTaskListBean;
import com.xingshi.y_main.R;

import java.util.List;

public class CommissionTaskAdapter extends MyRecyclerAdapter<CommissionTaskListBean.RecordsBean> {

    public CommissionTaskAdapter(Context context, List<CommissionTaskListBean.RecordsBean> mList, int mLayoutId) {
        super(context, mList, mLayoutId);
    }

    @Override
    public void convert(RecyclerViewHolder holder, CommissionTaskListBean.RecordsBean data, int position) {
        holder.setImageFresco(R.id.pay_a_tip_rec_pic, data.getIcon() == null ? "" : data.getIcon())
                .setText(R.id.pay_a_tip_rec_name, "类型: " + data.getTypeName())
                .setText(R.id.pay_a_tip_rec_title, "标题: " + data.getTitle())
                .setText(R.id.pay_a_tip_rec_common, "普通" + data.getOrdinaryPrice())
                .setText(R.id.pay_a_tip_rec_shareholder, "股东" + data.getShareholderPrice())
                .setText(R.id.pay_a_tip_rec_text, data.getPrice() + "");
    }
}
