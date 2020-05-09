package com.xingshi.secondarydetails;

import com.xingshi.adapter.SecondaryJDRecAdapter;
import com.xingshi.mvp.IView;
import com.xingshi.adapter.SecondaryPddRecAdapter;
import com.xingshi.secondarydetails.adapter.SecondaryTBRecAdapter;

/**
 * Created by cuihaohao on 2019/5/31
 * Describe:
 */
public interface SecondaryDetailsView extends IView {
    void lodeRec(SecondaryPddRecAdapter baseRecAdapter);

    void lodeTBRec(SecondaryTBRecAdapter secondaryTBRecAdapter);

    void lodeJDRec(SecondaryJDRecAdapter secondaryJDRecAdapter);

    void noGoods(boolean isNoGoods);
}
