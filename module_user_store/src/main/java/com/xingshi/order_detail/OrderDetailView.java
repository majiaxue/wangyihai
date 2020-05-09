package com.xingshi.order_detail;

import com.xingshi.bean.OrderDetailBean;
import com.xingshi.mvp.IView;
import com.xingshi.user_home.adapter.CommendAdapter;

public interface OrderDetailView extends IView {

    void loadData(OrderDetailBean orderDetailBean);

    void loadCommend(CommendAdapter adapter);
}
