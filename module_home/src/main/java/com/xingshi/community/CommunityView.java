package com.xingshi.community;

import com.xingshi.adapter.BaseVPAdapter;
import com.xingshi.mvp.IView;

public interface CommunityView extends IView {
    void updateVP(BaseVPAdapter adapter);
}
