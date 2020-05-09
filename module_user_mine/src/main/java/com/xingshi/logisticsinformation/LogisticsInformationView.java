package com.xingshi.logisticsinformation;

import com.xingshi.bean.LogisticsInforMationBean;
import com.xingshi.mvp.IView;

/**
 * Created by cuihaohao on 2019/5/28
 * Describe:
 */
public interface LogisticsInformationView extends IView {
    void traces(LogisticsInforMationBean inforMationBeanList,int size);
}
