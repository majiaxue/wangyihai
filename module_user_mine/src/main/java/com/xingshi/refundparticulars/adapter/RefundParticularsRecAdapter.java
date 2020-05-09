package com.xingshi.refundparticulars.adapter;

import android.content.Context;

import com.xingshi.adapter.MyRecyclerAdapter;
import com.xingshi.adapter.RecyclerViewHolder;
import com.xingshi.bean.RefundParticularsBean;
import com.xingshi.module_user_mine.R;

import java.util.List;

public class RefundParticularsRecAdapter extends MyRecyclerAdapter<RefundParticularsBean.ItemlistBean> {

    public RefundParticularsRecAdapter(Context context, List<RefundParticularsBean.ItemlistBean> mList, int mLayoutId) {
        super(context, mList, mLayoutId);
    }

    @Override
    public void convert(RecyclerViewHolder holder, RefundParticularsBean.ItemlistBean data, int position) {
        holder.setImageFresco(R.id.refund_particulars_rec_image, data.getProductPic());
        holder.setText(R.id.refund_particulars_rec_goods_name, data.getProductName());
        holder.setText(R.id.refund_particulars_rec_count, "X" + data.getProductQuantity());
        holder.setText(R.id.refund_particulars_rec_productAttr, data.getProductAttr());
        holder.setText(R.id.refund_particulars_rec_money, "￥" + data.getProductPrice());

    }
}
