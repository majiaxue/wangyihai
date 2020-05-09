package com.xingshi.superbrand;

import com.xingshi.mvp.IView;
import com.xingshi.superbrand.adapter.SuperBrandRecAdapter;

/**
 * Created by cuihaohao on 2019/6/5
 * Describe:
 */
public interface SuperBrandView extends IView {
    void loadAdapter(SuperBrandRecAdapter superBrandRecAdapter);
}
