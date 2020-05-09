package com.xingshi.shop_home;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.xingshi.adapter.MyRecyclerAdapter;
import com.xingshi.adapter.PopCouponAdapter;
import com.xingshi.bean.ShopHomeBean;
import com.xingshi.bean.ShopHomeClassBean;
import com.xingshi.bean.UserCouponBean;
import com.xingshi.common.CommonResource;
import com.xingshi.mvp.BasePresenter;
import com.xingshi.net.OnDataListener;
import com.xingshi.net.OnMyCallBack;
import com.xingshi.net.OnTripartiteCallBack;
import com.xingshi.net.RetrofitUtil;
import com.xingshi.shop_home.adapter.ShopHomeVPAdapter;
import com.xingshi.shop_home.treasure.ShopTreasureFragment;
import com.xingshi.utils.LogUtil;
import com.xingshi.utils.MapUtil;
import com.xingshi.utils.OnClearCacheListener;
import com.xingshi.utils.OnPopAdapterListener;
import com.xingshi.utils.PopUtil;
import com.xingshi.utils.PopUtils;
import com.xingshi.utils.SPUtil;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;

public class ShopHomePresneter extends BasePresenter<ShopHomeView> {
    //    private String[] titleArr = {"全部商品", "上衣", "裤子", "外套", "休闲裤", "运动裤"};
    private List<Fragment> fragmentList = new ArrayList<>();
    private List<ShopHomeClassBean> shopHomeClassBeans;
    private List<String> nameList = new ArrayList<>();

    public ShopHomePresneter(Context context) {
        super(context);
    }

    @Override
    protected void onViewDestroy() {

    }

    public void initView(String sellerId) {
        Map map = MapUtil.getInstance().addParms("id", Long.valueOf(sellerId)).addParms("userCode", SPUtil.getUserCode()).build();
        Observable data = RetrofitUtil.getInstance().getApi(CommonResource.BASEURL_9003).getData(CommonResource.GETSELLERBYID, map);
        RetrofitUtil.getInstance().toSubscribe(data, new OnTripartiteCallBack(new OnDataListener() {
            @Override
            public void onSuccess(String result, String msg) {
                LogUtil.e("商家详情---------->" + result);
                if (result != null) {
                    ShopHomeBean shopHomeBean = JSON.parseObject(result, ShopHomeBean.class);
                    if (shopHomeBean != null) {
                        getView().initView(shopHomeBean);
                    }
                }

            }

            @Override
            public void onError(String errorCode, String errorMsg) {
                LogUtil.e("商家详情---------->" + errorMsg);

            }
        }));
    }


    public void initTabLayout(final TabLayout intoShopTab, final String sellerId, String sellerCategory) {
        Map map = MapUtil.getInstance().addParms("sellerCategoryId", sellerCategory).build();
        Observable data = RetrofitUtil.getInstance().getApi(CommonResource.BASEURL_9001).getData(CommonResource.GETNETSELLERCATEGORY, map);
        RetrofitUtil.getInstance().toSubscribe(data, new OnMyCallBack(new OnDataListener() {
            @Override
            public void onSuccess(String result, String msg) {
                LogUtil.e("商家分类------------>" + result);
                if (result != null) {
                    shopHomeClassBeans = JSON.parseArray(result, ShopHomeClassBean.class);
                    for (int i = 0; i < shopHomeClassBeans.size(); i++) {
                        nameList.add(shopHomeClassBeans.get(i).getName());
                        intoShopTab.addTab(intoShopTab.newTab().setText(shopHomeClassBeans.get(i).getName()));
                        fragmentList.add(new ShopTreasureFragment(sellerId, shopHomeClassBeans.get(i).getId()));
                    }
                    ShopHomeVPAdapter intoShopVPAdapter = new ShopHomeVPAdapter(((FragmentActivity) mContext).getSupportFragmentManager(), fragmentList, nameList);
                    getView().loadVP(intoShopVPAdapter);
                }

            }

            @Override
            public void onError(String errorCode, String errorMsg) {
                LogUtil.e("商家分类------------>" + errorMsg);

            }
        }));


    }

