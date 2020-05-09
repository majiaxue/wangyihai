package com.xingshi.mineorder.stayobligation;

import com.xingshi.mineorder.adapter.MineOrderParentAdapter;
import com.xingshi.mvp.IView;

/**
 * Created by cuihaohao on 2019/5/27
 * Describe:
 */
public interface StayObligationView extends IView {
    void load(MineOrderParentAdapter mineOrderParentAdapter);
}
