package com.xingshi.points;

import com.xingshi.bean.MyPointsBean;
import com.xingshi.mvp.IView;

public interface PointsView extends IView {
    void loadData(MyPointsBean pointsBean);
}
