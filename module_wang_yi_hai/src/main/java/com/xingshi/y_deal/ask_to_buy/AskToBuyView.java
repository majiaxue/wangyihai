package com.xingshi.y_deal.ask_to_buy;

import android.content.Intent;
import android.net.Uri;

import com.xingshi.bean.BuyAndSellDetailBean;
import com.xingshi.mvp.IView;

public interface AskToBuyView extends IView {
    void loadData(BuyAndSellDetailBean bean);

    void takePhoto(Intent intent);

    void photoAlbum(Intent intent);

    void showHeader(Uri uri, String base64);

    void loadTime(String paymentTime);
}
