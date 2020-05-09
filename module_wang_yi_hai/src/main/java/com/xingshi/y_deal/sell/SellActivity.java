package com.xingshi.y_deal.sell;

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
import com.xingshi.y_deal.appeal.AppealActivity;
import com.xingshi.y_deal.my_pay_order_details.MyPayOrderDetailsActivity;
import com.xingshi.y_main.R;
import com.xingshi.y_main.R2;
import com.facebook.drawee.view.SimpleDraweeView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import butterknife.BindView;

/**
 * 出售详情
 */
public class SellActivity extends BaseActivity<SellView, SellPresenter> implements SellView {


    @BindView(R2.id.sell_order_details_back)
    ImageView sellOrderDetailsBack;
    @BindView(R2.id.sell_order_details_complaint)
    TextView sellOrderDetailsComplaint;
    @BindView(R2.id.sell_order_details_title)
    TextView sellOrderDetailsTitle;
    @BindView(R2.id.sell_order_details_linear)
    LinearLayout sellOrderDetailsLinear;
    @BindView(R2.id.sell_order_details_order_time)
    TextView sellOrderDetailsOrderTime;
    @BindView(R2.id.sell_order_details_relative2)
    RelativeLayout sellOrderDetailsRelative2;
    @BindView(R2.id.sell_order_details_order_account)
    TextView sellOrderDetailsOrderAccount;
    @BindView(R2.id.sell_order_details_singularization)
    TextView sellOrderDetailsSingularization;
    @BindView(R2.id.sell_order_details_task_earnings)
    TextView sellOrderDetailsTaskEarnings;
    @BindView(R2.id.sell_order_details_relative1)
    RelativeLayout sellOrderDetailsRelative1;
    @BindView(R2.id.sell_order_details_text)
    TextView sellOrderDetailsText;
    @BindView(R2.id.sell_order_details_head)
    SimpleDraweeView sellOrderDetailsHead;
    @BindView(R2.id.sell_order_details_name)
    TextView sellOrderDetailsName;
    @BindView(R2.id.sell_order_details_superior_id)
    TextView sellOrderDetailsSuperiorId;
    @BindView(R2.id.sell_order_details_alipay_account)
    TextView sellOrderDetailsAlipayAccount;
    @BindView(R2.id.sell_order_details_alipay_qr)
    ImageView sellOrderDetailsAlipayQr;
    @BindView(R2.id.sell_order_details_relative)
    RelativeLayout sellOrderDetailsRelative;
    @BindView(R2.id.sell_order_details_text1)
    TextView sellOrderDetailsText1;
    @BindView(R2.id.sell_order_details_head1)
    SimpleDraweeView sellOrderDetailsHead1;
    @BindView(R2.id.sell_order_details_name1)
    TextView sellOrderDetailsName1;
    @BindView(R2.id.sell_order_details_superior_id1)
    TextView sellOrderDetailsSuperiorId1;
    @BindView(R2.id.sell_order_details_relative3)
    RelativeLayout sellOrderDetailsRelative3;
    @BindView(R2.id.sell_order_details_relative4)
    RelativeLayout sellOrderDetailsRelative4;
    @BindView(R2.id.sell_order_details_upload_pic)
    SimpleDraweeView sellOrderDetailsUploadPic;
    @BindView(R2.id.sell_order_details_pay)
    TextView sellOrderDetailsPay;
    @BindView(R2.id.sell_order_details_time)
    TextView sellOrderDetailsTime;
    @BindView(R2.id.sell_order_details_bottom_linear)
    LinearLayout sellOrderDetailsBottomLinear;
    @BindView(R2.id.sell_order_details_alipay_linear)
    LinearLayout sellOrderDetailsAlipayLinear;
    @BindView(R2.id.sell_order_details_text2)
    TextView sellOrderDetailsText2;
    @BindView(R2.id.sell_order_details_text3)
    TextView sellOrderDetailsText3;
    @BindView(R2.id.sell_order_details_cancel)
    TextView sellOrderDetailsCancel;

    private int id;//订单ID
    private int status;//订单状态
    private final int TAKE_PHOTO_CODE = 0x113;
    private final int PHOTO_ALBUM_CODE = 0x223;
    private List<String> pics = new ArrayList<>();
    private CountDownTimer countDownTimer;
    private BuyAndSellDetailBean bean;

    @Override
    public int getLayoutId() {
        return R.layout.activity_sell_order_details;
    }

    @Override
    public void initData() {
        Intent intent = getIntent();
        id = intent.getIntExtra("id", 0);
        status = intent.getIntExtra("status", -1);
        sellOrderDetailsTitle.setText("出售");

        presenter.initData(id);

        sellOrderDetailsText2.setVisibility(View.VISIBLE);
    }

