package com.xingshi.upgrade.adapter;

import android.content.Context;

import com.xingshi.adapter.MyRecyclerAdapter;
import com.xingshi.adapter.RecyclerViewHolder;
import com.xingshi.module_mine.R;

import java.util.List;

public class UpgradeInsideAdapter extends MyRecyclerAdapter<String> {
    public UpgradeInsideAdapter(Context context, List<String> mList, int mLayoutId) {
        super(context, mList, mLayoutId);
    }

    @Override
    public void convert(RecyclerViewHolder holder, String data, int position) {
        holder.setTextFormHtml(R.id.rv_upgrade_inside_txt, data);
    }
}
