package com.xingshi.home;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.ali.auth.third.core.util.StringUtil;
import com.alibaba.android.arouter.launcher.ARouter;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.alipay.sdk.app.PayTask;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.xingshi.adapter.MyRecyclerAdapter;
import com.xingshi.bean.AliPayBean;
import com.xingshi.bean.BannerBean;
import com.xingshi.bean.GoodChoiceBean;
import com.xingshi.bean.GoodsRecommendBean;
import com.xingshi.bean.WeChatPayBean;
import com.xingshi.bean.ZhongXBannerBean;
import com.xingshi.common.CommonResource;
import com.xingshi.entity.BaseRecImageAndTextBean;
import com.xingshi.home.adapter.GoodChoiceRecAdapter;
import com.xingshi.home.adapter.GoodsRecommendAdapter;
import com.xingshi.home.adapter.HomeTopRecAdapter;
import com.xingshi.module_home.R;
import com.xingshi.mvp.BasePresenter;
import com.xingshi.net.OnDataListener;
import com.xingshi.net.OnMyCallBack;
import com.xingshi.net.OnTripartiteCallBack;
import com.xingshi.net.RetrofitUtil;
import com.xingshi.utils.ClickUtil;
import com.xingshi.utils.LogUtil;
import com.xingshi.utils.MapUtil;
import com.xingshi.utils.MyTimeUtil;
import com.xingshi.utils.OnClearCacheListener;
import com.xingshi.utils.PopUtils;
import com.xingshi.utils.ProcessDialogUtil;
import com.xingshi.utils.SPUtil;
import com.xingshi.view.SelfDialog;
import com.xingshi.view.animation.RotateYTransformer;
import com.facebook.drawee.view.SimpleDraweeView;
import com.stx.xhb.xbanner.XBanner;
import com.stx.xhb.xbanner.transformers.Transformer;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

public class HomePresenter extends BasePresenter<HomeView> {


    private List<String> data = new ArrayList<>();
    private List<View> views = new ArrayList<>();
    private List<BaseRecImageAndTextBean> strings;
    private List<GoodsRecommendBean.DataBean> goodList = new ArrayList<>();
    private List<GoodChoiceBean.DataBean> goodChoiceList = new ArrayList<>();
    private List<BannerBean.RecordsBean> beanList;
    private GoodsRecommendAdapter goodsRecommendAdapter;
    private PagerAdapter mAdapter;
    private List<String> images = new ArrayList<>();

