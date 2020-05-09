package com.xingshi.local_detail.local_goods;

import com.xingshi.local_detail.adapter.LocalDetailGoodsAdapter;
import com.xingshi.mvp.IView;

public interface LocalGoodsView extends IView {
    void loadGoods(LocalDetailGoodsAdapter adapter);
}
