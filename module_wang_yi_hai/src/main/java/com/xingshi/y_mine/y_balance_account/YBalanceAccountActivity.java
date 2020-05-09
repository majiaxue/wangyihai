package com.xingshi.y_mine.y_balance_account;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.ali.auth.third.core.util.StringUtil;
import com.xingshi.common.CommonResource;
import com.xingshi.entity.EventBusBean;
import com.xingshi.mvp.BaseActivity;
import com.xingshi.utils.ClickUtil;
import com.xingshi.utils.OnClearCacheListener;
import com.xingshi.utils.SPUtil;
import com.xingshi.utils.YPopUtil;
import com.xingshi.y_main.R;
import com.xingshi.y_main.R2;
import com.xingshi.y_mine.y_balance_account.adapter.YBalanceAccountAdapter;
import com.scwang.smartrefresh.header.MaterialHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;

/**
 * 区块账户余额
 */
public class YBalanceAccountActivity extends BaseActivity<YBalanceAccountView, YBalanceAccountPresenter> implements YBalanceAccountView {

    @BindView(R2.id.y_balance_account_back)
    ImageView yBalanceAccountBack;
    @BindView(R2.id.y_balance_account_total_assets)
    TextView yBalanceAccountTotalAssets;
    @BindView(R2.id.y_balance_account_top_up)
    TextView yBalanceAccountTopUp;
    @BindView(R2.id.y_balance_account_rec)
    RecyclerView yBalanceAccountRec;
    @BindView(R2.id.y_upgrade_merchant_release_task)
    TextView yUpgradeMerchantReleaseTask;
    @BindView(R2.id.y_balance_account_smart)
    SmartRefreshLayout yBalanceAccountSmart;

    private int page = 1;

    @Override
    public int getLayoutId() {
        return R.layout.activity_ybalance_account;
    }

    @Override
    public void initData() {
        presenter.initData();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        yBalanceAccountRec.setLayoutManager(linearLayoutManager);
        presenter.merchantRecord(page);

        yBalanceAccountSmart.setRefreshHeader(new MaterialHeader(this));
        yBalanceAccountSmart.setRefreshFooter(new ClassicsFooter(this));

        yBalanceAccountSmart.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page = 1;
                presenter.merchantRecord(page);
            }
        });

        yBalanceAccountSmart.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                page++;
                presenter.merchantRecord(page);
            }
        });
    }

    @Override
    public void initClick() {
        yBalanceAccountBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //充值
        yBalanceAccountTopUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                YPopUtil.topUp(YBalanceAccountActivity.this, new OnClearCacheListener() {
                    @Override
                    public void setOnClearCache(PopupWindow pop, View confirm) {
                        final RadioButton butWeChat = confirm.findViewById(R.id.pop_top_up_but_weChat);
                        final EditText upEdit = confirm.findViewById(R.id.pop_top_up_edit2);
                        TextView topUp = confirm.findViewById(R.id.pop_top_up_top_up);

                        topUp.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (ClickUtil.isFastClick()) {
                                    if (StringUtil.isBlank(upEdit.getText().toString())) {
                                        Toast.makeText(YBalanceAccountActivity.this, "请输入充值金额", Toast.LENGTH_SHORT).show();
                                    } else if ("0".equals(upEdit.getText().toString())) {
                                        Toast.makeText(YBalanceAccountActivity.this, "充值金额不能为0元", Toast.LENGTH_SHORT).show();
                                    } else {
                                        if (butWeChat.isChecked()) {
                                            //微信支付
                                            presenter.weChat(upEdit.getText().toString(), SPUtil.getUserCode());
                                        } else {
                                            //支付宝
                                            presenter.aliPay(upEdit.getText().toString(), SPUtil.getUserCode());
                                        }
                                    }
                                }

                            }
                        });

                    }
                });
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(EventBusBean eventBusBean) {
        if (CommonResource.QUKUAI.equals(eventBusBean.getMsg())) {
            //微信支付成功回调
            presenter.initData();
        }
    }

    @Override
    public YBalanceAccountView createView() {
        return this;
    }

    @Override
    public YBalanceAccountPresenter createPresenter() {
        return new YBalanceAccountPresenter(this);
    }

    @Override
    public void loadData(String s) {
        yBalanceAccountTotalAssets.setText(s);
    }

    @Override
    public void loadAdapter(YBalanceAccountAdapter adapter) {
        yBalanceAccountRec.setAdapter(adapter);
    }

    @Override
    public void finishRefresh() {
        yBalanceAccountSmart.finishRefresh();
        yBalanceAccountSmart.finishLoadMore();
    }
}
