package com.xingshi.y_mine.y_welfare_center.a_tip_to_release;

import android.content.Intent;
import android.net.Uri;

import com.xingshi.mvp.IView;

public interface ATipToReleaseView extends IView {

    void chooseTime(String time);

    void takePhoto(Intent intent);

    void photoAlbum(Intent intent);

    void showHeader(String bitmap, Uri uri);

    void imagePath(String s);
}
