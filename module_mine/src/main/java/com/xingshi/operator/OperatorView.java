package com.xingshi.operator;

import android.support.v4.view.PagerAdapter;

import com.xingshi.mvp.IView;
import com.xingshi.operator.adapter.YysQuanyiAdapter;

public interface OperatorView extends IView {

    void loadQuanyi(YysQuanyiAdapter adapter);

    void loadVp(PagerAdapter adapter);

    void loadFactor(String s);
}
