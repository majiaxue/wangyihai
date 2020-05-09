package com.xingshi.mineorder;

import com.xingshi.adapter.BaseVPAdapter;
import com.xingshi.mvp.IView;

/**
 * Created by cuihaohao on 2019/5/27
 * Describe:
 */
public interface MineOrderView extends IView {
    void updateVp(BaseVPAdapter baseVPAdapter);
}
