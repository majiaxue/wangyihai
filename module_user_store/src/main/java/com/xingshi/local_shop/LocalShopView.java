package com.xingshi.local_shop;

import com.xingshi.bean.BannerBean;
import com.xingshi.local_shop.adapter.LocalNavbarAdapter;
import com.xingshi.local_shop.adapter.LocalSellerAdapter;
import com.xingshi.mvp.IView;

import java.util.List;

public interface LocalShopView extends IView {
    void loadBanner(List<BannerBean.RecordsBean> beanList);

    void loadNavbar(LocalNavbarAdapter adapter);

    void loadSeller(LocalSellerAdapter adapter);

    void changed(boolean isDistanceJin, boolean isStarMore);

    void loadFinish();

    void noData();

    void cityName(String cityName);

}
