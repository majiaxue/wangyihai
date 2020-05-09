package com.xingshi.mine;

import com.xingshi.bean.HomePredictBean;
import com.xingshi.bean.UserInfoBean;
import com.xingshi.mine.adapter.MyToolAdapter;
import com.xingshi.mvp.IView;

public interface MineView extends IView {
    void loadMyTool(MyToolAdapter adapter);

    void loginSuccess(UserInfoBean userInfo);

    void onError();

    void loadPredict(HomePredictBean homePredictBean);
}
