package com.xingshi.y_mine.y_currency_balance;

import com.xingshi.mvp.IView;
import com.xingshi.y_mine.y_currency_balance.adapter.YCurrencyBalanceAdapter;

public interface YCurrencyBalanceView extends IView {
    void loadData(String s);

    void loadAdapter(YCurrencyBalanceAdapter yCurrencyBalanceAdapter);

    void finishRefresh();

}
