package com.xingshi.local_detail.local_goods;

import android.content.Context;
import android.content.Intent;

import com.alibaba.fastjson.JSON;
import com.xingshi.bean.LocalGoodsBean;
import com.xingshi.bean.LocalShopBean;
import com.xingshi.common.CommonResource;
import com.xingshi.local_detail.adapter.LocalDetailGoodsAdapter;
import com.xingshi.local_pay.LocalPayActivity;
import com.xingshi.mvp.BasePresenter;
import com.xingshi.net.OnDataListener;
import com.xingshi.net.OnMyCallBack;
import com.xingshi.net.RetrofitUtil;
import com.xingshi.user_store.R;
import com.xingshi.utils.LogUtil;
import com.xingshi.utils.MapUtil;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;

public class LocalGoodsPresenter extends BasePresenter<LocalGoodsView> {
    private LocalDetailGoodsAdapter goodsAdapter;
    private List<LocalGoodsBean.RecordsBean> beanList;

    public LocalGoodsPresenter(Context context) {
        super(context);
    }

    @Override
    protected void onViewDestroy() {

    }

    public void loadData(String sellerId) {
        Map map = MapUtil.getInstance().addParms("sellerId", sellerId).addParms("page", "1").addParms("size", "1000").build();
        Observable observable = RetrofitUtil.getInstance().getApi(CommonResource.BASEURL_9001).getData(CommonResource.LOCAL_SHOP_GOODS, map);
        RetrofitUtil.getInstance().toSubscribe(observable, new OnMyCallBack(new OnDataListener() {
            @Override
            public void onSuccess(String result, String msg) {
                LogUtil.e("本地商品：" + result);
                try {
                    if (result != null) {
                        LocalGoodsBean goodsBean = JSON.parseObject(result, LocalGoodsBean.class);
                        beanList = goodsBean.getRecords();
                        goodsAdapter = new LocalDetailGoodsAdapter(mContext, beanList, R.layout.rv_local_detail_goods);
                        if (getView() != null) {
                            getView().loadGoods(goodsAdapter);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(String errorCode, String errorMsg) {
                LogUtil.e(errorCode + "----------" + errorMsg);
            }
        }));
    }

    public void jumpToPay(LocalShopBean bean) {
        Intent intent = new Intent(mContext, LocalPayActivity.class);
        intent.putExtra("bean", bean);
        mContext.startActivity(intent);
    }
}
