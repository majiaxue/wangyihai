package com.xingshi.community.good_goods;

import android.graphics.Bitmap;

import com.xingshi.bean.GoodGoodsBean;
import com.xingshi.community.adapter.GoodGoodsAdapter;
import com.xingshi.mvp.IView;

public interface GoodGoodsView extends IView {

    void loadContent(GoodGoodsAdapter adapter);

    void loadFinish();

    void loadQR(Bitmap bitmap);

    void loadShareInfo(GoodGoodsBean.NetBean.ItemDataBean dataBean);
}
