package com.xingshi.setting;

import android.content.Intent;

import com.xingshi.bean.UserInfoBean;
import com.xingshi.mvp.IView;

public interface SettingView extends IView {
    void takePhoto(Intent intent);

    void photoAlbum(Intent intent);

    void cropPhoto(Intent intent);

    void showHeader(String url);

    void clearSuccess();

    void getDataSUccess(UserInfoBean userInfoBean);
}
