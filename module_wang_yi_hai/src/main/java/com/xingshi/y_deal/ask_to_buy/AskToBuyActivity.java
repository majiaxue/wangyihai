package com.xingshi.y_deal.ask_to_buy;

import android.content.Intent;
import android.net.Uri;
import android.os.CountDownTimer;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ali.auth.third.core.util.StringUtil;
import com.xingshi.bean.BuyAndSellDetailBean;
import com.xingshi.mvp.BaseActivity;
import com.xingshi.utils.LogUtil;
import com.xingshi.utils.MyTimeUtil;
import com.xingshi.utils.PopUtils;
import com.xingshi.y_deal.sell.SellActivity;
import com.xingshi.y_main.R;
import com.xingshi.y_main.R2;
import com.facebook.drawee.view.SimpleDraweeView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import butterknife.BindView;

/**
 * 求购详情
 */
public class AskToBuyActivity extends BaseActivity<AskToBuyView, AskToBuyPresenter> implements AskToBuyView {

    @BindView(R2.id.my_pay_order_details_back)
    ImageView myPayOrderDetailsBack;
    @BindView(R2.id.my_pay_order_details_complaint)
    TextView myPayOrderDetailsComplaint;
    @BindView(R2.id.my_pay_order_details_title)
    TextView myPayOrderDetailsTitle;
    @BindView(R2.id.my_pay_order_details_linear)
    LinearLayout myPayOrderDetailsLinear;
    @BindView(R2.id.my_pay_order_details_order_time)
    TextView myPayOrderDetailsOrderTime;
    @BindView(R2.id.my_pay_order_details_relative2)
    RelativeLayout myPayOrderDetailsRelative2;
    @BindView(R2.id.my_pay_order_details_order_account)
    TextView myPayOrderDetailsOrderAccount;
    @BindView(R2.id.my_pay_order_details_singularization)
    TextView myPayOrderDetailsSingularization;
    @BindView(R2.id.my_pay_order_details_task_earnings)
    TextView myPayOrderDetailsTaskEarnings;
    @BindView(R2.id.my_pay_order_details_relative1)
    RelativeLayout myPayOrderDetailsRelative1;
    @BindView(R2.id.my_pay_order_details_text)
    TextView myPayOrderDetailsText;
    @BindView(R2.id.my_pay_order_details_head)
    SimpleDraweeView myPayOrderDetailsHead;
    @BindView(R2.id.my_pay_order_details_name)
    TextView myPayOrderDetailsName;
    @BindView(R2.id.my_pay_order_details_superior_id)
    TextView myPayOrderDetailsSuperiorId;
    @BindView(R2.id.my_pay_order_details_alipay_account)
    TextView myPayOrderDetailsAlipayAccount;
    @BindView(R2.id.my_pay_order_details_alipay_qr)
    ImageView myPayOrderDetailsAlipayQr;
    @BindView(R2.id.my_pay_order_details_relative)
    RelativeLayout myPayOrderDetailsRelative;
    @BindView(R2.id.my_pay_order_details_text1)
    TextView myPayOrderDetailsText1;
    @BindView(R2.id.my_pay_order_details_head1)
    SimpleDraweeView myPayOrderDetailsHead1;
    @BindView(R2.id.my_pay_order_details_name1)
    TextView myPayOrderDetailsName1;
    @BindView(R2.id.my_pay_order_details_superior_id1)
    TextView myPayOrderDetailsSuperiorId1;
    @BindView(R2.id.my_pay_order_details_relative3)
    RelativeLayout myPayOrderDetailsRelative3;
    @BindView(R2.id.my_pay_order_details_upload_pic)
    SimpleDraweeView myPayOrderDetailsUploadPic;
    @BindView(R2.id.my_pay_order_details_pay)
    TextView myPayOrderDetailsPay;
    @BindView(R2.id.my_pay_order_details_time)
    TextView myPayOrderDetailsTime;
    @BindView(R2.id.my_pay_order_details_text3)
    TextView myPayOrderDetailsText3;
    @BindView(R2.id.my_pay_order_details_text2)
    TextView myPayOrderDetailsText2;
    @BindView(R2.id.my_pay_order_details_bottom_linear)
    LinearLayout myPayOrderDetailsBottomLinear;
    @BindView(R2.id.my_pay_order_details_relative4)
    RelativeLayout myPayOrderDetailsRelative4;

    private int id;//订单ID
    private int status;//订单状态
    private final int TAKE_PHOTO_CODE = 0x113;
    private final int PHOTO_ALBUM_CODE = 0x223;
    private List<String> pics = new ArrayList<>();
    private CountDownTimer countDownTimer;
    private BuyAndSellDetailBean bean;

    @Override
    public int getLayoutId() {
        return R.layout.activity_my_pay_order_details;
    }

    @Override
    public void initData() {
        Intent intent = getIntent();
        id = intent.getIntExtra("id", 0);
        status = intent.getIntExtra("status", -1);
        myPayOrderDetailsTitle.setText("求购");

        presenter.initData(id);

        myPayOrderDetailsText2.setVisibility(View.VISIBLE);
    }

