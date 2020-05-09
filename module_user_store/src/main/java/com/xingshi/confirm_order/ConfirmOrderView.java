package com.xingshi.confirm_order;

import com.xingshi.bean.ShippingAddressBean;
import com.xingshi.confirm_order.adapter.ConfirmOrderAdapter;
import com.xingshi.mvp.IView;

public interface ConfirmOrderView extends IView {
    void loadRv(ConfirmOrderAdapter adapter);

    void loadAddress(ShippingAddressBean addressBean);

    void noAddress();

    void loadPostage(double feight, double price, int number);

    void couponAfter(double amount);
}
