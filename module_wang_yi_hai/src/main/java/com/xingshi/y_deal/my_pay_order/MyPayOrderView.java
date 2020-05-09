package com.xingshi.y_deal.my_pay_order;

import com.xingshi.mvp.IView;
import com.xingshi.y_deal.my_pay_order.adapter.MyPayOrderAppealListAdapter;
import com.xingshi.y_deal.my_pay_order.adapter.MyPayOrderAskToBuyAdapter;
import com.xingshi.y_deal.my_pay_order.adapter.MyPayOrderBuyAdapter;

public interface MyPayOrderView extends IView {
    void loadAdapter(MyPayOrderBuyAdapter myPayOrderBuyAdapter);

    void loadAdapter(MyPayOrderAskToBuyAdapter adapter);

    void loadAdapter(MyPayOrderAppealListAdapter adapter);

    void finishRefresh();

}
