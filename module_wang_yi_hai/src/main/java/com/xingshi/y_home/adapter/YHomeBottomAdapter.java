package com.xingshi.y_home.adapter;

import android.content.Context;

import com.xingshi.adapter.MyRecyclerAdapter;
import com.xingshi.adapter.RecyclerViewHolder;
import com.xingshi.bean.YHomeBottomBean;
import com.xingshi.y_main.R;

import java.util.List;

/**
 * Created by cuihaohao on 2019/5/15
 * Describe:
 */
public class YHomeBottomAdapter extends MyRecyclerAdapter<YHomeBottomBean.DataBean> {

    public YHomeBottomAdapter(Context context, List<YHomeBottomBean.DataBean> mList, int mLayoutId) {
        super(context, mList, mLayoutId);
    }

    @Override
    public void convert(RecyclerViewHolder holder, YHomeBottomBean.DataBean data, int position) {
        holder.setText(R.id.y_home_bottom_name, data.getName())
                .setText(R.id.y_home_bottom_price, "￥" + data.getPrice())
                .setText(R.id.y_home_bottom_original_price, "￥" + data.getOriginalPrice())
                .setText(R.id.y_home_bottom_count, "已售" + data.getSale() + "笔")
                .setImageFresco(R.id.y_home_bottom_img, data.getPic());

//        if (1 == data.getType()) {
//            holder.getView(R.id.y_home_bottom_is_visibility).setVisibility(View.VISIBLE);
////            Drawable drawable = context.getResources().getDrawable(R.drawable.zuxiao);
////            drawable.setBounds(0,0,drawable.getIntrinsicWidth(),drawable.getIntrinsicHeight());
////            yHomeBottomName.setCompoundDrawables(drawable,null,null,null);
////            yHomeBottomName.setCompoundDrawablePadding(2);
//        } else {
//            holder.getView(R.id.y_home_bottom_is_visibility).setVisibility(View.GONE);
//
//        }
    }

}
