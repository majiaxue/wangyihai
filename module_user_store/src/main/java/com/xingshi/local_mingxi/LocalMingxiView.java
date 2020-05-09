package com.xingshi.local_mingxi;

import com.xingshi.local_mingxi.adapter.LocalMingxiAdapter;
import com.xingshi.mvp.IView;

public interface LocalMingxiView extends IView {
    void loadRv(LocalMingxiAdapter adapter);
}
