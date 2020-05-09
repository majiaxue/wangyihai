package com.xingshi.local_store;

import com.xingshi.bean.LocalStoreBean;
import com.xingshi.mvp.IView;

import java.util.List;

public interface LocalStoreView extends IView {

    void loadData(List<LocalStoreBean> localStoreBeans);

    void upMoney(String money, int size);
}
