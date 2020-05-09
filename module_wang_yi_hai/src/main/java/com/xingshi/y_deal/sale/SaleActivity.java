package com.xingshi.y_deal.sale;

import android.content.Intent;
import android.net.Uri;
import android.os.CountDownTimer;
import android.text.Html;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xingshi.bean.BuyAndSellDetailBean;
import com.xingshi.mvp.BaseActivity;
import com.xingshi.utils.LogUtil;
import com.xingshi.utils.MyTimeUtil;
import com.xingshi.utils.OnClearCacheListener;
import com.xingshi.utils.PopUtils;
import com.xingshi.utils.YPopUtil;
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
 * 卖出详情
 */
public class SaleActivity extends BaseActivity<SaleView, SalePresenter> implements SaleView {

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
    @BindView(R2.id.sell_order_details_text2)
    TextView sellOrderDetailsText2;
    @BindView(R2.id.sell_order_details_text3)
    TextView sellOrderDetailsText3;
    @BindView(R2.id.sell_order_details_cancel)
    TextView sellOrderDetailsCancel;

    private int id;//商品id
    private int status;//订单状态
    private final int PHOTO_ALBUM_CODE = 0x223;
    private SimpleDraweeView pic;
    private List<String> pics = new ArrayList<>();
    private CountDownTimer countDownTimer;
    private BuyAndSellDetailBean bean;

    @Override
    public int getLayoutId() {
        return R.layout.activity_sell_order_details;
    }

    @Override
    public void initData() {
        sellOrderDetailsTitle.setText("卖出");

        Intent intent = getIntent();
        id = intent.getIntExtra("id", -1);
        status = intent.getIntExtra("status", -1);
        LogUtil.e("这是status--------"+status);
        LogUtil.e("这是id-----------"+id);
        presenter.initData(id);

        sellOrderDetailsPay.setText("确认收款");
        LogUtil.e("走了这-------");
        sellOrderDetailsText3.setText("支付凭证：");
        sellOrderDetailsText2.setVisibility(View.GONE);
    }

    @Override
    public void initClick() {
        sellOrderDetailsBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //投诉
        sellOrderDetailsComplaint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                YPopUtil.payOrderComplain(SaleActivity.this, new OnClearCacheListener() {
                    @Override
                    public void setOnClearCache(PopupWindow pop, View confirm) {
                        final EditText editText = confirm.findViewById(R.id.pop_pay_order_complaint_edit);
                        pic = confirm.findViewById(R.id.pop_pay_order_complaint_pic);
                        TextView submit = confirm.findViewById(R.id.pop_pay_order_complaint_submit);
                        pic.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                presenter.openPhotoAlbum();
                            }
                        });

                        submit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                presenter.commit(id, editText.getText().toString(), pics);
                            }
                        });

                    }
                });
            }
        });

        sellOrderDetailsPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.affirmGathering(id);
            }
        });

        sellOrderDetailsUploadPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //放大图片
                PopUtils.seeBigImg(SaleActivity.this, bean.getVoucher());
            }
        });

        sellOrderDetailsAlipayQr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopUtils.seeBigImg(SaleActivity.this, bean.getPaymentCode());
            }
        });
    }

    @Override
    public SaleView createView() {
        return this;
    }

    @Override
    public SalePresenter createPresenter() {
        return new SalePresenter(this);
    }

    @Override
    public void photoAlbum(Intent intent) {
        startActivityForResult(intent, PHOTO_ALBUM_CODE);
    }

    @Override
    public void showHeader(Uri uri, String base64) {
        pic.setImageURI(uri);
        pics.add(base64);
    }

    @Override
    public void loadData(BuyAndSellDetailBean bean) {
        this.bean = bean;
        LogUtil.e("状态----------"+status);
        if (0 == status) {
            sellOrderDetailsComplaint.setVisibility(View.GONE);
        } else {
            sellOrderDetailsBottomLinear.setVisibility(View.VISIBLE);
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
        sellOrderDetailsName1.setText("卖家：" + bean.getBuyUserName());
        sellOrderDetailsSuperiorId1.setText("ID：" + bean.getBuyPhone());

        sellOrderDetailsUploadPic.setImageURI(bean.getVoucher() == null ? "" : bean.getVoucher());

        presenter.getTime();

    }

    @Override
    public void loadTime(String confirmTime) {
        if (0 == status) {
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {
            return;
        }

        switch (requestCode) {
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
