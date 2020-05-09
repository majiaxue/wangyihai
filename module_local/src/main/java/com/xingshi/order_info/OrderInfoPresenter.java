package com.xingshi.order_info;

import android.content.Context;

import com.xingshi.bean.LocalOrderBean;
import com.xingshi.module_local.R;
import com.xingshi.mvp.BasePresenter;
import com.xingshi.order_info.adapter.OrderInfoAdapter;

import java.util.List;

public class OrderInfoPresenter extends BasePresenter<OrderInfoView> {
    public OrderInfoPresenter(Context context) {
        super(context);
    }

    @Override
    protected void onViewDestroy() {

    }

    public void loadData(List<LocalOrderBean.LocalOrderItemListBean> list) {
        OrderInfoAdapter orderInfoAdapter = new OrderInfoAdapter(mContext, list, R.layout.rv_order_info);
        if (getView()!=null){
            getView().loadRv(orderInfoAdapter);
        }
    }
}
