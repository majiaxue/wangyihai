package com.xingshi.y_mine.y_welfare_center.commission_task;

import com.xingshi.mvp.IView;
import com.xingshi.y_mine.y_welfare_center.commission_task.adapter.CommissionTaskAdapter;
import com.xingshi.y_mine.y_welfare_center.release_a_task.adapter.ReleaseATaskTabAdapter;

public interface CommissionTaskView extends IView {

    void getType(int id);

    void loadAdapter(ReleaseATaskTabAdapter releaseATaskTabAdapter);

    void loadAdapter(CommissionTaskAdapter commissionTaskAdapter);

    void finishRefresh();
}
