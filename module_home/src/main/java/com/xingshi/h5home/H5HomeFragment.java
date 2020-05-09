package com.xingshi.h5home;

import android.view.View;
import android.webkit.WebView;

import com.xingshi.common.CommonResource;
import com.xingshi.entity.EventBusBean;
import com.xingshi.module_home.R;
import com.xingshi.module_home.R2;
import com.xingshi.mvp.BaseFragment;
import com.xingshi.utils.LogUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;

/**
 * h5首页
 */
public class H5HomeFragment extends BaseFragment<H5HomeView, H5HomePresenter> implements H5HomeView {

    @BindView(R2.id.home_h5_web)
    WebView homeH5Web;

    private int flag = 0;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_h5_home;
    }

    @Override
    public void initData() {
        EventBus.getDefault().register(this);
//        StatusBarUtils.setAndroidNativeLightStatusBar(getActivity(), false);
//        StatusBarUtils.setStatusTheme(getActivity(), true, true);
        presenter.check();
        presenter.initWebView(homeH5Web);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(EventBusBean eventBusBean) {
        if (CommonResource.WEBVIEW.equals(eventBusBean.getMsg())) {
            flag = 1;
            LogUtil.e("刷新webView" + flag);
        }
    }

    @Override
    public void initClick() {

        homeH5Web.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                return true;
            }
        });
    }

    @Override
    public H5HomeView createView() {
        return this;
    }

    @Override
    public H5HomePresenter createPresenter() {
        return new H5HomePresenter(getContext());
    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden) {
            //不可见
        } else {
            //可见
            if (1 == flag) {
                presenter.loadUrl(homeH5Web);
                flag = 0;
            }
        }
    }

}
