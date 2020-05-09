package com.xingshi.y_mine.y_welfare_center.task_list.adapter;

import android.content.Context;
import android.view.View;

import com.xingshi.adapter.MyRecyclerAdapter;
import com.xingshi.adapter.RecyclerViewHolder;
import com.xingshi.bean.TaskListBean;
import com.xingshi.y_main.R;

import java.util.List;

public class TaskListAdapter extends MyRecyclerAdapter<TaskListBean.RecordsBean> {

    public TaskListAdapter(Context context, List<TaskListBean.RecordsBean> mList, int mLayoutId) {
        super(context, mList, mLayoutId);
    }

    @Override
    public void convert(RecyclerViewHolder holder, TaskListBean.RecordsBean data, int position) {
        if (0 == data.getSubStatus()) {
            //未提交
            holder.setImageFresco(R.id.task_list_rec_pic, data.getIcon() == null ? "" : data.getIcon())
//                        .setText(R.id.task_list_rec_time,)
                    .setText(R.id.task_list_rec_name, data.getTaskName());

            holder.getView(R.id.take_list_rec_price).setVisibility(View.GONE);
            holder.getView(R.id.take_list_rec_cancel_registration).setVisibility(View.VISIBLE);
            holder.getView(R.id.take_list_rec_verify_time).setVisibility(View.GONE);
            holder.getView(R.id.take_list_rec_pass_or_be_passed).setVisibility(View.GONE);

            viewOnClickListener.ViewOnClick(holder.getView(R.id.take_list_rec_cancel_registration), position);
        } else {
            //0-->待审核,1-->通过,2-->拒绝
            if (0 == data.getStatus()) {
                holder.setImageFresco(R.id.task_list_rec_pic, data.getIcon() == null ? "" : data.getIcon())
//                        .setText(R.id.task_list_rec_time,)
                        .setText(R.id.task_list_rec_name, data.getTaskName());

                holder.getView(R.id.take_list_rec_price).setVisibility(View.GONE);
                holder.getView(R.id.take_list_rec_cancel_registration).setVisibility(View.GONE);
                holder.getView(R.id.take_list_rec_verify_time).setVisibility(View.VISIBLE);
                holder.getView(R.id.take_list_rec_pass_or_be_passed).setVisibility(View.GONE);

            } else if (1 == data.getStatus()) {
                holder.setImageFresco(R.id.task_list_rec_pic, data.getIcon() == null ? "" : data.getIcon())
//                        .setText(R.id.task_list_rec_time,)
                        .setText(R.id.take_list_rec_price, "+" + data.getOrdinaryPrice())
                        .setText(R.id.task_list_rec_name, data.getTaskName())
                        .setText(R.id.take_list_rec_pass_or_be_passed, "已通过")
                        .setBackgroundResource(R.id.take_list_rec_pass_or_be_passed, R.drawable.tongguo1);

                holder.getView(R.id.take_list_rec_price).setVisibility(View.VISIBLE);
                holder.getView(R.id.take_list_rec_cancel_registration).setVisibility(View.GONE);
                holder.getView(R.id.take_list_rec_verify_time).setVisibility(View.GONE);
                holder.getView(R.id.take_list_rec_pass_or_be_passed).setVisibility(View.VISIBLE);


            } else if (2 == data.getStatus()) {
                holder.setImageFresco(R.id.task_list_rec_pic, data.getIcon() == null ? "" : data.getIcon())
//                        .setText(R.id.task_list_rec_time,)
                        .setText(R.id.task_list_rec_name, data.getTaskName())
                        .setText(R.id.take_list_rec_pass_or_be_passed, "未通过")
                        .setBackgroundResource(R.id.take_list_rec_pass_or_be_passed, R.drawable.weitonggo);

                holder.getView(R.id.take_list_rec_price).setVisibility(View.GONE);
                holder.getView(R.id.take_list_rec_cancel_registration).setVisibility(View.GONE);
                holder.getView(R.id.take_list_rec_verify_time).setVisibility(View.GONE);
                holder.getView(R.id.take_list_rec_pass_or_be_passed).setVisibility(View.VISIBLE);

            }
        }
    }
}
