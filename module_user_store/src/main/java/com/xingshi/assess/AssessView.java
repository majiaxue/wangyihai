package com.xingshi.assess;

import com.xingshi.assess.adapter.AssessAdapter;
import com.xingshi.assess.adapter.AssessTitleAdapter;
import com.xingshi.mvp.IView;

public interface AssessView extends IView {
    void loadTitle(AssessTitleAdapter adapter);

    void loadAssess(AssessAdapter adapter);

    void loadFinish();
}
