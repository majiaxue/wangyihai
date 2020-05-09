package com.xingshi.commoditydetails.taobao;

import com.xingshi.bean.NewTBGoodsDetailsBean;
import com.xingshi.mvp.IView;

/**
 * Created by cuihaohao on 2019/6/14
 * Describe:
 */
public interface TBCommodityDetailsView extends IView {
    void tbBeanList(NewTBGoodsDetailsBean tbGoodsDetailsBean);

    void tBDetails();

    void noCoupon(boolean noCoupon);
}
