package com.xingshi.y_mine.y_welfare_center.pay_a_tip;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alipay.sdk.app.PayTask;
import com.xingshi.adapter.MyRecyclerAdapter;
import com.xingshi.bean.AliPayBean;
import com.xingshi.bean.PayATipBean;
import com.xingshi.bean.PayATipDetailBean;
import com.xingshi.bean.WeChatPayBean;
import com.xingshi.common.CommonResource;
import com.xingshi.mvp.BasePresenter;
import com.xingshi.net.OnDataListener;
import com.xingshi.net.OnMyCallBack;
import com.xingshi.net.OnTripartiteCallBack;
import com.xingshi.net.RetrofitUtil;
import com.xingshi.utils.ClickUtil;
import com.xingshi.utils.DisplayUtil;
import com.xingshi.utils.LogUtil;
import com.xingshi.utils.MapUtil;
import com.xingshi.utils.OnPopListener;
import com.xingshi.utils.PopUtils;
import com.xingshi.utils.ProcessDialogUtil;
import com.xingshi.utils.SPUtil;
import com.xingshi.y_main.R;
import com.xingshi.y_mine.y_welfare_center.pay_a_tip.adapter.PayATipAdapter;
import com.xingshi.y_mine.y_welfare_center.pay_a_tip_details.PayATipDetailsActivity;
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

public class PayATipPresenter extends BasePresenter<PayATipView> {
    private String info = "";
    private final int ALI_CODE = 0x1234;

