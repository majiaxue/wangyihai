package com.xingshi.community.goods_commend;

import android.graphics.Bitmap;

import com.xingshi.bean.CommunityLocalBean;
import com.xingshi.community.adapter.CommendTitleAdapter;
import com.xingshi.community.adapter.GoodsCommendAdapter;
import com.xingshi.mvp.IView;

public interface GoodsCommendView extends IView {

    void loadTitle(CommendTitleAdapter adapter);

    void loadContent(GoodsCommendAdapter adapter);

    void changeType();

    void loadFinish();

    void loadShareInfo(CommunityLocalBean bean);

    void loadQR(Bitmap bitmap);
}
