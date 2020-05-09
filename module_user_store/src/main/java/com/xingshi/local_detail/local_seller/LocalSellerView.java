package com.xingshi.local_detail.local_seller;

import com.xingshi.local_detail.adapter.SellerImaAdapter;
import com.xingshi.mvp.IView;

public interface LocalSellerView extends IView {
    void loadImg(SellerImaAdapter adapter);
}
