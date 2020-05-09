package com.xingshi.shop_home;

import com.xingshi.bean.ShopHomeBean;
import com.xingshi.mvp.IView;
import com.xingshi.shop_home.adapter.ShopHomeVPAdapter;

public interface ShopHomeView extends IView {
    void loadVP(ShopHomeVPAdapter adapter);

    void isCollect(String result);

    void initView(ShopHomeBean shopHomeBean);
}
