package com.xingshi.message_center;

import com.xingshi.message_center.adapter.MessageCenterAdapter;
import com.xingshi.mvp.IView;

public interface MessageCenterView extends IView {
    void loadRv(MessageCenterAdapter adapter);

    void loadFinish();
}
