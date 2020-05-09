package com.xingshi.y_mine.y_welfare_center.i_released.adapter;

import android.content.Context;
import android.view.View;

import com.xingshi.adapter.MyRecyclerAdapter;
import com.xingshi.adapter.RecyclerViewHolder;
import com.xingshi.bean.FuFeiXianBaoBean;
import com.xingshi.y_main.R;

import java.util.List;

public class FuFeiXianBaoAdapter extends MyRecyclerAdapter<FuFeiXianBaoBean.RecordsBean> {

    public FuFeiXianBaoAdapter(Context context, List<FuFeiXianBaoBean.RecordsBean> mList, int mLayoutId) {
        super(context, mList, mLayoutId);
    }

    @Override
    public void convert(RecyclerViewHolder holder, FuFeiXianBaoBean.RecordsBean data, int position) {
        if ("0".equals(data.getStatus())) {
            //上架
            holder.setText(R.id.fu_fei_xian_bao_putong, "普通" + data.getOrdinaryPrice())
                    .setText(R.id.fu_fei_xian_bao_gudong, "股东" + data.getShareholderPrice());
            holder.getView(R.id.fu_fei_xian_bao_cancel_registration).setVisibility(View.VISIBLE);
            holder.getView(R.id.fu_fei_xian_bao_linear4).setVisibility(View.VISIBLE);
            holder.getView(R.id.fu_fei_xian_bao_text).setVisibility(View.GONE);
        } else {
            //下架
            holder.getView(R.id.fu_fei_xian_bao_cancel_registration).setVisibility(View.GONE);
            holder.getView(R.id.fu_fei_xian_bao_linear4).setVisibility(View.GONE);
            holder.getView(R.id.fu_fei_xian_bao_text).setVisibility(View.VISIBLE);
        }

        holder.setText(R.id.fu_fei_xian_bao_title, data.getTitle())
                .setText(R.id.fu_fei_xian_bao_time, data.getCreateTime());

        viewOnClickListener.ViewOnClick(holder.getView(R.id.fu_fei_xian_bao_cancel_registration), position);
    }
}
