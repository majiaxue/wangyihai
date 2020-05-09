package com.xingshi.y_home;

import com.xingshi.mvp.IView;
import com.xingshi.y_home.adapter.YHomeBottomAdapter;

public interface YHomeView extends IView {
//    void loadAdapter(YHomeTopAdapter adapter);

    void loadAdapter(YHomeBottomAdapter adapter);

    void finishRefresh();
}
