package com.xingshi.y_deal.release_order;

import com.xingshi.mvp.IView;

public interface ReleaseOrderView extends IView {

    void checkoutLinear(boolean checkout);

    void serviceCharge(double serviceCharge,String max,String less);

    void serviceCharge(String max,String less);
}
