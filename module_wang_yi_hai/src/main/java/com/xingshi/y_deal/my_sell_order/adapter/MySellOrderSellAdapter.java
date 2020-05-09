package com.xingshi.y_deal.my_sell_order.adapter;

import android.content.Context;

import com.xingshi.adapter.MyRecyclerAdapter;
import com.xingshi.adapter.RecyclerViewHolder;
import com.xingshi.bean.MyPayOrderBuyBean;
import com.xingshi.y_main.R;

import java.util.List;

public class MySellOrderSellAdapter extends MyRecyclerAdapter<MyPayOrderBuyBean.RecordsBean> {

    public MySellOrderSellAdapter(Context context, List<MyPayOrderBuyBean.RecordsBean> mList, int mLayoutId) {
        super(context, mList, mLayoutId);
    }

    @Override
    public void convert(RecyclerViewHolder holder, MyPayOrderBuyBean.RecordsBean data, int position) {
        holder.setImageFresco(R.id.my_pay_order_rec_image, data.getIcon() == null ? "" : data.getIcon())
                .setText(R.id.my_pay_order_rec_type_name, data.getType() == 0 ? "买单" : "卖单")
                .setText(R.id.my_pay_order_rec_seller, data.getSellUserName())
                .setText(R.id.my_pay_order_rec_id, data.getId() + "")
                .setText(R.id.my_pay_order_rec_currency, data.getNumber() + "")
                .setText(R.id.my_pay_order_rec_total_price, data.getTotalPrice() + "")
                .setText(R.id.my_pay_order_rec_state, data.getStatus() == 0 ? "待支付" : data.getStatus() == 1 ? "待确认" : data.getStatus() == 2 ? "已完成" : data.getStatus() == 3 ? "申述" : data.getStatus() == 4 ? "取消" : "冻结")
                .setText(R.id.my_pay_order_rec_time, data.getCreateTime());

        viewOnClickListener.ViewOnClick(holder.getView(R.id.my_pay_order_rec_look_over), position);
    }
}
