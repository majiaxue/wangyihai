package com.xingshi.group_fans;

import com.xingshi.bean.GroupFansPeopleBean;
import com.xingshi.group_fans.adapter.GroupFansRvAdapter;
import com.xingshi.mvp.IView;

public interface GroupFansView extends IView {
    void loadUI(int totalPage, int totalFans);

    void loadCount(GroupFansPeopleBean peopleBean);

    void loadFinish();

    void noFans();

    void loadRv(GroupFansRvAdapter adapter);
}
