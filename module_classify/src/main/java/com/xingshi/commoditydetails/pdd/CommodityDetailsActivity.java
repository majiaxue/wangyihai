package com.xingshi.commoditydetails.pdd;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.xingshi.bean.CommodityDetailsBean;
import com.xingshi.common.CommonResource;
import com.xingshi.module_base.ModuleBaseApplication;
import com.xingshi.module_classify.R;
import com.xingshi.module_classify.R2;
import com.xingshi.mvp.BaseActivity;
import com.xingshi.utils.AppManager;
import com.xingshi.utils.ArithUtil;
import com.xingshi.utils.LogUtil;
import com.xingshi.utils.MyTimeUtil;
import com.xingshi.utils.PopUtils;
import com.xingshi.utils.ProcessDialogUtil;
import com.xingshi.utils.SPUtil;
import com.facebook.drawee.view.SimpleDraweeView;
import com.stx.xhb.xbanner.XBanner;
import com.umeng.socialize.UMShareAPI;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 拼多多商品详情
 */
@Route(path = "/module_classify/CommodityDetailsActivity")
public class CommodityDetailsActivity extends BaseActivity<CommodityDetailsView, CommodityDetailsPresenter> implements CommodityDetailsView, NestedScrollView.OnScrollChangeListener {

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
    @BindView(R2.id.commodity_coupon_price)
    TextView commodityCouponPrice;
    @BindView(R2.id.commodity_time)
    TextView commodityTime;
    @BindView(R2.id.commodity_immediately_receive)
    TextView commodityImmediatelyReceive;
    @BindView(R2.id.commodity_led_securities_text)
    TextView commodityLedSecuritiesText;


    @Autowired(name = "goods_id")
    String goods_id;


    private List<CommodityDetailsBean.GoodsDetailResponseBean.GoodsDetailsBean> detailsBeanList = new ArrayList<>();
    private String imageUrl;
    private int flag = 0;
    private File file;
//    private CustomDialog customDialog;


    @Override
    public int getLayoutId() {
        return R.layout.activity_commodity_details;
    }

    @Override
    public void initData() {
        ARouter.getInstance().inject(this);
        AppManager.getInstance().addGoodsActivity(this);
        ModuleBaseApplication.initShare();
        ProcessDialogUtil.showProcessDialog(this);

        LogUtil.e("goods_id" + goods_id);
        commodityIntoShop.setVisibility(View.INVISIBLE);
        //加载视图
        presenter.initView(goods_id);

        //保存浏览记录
//        presenter.historySave(goods_id);
        //推荐recycler
        presenter.setRecommendRec(shopRecommendRec);
        //字体加中划线
        commodityOriginalPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG); // 设置中划线并加清晰
        //店铺头像
        commodityShopImage.setImageResource(R.drawable.img_pinduoduo);
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
        //分享
        commodityShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(SPUtil.getToken())) {
                    PopUtils.isLogin(CommodityDetailsActivity.this);
                } else {
                    presenter.share();
                }
            }
        });
        //立即领取
        commodityImmediatelyReceive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.jumpToWeb(imageUrl);
            }
        });
        //领劵
        commodityLedSecurities.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(CommodityDetailsActivity.this, "点击了领劵", Toast.LENGTH_SHORT).show();
                presenter.jumpToWeb(imageUrl);
            }
        });
        //收藏
        commodityCollect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(CommodityDetailsActivity.this, "点击了收藏", Toast.LENGTH_SHORT).show();
                presenter.goodsCollect(commodityCollectImage, detailsBeanList);
            }
        });
    }

    @Override
    public CommodityDetailsView createView() {
        return this;
    }

    @Override
    public CommodityDetailsPresenter createPresenter() {
        return new CommodityDetailsPresenter(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d("ShopHomeFragment", "不可见");
        commodityXbanner.stopAutoPlay();
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("ShopHomeFragment", "可见");
        commodityXbanner.startAutoPlay();
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

        if (y <= commodityName.getHeight()) {
            commodityXbanner.stopAutoPlay();
        } else {
            commodityXbanner.startAutoPlay();
        }
    }

    @Override
    public void CommodityDetailsList(List<CommodityDetailsBean.GoodsDetailResponseBean.GoodsDetailsBean> beanList) {
        this.detailsBeanList = beanList;
//        LogUtil.e("收益1-------->" + earnings);

        LogUtil.e("图片" + detailsBeanList.get(0).getGoods_gallery_urls().get(0));
        //到手价
        double div = ArithUtil.div(beanList.get(0).getMin_group_price() - beanList.get(0).getCoupon_discount(), 100, 1);
        //佣金比例
        double promotionRate = ArithUtil.mul(div, ArithUtil.div(beanList.get(0).getPromotion_rate(), 1000, 1));

        commodityName.setText(beanList.get(0).getGoods_name());//名字
        commodityPreferentialPrice.setText("￥" + div);//优惠价
        commodityOriginalPrice.setText("原价：￥" + ArithUtil.div(beanList.get(0).getMin_group_price(), 100, 1));//原价
        commodityNumberSold.setText("已售" + beanList.get(0).getSold_quantity() + "件");//已售
        commodityCouponPrice.setText(ArithUtil.sub(ArithUtil.div(beanList.get(0).getMin_group_price(), 100, 0), div) + "元优惠劵");
        commodityEarnings.setText("预估收益：￥" + ArithUtil.mul(promotionRate, SPUtil.getFloatValue(CommonResource.BACKBL)));//收益
        LogUtil.e("预估收益: 到手价" + div + "佣金比例" + promotionRate + "个人佣金" + SPUtil.getFloatValue(CommonResource.BACKBL));
        String startTime = MyTimeUtil.date2String(beanList.get(0).getCoupon_start_time() * 1000 + "");
        String endTime = MyTimeUtil.date2String(beanList.get(0).getCoupon_end_time() * 1000 + "");
        commodityTime.setText("有效期限：" + startTime + "~" + endTime);
        commodityShopName.setText(beanList.get(0).getMall_name());
        shopDescribeScore.setText("" + ArithUtil.div(beanList.get(0).getAvg_desc(), 100, 1));
        shopServiceScore.setText("" + ArithUtil.div(beanList.get(0).getAvg_serv(), 100, 1));
        shopLogisticsScore.setText("" + ArithUtil.div(beanList.get(0).getAvg_lgst(), 100, 1));
        //详情轮播图
        presenter.setXBanner(commodityXbanner, beanList);
        //商品详情图片
        presenter.setShopParticulars(shopParticulars, beanList);
        //收藏状态
        presenter.isCollect(commodityCollectImage, beanList);
        //领劵
        presenter.ledSecurities(detailsBeanList.get(0).getGoods_id());


    }

    @Override
    public void imageUri(String url) {
        this.imageUrl = url;
        //viewToImage
        Glide.with(this)
                .asBitmap()
                .load(detailsBeanList.get(0).getGoods_gallery_urls().get(0))
                .into(new CustomTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap bitmap, @Nullable Transition<? super Bitmap> transition) {
                        saveImageToPhotos(bitmap);
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {

                    }
                });
//        customDialog.dismiss();

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
        file = new File(appDir, fileName);
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
        presenter.viewToImage(imageUrl, file.getPath());
        LogUtil.e("图片路径" + file.getPath());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

}
