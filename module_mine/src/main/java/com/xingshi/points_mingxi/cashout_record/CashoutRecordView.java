package com.xingshi.points_mingxi.cashout_record;

import com.xingshi.mvp.IView;
import com.xingshi.points_mingxi.adapter.CashoutRecordAdapter;

public interface CashoutRecordView extends IView {
    void loadRv(CashoutRecordAdapter adapter);
}
