package com.xingshi.cashout;

import com.xingshi.mvp.IView;

public interface CashoutView extends IView {

    void loadBalance(String balance);

    void loadInfo(String name, String aliAcount);
}
