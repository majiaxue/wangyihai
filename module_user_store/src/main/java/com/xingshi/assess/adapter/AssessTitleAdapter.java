package com.xingshi.assess.adapter;

import android.content.Context;
import android.widget.TextView;

import com.xingshi.adapter.MyRecyclerAdapter;
import com.xingshi.adapter.RecyclerViewHolder;
import com.xingshi.bean.AssessTitleBean;
import com.xingshi.user_store.R;
import com.xingshi.utils.TxtUtil;

import java.util.List;

public class AssessTitleAdapter extends MyRecyclerAdapter<AssessTitleBean> {
    public AssessTitleAdapter(Context context, List<AssessTitleBean> mList, int mLayoutId) {
        super(context, mList, mLayoutId);
    }

    @Override
    public void convert(RecyclerViewHolder holder, AssessTitleBean data, int position) {
        holder.setText(R.id.rv_assess_title_txt, data.getTitle());
        if (data.isCheck()) {
            TxtUtil.txtJianbian((TextView) holder.getView(R.id.rv_assess_title_txt), "#fb4119", "#febf0d");
            holder.setBackgroundResource(R.id.rv_assess_title_txt, R.drawable.icon_quanbu);
        } else {
            TxtUtil.txtJianbian((TextView) holder.getView(R.id.rv_assess_title_txt), "#333333", "#333333");
            holder.setBackgroundResource(R.id.rv_assess_title_txt, R.drawable.bg_11_666_border);
        }
        if (viewOnClickListener != null) {
            viewOnClickListener.ViewOnClick(holder.getView(R.id.rv_assess_title_parent), position);
        }
    }
}
