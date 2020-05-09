package com.xingshi.y_mine.y_commission;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xingshi.bean.PopRuleBean;
import com.xingshi.bean.YBillBean;
import com.xingshi.common.CommonResource;
import com.xingshi.mvp.BasePresenter;
import com.xingshi.net.OnDataListener;
import com.xingshi.net.OnMyCallBack;
import com.xingshi.net.RetrofitUtil;
import com.xingshi.utils.LogUtil;
import com.xingshi.utils.MapUtil;
import com.xingshi.utils.SPUtil;
import com.xingshi.y_main.R;
import com.xingshi.y_mine.y_bill.adapter.YBillAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.ResponseBody;

public class YCommissionPresenter extends BasePresenter<YCommissionView> {

    private String[] titleArr = {"提现记录", "发奖明细"};
    private List<YBillBean.RecordsBean> recordsBeanList = new ArrayList<>();
    private YBillAdapter yBillAdapter;

    public YCommissionPresenter(Context context) {
        super(context);
    }

    @Override
    protected void onViewDestroy() {

    }

    public void initTab(TabLayout yCommissionTab) {
        for (String title : titleArr) {
            yCommissionTab.addTab(yCommissionTab.newTab().setText(title));
        }

        yCommissionTab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (0 == tab.getPosition()){
                    initRec(1, 1);
                }else{
                    initRec(0, 1);
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

    public void initRec(int type, final int page) {
        Map map = MapUtil.getInstance().addParms("type", type).addParms("current", page).build();
        Observable observable = RetrofitUtil.getInstance().getApi(CommonResource.BASEURL_4001).getHead(CommonResource.IN_OUT, map, SPUtil.getToken());
        RetrofitUtil.getInstance().toSubscribe(observable, new OnMyCallBack(new OnDataListener() {
            @Override
            public void onSuccess(String result, String msg) {
                getView().loadFinish();
                LogUtil.e("收入：" + result);
                YBillBean yBillBean = JSON.parseObject(result, YBillBean.class);
                if (1 == page) {
                    recordsBeanList.clear();
                }
                recordsBeanList.addAll(yBillBean.getRecords());
                if (yBillAdapter == null) {
                    yBillAdapter = new YBillAdapter(mContext, recordsBeanList, R.layout.item_y_contribution_value_rec);
                    getView().loadAdapter(yBillAdapter);
                } else {
                    yBillAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onError(String errorCode, String errorMsg) {
                LogUtil.e(errorCode + "----shouru----" + errorMsg);
                getView().loadFinish();
            }
        }));
    }

    public void initData() {
        Observable<ResponseBody> headWithout = RetrofitUtil.getInstance().getApi(CommonResource.BASEURL_9006).getHeadWithout(CommonResource.USERBLANCE, SPUtil.getToken());
        RetrofitUtil.getInstance().toSubscribe(headWithout, new OnMyCallBack(new OnDataListener() {
            @Override
            public void onSuccess(String result, String msg) {
                LogUtil.e("奖金余额" + result);
                JSONObject jsonObject = JSON.parseObject(result);
                String total = jsonObject.getString("total");
                String blance = jsonObject.getString("blance");
                String backMoney = jsonObject.getString("backMoney");
                getView().loadData(total, blance, backMoney);
            }

            @Override
            public void onError(String errorCode, String errorMsg) {
                LogUtil.e("奖金余额" + errorMsg);
            }
        }));
    }

    public void popRule() {
        Observable<ResponseBody> dataWithout = RetrofitUtil.getInstance().getApi(CommonResource.BASEURL_9006).getDataWithout(CommonResource.CASHXITHDRAWALPARAM);
        RetrofitUtil.getInstance().toSubscribe(dataWithout, new OnMyCallBack(new OnDataListener() {
            @Override
            public void onSuccess(String result, String msg) {
                LogUtil.e("提现规则" + result);
                List<PopRuleBean> popRuleBeanList = JSON.parseArray(result, PopRuleBean.class);
                getView().popRule(popRuleBeanList);
            }

            @Override
            public void onError(String errorCode, String errorMsg) {
                LogUtil.e("提现规则errorMsg" + errorMsg);
            }
        }));
    }

    public void backMoney(String money) {
        Map amount = MapUtil.getInstance().addParms("amount", money).addParms("name","").addParms("account","").build();
        Observable head = RetrofitUtil.getInstance().getApi(CommonResource.BASEURL_4001).getHead(CommonResource.BACKMONEY, amount, SPUtil.getToken());
        RetrofitUtil.getInstance().toSubscribe(head, new OnMyCallBack(new OnDataListener() {
            @Override
            public void onSuccess(String result, String msg) {
                LogUtil.e("余额提现" + result);
                Toast.makeText(mContext, "提现成功", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(String errorCode, String errorMsg) {
                LogUtil.e("余额提现errorMsg" + errorMsg);
                Toast.makeText(mContext, errorMsg, Toast.LENGTH_SHORT).show();
            }
        }));
    }

    public void alipay(String money) {
        Map amount = MapUtil.getInstance().addParms("amount", money).addParms("name","").addParms("account","").build();
        Observable head = RetrofitUtil.getInstance().getApi(CommonResource.BASEURL_4001).getHead(CommonResource.ALIPAY, amount, SPUtil.getToken());
        RetrofitUtil.getInstance().toSubscribe(head, new OnMyCallBack(new OnDataListener() {
            @Override
            public void onSuccess(String result, String msg) {
                LogUtil.e("佣金提现" + result);
                Toast.makeText(mContext, "提现成功", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(String errorCode, String errorMsg) {
                LogUtil.e("佣金提现errorMsg" + errorMsg);
                Toast.makeText(mContext, errorMsg, Toast.LENGTH_SHORT).show();
            }
        }));
    }
}
