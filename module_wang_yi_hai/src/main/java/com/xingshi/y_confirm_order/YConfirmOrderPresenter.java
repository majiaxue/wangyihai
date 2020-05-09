package com.xingshi.y_confirm_order;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.xingshi.bean.OrderConfirmBean;
import com.xingshi.bean.ShippingAddressBean;
import com.xingshi.bean.SubmitOrderBean;
import com.xingshi.common.CommonResource;
import com.xingshi.mvp.BasePresenter;
import com.xingshi.net.OnDataListener;
import com.xingshi.net.OnMyCallBack;
import com.xingshi.net.RetrofitUtil;
import com.xingshi.utils.LogUtil;
import com.xingshi.utils.ProcessDialogUtil;
import com.xingshi.utils.SPUtil;
import com.xingshi.y_confirm_payment.YConfirmPaymentActivity;

import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

public class YConfirmOrderPresenter extends BasePresenter<YConfirmOrderView> {

    public ShippingAddressBean addressBean;

    public YConfirmOrderPresenter(Context context) {
        super(context);
    }

    @Override
    protected void onViewDestroy() {

    }

    public void getAddress() {
        ProcessDialogUtil.showProcessDialog(mContext);

        Observable<ResponseBody> observable = RetrofitUtil.getInstance().getApi(CommonResource.BASEURL_4001).getHeadWithout(CommonResource.MOREN_ADDRESS, SPUtil.getToken());
        RetrofitUtil.getInstance().toSubscribe(observable, new OnMyCallBack(new OnDataListener() {
            @Override
            public void onSuccess(String result, String msg) {
                LogUtil.e("默认地址：" + result);
                addressBean = JSON.parseObject(result, ShippingAddressBean.class);
                if (getView() != null) {
                    getView().loadAddress(addressBean);
                }
            }

            @Override
            public void onError(String errorCode, String errorMsg) {
                LogUtil.e("dizhi:" + errorCode + "-------" + errorMsg);
                if (getView() != null) {
                    getView().noAddress();
                }
            }
        }));

    }

    public void submit(final OrderConfirmBean bean) {
        if (bean.getReceiverProvince() == null || "".equals(bean.getReceiverProvince())) {
            Toast.makeText(mContext, "请选择收货地址", Toast.LENGTH_SHORT).show();
        } else {
            ProcessDialogUtil.showProcessDialog(mContext);
            bean.setType(1);
            String jsonString = JSON.toJSONString(bean);
            RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonString);
            Observable<ResponseBody> observable = RetrofitUtil.getInstance().getApi(CommonResource.BASEURL_9004).postHeadWithBody(CommonResource.COMMIT_ORDER, requestBody, SPUtil.getToken());
            RetrofitUtil.getInstance().toSubscribe(observable, new OnMyCallBack(new OnDataListener() {
                @Override
                public void onSuccess(String result, String msg) {
                    LogUtil.e("提交订单：" + result);
                    SubmitOrderBean submitOrderBean = JSON.parseObject(result, SubmitOrderBean.class);
                    submitOrderBean.setProductName("goods");
                    submitOrderBean.setProductCategoryId(bean.getProductCategoryId());

                    Intent intent = new Intent(mContext, YConfirmPaymentActivity.class);
                    intent.putExtra("submitOrderBean", submitOrderBean);
                    intent.putExtra("OrderConfirmBean", bean);
                    mContext.startActivity(intent);

                    ((Activity) mContext).finish();
                }

                @Override
                public void onError(String errorCode, String errorMsg) {
                    LogUtil.e(errorCode + "--------" + errorMsg);
                }
            }));

        }
    }
}
