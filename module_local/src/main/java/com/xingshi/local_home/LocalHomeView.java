package com.xingshi.local_home;

import com.xingshi.bean.BannerBean;
import com.xingshi.bean.LocalShopBean;
import com.xingshi.bean.LocalShopCommendBean;
import com.xingshi.local_home.adapter.LocalHomeCommendAdapter;
import com.xingshi.local_shop.adapter.LocalNavbarAdapter;
import com.xingshi.local_shop.adapter.LocalSellerAdapter;
import com.xingshi.mvp.IView;

import java.util.List;

public interface LocalHomeView extends IView {
    void loadFinish();

    void loadSeller(LocalSellerAdapter adapter);

    void noData();

    void loadNavbar(LocalNavbarAdapter adapter);

    void loadBanner(List<BannerBean.RecordsBean> beanList);

    void loadZhongBanner(List<LocalShopBean> zhongList);

    void loadCommend(LocalShopCommendBean shopCommendBean, LocalHomeCommendAdapter adapter);

    void noCommend();
}
