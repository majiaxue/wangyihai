package com.xingshi.commoditydetails.jd;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.xingshi.bean.JDListBean;
import com.xingshi.commoditydetails.pdd.adapter.CommodityDetailsRecAdapter;
import com.xingshi.common.CommonResource;
import com.xingshi.module_base.ModuleBaseApplication;
import com.xingshi.module_classify.R;
import com.xingshi.module_classify.R2;
import com.xingshi.mvp.BaseActivity;
import com.xingshi.utils.AppManager;
import com.xingshi.utils.ArithUtil;
import com.xingshi.utils.CustomDialog;
import com.xingshi.utils.LogUtil;
import com.xingshi.utils.MyTimeUtil;
import com.xingshi.utils.PopUtils;
import com.xingshi.utils.SPUtil;
import com.facebook.drawee.view.SimpleDraweeView;
import com.stx.xhb.xbanner.XBanner;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import butterknife.BindView;

/**
 * 京东商品详情
 */
@Route(path = "/module_classify/JDCommodityDetailsActivity")
public class JDCommodityDetailsActivity extends BaseActivity<JDCommodityDetailsView, JDCommodityDetailsPresenter> implements JDCommodityDetailsView, NestedScrollView.OnScrollChangeListener {

    @BindView(R2.id.commodity_image_back)
    ImageView commodityImageBack;
    @BindView(R2.id.commodity_xbanner)
    XBanner commodityXbanner;
    @BindView(R2.id.commodity_name)
    TextView commodityName;
    @BindView(R2.id.commodity_text)
    TextView commodityText;
    @BindView(R2.id.commodity_preferential_price)
    TextView commodityPreferentialPrice;
    @BindView(R2.id.commodity_earnings)
    TextView commodityEarnings;
    @BindView(R2.id.commodity_original_price)
    TextView commodityOriginalPrice;
    @BindView(R2.id.commodity_number_sold)
    TextView commodityNumberSold;
    @BindView(R2.id.commodity_shop_image)
    SimpleDraweeView commodityShopImage;
    @BindView(R2.id.commodity_shop_name)
    TextView commodityShopName;
    @BindView(R2.id.shop_describe_score)
    TextView shopDescribeScore;
    @BindView(R2.id.shop_service_score)
    TextView shopServiceScore;
    @BindView(R2.id.shop_logistics_score)
    TextView shopLogisticsScore;
    @BindView(R2.id.shop_particulars)
    RecyclerView shopParticulars;
    @BindView(R2.id.shop_recommend_rec)
    RecyclerView shopRecommendRec;
    @BindView(R2.id.commodity_nested_scroll)
    NestedScrollView commodityNestedScroll;
    @BindView(R2.id.commodity_stick)
    ImageView commodityStick;
    @BindView(R2.id.commodity_go_home)
    LinearLayout commodityGoHome;
    @BindView(R2.id.commodity_collect_image)
    ImageView commodityCollectImage;
    @BindView(R2.id.commodity_collect)
    LinearLayout commodityCollect;
    @BindView(R2.id.commodity_share)
    LinearLayout commodityShare;
    @BindView(R2.id.commodity_led_securities)
    LinearLayout commodityLedSecurities;
    @BindView(R2.id.commodity_linear)
    LinearLayout commodityLinear;
    @BindView(R2.id.commodity_into_shop)
    TextView commodityIntoShop;
    @BindView(R2.id.shop_text1)
    TextView shopText1;
    @BindView(R2.id.shop_text2)
    TextView shopText2;
    @BindView(R2.id.shop_text3)
    TextView shopText3;
    @BindView(R2.id.shop_no_goods)
    LinearLayout shopNoGoods;
    @BindView(R2.id.commodity_coupon_price)
    TextView commodityCouponPrice;
    @BindView(R2.id.commodity_time)
    TextView commodityTime;
    @BindView(R2.id.commodity_immediately_receive)
    TextView commodityImmediatelyReceive;
    @BindView(R2.id.commodity_led_securities_text)
    TextView commodityLedSecuritiesText;
    @BindView(R2.id.commodity_shop_item)
    RelativeLayout commodityShopItem;
    @BindView(R2.id.commodity_details_no_coupon)
    LinearLayout mLinear;

    @Autowired(name = "skuid")
    String skuid;


    private JDListBean listsBeanList;

    private String qRImage;
    private CustomDialog customDialog;


    @Override
    public int getLayoutId() {
        return R.layout.activity_commodity_details;
    }

    @Override
    public void initData() {
        ARouter.getInstance().inject(this);
        AppManager.getInstance().addGoodsActivity(this);
        ModuleBaseApplication.initShare();
        customDialog = new CustomDialog(this);
        customDialog.show();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        shopParticulars.setLayoutManager(linearLayoutManager);
        shopParticulars.setNestedScrollingEnabled(false);//禁止rcyc嵌套滑动
        shopParticulars.setHasFixedSize(true);

        commodityOriginalPrice.setVisibility(View.GONE);
        commodityText.setVisibility(View.GONE);
        shopNoGoods.setVisibility(View.GONE);

        presenter.loadData(skuid);

        presenter.historySave(skuid);

        commodityShopItem.setVisibility(View.GONE);

//        sub = ArithUtil.sub(Double.valueOf(listsBeanList.getPriceInfo().getPrice()), Double.valueOf(listsBeanList.getCouponInfo().getCouponList().get(0).getDiscount()));

        //收藏状态
        presenter.isCollect(commodityCollectImage, skuid);

    }

