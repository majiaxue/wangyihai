package com.xingshi.goods_detail;

import android.graphics.Bitmap;

import com.xingshi.bean.AssessBean;
import com.xingshi.bean.BannerBean;
import com.xingshi.bean.UserGoodsDetail;
import com.xingshi.goods_detail.adapter.GoodsAssessAdapter;
import com.xingshi.goods_detail.adapter.GoodsCouponAdapter;
import com.xingshi.adapter.GoodsImageAdapter;
import com.xingshi.mvp.IView;
import com.xingshi.user_home.adapter.CommendAdapter;

import java.util.List;

public interface GoodsDetailView extends IView {
    void loadCoupon(GoodsCouponAdapter adapter);

    void loadImage(GoodsImageAdapter adapter);

    void loadAssess(GoodsAssessAdapter adapter, AssessBean assessBean);

    void loadCommend(CommendAdapter adapter);

    void loadBanner(List<BannerBean.RecordsBean> list);

    void attention();

    void cancelAttention();

    void yixuanze(String attr);

    void weixuanze(String str);

    void loadUI(UserGoodsDetail data, int size);

    void loadQrCode(Bitmap qrImage);
}
