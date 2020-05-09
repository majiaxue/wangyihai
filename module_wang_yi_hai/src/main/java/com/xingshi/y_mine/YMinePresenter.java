package com.xingshi.y_mine;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.xingshi.bean.YMineBean;
import com.xingshi.common.CommonResource;
import com.xingshi.entity.EventBusBean;
import com.xingshi.mvp.BasePresenter;
import com.xingshi.net.OnDataListener;
import com.xingshi.net.OnMyCallBack;
import com.xingshi.net.RetrofitUtil;
import com.xingshi.utils.LogUtil;
import com.xingshi.utils.SPUtil;
import com.xingshi.y_main.R;
import com.xingshi.y_mine.promotion_code.PromotionCodeActivity;
import com.xingshi.y_mine.y_balance_account.YBalanceAccountActivity;
import com.xingshi.y_mine.y_bill.YBillActivity;
import com.xingshi.y_mine.y_commission.YCommissionActivity;
import com.xingshi.y_mine.y_liveness.YLivenessActivity;
import com.xingshi.y_mine.y_my_team.YMyTeamActivity;
import com.xingshi.y_mine.y_upgrade_merchant.YUpgradeMerchantActivity;
import com.xingshi.y_mine.y_welfare_center.YWelfareCenterActivity;
import com.xingshi.y_mine.y_my_task.YMyTaskActivity;
import com.xingshi.y_mine.y_shipping_address.YShippingAddressActivity;
import com.kongzue.tabbar.Tab;
import com.kongzue.tabbar.TabBarView;
import com.kongzue.tabbar.interfaces.OnTabChangeListener;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import okhttp3.ResponseBody;

public class YMinePresenter extends BasePresenter<YMineView> {
    public YMinePresenter(Context context) {
        super(context);
    }

    @Override
    protected void onViewDestroy() {

    }

    public void initData() {
        Observable<ResponseBody> headWithout = RetrofitUtil.getInstance().getApi(CommonResource.BASEURL_9006).getHeadWithout(CommonResource.MYVIEW, SPUtil.getToken());
        RetrofitUtil.getInstance().toSubscribe(headWithout, new OnMyCallBack(new OnDataListener() {
            @Override
            public void onSuccess(String result, String msg) {
                LogUtil.e("我的页面" + result);
                if (result != null) {
                    YMineBean yMineBean = JSON.parseObject(result, new TypeReference<YMineBean>() {
                    }.getType());
                    getView().initData(yMineBean);
                }
            }

            @Override
            public void onError(String errorCode, String errorMsg) {
                LogUtil.e("我的页面errorMsg" + errorMsg);

            }
        }));
    }

    public void setMyApplication(TabBarView yMineMyApplication) {
        List<Tab> tabs2 = new ArrayList<>();
        tabs2.add(new Tab(mContext, "充值", R.drawable.chongzhi));
        tabs2.add(new Tab(mContext, "提现", R.drawable.tixian));
        tabs2.add(new Tab(mContext, "兑换", R.drawable.duihuan));
        tabs2.add(new Tab(mContext, "推广码", R.drawable.tuiguangma));
//        tabs2.add(new Tab(mContext, "实名认证", R.drawable.renzheng));
        yMineMyApplication.setTab(tabs2);

        yMineMyApplication.setOnTabChangeListener(new OnTabChangeListener() {
            @Override
            public void onTabChanged(View v, int index) {
                switch (index) {
                    case 0:
                        mContext.startActivity(new Intent(mContext, YBalanceAccountActivity.class));
                        break;
                    case 1:
                        mContext.startActivity(new Intent(mContext, YCommissionActivity.class));
                        break;
                    case 2:
                        EventBus.getDefault().post(new EventBusBean(CommonResource.TASK));
                        break;
                    case 3:
                        mContext.startActivity(new Intent(mContext, PromotionCodeActivity.class));
                        break;
//                    case 4:
//                        mContext.startActivity(new Intent(mContext, CertificationActivity.class));
//                        break;
                    default:
                        break;

                }

            }
        });
    }

    public void setServiceAid(TabBarView yMineServiceAid1, TabBarView yMineServiceAid2) {
        List<Tab> tabs1 = new ArrayList<>();
        tabs1.add(new Tab(mContext, "我的任务", R.drawable.renwu));
        tabs1.add(new Tab(mContext, "升级商家", R.drawable.shangjia));
        tabs1.add(new Tab(mContext, "福利中心", R.drawable.fuli));
        tabs1.add(new Tab(mContext, "账单流水", R.drawable.zhangdan));
        yMineServiceAid1.setTab(tabs1);

        yMineServiceAid1.setOnTabChangeListener(new OnTabChangeListener() {
            @Override
            public void onTabChanged(View v, int index) {
                switch (index) {
                    case 0:
                        mContext.startActivity(new Intent(mContext, YMyTaskActivity.class));
                        break;
                    case 1:
                        mContext.startActivity(new Intent(mContext, YUpgradeMerchantActivity.class));
                        break;
                    case 2:
                        mContext.startActivity(new Intent(mContext, YWelfareCenterActivity.class));
                        break;
                    case 3:
                        mContext.startActivity(new Intent(mContext, YBillActivity.class));
                        break;
                    default:
                        break;

                }
            }
        });

        List<Tab> tabs2 = new ArrayList<>();
        tabs2.add(new Tab(mContext, "收货地址", R.drawable.shohuodizhi));
        tabs2.add(new Tab(mContext, "我的团队", R.drawable.tuandui));
        tabs2.add(new Tab(mContext, "活跃度", R.drawable.huoydu));
        tabs2.add(new Tab(mContext, "", 0));
        yMineServiceAid2.setTab(tabs2);

        yMineServiceAid2.setOnTabChangeListener(new OnTabChangeListener() {
            @Override
            public void onTabChanged(View v, int index) {
                switch (index) {
                    case 0:
                        mContext.startActivity(new Intent(mContext, YShippingAddressActivity.class));
                        break;
                    case 1:
                        mContext.startActivity(new Intent(mContext, YMyTeamActivity.class));
                        break;
                    case 2:
                        mContext.startActivity(new Intent(mContext, YLivenessActivity.class));
                        break;
                    default:
                        break;

                }
            }
        });
    }


}
