package com.xingshi.user_agreement;

import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.xingshi.common.CommonResource;
import com.xingshi.module_mine.R;
import com.xingshi.module_mine.R2;
import com.xingshi.mvp.BaseActivity;

import butterknife.BindView;

@Route(path = "/mine/agreement2")
public class UserAgreementActivity2 extends BaseActivity<UserAgreementView,UserAgreementPresenter> implements UserAgreementView {
    @BindView(R2.id.include_back)
    ImageView includeBack;
    @BindView(R2.id.include_title)
    TextView includeTitle;
    @BindView(R2.id.agrement_webview)
    WebView agrementWebview;
    @Autowired(name = "type")
    String type;

    @Override
    public int getLayoutId() {
        return R.layout.activity_user_agreement;
    }

    @Override
    public void initData() {
        ARouter.getInstance().inject(this);
        includeTitle.setText("用户协议");

        WebSettings webSettings = agrementWebview.getSettings();

        //如果访问的页面中要与Javascript交互，则webview必须设置支持Javascript
        webSettings.setJavaScriptEnabled(true);
        // 若加载的 html 里有JS 在执行动画等操作，会造成资源浪费（CPU、电量）

        //设置自适应屏幕，两者合用
        webSettings.setUseWideViewPort(true); //将图片调整到适合webview的大小
        webSettings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小
        webSettings.setDomStorageEnabled(false);
        webSettings.setDatabaseEnabled(true);
        webSettings.setAllowFileAccessFromFileURLs(true);
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);

        agrementWebview.loadUrl(CommonResource.BASEURL_9005 + CommonResource.USER_AGREEMENT + "/" + type);

        agrementWebview.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return false;
            }
        });
    }

    @Override
    public void initClick() {
        includeBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public UserAgreementView createView() {
        return this;
    }

    @Override
    public UserAgreementPresenter createPresenter() {
        return new UserAgreementPresenter(this);
    }
}
