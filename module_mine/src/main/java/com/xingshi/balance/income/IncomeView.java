package com.xingshi.balance.income;

import com.xingshi.balance.adapter.IncomeAdapter;
import com.xingshi.mvp.IView;

public interface IncomeView extends IView {

    void loadRv(IncomeAdapter adapter);

    void loadFinish();
}
