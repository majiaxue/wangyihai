package com.xingshi.predict.jd;

import com.xingshi.bean.PredictBean;
import com.xingshi.mvp.IView;

public interface JDView extends IView {
    void loadUI(PredictBean predictBean);

    void loadUI();
}
