package com.xingshi.mineorder.staydeliverygoods.orderdetails;

import com.xingshi.bean.OrderDetailBean;
import com.xingshi.mvp.IView;

/**
 * Created by cuihaohao on 2019/6/16
 * Describe:
 */
public interface OrderDetailsView extends IView {
    void loadData(OrderDetailBean orderDetailBean);
}
