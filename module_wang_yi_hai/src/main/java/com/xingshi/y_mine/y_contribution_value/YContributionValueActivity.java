package com.xingshi.y_mine.y_contribution_value;

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
import com.xingshi.y_mine.y_contribution_value.adapter.YContributionValueAdapter;
import com.scwang.smartrefresh.header.MaterialHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import butterknife.BindView;

/**
 * 区块贡献值
 */
public class YContributionValueActivity extends BaseActivity<YContributionValueView, YContributionValuePresenter> implements YContributionValueView {


    @BindView(R2.id.y_contribution_value_image)
    ImageView yContributionValueImage;
    @BindView(R2.id.y_contribution_value_back)
    ImageView yContributionValueBack;
    @BindView(R2.id.y_contribution_value_num)
    TextView yContributionValueNum;
    @BindView(R2.id.y_contribution_value_tab)
    TabLayout yContributionValueTab;
    @BindView(R2.id.y_contribution_value_rec)
    RecyclerView yContributionValueRec;
    @BindView(R2.id.y_contribution_value_smart)
    SmartRefreshLayout yContributionValueSmart;

    private int page = 1;

    @Override
    public int getLayoutId() {
        return R.layout.activity_ycontribution_value;
    }

    @Override
    public void initData() {
        presenter.initData();

        presenter.initTab(yContributionValueTab);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        yContributionValueRec.setLayoutManager(linearLayoutManager);

        presenter.currencyBalanceHistory(1, page, 10, yContributionValueTab.getSelectedTabPosition());

        yContributionValueSmart.setRefreshHeader(new MaterialHeader(this));
        yContributionValueSmart.setRefreshFooter(new ClassicsFooter(this));

        yContributionValueSmart.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page = 1;
                presenter.currencyBalanceHistory(1, page, 10, yContributionValueTab.getSelectedTabPosition());
            }
        });

        yContributionValueSmart.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                page++;
                presenter.currencyBalanceHistory(1, page, 10, yContributionValueTab.getSelectedTabPosition());
            }
        });
    }

    @Override
    public void initClick() {
        yContributionValueBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public YContributionValueView createView() {
        return this;
    }

    @Override
    public YContributionValuePresenter createPresenter() {
        return new YContributionValuePresenter(this);
    }

    @Override
    public void loadData(String s) {
        yContributionValueNum.setText(s);
    }

    @Override
    public void loadAdapter(YContributionValueAdapter yContributionValueAdapter) {
        yContributionValueRec.setAdapter(yContributionValueAdapter);
    }

    @Override
    public void finishRefresh() {
        yContributionValueSmart.finishRefresh();
        yContributionValueSmart.finishLoadMore();
    }
}
