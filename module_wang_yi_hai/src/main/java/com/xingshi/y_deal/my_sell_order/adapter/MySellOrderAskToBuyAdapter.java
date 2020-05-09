package com.xingshi.y_deal.my_sell_order.adapter;

import android.content.Context;
import android.view.View;

import com.ali.auth.third.core.util.StringUtil;
import com.xingshi.adapter.MyRecyclerAdapter;
import com.xingshi.adapter.RecyclerViewHolder;
import com.xingshi.bean.MyPayOrderAskToBuyBean;
import com.xingshi.y_main.R;

import java.util.List;

public class MySellOrderAskToBuyAdapter extends MyRecyclerAdapter<MyPayOrderAskToBuyBean.RecordsBean> {

    public MySellOrderAskToBuyAdapter(Context context, List<MyPayOrderAskToBuyBean.RecordsBean> mList, int mLayoutId) {
        super(context, mList, mLayoutId);
    }

    @Override
    public void convert(RecyclerViewHolder holder, MyPayOrderAskToBuyBean.RecordsBean data, int position) {
        if (0 == data.getStatus()) {
            holder.getView(R.id.my_pay_order_rec_seller).setVisibility(View.GONE);
            holder.getView(R.id.my_pay_order_rec_id).setVisibility(View.GONE);
        } else {
            holder.getView(R.id.my_pay_order_rec_seller).setVisibility(View.VISIBLE);
            holder.getView(R.id.my_pay_order_rec_id).setVisibility(View.VISIBLE);
        }
        //0求购中 1待支付 2待确认 3已完成 4 申诉 5取消 6冻结 如果订单号不存在使用这个
        //0待支付 1待确认 2已完成 3 申诉 4取消 5冻结 如果订单号存在存在使用这个
        if (StringUtil.isBlank(data.getOrderNumber())) {
            holder.setText(R.id.my_pay_order_rec_state, data.getStatus() == 0 ? "求购中" : data.getStatus() == 1 ? "待支付" : data.getStatus() == 2 ? "待确认" : data.getStatus() == 3 ? "已完成" : data.getStatus() == 4 ? "申述" : data.getStatus() == 5 ? "取消" : "冻结");
        } else {
            holder.setText(R.id.my_pay_order_rec_state, data.getStatus() == 0 ? "待支付" : data.getStatus() == 1 ? "待确认" : data.getStatus() == 2 ? "已完成" : data.getStatus() == 3 ? "申述" : data.getStatus() == 4 ? "取消" : "冻结");
        }

        holder.setImageFresco(R.id.my_pay_order_rec_image, data.getIcon() == null ? "" : data.getIcon())
                .setText(R.id.my_pay_order_rec_type_name, "卖单")
                .setText(R.id.my_pay_order_rec_seller, data.getUserName())
                .setText(R.id.my_pay_order_rec_id, data.getId() + "")
                .setText(R.id.my_pay_order_rec_currency, data.getNumber() + "")
                .setText(R.id.my_pay_order_rec_total_price, data.getTotalPrice() + "")
                .setText(R.id.my_pay_order_rec_time, data.getCreateTime());

        viewOnClickListener.ViewOnClick(holder.getView(R.id.my_pay_order_rec_look_over), position);
    }
}
