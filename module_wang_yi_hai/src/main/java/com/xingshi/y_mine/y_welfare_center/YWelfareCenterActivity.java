package com.xingshi.y_mine.y_welfare_center;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.xingshi.mvp.BaseActivity;
import com.xingshi.utils.ClickUtil;
import com.xingshi.utils.YPopUtil;
import com.xingshi.y_main.R;
import com.xingshi.y_main.R2;
import com.xingshi.y_mine.y_welfare_center.commission_task.CommissionTaskActivity;
import com.xingshi.y_mine.y_welfare_center.pay_a_tip.PayATipActivity;
import com.kongzue.tabbar.TabBarView;

import butterknife.BindView;

/**
 * 区块福利中心
 */
@Route(path = "/module_wang_yi_hai/task_list/YWelfareCenterActivity")
public class YWelfareCenterActivity extends BaseActivity<YWelfareCenterView, YWelfareCenterPresenter> implements YWelfareCenterView {

    @BindView(R2.id.y_welfare_center_back)
    ImageView yWelfareCenterBack;
    @BindView(R2.id.y_welfare_center_singularization)
    TextView yWelfareCenterSingularization;
    @BindView(R2.id.y_welfare_center_task_earnings)
    TextView yWelfareCenterTaskEarnings;
    @BindView(R2.id.y_welfare_center_tab)
    TabBarView yWelfareCenterTab;
    @BindView(R2.id.y_welfare_center_commission_task)
    ImageView yWelfareCenterCommissionTask;
    @BindView(R2.id.y_welfare_center_pay_a_tip)
    ImageView yWelfareCenterPayATip;
    @BindView(R2.id.y_welfare_center_release_task)
    TextView yWelfareCenterReleaseTask;

    @Override
    public int getLayoutId() {
        return R.layout.activity_ywelfare_center;
    }

    @Override
    public void initData() {

        presenter.initData();

        presenter.setTabBar(yWelfareCenterTab);
    }

    @Override
    public void initClick() {
        yWelfareCenterBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //佣金任务
        yWelfareCenterCommissionTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(YWelfareCenterActivity.this, CommissionTaskActivity.class));
            }
        });
        //付费线报
        yWelfareCenterPayATip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(YWelfareCenterActivity.this, PayATipActivity.class));
            }
        });


        yWelfareCenterReleaseTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ClickUtil.isFastClick()) {
                    YPopUtil.fulitankuang(YWelfareCenterActivity.this);
                }
            }
        });

    }


    @Override
    public YWelfareCenterView createView() {
        return this;
    }

    @Override
    public YWelfareCenterPresenter createPresenter() {
        return new YWelfareCenterPresenter(this);
    }

    @Override
    public void loadData(String count, String profit) {
        yWelfareCenterSingularization.setText(count);
        yWelfareCenterTaskEarnings.setText("￥ " + profit);
    }
}
