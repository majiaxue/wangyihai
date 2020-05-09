package com.xingshi.y_mine.y_my_task.take_records;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.xingshi.bean.TakeRecordsBean;
import com.xingshi.common.CommonResource;
import com.xingshi.mvp.BasePresenter;
import com.xingshi.net.OnDataListener;
import com.xingshi.net.OnMyCallBack;
import com.xingshi.net.RetrofitUtil;
import com.xingshi.utils.LogUtil;
import com.xingshi.utils.MapUtil;
import com.xingshi.utils.SPUtil;
import com.xingshi.y_main.R;
import com.xingshi.y_mine.y_my_task.take_records.adapter.TakeRecordsAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;

public class TakeRecordsPresenter extends BasePresenter<TakeRecordsView> {

    private List<TakeRecordsBean.RecordsBean> beanList = new ArrayList<>();
    private TakeRecordsAdapter takeRecordsAdapter;


    public TakeRecordsPresenter(Context context) {
        super(context);
    }

    @Override
    protected void onViewDestroy() {

    }

    public void initView(final int page) {
        Map map = MapUtil.getInstance().addParms("pageNum", page).addParms("pageSize", 10).build();
        Observable head = RetrofitUtil.getInstance().getApi(CommonResource.BASEURL_9006).getHead(CommonResource.PACKAGERECORD, map, SPUtil.getStringValue(CommonResource.TOKEN));
        RetrofitUtil.getInstance().toSubscribe(head, new OnMyCallBack(new OnDataListener() {
            @Override
            public void onSuccess(String result, String msg) {
                LogUtil.e("获取记录" + result);
                getView().finishRefresh();
                TakeRecordsBean takeRecordsBean = JSON.parseObject(result, TakeRecordsBean.class);
                if (1 == page){
                    beanList.clear();
                }
                beanList.addAll(takeRecordsBean.getRecords());
                if (takeRecordsAdapter == null){
                    takeRecordsAdapter = new TakeRecordsAdapter(mContext, beanList, R.layout.item_take_records_rec);
                    getView().loadAdapter(takeRecordsAdapter);
                }else{
                    takeRecordsAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onError(String errorCode, String errorMsg) {
                getView().finishRefresh();
                LogUtil.e("获取记录errorMsg" + errorMsg);
            }
        }));

    }
}
