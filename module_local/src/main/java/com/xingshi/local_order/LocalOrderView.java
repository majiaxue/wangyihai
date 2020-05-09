package com.xingshi.local_order;

import com.xingshi.local_order.adapter.LocalOrderAdapter;
import com.xingshi.local_order.adapter.LocalOrderNavbarAdapter;
import com.xingshi.local_order.adapter.LocalTuiKuanAdapter;
import com.xingshi.mvp.IView;

public interface LocalOrderView extends IView {
    void loadNavbar(LocalOrderNavbarAdapter adapter);

    void changeType(int position);

    void loadRv(LocalOrderAdapter adapter);

    void loadFinish();

    void loadTuiKuanRv(LocalTuiKuanAdapter adapter);
}
