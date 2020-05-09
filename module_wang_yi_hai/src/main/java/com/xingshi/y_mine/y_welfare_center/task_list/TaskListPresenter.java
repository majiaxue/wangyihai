package com.xingshi.y_mine.y_welfare_center.task_list;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alipay.sdk.app.PayTask;
import com.xingshi.adapter.MyRecyclerAdapter;
import com.xingshi.bean.AliPayBean;
import com.xingshi.bean.TaskListBean;
import com.xingshi.bean.WeChatPayBean;
import com.xingshi.common.CommonResource;
import com.xingshi.mvp.BasePresenter;
import com.xingshi.net.OnDataListener;
import com.xingshi.net.OnMyCallBack;
import com.xingshi.net.OnTripartiteCallBack;
import com.xingshi.net.RetrofitUtil;
import com.xingshi.utils.DisplayUtil;
import com.xingshi.utils.LogUtil;
import com.xingshi.utils.MapUtil;
import com.xingshi.utils.OnPopListener;
import com.xingshi.utils.PopUtils;
import com.xingshi.utils.ProcessDialogUtil;
import com.xingshi.utils.SPUtil;
import com.xingshi.y_main.R;
import com.xingshi.y_mine.y_welfare_center.commission_task_details.CommissionTaskDetailsActivity;
import com.xingshi.y_mine.y_welfare_center.task_list.adapter.TaskListAdapter;
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

public class TaskListPresenter extends BasePresenter<TaskListView> {

    private String[] titleArr = {"未提交", "审核中", "已通过", "未通过"};
    private List<TaskListBean.RecordsBean> taskList = new ArrayList<>();
    private TaskListAdapter taskListAdapter;

