package com.xingshi.y_mine.y_my_task.adapter;

import android.content.Context;

import com.xingshi.adapter.MyRecyclerAdapter;
import com.xingshi.adapter.RecyclerViewHolder;
import com.xingshi.bean.SignInBean;
import com.xingshi.y_main.R;

import java.util.List;

public class SignInAdapter extends MyRecyclerAdapter<SignInBean> {

    public SignInAdapter(Context context, List<SignInBean> mList, int mLayoutId) {
        super(context, mList, mLayoutId);
    }

    @Override
    public void convert(RecyclerViewHolder holder, SignInBean data, int position) {
        if ("0".equals(data.getStatus())) {
            holder.getView(R.id.sign_in_rec_linear).setBackground(context.getResources().getDrawable(R.drawable.bg_5_ffbfbfbf));
        } else {
            holder.getView(R.id.sign_in_rec_linear).setBackground(context.getResources().getDrawable(R.drawable.bg_5_ff4c49de));
        }
        holder.setText(R.id.sign_in_rec_status, "0".equals(data.getStatus()) ? "未签" : "已签");
        holder.setText(R.id.sign_in_rec_day, data.getDay());
    }
}
