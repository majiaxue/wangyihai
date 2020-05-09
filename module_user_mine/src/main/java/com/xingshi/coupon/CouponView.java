package com.xingshi.coupon;

import com.xingshi.adapter.BaseVPAdapter;
import com.xingshi.mvp.IView;

/**
 * Created by cuihaohao on 2019/5/25
 * Describe:
 */
public interface CouponView extends IView {
    void updateVp(BaseVPAdapter baseVPAdapter);
}
