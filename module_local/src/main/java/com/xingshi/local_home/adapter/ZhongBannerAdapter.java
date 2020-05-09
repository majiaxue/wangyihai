package com.xingshi.local_home.adapter;

import android.content.Context;

import com.xingshi.adapter.MyRecyclerAdapter;
import com.xingshi.adapter.RecyclerViewHolder;
import com.xingshi.module_local.R;

import java.util.List;

public class ZhongBannerAdapter extends MyRecyclerAdapter<String> {
    public ZhongBannerAdapter(Context context, List<String> mList, int mLayoutId) {
        super(context, mList, mLayoutId);
    }

    @Override
    public void convert(RecyclerViewHolder holder, String data, int position) {
        holder.setText(R.id.rv_textview_txt, data);
    }
}
