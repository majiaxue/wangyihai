package com.xingshi.web_detail;

import android.net.Uri;

import com.xingshi.mvp.IView;

public interface WebDetailView extends IView {
    void loadUri(Uri uri);
}
