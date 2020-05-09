package com.xingshi.y_deal.my_pay_order;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.xingshi.adapter.MyRecyclerAdapter;
import com.xingshi.bean.AppealListBean;
import com.xingshi.bean.MyPayOrderAskToBuyBean;
import com.xingshi.bean.MyPayOrderBuyBean;
import com.xingshi.common.CommonResource;
import com.xingshi.mvp.BasePresenter;
import com.xingshi.net.OnDataListener;
import com.xingshi.net.OnMyCallBack;
import com.xingshi.net.RetrofitUtil;
import com.xingshi.utils.LogUtil;
import com.xingshi.utils.MapUtil;
import com.xingshi.utils.SPUtil;
import com.xingshi.y_deal.appeal.AppealActivity;
import com.xingshi.y_deal.ask_to_buy.AskToBuyActivity;
import com.xingshi.y_deal.my_pay_order.adapter.MyPayOrderAppealListAdapter;
import com.xingshi.y_deal.my_pay_order.adapter.MyPayOrderAskToBuyAdapter;
import com.xingshi.y_deal.my_pay_order.adapter.MyPayOrderBuyAdapter;
import com.xingshi.y_deal.my_pay_order_details.MyPayOrderDetailsActivity;
import com.xingshi.y_main.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;

public class MyPayOrderPresenter extends BasePresenter<MyPayOrderView> {

    private int page = 1;
    private List<MyPayOrderBuyBean.RecordsBean> recordsBeanList = new ArrayList<>();
    private List<MyPayOrderAskToBuyBean.RecordsBean> askToBuyList = new ArrayList<>();
    private List<AppealListBean.RecordsBean> appealList = new ArrayList<>();
    private MyPayOrderBuyAdapter myPayOrderBuyAdapter;
    private MyPayOrderAskToBuyAdapter myPayOrderAskToBuyAdapter;
    private MyPayOrderAppealListAdapter myPayOrderAppealListAdapter;

    public MyPayOrderPresenter(Context context) {
        super(context);
    }

    @Override
    protected void onViewDestroy() {

    }

