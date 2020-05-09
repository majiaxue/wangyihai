package com.xingshi.message_list;

import com.xingshi.message_list.adapter.MessageListAdapter;
import com.xingshi.mvp.IView;

public interface MessageListView extends IView {

    void loadUI(MessageListAdapter adapter);
}
