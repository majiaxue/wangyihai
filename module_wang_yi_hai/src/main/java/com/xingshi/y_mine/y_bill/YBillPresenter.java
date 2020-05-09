package com.xingshi.y_mine.y_bill;

import android.content.Context;
import android.support.design.widget.TabLayout;

import com.alibaba.fastjson.JSON;
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

public class YBillPresenter extends BasePresenter<YBillView> {

    private String[] titleArr = {"收入", "支出"};
    private List<YBillBean.RecordsBean> recordsBeanList = new ArrayList<>();
    private YBillAdapter yBillAdapter;

    public YBillPresenter(Context context) {
        super(context);
    }

    @Override
    protected void onViewDestroy() {

    }

    public void initTab(TabLayout yBillTab) {
        for (String title : titleArr) {
            yBillTab.addTab(yBillTab.newTab().setText(title));
        }

        yBillTab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                initData(tab.getPosition(), 1);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    public void initData(int type, final int page) {
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
}
