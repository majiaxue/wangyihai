package com.xingshi.y_deal.my_sell_order.adapter;

import android.content.Context;

import com.xingshi.adapter.MyRecyclerAdapter;
import com.xingshi.adapter.RecyclerViewHolder;
import com.xingshi.bean.AppealListBean;
import com.xingshi.y_main.R;

import java.util.List;

public class MySellOrderAppealListAdapter extends MyRecyclerAdapter<AppealListBean.RecordsBean> {

    public MySellOrderAppealListAdapter(Context context, List<AppealListBean.RecordsBean> mList, int mLayoutId) {
        super(context, mList, mLayoutId);
    }

    @Override
    public void convert(RecyclerViewHolder holder, AppealListBean.RecordsBean data, int position) {
        holder.setImageFresco(R.id.my_pay_order_rec_image, data.getComplaintuser() == null ? "" : data.getComplaintuser())
                .setText(R.id.my_pay_order_rec_type_name, data.getType() == 0 ? "买单" : "卖单")
                .setText(R.id.my_pay_order_rec_seller, data.getSellUserName())
                .setText(R.id.my_pay_order_rec_id, data.getId() + "")
                .setText(R.id.my_pay_order_rec_currency, data.getNumber() + "")
                .setText(R.id.my_pay_order_rec_total_price, data.getTotalPrice() + "")
                .setText(R.id.my_pay_order_rec_state, data.getStatus() == 0 ? "申诉中" : data.getStatus() == 1 ? "已通过" : "已拒绝")
                .setText(R.id.my_pay_order_rec_time, data.getCreateTime());

        viewOnClickListener.ViewOnClick(holder.getView(R.id.my_pay_order_rec_look_over), position);
    }
}
