package com.xingshi.intoshop;

import com.xingshi.intoshop.adapter.IntoShopVPAdapter;
import com.xingshi.mvp.IView;

/**
 * Created by cuihaohao on 2019/5/21
 * Describe:
 */
public interface IntoShopView extends IView {
    void updateVp(IntoShopVPAdapter intoShopVPAdapter);
}
