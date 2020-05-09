package com.xingshi.adapter;

import android.content.Context;
import android.text.TextUtils;

import com.xingshi.bean.SecondaryPddRecBean;
import com.xingshi.common.CommonResource;
import com.xingshi.module_base.R;
import com.xingshi.utils.ArithUtil;
import com.xingshi.utils.SPUtil;

import java.util.List;


/**
 * Created by cuihaohao on 2019/6/6
 * Describe:
 */
public class SecondaryPddRecAdapter extends MyRecyclerAdapter<SecondaryPddRecBean.GoodsSearchResponseBean.GoodsListBean> {

    public SecondaryPddRecAdapter(Context context, List<SecondaryPddRecBean.GoodsSearchResponseBean.GoodsListBean> mList, int mLayoutId) {
        super(context, mList, mLayoutId);
    }

    @Override
    public void convert(final RecyclerViewHolder holder, SecondaryPddRecBean.GoodsSearchResponseBean.GoodsListBean data, int position) {

        //拼多多
        holder.setImageResource(R.id.base_type, R.drawable.pinduoduo);

        double div = ArithUtil.div(Double.valueOf(data.getMin_group_price()) - Double.valueOf(data.getCoupon_discount()), 100, 2);
        double mul = ArithUtil.mul(div, ArithUtil.div(Double.valueOf(data.getPromotion_rate()), 1000, 2));

        holder.setImageFresco(com.xingshi.module_base.R.id.base_image, data.getGoods_thumbnail_url());

//        Glide.with(context)
////                .asBitmap()
//                .load(data.getGoods_thumbnail_url())
//                .transform(new GlideRoundTransform(5,0))
//                .into((ImageView) holder.getView(R.id.base_image));

        holder.setText(R.id.base_name, data.getGoods_name());
        holder.setText(R.id.base_reduce_price, "领劵减" + ArithUtil.div(Double.valueOf(data.getCoupon_discount()), 100, 2) + "元");
        holder.setText(R.id.base_preferential_price, "￥" + div);
        holder.setText(R.id.base_original_price, "" + ArithUtil.div(Double.valueOf(data.getMin_group_price()), 100, 2));
        if (!TextUtils.isEmpty(data.getSold_quantity())){
            holder.setText(R.id.base_number, "已抢" + data.getSold_quantity());
        }else{
            holder.setText(R.id.base_number, "已抢" + 0+"件");
        }
        // 中间加横线 ， 添加Paint.ANTI_ALIAS_FLAG是线会变得清晰去掉锯齿
        holder.setTextLine(R.id.base_original_price);
        if (!TextUtils.isEmpty(SPUtil.getToken())) {
            if (SPUtil.getFloatValue(CommonResource.BACKBL) != 0) {
                holder.setText(R.id.base_estimate, "预估赚" + ArithUtil.mulRound(mul, SPUtil.getFloatValue(CommonResource.BACKBL)));
            } else {
                holder.setText(R.id.base_estimate, "预估赚" + ArithUtil.mulRound(mul, 0.3));
            }
//            LogUtil.e("预估收益:" + "商品价格" + couponPrice + "佣金" + div + "个人收益" + SPUtil.getFloatValue(CommonResource.BACKBL) + "最终金额" + "预估赚" + ArithUtil.mul(mul, SPUtil.getFloatValue(CommonResource.BACKBL)));
        } else {
            holder.setText(R.id.base_estimate, "预估赚" + ArithUtil.mulRound(mul, 0.3));
        }
        holder.setText(R.id.base_upgrade, "升级赚"+ArithUtil.mul(mul,0.8));

//        TextView immediatelyGrab = holder.getView(R.id.base_immediately_grab);
//        if (viewOnClickListener != null) {
//            viewOnClickListener.ViewOnClick(immediatelyGrab, position);
//        }
    }
}
