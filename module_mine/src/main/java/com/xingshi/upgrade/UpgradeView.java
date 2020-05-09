package com.xingshi.upgrade;

import com.xingshi.mvp.IView;
import com.xingshi.upgrade.adapter.UpgradeAdapter;

public interface UpgradeView extends IView {
    void loadUI(UpgradeAdapter adapter);
}
