package com.xingshi.buy2up;

import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.xingshi.bean.BannerBean;
import com.xingshi.bean.UserGoodsDetail;
import com.xingshi.module_mine.R;
import com.xingshi.module_mine.R2;
import com.xingshi.mvp.BaseActivity;
import com.stx.xhb.xbanner.XBanner;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

@Route(path = "/module_mine/buy2up")
public class Buy2UpActivity extends BaseActivity<Buy2UpView, Buy2UpPresenter> implements Buy2UpView {
    @BindView(R2.id.include_back)
    ImageView includeBack;
    @BindView(R2.id.include_title)
    TextView includeTitle;
    @BindView(R2.id.buy2up_banner)
    XBanner buy2upBanner;
    @BindView(R2.id.buy2up_quanyi)
    TextView buy2upQuanyi;
    @BindView(R2.id.buy2up_webview)
    WebView buy2upWebview;
    @BindView(R2.id.buy2up_btn)
    TextView buy2upBtn;
    @BindView(R2.id.buy2up_name)
    TextView mName;

    @Autowired(name = "bean")
    UserGoodsDetail bean;

    @Override
    public int getLayoutId() {
        return R.layout.activity_buy2up;
    }

    @Override
    public void initData() {
        ARouter.getInstance().inject(this);
//        Intent intent = getIntent();
//        bean = (UserGoodsDetail) intent.getSerializableExtra("bean");
        String albumPics = bean.getAlbumPics();
        String[] split = albumPics.split(",");
        List<BannerBean.RecordsBean> banner = new ArrayList<>();
        for (int i = 0; i < split.length; i++) {
            banner.add(new BannerBean.RecordsBean(split[i]));
        }

        includeTitle.setText("商品详情");

        buy2upBanner.loadImage(new XBanner.XBannerAdapter() {
            @Override
            public void loadBanner(XBanner banner, Object model, View view, int position) {
                Glide.with(Buy2UpActivity.this).load(((BannerBean.RecordsBean) model).getXBannerUrl()).apply(RequestOptions.centerCropTransform()).into((ImageView) view);
            }
        });

        buy2upWebview.getSettings().setJavaScriptEnabled(true);
        presenter.loadData(bean.getId(), bean.getLevelId());

        presenter.loadQuanyi(bean.getLevelId());
    }

    @Override
    public void initClick() {
        includeBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        buy2upQuanyi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.popQuanYi();
            }
        });

        buy2upBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.isCanUp(bean.getLevelId());
            }
        });
    }

    @Override
    public void loadUI(UserGoodsDetail bean) {
        mName.setText(bean.getName());
        String detailHtml = bean.getDetailHtml();
        String varjs = "<script type='text/javascript'> \nwindow.onload = function()\n{var $img = document.getElementsByTagName('img');for(var p in  $img){$img[p].style.width = '100%'; $img[p].style.height ='auto'}}</script>";
        //替换img属性
        buy2upWebview.loadData(varjs + detailHtml, "text/html", "UTF-8");
    }

    @Override
    public void loadBanner(List<BannerBean.RecordsBean> bannerList) {
        buy2upBanner.setBannerData(bannerList);
    }

    @Override
    public Buy2UpView createView() {
        return this;
    }

    @Override
    public Buy2UpPresenter createPresenter() {
        return new Buy2UpPresenter(this);
    }
}
