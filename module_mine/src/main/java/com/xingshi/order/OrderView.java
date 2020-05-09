package com.xingshi.order;

import com.xingshi.mvp.IView;
import com.xingshi.order.adapter.OrderVPAdapter;

public interface OrderView extends IView {

    void updateVP(OrderVPAdapter adapter);

    void typeChanged(int position);
}
