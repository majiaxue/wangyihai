package com.xingshi.productdetail;

import com.xingshi.bean.BannerImageBean;
import com.xingshi.mvp.IView;
import com.xingshi.productdetail.adapter.ProductAccountAdapter;

import java.util.List;

public interface ProductDetailView extends IView {
    void loadRv(ProductAccountAdapter adapter);

    void updatePhone(int type, String phone);

    void loadBanner(List<BannerImageBean> imgList);
}
