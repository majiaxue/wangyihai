package com.xingshi.y_mine.certification;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.ali.auth.third.core.util.StringUtil;
import com.alibaba.android.arouter.launcher.ARouter;
import com.xingshi.common.CommonResource;
import com.xingshi.mvp.BasePresenter;
import com.xingshi.net.OnDataListener;
import com.xingshi.net.OnMyCallBack;
import com.xingshi.net.RetrofitUtil;
import com.xingshi.utils.MapUtil;
import com.xingshi.utils.SPUtil;
import com.xingshi.y_mine.y_personal_details.YPersonalDetailsActivity;

import java.util.Map;

import io.reactivex.Observable;

public class CertificationPresenter extends BasePresenter<CertificationView> {

    public CertificationPresenter(Context context) {
        super(context);
    }

    @Override
    protected void onViewDestroy() {

    }

    public void submit(String name, String idNum) {
        if (StringUtil.isBlank(name)) {
            Toast.makeText(mContext, "请输入真实姓名", Toast.LENGTH_SHORT).show();
        } else if (StringUtil.isBlank(idNum)) {
            Toast.makeText(mContext, "请输入身份证信息", Toast.LENGTH_SHORT).show();
        } else {
            final Map map = MapUtil.getInstance().addParms("name", name).addParms("idNum", idNum).build();
            Observable head = RetrofitUtil.getInstance().getApi(CommonResource.BASEURL_9006).getHead(CommonResource.AUTHENTICATION, map, SPUtil.getToken());
            RetrofitUtil.getInstance().toSubscribe(head, new OnMyCallBack(new OnDataListener() {
                @Override
                public void onSuccess(String result, String msg) {
                    Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(mContext, YPersonalDetailsActivity.class);
                    mContext.startActivity(intent);
                    ((Activity)mContext).finish();
                }

                @Override
                public void onError(String errorCode, String errorMsg) {
                    Toast.makeText(mContext, errorMsg, Toast.LENGTH_SHORT).show();
                }
            }));
        }
    }
}
