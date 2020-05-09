package com.xingshi.y_mine.y_my_team;

import com.xingshi.bean.YMyTeamBean;
import com.xingshi.mvp.IView;
import com.xingshi.y_mine.y_my_team.adapter.QuKuaiTeamAdapter;

public interface YMyTeamView extends IView {
    void  loadData(YMyTeamBean bean);

    void refresh();

    void loadAdapter(QuKuaiTeamAdapter adapter);
}
