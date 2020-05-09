package com.xingshi.order_info;

import com.xingshi.mvp.IView;
import com.xingshi.order_info.adapter.OrderInfoAdapter;

public interface OrderInfoView extends IView {
    void loadRv(OrderInfoAdapter adapter);
}
