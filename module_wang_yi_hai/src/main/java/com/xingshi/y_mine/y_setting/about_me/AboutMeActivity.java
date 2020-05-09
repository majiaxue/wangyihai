package com.xingshi.y_mine.y_setting.about_me;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.xingshi.common.CommonResource;
import com.xingshi.net.OnDataListener;
import com.xingshi.net.OnMyCallBack;
import com.xingshi.net.RetrofitUtil;
import com.xingshi.utils.MapUtil;
import com.xingshi.utils.StatusBarUtils;
import com.xingshi.y_main.R;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.ResponseBody;

/**
 * 区块关于我们
 */
public class AboutMeActivity extends AppCompatActivity {
    private ImageView aboutMeBack;
    private TextView aboutMeContent;
    private TextView aboutMeBackTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 设置状态栏
        StatusBarUtils.transparencyBar(this);
        StatusBarUtils.setAndroidNativeLightStatusBar(this, false);
        setContentView(R.layout.activity_about_me);

        initView();

        initData();

        initClick();

    }

    private void initClick() {
        aboutMeBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initData() {
        Intent intent = getIntent();
        int type = intent.getIntExtra("type", 0);
        if (1 == type) {
            aboutMeBackTitle.setText("任务介绍");
        } else {
            aboutMeBackTitle.setText("关于我们");
        }
        Map map = MapUtil.getInstance().addParms("type", type).build();
        Observable<ResponseBody> dataWithout = RetrofitUtil.getInstance().getApi(CommonResource.BASEURL_9006).getData(CommonResource.ABOUTUS, map);
        RetrofitUtil.getInstance().toSubscribe(dataWithout, new OnMyCallBack(new OnDataListener() {
            @Override
            public void onSuccess(String result, String msg) {
                aboutMeContent.setText(result);
            }

            @Override
            public void onError(String errorCode, String errorMsg) {

            }
        }));
    }

    private void initView() {
        aboutMeBack = findViewById(R.id.about_me_back);
        aboutMeContent = findViewById(R.id.about_me_content);
        aboutMeBackTitle = findViewById(R.id.about_me_back_title);

    }
}
