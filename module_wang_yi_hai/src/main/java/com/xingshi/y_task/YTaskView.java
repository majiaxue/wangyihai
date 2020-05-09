package com.xingshi.y_task;

import com.xingshi.mvp.IView;
import com.xingshi.y_task.adapter.YTaskAdapter;

public interface YTaskView extends IView {
    void loadAdapter(YTaskAdapter taskAdapter);
}
