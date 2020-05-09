package com.xingshi.y_mine.y_welfare_center.i_released;

import com.xingshi.mvp.IView;
import com.xingshi.y_mine.y_welfare_center.i_released.adapter.FuFeiXianBaoAdapter;
import com.xingshi.y_mine.y_welfare_center.i_released.adapter.YongJinTaskAdapter;

public interface IReleasedView extends IView {

    void loadAdapter(YongJinTaskAdapter adapter);

    void loadAdapter(FuFeiXianBaoAdapter adapter);

    void finishRefresh();

}
