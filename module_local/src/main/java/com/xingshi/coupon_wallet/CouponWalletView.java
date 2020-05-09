package com.xingshi.coupon_wallet;

import com.xingshi.adapter.CouponWalletAdapter;
import com.xingshi.mvp.IView;

public interface CouponWalletView extends IView {
    void loadRv(CouponWalletAdapter adapter);
}
