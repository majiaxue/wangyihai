package com.xingshi.points_mingxi;

import com.xingshi.mvp.IView;
import com.xingshi.order.adapter.OrderVPAdapter;

public interface PointsMXView extends IView {
    void updateVP(OrderVPAdapter vpAdapter);
}
