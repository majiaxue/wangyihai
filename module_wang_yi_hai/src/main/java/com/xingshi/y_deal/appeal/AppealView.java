package com.xingshi.y_deal.appeal;

import com.xingshi.bean.AppealBean;
import com.xingshi.mvp.IView;

public interface AppealView extends IView {
    void loadData(AppealBean appealBean);
}
