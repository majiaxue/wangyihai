package com.xingshi.user_classify.adapter;

import android.content.Context;
import android.graphics.Color;

import com.xingshi.adapter.MyRecyclerAdapter;
import com.xingshi.adapter.RecyclerViewHolder;
import com.xingshi.bean.ClassifyBean;
import com.xingshi.user_store.R;

import java.util.List;

public class UserLeftRvAdapter extends MyRecyclerAdapter<ClassifyBean.Records> {
    public UserLeftRvAdapter(Context context, List<ClassifyBean.Records> mList, int mLayoutId) {
        super(context, mList, mLayoutId);
    }

    @Override
    public void convert(RecyclerViewHolder holder, ClassifyBean.Records data, int position) {
        holder.setText(R.id.rv_left_classify_txt, data.getName())
                .setBackgroundColor(R.id.rv_left_classify_txt, Color.parseColor(data.isSelect() ? "#ffffff" : "#fafafa"))
                .setTextColor(R.id.rv_left_classify_txt, Color.parseColor(data.isSelect() ? "#fd3c15" : "#666666"));
    }
}
