package com.xingshi.first_page;

import com.xingshi.mvp.BaseFragmentActivity;

public class FirstPageActivity extends BaseFragmentActivity<FirstPageView,FirstPagePresenter> implements FirstPageView {
    @Override
    public int getLayoutId() {
        return 0;
    }

    @Override
    public void initData() {

    }

    @Override
    public void initClick() {

    }

    @Override
    public FirstPageView createView() {
        return null;
    }

    @Override
    public FirstPagePresenter createPresenter() {
        return null;
    }
}
