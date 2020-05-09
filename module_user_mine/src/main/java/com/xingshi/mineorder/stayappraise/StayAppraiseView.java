package com.xingshi.mineorder.stayappraise;

import com.xingshi.mineorder.stayappraise.adapter.StayAppraiseParentAdapter;
import com.xingshi.mvp.IView;

/**
 * Created by cuihaohao on 2019/5/27
 * Describe:
 */
public interface StayAppraiseView extends IView {
    void load(StayAppraiseParentAdapter stayAppraiseParentAdapter);
}
