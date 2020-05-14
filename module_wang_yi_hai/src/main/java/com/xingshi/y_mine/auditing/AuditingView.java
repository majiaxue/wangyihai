package com.xingshi.y_mine.auditing;

import com.xingshi.mvp.IView;
import com.xingshi.y_mine.auditing.adapter.AudutingAdapter;

public interface AuditingView extends IView {
    void loadAdapter(AudutingAdapter adapter);

    void refresh();

    void getStatus(int i);
}
