package com.xingshi.y_task;

import android.content.Context;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.xingshi.adapter.MyRecyclerAdapter;
import com.xingshi.bean.YTaskBean;
import com.xingshi.common.CommonResource;
import com.xingshi.mvp.BasePresenter;
import com.xingshi.net.OnDataListener;
import com.xingshi.net.OnMyCallBack;
import com.xingshi.net.RetrofitUtil;
import com.xingshi.utils.LogUtil;
import com.xingshi.utils.MapUtil;
import com.xingshi.utils.OnClearCacheListener;
import com.xingshi.utils.SPUtil;
import com.xingshi.utils.YPopUtil;
import com.xingshi.y_main.R;
import com.xingshi.y_task.adapter.YTaskAdapter;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.ResponseBody;

public class YTaskPresenter extends BasePresenter<YTaskView> {

    public YTaskPresenter(Context context) {
        super(context);
    }

    @Override
    protected void onViewDestroy() {

    }

    public void initData() {
        Observable<ResponseBody> dataWithout = RetrofitUtil.getInstance().getApi(CommonResource.BASEURL_9006).getDataWithout(CommonResource.TASKALL);
        RetrofitUtil.getInstance().toSubscribe(dataWithout, new OnMyCallBack(new OnDataListener() {
            @Override
            public void onSuccess(String result, String msg) {
                LogUtil.e("任务列表" + result);
                if (result != null) {
                    final List<YTaskBean> list = JSON.parseArray(result, YTaskBean.class);

                    YTaskAdapter yTaskAdapter = new YTaskAdapter(mContext, list, R.layout.item_y_task_rec);
                    if (getView() != null) {
                        getView().loadAdapter(yTaskAdapter);
                    }
                    yTaskAdapter.setViewOnClickListener(new MyRecyclerAdapter.ViewOnClickListener() {
                        @Override
                        public void ViewOnClick(View view, final int index) {
                            view.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    YPopUtil.conversionGoods(mContext, list.get(index).getConsume(), new OnClearCacheListener() {
                                        @Override
                                        public void setOnClearCache(final PopupWindow pop, View confirm) {
                                            confirm.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    Map id = MapUtil.getInstance().addParms("id", list.get(index).getId()).build();
                                                    Observable head = RetrofitUtil.getInstance().getApi(CommonResource.BASEURL_9006).getHead(CommonResource.BUYTASK, id, SPUtil.getToken());
                                                    RetrofitUtil.getInstance().toSubscribe(head, new OnMyCallBack(new OnDataListener() {
                                                        @Override
                                                        public void onSuccess(String result, String msg) {
                                                            pop.dismiss();
                                                            YPopUtil.getTheGiftBag(mContext, list.get(index).getTaskLevel());
                                                        }

                                                        @Override
                                                        public void onError(String errorCode, String errorMsg) {
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
                    });
                }
            }

            @Override
            public void onError(String errorCode, String errorMsg) {
                LogUtil.e("任务列表error" + errorMsg);
            }
        }));

    }
}
