package com.xingshi.y_deal.trading_center;

import com.xingshi.mvp.IView;
import com.xingshi.y_deal.trading_center.adapter.TradingCenterPayAdapter;
import com.xingshi.y_deal.trading_center.adapter.TradingCenterSellAdapter;

public interface TradingCenterView extends IView {
    void isShow(boolean isShow);

    void loadAdapter(TradingCenterPayAdapter adapter);

    void loadAdapter(TradingCenterSellAdapter adapter);

    void buyBack(String serviceCharge, int minNumber, double price, double currency);

    void finishRefresh();
}
