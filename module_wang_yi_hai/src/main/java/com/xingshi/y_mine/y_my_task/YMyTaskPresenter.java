package com.xingshi.y_mine.y_my_task;

import android.app.Activity;
import android.content.Context;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.xingshi.adapter.MyRecyclerAdapter;
import com.xingshi.bean.SignInBean;
import com.xingshi.bean.YMyTaskBean;
import com.xingshi.common.CommonResource;
import com.xingshi.entity.EventBusBean;
import com.xingshi.mvp.BasePresenter;
import com.xingshi.net.OnDataListener;
import com.xingshi.net.OnMyCallBack;
import com.xingshi.net.RetrofitUtil;
import com.xingshi.utils.LogUtil;
import com.xingshi.utils.MapUtil;
import com.xingshi.utils.SPUtil;
import com.xingshi.utils.YPopUtil;
import com.xingshi.y_main.R;
import com.xingshi.y_mine.y_my_task.adapter.YMyTaskAdapter;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;

public class YMyTaskPresenter extends BasePresenter<YMyTaskView> {

    private List<YMyTaskBean.RecordsBean> recordsBeanList = new ArrayList<>();
    private YMyTaskAdapter yMyTaskAdapter;

    public YMyTaskPresenter(Context context) {
        super(context);
    }

    @Override
    protected void onViewDestroy() {

    }

    public void initData(final int page) {
        Map build = MapUtil.getInstance().addParms("pageNum", page).addParms("pageSize", 10).build();
        Observable head = RetrofitUtil.getInstance().getApi(CommonResource.BASEURL_9006).getHead(CommonResource.MYTASKLIST, build, SPUtil.getToken());
        RetrofitUtil.getInstance().toSubscribe(head, new OnMyCallBack(new OnDataListener() {
            @Override
            public void onSuccess(String result, final String msg) {
                getView().finishRefresh();
                LogUtil.e("我的任务礼包" + result);
                YMyTaskBean yMyTaskBean = JSON.parseObject(result, new TypeReference<YMyTaskBean>() {
                }.getType());
                if (yMyTaskBean != null) {
                    if (1 == page) {
                        recordsBeanList.clear();
                    }
                    recordsBeanList.addAll(yMyTaskBean.getRecords());
                    if (yMyTaskAdapter == null) {
                        yMyTaskAdapter = new YMyTaskAdapter(mContext, recordsBeanList, R.layout.item_y_task_rec);
                        getView().loadAdapter(yMyTaskAdapter);
                    } else {
                        yMyTaskAdapter.notifyDataSetChanged();
                    }

                    yMyTaskAdapter.setViewOnClickListener(new MyRecyclerAdapter.ViewOnClickListener() {
                        @Override
                        public void ViewOnClick(View view, final int index) {
                            view.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if (recordsBeanList.get(index).getSurplusNumber() != 0) {
                                        //去完成
                                        EventBus.getDefault().post(new EventBusBean(CommonResource.HOME));
                                        ((Activity) mContext).finish();
                                    } else {
                                        int day = recordsBeanList.get(index).getSurplusDay() + recordsBeanList.get(index).getCompleteDay();
                                        if (40 == day) {
                                            //已完成
                                        } else {
                                            //补签
                                            sign(recordsBeanList.get(index).getId());
                                        }
                                    }
                                }
                            });
                        }
                    });
                }
            }

            @Override
            public void onError(String errorCode, String errorMsg) {
                getView().finishRefresh();
                LogUtil.e("我的任务礼包error" + errorMsg);
            }
        }));
    }

    private void sign(int id) {
        Map map = MapUtil.getInstance().addParms("id", id).build();
        Observable data = RetrofitUtil.getInstance().getApi(CommonResource.BASEURL_9006).getData(CommonResource.REMEDYTASKLIST, map);
        RetrofitUtil.getInstance().toSubscribe(data, new OnMyCallBack(new OnDataListener() {
            @Override
            public void onSuccess(String result, String msg) {
                LogUtil.e("签到日历" + result);
                List<SignInBean> signInBeanList = JSON.parseArray(result, SignInBean.class);
                if (signInBeanList != null && signInBeanList.size() != 0) {
                    YPopUtil.sign(mContext, signInBeanList);
                }
            }

            @Override
            public void onError(String errorCode, String errorMsg) {
                LogUtil.e("签到日历errorMsg" + errorMsg);
            }
        }));
    }
}
