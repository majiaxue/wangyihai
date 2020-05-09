package com.xingshi.y_mine.y_my_task.take_records;

import com.xingshi.mvp.IView;
import com.xingshi.y_mine.y_my_task.take_records.adapter.TakeRecordsAdapter;

public interface TakeRecordsView extends IView {
    void loadAdapter(TakeRecordsAdapter takeRecordsAdapter);

    void finishRefresh();

}
