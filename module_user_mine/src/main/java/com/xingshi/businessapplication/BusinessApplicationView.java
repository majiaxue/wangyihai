package com.xingshi.businessapplication;

import android.content.Intent;
import android.net.Uri;

import com.xingshi.mvp.IView;

import java.io.File;

/**
 * Created by cuihaohao on 2019/5/25
 * Describe:
 */
public interface BusinessApplicationView extends IView {
    void takePhoto(Intent intent);

    void photoAlbum(Intent intent);

    void selectPhoto(int type, File file, Uri uri);

    void showHeader(String base64);

    void categoryId(String name, int categoryId);

    void kedian();
}
