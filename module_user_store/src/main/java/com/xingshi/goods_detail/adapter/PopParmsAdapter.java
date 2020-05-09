package com.xingshi.goods_detail.adapter;

import android.content.Context;

import com.xingshi.adapter.MyRecyclerAdapter;
import com.xingshi.adapter.RecyclerViewHolder;
import com.xingshi.bean.ParmsBean;
import com.xingshi.user_store.R;

import java.util.List;

public class PopParmsAdapter extends MyRecyclerAdapter<ParmsBean> {
    public PopParmsAdapter(Context context, List<ParmsBean> mList, int mLayoutId) {
        super(context, mList, mLayoutId);
    }

    @Override
    public void convert(RecyclerViewHolder holder, ParmsBean data, int position) {
        holder.setText(R.id.rv_pop_parms_key, data.getKey())
                .setText(R.id.rv_pop_parms_vaule, data.getValue());
    }
}
