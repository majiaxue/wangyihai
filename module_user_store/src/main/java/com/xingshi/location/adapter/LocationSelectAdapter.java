package com.xingshi.location.adapter;

import android.content.Context;
import android.view.View;

import com.xingshi.adapter.MyRecyclerAdapter;
import com.xingshi.adapter.RecyclerViewHolder;
import com.xingshi.user_store.R;

import java.util.List;

public class LocationSelectAdapter extends MyRecyclerAdapter<String> {


    public LocationSelectAdapter(Context context, List<String> mList, int mLayoutId) {
        super(context, mList, mLayoutId);
    }

    @Override
    public void convert(RecyclerViewHolder holder, String data, int position) {
        holder.setText(R.id.item_text,data);
        if (position == mList.size() - 1) {
            holder.getView(R.id.item_line).setVisibility(View.GONE);
        }
    }

}
