package com.xingshi.y_mine.y_setting;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.xingshi.mvp.BaseActivity;
import com.xingshi.y_main.R;
import com.xingshi.y_main.R2;
import com.xingshi.y_mine.y_setting.about_me.AboutMeActivity;
import com.xingshi.y_mine.y_setting.y_change_password.YChangePasswordActivity;

import butterknife.BindView;

/**
 * 区块设置
 */
public class YSettingActivity extends BaseActivity<YSettingView, YSettingPresenter> implements YSettingView {


    @BindView(R2.id.y_setting_back)
    ImageView ySettingBack;
    @BindView(R2.id.y_setting_change_password)
    LinearLayout ySettingChangePassword;
    @BindView(R2.id.y_setting_about_us)
    LinearLayout ySettingAboutUs;

    @Override
    public int getLayoutId() {
        return R.layout.activity_ysetting;
    }

    @Override
    public void initData() {

    }

    @Override
    public void initClick() {
        ySettingBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //修改登陆密码
        ySettingChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(YSettingActivity.this, YChangePasswordActivity.class));
            }
        });
        //关于我们
        ySettingAboutUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(YSettingActivity.this, AboutMeActivity.class);
                intent.putExtra("type",0);
                startActivity(intent);
            }
        });
    }

    @Override
    public YSettingView createView() {
        return this;
    }

    @Override
    public YSettingPresenter createPresenter() {
        return new YSettingPresenter(this);
    }

}
