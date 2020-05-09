package com.xingshi.home;

import android.view.View;

import com.xingshi.mvp.IView;

import java.util.List;

public interface HomeView extends IView {
    void lodeMarquee(List<View> views);

    void refreshSuccess();
}