    private String info = "";
    private final int ALI_CODE = 0x1234;

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            if (msg.what == ALI_CODE) {
                Map<String, String> map = (Map<String, String>) msg.obj;
                String resultStatus = map.get("resultStatus");
                String result = map.get("result");
                String memo = map.get("memo");
                if ("9000".equals(resultStatus)) {
                    Toast.makeText(mContext, "支付成功", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(mContext, "支付失败", Toast.LENGTH_SHORT).show();
                }
            }
        }

    };

    public HomePresenter(Context context) {
        super(context);
    }

    @Override
    protected void onViewDestroy() {

    }

    public void setViewSingleLine() {
        if (data != null || data.size() != 0) {
            data.clear();
        }
        data.add("王**获得了5.2元佣金");
        data.add("李**获得了3.6元佣金");
        data.add("白**获得了0.48元佣金");
        data.add("崔**获得了10.5元佣金");
        data.add("谷**获得了15.1元佣金");
        data.add("张**获得了1.19元佣金");
        data.add("赵**获得了26.02元佣金");
        data.add("孙**获得了10.8元佣金");
        data.add("武**获得了10.8元佣金");
        data.add("乾**获得了10.8元佣金");
        data.add("宋**获得了10.8元佣金");
        views.clear();
        for (int i = 0; i < data.size(); i++) {
            //设置滚动的单个布局
            LinearLayout moreView = (LinearLayout) LayoutInflater.from(mContext).inflate(R.layout.item_home_marquee_view, null);
            //初始化布局的控件
            TextView marqueeMessage = moreView.findViewById(R.id.marquee_message);

            //进行对控件赋值
            marqueeMessage.setText(data.get(i));

            //添加到循环滚动数组里面去
            views.add(moreView);
            if (getView() != null) {
                getView().lodeMarquee(views);
            }
        }
    }

    public void setXBanner(final XBanner homeXbanner, final ImageView homeTopBg) {
        //轮播图
        Observable<ResponseBody> observable = RetrofitUtil.getInstance().getApi(CommonResource.BASEURL_9005).getDataWithout(CommonResource.HOMEADVERTISETK);
        RetrofitUtil.getInstance().toSubscribe(observable, new OnMyCallBack(new OnDataListener() {
            @Override
            public void onSuccess(String result, String msg) {
                LogUtil.e("homePresenterResult轮播图---------->" + result);
                BannerBean records = JSON.parseObject(result, BannerBean.class);

                if (records != null) {
                    if (records.getRecords() != null) {
                        beanList = records.getRecords();
                        homeXbanner.setBannerData(beanList);
                        Glide.with(mContext).load(beanList.get(0).getPicBackUrl()).into(homeTopBg);
                        homeXbanner.loadImage(new XBanner.XBannerAdapter() {
                            @Override
                            public void loadBanner(XBanner banner, Object model, View view, int position) {
                                RequestOptions requestOptions = RequestOptions.centerCropTransform();
                                Glide.with(mContext).load(((BannerBean.RecordsBean) model).getXBannerUrl())
                                        .apply(requestOptions)
                                        .transform(new RoundedCorners((int) mContext.getResources().getDimension(R.dimen.dp_10)))
                                        .into((ImageView) view);

                            }
                        });
                        // 设置XBanner的页面切换特效
                        homeXbanner.setPageTransformer(Transformer.Default);

                        // 设置XBanner页面切换的时间，即动画时长
                        homeXbanner.setPageChangeDuration(1000);

                        //banner切换image也切换
                        homeXbanner.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                            @Override
                            public void onPageScrolled(int i, float v, int i1) {

                            }

                            @Override
                            public void onPageSelected(int i) {
//                        homeTopBg.setImageURI(Uri.parse(beanList.get(i).getPicBackUrl()));
                                Glide.with(mContext).load(beanList.get(i).getPicBackUrl()).into(homeTopBg);

                            }

                            @Override
                            public void onPageScrollStateChanged(int i) {

                            }
                        });
                        //监听广告 item 的单击事件
                        homeXbanner.setOnItemClickListener(new XBanner.OnItemClickListener() {
                            @Override
                            public void onItemClick(XBanner banner, Object model, View view, int position) {
//                                Toast.makeText(mContext, "点击了第" + position + "图片", Toast.LENGTH_SHORT).show();
                            }
                        });
                    } else {
                        LogUtil.e("轮播图数据为空");
                    }

                } else {
                    LogUtil.e("轮播图数据为空");
                }
            }

            @Override
            public void onError(String errorCode, String errorMsg) {
                LogUtil.e("homePresenterErrorMsg轮播图---------->" + errorMsg);
            }

        }));


    }

    public void setZhongXBanner(final ViewPager homeZhongXbanner) {

        Observable<ResponseBody> dataWithout = RetrofitUtil.getInstance().getApi(CommonResource.BASEURL_9005).getDataWithout(CommonResource.HOMEADVERTISEBOTTOM);
        RetrofitUtil.getInstance().toSubscribe(dataWithout, new OnMyCallBack(new OnDataListener() {
            @Override
            public void onSuccess(String result, String msg) {
                LogUtil.e("HomePresenterResult" + result);
                try {
                    ZhongXBannerBean zhongXBannerBean = JSON.parseObject(result, new TypeReference<ZhongXBannerBean>() {
                    }.getType());
                    if (zhongXBannerBean != null) {
                        for (int i = 0; i < zhongXBannerBean.getRecords().size(); i++) {
                            images.add(zhongXBannerBean.getRecords().get(i).getPicUrl());
                        }
                        homeZhongXbanner.setPageMargin(16);
                        homeZhongXbanner.setOffscreenPageLimit(3);
                        homeZhongXbanner.setPageTransformer(true, new RotateYTransformer());
                        if (images.size() > 0) {
                            homeZhongXbanner.setAdapter(mAdapter = new PagerAdapter() {
                                @Override
                                public Object instantiateItem(ViewGroup container, int position) {
                                    SimpleDraweeView view = new SimpleDraweeView(mContext);
                                    view.setScaleType(SimpleDraweeView.ScaleType.FIT_XY);
                                    final int realPosition = getRealPosition(position);
                                    view.setImageURI(Uri.parse(images.get(realPosition)));
                                    container.addView(view);
                                    view.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            if (realPosition == 0) {
                                                ARouter.getInstance().build("/mine/invite_friends").navigation();
                                            } else if (realPosition == 1) {
                                                if (!TextUtils.isEmpty(SPUtil.getToken())) {
                                                    ARouter.getInstance().build("/module_home/PunchSignActivity").navigation();
                                                } else {
                                                    //是否登录
                                                    PopUtils.isLogin(mContext);
                                                }
                                            } else {

                                            }
                                        }
                                    });
                                    return view;
                                }


                                @Override
                                public int getItemPosition(Object object) {
                                    return POSITION_NONE;
                                }

                                @Override
                                public void destroyItem(ViewGroup container, int position, Object object) {
                                    container.removeView((View) object);
                                }

                                @Override
                                public int getCount() {
                                    return Integer.MAX_VALUE;
                                }

                                @Override
                                public boolean isViewFromObject(View view, Object o) {
                                    return view == o;
                                }

                                //
                                @Override
                                public void startUpdate(ViewGroup container) {
                                    super.startUpdate(container);
                                    ViewPager viewPager = (ViewPager) container;
                                    int position = viewPager.getCurrentItem();
                                    if (position == 0) {
                                        position = getFirstItemPosition();
                                    } else if (position == getCount() - 1) {
                                        position = getLastItemPosition();
                                    }
                                    viewPager.setCurrentItem(position, false);

                                }

                                //
                                private int getRealCount() {
                                    return images.size();
                                }

                                //
                                private int getRealPosition(int position) {
                                    return getRealCount() == 0 ? 0 : position % getRealCount();
                                }

                                //
                                private int getFirstItemPosition() {
                                    return getRealCount() == 0 ? 0 : (Integer.MAX_VALUE / getRealCount() / 2 * getRealCount());
                                }

                                private int getLastItemPosition() {
                                    return getRealCount() == 0 ? 0 : (Integer.MAX_VALUE / getRealCount() / 2 * getRealCount() - 1);
                                }
                            });
                        }
                        homeZhongXbanner.setCurrentItem(Integer.MAX_VALUE / 2 - (Integer.MAX_VALUE / 2 % images.size()));//设置首个轮播显示的位置   实现左右滑动 且首页面对应的是第一个数据
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(String errorCode, String errorMsg) {
                LogUtil.e("HomePresenterErrorMsg" + errorMsg);
            }
        }));


    }

    //店铺
    public void setRec(RecyclerView homeTopRec, final SeekBar homeSlideIndicatorPoint) {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, 2, LinearLayoutManager.HORIZONTAL, false);
        homeTopRec.setLayoutManager(gridLayoutManager);

        strings = new ArrayList<>();
        strings.add(new BaseRecImageAndTextBean("淘宝", R.drawable.icon_yb));//0
        strings.add(new BaseRecImageAndTextBean("淘抢购", R.drawable.icon_tqg));//1
        strings.add(new BaseRecImageAndTextBean("拼多多", R.drawable.icon_pdd));//2