    public void initTab(TabLayout myPayOrderTab) {
        myPayOrderTab.addTab(myPayOrderTab.newTab().setText("买入"));
        myPayOrderTab.addTab(myPayOrderTab.newTab().setText("求购"));
        myPayOrderTab.addTab(myPayOrderTab.newTab().setText("申诉"));

        myPayOrderTab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        initData(page);
                        break;
                    case 1:
                        askToBuy(page);
                        break;
                    case 2:
                        appealList(page);
                        break;
                    default:
                        break;
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

    public void initData(final int page) {
        Map map = MapUtil.getInstance().addParms("pageNum", page).addParms("pageSize", 10).addParms("type", 0).build();
        Observable head = RetrofitUtil.getInstance().getApi(CommonResource.BASEURL_9006).getHead(CommonResource.MYCURRENCY, map, SPUtil.getToken());
        RetrofitUtil.getInstance().toSubscribe(head, new OnMyCallBack(new OnDataListener() {
            @Override
            public void onSuccess(String result, String msg) {
                LogUtil.e("我的买单买入" + result);
                getView().finishRefresh();
                MyPayOrderBuyBean myPayOrderBuyBean = JSON.parseObject(result, new TypeReference<MyPayOrderBuyBean>() {
                }.getType());

                if (1 == page) {
                    recordsBeanList.clear();
                }
                recordsBeanList.addAll(myPayOrderBuyBean.getRecords());
                myPayOrderBuyAdapter = new MyPayOrderBuyAdapter(mContext, recordsBeanList, R.layout.item_my_pay_order_rec);
                if (getView() != null) {
                    getView().loadAdapter(myPayOrderBuyAdapter);
                }

                myPayOrderBuyAdapter.setViewOnClickListener(new MyRecyclerAdapter.ViewOnClickListener() {
                    @Override
                    public void ViewOnClick(View view, final int index) {
                        view.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
//                                    Toast.makeText(mContext, "无法查看", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(mContext, MyPayOrderDetailsActivity.class);
                                intent.putExtra("status", recordsBeanList.get(index).getStatus());
                                intent.putExtra("id", recordsBeanList.get(index).getId());
                                mContext.startActivity(intent);
                            }
                        });
                    }
                });

            }

            @Override
            public void onError(String errorCode, String errorMsg) {
                LogUtil.e("我的买单买入errorMsg" + errorMsg);
                getView().finishRefresh();
            }
        }));
    }

    public void askToBuy(final int page) {
        Map map = MapUtil.getInstance().addParms("pageNum", page).addParms("pageSize", 10).addParms("type", 0).build();
        Observable head = RetrofitUtil.getInstance().getApi(CommonResource.BASEURL_9006).getHead(CommonResource.MYBUYCURRENCYLIST, map, SPUtil.getToken());
        RetrofitUtil.getInstance().toSubscribe(head, new OnMyCallBack(new OnDataListener() {
            @Override
            public void onSuccess(String result, String msg) {
                LogUtil.e("我的买单求购" + result);
                getView().finishRefresh();

                MyPayOrderAskToBuyBean myPayOrderAskToBuyBean = JSON.parseObject(result, new TypeReference<MyPayOrderAskToBuyBean>() {
                }.getType());
                if (1 == page) {
                    askToBuyList.clear();
                }
                askToBuyList.addAll(myPayOrderAskToBuyBean.getRecords());
                myPayOrderAskToBuyAdapter = new MyPayOrderAskToBuyAdapter(mContext, askToBuyList, R.layout.item_my_pay_order_rec);
                if (getView() != null) {
                    getView().loadAdapter(myPayOrderAskToBuyAdapter);
                }
                myPayOrderAskToBuyAdapter.setViewOnClickListener(new MyRecyclerAdapter.ViewOnClickListener() {
                    @Override
                    public void ViewOnClick(View view, final int index) {
                        view.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(mContext, AskToBuyActivity.class);
                                intent.putExtra("status", askToBuyList.get(index).getStatus());
                                intent.putExtra("id", askToBuyList.get(index).getId());
                                mContext.startActivity(intent);
                            }
                        });
                    }
                });
            }

            @Override
            public void onError(String errorCode, String errorMsg) {
                LogUtil.e("我的买单求购errorMsg" + errorMsg);
                getView().finishRefresh();

            }
        }));
    }

    public void appealList(final int page) {
        Map map = MapUtil.getInstance().addParms("pageNum", page).addParms("pageSize", 10).addParms("type", 0).build();
        Observable head = RetrofitUtil.getInstance().getApi(CommonResource.BASEURL_9006).getHead(CommonResource.APPEALLIST, map, SPUtil.getToken());
        RetrofitUtil.getInstance().toSubscribe(head, new OnMyCallBack(new OnDataListener() {
            @Override
            public void onSuccess(String result, String msg) {
                LogUtil.e("我的买单投诉" + result);
                getView().finishRefresh();
                AppealListBean appealListBean = JSON.parseObject(result, new TypeReference<AppealListBean>() {
                }.getType());

                if (1 == page) {
                    appealList.clear();
                }
                appealList.addAll(appealListBean.getRecords());
                myPayOrderAppealListAdapter = new MyPayOrderAppealListAdapter(mContext, appealList, R.layout.item_my_pay_order_rec);
                if (getView() != null) {
                    getView().loadAdapter(myPayOrderAppealListAdapter);
                }
                myPayOrderAppealListAdapter.setViewOnClickListener(new MyRecyclerAdapter.ViewOnClickListener() {
                    @Override
                    public void ViewOnClick(View view, final int index) {
                        view.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
//                                    Toast.makeText(mContext, "无法查看", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(mContext, AppealActivity.class);
                                intent.putExtra("type", 0);
                                intent.putExtra("id", appealList.get(index).getId());
                                mContext.startActivity(intent);
                            }
                        });
                    }
                });
            }

            @Override
            public void onError(String errorCode, String errorMsg) {
                LogUtil.e("我的买单投诉errorMsg" + errorMsg);
                getView().finishRefresh();
            }
        }));
    }
}
