package com.xingshi.y_deal.my_pay_order_details;

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
import android.widget.Toast;

import com.xingshi.bean.BuyAndSellDetailBean;
import com.xingshi.mvp.BaseActivity;
import com.xingshi.utils.LogUtil;
import com.xingshi.utils.MyTimeUtil;
import com.xingshi.utils.OnClearCacheListener;
import com.xingshi.utils.PopUtils;
import com.xingshi.utils.YPopUtil;
import com.xingshi.y_deal.ask_to_buy.AskToBuyActivity;
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
 * 买入详情
 */
public class MyPayOrderDetailsActivity extends BaseActivity<MyPayOrderDetailsView, MyPayOrderDetailsPresenter> implements MyPayOrderDetailsView {


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
    @BindView(R2.id.my_pay_order_details_relative4)
    RelativeLayout myPayOrderDetailsRelative4;
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

    private int id;//订单ID
    private int status;//订单状态
    private final int TAKE_PHOTO_CODE = 0x113;
    private final int PHOTO_ALBUM_CODE = 0x223;
    private int flag = 0;
    private SimpleDraweeView pic;
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
        myPayOrderDetailsTitle.setText("买入");

        presenter.initData(id);

        myPayOrderDetailsPay.setText("付款");
        myPayOrderDetailsText3.setText("上传支付凭证：");
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
        //投诉
        myPayOrderDetailsComplaint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                YPopUtil.payOrderComplain(MyPayOrderDetailsActivity.this, new OnClearCacheListener() {
                    @Override
                    public void setOnClearCache(PopupWindow pop, View confirm) {
                        final EditText editText = confirm.findViewById(R.id.pop_pay_order_complaint_edit);
                        pic = confirm.findViewById(R.id.pop_pay_order_complaint_pic);
                        TextView submit = confirm.findViewById(R.id.pop_pay_order_complaint_submit);
                        pic.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                presenter.openPhotoAlbum();
                                flag = 0;
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

        myPayOrderDetailsPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogUtil.e("imglist-------"+pics.toString());
                if (pics.size() != 0) {
                    presenter.payment(id);
                } else {
                    Toast.makeText(MyPayOrderDetailsActivity.this, "请上传照片", Toast.LENGTH_SHORT).show();
                }
            }
        });

        myPayOrderDetailsUploadPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //选择照片
                if (0 == status) {
                    presenter.popupWindow();
                    flag = 1;
                } else {
                    //放大图片
                    PopUtils.seeBigImg(MyPayOrderDetailsActivity.this, bean.getVoucher());
                }
            }
        });

        myPayOrderDetailsAlipayQr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopUtils.seeBigImg(MyPayOrderDetailsActivity.this, bean.getPaymentCode());
            }
        });

    }

    @Override
    public MyPayOrderDetailsView createView() {
        return this;
    }

    @Override
    public MyPayOrderDetailsPresenter createPresenter() {
        return new MyPayOrderDetailsPresenter(this);
    }

    @Override
    public void loadData(BuyAndSellDetailBean bean) {
        this.bean = bean;
        if (0 == status) {
            myPayOrderDetailsComplaint.setVisibility(View.VISIBLE);
        } else {
            myPayOrderDetailsBottomLinear.setVisibility(View.GONE);
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
        if (1 == flag) {
            myPayOrderDetailsUploadPic.setImageURI(uri);
            pics.add(base64);
        } else {
            pics.add(base64);
            LogUtil.e("图片-----"+pics.toString());
            pic.setImageURI(uri);
        }
    }

    @Override
    public void loadTime(String paymentTime) {
        if (0 == status) {
            //获取当前时间戳
            long nowTime = System.currentTimeMillis();
            //得到订单创建时间戳
            long timeStamp = MyTimeUtil.getTimeStamp(bean.getCreateTime(), "yyyy-MM-dd HH:mm:ss");
            long endTime = timeStamp + Long.parseLong(paymentTime) * 1000;
            long consumeTime = endTime - nowTime;
            LogUtil.e("倒计时时间------------>" + consumeTime);
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
