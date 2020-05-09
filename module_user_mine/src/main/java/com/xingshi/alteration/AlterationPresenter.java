package com.xingshi.alteration;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.alibaba.android.arouter.launcher.ARouter;
import com.alibaba.fastjson.JSON;
import com.xingshi.adapter.MyRecyclerAdapter;
import com.xingshi.alteration.adapter.AlterationAdapter;
import com.xingshi.bean.AlterationBean;
import com.xingshi.common.CommonResource;
import com.xingshi.module_user_mine.R;
import com.xingshi.mvp.BasePresenter;
import com.xingshi.net.OnDataListener;
import com.xingshi.net.OnMyCallBack;
import com.xingshi.net.RetrofitUtil;
import com.xingshi.utils.LogUtil;
import com.xingshi.utils.ProcessDialogUtil;
import com.xingshi.utils.SPUtil;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import okhttp3.ResponseBody;

/**
 * Created by cuihaohao on 2019/5/27
 * Describe:
 */
public class AlterationPresenter extends BasePresenter<AlterationView> {

    private List<AlterationBean> rBeanList = new ArrayList<>();
//    private CustomDialog customDialog = new CustomDialog(mContext);

    public AlterationPresenter(Context context) {
        super(context);
    }

    @Override
    protected void onViewDestroy() {

    }

    public void alterationRec(final RecyclerView alterationRec) {
        ProcessDialogUtil.showProcessDialog(mContext);
        Observable<ResponseBody> responseBodyObservable = RetrofitUtil.getInstance().getApi(CommonResource.BASEURL_9004).postHeadWithout(CommonResource.RETURNTABLE, SPUtil.getToken());
        RetrofitUtil.getInstance().toSubscribe(responseBodyObservable, new OnMyCallBack(new OnDataListener() {

            @Override
            public void onSuccess(String result, String msg) {
                LogUtil.e("AlterationResult------->" + result);
                rBeanList = JSON.parseArray(result, AlterationBean.class);
                LogUtil.e("AlterationResult1------->" + rBeanList);
                if (rBeanList != null && rBeanList.size()!=0) {
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
                    alterationRec.setLayoutManager(linearLayoutManager);
                    AlterationAdapter alterationAdapter = new AlterationAdapter(mContext, rBeanList, R.layout.item_alteration_rec);
                    alterationRec.setAdapter(alterationAdapter);
                    alterationAdapter.setViewOnClickListener(new MyRecyclerAdapter.ViewOnClickListener() {
                        @Override
                        public void ViewOnClick(View view, final int position) {
                            view.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    //查看详情
                                    ARouter.getInstance()
                                            .build("/module_user_mine/RefundParticularsActivity")
                                            .withString("orderSn", rBeanList.get(position).getOrderSn())
                                            .withInt("position",position)
                                            .navigation();
                                }
                            });
                        }
                    });
                }
            }

            @Override
            public void onError(String errorCode, String errorMsg) {
                LogUtil.e("AlterationErrorMsg------->" + errorMsg);
            }
        }));

    }
}
