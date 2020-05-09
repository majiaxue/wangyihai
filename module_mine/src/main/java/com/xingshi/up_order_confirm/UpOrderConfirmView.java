package com.xingshi.up_order_confirm;

import com.xingshi.bean.ShippingAddressBean;
import com.xingshi.mvp.IView;

public interface UpOrderConfirmView extends IView {
    void loadAddress(ShippingAddressBean bean);

    void noAddress();

    void callBack();
}
