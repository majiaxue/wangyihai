package com.xingshi.home.bannerzhanshi;

import android.os.Bundle;
import android.widget.ImageView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.bumptech.glide.Glide;
import com.xingshi.module_home.R;
import com.xingshi.module_home.R2;
import com.xingshi.mvp.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
@Route(path = "/model_home/BannerGlideActivity")
public class BannerGlideActivity extends BaseActivity<BannerGlideView, BannerGlidePresenter> implements BannerGlideView {
    @BindView(R2.id.img)
    ImageView img;
    @Autowired(name = "url")
    String url;
    @Override
    public int getLayoutId() {
        return R.layout.activity_banner_glide;
    }

    @Override
    public void initData() {
        ARouter.getInstance().inject(this);
        Glide.with(this).load(url).into(img);
    }

    @Override
    public void initClick() {

    }

    @Override
    public BannerGlideView createView() {
        return this;
    }

    @Override
    public BannerGlidePresenter createPresenter() {
        return new BannerGlidePresenter(this);
    }
}
