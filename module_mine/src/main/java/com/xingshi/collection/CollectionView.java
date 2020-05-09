package com.xingshi.collection;

import com.xingshi.collection.adapter.CollectionAdapter;
import com.xingshi.mvp.IView;

public interface CollectionView extends IView {

    void toEdit();

    void toFinish();

    void allCheck();

    void notAllCheck();

    void loadUI(CollectionAdapter adapter);

    void loadFinish(int size);
}
