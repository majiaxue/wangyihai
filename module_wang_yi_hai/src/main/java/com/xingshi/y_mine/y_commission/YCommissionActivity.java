package com.xingshi.y_mine.y_commission;

import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.TextView;

import com.scwang.smartrefresh.header.MaterialHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.xingshi.bean.PopRuleBean;
import com.xingshi.mvp.BaseActivity;
import com.xingshi.utils.ClickUtil;
import com.xingshi.utils.OnClearCacheListener;
import com.xingshi.utils.YPopUtil;
import com.xingshi.y_main.R;
import com.xingshi.y_main.R2;
import com.xingshi.y_mine.y_bill.adapter.YBillAdapter;

import java.util.List;

import butterknife.BindView;

/**
 * 奖金余额
 */
public class YCommissionActivity extends BaseActivity<YCommissionView, YCommissionPresenter> implements YCommissionView {


    @BindView(R2.id.y_commission_back)
    ImageView yCommissionBack;
    @BindView(R2.id.y_commission_total_assets)
    TextView yCommissionTotalAssets;
    @BindView(R2.id.y_commission_shouyi)
    TextView yCommissionShouyi;
    @BindView(R2.id.y_commission_yue)
    TextView yCommissionYue;
    @BindView(R2.id.y_commission_withdraw)
    TextView yCommissionWithdraw;
    @BindView(R2.id.y_commission_tab)
    TabLayout yCommissionTab;
    @BindView(R2.id.y_commission_rec)
    RecyclerView yCommissionRec;
    @BindView(R2.id.y_commission_smart)
    SmartRefreshLayout yCommissionSmart;

    private String total;
    private List<PopRuleBean> popRuleBeanList;
    private int page = 1;

    @Override
    public int getLayoutId() {
        return R.layout.activity_ycommission;
    }

    @Override
    public void initData() {
        presenter.initData();
        presenter.popRule();
        presenter.initTab(yCommissionTab);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        yCommissionRec.setLayoutManager(linearLayoutManager);

        presenter.initRec(1, page);

        //设置 Header 为 官方主题 样式
        yCommissionSmart.setRefreshHeader(new MaterialHeader(this));
        //设置 Footer 为 默认 样式
        yCommissionSmart.setRefreshFooter(new ClassicsFooter(this));
    }

    @Override
    public void initClick() {
        yCommissionBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        yCommissionWithdraw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (popRuleBeanList.size() != 0) {
                    YPopUtil.commissionWithdraw(YCommissionActivity.this, total, popRuleBeanList, new OnClearCacheListener() {
                        @Override
                        public void setOnClearCache(final PopupWindow pop, View confirm) {
                            final EditText edit = confirm.findViewById(R.id.pop_commission_withdraw_edit);
                            final RadioButton butBalance = confirm.findViewById(R.id.pop_commission_withdraw_but_balance);
                            TextView withdraw = confirm.findViewById(R.id.pop_commission_withdraw_withdraw);

                            withdraw.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if (ClickUtil.isFastClick()) {
                                        if (butBalance.isChecked()) {
                                            presenter.backMoney(edit.getText().toString());
                                        } else {
                                            presenter.alipay(edit.getText().toString());
                                        }
                                    }
                                }
                            });
                        }
                    });
                }
            }
        });

        //********************设置上拉刷新下拉加载
        yCommissionSmart.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page = 1;
                presenter.initRec(yCommissionTab.getSelectedTabPosition(), page);
            }
        });
        yCommissionSmart.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                page++;
                presenter.initRec(yCommissionTab.getSelectedTabPosition(), page);
            }
        });
    }

    @Override
    public YCommissionView createView() {
        return this;
    }

    @Override
    public YCommissionPresenter createPresenter() {
        return new YCommissionPresenter(this);
    }


    @Override
    public void loadData(String total, String blance, String backMoney) {
        this.total = total;
        yCommissionTotalAssets.setText(total);
        yCommissionShouyi.setText(blance);
        yCommissionYue.setText(backMoney);
    }

    @Override
    public void popRule(List<PopRuleBean> popRuleBeanList) {
        this.popRuleBeanList = popRuleBeanList;
    }

    @Override
    public void loadAdapter(YBillAdapter adapter) {
        yCommissionRec.setAdapter(adapter);
    }

    @Override
    public void loadFinish() {
        yCommissionSmart.finishRefresh();
        yCommissionSmart.finishLoadMore();
    }
}
