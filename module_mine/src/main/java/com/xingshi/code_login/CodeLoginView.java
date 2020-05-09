package com.xingshi.code_login;

import com.xingshi.mvp.IView;

public interface CodeLoginView extends IView {

    void getCodeSuccess();

    void agreeAgreement();

    void disagreeAgreement();
}
