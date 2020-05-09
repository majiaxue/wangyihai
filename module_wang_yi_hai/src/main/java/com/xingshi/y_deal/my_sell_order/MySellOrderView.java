package com.xingshi.y_deal.my_sell_order;

import com.xingshi.mvp.IView;
import com.xingshi.y_deal.my_sell_order.adapter.MySellOrderAppealListAdapter;
import com.xingshi.y_deal.my_sell_order.adapter.MySellOrderAskToBuyAdapter;
import com.xingshi.y_deal.my_sell_order.adapter.MySellOrderSellAdapter;

public interface MySellOrderView extends IView {
    void loadAdapter(MySellOrderSellAdapter adapter);

    void loadAdapter(MySellOrderAskToBuyAdapter adapter);

    void loadAdapter(MySellOrderAppealListAdapter adapter);

    void finishRefresh();
}
