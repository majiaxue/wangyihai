package com.xingshi.y_mine.y_contribution_value;

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
import com.xingshi.y_mine.y_contribution_value.adapter.YContributionValueAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.ResponseBody;

public class YContributionValuePresenter extends BasePresenter<YContributionValueView> {

    private String[] titleArr = {"获取", "支出"};
    private List<CurrencyBalanceHistoryBean.RecordsBean> recordsBeanList = new ArrayList<>();
    private YContributionValueAdapter yContributionValueAdapter;

    public YContributionValuePresenter(Context context) {
        super(context);
    }

    @Override
    protected void onViewDestroy() {

    }

    public void initData() {
        Observable<ResponseBody> dataWithout = RetrofitUtil.getInstance().getApi(CommonResource.BASEURL_9006).getHeadWithout(CommonResource.CONTRIBUTION, SPUtil.getToken());
        RetrofitUtil.getInstance().toSubscribe(dataWithout, new OnMyCallBack(new OnDataListener() {
            @Override
            public void onSuccess(String result, String msg) {
                LogUtil.e("贡献值" + result);
                getView().loadData(result);
            }

            @Override
            public void onError(String errorCode, String errorMsg) {
                LogUtil.e("贡献值" + errorMsg);
            }
        }));
    }

    public void initTab(TabLayout yContributionValueTab) {
        for (String title : titleArr) {
            yContributionValueTab.addTab(yContributionValueTab.newTab().setText(title));
        }

        yContributionValueTab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                currencyBalanceHistory(1, 1, 1000, tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    public void currencyBalanceHistory(int type, final int page, int pageSize, int status) {
        Map build = MapUtil.getInstance().addParms("type", type).addParms("pageNum", page).addParms("pageSize", pageSize).addParms("status", status).build();
        Observable data = RetrofitUtil.getInstance().getApi(CommonResource.BASEURL_9006).getHead(CommonResource.CURRENCYBALANCEHISTORY, build, SPUtil.getToken());
        RetrofitUtil.getInstance().toSubscribe(data, new OnMyCallBack(new OnDataListener() {
            @Override
            public void onSuccess(String result, String msg) {
                LogUtil.e("贡献值记录" + result);
                CurrencyBalanceHistoryBean currencyBalanceHistoryBean = JSON.parseObject(result, CurrencyBalanceHistoryBean.class);
                if (1 == page) {
                    recordsBeanList.clear();
                }
                recordsBeanList.addAll(currencyBalanceHistoryBean.getRecords());

                if (yContributionValueAdapter == null) {
                    yContributionValueAdapter = new YContributionValueAdapter(mContext, recordsBeanList, R.layout.item_y_contribution_value_rec);
                    getView().loadAdapter(yContributionValueAdapter);
                } else {
                    yContributionValueAdapter.notifyDataSetChanged();
                }

                getView().finishRefresh();
            }

            @Override
            public void onError(String errorCode, String errorMsg) {
                getView().finishRefresh();
                LogUtil.e("贡献值记录errorMsg" + errorMsg);
            }
        }));
    }
}
