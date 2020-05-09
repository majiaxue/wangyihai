package com.xingshi.y_main;

import android.view.View;
import android.widget.FrameLayout;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.xingshi.common.CommonResource;
import com.xingshi.entity.EventBusBean;
import com.xingshi.mvp.BaseFragmentActivity;
import com.xingshi.utils.StatusBarUtils;
import com.kongzue.tabbar.Tab;
import com.kongzue.tabbar.TabBarView;
import com.kongzue.tabbar.interfaces.OnTabChangeListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 区块
 */
@Route(path = "/module_wang_yi_hai/YMainActivity")
public class YMainActivity extends BaseFragmentActivity<YMainView, YMainPresenter> implements YMainView {


    @BindView(R2.id.y_main_frame)
    FrameLayout yMainFrame;
    @BindView(R2.id.y_main_tab_bar)
    TabBarView yMainTabBar;

    private List<Tab> tabs = new ArrayList<>();

    @Override
    public int getLayoutId() {
        return R.layout.activity_ymain;
    }

    @Override
    public void initData() {
        StatusBarUtils.setAndroidNativeLightStatusBar(this, false);
        EventBus.getDefault().register(this);

        //使用 setFocusIcon(bitmap/drawable/resId) 来添加选中时的第二套图标
        tabs.add(new Tab(this, "首页", R.drawable.shoy_wei).setFocusIcon(this, R.drawable.shoy_xuan));
        tabs.add(new Tab(this, "任务", R.drawable.renwu_wei).setFocusIcon(this, R.drawable.renwu_xuan));
        tabs.add(new Tab(this, "交易", R.drawable.jiaoyi_wei).setFocusIcon(this, R.drawable.jiaoyi_xuan));
        tabs.add(new Tab(this, "我的", R.drawable.my_wei).setFocusIcon(this, R.drawable.my_xuan));
        yMainTabBar.setTab(tabs).setNormalFocusIndex(0);

        presenter.loadData(getSupportFragmentManager(), R.id.y_main_frame);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(EventBusBean eventBusBean) {
        if (CommonResource.TASK.equals(eventBusBean.getMsg())) {
            presenter.click(1);
            yMainTabBar.setNormalFocusIndex(1);
        } else if (CommonResource.HOME.equals(eventBusBean.getMsg())) {
            yMainTabBar.setNormalFocusIndex(0);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(500);
                        presenter.click(0);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }

    @Override
    public void initClick() {
        yMainTabBar.setOnTabChangeListener(new OnTabChangeListener() {
            @Override
            public void onTabChanged(View v, int index) {
                presenter.click(index);
            }
        });
    }

    @Override
    public YMainView createView() {
        return this;
    }

    @Override
    public YMainPresenter createPresenter() {
        return new YMainPresenter(this);
    }

}
