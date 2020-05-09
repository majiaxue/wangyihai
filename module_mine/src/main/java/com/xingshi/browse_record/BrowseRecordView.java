package com.xingshi.browse_record;

import com.xingshi.browse_record.adapter.BrowseRecordAdapter;
import com.xingshi.mvp.IView;

public interface BrowseRecordView extends IView {
    void loadUI(BrowseRecordAdapter adapter);

    void loadFinish();
}
