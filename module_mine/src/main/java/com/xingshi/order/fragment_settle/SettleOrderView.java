package com.xingshi.order.fragment_settle;

import com.xingshi.mvp.IView;
import com.xingshi.order.adapter.JDAdapter;
import com.xingshi.order.adapter.RvListAdapter;
import com.xingshi.order.adapter.TBAdapter;

public interface SettleOrderView extends IView {
    void loadMineRv(RvListAdapter adapter);

    void loadJD(JDAdapter adapter);

    void loadTB(TBAdapter adapter);

    void moveTo(int flag);
}
