package com.xingshi.commoditydetails.pdd.adapter;

import android.content.Context;

import com.xingshi.adapter.MyRecyclerAdapter;
import com.xingshi.adapter.RecyclerViewHolder;
import com.xingshi.module_classify.R;
import com.xingshi.utils.DisplayUtil;
import com.xingshi.utils.FrescoUtils;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

/**
 * Created by cuihaohao on 2019/6/11
 * Describe:
 */
public class CommodityDetailsRecAdapter extends MyRecyclerAdapter<String> {

    public CommodityDetailsRecAdapter(Context context, List<String> mList, int mLayoutId) {
        super(context, mList, mLayoutId);
    }

    @Override
    public void convert(RecyclerViewHolder holder, String data, int position) {
        SimpleDraweeView image = holder.getView(R.id.commodity_details_rec_image);
        FrescoUtils.setControllerListener(image,data, DisplayUtil.getScreenWidth(context));

    }
}
