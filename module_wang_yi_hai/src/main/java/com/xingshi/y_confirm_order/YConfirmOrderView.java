package com.xingshi.y_confirm_order;

import com.xingshi.bean.ShippingAddressBean;
import com.xingshi.mvp.IView;

public interface YConfirmOrderView extends IView {
    void noAddress();

    void loadAddress(ShippingAddressBean addressBean);
}
