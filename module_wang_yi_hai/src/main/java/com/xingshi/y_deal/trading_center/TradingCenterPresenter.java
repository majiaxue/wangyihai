package com.xingshi.y_deal.trading_center;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.view.View;
import android.widget.Toast;

import com.ali.auth.third.core.util.StringUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.xingshi.adapter.MyRecyclerAdapter;
import com.xingshi.bean.TradingCenterBean;
import com.xingshi.common.CommonResource;
import com.xingshi.mvp.BasePresenter;
import com.xingshi.net.OnDataListener;
import com.xingshi.net.OnMyCallBack;
import com.xingshi.net.RetrofitUtil;
import com.xingshi.utils.LogUtil;
import com.xingshi.utils.MapUtil;
import com.xingshi.utils.SPUtil;
import com.xingshi.y_deal.trading_center.adapter.TradingCenterPayAdapter;
import com.xingshi.y_deal.trading_center.adapter.TradingCenterSellAdapter;
import com.xingshi.y_main.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

public class TradingCenterPresenter extends BasePresenter<TradingCenterView> {

    private List<TradingCenterBean.RecordsBean> tradingCenterBeanList = new ArrayList<>();
    private TradingCenterPayAdapter tradingCenterPayAdapter;
    private TradingCenterSellAdapter tradingCenterSellAdapter;

    public TradingCenterPresenter(Context context) {
        super(context);
    }

    @Override
    protected void onViewDestroy() {

    }

