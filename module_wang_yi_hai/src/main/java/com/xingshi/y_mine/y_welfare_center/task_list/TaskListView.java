package com.xingshi.y_mine.y_welfare_center.task_list;

import com.xingshi.mvp.IView;
import com.xingshi.y_mine.y_welfare_center.task_list.adapter.TaskListAdapter;


public interface TaskListView extends IView {
    void loadAdapter(TaskListAdapter taskListAdapter);

    void id(int id);

    void finishRefresh();

}
