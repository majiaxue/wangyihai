package com.xingshi.obligation;

import com.xingshi.bean.OrderDetailBean;
import com.xingshi.mvp.IView;

/**
 * Created by cuihaohao on 2019/5/28
 * Describe:
 */
public interface ObligationView extends IView {
    void loadData(OrderDetailBean orderDetailBean);

    void isDelete(boolean isDelete);
}
