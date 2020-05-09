package com.xingshi.y_deal;

import android.support.design.widget.TabLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.xingshi.mvp.BaseFragment;
import com.xingshi.y_deal.adapter.YDealAdapter;
import com.xingshi.y_main.R;
import com.xingshi.y_main.R2;
import com.github.mikephil.charting.charts.LineChart;
import com.kongzue.tabbar.TabBarView;

import butterknife.BindView;

/**
 * 区块交易
 */
public class YDealFragment extends BaseFragment<YDealView, YDealPresenter> implements YDealView {

    @BindView(R2.id.y_deal_platform)
    TextView yDealPlatform;
    @BindView(R2.id.y_deal_total_fee)
    TextView yDealTotalFee;
    @BindView(R2.id.y_deal_palette)
    TabBarView yDealPalette;
    @BindView(R2.id.y_deal_tab)
    TabLayout yDealTab;
    @BindView(R2.id.y_deal_rec)
    RecyclerView yDealRec;
    @BindView(R2.id.y_deal_line_chart)
    LineChart yDealLineChart;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_ydeal;
    }

    @Override
    public void initData() {
        presenter.setPalette(yDealPalette);

        presenter.initTab(yDealTab);

        presenter.initTop();

        //数据
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        yDealRec.setLayoutManager(linearLayoutManager);
        presenter.setDealRec();
    }

    @Override
    public void initClick() {

    }

    @Override
    public YDealView createView() {
        return this;
    }

    @Override
    public YDealPresenter createPresenter() {
        return new YDealPresenter(getContext());
    }

    @Override
    public void loadAdapter(YDealAdapter yDealAdapter) {
        yDealRec.setAdapter(yDealAdapter);
    }

    @Override
    public void loadData(double totalPrice, double totalServiceCharge) {
        yDealPlatform.setText("￥" + totalPrice);
        yDealTotalFee.setText("￥" + totalServiceCharge);
        //图表
        presenter.initTuBiao(yDealLineChart);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden) {
            //不可见
        } else {
            presenter.initTop();

            presenter.setDealRec();
        }
    }

}
