package com.xingshi.order_confirm;

import com.xingshi.bean.PostageBean;
import com.xingshi.bean.ShippingAddressBean;
import com.xingshi.bean.UserCouponBean;
import com.xingshi.mvp.IView;

public interface OrderConfirmView extends IView {

    void noAddress();

    void loadAddress(ShippingAddressBean addressBean);

    void loadPostage(PostageBean postageBean);

    void payFail();

    void couponChoosed(UserCouponBean coupon);

    void loadFinish();
}
