package com.xingshi.y_mine.auditing.xiangqing;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.xingshi.bean.TaskListDetailsBean;
import com.xingshi.common.CommonResource;
import com.xingshi.mvp.BasePresenter;
import com.xingshi.net.OnDataListener;
import com.xingshi.net.OnMyCallBack;
import com.xingshi.net.RetrofitUtil;
import com.xingshi.utils.LogUtil;
import com.xingshi.utils.MapUtil;
import com.xingshi.utils.PopUtils;
import com.xingshi.utils.SPUtil;
import com.xingshi.y_main.R;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.ResponseBody;

public class AuditingDetialPresenter extends BasePresenter<AuditingDetialView> {
    public AuditingDetialPresenter(Context context) {
        super(context);
    }

    @Override
    protected void onViewDestroy() {

    }

    public void initTaskListDetailData(String id) {
        Map map = MapUtil.getInstance().addParms("id", id).build();
        Observable data = RetrofitUtil.getInstance().getApi(CommonResource.BASEURL_9006).getData(CommonResource.MYCOMMISSIONTASKVIEW, map);
        RetrofitUtil.getInstance().toSubscribe(data, new OnMyCallBack(new OnDataListener() {
            @Override
            public void onSuccess(String result, String msg) {
                LogUtil.e("我的任务详情" + result);
                TaskListDetailsBean taskListDetailsBean = JSON.parseObject(result, TaskListDetailsBean.class);
                if (getView() != null) {
                    getView().loadData(taskListDetailsBean);
                }
            }

            @Override
            public void onError(String errorCode, String errorMsg) {
                LogUtil.e("我的任务详情errorMsg" + errorMsg);
            }
        }));
    }

    public void getPanDuan(int status, String id) {
        LogUtil.e("id----------" + id);
        LogUtil.e("status---------" + status);
        Map build = MapUtil.getInstance().addParms("status", status).addParms("id", id).build();
        Observable head = RetrofitUtil.getInstance().getApi(CommonResource.BASEURL_9006).getHead(CommonResource.TONGYI, build, SPUtil.getToken());
        RetrofitUtil.getInstance().toSubscribe(head, new OnMyCallBack(new OnDataListener() {
            @Override
            public void onSuccess(String result, String msg) {
                Toast.makeText(mContext, result, Toast.LENGTH_SHORT).show();
                ((Activity) mContext).finish();
                LogUtil.e("同意或拒绝--------" + result);
            }

            @Override
            public void onError(String errorCode, String errorMsg) {
                LogUtil.e("同意或拒绝错误信息-----------" + errorCode + "----------" + errorMsg);
                Toast.makeText(mContext, errorMsg, Toast.LENGTH_SHORT).show();
            }
        }));
    }

    public void popupwindow(final String id, int panduan) {
        View inflate = LayoutInflater.from(mContext).inflate(R.layout.pop_tan, null);
        TextView tvOk = inflate.findViewById(R.id.ok);   //确认按钮
        TextView cancel = inflate.findViewById(R.id.cancel);   //取消
        TextView tv = inflate.findViewById(R.id.tv_tongguo);   //通过
        ImageView img = inflate.findViewById(R.id.guan);
        final PopupWindow popupWindow = new PopupWindow(inflate, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, true);
        popupWindow.setOutsideTouchable(false);
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        popupWindow.showAtLocation(new View(mContext), Gravity.CENTER, 0, 0);
        PopUtils.setTransparency(mContext, 0.3f);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                PopUtils.setTransparency(mContext, 1f);
            }
        });
        if (panduan == 1) {
            tv.setText("确定要通过吗");
            tvOk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getPanDuan(1, id);
                }
            });
        } else {
            tv.setText("确定要拒绝吗");
            tvOk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getPanDuan(2, id);
                }
            });
        }
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
            }
        });
    }
}
