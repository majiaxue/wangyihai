package com.xingshi.y_home.y_goods_details;

import android.content.Intent;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.xingshi.adapter.GoodsImageAdapter;
import com.xingshi.bean.BannerBean;
import com.xingshi.bean.YGoodsDetailsBean;
import com.xingshi.mvp.BaseActivity;
import com.xingshi.y_confirm_order.YConfirmOrderActivity;
import com.xingshi.y_main.R;
import com.xingshi.y_main.R2;
import com.stx.xhb.xbanner.XBanner;

import java.util.List;

import butterknife.BindView;

/**
 * 区块商品详情
 */
public class YGoodsDetailsActivity extends BaseActivity<YGoodsDetailsView, YGoodsDetailsPresenter> implements YGoodsDetailsView {


    @BindView(R2.id.y_goods_details_buy_goods)
    TextView yGoodsDetailsBuyGoods;
    @BindView(R2.id.y_goods_detail_banner)
    XBanner yGoodsDetailBanner;
    @BindView(R2.id.y_goods_details_title)
    TextView yGoodsDetailsTitle;
    @BindView(R2.id.y_goods_details_is_hot_sell)
    ImageView yGoodsDetailsIsHotSell;
    @BindView(R2.id.y_goods_details_present_price)
    TextView yGoodsDetailsPresentPrice;
    @BindView(R2.id.y_goods_details_original_price)
    TextView yGoodsDetailsOriginalPrice;
    @BindView(R2.id.y_goods_details_sale)
    TextView yGoodsDetailsSale;
    @BindView(R2.id.y_goods_details_intro)
    TextView yGoodsDetailsIntro;
    @BindView(R2.id.y_goods_details_specification)
    TextView yGoodsDetailsSpecification;
    @BindView(R2.id.y_goods_details_web_details)
    WebView yGoodsDetailsWebDetails;
    @BindView(R2.id.y_goods_details_back)
    LinearLayout yGoodsDetailsBack;
    @BindView(R2.id.y_goods_details_specification_linear)
    LinearLayout yGoodsDetailsSpecificationLinear;

    private int id;

    @Override
    public int getLayoutId() {
        return R.layout.activity_ygoods_details;
    }

    @Override
    public void initData() {
        Intent intent = getIntent();
        id = intent.getIntExtra("id", -1);
        presenter.initData(id);

        presenter.completeTask();
        yGoodsDetailsWebDetails.getSettings().setJavaScriptEnabled(true);
        //设置自适应屏幕，两者合用
        yGoodsDetailsWebDetails.getSettings().setUseWideViewPort(true); //将图片调整到适合webview的大小
        yGoodsDetailsWebDetails.getSettings().setLoadWithOverviewMode(true); // 缩放至屏幕的大小
        yGoodsDetailsWebDetails.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });

        //轮播图
        yGoodsDetailBanner.loadImage(new XBanner.XBannerAdapter() {
            @Override
            public void loadBanner(XBanner banner, Object model, View view, int position) {
                Glide.with(YGoodsDetailsActivity.this).load(((BannerBean.RecordsBean) model).getXBannerUrl()).apply(RequestOptions.centerCropTransform()).into((ImageView) view);
            }
        });
    }

    @Override
    public void initClick() {
        yGoodsDetailsBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        yGoodsDetailsBuyGoods.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //去确认订单
                startActivity(new Intent(YGoodsDetailsActivity.this, YConfirmOrderActivity.class));
            }
        });
        //选择规格
        yGoodsDetailsSpecificationLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.chooseGoodsPop();
            }
        });
        //立即购买
        yGoodsDetailsBuyGoods.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.chooseOrJump();
            }
        });
    }

    @Override
    public YGoodsDetailsView createView() {
        return this;
    }

    @Override
    public YGoodsDetailsPresenter createPresenter() {
        return new YGoodsDetailsPresenter(this);
    }

    @Override
    public void loadImage(GoodsImageAdapter adapter) {

    }

    @Override
    public void loadUI(YGoodsDetailsBean data, int size) {
        yGoodsDetailsTitle.setText(data.getName());
        yGoodsDetailsPresentPrice.setText("$" + data.getPrice());
        yGoodsDetailsOriginalPrice.setText("￥" + data.getOriginalPrice());
        yGoodsDetailsSale.setText("已售" + data.getSale() + "笔");

        StringBuffer sb = new StringBuffer();
        sb.append("选择");
        for (int i = 0; i < data.getXsProductAttributes().size(); i++) {
            sb.append(data.getXsProductAttributes().get(i).getName() + "  ");
        }
        yGoodsDetailsSpecification.setText(sb);

        String detailHtml = data.getDetailHtml();
        String varjs = "<script type='text/javascript'> \nwindow.onload = function()\n{var $img = document.getElementsByTagName('img');for(var p in  $img){$img[p].style.width = '100%'; $img[p].style.height ='auto'}}</script>";
        //替换img属性
        yGoodsDetailsWebDetails.loadData(varjs + detailHtml, "text/html", "UTF-8");
    }

    @Override
    public void loadBanner(List<BannerBean.RecordsBean> list) {
        yGoodsDetailBanner.setBannerData(list);
    }

    @Override
    public void yixuanze(String attr) {
        yGoodsDetailsSpecification.setText(attr);
    }

    @Override
    public void weixuanze(String str) {
        yGoodsDetailsSpecification.setText("选择" + str);
    }
}
