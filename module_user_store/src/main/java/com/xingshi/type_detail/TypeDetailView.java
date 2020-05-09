package com.xingshi.type_detail;

import com.xingshi.mvp.IView;
import com.xingshi.type_detail.adapter.TypeDetailLstAdapter;
import com.xingshi.type_detail.adapter.TypeDetailWaterfallAdapter;

public interface TypeDetailView extends IView {
    void loadLstRv(TypeDetailLstAdapter adapter);

    void loadWaterfallRv(TypeDetailWaterfallAdapter adapter);

    void updateTitle(boolean salesVolume, boolean price, boolean credit);

    void refreshSuccess();
}
