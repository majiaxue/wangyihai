package com.xingshi.local_pay;

import com.xingshi.bean.UserCouponBean;
import com.xingshi.mvp.IView;

public interface LocalPayView extends IView {
    void chooseFinish(UserCouponBean coupon);

    void updateMoney(String money);

    void callBack();
}
