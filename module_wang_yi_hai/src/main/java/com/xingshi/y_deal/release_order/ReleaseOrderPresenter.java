package com.xingshi.y_deal.release_order;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.text.TextUtils;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xingshi.common.CommonResource;
import com.xingshi.mvp.BasePresenter;
import com.xingshi.net.OnDataListener;
import com.xingshi.net.OnMyCallBack;
import com.xingshi.net.RetrofitUtil;
import com.xingshi.utils.LogUtil;
import com.xingshi.utils.MapUtil;
import com.xingshi.utils.SPUtil;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

public class ReleaseOrderPresenter extends BasePresenter<ReleaseOrderView> {

    public ReleaseOrderPresenter(Context context) {
        super(context);
    }

    @Override
    protected void onViewDestroy() {

    }

    public void initTab(TabLayout releaseOrderTab) {
        releaseOrderTab.addTab(releaseOrderTab.newTab().setText("发布买单"));
        releaseOrderTab.addTab(releaseOrderTab.newTab().setText("发布卖单"));

        releaseOrderTab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (0 == tab.getPosition()) {
                    getView().checkoutLinear(true);
                } else {
                    getView().checkoutLinear(false);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    public void serviceCharge() {
        Observable<ResponseBody> headWithout = RetrofitUtil.getInstance().getApi(CommonResource.BASEURL_9006).getHeadWithout(CommonResource.SERVICECHARGE, SPUtil.getToken());
        RetrofitUtil.getInstance().toSubscribe(headWithout, new OnMyCallBack(new OnDataListener() {
            @Override
            public void onSuccess(String result, String msg) {
                LogUtil.e("发布卖单手续费比率" + result);
                JSONObject jsonObject = JSON.parseObject(result);
                String serviceCharge = jsonObject.getString("serviceCharge");
                String max = jsonObject.getString("max");
                String less = jsonObject.getString("less");
                double service = Double.valueOf(serviceCharge) / 100;
                getView().serviceCharge(service, max, less);
            }

            @Override
            public void onError(String errorCode, String errorMsg) {
                LogUtil.e("发布卖单手续费比率errorMsg" + errorMsg);

            }
        }));
    }

    public void buyRule() {
        Observable<ResponseBody> headWithout = RetrofitUtil.getInstance().getApi(CommonResource.BASEURL_9006).getDataWithout(CommonResource.BUYCURRENCYPARAM);
        RetrofitUtil.getInstance().toSubscribe(headWithout, new OnMyCallBack(new OnDataListener() {
            @Override
            public void onSuccess(String result, String msg) {
                LogUtil.e("发布买单规则" + result);
                JSONObject jsonObject = JSON.parseObject(result);
                String max = jsonObject.getString("max");
                String less = jsonObject.getString("less");
                getView().serviceCharge(max, less);
            }

            @Override
            public void onError(String errorCode, String errorMsg) {
                LogUtil.e("发布买单规则errorMsg" + errorMsg);

            }
        }));
    }

    //买单
    public void releasePayTheBill(String price, String num, int totalAmount) {
        if (TextUtils.isEmpty(price)) {
            Toast.makeText(mContext, "请输入单价", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(num)) {
            Toast.makeText(mContext, "请输入数量", Toast.LENGTH_SHORT).show();
        } else {
            Map map = MapUtil.getInstance().addParms("price", price).addParms("number", num).addParms("totalPrice", totalAmount).build();
            String jsonString = JSON.toJSONString(map);
            RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonString);
            Observable<ResponseBody> responseBodyObservable = RetrofitUtil.getInstance().getApi(CommonResource.BASEURL_9006).postHeadWithBody(CommonResource.BUYCURRENCYADD, requestBody, SPUtil.getToken());
            RetrofitUtil.getInstance().toSubscribe(responseBodyObservable, new OnMyCallBack(new OnDataListener() {
                @Override
                public void onSuccess(String result, String msg) {
                    LogUtil.e("发布买单" + result);
                    Toast.makeText(mContext, "发布成功", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onError(String errorCode, String errorMsg) {
                    LogUtil.e("发布买单errorMsg" + errorMsg);
                    Toast.makeText(mContext, errorMsg, Toast.LENGTH_SHORT).show();
                }
            }));
        }
    }

    //卖单
    public void releaseSellOrders(String price, String num, double totalAmount) {
        LogUtil.e("num---------"+num);
        LogUtil.e("totalamount-------------"+totalAmount);
        if (TextUtils.isEmpty(price)) {
            Toast.makeText(mContext, "请输入单价", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(num+"")) {
            Toast.makeText(mContext, "请输入数量", Toast.LENGTH_SHORT).show();
        } else {
            Map map = MapUtil.getInstance().addParms("price", price).addParms("number", num).addParms("totalPrice", totalAmount).build();
            String jsonString = JSON.toJSONString(map);
            RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonString);
            Observable<ResponseBody> responseBodyObservable = RetrofitUtil.getInstance().getApi(CommonResource.BASEURL_9006).postHeadWithBody(CommonResource.SELLCURRENCYADD, requestBody, SPUtil.getToken());
            RetrofitUtil.getInstance().toSubscribe(responseBodyObservable, new OnMyCallBack(new OnDataListener() {
                @Override
                public void onSuccess(String result, String msg) {
                    LogUtil.e("发布卖单" + result);
                    Toast.makeText(mContext, "发布成功", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onError(String errorCode, String errorMsg) {
                    LogUtil.e("发布卖单errorMsg" + errorMsg);
                    Toast.makeText(mContext, errorMsg, Toast.LENGTH_SHORT).show();
                }
            }));
        }
    }
}