//        strings.add(new BaseRecImageAndTextBean("今日免单", R.drawable.icon_miandan1));//3
        strings.add(new BaseRecImageAndTextBean("福利中心", R.drawable.icon_cpzx));//3
//        strings.add(new BaseRecImageAndTextBean("商城", R.drawable.icon_shangcheng1));
        strings.add(new BaseRecImageAndTextBean("京东", R.drawable.icon_jd));//4
        strings.add(new BaseRecImageAndTextBean("礼包", R.drawable.icon_fjxd));//5
        strings.add(new BaseRecImageAndTextBean("天猫", R.drawable.icon_tm));//6
        strings.add(new BaseRecImageAndTextBean("9.9包邮", R.drawable.icon_99by));//7
        strings.add(new BaseRecImageAndTextBean("聚划算", R.drawable.icon_jhs));//8
        strings.add(new BaseRecImageAndTextBean("打卡签到", R.drawable.icon_dkqd));//9

        HomeTopRecAdapter homeTopRecAdapter = new HomeTopRecAdapter(mContext, strings, R.layout.item_home_top_rec);
        homeTopRec.setAdapter(homeTopRecAdapter);

        homeSlideIndicatorPoint.setPadding(0, 0, 0, 0);
        homeSlideIndicatorPoint.setThumbOffset(0);
