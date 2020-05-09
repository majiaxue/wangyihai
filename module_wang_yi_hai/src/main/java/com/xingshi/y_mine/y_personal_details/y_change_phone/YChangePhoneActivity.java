package com.xingshi.y_mine.y_personal_details.y_change_phone;

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
 * 区块更换手机号
 */
public class YChangePhoneActivity extends BaseActivity<YChangePhoneView, YChangePhonePresenter> implements YChangePhoneView {


    @BindView(R2.id.y_change_phone_image_back)
    ImageView yChangePhoneImageBack;
    @BindView(R2.id.y_change_phone_phone)
    EditText yChangePhonePhone;
    @BindView(R2.id.y_change_phone_get_verification_code)
    TextView yChangePhoneGetVerificationCode;
    @BindView(R2.id.y_change_phone_verification_code)
    EditText yChangePhoneVerificationCode;
    @BindView(R2.id.y_change_phone_commit)
    TextView yChangePhoneCommit;

    @Override
    public int getLayoutId() {
        return R.layout.activity_ychange_phone;
    }

    @Override
    public void initData() {

    }

    @Override
    public void initClick() {
        yChangePhoneImageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //获取验证码
        yChangePhoneGetVerificationCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                yChangePhoneGetVerificationCode.setEnabled(false);
                CountDownTimerUtil.startTimer1(YChangePhoneActivity.this, yChangePhoneGetVerificationCode);
            }
        });

        //提交
        yChangePhoneCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public YChangePhoneView createView() {
        return this;
    }

    @Override
    public YChangePhonePresenter createPresenter() {
        return new YChangePhonePresenter(this);
    }

}
