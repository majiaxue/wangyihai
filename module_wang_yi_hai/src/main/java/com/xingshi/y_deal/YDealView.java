package com.xingshi.y_deal;

import com.xingshi.mvp.IView;
import com.xingshi.y_deal.adapter.YDealAdapter;

public interface YDealView extends IView {
    void loadAdapter(YDealAdapter yDealAdapter);

    void loadData(double totalPrice,double totalServiceCharge);
}
