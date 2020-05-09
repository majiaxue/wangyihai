package com.xingshi.local_mine;

import com.xingshi.bean.RedPackageBean;
import com.xingshi.mvp.IView;

import java.util.List;

public interface LocalMineView extends IView {
    void loadBanner(List<RedPackageBean> redPackageBeans);

    void callBack();

}
