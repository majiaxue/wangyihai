package com.xingshi.y_mine.y_welfare_center;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xingshi.common.CommonResource;
import com.xingshi.mvp.BasePresenter;
import com.xingshi.net.OnDataListener;
import com.xingshi.net.OnMyCallBack;
import com.xingshi.net.RetrofitUtil;
import com.xingshi.utils.LogUtil;
import com.xingshi.utils.SPUtil;
import com.xingshi.y_main.R;
import com.xingshi.y_mine.auditing.AuditingActivity;
import com.xingshi.y_mine.y_setting.about_me.AboutMeActivity;
import com.xingshi.y_mine.y_upgrade_merchant.YUpgradeMerchantActivity;
import com.xingshi.y_mine.y_welfare_center.i_released.IReleasedActivity;
import com.xingshi.y_mine.y_welfare_center.task_list.TaskListActivity;
import com.kongzue.tabbar.Tab;
import com.kongzue.tabbar.TabBarView;
import com.kongzue.tabbar.interfaces.OnTabChangeListener;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import okhttp3.ResponseBody;

public class YWelfareCenterPresenter extends BasePresenter<YWelfareCenterView> {

    public YWelfareCenterPresenter(Context context) {
        super(context);
    }

    @Override
    protected void onViewDestroy() {

    }

    public void initData() {
        Observable<ResponseBody> headWithout = RetrofitUtil.getInstance().getApi(CommonResource.BASEURL_9006).getHeadWithout(CommonResource.WELFARECENTRE, SPUtil.getToken());
        RetrofitUtil.getInstance().toSubscribe(headWithout, new OnMyCallBack(new OnDataListener() {
            @Override
            public void onSuccess(String result, String msg) {
                LogUtil.e("福利中心" + result);
                JSONObject jsonObject = JSON.parseObject(result);
                String count = jsonObject.getString("count");
                String profit = jsonObject.getString("profit");
                getView().loadData(count, profit);
            }

            @Override
            public void onError(String errorCode, String errorMsg) {
                LogUtil.e("福利中心errorMsg" + errorMsg);

            }
        }));
    }

    public void setTabBar(TabBarView yWelfareCenterTab) {
        List<Tab> tabs = new ArrayList<>();
        tabs.add(new Tab(mContext, "升级商家", R.drawable.flzx_shengjishangjia));
        tabs.add(new Tab(mContext, "我发布的", R.drawable.fabu));
        tabs.add(new Tab(mContext, "任务审核", R.drawable.fabu));
        tabs.add(new Tab(mContext, "我的任务", R.drawable.flzx_renwu));
        tabs.add(new Tab(mContext, "任务介绍", R.drawable.flzx_renwujiesho));
        yWelfareCenterTab.setTab(tabs);

        yWelfareCenterTab.setOnTabChangeListener(new OnTabChangeListener() {
            @Override
            public void onTabChanged(View v, int index) {
                switch (index) {
                    case 0:
                        mContext.startActivity(new Intent(mContext, YUpgradeMerchantActivity.class));
                        break;
                    case 1:
                        mContext.startActivity(new Intent(mContext, IReleasedActivity.class));
                        break;
                    case 2:
                        mContext.startActivity(new Intent(mContext, AuditingActivity.class));
                        break;
                    case 3:
                        mContext.startActivity(new Intent(mContext, TaskListActivity.class));
                        break;
                     case 4:
                         Intent intent = new Intent(mContext, AboutMeActivity.class);
                         intent.putExtra("type", 1);
                         mContext.startActivity(intent);
                         break;
                    default:
                        break;
                }

            }
        });
    }
}
