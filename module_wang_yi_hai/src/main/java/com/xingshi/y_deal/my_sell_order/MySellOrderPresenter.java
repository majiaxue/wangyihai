package com.xingshi.y_deal.my_sell_order;

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
import com.xingshi.y_deal.my_sell_order.adapter.MySellOrderAppealListAdapter;
import com.xingshi.y_deal.my_sell_order.adapter.MySellOrderAskToBuyAdapter;
import com.xingshi.y_deal.my_sell_order.adapter.MySellOrderSellAdapter;
import com.xingshi.y_deal.sale.SaleActivity;
import com.xingshi.y_deal.sell.SellActivity;
import com.xingshi.y_main.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;

public class MySellOrderPresenter extends BasePresenter<MySellOrderView> {

    private int page = 1;
    private List<MyPayOrderBuyBean.RecordsBean> recordsBeanList = new ArrayList<>();
    private List<MyPayOrderAskToBuyBean.RecordsBean> askToBuyList = new ArrayList<>();
    private List<AppealListBean.RecordsBean> appealList = new ArrayList<>();
    private MySellOrderAppealListAdapter mySellOrderAppealListAdapter;
    private MySellOrderSellAdapter mySellOrderSellAdapter;
    private MySellOrderAskToBuyAdapter mySellOrderAskToBuyAdapter;

    public MySellOrderPresenter(Context context) {
        super(context);
    }

    @Override
    protected void onViewDestroy() {

    }

    public void initTab(TabLayout myPayOrderTab) {
        myPayOrderTab.addTab(myPayOrderTab.newTab().setText("卖出"));
        myPayOrderTab.addTab(myPayOrderTab.newTab().setText("出售"));
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
        Map map = MapUtil.getInstance().addParms("pageNum", page).addParms("pageSize", 10).addParms("type", 1).build();
        Observable head = RetrofitUtil.getInstance().getApi(CommonResource.BASEURL_9006).getHead(CommonResource.MYCURRENCY, map, SPUtil.getToken());
        RetrofitUtil.getInstance().toSubscribe(head, new OnMyCallBack(new OnDataListener() {
            @Override
            public void onSuccess(String result, String msg) {
                getView().finishRefresh();
                LogUtil.e("我的卖单卖出" + result);
                MyPayOrderBuyBean myPayOrderBuyBean = JSON.parseObject(result, new TypeReference<MyPayOrderBuyBean>() {
                }.getType());

                if (1 == page) {
                    recordsBeanList.clear();
                }
                recordsBeanList.addAll(myPayOrderBuyBean.getRecords());
                mySellOrderSellAdapter = new MySellOrderSellAdapter(mContext, recordsBeanList, R.layout.item_my_pay_order_rec);
                if (getView() != null) {
                    getView().loadAdapter(mySellOrderSellAdapter);
                }
                mySellOrderSellAdapter.setViewOnClickListener(new MyRecyclerAdapter.ViewOnClickListener() {
                    @Override
                    public void ViewOnClick(View view, final int index) {
                        view.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
//                                    Toast.makeText(mContext, "无法查看", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(mContext, SaleActivity.class);
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
                LogUtil.e("我的卖单卖出errorMsg" + errorMsg);
                getView().finishRefresh();
            }
        }));
    }

    public void askToBuy(final int page) {
        Map map = MapUtil.getInstance().addParms("pageNum", page).addParms("pageSize", 10).addParms("type", 1).build();
        Observable head = RetrofitUtil.getInstance().getApi(CommonResource.BASEURL_9006).getHead(CommonResource.MYBUYCURRENCYLIST, map, SPUtil.getToken());
        RetrofitUtil.getInstance().toSubscribe(head, new OnMyCallBack(new OnDataListener() {
            @Override
            public void onSuccess(String result, String msg) {
                getView().finishRefresh();

                LogUtil.e("我的卖单出售" + result);
                MyPayOrderAskToBuyBean myPayOrderAskToBuyBean = JSON.parseObject(result, new TypeReference<MyPayOrderAskToBuyBean>() {
                }.getType());
                if (1 == page) {
                    askToBuyList.clear();
                }
                askToBuyList.addAll(myPayOrderAskToBuyBean.getRecords());
                mySellOrderAskToBuyAdapter = new MySellOrderAskToBuyAdapter(mContext, askToBuyList, R.layout.item_my_pay_order_rec);
                if (getView() != null) {
                    getView().loadAdapter(mySellOrderAskToBuyAdapter);
                }

                mySellOrderAskToBuyAdapter.setViewOnClickListener(new MyRecyclerAdapter.ViewOnClickListener() {
                    @Override
                    public void ViewOnClick(View view, final int index) {
                        view.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
//                                    Toast.makeText(mContext, "无法查看", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(mContext, SellActivity.class);
                                intent.putExtra("status", askToBuyList.get(index).getStatus());
                                LogUtil.e("投诉状态---------"+askToBuyList.get(index).getStatus());
                                intent.putExtra("id", askToBuyList.get(index).getId());
                                mContext.startActivity(intent);
                            }
                        });
                    }
                });
            }

            @Override
            public void onError(String errorCode, String errorMsg) {
                LogUtil.e("我的卖单出售errorMsg" + errorMsg);
                getView().finishRefresh();

            }
        }));
    }

    public void appealList(final int page) {
        Map map = MapUtil.getInstance().addParms("pageNum", page).addParms("pageSize", 10).addParms("type", 1).build();
        Observable head = RetrofitUtil.getInstance().getApi(CommonResource.BASEURL_9006).getHead(CommonResource.APPEALLIST, map, SPUtil.getToken());
        RetrofitUtil.getInstance().toSubscribe(head, new OnMyCallBack(new OnDataListener() {
            @Override
            public void onSuccess(String result, String msg) {
                getView().finishRefresh();

                LogUtil.e("我的卖单申诉" + result);
                AppealListBean appealListBean = JSON.parseObject(result, new TypeReference<AppealListBean>() {
                }.getType());

                if (1 == page) {
                    appealList.clear();
                }
                appealList.addAll(appealListBean.getRecords());
                mySellOrderAppealListAdapter = new MySellOrderAppealListAdapter(mContext, appealList, R.layout.item_my_pay_order_rec);
                if (getView() != null) {
                    getView().loadAdapter(mySellOrderAppealListAdapter);
                }
                mySellOrderAppealListAdapter.setViewOnClickListener(new MyRecyclerAdapter.ViewOnClickListener() {
                    @Override
                    public void ViewOnClick(View view, int index) {
                        view.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
//                                    Toast.makeText(mContext, "无法查看", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(mContext, AppealActivity.class);
                                intent.putExtra("type", 1);
                                mContext.startActivity(intent);
                            }
                        });
                    }
                });
            }

            @Override
            public void onError(String errorCode, String errorMsg) {
                LogUtil.e("我的卖单申诉errorMsg" + errorMsg);
                getView().finishRefresh();

            }
        }));
    }
}
