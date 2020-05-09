package com.xingshi.coupon.all;

import com.xingshi.coupon.adapter.LocalCouponWalletAdapter;
import com.xingshi.mvp.IView;

/**
 * Created by cuihaohao on 2019/5/25
 * Describe:
 */
public interface AllView extends IView {
    void loadRv(LocalCouponWalletAdapter adapter);
}
