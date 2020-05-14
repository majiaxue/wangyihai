package com.xingshi.y_mine.auditing;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.xingshi.mvp.BaseActivity;
import com.xingshi.view.CustomHeader;
import com.xingshi.y_main.R;
import com.xingshi.y_main.R2;
import com.xingshi.y_mine.auditing.adapter.AudutingAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

//审核界面
public class AuditingActivity extends BaseActivity<AuditingView, AuditingPresenter> implements AuditingView {
    @BindView(R2.id.tab)
    TabLayout tab;
    @BindView(R2.id.audit_rec)
    RecyclerView auditRec;
    @BindView(R2.id.smart)
    SmartRefreshLayout smart;
    @BindView(R2.id.commission_task_details_back)
    ImageView includeBack;
    int page=1;
    int i;

    @Override
    public int getLayoutId() {
        return R.layout.auditing_activity;
    }

    @Override
    public void initData() {
        presenter.loadData(0,page);
        presenter.getTab(tab);
        LinearLayoutManager manager=new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        auditRec.setLayoutManager(manager);
        //下拉刷新样式
        CustomHeader customHeader = new CustomHeader(this);
        customHeader.setPrimaryColors(getResources().getColor(R.color.colorTransparency));
        smart.setRefreshHeader(customHeader);
    }

    @Override
    public void initClick() {
        includeBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        smart.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page=1;
                presenter.loadData(i,page);
            }
        });
        smart.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                page++;
                presenter.loadData(i,page);
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

    @Override
    public void loadAdapter(AudutingAdapter adapter) {
        auditRec.setAdapter(adapter);
    }

    @Override
    public void refresh() {
        smart.finishRefresh();
        smart.finishLoadMore();
    }

    @Override
    public void getStatus(int i) {
        this.i=i;
    }
}
