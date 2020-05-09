package com.xingshi.y_mine.y_welfare_center.commission_task;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.xingshi.adapter.MyRecyclerAdapter;
import com.xingshi.bean.ReleaseATaskTabBean;
import com.xingshi.common.CommonResource;
import com.xingshi.mvp.BasePresenter;
import com.xingshi.net.OnDataListener;
import com.xingshi.net.OnMyCallBack;
import com.xingshi.net.RetrofitUtil;
import com.xingshi.utils.LogUtil;
import com.xingshi.utils.MapUtil;
import com.xingshi.y_main.R;
import com.xingshi.bean.CommissionTaskListBean;
import com.xingshi.y_mine.y_welfare_center.commission_task.adapter.CommissionTaskAdapter;
import com.xingshi.y_mine.y_welfare_center.commission_task_details.CommissionTaskDetailsActivity;
import com.xingshi.y_mine.y_welfare_center.release_a_task.adapter.ReleaseATaskTabAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.ResponseBody;

public class CommissionTaskPresenter extends BasePresenter<CommissionTaskView> {

    private List<ReleaseATaskTabBean> tabBeanList;
    private List<CommissionTaskListBean.RecordsBean> commissionTaskList = new ArrayList<>();
    private CommissionTaskAdapter commissionTaskAdapter;


    public CommissionTaskPresenter(Context context) {
        super(context);
    }

    @Override
    protected void onViewDestroy() {

    }

    public void initTab() {
        Observable<ResponseBody> dataWithout = RetrofitUtil.getInstance().getApi(CommonResource.BASEURL_9006).getDataWithout(CommonResource.COMMISSIONTASKTYPE);
        RetrofitUtil.getInstance().toSubscribe(dataWithout, new OnMyCallBack(new OnDataListener() {
            @Override
            public void onSuccess(String result, String msg) {
                LogUtil.e("任务类型" + result);
                tabBeanList = JSON.parseArray(result, ReleaseATaskTabBean.class);
                tabBeanList.get(0).setClick(true);
                getView().getType(tabBeanList.get(0).getId());
                final ReleaseATaskTabAdapter releaseATaskTabAdapter = new ReleaseATaskTabAdapter(mContext, tabBeanList, R.layout.item_text);
                if (getView() != null) {
                    getView().loadAdapter(releaseATaskTabAdapter);
                }

                releaseATaskTabAdapter.setOnItemClick(new MyRecyclerAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(RecyclerView parent, View view, int position) {
                        tabBeanList.get(position).setClick(true);
                        initData(tabBeanList.get(position).getId(), 1);
                        for (int i = 0; i < tabBeanList.size(); i++) {
                            if (position != i) {
                                tabBeanList.get(i).setClick(false);
                            }
                        }
                        releaseATaskTabAdapter.notifyDataSetChanged();
                    }
                });
            }

            @Override
            public void onError(String errorCode, String errorMsg) {
                LogUtil.e("任务类型errorMsg" + errorMsg);
            }
        }));
    }

    public void initData(int type, final int page) {
        Map build = MapUtil.getInstance().addParms("pageNum", page).addParms("pageSize", 10).addParms("type", type).build();
        Observable data = RetrofitUtil.getInstance().getApi(CommonResource.BASEURL_9006).getData(CommonResource.COMMISSIONTASKLIST, build);
        RetrofitUtil.getInstance().toSubscribe(data, new OnMyCallBack(new OnDataListener() {
            @Override
            public void onSuccess(String result, String msg) {
                LogUtil.e("任务列表" + result);
                CommissionTaskListBean commissionTaskListBean = JSON.parseObject(result, CommissionTaskListBean.class);
                if (1 == page) {
                    commissionTaskList.clear();
                }
                commissionTaskList.addAll(commissionTaskListBean.getRecords());
                if (commissionTaskAdapter == null) {
                    commissionTaskAdapter = new CommissionTaskAdapter(mContext, commissionTaskList, R.layout.item_pay_a_tip_rec);
                    getView().loadAdapter(commissionTaskAdapter);
                } else {
                    commissionTaskAdapter.notifyDataSetChanged();
                }
                commissionTaskAdapter.setOnItemClick(new MyRecyclerAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(RecyclerView parent, View view, int position) {
//                        Toast.makeText(mContext, "无法查看", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(mContext, CommissionTaskDetailsActivity.class);
                        intent.putExtra("id",commissionTaskList.get(position).getId());
                        mContext.startActivity(intent);
                    }
                });
            }

            @Override
            public void onError(String errorCode, String errorMsg) {
                LogUtil.e("任务列表errorMsg" + errorMsg);
            }
        }));
    }
}
