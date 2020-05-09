package com.xingshi.y_confirm_payment;

import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.xingshi.bean.OrderConfirmBean;
import com.xingshi.bean.SubmitOrderBean;
import com.xingshi.mvp.BaseActivity;
import com.xingshi.y_main.R;
import com.xingshi.y_main.R2;
import com.facebook.drawee.view.SimpleDraweeView;

import butterknife.BindView;

/**
 * 区块确认支付
 */
public class YConfirmPaymentActivity extends BaseActivity<YConfirmPaymentView, YConfirmPaymentPresenter> implements YConfirmPaymentView {

    @BindView(R2.id.y_address_back)
    ImageView yAddressBack;
    @BindView(R2.id.y_confirm_payment_pic)
    SimpleDraweeView yConfirmPaymentPic;
    @BindView(R2.id.y_confirm_payment_title)
    TextView yConfirmPaymentTitle;
    @BindView(R2.id.y_confirm_payment_quantity)
    TextView yConfirmPaymentQuantity;
    @BindView(R2.id.y_confirm_payment_price)
    TextView yConfirmPaymentPrice;
    @BindView(R2.id.y_confirm_payment_original_price)
    TextView yConfirmPaymentOriginalPrice;
    @BindView(R2.id.y_confirm_payment_total_price)
    TextView yConfirmPaymentTotalPrice;
    @BindView(R2.id.y_confirm_payment_freight)
    TextView yConfirmPaymentFreight;
    @BindView(R2.id.y_confirm_payment_actual_payment)
    TextView yConfirmPaymentActualPayment;
    @BindView(R2.id.y_confirm_payment_express_company)
    TextView yConfirmPaymentExpressCompany;
    @BindView(R2.id.y_confirm_payment_tracking_number)
    TextView yConfirmPaymentTrackingNumber;
    @BindView(R2.id.y_confirm_payment_remark)
    EditText yConfirmPaymentRemark;
    @BindView(R2.id.y_confirm_payment_consignee)
    TextView yConfirmPaymentConsignee;
    @BindView(R2.id.y_confirm_payment_consignee_phone)
    TextView yConfirmPaymentConsigneePhone;
    @BindView(R2.id.y_confirm_payment_system_currency)
    TextView yConfirmPaymentSystemCurrency;
    @BindView(R2.id.y_confirm_payment_number_total_price)
    TextView yConfirmPaymentNumberTotalPrice;
    @BindView(R2.id.y_confirm_payment_confirm)
    TextView yConfirmPaymentConfirm;

    //触碰标识
    private long exitTime = 0;
    private SubmitOrderBean submitOrderBean;
    private OrderConfirmBean orderConfirmBean;

    @Override
    public int getLayoutId() {
        return R.layout.activity_yconfirm_payment;
    }

    @Override
    public void initData() {
        submitOrderBean = (SubmitOrderBean) getIntent().getSerializableExtra("submitOrderBean");
        orderConfirmBean = (OrderConfirmBean) getIntent().getSerializableExtra("OrderConfirmBean");
        yConfirmPaymentPic.setImageURI(orderConfirmBean.getPic());
        yConfirmPaymentTitle.setText(orderConfirmBean.getProductName());
        yConfirmPaymentQuantity.setText("数量: " + orderConfirmBean.getQuantity());
        yConfirmPaymentPrice.setText("￥" + orderConfirmBean.getPrice());
        yConfirmPaymentOriginalPrice.setText("￥" + orderConfirmBean.getProductPrice());

        yConfirmPaymentTotalPrice.setText("￥" + submitOrderBean.getTotalAmount());
        yConfirmPaymentActualPayment.setText("￥" + submitOrderBean.getTotalAmount());
        yConfirmPaymentConsignee.setText(orderConfirmBean.getReceiverName());
        yConfirmPaymentConsigneePhone.setText(orderConfirmBean.getReceiverPhone());

        yConfirmPaymentNumberTotalPrice.setText("共" + orderConfirmBean.getQuantity() + "件  合计：￥" + submitOrderBean.getTotalAmount());
    }

    @Override
    public void initClick() {
        yAddressBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //确认支付
        yConfirmPaymentConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((System.currentTimeMillis() - exitTime) > 3000) {
                    presenter.confirmPayment(submitOrderBean.getMasterNo());
                    exitTime = System.currentTimeMillis();
                }
            }
        });
    }

    @Override
    public YConfirmPaymentView createView() {
        return this;
    }

    @Override
    public YConfirmPaymentPresenter createPresenter() {
        return new YConfirmPaymentPresenter(this);
    }
}
