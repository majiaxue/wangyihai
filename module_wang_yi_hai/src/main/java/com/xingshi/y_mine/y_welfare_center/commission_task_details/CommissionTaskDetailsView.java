package com.xingshi.y_mine.y_welfare_center.commission_task_details;

import android.content.Intent;
import android.net.Uri;

import com.xingshi.bean.CommissionTaskDetailsBean;
import com.xingshi.bean.TaskListDetailsBean;
import com.xingshi.mvp.IView;

public interface CommissionTaskDetailsView extends IView {
    void loadData(CommissionTaskDetailsBean bean);

    void loadData(TaskListDetailsBean bean);

    void takePhoto(Intent intent);

    void photoAlbum(Intent intent);

    void showHeader(Uri uri);

    void loadTime(String loadTime);
}
