package com.xingshi.y_main;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.xingshi.mvp.BasePresenter;
import com.xingshi.y_deal.YDealFragment;
import com.xingshi.y_home.YHomeFragment;
import com.xingshi.y_mine.YMineFragment;
import com.xingshi.y_task.YTaskFragment;

import org.greenrobot.eventbus.EventBus;

public class YMainPresenter extends BasePresenter<YMainView> {
    private FragmentManager fragmentManager;
    private YHomeFragment yHomeFragment;
    private YTaskFragment yTaskFragment;
    private YDealFragment yDealFragment;
    private YMineFragment yMineFragment;

    public YMainPresenter(Context context) {
        super(context);
    }

    @Override
    protected void onViewDestroy() {
        EventBus.getDefault().unregister(this);
    }

    public void loadData(FragmentManager fragmentManager, int resId) {
        this.fragmentManager = fragmentManager;
        yHomeFragment = new YHomeFragment();
        yTaskFragment = new YTaskFragment();
        yDealFragment = new YDealFragment();
        yMineFragment = new YMineFragment();

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(resId, yHomeFragment)
                .add(resId, yTaskFragment)
                .add(resId, yDealFragment)
                .add(resId, yMineFragment);

        transaction.show(yHomeFragment)
                .hide(yTaskFragment)
                .hide(yDealFragment)
                .hide(yMineFragment)
                .commit();
    }

    public void click(int index) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        switch (index) {
            case 0:
                transaction.show(yHomeFragment)
                        .hide(yTaskFragment)
                        .hide(yDealFragment)
                        .hide(yMineFragment)
                        .commit();
                break;
            case 1:
                transaction.show(yTaskFragment)
                        .hide(yHomeFragment)
                        .hide(yDealFragment)
                        .hide(yMineFragment)
                        .commit();
                break;
            case 2:
                transaction.show(yDealFragment)
                        .hide(yTaskFragment)
                        .hide(yHomeFragment)
                        .hide(yMineFragment)
                        .commit();
                break;
            case 3:
                transaction.show(yMineFragment)
                        .hide(yTaskFragment)
                        .hide(yDealFragment)
                        .hide(yHomeFragment)
                        .commit();
                break;
            default:
                break;
        }
    }
}
