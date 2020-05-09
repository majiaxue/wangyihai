package com.xingshi.y_mine.y_contribution_value;

import com.xingshi.mvp.IView;
import com.xingshi.y_mine.y_contribution_value.adapter.YContributionValueAdapter;

public interface YContributionValueView extends IView {
    void loadData(String s);

    void loadAdapter(YContributionValueAdapter yContributionValueAdapter);

    void finishRefresh();

}
