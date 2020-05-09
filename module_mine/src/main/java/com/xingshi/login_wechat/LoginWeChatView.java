package com.xingshi.login_wechat;

import com.xingshi.mvp.IView;

public interface LoginWeChatView extends IView {
    void readed();

    void noRead();

    void getCodeSuccess();
}
