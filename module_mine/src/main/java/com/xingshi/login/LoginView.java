package com.xingshi.login;

import com.xingshi.mvp.IView;

public interface LoginView extends IView {

    void getCodeSuccess();

    void getCodeFail();
}