    @Override
    public void initClick() {
        sellOrderDetailsBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        sellOrderDetailsUploadPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //放大图片
                PopUtils.seeBigImg(SellActivity.this, bean.getVoucher());
//                presenter.popupWindow();
            }
        });

        sellOrderDetailsAlipayQr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopUtils.seeBigImg(SellActivity.this, bean.getPaymentCode());
            }
        });

        sellOrderDetailsPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (1 == status) {
                    LogUtil.e("确认收款的id---------"+id);
                    presenter.affirmGathering(id);
                }
            }
        });

        sellOrderDetailsCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.cancel(id);
            }
        });


    }

    @Override
    public SellView createView() {
        return this;
    }

    @Override
    public SellPresenter createPresenter() {
        return new SellPresenter(this);
    }

    @Override
    public void loadData(BuyAndSellDetailBean bean) {
        this.bean = bean;
        if (StringUtil.isBlank(bean.getOrderNumber())) {
            //0求购中 1待支付 2待确认 3已完成 4 申诉 5取消 冻结 如果订单号不存在使用这个
            if (0 == status) {
                sellOrderDetailsRelative3.setVisibility(View.GONE);
                sellOrderDetailsRelative4.setVisibility(View.GONE);
                sellOrderDetailsTime.setVisibility(View.INVISIBLE);
                sellOrderDetailsAlipayLinear.setVisibility(View.GONE);
                sellOrderDetailsAlipayAccount.setVisibility(View.GONE);
                sellOrderDetailsCancel.setVisibility(View.VISIBLE);
                sellOrderDetailsPay.setText("正在出售");
            } else if (5 == status) {
                sellOrderDetailsRelative3.setVisibility(View.GONE);
                sellOrderDetailsRelative4.setVisibility(View.GONE);
                sellOrderDetailsAlipayLinear.setVisibility(View.GONE);
                sellOrderDetailsAlipayAccount.setVisibility(View.GONE);
                sellOrderDetailsBottomLinear.setVisibility(View.GONE);
            }

        } else {
            //0待支付 1待确认 2已完成 3 申诉 4取消 5冻结 如果订单号存在存在使用这个
            if (0 == status) {
                sellOrderDetailsRelative4.setVisibility(View.GONE);
            } else if (1 == status) {
                sellOrderDetailsPay.setText("确认收款");
                sellOrderDetailsText3.setText("支付凭证：");
                sellOrderDetailsComplaint.setVisibility(View.VISIBLE);
            } else {
                sellOrderDetailsBottomLinear.setVisibility(View.GONE);
            }
        }


        sellOrderDetailsOrderAccount.setText(bean.getOrderNumber());
        sellOrderDetailsSingularization.setText(bean.getNumber() + "");
        sellOrderDetailsTaskEarnings.setText("￥" + bean.getTotalPrice());
        sellOrderDetailsOrderTime.setText(bean.getCreateTime());

        sellOrderDetailsHead.setImageURI(bean.getSellUserCode() == null ? "" : bean.getSellUserCode());
        sellOrderDetailsName.setText(Html.fromHtml("姓名:<font color= '#FF0000'>" + bean.getSellUserName() + "</font>"));
        sellOrderDetailsSuperiorId.setText("ID：" + bean.getId());
        sellOrderDetailsAlipayAccount.setText("支付宝账号：" + bean.getOpenid() + "");
        sellOrderDetailsAlipayQr.setImageURI(Uri.parse(bean.getPaymentCode() == null ? "" : bean.getPaymentCode()));

        sellOrderDetailsHead1.setImageURI(bean.getIcon() == null ? "" : bean.getIcon());
        sellOrderDetailsName1.setText("买家：" + bean.getBuyUserName());
        sellOrderDetailsSuperiorId1.setText("ID：" + bean.getBuyPhone());

        sellOrderDetailsUploadPic.setImageURI(bean.getVoucher() == null ? "" : bean.getVoucher());

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
        sellOrderDetailsUploadPic.setImageURI(uri);
        pics.add(base64);
    }

    @Override
    public void loadTime(String confirmTime) {
        LogUtil.e("时间倒计时");
        if (!StringUtil.isBlank(bean.getOrderNumber())) {
            if (1 == status) {
                //获取当前时间戳
                long nowTime = System.currentTimeMillis();
                //得到订单创建时间戳
                long timeStamp = MyTimeUtil.getTimeStamp(bean.getCreateTime(), "yyyy-MM-dd HH:mm:ss");
                long endTime = timeStamp + Long.parseLong(confirmTime) * 1000;
                long consumeTime = endTime - nowTime;

                countDownTimer = new CountDownTimer(consumeTime, 1000) {//第一个参数表示总时间，第二个参数表示间隔时间。
                    @Override
                    public void onTick(long millisUntilFinished) {
                        SimpleDateFormat formatter = new SimpleDateFormat("HH小时mm分ss秒", Locale.CHINA);
                        formatter.setTimeZone(TimeZone.getTimeZone("GMT+00:00"));
                        String dateString = formatter.format(new Date(millisUntilFinished));
                        sellOrderDetailsTime.setText("剩余时间:" + dateString);
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
