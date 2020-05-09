package com.xingshi.y_home.y_goods_details;

import com.xingshi.adapter.GoodsImageAdapter;
import com.xingshi.bean.BannerBean;
import com.xingshi.bean.YGoodsDetailsBean;
import com.xingshi.mvp.IView;

import java.util.List;

public interface YGoodsDetailsView extends IView {

    void loadImage(GoodsImageAdapter adapter);

    void loadUI(YGoodsDetailsBean data, int size);

    void loadBanner(List<BannerBean.RecordsBean> list);

    void yixuanze(String attr);

    void weixuanze(String str);

}
