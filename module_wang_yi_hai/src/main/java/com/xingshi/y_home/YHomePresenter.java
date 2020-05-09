package com.xingshi.y_home;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.xingshi.adapter.MyRecyclerAdapter;
import com.xingshi.bean.BannerBean;
import com.xingshi.bean.YHomeBottomBean;
import com.xingshi.common.CommonResource;
import com.xingshi.entity.BaseRecImageAndTextBean;
import com.xingshi.mvp.BasePresenter;
import com.xingshi.net.OnDataListener;
import com.xingshi.net.OnMyCallBack;
import com.xingshi.net.RetrofitUtil;
import com.xingshi.utils.LogUtil;
import com.xingshi.utils.MapUtil;
import com.xingshi.y_home.y_goods_details.YGoodsDetailsActivity;
import com.xingshi.y_home.adapter.YHomeBottomAdapter;
import com.xingshi.y_main.R;
import com.kongzue.tabbar.Tab;
import com.kongzue.tabbar.TabBarView;
import com.kongzue.tabbar.interfaces.OnTabChangeListener;
import com.stx.xhb.xbanner.XBanner;
import com.stx.xhb.xbanner.transformers.Transformer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.ResponseBody;

public class YHomePresenter extends BasePresenter<YHomeView> {

    private List<BannerBean.RecordsBean> beanList;
    private List<BaseRecImageAndTextBean> strings;
    private List<YHomeBottomBean.DataBean> dataBeanList = new ArrayList<>();
    private YHomeBottomAdapter yHomeBottomAdapter;

    public YHomePresenter(Context context) {
        super(context);
    }

    @Override
    protected void onViewDestroy() {

    }

    public void setXBanner(final XBanner homeXbanner) {
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

                        //监听广告 item 的单击事件
                        homeXbanner.setOnItemClickListener(new XBanner.OnItemClickListener() {
                            @Override
                            public void onItemClick(XBanner banner, Object model, View view, int position) {
//                                Toast.makeText(mContext, "点击了第" + position + "图片", Toast.LENGTH_SHORT).show();
                            }
                        });
                    } else {
                        LogUtil.e("数据为空");
                    }

                } else {
                    LogUtil.e("数据为空");
                }
            }

            @Override
            public void onError(String errorCode, String errorMsg) {
                LogUtil.e("homePresenterErrorMsg轮播图---------->" + errorMsg);
            }

        }));

    }

    public void setRecTop(TabBarView yHomeTop) {

        List<Tab> tabs2 = new ArrayList<>();
        tabs2.add(new Tab(mContext, "品牌榜单", R.drawable.pinpai));
        tabs2.add(new Tab(mContext, "服饰专区", R.drawable.fushi));
        tabs2.add(new Tab(mContext, "数码电子", R.drawable.shuma));
        tabs2.add(new Tab(mContext, "生活超市", R.drawable.chaoshi));
        tabs2.add(new Tab(mContext, "生鲜专送", R.drawable.shengxian));
        yHomeTop.setTab(tabs2);

        yHomeTop.setOnTabChangeListener(new OnTabChangeListener() {
            @Override
            public void onTabChanged(View v, int index) {
                Toast.makeText(mContext, "尽情期待", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void setRecBottom(final int page) {
        Map map = MapUtil.getInstance().addParms("pageNum", page).addParms("saleDesc", "1").addParms("isExchange", 1).build();
        Observable<ResponseBody> observable = RetrofitUtil.getInstance().getApi(CommonResource.BASEURL_9001).getData(CommonResource.HOTNEWSEARCH, map);
        RetrofitUtil.getInstance().toSubscribe(observable, new OnMyCallBack(new OnDataListener() {
            @Override
            public void onSuccess(String result, String msg) {
                LogUtil.e("区块商品：" + result);
                getView().finishRefresh();
                YHomeBottomBean yHomeBottomBean = JSON.parseObject(result, YHomeBottomBean.class);
                if (yHomeBottomBean != null) {
                    if (1 == page) {
                        dataBeanList.clear();
                    }
                    dataBeanList.addAll(yHomeBottomBean.getData());
                    if (yHomeBottomAdapter == null) {
                        yHomeBottomAdapter = new YHomeBottomAdapter(mContext, dataBeanList, R.layout.item_yhome_bottom_rec);
                        if (getView() != null) {
                            getView().loadAdapter(yHomeBottomAdapter);
                        }
                    } else {
                        yHomeBottomAdapter.notifyDataSetChanged();
                    }
                    yHomeBottomAdapter.setOnItemClick(new MyRecyclerAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(RecyclerView parent, View view, int position) {
                            Intent intent = new Intent(mContext, YGoodsDetailsActivity.class);
                            intent.putExtra("id", dataBeanList.get(position).getId());
                            mContext.startActivity(intent);
                        }
                    });
                }
            }

            @Override
            public void onError(String errorCode, String errorMsg) {
                LogUtil.e("区块商品：errorMsg" + errorMsg);
                getView().finishRefresh();
            }
        }));
    }

}
