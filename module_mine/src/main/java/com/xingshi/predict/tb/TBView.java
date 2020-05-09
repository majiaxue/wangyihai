package com.xingshi.predict.tb;

import com.xingshi.bean.PredictBean;
import com.xingshi.mvp.IView;

public interface TBView extends IView {
    void loadUI(PredictBean predictBean);

    void loadUI();
}
