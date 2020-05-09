package com.xingshi.local_store.ShoppingRight;


import com.alibaba.android.arouter.launcher.ARouter;
import com.alibaba.fastjson.JSON;
import com.xingshi.bean.LocalCartBean;
import com.xingshi.bean.LocalOrderBean;
import com.xingshi.common.CommonResource;
import com.xingshi.net.OnDataListener;
import com.xingshi.net.OnMyCallBack;
import com.xingshi.net.RetrofitUtil;
import com.xingshi.utils.LogUtil;
import com.xingshi.utils.SPUtil;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import okhttp3.ResponseBody;

/**
 * author pangchao
 * created on 2017/5/12
 * email fat_chao@163.com.
 */

public class SortDetailPresenter extends BasePresenter2 {
    @Override
    protected void getData() {

    }

    public void loadCart() {
        Observable<ResponseBody> observable = RetrofitUtil.getInstance().getApi(CommonResource.BASEURL_9010).getDataWithout(CommonResource.LOCAL_GET_CART + SPUtil.getStringValue(CommonResource.SELLERID) + "/" + SPUtil.getUserCode());
        RetrofitUtil.getInstance().toSubscribe(observable, new OnMyCallBack(new OnDataListener() {
            @Override
            public void onSuccess(String result, String msg) {
                LogUtil.e("购物车：" + result);
                LocalCartBean localCartBeans = JSON.parseObject(result, LocalCartBean.class);
                submitOrder(localCartBeans);
            }

            @Override
            public void onError(String errorCode, String errorMsg) {
                LogUtil.e(errorCode + "--------------" + errorMsg);
            }
        }));
    }

    public void submitOrder(LocalCartBean data) {
        List<LocalOrderBean.LocalOrderItemListBean> list = new ArrayList<>();
        double price = 0;
        boolean hasGoods = false;
        for (int i = 0; i < data.getLocalShopcarList().size(); i++) {
            hasGoods = true;
            LocalOrderBean.LocalOrderItemListBean bean = new LocalOrderBean.LocalOrderItemListBean();
            bean.setGoodsName(data.getLocalShopcarList().get(i).getLocalGoodsName());
            bean.setGoodsId(data.getLocalShopcarList().get(i).getLocalGoodsId());
            bean.setGoodsNum(Integer.valueOf(data.getLocalShopcarList().get(i).getNum()));
            bean.setGoodsPic(data.getLocalShopcarList().get(i).getLocalGoodsPic());
            bean.setGoodsSpec(data.getLocalShopcarList().get(i).getLocalGoodsSpecification());
            bean.setGoodsPrice(data.getLocalShopcarList().get(i).getPrice() + "");

            list.add(bean);
        }
        LocalOrderBean localOrderBean = new LocalOrderBean();
        localOrderBean.setLocalSellerId(SPUtil.getStringValue(CommonResource.SELLERID));
        localOrderBean.setSellerName(SPUtil.getStringValue(CommonResource.SELLERNAME));
        localOrderBean.setUserCode(SPUtil.getUserCode());
        localOrderBean.setLocalOrderItemList(list);
        localOrderBean.setTotalMoney(Double.valueOf(data.getTotalMoney()));
        localOrderBean.setDeliverType("1");
        localOrderBean.setSellerManJian(data.getAmount());
        LogUtil.e("------------------->" + data.getAmount());

        if (hasGoods) {
            ARouter.getInstance().build("/module_local/LocalOrderConfirmActivity").withSerializable("bean", localOrderBean).navigation();
        }
    }
}
