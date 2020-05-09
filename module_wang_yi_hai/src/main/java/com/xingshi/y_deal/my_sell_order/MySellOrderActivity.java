package com.xingshi.y_deal.my_sell_order;

import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.xingshi.mvp.BaseActivity;
import com.xingshi.y_deal.my_sell_order.adapter.MySellOrderAppealListAdapter;
import com.xingshi.y_deal.my_sell_order.adapter.MySellOrderAskToBuyAdapter;
import com.xingshi.y_deal.my_sell_order.adapter.MySellOrderSellAdapter;
import com.xingshi.y_main.R;
import com.xingshi.y_main.R2;
import com.scwang.smartrefresh.header.MaterialHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import butterknife.BindView;

/**
 * 我的卖单
 */
public class MySellOrderActivity extends BaseActivity<MySellOrderView, MySellOrderPresenter> implements MySellOrderView {

    @BindView(R2.id.my_pay_order_back)
    ImageView myPayOrderBack;
    @BindView(R2.id.my_pay_order_title)
    TextView myPayOrderTitle;
    @BindView(R2.id.my_pay_order_tab)
    TabLayout myPayOrderTab;
    @BindView(R2.id.my_pay_order_rec)
    RecyclerView myPayOrderRec;
    @BindView(R2.id.my_pay_order_smart)
    SmartRefreshLayout myPayOrderSmart;

    private int page = 1;

    @Override
    public int getLayoutId() {
        return R.layout.activity_my_pay_order;
    }

    @Override
    public void initData() {
        myPayOrderTitle.setText("我的卖单");
        presenter.initTab(myPayOrderTab);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        myPayOrderRec.setLayoutManager(linearLayoutManager);
        presenter.initData(page);

        myPayOrderSmart.setRefreshHeader(new MaterialHeader(this));
        myPayOrderSmart.setRefreshFooter(new ClassicsFooter(this));

        myPayOrderSmart.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page = 1;
                switch (myPayOrderTab.getSelectedTabPosition()) {
                    case 0:
                        presenter.initData(page);
                        break;
                    case 1:
                        presenter.askToBuy(page);
                        break;
                    case 2:
                        presenter.appealList(page);
                        break;
                    default:
                        break;
                }
            }
        });

        myPayOrderSmart.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                page++;
                switch (myPayOrderTab.getSelectedTabPosition()) {
                    case 0:
                        presenter.initData(page);
                        break;
                    case 1:
                        presenter.askToBuy(page);
                        break;
                    case 2:
                        presenter.appealList(page);
                        break;
                    default:
                        break;
                }
            }
        });

    }

    @Override
    public void initClick() {
        myPayOrderBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public MySellOrderView createView() {
        return this;
    }

    @Override
    public MySellOrderPresenter createPresenter() {
        return new MySellOrderPresenter(this);
    }

    @Override
    public void loadAdapter(MySellOrderSellAdapter adapter) {
        myPayOrderRec.setAdapter(adapter);
    }

    @Override
    public void loadAdapter(MySellOrderAskToBuyAdapter adapter) {
        myPayOrderRec.setAdapter(adapter);
    }

    @Override
    public void loadAdapter(MySellOrderAppealListAdapter adapter) {
        myPayOrderRec.setAdapter(adapter);
    }

    @Override
    public void finishRefresh() {
        myPayOrderSmart.finishRefresh();
        myPayOrderSmart.finishLoadMore();
    }

    @Override
    protected void onResume() {
        super.onResume();
        switch (myPayOrderTab.getSelectedTabPosition()) {
            case 0:
                presenter.initData(page);
                break;
            case 1:
                presenter.askToBuy(page);
                break;
            case 2:
                presenter.appealList(page);
                break;
            default:
                break;
        }
    }
}
