package com.xingshi.y_mine.y_welfare_center.fuwenben;

import android.content.Intent;

import com.xingshi.mvp.IView;

public interface FuWenBenView extends IView {
    void photoAlbum(Intent intent);

    void takePhoto(Intent captureIntent);

    void imagePath(String s);
}
