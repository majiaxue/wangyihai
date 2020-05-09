package com.xingshi.balance;

import com.xingshi.bean.BalanceBean;
import com.xingshi.mvp.IView;

public interface BalanceView extends IView {

    void loadBalance(BalanceBean balanceBean);
}
