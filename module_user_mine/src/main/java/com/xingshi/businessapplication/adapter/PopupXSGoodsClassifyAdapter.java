package com.xingshi.businessapplication.adapter;

import android.content.Context;

import com.xingshi.adapter.MyRecyclerAdapter;
import com.xingshi.adapter.RecyclerViewHolder;
import com.xingshi.bean.PopupXSGoodsClassBean;
import com.xingshi.module_user_mine.R;

import java.util.List;

public class PopupXSGoodsClassifyAdapter extends MyRecyclerAdapter<PopupXSGoodsClassBean> {

    public PopupXSGoodsClassifyAdapter(Context context, List<PopupXSGoodsClassBean> mList, int mLayoutId) {
        super(context, mList, mLayoutId);
    }

    @Override
    public void convert(RecyclerViewHolder holder, PopupXSGoodsClassBean data, int position) {
        if (data.isCheck()) {
            holder.setImageResource(R.id.popup_item_goods_classify_check, R.drawable.icon_xuanzhong);
        } else {
            holder.setImageResource(R.id.popup_item_goods_classify_check, R.drawable.icon_weixuanzhong);
        }
        holder.setText(R.id.popup_item_goods_classify_text, data.getName());
    }
}
