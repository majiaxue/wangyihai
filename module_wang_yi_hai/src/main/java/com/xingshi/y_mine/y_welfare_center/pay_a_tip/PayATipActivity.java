package com.xingshi.y_mine.y_welfare_center.pay_a_tip;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.xingshi.common.CommonResource;
import com.xingshi.entity.EventBusBean;
import com.xingshi.mvp.BaseActivity;
import com.xingshi.y_main.R;
import com.xingshi.y_main.R2;
import com.xingshi.y_mine.y_welfare_center.pay_a_tip.adapter.PayATipAdapter;
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
 * 付费线报
 */
public class PayATipActivity extends BaseActivity<PayATipView, PayATipPresenter> implements PayATipView {


    @BindView(R2.id.pay_a_tip_image_back)
    ImageView payATipImageBack;
    @BindView(R2.id.pay_a_tip_rec)
    RecyclerView payATipRec;
    @BindView(R2.id.pay_a_tip_smart)
    SmartRefreshLayout payATipSmart;

    private int page = 1;
    private int position;

    @Override
    public int getLayoutId() {
        return R.layout.activity_pay_atip;
    }

    @Override
    public void initData() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        payATipRec.setLayoutManager(linearLayoutManager);
        presenter.initData(page);

        payATipSmart.setRefreshHeader(new MaterialHeader(this));
        payATipSmart.setRefreshFooter(new ClassicsFooter(this));

        payATipSmart.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page = 1;
                presenter.initData(page);
            }
        });

        payATipSmart.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                page++;
                presenter.initData(page);
            }
        });
    }

    @Override
    public void initClick() {
        payATipImageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(EventBusBean eventBusBean) {
        if (CommonResource.QUKUAI.equals(eventBusBean.getMsg())) {
            //微信支付成功回调
            presenter.isPay(position);
        }
    }

    @Override
    public PayATipView createView() {
        return this;
    }

    @Override
    public PayATipPresenter createPresenter() {
        return new PayATipPresenter(this);
    }

    @Override
    public void loadAdapter(PayATipAdapter payATipAdapter) {
        payATipRec.setAdapter(payATipAdapter);
    }

    @Override
    public void position(int position) {
        this.position = position;
    }

    @Override
    public void finishRefresh() {
        payATipSmart.finishRefresh();
        payATipSmart.finishLoadMore();
    }
}