    public void initTab(TabLayout tradingCenterTab, int type) {
        tradingCenterTab.addTab(tradingCenterTab.newTab().setText("买单"));
        tradingCenterTab.addTab(tradingCenterTab.newTab().setText("卖单"));
        tradingCenterTab.addTab(tradingCenterTab.newTab().setText("平台回购"));

        tradingCenterTab.getTabAt(type).select();

        tradingCenterTab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (0 == tab.getPosition() || 1 == tab.getPosition()) {
                    initData(1, tab.getPosition());
                    getView().isShow(true);
                } else {
                    initData(1, tab.getPosition());
                    getView().isShow(false);
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

    public void initData(final int page, final int type) {
        if (0 == type) {
            Map map = MapUtil.getInstance().addParms("pageNum", page).addParms("pageSize", 10).build();
            Observable head = RetrofitUtil.getInstance().getApi(CommonResource.BASEURL_9006).getHead(CommonResource.BUYCURRENCYLIST, map, SPUtil.getToken());
            RetrofitUtil.getInstance().toSubscribe(head, new OnMyCallBack(new OnDataListener() {
                @Override
                public void onSuccess(String result, String msg) {
                    LogUtil.e("买单列表" + result);
                    getView().finishRefresh();
                    TradingCenterBean tradingCenterBean = JSON.parseObject(result, new TypeReference<TradingCenterBean>() {
                    }.getType());
                    if (1 == page) {
                        tradingCenterBeanList.clear();
                    }
                    tradingCenterBeanList.addAll(tradingCenterBean.getRecords());
                    tradingCenterPayAdapter = new TradingCenterPayAdapter(mContext, tradingCenterBeanList, R.layout.item_y_deal_rec);
                    getView().loadAdapter(tradingCenterPayAdapter);
                    tradingCenterPayAdapter.setViewOnClickListener(new MyRecyclerAdapter.ViewOnClickListener() {
                        @Override
                        public void ViewOnClick(View view, final int index) {
                            view.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    toCurrency(tradingCenterBeanList.get(index).getId(), type, index);
                                }
                            });
                        }
                    });
                }

                @Override
                public void onError(String errorCode, String errorMsg) {
                    getView().finishRefresh();
                    LogUtil.e("买单列表errorMsg" + errorMsg);

                }
            }));
        } else if (1 == type) {
            Map map = MapUtil.getInstance().addParms("pageNum", 1).addParms("pageSize", 10).build();
            Observable head = RetrofitUtil.getInstance().getApi(CommonResource.BASEURL_9006).getHead(CommonResource.SELLCURRENCYLIST, map, SPUtil.getToken());
            RetrofitUtil.getInstance().toSubscribe(head, new OnMyCallBack(new OnDataListener() {
                @Override
                public void onSuccess(String result, String msg) {
                    LogUtil.e("卖单列表" + result);
                    getView().finishRefresh();
                    TradingCenterBean tradingCenterBean = JSON.parseObject(result, new TypeReference<TradingCenterBean>() {
                    }.getType());
                    if (1 == page) {
                        tradingCenterBeanList.clear();
                    }
                    tradingCenterBeanList.addAll(tradingCenterBean.getRecords());
                    tradingCenterSellAdapter = new TradingCenterSellAdapter(mContext, tradingCenterBeanList, R.layout.item_y_deal_rec);
                    getView().loadAdapter(tradingCenterSellAdapter);
                    tradingCenterSellAdapter.setViewOnClickListener(new MyRecyclerAdapter.ViewOnClickListener() {
                        @Override
                        public void ViewOnClick(View view, final int index) {
                            view.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    toCurrency(tradingCenterBeanList.get(index).getId(), type, index);
                                }
                            });
                        }
                    });
                }

                @Override
                public void onError(String errorCode, String errorMsg) {
                    getView().finishRefresh();
                    LogUtil.e("卖单列表errorMsg" + errorMsg);

                }
            }));
        } else {
            Observable<ResponseBody> headWithout = RetrofitUtil.getInstance().getApi(CommonResource.BASEURL_9006).getHeadWithout(CommonResource.BUYBACKPARAMETER, SPUtil.getToken());
            RetrofitUtil.getInstance().toSubscribe(headWithout, new OnMyCallBack(new OnDataListener() {
                @Override
                public void onSuccess(String result, String msg) {
                    LogUtil.e("交易中心回购" + result);
                    JSONObject jsonObject = JSON.parseObject(result);
                    if (jsonObject != null) {
                        String serviceCharge = jsonObject.getString("serviceCharge");
                        int minNumber = jsonObject.getIntValue("minNumber");
                        double price = jsonObject.getDoubleValue("price");
                        double currency = jsonObject.getDoubleValue("currency");
                        getView().buyBack(serviceCharge, minNumber, price, currency);
                    }

                }

                @Override
                public void onError(String errorCode, String errorMsg) {
                    LogUtil.e("交易中心回购errorMsg" + errorMsg);

                }
            }));
        }
    }

    private void toCurrency(int id, final int type, final int position) {
        Map build = MapUtil.getInstance().addParms("id", id).addParms("type", type).build();
        Observable head = RetrofitUtil.getInstance().getApi(CommonResource.BASEURL_9006).getHead(CommonResource.TOCURRENCY, build, SPUtil.getToken());
        RetrofitUtil.getInstance().toSubscribe(head, new OnMyCallBack(new OnDataListener() {
            @Override
            public void onSuccess(String result, String msg) {
                LogUtil.e("形成交易" + result);
                if (0 == type) {
                    for (int i = 0; i < tradingCenterBeanList.size(); i++) {
                        tradingCenterBeanList.remove(position);
                    }
                    tradingCenterPayAdapter.notifyDataSetChanged();
                } else {
                    for (int i = 0; i < tradingCenterBeanList.size(); i++) {
                        tradingCenterBeanList.remove(position);
                    }
                    tradingCenterSellAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onError(String errorCode, String errorMsg) {
                LogUtil.e("形成交易errorMsg" + errorMsg);
                Toast.makeText(mContext, errorMsg, Toast.LENGTH_SHORT).show();
            }
        }));
    }

    public void buyBack(double price, String number, double totalPrice, double serviceCharge) {
        if (StringUtil.isBlank(number)) {
            Toast.makeText(mContext, "请输入数量", Toast.LENGTH_SHORT).show();
        } else {
            Map build = MapUtil.getInstance().addParms("price", price).addParms("number", number).addParms("totalPrice", totalPrice).addParms("serviceCharge", serviceCharge).build();
            String jsonString = JSON.toJSONString(build);
            RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonString);
            Observable<ResponseBody> responseBodyObservable = RetrofitUtil.getInstance().getApi(CommonResource.BASEURL_9006).postHeadWithBody(CommonResource.BUYBACK, requestBody, SPUtil.getToken());
            RetrofitUtil.getInstance().toSubscribe(responseBodyObservable, new OnMyCallBack(new OnDataListener() {
                @Override
                public void onSuccess(String result, String msg) {
                    LogUtil.e("回购" + result);
                    Toast.makeText(mContext, "回购成功", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onError(String errorCode, String errorMsg) {
                    LogUtil.e("回购errorMsg" + errorMsg);
                    Toast.makeText(mContext, errorMsg, Toast.LENGTH_SHORT).show();
                }
            }));
        }
    }
}
