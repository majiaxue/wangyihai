package com.xingshi.register;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;

import com.alibaba.android.arouter.launcher.ARouter;
import com.alibaba.fastjson.JSON;
import com.xingshi.bean.RegisterBean;
import com.xingshi.bean.UserInfoBean;
import com.xingshi.common.CommonResource;
import com.xingshi.mvp.BasePresenter;
import com.xingshi.net.OnDataListener;
import com.xingshi.net.OnMyCallBack;
import com.xingshi.net.RetrofitUtil;
import com.xingshi.utils.JpushUtil;
import com.xingshi.utils.LogUtil;
import com.xingshi.utils.MapUtil;
import com.xingshi.utils.PhoneNumUtil;
import com.xingshi.utils.SPUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.ResponseBody;

public class RegisterPresenter extends BasePresenter<RegisterView> {
    public boolean isRead = true;

    public RegisterPresenter(Context context) {
        super(context);
    }

    @Override
    protected void onViewDestroy() {

    }

    public void check() {
        if (isRead) {
            getView().noRead();
            isRead = false;
        } else {
            getView().readed();
            isRead = true;
        }
    }

    public void getCodeNum(String phone) {
        if (PhoneNumUtil.isMobileNO(phone)) {
            Observable<ResponseBody> observable = RetrofitUtil.getInstance().getApi(CommonResource.BASEURL_4001).getDataWithout(CommonResource.GETREGISTERCODE + "/" + phone);
            RetrofitUtil.getInstance().toSubscribe(observable, new OnMyCallBack(new OnDataListener() {
                @Override
                public void onSuccess(String result, String msg) {
                    LogUtil.e("获取验证码：" + result);
                    getView().getCodeSuccess();
                }

                @Override
                public void onError(String errorCode, String errorMsg) {
                    Toast.makeText(mContext, "" + errorMsg, Toast.LENGTH_SHORT).show();
                    LogUtil.e(errorCode + "-----------" + errorMsg);
                }
            }));
        } else {
            Toast.makeText(mContext, "请输入正确的手机号", Toast.LENGTH_SHORT).show();
        }
    }

    public void toRegister(String phone, String password, String code, String inviteCode) {
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(mContext, "请输入密码", Toast.LENGTH_SHORT).show();
        } else {
            RegisterBean registerBean;
            if ("".equals(inviteCode)) {
                registerBean = new RegisterBean(phone, code, password);
            } else {
                registerBean = new RegisterBean(phone, code, password, inviteCode);
            }
            String jsonString = JSON.toJSONString(registerBean);
            Map map = MapUtil.getInstance().addParms("memberStr", jsonString).build();
            Observable<ResponseBody> observable = RetrofitUtil.getInstance().getApi(CommonResource.BASEURL_4001).postData(CommonResource.PHONEREGISTER, map);
            RetrofitUtil.getInstance().toSubscribe(observable, new OnMyCallBack(new OnDataListener() {
                @Override
                public void onSuccess(String result, String msg) {
                    LogUtil.e("注册：" + result);
                    UserInfoBean userInfoBean = new Gson().fromJson(result, new TypeToken<UserInfoBean>() {
                    }.getType());
                    SPUtil.addParm(CommonResource.TOKEN, "JWT " + userInfoBean.getToken());
                    SPUtil.addParm(CommonResource.USERCODE, userInfoBean.getUserCode());
                    SPUtil.addParm(CommonResource.USER_NAME, userInfoBean.getNickname());
                    SPUtil.addParm(CommonResource.USER_PIC, userInfoBean.getIcon());
                    SPUtil.addParm(CommonResource.USER_INVITE, userInfoBean.getInviteCode());
                    SPUtil.addParm(CommonResource.LEVELID, userInfoBean.getLevelId());

                    JpushUtil.setAlias(userInfoBean.getUserCode());
                    ARouter.getInstance().build("/home/main").withString("type", "login").navigation();
                    ((Activity) mContext).finish();
                }

                @Override
                public void onError(String errorCode, String errorMsg) {
                    Toast.makeText(mContext, "" + errorMsg, Toast.LENGTH_SHORT).show();
                }
            }));
        }
    }
}
