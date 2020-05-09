package com.xingshi.balance;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.alibaba.fastjson.JSON;
import com.xingshi.balance.income.IncomeFragment;
import com.xingshi.balance.payout.PayoutFragment;
import com.xingshi.bean.BalanceBean;
import com.xingshi.cashout.CashoutActivity;
import com.xingshi.common.CommonResource;
import com.xingshi.mvp.BasePresenter;
import com.xingshi.net.OnDataListener;
import com.xingshi.net.OnMyCallBack;
import com.xingshi.net.RetrofitUtil;
import com.xingshi.utils.LogUtil;
import com.xingshi.utils.SPUtil;

import io.reactivex.Observable;
import okhttp3.ResponseBody;

public class BalancePresenter extends BasePresenter<BalanceView> {

    private FragmentManager fragmentManager;
    private IncomeFragment incomeFragment;
    private PayoutFragment payoutFragment;

    public BalancePresenter(Context context) {
        super(context);
    }

    @Override
    protected void onViewDestroy() {

    }

    public void loadData() {
        Observable<ResponseBody> observable = RetrofitUtil.getInstance().getApi(CommonResource.BASEURL_4001).getHeadWithout(CommonResource.GETBALANCE, SPUtil.getToken());
        RetrofitUtil.getInstance().toSubscribe(observable, new OnMyCallBack(new OnDataListener() {
            @Override
            public void onSuccess(String result, String msg) {
                LogUtil.e("余额：" + result);
                BalanceBean balanceBean = JSON.parseObject(result, BalanceBean.class);
                if (getView() != null) {
                    getView().loadBalance(balanceBean);
                }
            }

            @Override
            public void onError(String errorCode, String errorMsg) {

            }
        }));
    }

    public void jumpToCashout() {
        mContext.startActivity(new Intent(mContext, CashoutActivity.class));
    }

    public void initFragment(FragmentManager fragmentManager, int resId) {
        this.fragmentManager = fragmentManager;
        incomeFragment = new IncomeFragment();
        payoutFragment = new PayoutFragment();
        FragmentTransaction transaction = this.fragmentManager.beginTransaction();
        transaction.add(resId, incomeFragment)
                .add(resId, payoutFragment)
                .show(incomeFragment)
                .hide(payoutFragment)
                .commit();
    }

    public void changeView(int i) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        if (i == 1) {
            transaction.show(incomeFragment)
                    .hide(payoutFragment)
                    .commit();
        } else {
            transaction.show(payoutFragment)
                    .hide(incomeFragment)
                    .commit();
        }
    }
}
