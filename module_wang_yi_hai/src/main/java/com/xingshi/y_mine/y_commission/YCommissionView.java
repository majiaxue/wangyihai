package com.xingshi.y_mine.y_commission;

import com.xingshi.bean.PopRuleBean;
import com.xingshi.mvp.IView;
import com.xingshi.y_mine.y_bill.adapter.YBillAdapter;

import java.util.List;

public interface YCommissionView extends IView {
    void loadData(String total, String blance, String backMoney);

    void popRule(List<PopRuleBean> popRuleBeanList);

    void loadAdapter(YBillAdapter adapter);

    void loadFinish();
}
