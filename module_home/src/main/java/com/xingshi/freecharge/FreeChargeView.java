package com.xingshi.freecharge;

import com.xingshi.freecharge.adapter.FreeChargeAdapter;
import com.xingshi.freecharge.adapter.FreeChargeLookAdapter;
import com.xingshi.mvp.IView;

public interface FreeChargeView extends IView {
    void noGoods(boolean noGoods);

    void load(FreeChargeAdapter freeChargeAdapter);

    void load(FreeChargeLookAdapter freeChargeLookAdapter);
}
