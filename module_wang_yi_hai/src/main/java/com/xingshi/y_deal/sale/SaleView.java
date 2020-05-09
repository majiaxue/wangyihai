package com.xingshi.y_deal.sale;

import android.content.Intent;
import android.net.Uri;

import com.xingshi.bean.BuyAndSellDetailBean;
import com.xingshi.mvp.IView;

public interface SaleView extends IView {
    void photoAlbum(Intent intent);

    void showHeader(Uri uri,String base64);

    void loadData(BuyAndSellDetailBean bean);

    void loadTime(String confirmTime);
}
