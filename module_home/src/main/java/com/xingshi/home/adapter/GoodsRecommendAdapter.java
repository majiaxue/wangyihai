package com.xingshi.home.adapter;

import android.content.Context;
import android.text.TextUtils;

import com.xingshi.adapter.MyRecyclerAdapter;
import com.xingshi.adapter.RecyclerViewHolder;
import com.xingshi.bean.GoodsRecommendBean;
import com.xingshi.common.CommonResource;
import com.xingshi.module_base.R;
import com.xingshi.utils.ArithUtil;
import com.xingshi.utils.SPUtil;

import java.util.List;

/**
 * Created by cuihaohao on 2019/6/13
 * Describe:
 */
public class GoodsRecommendAdapter extends MyRecyclerAdapter<GoodsRecommendBean.DataBean> {

    public GoodsRecommendAdapter(Context context, List<GoodsRecommendBean.DataBean> mList, int mLayoutId) {
        super(context, mList, mLayoutId);
    }

    @Override
    public void convert(RecyclerViewHolder holder, GoodsRecommendBean.DataBean data, int position) {
        // 1表示天猫，0表示淘宝产品
        if (data.getUser_type().equals("0")) {
            //淘宝
            holder.setImageResource(R.id.base_type, R.drawable.icon_taobao);
        } else {
            //天猫
            holder.setImageResource(R.id.base_type, R.drawable.icon_tianmao);
        }
        double couponPrice = Double.valueOf(data.getZk_final_price()) - Double.valueOf(data.getCoupon_amount());//商品价格
        double div = Double.valueOf(data.getCommission_rate()) / 100;
        double mul = couponPrice * div * 0.9;//商品收益需要乘个人收益

        holder.setImageFresco(R.id.base_image, data.getPict_url());
        holder.setText(R.id.base_name, data.getTitle());
        holder.setText(R.id.base_reduce_price, "领劵减" + data.getCoupon_amount() + "元");//优惠劵
        holder.setText(R.id.base_preferential_price, "￥" + ArithUtil.sub(Double.valueOf(data.getZk_final_price()), Double.valueOf(data.getCoupon_amount())));//优惠价
        holder.setText(R.id.base_original_price, "￥" + data.getZk_final_price());//原价
        holder.setText(R.id.base_number, "已抢" + data.getVolume() + "件");//已抢数量
        if (!TextUtils.isEmpty(SPUtil.getToken())) {
            if (SPUtil.getFloatValue(CommonResource.BACKBL) != 0) {
                holder.setText(R.id.base_estimate, "预估赚" + ArithUtil.mulRound(mul, SPUtil.getFloatValue(CommonResource.BACKBL)));
            } else {
                holder.setText(R.id.base_estimate, "预估赚" + ArithUtil.mulRound(mul, 0.3));
            }
        } else {
            holder.setText(R.id.base_estimate, "预估赚" + ArithUtil.mulRound(mul, 0.3));
        }
        holder.setText(R.id.base_upgrade, "升级赚" + ArithUtil.mulRound(mul, 0.8));
        // 中间加横线 ， 添加Paint.ANTI_ALIAS_FLAG是线会变得清晰去掉锯齿
        holder.setTextLine(R.id.base_original_price);

//        TextView immediatelyGrab = holder.getView(R.id.base_immediately_grab);
//        viewOnClickListener.ViewOnClick(immediatelyGrab, position);
    }
}
