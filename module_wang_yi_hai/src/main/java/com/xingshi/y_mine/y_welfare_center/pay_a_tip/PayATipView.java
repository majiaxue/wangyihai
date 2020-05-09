package com.xingshi.y_mine.y_welfare_center.pay_a_tip;

import com.xingshi.mvp.IView;
import com.xingshi.y_mine.y_welfare_center.pay_a_tip.adapter.PayATipAdapter;

public interface PayATipView extends IView {

    void loadAdapter(PayATipAdapter payATipAdapter);

    void position(int position);

    void finishRefresh();
}
