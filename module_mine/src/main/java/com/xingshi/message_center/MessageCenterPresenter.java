package com.xingshi.message_center;

import android.content.Context;
import android.content.Intent;

import com.alibaba.fastjson.JSON;
import com.xingshi.bean.MessageCenterBean;
import com.xingshi.common.CommonResource;
import com.xingshi.message_center.adapter.MessageCenterAdapter;
import com.xingshi.message_detail.MessageDetailActivity;
import com.xingshi.mvp.BasePresenter;
import com.xingshi.net.OnDataListener;
import com.xingshi.net.OnMyCallBack;
import com.xingshi.net.RetrofitUtil;
import com.xingshi.utils.LogUtil;
import com.xingshi.utils.SPUtil;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import okhttp3.ResponseBody;

public class MessageCenterPresenter extends BasePresenter<MessageCenterView> {

    private MessageCenterAdapter centerAdapter;
    private List<MessageCenterBean> dataList = new ArrayList<>();

    public MessageCenterPresenter(Context context) {
        super(context);
    }

    @Override
    protected void onViewDestroy() {

    }

    public void loadData() {
        Observable<ResponseBody> observable = RetrofitUtil.getInstance().getApi(CommonResource.BASEURL_4001).getHeadWithout(CommonResource.MESSAGELIST, SPUtil.getToken());
        RetrofitUtil.getInstance().toSubscribe(observable, new OnMyCallBack(new OnDataListener() {
            @Override
            public void onSuccess(String result, String msg) {
                if (getView() != null) {
                    getView().loadFinish();
                }
                LogUtil.e("消息列表：" + result);

                dataList.clear();

                dataList.addAll(JSON.parseArray(result, MessageCenterBean.class));
                if (centerAdapter == null) {
                    centerAdapter = new MessageCenterAdapter(mContext, dataList);
                } else {
                    centerAdapter.notifyDataSetChanged();
                }
                if (getView() != null) {
                    getView().loadRv(centerAdapter);
                }
            }

            @Override
            public void onError(String errorCode, String errorMsg) {
                LogUtil.e(errorCode + "------------" + errorMsg);
                if (getView() != null) {
                    getView().loadFinish();
                }
            }
        }));
    }

    public void jumpToDetail(int position) {
        Intent intent = new Intent(mContext, MessageDetailActivity.class);
        intent.putExtra("bean", dataList.get(position));
        mContext.startActivity(intent);
    }
}