    @Override
    public void initClick() {
        myPayOrderDetailsBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        myPayOrderDetailsUploadPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!StringUtil.isBlank(bean.getOrderNumber())) {
                    if (0 == status) {
                        presenter.popupWindow();
                    }
                } else {
                    //放大图片
                    PopUtils.seeBigImg(AskToBuyActivity.this, bean.getVoucher());
                }
            }
        });

        myPayOrderDetailsAlipayQr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopUtils.seeBigImg(AskToBuyActivity.this, bean.getPaymentCode());
            }
        });

        myPayOrderDetailsPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (1 == status) {
                    presenter.payment(id);
                }
            }
        });
    }

    @Override
    public AskToBuyView createView() {
        return this;
    }

    @Override
    public AskToBuyPresenter createPresenter() {
        return new AskToBuyPresenter(this);
    }

    @Override
    public void loadData(BuyAndSellDetailBean bean) {
        this.bean = bean;
        if (StringUtil.isBlank(bean.getOrderNumber())) {
            //0求购中 1待支付 2待确认 3已完成 4 申诉 5取消 冻结 如果订单号不存在使用这个
            if (0 == status) {
                myPayOrderDetailsRelative.setVisibility(View.GONE);
                myPayOrderDetailsRelative4.setVisibility(View.GONE);
                myPayOrderDetailsTime.setVisibility(View.INVISIBLE);
                myPayOrderDetailsPay.setText("求购中");
            } else {
                myPayOrderDetailsBottomLinear.setVisibility(View.GONE);
                myPayOrderDetailsRelative.setVisibility(View.GONE);
                myPayOrderDetailsRelative4.setVisibility(View.GONE);
            }
        } else {
            //0待支付 1待确认 2已完成 3 申诉 4取消 5冻结 如果订单号存在存在使用这个
            if (0 == status) {
                myPayOrderDetailsPay.setText("付款");
                myPayOrderDetailsText3.setText("上传支付凭证：");
            } else {
                myPayOrderDetailsBottomLinear.setVisibility(View.GONE);
            }
        }

        myPayOrderDetailsOrderAccount.setText(bean.getOrderNumber());
        myPayOrderDetailsSingularization.setText(bean.getNumber() + "");
        myPayOrderDetailsTaskEarnings.setText("￥" + bean.getTotalPrice());
        myPayOrderDetailsOrderTime.setText(bean.getCreateTime());

        myPayOrderDetailsHead.setImageURI(bean.getSellUserCode() == null ? "" : bean.getSellUserCode());
        myPayOrderDetailsName.setText(Html.fromHtml("卖家:<font color= '#FF0000'>" + bean.getSellUserName() + "</font>"));
        myPayOrderDetailsSuperiorId.setText("ID：" + bean.getSellPhone());
        myPayOrderDetailsAlipayAccount.setText("支付宝账号：" + bean.getOpenid() + "");
        myPayOrderDetailsAlipayQr.setImageURI(Uri.parse(bean.getPaymentCode() == null ? "" : bean.getPaymentCode()));

        myPayOrderDetailsHead1.setImageURI(bean.getIcon() == null ? "" : bean.getIcon());
        myPayOrderDetailsName1.setText("姓名：" + bean.getBuyUserName());
        myPayOrderDetailsSuperiorId1.setText("ID：" + bean.getId());

        myPayOrderDetailsUploadPic.setImageURI(bean.getVoucher() == null ? "" : bean.getVoucher());

        presenter.getTime();
    }

    @Override
    public void takePhoto(Intent intent) {
        startActivityForResult(intent, TAKE_PHOTO_CODE);
    }

    @Override
    public void photoAlbum(Intent intent) {
        startActivityForResult(intent, PHOTO_ALBUM_CODE);
    }

    @Override
    public void showHeader(Uri uri, String base64) {
        myPayOrderDetailsUploadPic.setImageURI(uri);
        pics.add(base64);
    }

    @Override
    public void loadTime(String paymentTime) {
        if (!StringUtil.isBlank(bean.getOrderNumber())) {
            if (0 == status) {
                //获取当前时间戳
                long nowTime = System.currentTimeMillis();
                //得到订单创建时间戳
                long timeStamp = MyTimeUtil.getTimeStamp(bean.getCreateTime(), "yyyy-MM-dd HH:mm:ss");
                long endTime = timeStamp + Long.parseLong(paymentTime) * 1000;
                long consumeTime = endTime - nowTime;

                countDownTimer = new CountDownTimer(consumeTime, 1000) {//第一个参数表示总时间，第二个参数表示间隔时间。
                    @Override
                    public void onTick(long millisUntilFinished) {
                        SimpleDateFormat formatter = new SimpleDateFormat("HH小时mm分ss秒", Locale.CHINA);
                        formatter.setTimeZone(TimeZone.getTimeZone("GMT+00:00"));
                        String dateString = formatter.format(new Date(millisUntilFinished));
                        myPayOrderDetailsTime.setText("剩余时间:" + dateString);
                    }

                    @Override
                    public void onFinish() {
                        LogUtil.e("结束");
                    }
                }.start();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {
            return;
        }

        switch (requestCode) {
            case TAKE_PHOTO_CODE:
                presenter.takePhoto();
                break;
            case PHOTO_ALBUM_CODE:
                presenter.parseUri(data);
                break;
            default:
                break;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (countDownTimer != null) {
            countDownTimer.cancel();
            countDownTimer.onFinish();
        }
    }
}
