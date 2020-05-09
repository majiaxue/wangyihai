package com.xingshi.local_shop.adapter;

import android.content.Context;

import com.xingshi.adapter.MyRecyclerAdapter;
import com.xingshi.adapter.RecyclerViewHolder;
import com.xingshi.bean.LocalNavbarBean;
import com.xingshi.user_store.R;

import java.util.List;

public class LocalNavbarAdapter extends MyRecyclerAdapter<LocalNavbarBean> {

    public LocalNavbarAdapter(Context context, List<LocalNavbarBean> mList, int mLayoutId) {
        super(context, mList, mLayoutId);
    }

    @Override
    public void convert(RecyclerViewHolder holder, LocalNavbarBean data, int position) {
        holder.setImageUrl(R.id.rv_local_navbar_img, data.getSellerCategoryPicture())
                .setText(R.id.rv_local_navbar_txt, data.getSellerCategoryName());
    }
}
