package com.xingshi.y_mine.certification;

import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.xingshi.mvp.BaseActivity;
import com.xingshi.y_main.R;
import com.xingshi.y_main.R2;

import butterknife.BindView;

/**
 * 实名认证
 */
@Route(path = "/module_wang_yi_hai/CertificationActivity")
public class CertificationActivity extends BaseActivity<CertificationView, CertificationPresenter> implements CertificationView {


    @BindView(R2.id.certification_back)
    ImageView certificationBack;
    @BindView(R2.id.certification_name)
    EditText certificationName;
    @BindView(R2.id.certification_identity_card)
    EditText certificationIdentityCard;
    @BindView(R2.id.certification_submit)
    TextView certificationSubmit;

    @Autowired(name = "type")
    int type;

    @Override
    public int getLayoutId() {
        return R.layout.activity_certification;
    }

    @Override
    public void initData() {
        ARouter.getInstance().inject(this);
    }

    @Override
    public void initClick() {
        certificationBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (1 == type) {
                    ARouter.getInstance().build("/module_wang_yi_hai/YMainActivity").navigation();
                    finish();
                } else {
                    finish();
                }
            }
        });

        certificationSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.submit(certificationName.getText().toString(),certificationIdentityCard.getText().toString());
            }
        });
    }

    @Override
    public CertificationView createView() {
        return this;
    }

    @Override
    public CertificationPresenter createPresenter() {
        return new CertificationPresenter(this);
    }
}
