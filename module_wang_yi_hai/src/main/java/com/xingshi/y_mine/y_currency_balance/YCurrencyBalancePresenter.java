package com.xingshi.y_mine.y_currency_balance;

import android.content.Context;
import android.support.design.widget.TabLayout;

import com.alibaba.fastjson.JSON;
import com.xingshi.bean.CurrencyBalanceHistoryBean;
import com.xingshi.common.CommonResource;
import com.xingshi.mvp.BasePresenter;
import com.xingshi.net.OnDataListener;
import com.xingshi.net.OnMyCallBack;
import com.xingshi.net.RetrofitUtil;
import com.xingshi.utils.LogUtil;
import com.xingshi.utils.MapUtil;
import com.xingshi.utils.SPUtil;
import com.xingshi.y_main.R;
import com.xingshi.y_mine.y_currency_balance.adapter.YCurrencyBalanceAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.ResponseBody;

public class YCurrencyBalancePresenter extends BasePresenter<YCurrencyBalanceView> {

    private String[] titleArr = {"获取", "支出"};
    private List<CurrencyBalanceHistoryBean.RecordsBean> recordsBeanList = new ArrayList<>();
    private YCurrencyBalanceAdapter yCurrencyBalanceAdapter;

    public YCurrencyBalancePresenter(Context context) {
        super(context);
    }

    @Override
    protected void onViewDestroy() {

    }

    public void initData() {
        Observable<ResponseBody> dataWithout = RetrofitUtil.getInstance().getApi(CommonResource.BASEURL_9006).getHeadWithout(CommonResource.CURRENCYBALANCE, SPUtil.getToken());
        RetrofitUtil.getInstance().toSubscribe(dataWithout, new OnMyCallBack(new OnDataListener() {
            @Override
            public void onSuccess(String result, String msg) {
                LogUtil.e("币余额" + result);
                getView().loadData(result);
            }

            @Override
            public void onError(String errorCode, String errorMsg) {
                LogUtil.e("币余额error" + errorMsg);
            }
        }));
    }

    public void initTab(TabLayout yCurrencyBalanceTab) {
        for (String title : titleArr) {
            yCurrencyBalanceTab.addTab(yCurrencyBalanceTab.newTab().setText(title));
        }

        yCurrencyBalanceTab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                currencyBalanceHistory(0, 1, tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }


    public void currencyBalanceHistory(int type, final int page, int status) {
        Map build = MapUtil.getInstance().addParms("type", type).addParms("pageNum", page).addParms("pageSize", 10).addParms("status", status).build();
        Observable data = RetrofitUtil.getInstance().getApi(CommonResource.BASEURL_9006).getHead(CommonResource.CURRENCYBALANCEHISTORY, build, SPUtil.getToken());
        RetrofitUtil.getInstance().toSubscribe(data, new OnMyCallBack(new OnDataListener() {
            @Override
            public void onSuccess(String result, String msg) {
                LogUtil.e("贡献值记录" + result);
                getView().finishRefresh();
                CurrencyBalanceHistoryBean currencyBalanceHistoryBean = JSON.parseObject(result, CurrencyBalanceHistoryBean.class);
                if (1 == page){
                    recordsBeanList.clear();
                }
                recordsBeanList.addAll(currencyBalanceHistoryBean.getRecords());
                if (yCurrencyBalanceAdapter == null){
                    yCurrencyBalanceAdapter = new YCurrencyBalanceAdapter(mContext, recordsBeanList, R.layout.item_y_currency_balance_rec);
                    getView().loadAdapter(yCurrencyBalanceAdapter);
                }else{
                    yCurrencyBalanceAdapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onError(String errorCode, String errorMsg) {
                getView().finishRefresh();
                LogUtil.e("贡献值记录errorMsg" + errorMsg);
            }
        }));
    }
}
