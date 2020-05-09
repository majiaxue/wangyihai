package com.xingshi.buy2up;

import com.xingshi.bean.BannerBean;
import com.xingshi.bean.UserGoodsDetail;
import com.xingshi.mvp.IView;

import java.util.List;

public interface Buy2UpView extends IView {
    void loadUI(UserGoodsDetail bean);

    void loadBanner(List<BannerBean.RecordsBean> banner);
}
