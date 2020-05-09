package com.xingshi.y_deal.appeal;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xingshi.adapter.MyRecyclerAdapter;
import com.xingshi.bean.AppealBean;
import com.xingshi.mvp.BaseActivity;
import com.xingshi.utils.PopUtils;
import com.xingshi.y_deal.appeal.adapter.AppealAdapter;
import com.xingshi.y_deal.sale.SaleActivity;
import com.xingshi.y_main.R;
import com.xingshi.y_main.R2;
import com.facebook.drawee.view.SimpleDraweeView;

import butterknife.BindView;

/**
 * 申述详情
 */
public class AppealActivity extends BaseActivity<AppealView, AppealPresenter> implements AppealView {

    @BindView(R2.id.appeal_back)
    ImageView appealBack;
    @BindView(R2.id.appeal_complaint)
    TextView appealComplaint;
    @BindView(R2.id.appeal_linear)
    LinearLayout appealLinear;
    @BindView(R2.id.appeal_order_time)
    TextView appealOrderTime;
    @BindView(R2.id.appeal_relative2)
    RelativeLayout appealRelative2;
    @BindView(R2.id.appeal_order_account)
    TextView appealOrderAccount;
    @BindView(R2.id.appeal_singularization)
    TextView appealSingularization;
    @BindView(R2.id.appeal_task_earnings)
    TextView appealTaskEarnings;
    @BindView(R2.id.appeal_relative1)
    RelativeLayout appealRelative1;
    @BindView(R2.id.appeal_text)
    TextView appealText;
    @BindView(R2.id.appeal_head)
    SimpleDraweeView appealHead;
    @BindView(R2.id.appeal_name)
    TextView appealName;
    @BindView(R2.id.appeal_superior_id)
    TextView appealSuperiorId;
    @BindView(R2.id.appeal_alipay_account)
    TextView appealAlipayAccount;
    @BindView(R2.id.appeal_alipay_qr)
    ImageView appealAlipayQr;
    @BindView(R2.id.appeal_relative)
    RelativeLayout appealRelative;
    @BindView(R2.id.appeal_text1)
    TextView appealText1;
    @BindView(R2.id.appeal_head1)
    SimpleDraweeView appealHead1;
    @BindView(R2.id.appeal_name1)
    TextView appealName1;
    @BindView(R2.id.appeal_superior_id1)
    TextView appealSuperiorId1;
    @BindView(R2.id.appeal_relative3)
    RelativeLayout appealRelative3;
    @BindView(R2.id.appeal_upload_remark)
    TextView appealUploadRemark;
    @BindView(R2.id.appeal_relative4)
    RelativeLayout appealRelative4;
    @BindView(R2.id.appeal_pic_rec)
    RecyclerView appealPicRec;
    private int id;
    private int type;
    private AppealBean.ComplaintBean bean;

    @Override
    public int getLayoutId() {
        return R.layout.activity_appeal;
    }

    @Override
    public void initData() {
        Intent intent = getIntent();
        //0买单  1卖单
        type = intent.getIntExtra("type", -1);
        id = intent.getIntExtra("id", -1);
        presenter.initData(id);
    }

    @Override
    public void initClick() {
        appealBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        appealAlipayQr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopUtils.seeBigImg(AppealActivity.this, bean.getPaymentCode());
            }
        });
    }

    @Override
    public AppealView createView() {
        return this;
    }

    @Override
    public AppealPresenter createPresenter() {
        return new AppealPresenter(this);
    }

    @Override
    public void loadData(final AppealBean appealBean) {
        this.bean = appealBean.getComplaint();
        appealOrderAccount.setText(bean.getOrderNumber());
        appealSingularization.setText(bean.getNumber() + "");
        appealTaskEarnings.setText("￥" + bean.getTotalPrice());
        appealOrderTime.setText(bean.getCreateTime());

        appealHead.setImageURI(bean.getSellUserCode() == null ? "" : bean.getSellUserCode());
        appealName.setText(Html.fromHtml("卖家:<font color= '#FF0000'>" + bean.getSellUserName() + "</font>"));
        appealSuperiorId.setText("ID：" + bean.getSellPhone());
        appealAlipayAccount.setText("支付宝账号：" + bean.getOpenid() + "");
        appealAlipayQr.setImageURI(Uri.parse(bean.getPaymentCode() == null ? "" : bean.getPaymentCode()));

        appealHead1.setImageURI(bean.getComplaintuser() == null ? "" : bean.getComplaintuser());
        appealName1.setText(Html.fromHtml("姓名：<font color= '#FF0000'>" + bean.getBuyUserName() + "</font>"));
        appealSuperiorId1.setText("ID：" + bean.getId());

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        appealPicRec.setLayoutManager(linearLayoutManager);
        AppealAdapter appealAdapter = new AppealAdapter(this, appealBean.getComplaintImg(), R.layout.item_image);
        appealPicRec.setAdapter(appealAdapter);
        appealAdapter.setOnItemClick(new MyRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerView parent, View view, int position) {
                PopUtils.seeBigImg(AppealActivity.this, appealBean.getComplaintImg().get(position).getImgUrl());
            }
        });

    }
}
