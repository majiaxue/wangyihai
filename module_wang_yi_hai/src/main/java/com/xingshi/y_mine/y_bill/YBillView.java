package com.xingshi.y_mine.y_bill;

import com.xingshi.mvp.IView;
import com.xingshi.y_mine.y_bill.adapter.YBillAdapter;

public interface YBillView extends IView {
    void loadAdapter(YBillAdapter adapter);

    void loadFinish();
}
