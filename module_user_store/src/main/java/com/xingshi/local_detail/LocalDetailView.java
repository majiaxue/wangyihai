package com.xingshi.local_detail;

import com.xingshi.adapter.BaseVPAdapter;
import com.xingshi.local_shop.adapter.ManJianAdapter;
import com.xingshi.mvp.IView;

public interface LocalDetailView extends IView {
    void updateVP(BaseVPAdapter vpAdapter);

    void loadManJian(ManJianAdapter adapter);
}
