package com.xingshi.universallist.adapter;

import android.content.Context;
import android.text.TextUtils;

import com.xingshi.adapter.MyRecyclerAdapter;
import com.xingshi.adapter.RecyclerViewHolder;
import com.xingshi.bean.UniversalListBean;
import com.xingshi.common.CommonResource;
import com.xingshi.module_home.R;
import com.xingshi.utils.ArithUtil;
import com.xingshi.utils.SPUtil;

import java.util.List;

public class UniversalListRecAdapter extends MyRecyclerAdapter<UniversalListBean.DataBean.ListBean> {

    public UniversalListRecAdapter(Context context, List<UniversalListBean.DataBean.ListBean> mList, int mLayoutId) {
        super(context, mList, mLayoutId);
    }

    @Override
    public void convert(RecyclerViewHolder holder, UniversalListBean.DataBean.ListBean data, int position) {
        double commissionRate = data.getCommissionRate() / 100;
        double mul = commissionRate * data.getActualPrice() * 0.9;
        holder.setImageFresco(R.id.universal_list_rec_image, data.getMainPic());
        holder.setText(R.id.universal_list_rec_name, data.getTitle());
        holder.setText(R.id.universal_list_rec_price, "￥" + data.getOriginalPrice());
        holder.setText(R.id.universal_list_rec_payment_amount, "领劵减" + data.getCouponPrice() + "元");
        if (!TextUtils.isEmpty(SPUtil.getToken())) {
            if (SPUtil.getFloatValue(CommonResource.BACKBL) != 0) {
                holder.setText(R.id.universal_list_rec_yuguzhuan, "预估赚" + ArithUtil.mulRound(mul, SPUtil.getFloatValue(CommonResource.BACKBL)));
            } else {
                holder.setText(R.id.universal_list_rec_yuguzhuan, "预估赚" + ArithUtil.mulRound(mul, 0.3));
            }
        } else {
            holder.setText(R.id.universal_list_rec_yuguzhuan, "预估赚" + ArithUtil.mulRound(mul, 0.3));
        }

        holder.setText(R.id.universal_list_rec_shengjizhuan, "升级赚" + ArithUtil.mulRound(mul, 0.8));
    }
}
