package com.xingshi.local_order_detail;

import com.xingshi.module_local.R;
import com.xingshi.mvp.BaseActivity;

public class LocalOrderDetailActivity extends BaseActivity<LocalOrderDetailView, LocalOrderDetailPresenter> implements LocalOrderDetailView {
    @Override
    public int getLayoutId() {
        return R.layout.activity_local_order_detail;
    }

    @Override
    public void initData() {

    }

    @Override
    public void initClick() {

    }

    @Override
    public LocalOrderDetailView createView() {
        return this;
    }

    @Override
    public LocalOrderDetailPresenter createPresenter() {
        return new LocalOrderDetailPresenter(this);
    }
}
