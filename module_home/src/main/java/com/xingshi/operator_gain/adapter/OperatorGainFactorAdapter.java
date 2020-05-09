package com.xingshi.operator_gain.adapter;

import android.content.Context;

import com.xingshi.adapter.MyRecyclerAdapter;
import com.xingshi.adapter.RecyclerViewHolder;
import com.xingshi.module_home.R;

import java.util.List;

public class OperatorGainFactorAdapter extends MyRecyclerAdapter<String> {
    public OperatorGainFactorAdapter(Context context, List<String> mList, int mLayoutId) {
        super(context, mList, mLayoutId);
    }

    @Override
    public void convert(RecyclerViewHolder holder, String data, int position) {
        holder.setTextFormHtml(R.id.rv_vp_operator_factor_txt, data);
    }
}
