package com.xingshi.points_mingxi.cashout_record;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.xingshi.bean.PointsCashoutRecordBean;
import com.xingshi.common.CommonResource;
import com.xingshi.module_mine.R;
import com.xingshi.mvp.BasePresenter;
import com.xingshi.net.OnDataListener;
import com.xingshi.net.OnMyCallBack;
import com.xingshi.net.RetrofitUtil;
import com.xingshi.points_mingxi.adapter.CashoutRecordAdapter;
import com.xingshi.utils.LogUtil;
import com.xingshi.utils.MapUtil;
import com.xingshi.utils.SPUtil;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;

public class CashoutRecordPresenter extends BasePresenter<CashoutRecordView> {

    private List<PointsCashoutRecordBean> recordBeans;
    private CashoutRecordAdapter adapter;

    public CashoutRecordPresenter(Context context) {
        super(context);
    }

    @Override
    protected void onViewDestroy() {

    }

    public void loadData() {
        Map map = MapUtil.getInstance().addParms("type", "1").build();
        Observable observable = RetrofitUtil.getInstance().getApi(CommonResource.BASEURL_4001).getHead(CommonResource.POINTS_CASHOUT_RECORD, map, SPUtil.getToken());
        RetrofitUtil.getInstance().toSubscribe(observable, new OnMyCallBack(new OnDataListener() {
            @Override
            public void onSuccess(String result, String msg) {
                LogUtil.e("积分提现记录：" + result);
                try {
                    if (result != null) {
                        recordBeans = JSON.parseArray(result, PointsCashoutRecordBean.class);
                        adapter = new CashoutRecordAdapter(mContext, recordBeans, R.layout.rv_cashout_record);
                        if (getView() != null) {
                            getView().loadRv(adapter);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(String errorCode, String errorMsg) {
                LogUtil.e(errorCode + "-------" + errorMsg);
            }
        }));
    }
}
