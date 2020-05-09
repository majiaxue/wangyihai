package com.xingshi.balance.payout;

import com.xingshi.balance.adapter.IncomeAdapter;
import com.xingshi.mvp.IView;

public interface PayoutView extends IView {

    void loadRv(IncomeAdapter adapter);

    void loadFinish();
}
