package com.xingshi.operator_gain;

import android.support.v4.view.PagerAdapter;

import com.xingshi.mvp.IView;
import com.xingshi.operator_gain.adapter.OperatorGainBottomAdapter;

public interface OperatorGainView extends IView {
    void loadVp(PagerAdapter adapter);

    void loadQuanyi(OperatorGainBottomAdapter adapter);
}
