package com.xingshi.y_mine.y_balance_account;

import com.xingshi.mvp.IView;
import com.xingshi.y_mine.y_balance_account.adapter.YBalanceAccountAdapter;

public interface YBalanceAccountView extends IView {
    void loadData(String s);

    void loadAdapter(YBalanceAccountAdapter adapter);

    void finishRefresh();
}