    private List<PayATipBean.RecordsBean> recordsBeanList = new ArrayList<>();
    private PayATipAdapter payATipAdapter;

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            if (msg.what == ALI_CODE) {
                Map<String, String> map = (Map<String, String>) msg.obj;
                String resultStatus = map.get("resultStatus");
                String result = map.get("result");
                String memo = map.get("memo");
                if ("9000".equals(resultStatus)) {
//                   ((Activity) mContext).finish();
                    isPay(index);
                    Toast.makeText(mContext, "支付成功", Toast.LENGTH_SHORT).show();
                } else {
                    LogUtil.e("resultStatus--------"+resultStatus);
                    Toast.makeText(mContext, "支付失败", Toast.LENGTH_SHORT).show();
                }
            }
        }

    };
    private PayATipDetailBean payATipDetailBean;
    private int index;

    public PayATipPresenter(Context context) {
        super(context);
    }

    @Override
    protected void onViewDestroy() {

    }

    public void initData(final int page) {
        final Map map = MapUtil.getInstance().addParms("pageNum", 1).addParms("pageSize", 10).build();
        Observable data = RetrofitUtil.getInstance().getApi(CommonResource.BASEURL_9006).getData(CommonResource.NEWSPAPERLIST, map);
        RetrofitUtil.getInstance().toSubscribe(data, new OnMyCallBack(new OnDataListener() {
            @Override
            public void onSuccess(String result, String msg) {
                LogUtil.e("付费线报" + result);
                getView().finishRefresh();
                PayATipBean payATipBean = JSON.parseObject(result, PayATipBean.class);
                if (payATipBean.getRecords().size() != 0) {
//                    if (1 == page) {
//                        recordsBeanList.clear();
//                    }
                    recordsBeanList.addAll(payATipBean.getRecords());
                    if (payATipAdapter == null) {
                        payATipAdapter = new PayATipAdapter(mContext, recordsBeanList, R.layout.item_pay_a_tip_rec);
                        if (getView() != null) {
                            getView().loadAdapter(payATipAdapter);
                        }
                    } else {
                        payATipAdapter.notifyDataSetChanged();
                    }

                    payATipAdapter.setOnItemClick(new MyRecyclerAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(RecyclerView parent, View view, final int position) {
                            index = position;
                            getView().position(position);
                            isPay(position);
                        }
                    });
                }
            }

            @Override
            public void onError(String errorCode, String errorMsg) {
                getView().finishRefresh();
                LogUtil.e("付费线报errorMsg" + errorMsg);
            }
        }));
    }

    public void isPay(final int position) {
        ProcessDialogUtil.showProcessDialog(mContext);
        Map build = MapUtil.getInstance().addParms("id", recordsBeanList.get(position).getId()).build();
        Observable head = RetrofitUtil.getInstance().getApi(CommonResource.BASEURL_9006).getHead(CommonResource.JUDGENEWSPAPER, build, SPUtil.getToken());
        RetrofitUtil.getInstance().toSubscribe(head, new OnMyCallBack(new OnDataListener() {
            @Override
            public void onSuccess(String result, String msg) {
                LogUtil.e("付费线报查看" + result);
                payATipDetailBean = JSON.parseObject(result, PayATipDetailBean.class);
                Intent intent = new Intent(mContext, PayATipDetailsActivity.class);
                intent.putExtra("payATipDetailBean", payATipDetailBean);
                mContext.startActivity(intent);
            }

            @Override
            public void onError(String errorCode, String errorMsg) {
                LogUtil.e("付费线报查看error" + errorCode + "" + errorMsg);
                if ("1".equals(errorMsg)) {
                    View view = LayoutInflater.from(mContext).inflate(R.layout.pop_payment, null);
                    final TextView popHeaderCancel = view.findViewById(R.id.pop_payment_cancel);
                    final TextView popHeaderCamera = view.findViewById(R.id.pop_payment_wechat);
                    final TextView popHeaderXiangce = view.findViewById(R.id.pop_payment_ali);
                    final TextView popPaymentBalance = view.findViewById(R.id.pop_payment_balance);
                    PopUtils.setTransparency(mContext, 0.3f);
                    PopUtils.createPop(mContext, view, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, new OnPopListener() {
                        @Override
                        public void setOnPop(final PopupWindow pop) {
                            popHeaderCancel.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    pop.dismiss();
                                }
                            });
                            popHeaderCamera.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if (ClickUtil.isFastClick()) {
                                        pay(recordsBeanList.get(position).getId(), 0);
                                    }
                                    pop.dismiss();
                                }
                            });
                            popHeaderXiangce.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if (ClickUtil.isFastClick()) {
                                        pay(recordsBeanList.get(position).getId(), 1);
                                    }
                                    pop.dismiss();
                                }
                            });
                            popPaymentBalance.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if (ClickUtil.isFastClick()) {
                                        pay(recordsBeanList.get(position).getId(), 2);
                                    }
                                    pop.dismiss();
                                }
                            });
                        }
                    });
                }
            }
        }));
    }

    private void pay(int id, final int type) {
        //type == 0微信  type == 1支付宝  type 2 == 余额
        Map map = MapUtil.getInstance().addParms("id", id).build();
        Observable head = RetrofitUtil.getInstance().getApi(CommonResource.BASEURL_9006).getHead(CommonResource.PAYNEWSPAPER, map, SPUtil.getToken());
        RetrofitUtil.getInstance().toSubscribe(head, new OnMyCallBack(new OnDataListener() {
            @Override
            public void onSuccess(String result, String msg) {
                LogUtil.e("生成订单" + result);
                JSONObject jsonObject = JSON.parseObject(result);
                String orderSn = jsonObject.getString("orderSn");
                String price = jsonObject.getString("price");
                String userCode = jsonObject.getString("userCode");

                if (0 == type) {
                    //微信
                    weChat(orderSn, price, userCode);
                } else if (1 == type) {
                    //支付宝
                    aliPay(orderSn, price, userCode);
                } else {
                    //余额
                    balance(orderSn, price);
                }
            }

            @Override
            public void onError(String errorCode, String errorMsg) {
                LogUtil.e("生成订单errorMsg" + errorMsg);
                Toast.makeText(mContext, errorMsg, Toast.LENGTH_SHORT).show();
            }
        }));
    }

    //微信支付
    private void weChat(String orderSn, String price, String userCode) {
        final IWXAPI api = WXAPIFactory.createWXAPI(mContext, CommonResource.WXAPPID, false);

        Map map = MapUtil.getInstance().addParms("orderSn", orderSn).addParms("totalAmount", price).addParms("userCode", userCode).addParms("type", 3).build();
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
    private void aliPay(String orderSn, String price, String userCode) {
        LogUtil.e("ordersn----------"+orderSn);
        LogUtil.e("price----------"+price);
        LogUtil.e("userCode----------"+userCode);
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("orderSn",orderSn);
        jsonObject.put("totalAmount",price);
        jsonObject.put("userCode",userCode);
        jsonObject.put("type",2);
        String string = JSON.toJSONString(jsonObject);
        //Map map = MapUtil.getInstance().addParms("orderSn", orderSn).addParms("totalAmount", price).addParms("userCode", userCode).addParms("type", 3).build();
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), string);
//        String jsonString = JSON.toJSONString(map);
//        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonString);
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

    //余额支付
    private void balance(String orderSn, String price) {
        Map map = MapUtil.getInstance().addParms("orderSn", orderSn).addParms("totalAmount", price).build();
        Observable head = RetrofitUtil.getInstance().getApi(CommonResource.BASEURL_9006).getHead(CommonResource.WIREBALANCEPAYMENT, map, SPUtil.getToken());
        RetrofitUtil.getInstance().toSubscribe(head, new OnMyCallBack(new OnDataListener() {
            @Override
            public void onSuccess(String result, String msg) {
                LogUtil.e("余额支付" + result);
                isPay(index);
            }

            @Override
            public void onError(String errorCode, String errorMsg) {
                LogUtil.e("余额支付" + errorMsg);
                Toast.makeText(mContext, "" + errorMsg, Toast.LENGTH_SHORT).show();
            }
        }));
    }

    private Runnable payRunnable = new Runnable() {

        @Override
        public void run() {
            PayTask alipay = new PayTask((Activity) mContext);
            Map<String, String> result = alipay.payV2(info, true);

            LogUtil.e("result-----"+result);
            Message msg = new Message();
            msg.what = ALI_CODE;
            msg.obj = result;
            mHandler.sendMessage(msg);
        }
    };

}
