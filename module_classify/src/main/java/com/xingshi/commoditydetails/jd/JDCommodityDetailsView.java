package com.xingshi.commoditydetails.jd;

import com.xingshi.bean.JDListBean;
import com.xingshi.commoditydetails.pdd.adapter.CommodityDetailsRecAdapter;
import com.xingshi.mvp.IView;

public interface JDCommodityDetailsView extends IView {

    void isNoGoods(boolean isNoGoods);

    void qrImage(String url);

    void loadUI(JDListBean jDGoodsRecBean, CommodityDetailsRecAdapter adapter);
}
