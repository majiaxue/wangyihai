package com.xingshi.y_mine.y_setting.y_change_password;

import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.xingshi.mvp.BaseActivity;
import com.xingshi.utils.CountDownTimerUtil;
import com.xingshi.y_main.R;
import com.xingshi.y_main.R2;

import butterknife.BindView;

/**
 * 区块修改登陆密码
 */
public class YChangePasswordActivity extends BaseActivity<YChangePasswordView, YChangePasswordPresenter> implements YChangePasswordView {


    @BindView(R2.id.y_change_password_back)
    ImageView yChangePasswordBack;
    @BindView(R2.id.y_change_password_password)
    EditText yChangePasswordPassword;
    @BindView(R2.id.y_change_password_get_verification_code)
    TextView yChangePasswordGetVerificationCode;
    @BindView(R2.id.y_change_password_verification_code)
    EditText yChangePasswordVerificationCode;
    @BindView(R2.id.y_change_password_new_password)
    EditText yChangePasswordNewPassword;
    @BindView(R2.id.y_change_password_affirm_password)
    EditText yChangePasswordAffirmPassword;
    @BindView(R2.id.y_change_password_commit)
    TextView yChangePasswordCommit;

    @Override
    public int getLayoutId() {
        return R.layout.activity_ychange_password;
    }

    @Override
    public void initData() {

    }

    @Override
    public void initClick() {
        yChangePasswordBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        yChangePasswordGetVerificationCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                yChangePasswordGetVerificationCode.setEnabled(false);
                CountDownTimerUtil.startTimer1(YChangePasswordActivity.this, yChangePasswordGetVerificationCode);
            }
        });

        //完成
        yChangePasswordCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public YChangePasswordView createView() {
        return this;
    }

    @Override
    public YChangePasswordPresenter createPresenter() {
        return new YChangePasswordPresenter(this);
    }

}
