package com.xingshi.y_confirm_payment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.xingshi.common.CommonResource;
import com.xingshi.mvp.BasePresenter;
import com.xingshi.net.OnDataListener;
import com.xingshi.net.OnMyCallBack;
import com.xingshi.net.RetrofitUtil;
import com.xingshi.utils.MapUtil;
import com.xingshi.utils.OnConfirmPaymentListener;
import com.xingshi.utils.SPUtil;
import com.xingshi.utils.YPopUtil;
import com.xingshi.y_main.R;
import com.xingshi.y_main.YMainActivity;

import java.util.Map;

import io.reactivex.Observable;

public class YConfirmPaymentPresenter extends BasePresenter<YConfirmPaymentView> {

    public YConfirmPaymentPresenter(Context context) {
        super(context);
    }

    @Override
    protected void onViewDestroy() {

    }

    public void confirmPayment(final String orderSn) {
        YPopUtil.confirmPayment(mContext, new OnConfirmPaymentListener() {
            @Override
            public void setConfirmPayment(final PopupWindow pop, final View passwordInput, View confirm) {
                final EditText password = passwordInput.findViewById(R.id.pop_confirm_payment_password);
                confirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final int length = password.getText().length();
                        if (length == 0) {
                            Toast.makeText(mContext, "请输入密码", Toast.LENGTH_SHORT).show();
                        } else if (length < 6) {
                            Toast.makeText(mContext, "请输入完整的密码", Toast.LENGTH_SHORT).show();
                        } else {
                            final Map map = MapUtil.getInstance().addParms("orderSn", orderSn).addParms("password", password.getText().toString()).build();
                            Observable head = RetrofitUtil.getInstance().getApi(CommonResource.BASEURL_9004).getHead(CommonResource.EXCHANGEPRODUCT, map, SPUtil.getToken());
                            RetrofitUtil.getInstance().toSubscribe(head, new OnMyCallBack(new OnDataListener() {
                                @Override
                                public void onSuccess(String result, String msg) {
                                    Toast.makeText(mContext, "支付成功", Toast.LENGTH_SHORT).show();
                                    pop.dismiss();
                                    mContext.startActivity(new Intent(mContext, YMainActivity.class));
                                    ((Activity) mContext).finish();
                                }

                                @Override
                                public void onError(String errorCode, String errorMsg) {
                                    Toast.makeText(mContext, errorMsg, Toast.LENGTH_SHORT).show();
                                    pop.dismiss();
                                }
                            }));
                        }
                    }
                });
            }
        });
    }
}
