package com.xingshi.register;

import com.xingshi.mvp.IView;

public interface RegisterView extends IView {
    void readed();

    void noRead();

    void getCodeSuccess();
}
