package com.xingshi.commoditydetails.pdd;

import com.xingshi.bean.CommodityDetailsBean;
import com.xingshi.mvp.IView;

import java.util.List;

/**
 * Created by cuihaohao on 2019/5/16
 * Describe:
 */
public interface CommodityDetailsView extends IView {
    void CommodityDetailsList(List<CommodityDetailsBean.GoodsDetailResponseBean.GoodsDetailsBean> beanList);

    void imageUri(String url);


}
