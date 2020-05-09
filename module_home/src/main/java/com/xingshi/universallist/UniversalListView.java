package com.xingshi.universallist;

import com.xingshi.mvp.IView;
import com.xingshi.universallist.adapter.BaoYouAdapter;
import com.xingshi.universallist.adapter.HotRecommendRecAdapter;
import com.xingshi.universallist.adapter.UniversalListRecAdapter;

public interface UniversalListView extends IView {
    void finishRefresh();

    void loadData(BaoYouAdapter baoYouAdapter);

    void loadData(HotRecommendRecAdapter hotRecommendRecAdapter);

    void loadData(UniversalListRecAdapter universalListRecAdapter);
}
