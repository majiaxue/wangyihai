package com.xingshi.y_mine.y_balance_account;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alipay.sdk.app.PayTask;
import com.xingshi.bean.AliPayBean;
import com.xingshi.bean.WeChatPayBean;
import com.xingshi.bean.YBalanceAccountBean;
import com.xingshi.common.CommonResource;
import com.xingshi.mvp.BasePresenter;
import com.xingshi.net.OnDataListener;
import com.xingshi.net.OnMyCallBack;
import com.xingshi.net.OnTripartiteCallBack;
import com.xingshi.net.RetrofitUtil;
import com.xingshi.utils.LogUtil;
import com.xingshi.utils.MapUtil;
import com.xingshi.utils.SPUtil;
import com.xingshi.y_main.R;
import com.xingshi.y_mine.y_balance_account.adapter.YBalanceAccountAdapter;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

public class YBalanceAccountPresenter extends BasePresenter<YBalanceAccountView> {

    private List<YBalanceAccountBean.RecordsBean> recordsBeanList = new ArrayList<>();
    private YBalanceAccountAdapter yBalanceAccountAdapter;

    private String info = "";
    private final int ALI_CODE = 0x1234;
    private int id;

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            if (msg.what == ALI_CODE) {
                Map<String, String> map = (Map<String, String>) msg.obj;
                String resultStatus = map.get("resultStatus");
                String result = map.get("result");
                String memo = map.get("memo");
                if ("9000".equals(resultStatus)) {
                    initData();
                    Toast.makeText(mContext, "支付成功", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(mContext, "支付失败", Toast.LENGTH_SHORT).show();
                }
            }
        }

    };

    public YBalanceAccountPresenter(Context context) {
        super(context);
    }

    @Override
    protected void onViewDestroy() {

    }

    public void initData() {
        Observable<ResponseBody> dataWithout = RetrofitUtil.getInstance().getApi(CommonResource.BASEURL_9006).getHeadWithout(CommonResource.MERCHANTBALANCE, SPUtil.getToken());
        RetrofitUtil.getInstance().toSubscribe(dataWithout, new OnMyCallBack(new OnDataListener() {
            @Override
            public void onSuccess(String result, String msg) {
                LogUtil.e("商家余额" + result);
                getView().loadData(result);
            }

            @Override
            public void onError(String errorCode, String errorMsg) {
                LogUtil.e("商家余额error" + errorMsg);
            }
        }));
    }

    public void merchantRecord(final int page) {
        Map map = MapUtil.getInstance().addParms("pageNum", page).addParms("pageSize", 10).build();
        Observable data = RetrofitUtil.getInstance().getApi(CommonResource.BASEURL_9006).getHead(CommonResource.MERCHANTRECORD, map, SPUtil.getToken());
        RetrofitUtil.getInstance().toSubscribe(data, new OnMyCallBack(new OnDataListener() {
            @Override
            public void onSuccess(String result, String msg) {
                LogUtil.e("账户明细" + result);
                getView().finishRefresh();
                YBalanceAccountBean yBalanceAccountBean = JSON.parseObject(result, YBalanceAccountBean.class);
                if (1 == page) {
                    recordsBeanList.clear();
                }
                recordsBeanList.addAll(yBalanceAccountBean.getRecords());
                if (yBalanceAccountAdapter == null){
                    yBalanceAccountAdapter = new YBalanceAccountAdapter(mContext, recordsBeanList, R.layout.item_y_contribution_value_rec);
                    getView().loadAdapter(yBalanceAccountAdapter);
                }else{
                    yBalanceAccountAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onError(String errorCode, String errorMsg) {
                getView().finishRefresh();
                LogUtil.e("账户明细errorMsg" + errorMsg);
            }
        }));
    }

    //微信支付
    public void weChat(String price,String userCode) {
        final IWXAPI api = WXAPIFactory.createWXAPI(mContext, CommonResource.WXAPPID, false);

        Map map = MapUtil.getInstance().addParms("totalAmount", price).addParms("userCode", userCode).addParms("type", 1).build();
        String jsonString = JSON.toJSONString(map);
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonString);
        Observable<ResponseBody> responseBodyObservable = RetrofitUtil.getInstance().getApi(CommonResource.BASEURL_9006).postDataWithBody(CommonResource.WXPAYWELFARECENTRE, body);
        RetrofitUtil.getInstance().toSubscribe(responseBodyObservable, new OnTripartiteCallBack(new OnDataListener() {
            @Override
            public void onSuccess(String result, String msg) {
                LogUtil.e("微信支付-------------->" + result);
                try {

                    WeChatPayBean payBean = JSON.parseObject(result, WeChatPayBean.class);

                    PayReq request = new PayReq();
                    request.appId = payBean.getAppid();
                    request.partnerId = payBean.getPartnerid();
                    request.prepayId = payBean.getPrepayid();
                    request.packageValue = "Sign=WXPay";
                    request.nonceStr = payBean.getNoncestr();
                    request.timeStamp = payBean.getTimestamp();
                    request.sign = payBean.getSign();

                    api.sendReq(request);
                    SPUtil.addParm("wxpay", "14");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(String errorCode, String errorMsg) {

            }
        }));
    }


    //支付宝支付
    public void aliPay(String price,String userCode) {
        Map map = MapUtil.getInstance().addParms("totalAmount", price).addParms("userCode", userCode).addParms("type", 1).build();
        String jsonString = JSON.toJSONString(map);
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonString);
        Observable observable = RetrofitUtil.getInstance().getApi(CommonResource.BASEURL_9006).postDataWithBody(CommonResource.ALIPAYWELFARECENTRE, body);
        RetrofitUtil.getInstance().toSubscribe(observable, new OnMyCallBack(new OnDataListener() {
            @Override
            public void onSuccess(String result, String msg) {
                LogUtil.e("支付宝：" + result);
                AliPayBean aliPayBean = JSON.parseObject(result, AliPayBean.class);
                info = aliPayBean.getBody();
                Thread thread = new Thread(payRunnable);
                thread.start();
            }

            @Override
            public void onError(String errorCode, String errorMsg) {
            }
        }));
    }

    private Runnable payRunnable = new Runnable() {

        @Override
        public void run() {
            PayTask alipay = new PayTask((Activity) mContext);
            Map<String, String> result = alipay.payV2(info, true);

            Message msg = new Message();
            msg.what = ALI_CODE;
            msg.obj = result;
            mHandler.sendMessage(msg);
        }
    };

}
