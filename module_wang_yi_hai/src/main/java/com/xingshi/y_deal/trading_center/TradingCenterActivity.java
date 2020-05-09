package com.xingshi.y_deal.trading_center;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xingshi.mvp.BaseActivity;
import com.xingshi.utils.LogUtil;
import com.xingshi.y_deal.trading_center.adapter.TradingCenterPayAdapter;
import com.xingshi.y_deal.trading_center.adapter.TradingCenterSellAdapter;
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
 * 交易中心
 */
public class TradingCenterActivity extends BaseActivity<TradingCenterView, TradingCenterPresenter> implements TradingCenterView {
    @BindView(R2.id.trading_center_back)
    ImageView tradingCenterBack;
    @BindView(R2.id.trading_center_title)
    TextView tradingCenterTitle;
    @BindView(R2.id.trading_center_tab)
    TabLayout tradingCenterTab;
    @BindView(R2.id.trading_center_rec)
    RecyclerView tradingCenterRec;
    @BindView(R2.id.trading_center_smart)
    SmartRefreshLayout tradingCenterSmart;
    @BindView(R2.id.trading_center_price)
    TextView tradingCenterPrice;
    @BindView(R2.id.trading_center_num)
    EditText tradingCenterNum;
    @BindView(R2.id.trading_center_sell_amount)
    TextView tradingCenterSellAmount;
    @BindView(R2.id.trading_center_service_charge)
    TextView tradingCenterServiceCharge;
    @BindView(R2.id.trading_center_affirm)
    TextView tradingCenterAffirm;
    @BindView(R2.id.trading_center_hint)
    TextView tradingCenterHint;
    @BindView(R2.id.trading_center_linear)
    LinearLayout tradingCenterLinear;

    private double amount;//总价
    private double servicePrice;//手续费
    private double price;//回购单价
    private String serviceCharge;//手续费%

    private int page = 1;
    private int type;

    @Override
    public int getLayoutId() {
        return R.layout.activity_trading_center;
    }

    @Override
    public void initData() {
        Intent intent = getIntent();
        type = intent.getIntExtra("type", -1);
        presenter.initTab(tradingCenterTab, type);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        tradingCenterRec.setLayoutManager(linearLayoutManager);
        presenter.initData(page, type);

        tradingCenterSmart.setRefreshHeader(new MaterialHeader(this));
        tradingCenterSmart.setRefreshFooter(new ClassicsFooter(this));

        tradingCenterSmart.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page = 1;
                presenter.initData(page, type);
            }
        });

        tradingCenterSmart.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                page++;
                presenter.initData(page, type);

            }
        });
    }

    @Override
    public void initClick() {
        tradingCenterBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        tradingCenterAffirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.buyBack(price, tradingCenterNum.getText().toString(), amount, servicePrice);
            }
        });

        tradingCenterNum.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().length() > 0) {
                    amount = price * Integer.parseInt(tradingCenterNum.getText().toString());
                    tradingCenterSellAmount.setText("" + amount);
                    servicePrice = (Double.valueOf(serviceCharge) / 100) * amount;
                    tradingCenterServiceCharge.setText("$" + servicePrice);
                } else {
                    tradingCenterSellAmount.setText("总数量");
                    tradingCenterServiceCharge.setText("$0.0");
                }
            }
        });
    }

    @Override
    public TradingCenterView createView() {
        return this;
    }

    @Override
    public TradingCenterPresenter createPresenter() {
        return new TradingCenterPresenter(this);
    }

    @Override
    public void isShow(boolean isShow) {
        if (isShow) {
            LogUtil.e("isshow11--------"+isShow);
            tradingCenterSmart.setVisibility(View.VISIBLE);
            tradingCenterLinear.setVisibility(View.GONE);
        } else {
            LogUtil.e("isshow22--------"+isShow);
            tradingCenterSmart.setVisibility(View.GONE);
            tradingCenterLinear.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void loadAdapter(TradingCenterPayAdapter adapter) {
        tradingCenterRec.setAdapter(adapter);
    }

    @Override
    public void loadAdapter(TradingCenterSellAdapter adapter) {
        tradingCenterRec.setAdapter(adapter);
    }

    @Override
    public void buyBack(String serviceCharge, int minNumber, double price, double currency) {
        this.serviceCharge = serviceCharge;
        LogUtil.e("serviceCharge=========="+serviceCharge);
        LogUtil.e("minNumber============"+minNumber);
        LogUtil.e("price==========="+price);
        LogUtil.e("currency=========="+currency);
        this.price = price;
        tradingCenterPrice.setText("" + price);
        tradingCenterNum.setHint("每单最低回购" + minNumber + "个");
        amount = minNumber * price;
        tradingCenterSellAmount.setText("" + amount);
        servicePrice = (Double.valueOf(serviceCharge) / 100) * amount;
        tradingCenterServiceCharge.setText("$" + servicePrice);
        //tradingCenterHint.setText("每单最低回购" + minNumber + "个\n" + "平台每日最多回购1000个");
    }

    @Override
    public void finishRefresh() {
        tradingCenterSmart.finishRefresh();
        tradingCenterSmart.finishLoadMore();
    }
}
