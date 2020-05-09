package com.xingshi.points_mingxi.points_record;

import com.xingshi.mvp.IView;
import com.xingshi.points_mingxi.adapter.PointsRecordAdapter;

public interface PointsRecordView extends IView {
    void loadRv(PointsRecordAdapter adapter);
}
