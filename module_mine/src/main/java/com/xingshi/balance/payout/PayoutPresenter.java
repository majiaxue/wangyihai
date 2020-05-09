package com.xingshi.balance.payout;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.xingshi.balance.adapter.IncomeAdapter;
import com.xingshi.bean.InAndOutBean;
import com.xingshi.common.CommonResource;
import com.xingshi.module_mine.R;
import com.xingshi.mvp.BasePresenter;
import com.xingshi.net.OnDataListener;
import com.xingshi.net.OnMyCallBack;
import com.xingshi.net.RetrofitUtil;
import com.xingshi.utils.LogUtil;
import com.xingshi.utils.MapUtil;
import com.xingshi.utils.SPUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;

public class PayoutPresenter extends BasePresenter<PayoutView> {
    private List<InAndOutBean.RecordsBean> dataList = new ArrayList<>();
    private IncomeAdapter adapter;

    public PayoutPresenter(Context context) {
        super(context);
    }

    @Override
    protected void onViewDestroy() {

    }

    public void loadData(final int page) {
        Map map = MapUtil.getInstance().addParms("type", "1").addParms("current", page).build();
        Observable observable = RetrofitUtil.getInstance().getApi(CommonResource.BASEURL_4001).getHead(CommonResource.IN_OUT, map, SPUtil.getToken());
        RetrofitUtil.getInstance().toSubscribe(observable, new OnMyCallBack(new OnDataListener() {
            @Override
            public void onSuccess(String result, String msg) {
                LogUtil.e("支出：" + result);
                InAndOutBean inAndOutBean = JSON.parseObject(result, InAndOutBean.class);
                if (page == 1) {
                    dataList.clear();
                }
                dataList.addAll(inAndOutBean.getRecords());
                if (adapter == null) {
                    adapter = new IncomeAdapter(mContext, dataList, R.layout.rv_income);
                    if (getView() != null) {
                        getView().loadRv(adapter);
                    }
                } else {
                    adapter.notifyDataSetChanged();
                }
                if (getView() != null) {
                    getView().loadFinish();
                }
            }

            @Override
            public void onError(String errorCode, String errorMsg) {
                if (getView() != null) {
                    getView().loadFinish();
                }
            }
        }));
    }
}
