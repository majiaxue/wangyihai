package com.xingshi.y_mine;

import com.xingshi.bean.YMineBean;
import com.xingshi.mvp.IView;

public interface YMineView extends IView {

    void initData(YMineBean yMineBean);
}
