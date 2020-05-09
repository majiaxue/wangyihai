package com.xingshi.home.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.widget.TextView;

import com.xingshi.adapter.MyRecyclerAdapter;
import com.xingshi.adapter.RecyclerViewHolder;
import com.xingshi.bean.GoodChoiceBean;
import com.xingshi.module_home.R;

import java.util.List;

/**
 * Created by cuihaohao on 2019/5/15
 * Describe:
 */
public class GoodChoiceRecAdapter extends MyRecyclerAdapter<GoodChoiceBean.DataBean> {

    public GoodChoiceRecAdapter(Context context, List<GoodChoiceBean.DataBean> mList, int mLayoutId) {
        super(context, mList, mLayoutId);
    }

    @Override
    public void convert(RecyclerViewHolder holder, GoodChoiceBean.DataBean data, int position) {
        holder.setImageFresco(R.id.good_choice_image, data.getItempic());
        holder.setText(R.id.good_choice_name, data.getItemtitle());
        holder.setText(R.id.good_choice_preferential_price, "￥" + data.getItemendprice());
        holder.setText(R.id.good_choice_original_price, data.getItemprice());
        // 中间加横线 ， 添加Paint.ANTI_ALIAS_FLAG是线会变得清晰去掉锯齿
        TextView originalPrice = holder.getView(R.id.good_choice_original_price);
        originalPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);
    }
}
