package com.xingshi.fans_order;

import com.xingshi.bean.FansOrderCensusBean;
import com.xingshi.mvp.IView;
import com.xingshi.order.adapter.OrderVPAdapter;

public interface FansOrderView extends IView {

    void updateVP(OrderVPAdapter adapter);

    void typeChanged(int position);

    void loadCensus(FansOrderCensusBean bean);
}
