package com.xingshi.productcenter;

import com.xingshi.mvp.IView;
import com.xingshi.productcenter.adapter.ProductCenterAdapter;

public interface ProductCenterView extends IView {
    void loadAdapter(ProductCenterAdapter productCenterAdapter);

    void loadRefresh();

}
