package com.xingshi.y_deal.my_pay_order_details;

import android.content.Intent;
import android.net.Uri;

import com.xingshi.bean.BuyAndSellDetailBean;
import com.xingshi.mvp.IView;

public interface MyPayOrderDetailsView extends IView {
    void loadData(BuyAndSellDetailBean bean);

    void takePhoto(Intent intent);

    void photoAlbum(Intent intent);

    void showHeader(Uri uri,String base64);

    void loadTime(String paymentTime);
}
