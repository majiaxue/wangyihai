package com.xingshi.y_mine.y_personal_details;

import android.content.Intent;
import android.net.Uri;

import com.xingshi.bean.YPersonalDetailsBean;
import com.xingshi.mvp.IView;

public interface YPersonalDetailsView extends IView {
    void loadData(YPersonalDetailsBean yPersonalDetailsBean);

    void takePhoto(Intent intent);

    void photoAlbum(Intent intent);

    void imageUri(Uri uri);
}
