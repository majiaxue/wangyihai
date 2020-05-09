package com.xingshi.superbrand.rests;

import com.xingshi.mvp.IView;
import com.xingshi.superbrand.adapter.RestsAdapter;

public interface RestsView extends IView {
    void loadAdapter(RestsAdapter restsAdapter);

    void refreshSuccess();
}
