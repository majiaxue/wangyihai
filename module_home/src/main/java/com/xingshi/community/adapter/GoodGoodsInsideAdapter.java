package com.xingshi.community.adapter;

import android.content.Context;

import com.xingshi.adapter.MyRecyclerAdapter;
import com.xingshi.adapter.RecyclerViewHolder;
import com.xingshi.bean.GoodGoodsBean;
import com.xingshi.module_home.R;

import java.util.List;

public class GoodGoodsInsideAdapter extends MyRecyclerAdapter<GoodGoodsBean.NetBean.ItemDataBean> {
    public GoodGoodsInsideAdapter(Context context, List<GoodGoodsBean.NetBean.ItemDataBean> mList, int mLayoutId) {
        super(context, mList, mLayoutId);
    }

    @Override
    public void convert(RecyclerViewHolder holder, GoodGoodsBean.NetBean.ItemDataBean data, int position) {
        holder.setImageUrl(R.id.rv_goods_commend_inside_img, data.getItempic());
    }
}
