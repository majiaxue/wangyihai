package com.xingshi.predict.tb;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.xingshi.bean.PredictBean;
import com.xingshi.common.CommonResource;
import com.xingshi.mvp.BasePresenter;
import com.xingshi.net.OnDataListener;
import com.xingshi.net.OnMyCallBack;
import com.xingshi.net.RetrofitUtil;
import com.xingshi.utils.LogUtil;
import com.xingshi.utils.MapUtil;
import com.xingshi.utils.ProcessDialogUtil;
import com.xingshi.utils.SPUtil;

import java.util.Map;

import io.reactivex.Observable;

public class TBPresenter extends BasePresenter<TBView> {
    public TBPresenter(Context context) {
        super(context);
    }

    @Override
    protected void onViewDestroy() {

    }

    public void loadData() {
        ProcessDialogUtil.showProcessDialog(mContext);
//        WaitDialog.show((AppCompatActivity)mContext,null);

        Map map = MapUtil.getInstance().addParms("type", "1").build();
        Observable observable = RetrofitUtil.getInstance().getApi(CommonResource.BASEURL_4001).getHead(CommonResource.GETPREDICT, map, SPUtil.getToken());
        RetrofitUtil.getInstance().toSubscribe(observable, new OnMyCallBack(new OnDataListener() {
            @Override
            public void onSuccess(String result, String msg) {
                LogUtil.e("tb:" + result);
                PredictBean predictBean = JSON.parseObject(result, PredictBean.class);
                if (predictBean != null) {
                    if (getView() != null) {
                        getView().loadUI(predictBean);
                    }
                } else {
                    if (getView() != null) {
                        getView().loadUI();
                    }
                }
            }

            @Override
            public void onError(String errorCode, String errorMsg) {
                LogUtil.e("tbErrorMsg:" + errorMsg);
                if (getView() != null) {
                    getView().loadUI();
                }
            }
        }));
    }
}
