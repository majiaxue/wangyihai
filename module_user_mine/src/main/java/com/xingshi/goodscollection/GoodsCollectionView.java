package com.xingshi.goodscollection;

import com.xingshi.adapter.BaseRecStaggeredAdapter;
import com.xingshi.goodscollection.adapter.GoodsCollectionRecAdapter;
import com.xingshi.mvp.IView;

/**
 * Created by cuihaohao on 2019/5/23
 * Describe:
 */
public interface GoodsCollectionView extends IView {

    void isCompile(boolean isCompile);

    void isCheckAll(boolean isCheckAll);

    void loadUI(GoodsCollectionRecAdapter adapter);

    void empty(boolean isEmpty);

    void loadCommend(BaseRecStaggeredAdapter adapter);
}
