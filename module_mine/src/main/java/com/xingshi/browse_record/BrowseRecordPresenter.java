package com.xingshi.browse_record;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.alibaba.android.arouter.launcher.ARouter;
import com.alibaba.fastjson.JSON;
import com.xingshi.adapter.MyRecyclerAdapter;
import com.xingshi.bean.MyCollectBean;
import com.xingshi.browse_record.adapter.BrowseRecordAdapter;
import com.xingshi.common.CommonResource;
import com.xingshi.module_mine.R;
import com.xingshi.mvp.BasePresenter;
import com.xingshi.net.OnDataListener;
import com.xingshi.net.OnMyCallBack;
import com.xingshi.net.RetrofitUtil;
import com.xingshi.utils.LogUtil;
import com.xingshi.utils.MapUtil;
import com.xingshi.utils.SPUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;

public class BrowseRecordPresenter extends BasePresenter<BrowseRecordView> {
    private List<MyCollectBean> dataList = new ArrayList<>();
    private BrowseRecordAdapter recordAdapter;

    public BrowseRecordPresenter(Context context) {
        super(context);
    }

    @Override
    protected void onViewDestroy() {

    }

    public void loadData(final int page) {
        Map map = MapUtil.getInstance().addParms("type", "1").addParms("current", page).build();
        Observable observable = RetrofitUtil.getInstance().getApi(CommonResource.BASEURL_4001).getHead(CommonResource.BROWSE_LIST, map, SPUtil.getToken());
        RetrofitUtil.getInstance().toSubscribe(observable, new OnMyCallBack(new OnDataListener() {
            @Override
            public void onSuccess(String result, String msg) {

                LogUtil.e("浏览记录：" + result);
                if (getView() != null) {
                    getView().loadFinish();
                }

                if (result != null) {
                    if (page == 1) {
                        dataList.clear();
                    }
                    dataList.addAll(JSON.parseArray(result, MyCollectBean.class));

                    if (recordAdapter == null) {
                        recordAdapter = new BrowseRecordAdapter(mContext, dataList, R.layout.rv_collection);
                        if (getView() != null) {
                            getView().loadUI(recordAdapter);
                        }
                    } else {
                        recordAdapter.notifyDataSetChanged();
                    }
                }

                if (getView() != null) {
                    getView().loadFinish();
                }
            }

            @Override
            public void onError(String errorCode, String errorMsg) {
                LogUtil.e("浏览记录errorMsg:" + errorMsg);
                if (getView() != null) {
                    getView().loadFinish();
                }
            }
        }));
    }

    public void click() {
        recordAdapter.setOnItemClick(new MyRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerView parent, View view, int position) {
                if (dataList.get(position).getType() == 0) {
                    //淘宝
                    ARouter.getInstance()
                            .build("/module_classify/TBCommodityDetailsActivity")
                            .withString("para", dataList.get(position).getGoodsId() + "")
                            .withString("shoptype", "1")
                            .withString("commission_rate","25")
                            .withString("type","0")
                            .navigation();
                } else if (dataList.get(position).getType() == 2) {
                    //拼多多
                    ARouter.getInstance().build("/module_classify/CommodityDetailsActivity").withString("goods_id", dataList.get(position).getGoodsId() + "").navigation();
                } else {
                    //京东
                    ARouter.getInstance().build("/module_classify/JDCommodityDetailsActivity").withString("skuid", dataList.get(position).getGoodsId() + "").navigation();
                }
            }
        });
    }
}