    @Override
    public void initClick() {
        //返回上个页面
        commodityImageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //监听    NestedScrollView
        commodityNestedScroll.setOnScrollChangeListener(this);
        //点击回到顶部
        commodityStick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                commodityNestedScroll.fullScroll(NestedScrollView.FOCUS_UP);
            }
        });
        //回到首页
        commodityGoHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ARouter.getInstance().build("/home/main").navigation();
                AppManager.getInstance().finishGoodsActivity();
            }
        });
        commodityShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(SPUtil.getToken())) {
                    PopUtils.isLogin(JDCommodityDetailsActivity.this);
                } else {
                    presenter.share();
                }
            }
        });

        //立即领取
        commodityImmediatelyReceive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.clickLedSecurities(qRImage);
            }
        });

        //领劵
        commodityLedSecurities.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(CommodityDetailsActivity.this, "点击了领劵", Toast.LENGTH_SHORT).show();
                if (listsBeanList.getData().get(0).getCouponInfo().getCouponList().size() > 0) {
                    presenter.clickLedSecurities(qRImage);
                } else {
                    presenter.clickLedSecurities(listsBeanList.getData().get(0).getMaterialUrl());
                }
            }
        });
        //收藏
        commodityCollect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(CommodityDetailsActivity.this, "点击了收藏", Toast.LENGTH_SHORT).show();
                presenter.goodsCollect(commodityCollectImage, skuid);
            }
        });
    }

    @Override
    public JDCommodityDetailsView createView() {
        return this;
    }

    @Override
    public JDCommodityDetailsPresenter createPresenter() {
        return new JDCommodityDetailsPresenter(this);
    }

    @Override
    public void onScrollChange(NestedScrollView nestedScrollView, int i, int i1, int i2, int i3) {
        int[] ints = new int[2];
        commodityLinear.getLocationOnScreen(ints);
        int y = ints[1];
        if (y <= commodityImageBack.getHeight()) {
            //显示
            commodityStick.setVisibility(View.VISIBLE);
        } else {
            //隐藏
            commodityStick.setVisibility(View.GONE);
        }
    }

    @Override
    public void loadUI(JDListBean jDGoodsRecBean, CommodityDetailsRecAdapter adapter) {
        customDialog.dismiss();
        listsBeanList = jDGoodsRecBean;
        commodityName.setText(jDGoodsRecBean.getData().get(0).getSkuName());//名字
        commodityPreferentialPrice.setText("￥" + jDGoodsRecBean.getData().get(0).getPriceInfo().getPrice());//优惠价
//        commodityOriginalPrice.setText("原价：￥" + jDGoodsRecBean.getData().get(0).getPriceInfo().getPrice());//原价
        commodityNumberSold.setText("已售" + jDGoodsRecBean.getData().get(0).getInOrderCount30Days() + "件");//已售
        if (jDGoodsRecBean.getData().get(0).getCouponInfo().getCouponList().size() > 0) {
            commodityCouponPrice.setText(jDGoodsRecBean.getData().get(0).getCouponInfo().getCouponList().get(0).getDiscount() + "元优惠劵");
            String startTime = MyTimeUtil.date2String("" + jDGoodsRecBean.getData().get(0).getCouponInfo().getCouponList().get(0).getUseStartTime());
            String endTime = MyTimeUtil.date2String("" + jDGoodsRecBean.getData().get(0).getCouponInfo().getCouponList().get(0).getUseEndTime());
            commodityTime.setText("有效期：" + startTime + "~" + endTime);
        } else {
            commodityCouponPrice.setText("暂无优惠券");
            commodityTime.setText("*注:分享或购买后可获得佣金");
            commodityImmediatelyReceive.setText("立即购买");
        }
        double commission = jDGoodsRecBean.getData().get(0).getCommissionInfo().getCommission();
        float value = SPUtil.getFloatValue(CommonResource.BACKBL);
        commodityEarnings.setText("预估收益：￥" + ArithUtil.mul(commission, value));//收益
        shopParticulars.setAdapter(adapter);
        presenter.setXBanner(commodityXbanner);
        //领劵
        if (listsBeanList.getData().get(0).getCouponInfo().getCouponList().size() > 0) {
            presenter.ledSecurities(listsBeanList.getData().get(0).getMaterialUrl(), listsBeanList.getData().get(0).getCouponInfo().getCouponList().get(0).getLink());
        } else {
            presenter.ledSecurities(listsBeanList.getData().get(0).getMaterialUrl(), "");
        }
        //推荐
//        presenter.setRecommendRec(shopRecommendRec, jDGoodsRecBean.getData().get(0).getCategoryInfo().getCid1Name());

    }

    @Override
    public void isNoGoods(boolean isNoGoods) {
        if (isNoGoods) {
            shopNoGoods.setVisibility(View.GONE);
        } else {
            shopNoGoods.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void qrImage(String url) {
        this.qRImage = url;
        Glide.with(this)
                .asBitmap()
                .load(listsBeanList.getData().get(0).getImageInfo().getImageList().get(0).getUrl())
                .into(new CustomTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap bitmap, @Nullable Transition<? super Bitmap> transition) {
                        saveImageToPhotos(bitmap);
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {

                    }
                });
    }

    /**
     * 保存二维码到本地相册
     */
    private void saveImageToPhotos(Bitmap bmp) {
        // 首先保存图片
        File appDir = new File(Environment.getExternalStorageDirectory(), "Boohee");
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        String fileName = "wwww" + ".jpg";
        File file = new File(appDir, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.JPEG, 30, fos);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //shareImage.setImageURI(Uri.fromFile(new File(file.getPath())));
        presenter.viewToImage(listsBeanList.getData().get(0), qRImage, file.getPath());
        LogUtil.e("图片路径" + file.getPath());
    }


}
