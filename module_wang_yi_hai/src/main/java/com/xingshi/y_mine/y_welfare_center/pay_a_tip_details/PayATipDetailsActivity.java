package com.xingshi.y_mine.y_welfare_center.pay_a_tip_details;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xingshi.bean.PayATipDetailBean;
import com.xingshi.mvp.BaseActivity;
import com.xingshi.y_main.R;
import com.xingshi.y_main.R2;
import com.facebook.drawee.view.SimpleDraweeView;

import butterknife.BindView;

/**
 * 线报详情
 */
public class PayATipDetailsActivity extends BaseActivity<PayATipDetailsView, PayATipDetailsPresenter> implements PayATipDetailsView {


    @BindView(R2.id.pay_a_tip_details_image_back)
    ImageView payATipDetailsImageBack;
    @BindView(R2.id.pay_a_tip_details_relative)
    RelativeLayout payATipDetailsRelative;
    @BindView(R2.id.pay_a_tip_details_title)
    TextView payATipDetailsTitle;
    @BindView(R2.id.pay_a_tip_details_pic)
    SimpleDraweeView payATipDetailsPic;
    @BindView(R2.id.pay_a_tip_details_content)
    TextView payATipDetailsContent;

    private PayATipDetailBean payATipDetailBean;

    @Override
    public int getLayoutId() {
        return R.layout.activity_pay_atip_details;
    }

    @Override
    public void initData() {
        Intent intent = getIntent();
        payATipDetailBean = (PayATipDetailBean) intent.getSerializableExtra("payATipDetailBean");
        payATipDetailsTitle.setText(payATipDetailBean.getTitle());
        payATipDetailsPic.setImageURI(payATipDetailBean.getPictureUrl() == null ? "" : payATipDetailBean.getPictureUrl());
        payATipDetailsContent.setText("  "+payATipDetailBean.getContent());
    }

    @Override
    public void initClick() {
        payATipDetailsImageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public PayATipDetailsView createView() {
        return this;
    }

    @Override
    public PayATipDetailsPresenter createPresenter() {
        return new PayATipDetailsPresenter(this);
    }

}
