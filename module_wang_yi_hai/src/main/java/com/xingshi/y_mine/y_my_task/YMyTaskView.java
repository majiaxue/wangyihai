package com.xingshi.y_mine.y_my_task;

import com.xingshi.mvp.IView;
import com.xingshi.y_mine.y_my_task.adapter.YMyTaskAdapter;

public interface YMyTaskView extends IView {
    void loadAdapter(YMyTaskAdapter yMyTaskAdapter);

    void finishRefresh();

}
