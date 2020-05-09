package com.xingshi.y_mine.y_currency_balance;

import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.xingshi.mvp.BaseActivity;
import com.xingshi.y_main.R;
import com.xingshi.y_main.R2;
import com.xingshi.y_mine.y_currency_balance.adapter.YCurrencyBalanceAdapter;
import com.scwang.smartrefresh.header.MaterialHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import butterknife.BindView;

/**
 * 区块币种余额
 */
public class YCurrencyBalanceActivity extends BaseActivity<YCurrencyBalanceView, YCurrencyBalancePresenter> implements YCurrencyBalanceView {


    @BindView(R2.id.y_currency_balance_back)
    ImageView yCurrencyBalanceBack;
    @BindView(R2.id.y_currency_balance_num)
    TextView yCurrencyBalanceNum;
    @BindView(R2.id.y_currency_balance_tab)
    TabLayout yCurrencyBalanceTab;
    @BindView(R2.id.y_currency_balance_rec)
    RecyclerView yCurrencyBalanceRec;
    @BindView(R2.id.y_currency_balance_smart)
    SmartRefreshLayout yCurrencyBalanceSmart;

    private int page = 1;

    @Override
    public int getLayoutId() {
        return R.layout.activity_ycurrency_balance;
    }

    @Override
    public void initData() {
        presenter.initData();
        presenter.initTab(yCurrencyBalanceTab);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        yCurrencyBalanceRec.setLayoutManager(linearLayoutManager);
        presenter.currencyBalanceHistory(0, page, yCurrencyBalanceTab.getSelectedTabPosition());

        yCurrencyBalanceSmart.setRefreshHeader(new MaterialHeader(this));
        yCurrencyBalanceSmart.setRefreshFooter(new ClassicsFooter(this));

        yCurrencyBalanceSmart.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page = 1;
                presenter.currencyBalanceHistory(0, page, yCurrencyBalanceTab.getSelectedTabPosition());
            }
        });

        yCurrencyBalanceSmart.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                page++;
                presenter.currencyBalanceHistory(0, page, yCurrencyBalanceTab.getSelectedTabPosition());
            }
        });
    }

    @Override
    public void initClick() {
        yCurrencyBalanceBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public YCurrencyBalanceView createView() {
        return this;
    }

    @Override
    public YCurrencyBalancePresenter createPresenter() {
        return new YCurrencyBalancePresenter(this);
    }

    @Override
    public void loadData(String s) {
        yCurrencyBalanceNum.setText(s);
    }

    @Override
    public void loadAdapter(YCurrencyBalanceAdapter yCurrencyBalanceAdapter) {
        yCurrencyBalanceRec.setAdapter(yCurrencyBalanceAdapter);
    }

    @Override
    public void finishRefresh() {
        yCurrencyBalanceSmart.finishRefresh();
        yCurrencyBalanceSmart.finishLoadMore();
    }
}
