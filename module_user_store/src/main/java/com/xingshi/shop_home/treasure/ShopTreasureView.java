package com.xingshi.shop_home.treasure;

import com.xingshi.mvp.IView;
import com.xingshi.type_detail.adapter.TypeDetailLstAdapter;
import com.xingshi.type_detail.adapter.TypeDetailWaterfallAdapter;

public interface ShopTreasureView extends IView {

    void loadLstRv(TypeDetailLstAdapter adapter, int flag);

    void loadWaterfallRv(TypeDetailWaterfallAdapter adapter, int flag);

    void updateTitle(boolean salesVolume, boolean price, boolean credit);

    void loadFinish();
}