    public void showMore(ImageView more) {
        PopUtil.showMore(mContext, more, new OnClearCacheListener() {
            @Override
            public void setOnClearCache(final PopupWindow pop, View confirm) {
                confirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        share();
                        pop.dismiss();
                    }
                });
            }
        });
    }

    public void youhuiquan(String sellerID) {
        Map map = MapUtil.getInstance().addParms("platform", "2").addParms("sellerId", sellerID).build();
        Observable observable = RetrofitUtil.getInstance().getApi(CommonResource.BASEURL_9003).getData(CommonResource.COUPON_KELING, map);
        RetrofitUtil.getInstance().toSubscribe(observable, new OnMyCallBack(new OnDataListener() {
            @Override
            public void onSuccess(String result, String msg) {
                LogUtil.e("可用优惠券：" + result);
                try {
                    final List<UserCouponBean> couponBeanList = JSON.parseArray(result, UserCouponBean.class);
                    if (couponBeanList != null && couponBeanList.size() > 0) {
                        PopUtils.youhuiquan(mContext, couponBeanList, new OnPopAdapterListener() {
                            @Override
                            public void setOnAdapterListener(PopupWindow popupWindow, final PopCouponAdapter adapter) {
                                adapter.setViewOnClickListener(new MyRecyclerAdapter.ViewOnClickListener() {
                                    @Override
                                    public void ViewOnClick(View view, final int index) {
                                        view.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                Map map = MapUtil.getInstance().addParms("couponID", couponBeanList.get(index).getId()).addParms("userID", SPUtil.getUserCode()).addParms("userNickName", SPUtil.getStringValue(CommonResource.USER_NAME)).build();
                                                Observable observable = RetrofitUtil.getInstance().getApi(CommonResource.BASEURL_9003).getData(CommonResource.LINGCOUPON, map);
                                                RetrofitUtil.getInstance().toSubscribe(observable, new OnMyCallBack(new OnDataListener() {
                                                    @Override
                                                    public void onSuccess(String result, String msg) {
                                                        LogUtil.e("领取：" + result);
                                                        Toast.makeText(mContext, "领取成功", Toast.LENGTH_SHORT).show();
                                                        couponBeanList.get(index).setHas(true);
                                                        adapter.notifyDataSetChanged();
                                                    }

                                                    @Override
                                                    public void onError(String errorCode, String errorMsg) {
                                                        LogUtil.e(errorCode + "------------" + errorMsg);
                                                        Toast.makeText(mContext, errorMsg, Toast.LENGTH_SHORT).show();
                                                    }
                                                }));
                                            }
                                        });
                                    }
                                });
                            }
                        });
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(String errorCode, String errorMsg) {
                LogUtil.e(errorCode + "--------------" + errorMsg);
                Toast.makeText(mContext, errorMsg, Toast.LENGTH_SHORT).show();
            }
        }));
    }

    public void collectShop(String shop_id) {
        Map map = MapUtil.getInstance().addParms("sellerId", shop_id).build();
        Observable observable = RetrofitUtil.getInstance().getApi(CommonResource.BASEURL_4001).getHead(CommonResource.COLLECT_SHOP, map, SPUtil.getToken());
        RetrofitUtil.getInstance().toSubscribe(observable, new OnMyCallBack(new OnDataListener() {
            @Override
            public void onSuccess(String result, String msg) {
                LogUtil.e("收藏商品：" + result + "------------" + msg);
                Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
                getView().isCollect(result);
            }

            @Override
            public void onError(String errorCode, String errorMsg) {
                LogUtil.e("收藏商品失败" + errorCode + "------------" + errorMsg);
                Toast.makeText(mContext, errorMsg, Toast.LENGTH_SHORT).show();
            }
        }));
    }

    private void share() {

    }

    private UMShareListener shareListener = new UMShareListener() {
        @Override
        public void onStart(SHARE_MEDIA share_media) {
            LogUtil.e("start:" + share_media.toString());
        }

        @Override
        public void onResult(SHARE_MEDIA share_media) {
            LogUtil.e("result:" + share_media.toString());
        }

        @Override
        public void onError(SHARE_MEDIA share_media, Throwable throwable) {

        }

        @Override
        public void onCancel(SHARE_MEDIA share_media) {

        }
    };

//    public void isCollect(String shop_id) {
//        Observable<ResponseBody> observable = RetrofitUtil.getInstance().getApi(CommonResource.BASEURL_4001).getHeadWithout(CommonResource.USER_ISCOLLECT + "/" + shop_id, SPUtil.getToken());
//        RetrofitUtil.getInstance().toSubscribe(observable, new OnMyCallBack(new OnDataListener() {
//            @Override
//            public void onSuccess(String result, String msg) {
//                LogUtil.e("是否收藏：" + result);
//                if (getView() != null) {
//                    getView().isCollect(result);
//                }
//            }
//
//            @Override
//            public void onError(String errorCode, String errorMsg) {
//                LogUtil.e(errorCode + "---------------" + errorMsg);
//            }
//        }));
//    }
}
