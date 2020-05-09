package com.xingshi.local_cart;

import com.xingshi.mvp.IView;
import com.xingshi.user_shopping_cart.adapter.CartParentRecAdapter;

public interface LocalCartView extends IView {
    void loadSuccess();

    void isHide(boolean b);

    void isCheckAll(boolean isAllCheck);

    void loadCartRv(CartParentRecAdapter adapter);

    void deleteSuccess();

    void updateCount(int count);

    void totalPrice(double totalPrice);
}
