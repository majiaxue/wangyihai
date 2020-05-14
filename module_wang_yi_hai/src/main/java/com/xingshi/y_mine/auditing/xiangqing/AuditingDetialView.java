package com.xingshi.y_mine.auditing.xiangqing;

import com.xingshi.bean.TaskListDetailsBean;
import com.xingshi.mvp.IView;

public interface AuditingDetialView extends IView {
    void loadData(TaskListDetailsBean taskListDetailsBean);
}
