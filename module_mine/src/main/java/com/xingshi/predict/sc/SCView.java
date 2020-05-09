package com.xingshi.predict.sc;

import com.xingshi.bean.PredictBean;
import com.xingshi.mvp.IView;

public interface SCView extends IView {
    void loadUI(PredictBean predictBean);

    void loadUI();
}
