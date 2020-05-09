package com.xingshi.fans_order.fragment_lose;

import com.xingshi.fans_order.adapter.FansOrderRvAdapter;
import com.xingshi.fans_order.adapter.JdFansAdapter;
import com.xingshi.fans_order.adapter.TbFansAdapter;
import com.xingshi.mvp.IView;

public interface FansLoseOrderView extends IView {
    void loadFansRv(FansOrderRvAdapter adapter);

    void loadSuccess();

    void loadTb(TbFansAdapter adapter);

    void moveTo(int flag);

    void loadJd(JdFansAdapter adapter);
}
