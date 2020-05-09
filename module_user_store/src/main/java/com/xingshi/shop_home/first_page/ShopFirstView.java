package com.xingshi.shop_home.first_page;

import com.xingshi.mvp.IView;
import com.xingshi.shop_home.adapter.FirstCouponAdapter;

public interface ShopFirstView extends IView {
    void loadRv(FirstCouponAdapter adapter);
}
