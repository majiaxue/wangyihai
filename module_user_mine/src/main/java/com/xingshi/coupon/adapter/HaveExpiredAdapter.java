package com.xingshi.coupon.adapter;

import android.content.Context;

import com.xingshi.adapter.MyRecyclerAdapter;
import com.xingshi.adapter.RecyclerViewHolder;
import com.xingshi.bean.CouponBean;
import com.xingshi.module_user_mine.R;

import java.util.List;

/**
 * Created by cuihaohao on 2019/6/13
 * Describe:
 */
public class HaveExpiredAdapter extends MyRecyclerAdapter<CouponBean> {

    public HaveExpiredAdapter(Context context, List<CouponBean> mList, int mLayoutId) {
        super(context, mList, mLayoutId);
    }

    @Override
    public void convert(RecyclerViewHolder holder, CouponBean data, int position) {
        holder.setText(R.id.have_expired_rec_shop_name, data.getSellerName());
        holder.setImageResource(R.id.have_expired_rec_image, R.drawable.img_104);
        holder.setText(R.id.have_expired_rec_qian, data.getAmount() + "");
        holder.setText(R.id.have_expired_rec_total_usage_amount, "满" + data.getMinPoint() + "元可使用");
        holder.setText(R.id.have_expired_rec_valid_time, data.getEndTime());
        viewOnClickListener.ViewOnClick(holder.getView(R.id.have_expired_rec_go_shop), position);
    }
}
