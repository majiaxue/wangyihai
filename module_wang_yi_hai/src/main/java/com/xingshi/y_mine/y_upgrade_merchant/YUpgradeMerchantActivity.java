package com.xingshi.y_mine.y_upgrade_merchant;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.xingshi.common.CommonResource;
import com.xingshi.net.OnDataListener;
import com.xingshi.net.OnMyCallBack;
import com.xingshi.net.RetrofitUtil;
import com.xingshi.utils.SPUtil;
import com.xingshi.utils.StatusBarUtils;
import com.xingshi.y_main.R;

import io.reactivex.Observable;
import okhttp3.ResponseBody;

/**
 * 区块升级商家
 */
public class YUpgradeMerchantActivity extends AppCompatActivity {

    private ImageView yUpgradeMerchantBack;
    private TextView yUpgradeMerchantCommit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 设置状态栏
        StatusBarUtils.transparencyBar(this);
        StatusBarUtils.setAndroidNativeLightStatusBar(this, false);

        setContentView(R.layout.activity_yupgrade_merchant);
        yUpgradeMerchantBack = findViewById(R.id.y_upgrade_merchant_back);
        yUpgradeMerchantCommit = findViewById(R.id.y_upgrade_merchant_commit);

        yUpgradeMerchantBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        yUpgradeMerchantCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Observable<ResponseBody> headWithout = RetrofitUtil.getInstance().getApi(CommonResource.BASEURL_9006).getHeadWithout(CommonResource.BUSINESSAPPLY, SPUtil.getToken());
                RetrofitUtil.getInstance().toSubscribe(headWithout,new OnMyCallBack(new OnDataListener() {
                    @Override
                    public void onSuccess(String result, String msg) {
                        Toast.makeText(YUpgradeMerchantActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(String errorCode, String errorMsg) {
                        Toast.makeText(YUpgradeMerchantActivity.this, errorMsg, Toast.LENGTH_SHORT).show();

                    }
                }));
            }
        });
    }
}
