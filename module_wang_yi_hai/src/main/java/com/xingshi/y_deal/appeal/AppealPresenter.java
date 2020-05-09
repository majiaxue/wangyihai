package com.xingshi.y_deal.appeal;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.xingshi.bean.AppealBean;
import com.xingshi.common.CommonResource;
import com.xingshi.mvp.BasePresenter;
import com.xingshi.net.OnDataListener;
import com.xingshi.net.OnMyCallBack;
import com.xingshi.net.RetrofitUtil;
import com.xingshi.utils.LogUtil;
import com.xingshi.utils.MapUtil;

import java.util.Map;

import io.reactivex.Observable;

public class AppealPresenter extends BasePresenter<AppealView> {

    public AppealPresenter(Context context) {
        super(context);
    }

    @Override
    protected void onViewDestroy() {

    }

    public void initData(int id) {
        Map map = MapUtil.getInstance().addParms("id", id).build();
        Observable data = RetrofitUtil.getInstance().getApi(CommonResource.BASEURL_9006).getData(CommonResource.APPEALVIEW, map);
        RetrofitUtil.getInstance().toSubscribe(data, new OnMyCallBack(new OnDataListener() {
            @Override
            public void onSuccess(String result, String msg) {
                LogUtil.e("申述详情" + result);
                AppealBean appealBean = JSON.parseObject(result, AppealBean.class);
                getView().loadData(appealBean);
            }

            @Override
            public void onError(String errorCode, String errorMsg) {
                LogUtil.e("申述详情errorMsg" + errorMsg);
            }
        }));
    }
}
