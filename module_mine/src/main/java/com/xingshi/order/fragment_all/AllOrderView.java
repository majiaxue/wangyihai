package com.xingshi.order.fragment_all;

import com.xingshi.mvp.IView;
import com.xingshi.order.adapter.JDAdapter;
import com.xingshi.order.adapter.RvListAdapter;
import com.xingshi.order.adapter.TBAdapter;

public interface AllOrderView extends IView {

    void loadMineRv(RvListAdapter adapter);

    void loadJD(JDAdapter adapter);

    void loadTB(TBAdapter adapter);

    void moveTo(int flag);
}
