package com.xingshi.user_classify;

import com.xingshi.bean.BannerBean;
import com.xingshi.mvp.IView;
import com.xingshi.user_classify.adapter.UserLeftRvAdapter;
import com.xingshi.user_classify.adapter.UserRightRecAdapter;

import java.util.List;

/**
 * Created by cuihaohao on 2019/5/16
 * Describe:
 */
public interface ClassifyView extends IView {

    void loadRv(UserLeftRvAdapter leftAdapter, UserRightRecAdapter rightAdapter);

    void loadBanner(List<BannerBean.RecordsBean> list);

}
