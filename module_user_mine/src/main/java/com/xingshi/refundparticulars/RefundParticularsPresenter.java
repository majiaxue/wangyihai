package com.xingshi.refundparticulars;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.xingshi.bean.RefundParticularsBean;
import com.xingshi.common.CommonResource;
import com.xingshi.module_user_mine.R;
import com.xingshi.mvp.BasePresenter;
import com.xingshi.net.OnDataListener;
import com.xingshi.net.OnMyCallBack;
import com.xingshi.net.RetrofitUtil;
import com.xingshi.refundparticulars.adapter.RefundParticularsRecAdapter;
import com.xingshi.utils.LogUtil;
import com.xingshi.utils.MapUtil;
import com.xingshi.utils.ProcessDialogUtil;
import com.xingshi.utils.SPUtil;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;

/**
 * Created by cuihaohao on 2019/5/27
 * Describe:
 */
public class RefundParticularsPresenter extends BasePresenter<RefundParticularsView> {

//    private CustomDialog customDialog = new CustomDialog(mContext);

    public RefundParticularsPresenter(Context context) {
        super(context);
    }

    @Override
    protected void onViewDestroy() {

    }

    public void initView(String orderSn) {
//        customDialog.show();
//        WaitDialog.show((AppCompatActivity)mContext,null);
        ProcessDialogUtil.showProcessDialog(mContext);

        Map map = MapUtil.getInstance().addParms("orderSn", orderSn).build();
        Observable observable = RetrofitUtil.getInstance().getApi(CommonResource.BASEURL_9004).postHead(CommonResource.RETURNINFO, map, SPUtil.getToken());
        RetrofitUtil.getInstance().toSubscribe(observable, new OnMyCallBack(new OnDataListener() {
            @Override
            public void onSuccess(String result, String msg) {
//                customDialog.dismiss();
                LogUtil.e("RefundParticularsResult------------->" + result);
//                List<AlterationBean> list = JSON.parseArray(result, AlterationBean.class);
                RefundParticularsBean refundParticularsBean = JSON.parseObject(result, new TypeReference<RefundParticularsBean>() {
                }.getType());
                if (refundParticularsBean != null) {
                    if (getView() != null) {
                        getView().initView(refundParticularsBean);
                    }
                }
            }

            @Override
            public void onError(String errorCode, String errorMsg) {
//                customDialog.dismiss();
                LogUtil.e("RefundParticularsErrorMsg------------->" + errorMsg);
            }
        }));

    }

    public void goodsList(RecyclerView refundParticularsRec, List<RefundParticularsBean.ItemlistBean> itemList) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        refundParticularsRec.setLayoutManager(linearLayoutManager);
        RefundParticularsRecAdapter refundParticularsRecAdapter = new RefundParticularsRecAdapter(mContext, itemList, R.layout.item_refund_particulars_rec);
        refundParticularsRec.setAdapter(refundParticularsRecAdapter);
    }
}