    private String info = "";
    private final int ALI_CODE = 0x1234;
    private int id;

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            if (msg.what == ALI_CODE) {
                Map<String, String> map = (Map<String, String>) msg.obj;
                String resultStatus = map.get("resultStatus");
                String result = map.get("result");
                String memo = map.get("memo");
                if ("9000".equals(resultStatus)) {
                    isPay(id);
                    Toast.makeText(mContext, "支付成功", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(mContext, "支付失败", Toast.LENGTH_SHORT).show();
                }
            }
        }

    };

    public TaskListPresenter(Context context) {
        super(context);
    }

    @Override
    protected void onViewDestroy() {

    }

    public void initTab(TabLayout yTaskListTab) {
        for (String title : titleArr) {
            yTaskListTab.addTab(yTaskListTab.newTab().setText(title));
        }

        yTaskListTab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                ProcessDialogUtil.showProcessDialog(mContext);
                taskList(1, tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    public void taskList(final int page, int type) {
        Map build = MapUtil.getInstance().addParms("pageNum", page).addParms("pageSize", 10).addParms("type", type).build();
        Observable head = RetrofitUtil.getInstance().getApi(CommonResource.BASEURL_9006).getHead(CommonResource.MYCOMMISSIONTASKLIST, build, SPUtil.getToken());
        RetrofitUtil.getInstance().toSubscribe(head, new OnMyCallBack(new OnDataListener() {
            @Override
            public void onSuccess(String result, final String msg) {
                LogUtil.e("我的任务列表" + result);
                getView().finishRefresh();
                TaskListBean taskListBean = JSON.parseObject(result, TaskListBean.class);
                if (1 == page) {
                    taskList.clear();
                }
                taskList.addAll(taskListBean.getRecords());
                if (taskListAdapter == null) {
                    taskListAdapter = new TaskListAdapter(mContext, taskList, R.layout.item_take_list_rec);
                    getView().loadAdapter(taskListAdapter);
                } else {
                    taskListAdapter.notifyDataSetChanged();
                }

                taskListAdapter.setViewOnClickListener(new MyRecyclerAdapter.ViewOnClickListener() {
                    @Override
                    public void ViewOnClick(View view, final int index) {
                        view.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                //取消报名
                                pickUpDelete(taskList.get(index).getId(), index);
                            }
                        });
                    }
                });

                taskListAdapter.setOnItemClick(new MyRecyclerAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(RecyclerView parent, View view, int position) {
                        //点击查看详情
                        if (0 == taskList.get(position).getSubStatus()) {
                            id = taskList.get(position).getId();
                            getView().id(id);
                            isPay(taskList.get(position).getId());
                        } else {
                            Intent intent = new Intent(mContext, CommissionTaskDetailsActivity.class);
                            intent.putExtra("id", taskList.get(position).getId());
                            intent.putExtra("status", 1);
                            mContext.startActivity(intent);
                        }
                    }
                });

            }

            @Override
            public void onError(String errorCode, String errorMsg) {
                getView().finishRefresh();
                LogUtil.e("我的任务列表errorMsg" + errorMsg);
            }
        }));
    }

    private void pickUpDelete(int id, final int position) {
        Map map = MapUtil.getInstance().addParms("id", id).build();
        Observable data = RetrofitUtil.getInstance().getApi(CommonResource.BASEURL_9006).getData(CommonResource.COMMISSIONTASKPICKUPDEL, map);
        RetrofitUtil.getInstance().toSubscribe(data, new OnMyCallBack(new OnDataListener() {
            @Override
            public void onSuccess(String result, String msg) {
                LogUtil.e("取消报名" + result);
                taskList.remove(position);
                taskListAdapter.notifyDataSetChanged();
            }

            @Override
            public void onError(String errorCode, String errorMsg) {
                LogUtil.e("取消报名errorMsg" + errorMsg);

            }
        }));
    }

    public void isPay(final int id) {
        Map map = MapUtil.getInstance().addParms("id", id).build();
        Observable data = RetrofitUtil.getInstance().getApi(CommonResource.BASEURL_9006).getData(CommonResource.MYCOMMISSIONTASKVIEW, map);
        RetrofitUtil.getInstance().toSubscribe(data, new OnMyCallBack(new OnDataListener() {
            @Override
            public void onSuccess(String result, String msg) {
                if ("3".equals(msg)) {
                    JSONObject jsonObject = JSON.parseObject(result);
                    final String orderSn = jsonObject.getString("number");
                    final String price = jsonObject.getString("taskUnitPrice");
                    final String userCode = jsonObject.getString("userCode");

                    View view = LayoutInflater.from(mContext).inflate(R.layout.pop_bottom, null);
                    final TextView popHeaderCancel = view.findViewById(R.id.pop_header_cancel);
                    final TextView popHeaderCamera = view.findViewById(R.id.pop_header_camera);
                    final TextView popHeaderXiangce = view.findViewById(R.id.pop_header_xiangce);
                    popHeaderCamera.setText("微信支付");
                    popHeaderXiangce.setText("支付宝支付");
                    PopUtils.setTransparency(mContext, 0.3f);
                    PopUtils.createPop(mContext, view, LinearLayout.LayoutParams.MATCH_PARENT, DisplayUtil.dip2px(mContext, 146), new OnPopListener() {
                        @Override
                        public void setOnPop(final PopupWindow pop) {
                            popHeaderCancel.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    pop.dismiss();
                                }
                            });
                            popHeaderCamera.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    weChat(orderSn, price,userCode);
                                    pop.dismiss();
                                }
                            });
                            popHeaderXiangce.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    aliPay(orderSn, price,userCode);
                                    pop.dismiss();
                                }
                            });
                        }
                    });
                } else {
                    Intent intent = new Intent(mContext, CommissionTaskDetailsActivity.class);
                    intent.putExtra("id", id);
                    intent.putExtra("status", 0);
                    mContext.startActivity(intent);
                }
            }

            @Override
            public void onError(String errorCode, String errorMsg) {

            }
        }));

    }

    //微信支付
    private void weChat(String orderSn, String price,String userCode) {
        final IWXAPI api = WXAPIFactory.createWXAPI(mContext, CommonResource.WXAPPID, false);

        Map map = MapUtil.getInstance().addParms("orderSn", orderSn).addParms("totalAmount", price).addParms("userCode", userCode).addParms("type", 2).build();
        String jsonString = JSON.toJSONString(map);
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonString);
        Observable<ResponseBody> responseBodyObservable = RetrofitUtil.getInstance().getApi(CommonResource.BASEURL_9006).postDataWithBody(CommonResource.WXPAYWELFARECENTRE, body);
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
    private void aliPay(String orderSn, String price,String userCode) {
        Map map = MapUtil.getInstance().addParms("orderSn", orderSn).addParms("totalAmount", price).addParms("userCode", userCode).addParms("type", 2).build();
        String jsonString = JSON.toJSONString(map);
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonString);
        Observable observable = RetrofitUtil.getInstance().getApi(CommonResource.BASEURL_9006).postDataWithBody(CommonResource.ALIPAYWELFARECENTRE, body);
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
