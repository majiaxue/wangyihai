package com.xingshi.local_coupon;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.xingshi.bean.UserCouponBean;
import com.xingshi.common.CommonResource;
import com.xingshi.local_coupon.adapter.LocalCouponAdapter;
import com.xingshi.mvp.BasePresenter;
import com.xingshi.net.OnDataListener;
import com.xingshi.net.OnMyCallBack;
import com.xingshi.net.RetrofitUtil;
import com.xingshi.user_store.R;
import com.xingshi.utils.LogUtil;
import com.xingshi.utils.MapUtil;
import com.xingshi.utils.SPUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;

public class LocalCouponPresenter extends BasePresenter<LocalCouponView> {
    private List<UserCouponBean> couponList = new ArrayList<>();
    private LocalCouponAdapter couponAdapter;

    public LocalCouponPresenter(Context context) {
        super(context);
    }

    @Override
    protected void onViewDestroy() {

    }

    public void loadData() {
        Map map = MapUtil.getInstance().addParms("status", "0").addParms("userCode", SPUtil.getUserCode()).build();
        Observable observable = RetrofitUtil.getInstance().getApi(CommonResource.BASEURL_9003).getData(CommonResource.QUERY_COUPON, map);
        RetrofitUtil.getInstance().toSubscribe(observable, new OnMyCallBack(new OnDataListener() {
            @Override
            public void onSuccess(String result, String msg) {
                LogUtil.e("优惠券：" + result);
                couponList.addAll(JSON.parseArray(result, UserCouponBean.class));

                couponAdapter = new LocalCouponAdapter(mContext, couponList, R.layout.rv_local_my_coupon);
                if (getView() != null) {
                    getView().loadCoupon(couponAdapter);
                }
            }

            @Override
            public void onError(String errorCode, String errorMsg) {

            }
        }));

    }
}
