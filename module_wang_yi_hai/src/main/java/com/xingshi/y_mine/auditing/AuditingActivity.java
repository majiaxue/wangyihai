package com.xingshi.y_mine.auditing;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.xingshi.mvp.BaseActivity;
import com.xingshi.y_main.R;
import com.xingshi.y_main.R2;

import butterknife.BindView;
import butterknife.ButterKnife;

//审核界面
public class AuditingActivity extends BaseActivity<AuditingView, AuditingPresenter> implements AuditingView {

    @BindView(R2.id.include_back)
    ImageView includeBack;
    @BindView(R2.id.include_title)
    TextView includeTitle;
    @BindView(R2.id.include_right)
    ImageView includeRight;
    @BindView(R2.id.include_right_btn)
    TextView includeRightBtn;
    @BindView(R2.id.tab)
    TabLayout tab;
    @BindView(R2.id.audit_rec)
    RecyclerView auditRec;
    @BindView(R2.id.smart)
    SmartRefreshLayout smart;

    @Override
    public int getLayoutId() {
        return R.layout.auditing_activity;
    }

    @Override
    public void initData() {
        includeTitle.setText("任务审核");
        presenter.loadData("0");
        presenter.getDetialData();
        presenter.getTab(tab);
    }

    @Override
    public void initClick() {
        includeBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    public AuditingView createView() {
        return this;
    }

    @Override
    public AuditingPresenter createPresenter() {
        return new AuditingPresenter(this);
    }
}
