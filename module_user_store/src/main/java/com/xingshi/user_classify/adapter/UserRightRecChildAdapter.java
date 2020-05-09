package com.xingshi.user_classify.adapter;

import android.content.Context;

import com.xingshi.adapter.MyRecyclerAdapter;
import com.xingshi.adapter.RecyclerViewHolder;
import com.xingshi.bean.ClassifyBean;
import com.xingshi.user_store.R;

import java.util.List;

public class UserRightRecChildAdapter extends MyRecyclerAdapter<ClassifyBean.Records.RecordsSecond.RecordsThird> {
    public UserRightRecChildAdapter(Context context, List<ClassifyBean.Records.RecordsSecond.RecordsThird> mList, int mLayoutId) {
        super(context, mList, mLayoutId);
    }

    @Override
    public void convert(RecyclerViewHolder holder, ClassifyBean.Records.RecordsSecond.RecordsThird data, int position) {
        holder.setImageUrl(R.id.classify_right_child_simple, data.getIcon())
                .setText(R.id.classify_right_child_name, data.getName());
    }
}
