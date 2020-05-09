package com.xingshi.local_order_confirm;

import com.xingshi.bean.RedPackageBean;
import com.xingshi.bean.ShippingAddressBean;
import com.xingshi.local_order_confirm.adapter.LocalOrderConfirmAdapter;
import com.xingshi.mvp.IView;

public interface LocalOrderConfirmView extends IView {
    void loadAddress(ShippingAddressBean addressBean);

    void noAddress();

    void loadRv(LocalOrderConfirmAdapter adapter);

    void loadFinish();

    void loadCoupon(RedPackageBean chooseRedPacgage);

    void loadSongType(String string);
}
