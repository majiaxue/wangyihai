package com.xingshi.invite_friends;

import com.xingshi.invite_friends.adapter.InviteVpAdapter;
import com.xingshi.mvp.IView;

public interface InviteFriendsView extends IView {

    void loadVP(InviteVpAdapter adapter, int size);
}
