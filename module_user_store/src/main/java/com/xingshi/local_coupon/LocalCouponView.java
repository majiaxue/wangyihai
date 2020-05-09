package com.xingshi.local_coupon;

import com.xingshi.local_coupon.adapter.LocalCouponAdapter;
import com.xingshi.mvp.IView;

public interface LocalCouponView extends IView {
    void loadCoupon(LocalCouponAdapter adapter);
}
