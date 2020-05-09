package com.xingshi.local_list;

import com.xingshi.local_shop.adapter.LocalSellerAdapter;
import com.xingshi.mvp.IView;

public interface LocalListView extends IView {
    void loadFinish();

    void loadSeller(LocalSellerAdapter adapter);

    void noData();

    void changed(boolean isDistanceJin, boolean isStarMore);
}
