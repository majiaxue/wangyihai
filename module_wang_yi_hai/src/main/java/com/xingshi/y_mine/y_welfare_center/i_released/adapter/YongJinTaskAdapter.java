package com.xingshi.y_mine.y_welfare_center.i_released.adapter;

import android.content.Context;
import android.view.View;

import com.xingshi.adapter.MyRecyclerAdapter;
import com.xingshi.adapter.RecyclerViewHolder;
import com.xingshi.bean.YongJinTaskBean;
import com.xingshi.y_main.R;

import java.util.List;

public class YongJinTaskAdapter extends MyRecyclerAdapter<YongJinTaskBean.RecordsBean> {

    public YongJinTaskAdapter(Context context, List<YongJinTaskBean.RecordsBean> mList, int mLayoutId) {
        super(context, mList, mLayoutId);
    }

    @Override
    public void convert(RecyclerViewHolder holder, YongJinTaskBean.RecordsBean data, int position) {

        if ("0".equals(data.getStatus())) {
            //上架
            holder.setText(R.id.yongjin_task_putong, "普通" + data.getOrdinaryPrice())
                    .setText(R.id.yongjin_task_gudong, "股东" + data.getShareholderPrice());
            holder.getView(R.id.yongjin_task_cancel_registration).setVisibility(View.VISIBLE);
            holder.getView(R.id.yongjin_task_linear4).setVisibility(View.VISIBLE);
            holder.getView(R.id.yongjin_task_text).setVisibility(View.GONE);
        } else {
            //下架
            holder.getView(R.id.yongjin_task_cancel_registration).setVisibility(View.GONE);
            holder.getView(R.id.yongjin_task_linear4).setVisibility(View.GONE);
            holder.getView(R.id.yongjin_task_text).setVisibility(View.VISIBLE);
        }

        holder.setText(R.id.yongjin_task_type, data.getTypeName())
                .setText(R.id.yongjin_task_title, data.getTitle())
                .setText(R.id.yongjin_task_num, data.getNumber())
                .setText(R.id.yongjin_task_time, data.getCreateTime());

        viewOnClickListener.ViewOnClick(holder.getView(R.id.yongjin_task_cancel_registration), position);
    }
}
