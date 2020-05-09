package com.xingshi.y_mine.y_bill;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.scwang.smartrefresh.header.MaterialHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.xingshi.mvp.BaseActivity;
import com.xingshi.y_main.R;
import com.xingshi.y_main.R2;
import com.xingshi.y_mine.y_bill.adapter.YBillAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 账单流水
 */
public class YBillActivity extends BaseActivity<YBillView, YBillPresenter> implements YBillView {


    @BindView(R2.id.y_bill_back)
    ImageView yBillBack;
    @BindView(R2.id.y_bill_tab)
    TabLayout yBillTab;
    @BindView(R2.id.y_bill_rec)
    RecyclerView yBillRec;
    @BindView(R2.id.y_bill_smart)
    SmartRefreshLayout yBillSmart;

    private int page = 1;

    @Override
    public int getLayoutId() {
        return R.layout.activity_ybill;
    }

    @Override
    public void initData() {
        presenter.initTab(yBillTab);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        yBillRec.setLayoutManager(linearLayoutManager);

        presenter.initData(0, page);

        //设置 Header 为 官方主题 样式
        yBillSmart.setRefreshHeader(new MaterialHeader(this));
        //设置 Footer 为 默认 样式
        yBillSmart.setRefreshFooter(new ClassicsFooter(this));
    }

    @Override
    public void initClick() {
        yBillBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //********************设置上拉刷新下拉加载
        yBillSmart.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page = 1;
                presenter.initData(yBillTab.getSelectedTabPosition(), page);
            }
        });
        yBillSmart.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                page++;
                presenter.initData(yBillTab.getSelectedTabPosition(), page);
            }
        });
    }

    @Override
    public YBillView createView() {
        return this;
    }

    @Override
    public YBillPresenter createPresenter() {
        return new YBillPresenter(this);
    }

    @Override
    public void loadAdapter(YBillAdapter adapter) {
        yBillRec.setAdapter(adapter);
    }

    @Override
    public void loadFinish() {
        yBillSmart.finishRefresh();
        yBillSmart.finishLoadMore();
    }
}
