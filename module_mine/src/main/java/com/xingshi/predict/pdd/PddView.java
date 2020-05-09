package com.xingshi.predict.pdd;

import com.xingshi.bean.PredictBean;
import com.xingshi.mvp.IView;

public interface PddView extends IView {
    void loadUI(PredictBean predictBean);

    void loadUI();
}