//        //显示区域的高度。
//        int extent = homeTopRec.computeHorizontalScrollExtent();
//        //整体的高度，注意是整体，包括在显示区域之外的。
//        int range = homeTopRec.computeHorizontalScrollRange();
//        //已经向下滚动的距离，为0时表示已处于顶部。
//        int offset = homeTopRec.computeHorizontalScrollOffset();

        homeTopRec.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                //显示区域的高度。
                int extent = recyclerView.computeHorizontalScrollExtent();
                //整体的高度，注意是整体，包括在显示区域之外的。
                int range = recyclerView.computeHorizontalScrollRange();
                //已经向下滚动的距离，为0时表示已处于顶部。
                int offset = recyclerView.computeHorizontalScrollOffset();
                LogUtil.e("dx------" + range + "****" + extent + "****" + offset);
                //此处获取seekbar的getThumb，就是可以滑动的小的滚动游标
                GradientDrawable gradientDrawable = (GradientDrawable) homeSlideIndicatorPoint.getThumb();
                //根据列表的个数，动态设置游标的大小，设置游标的时候，progress进度的颜色设置为和seekbar的颜色设置的一样的，所以就不显示进度了。
                gradientDrawable.setSize(extent / (int) (strings.size() / 0.7), 6);
                //设置可滚动区域
                homeSlideIndicatorPoint.setMax((int) (range - extent));
                if (dx == 0) {
                    homeSlideIndicatorPoint.setProgress(0);
                } else if (dx > 0) {
//                    int ss = (int)(tt/2.3f);
                    homeSlideIndicatorPoint.setProgress(offset);
                } else if (dx < 0) {
                    homeSlideIndicatorPoint.setProgress(offset);
                }
            }
        });

        homeTopRecAdapter.setOnItemClick(new MyRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerView parent, View view, int position) {
                //0淘宝  2 拼多多 3京东 6天猫
                if (position == 0 || position == 2 || position == 4 || position == 6) {
                    ARouter.getInstance().build("/module_home/SecondaryDetailsActivity")
                            .withString("type", position + "")
                            .navigation();
                } else if (position == 3) {
                    if (TextUtils.isEmpty(SPUtil.getToken())) {
                        PopUtils.isLogin(mContext);
                    } else {
                        ARouter.getInstance().build("/module_wang_yi_hai/task_list/YWelfareCenterActivity").navigation();
                    }
//                    ARouter.getInstance().build("/module_home/FreeChargeActivity").navigation();
                } else if (position == 9) {
                    ARouter.getInstance().build("/module_home/PunchSignActivity").navigation();
                } else if (position == 10) {
                } else if (position == 1) {
                    ARouter.getInstance().build("/module_home/UniversalListActivity").withInt("position", 1).navigation();
                } else if (position == 7) {
                    ARouter.getInstance().build("/module_home/UniversalListActivity").withInt("position", 2).navigation();
                } else if (position == 8) {
                    ARouter.getInstance().build("/module_home/UniversalListActivity").withInt("position", 3).navigation();
                } else if (position == 5) {
                    if (TextUtils.isEmpty(SPUtil.getToken())) {
                        PopUtils.isLogin(mContext);
                    } else {
                        if (ClickUtil.isFastClick()) {
                            ProcessDialogUtil.showProcessDialog(mContext);
                            Observable<ResponseBody> headWithout = RetrofitUtil.getInstance().getApi(CommonResource.BASEURL_9006).getHeadWithout(CommonResource.ACTIVATION, SPUtil.getToken());
                            RetrofitUtil.getInstance().toSubscribe(headWithout, new OnMyCallBack(new OnDataListener() {
                                @Override
                                public void onSuccess(String result, String msg) {
                                    LogUtil.e("激活用户" + result);
                                    JSONObject jsonObject = JSON.parseObject(result);
                                    String realName = jsonObject.getString("realName");
                                    String editStatus = jsonObject.getString("editStatus");

                                    if ("0".equals(realName)) {
                                        //未实名
                                        ARouter.getInstance().build("/module_wang_yi_hai/CertificationActivity").withInt("type", 0).navigation();
                                    } else {
                                        //已实名
                                        if ("0".equals(editStatus)) {
                                            ARouter.getInstance().build("/module_wang_yi_hai/y_personal_details/YPersonalDetailsActivity").navigation();
                                        } else {
                                            ARouter.getInstance().build("/module_wang_yi_hai/YMainActivity").navigation();
                                        }
                                    }
                                }

                                @Override
                                public void onError(String errorCode, String errorMsg) {
                                    LogUtil.e("激活用户error" + errorMsg);
                                    Toast.makeText(mContext, errorMsg, Toast.LENGTH_SHORT).show();
                                    if ("1".equals(errorCode)) {
                                        final SelfDialog selfDialog = new SelfDialog(mContext);
                                        selfDialog.setTitle("提示");
                                        selfDialog.setMessage("当前您账户余额低于1元，请充值激活!");
                                        selfDialog.setNoOnclickListener("确认", new SelfDialog.onNoOnclickListener() {
                                            @Override
                                            public void onNoClick() {
                                                pop();
                                                selfDialog.dismiss();
                                            }
                                        });

                                        selfDialog.setYesOnclickListener("取消", new SelfDialog.onYesOnclickListener() {
                                            @Override
                                            public void onYesClick() {
                                                PopUtils.setTransparency(mContext, 1f);
                                                selfDialog.dismiss();
                                            }
                                        });
                                        PopUtils.setTransparency(mContext, 0.3f);
                                        selfDialog.show();
                                    }
                                }
                            }));
                        }
                    }
                }
            }
        });
    }

    //优选
    public void setGoodChoiceRec(final RecyclerView homeGoodChoiceRec) {

        Map map = MapUtil.getInstance().addParms("sale_type", 1).build();
        Observable data = RetrofitUtil.getInstance().getApi(CommonResource.BASEURL_9001).getData(CommonResource.TBKGOODSSALESLIST, map);
        RetrofitUtil.getInstance().toSubscribe(data, new OnTripartiteCallBack(new OnDataListener() {
            @Override
            public void onSuccess(String result, String msg) {
                LogUtil.e("优选：" + result);
                GoodChoiceBean goodChoiceBean = JSON.parseObject(result, new TypeReference<GoodChoiceBean>() {
                }.getType());
//                GoodChoiceBean goodChoiceBean = JSON.parseObject(result, GoodChoiceBean.class);
                LogUtil.e("goodChoiceBean" + goodChoiceBean);
                if (goodChoiceBean != null && goodChoiceBean.getData() != null && goodChoiceBean.getData().size() != 0) {
                    goodChoiceList.clear();
                    goodChoiceList.addAll(goodChoiceBean.getData());
                    GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, 1, LinearLayoutManager.HORIZONTAL, false);
                    homeGoodChoiceRec.setLayoutManager(gridLayoutManager);
                    GoodChoiceRecAdapter goodChoiceRecAdapter = new GoodChoiceRecAdapter(mContext, goodChoiceList, R.layout.item_home_good_choice_rec);
                    homeGoodChoiceRec.setAdapter(goodChoiceRecAdapter);
                    goodChoiceRecAdapter.setOnItemClick(new MyRecyclerAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(RecyclerView parent, View view, int position) {
                            if (!TextUtils.isEmpty(SPUtil.getToken())) {
                                String startTime = MyTimeUtil.date2String(goodChoiceList.get(position).getCouponstarttime() + "000");
                                String endTime = MyTimeUtil.date2String(goodChoiceList.get(position).getCouponendtime() + "000");
                                ARouter.getInstance().build("/module_classify/TBCommodityDetailsActivity")
                                        .withString("para", goodChoiceList.get(position).getItemid())
                                        .withString("shoptype", "1")
                                        .withDouble("youhuiquan", Double.valueOf(goodChoiceList.get(position).getCouponmoney()))
                                        .withString("coupon_start_time", startTime)
                                        .withString("coupon_end_time", endTime)
                                        .withString("commission_rate", goodChoiceList.get(position).getTkrates())
                                        .withInt("type", 0)
                                        .navigation();
                            } else {
                                //是否登录
                                PopUtils.isLogin(mContext);
                            }

                        }
                    });
                } else {
                    LogUtil.e("优选数据为空");
                }
            }

            @Override
            public void onError(String errorCode, String errorMsg) {
                LogUtil.e("homePresenterErrorMsg---------->" + errorMsg);
            }
        }));


    }

    //推荐
    public void setBottomRec(final int nextPage, final RecyclerView homeBottomRec) {
        Map map = MapUtil.getInstance().addParms("page", nextPage).addParms("pagesize", 20).build();
        Observable data = RetrofitUtil.getInstance().getApi(CommonResource.BASEURL_9001).getData(CommonResource.TBKGOODSPRODUCTS, map);
        RetrofitUtil.getInstance().toSubscribe(data, new OnTripartiteCallBack(new OnDataListener() {
            @Override
            public void onSuccess(String result, String msg) {
                LogUtil.e("homePresenterResult---------->" + result);
                getView().refreshSuccess();
                GoodsRecommendBean goodsRecommendBean = JSON.parseObject(result, new TypeReference<GoodsRecommendBean>() {
                }.getType());
                LogUtil.e("推荐数据" + goodsRecommendBean);
                if (goodsRecommendBean.getData() != null && goodsRecommendBean.getData().size() != 0) {
                    if (nextPage == 5) {
                        goodList.clear();
                    }
                    goodList.addAll(goodsRecommendBean.getData());

                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
                    homeBottomRec.setLayoutManager(linearLayoutManager);
                    if (goodsRecommendAdapter == null) {
                        goodsRecommendAdapter = new GoodsRecommendAdapter(mContext, goodList, R.layout.item_base_rec);
                        homeBottomRec.setAdapter(goodsRecommendAdapter);
                    } else {
                        goodsRecommendAdapter.notifyDataSetChanged();
                    }

                    goodsRecommendAdapter.setOnItemClick(new MyRecyclerAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(RecyclerView parent, View view, int position) {
                            String startTime = MyTimeUtil.date2String(goodList.get(position).getCoupon_start_time() + "000");
                            String endTime = MyTimeUtil.date2String(goodList.get(position).getCoupon_end_time() + "000");
                            if (!TextUtils.isEmpty(SPUtil.getToken())) {
                                ARouter.getInstance().build("/module_classify/TBCommodityDetailsActivity")
                                        .withString("para", goodList.get(position).getItem_id())
                                        .withString("shoptype", goodList.get(position).getUser_type())
                                        .withDouble("youhuiquan", Double.valueOf(goodList.get(position).getCoupon_amount()))
                                        .withString("coupon_start_time", startTime)
                                        .withString("coupon_end_time", endTime)
                                        .withString("commission_rate", goodList.get(position).getCommission_rate())
                                        .withInt("type", 0)
                                        .navigation();
                            } else {
                                //是否登录
                                PopUtils.isLogin(mContext);
                            }
                        }
                    });

                } else {
                    LogUtil.e("推荐数据为空");
                }

            }

            @Override
            public void onError(String errorCode, String errorMsg) {
                LogUtil.e("homePresenterErrorMsg---------->" + errorMsg);
                getView().refreshSuccess();
            }
        }));

    }

    private void pop() {
        PopUtils.topUp(mContext, new OnClearCacheListener() {
            @Override
            public void setOnClearCache(PopupWindow pop, View confirm) {
                final RadioButton butWeChat = confirm.findViewById(R.id.pop_top_up_but_weChat);
                final EditText upEdit = confirm.findViewById(R.id.pop_top_up_edit2);
                TextView topUp = confirm.findViewById(R.id.pop_top_up_top_up);

                topUp.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (ClickUtil.isFastClick()) {
                            if (StringUtil.isBlank(upEdit.getText().toString())) {
                                Toast.makeText(mContext, "请输入充值金额", Toast.LENGTH_SHORT).show();
                            } else if ("0".equals(upEdit.getText().toString())) {
                                Toast.makeText(mContext, "充值金额不能为0元", Toast.LENGTH_SHORT).show();
                            } else {
                                if (butWeChat.isChecked()) {
                                    //微信支付
                                    weChat(upEdit.getText().toString());
                                } else {
                                    //支付宝
                                    aliPay(upEdit.getText().toString());
                                }
                            }
                        }

                    }
                });

            }
        });
    }

    //微信支付
    public void weChat(String price) {
        final IWXAPI api = WXAPIFactory.createWXAPI(mContext, CommonResource.WXAPPID, false);

        Map map = MapUtil.getInstance().addParms("totalAmount", price).addParms("userCode", SPUtil.getUserCode()).build();
        String jsonString = JSON.toJSONString(map);
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonString);
        Observable<ResponseBody> responseBodyObservable = RetrofitUtil.getInstance().getApi(CommonResource.BASEURL_9004).postDataWithBody(CommonResource.WXPAYRECHARGE, body);
        RetrofitUtil.getInstance().toSubscribe(responseBodyObservable, new OnTripartiteCallBack(new OnDataListener() {
            @Override
            public void onSuccess(String result, String msg) {
                LogUtil.e("微信支付-------------->" + result);
                try {

                    WeChatPayBean payBean = JSON.parseObject(result, WeChatPayBean.class);

                    PayReq request = new PayReq();
                    request.appId = payBean.getAppid();
                    request.partnerId = payBean.getPartnerid();
                    request.prepayId = payBean.getPrepayid();
                    request.packageValue = "Sign=WXPay";
                    request.nonceStr = payBean.getNoncestr();
                    request.timeStamp = payBean.getTimestamp();
                    request.sign = payBean.getSign();

                    api.sendReq(request);
                    SPUtil.addParm("wxpay", "14");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(String errorCode, String errorMsg) {

            }
        }));
    }


    //支付宝支付
    public void aliPay(String price) {
        Map map = MapUtil.getInstance().addParms("totalAmount", price).addParms("userCode", SPUtil.getUserCode()).build();
//        String jsonString = JSON.toJSONString(map);
//        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonString);
        Observable observable = RetrofitUtil.getInstance().getApi(CommonResource.BASEURL_9004).postData(CommonResource.ALIPAYRECHARGE, map);
        RetrofitUtil.getInstance().toSubscribe(observable, new OnMyCallBack(new OnDataListener() {
            @Override
            public void onSuccess(String result, String msg) {
                LogUtil.e("支付宝：" + result);
                AliPayBean aliPayBean = JSON.parseObject(result, AliPayBean.class);
                info = aliPayBean.getBody();
                Thread thread = new Thread(payRunnable);
                thread.start();
            }

            @Override
            public void onError(String errorCode, String errorMsg) {
            }
        }));
    }

    private Runnable payRunnable = new Runnable() {

        @Override
        public void run() {
            PayTask alipay = new PayTask((Activity) mContext);
            Map<String, String> result = alipay.payV2(info, true);

            Message msg = new Message();
            msg.what = ALI_CODE;
            msg.obj = result;
            mHandler.sendMessage(msg);
        }
    };

}
