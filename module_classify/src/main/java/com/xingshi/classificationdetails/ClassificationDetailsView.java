package com.xingshi.classificationdetails;

import com.xingshi.adapter.BaseRecAdapter;
import com.xingshi.adapter.SecondaryJDRecAdapter;
import com.xingshi.adapter.SecondaryPddRecAdapter;
import com.xingshi.classificationdetails.adapter.ClassificationRecAdapter;
import com.xingshi.classificationdetails.adapter.JdWaterfallAdapter;
import com.xingshi.classificationdetails.adapter.PddWaterAdapter;
import com.xingshi.mvp.IView;

/**
 * Created by cuihaohao on 2019/5/20
 * Describe:
 */
public interface ClassificationDetailsView extends IView {
    void loadTBLstRv(BaseRecAdapter adapter);

    void loadTBWaterfallRv(ClassificationRecAdapter adapter);

    void loadPDDLstRv(SecondaryPddRecAdapter adapter);

    void loadPDDWaterfallRv(PddWaterAdapter adapter);

    void loadJDLstRv(SecondaryJDRecAdapter adapter);

    void loadJDWaterfallRv(JdWaterfallAdapter adapter);

    void loadFinish();

    void updateTitle(boolean isPositiveSalesVolume, boolean isPositivePrice, boolean isPositiveCredit);

    void moveTo(int num, boolean isWaterfall);
}
