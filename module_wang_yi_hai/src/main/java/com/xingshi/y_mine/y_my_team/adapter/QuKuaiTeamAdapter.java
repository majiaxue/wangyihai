package com.xingshi.y_mine.y_my_team.adapter;

import android.content.Context;

import com.xingshi.adapter.MyRecyclerAdapter;
import com.xingshi.adapter.RecyclerViewHolder;
import com.xingshi.bean.MyTeamList;
import com.xingshi.y_main.R;

import java.util.List;

public class QuKuaiTeamAdapter extends MyRecyclerAdapter<MyTeamList> {
    public QuKuaiTeamAdapter(Context context, List<MyTeamList> mList, int mLayoutId) {
        super(context, mList, mLayoutId);
    }

    @Override
    public void convert(RecyclerViewHolder holder, MyTeamList data, int position) {
        holder.setText(R.id.nickname,"昵称："+data.getNickname())
                .setText(R.id.phone,"手机号："+data.getPhone())
                .setText(R.id.time,"注册日期："+data.getCreate_time())
                .setImageUrlCircular(R.id.img,data.getIcon())
                .setText(R.id.first_fans_num,data.getFirst_fans_num()+"");
    }
}
