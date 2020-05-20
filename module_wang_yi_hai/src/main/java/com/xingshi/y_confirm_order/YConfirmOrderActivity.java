package com.xingshi.y_confirm_order;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xingshi.bean.OrderConfirmBean;
import com.xingshi.bean.ShippingAddressBean;
import com.xingshi.mvp.BaseActivity;
import com.xingshi.utils.ClickUtil;
import com.xingshi.y_main.R;
import com.xingshi.y_main.R2;
import com.xingshi.y_mine.y_shipping_address.YShippingAddressActivity;
import com.facebook.drawee.view.SimpleDraweeView;

import butterknife.BindView;

/**
 * 区块确认订单
 */
public class YConfirmOrderActivity extends BaseActivity<YConfirmOrderView, YConfirmOrderPresenter> implements YConfirmOrderView {

    @BindView(R2.id.y_confirm_order_back)
    ImageView yConfirmOrderBack;
    @BindView(R2.id.y_confirm_order_image)
    ImageView yConfirmOrderImage;
    @BindView(R2.id.y_confirm_order_name)
    TextView yConfirmOrderName;
    @BindView(R2.id.y_confirm_order_phone)
    TextView yConfirmOrderPhone;
    @BindView(R2.id.y_confirm_order_detail)
    TextView yConfirmOrderDetail;
    @BindView(R2.id.y_confirm_order_choose_address)
    TextView yConfirmOrderChooseAddress;
    @BindView(R2.id.y_confirm_order_relative)
    RelativeLayout yConfirmOrderRelative;
    @BindView(R2.id.y_confirm_order_pic)
    SimpleDraweeView yConfirmOrderPic;
    @BindView(R2.id.y_confirm_order_title)
    TextView yConfirmOrderTitle;
    @BindView(R2.id.y_confirm_order_quantity)
    TextView yConfirmOrderQuantity;
    @BindView(R2.id.y_confirm_order_price)
    TextView yConfirmOrderPrice;
    @BindView(R2.id.y_confirm_order_original_price)
    TextView yConfirmOrderOriginalPrice;
    @BindView(R2.id.y_confirm_order_number_total_price)
    TextView yConfirmOrderNumberTotalPrice;
    @BindView(R2.id.y_confirm_order_confirm)
    TextView yConfirmOrderConfirm;

    private OrderConfirmBean confirmBean;

    @Override
    public int getLayoutId() {
        return R.layout.activity_yconfirm_order;
    }

    @Override
    public void initData() {
        Intent intent = getIntent();
        confirmBean = (OrderConfirmBean) intent.getSerializableExtra("order");
        yConfirmOrderPic.setImageURI(confirmBean.getPic());
        yConfirmOrderTitle.setText(confirmBean.getProductName());
        yConfirmOrderQuantity.setText("数量: " + confirmBean.getQuantity());
        yConfirmOrderPrice.setText("￥ " + confirmBean.getPrice());
        yConfirmOrderOriginalPrice.setText("￥ " + confirmBean.getProductPrice());
        yConfirmOrderNumberTotalPrice.setText("共" + confirmBean.getQuantity() + "件  合计：￥" + (confirmBean.getQuantity() * confirmBean.getPrice()));
        presenter.getAddress();
    }

    @Override
    public void initClick() {
        yConfirmOrderBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        yConfirmOrderRelative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //选择地址
                Intent intent = new Intent(YConfirmOrderActivity.this, YShippingAddressActivity.class);
                intent.putExtra("from", "order");
                startActivityForResult(intent, 123);
            }
        });

        //确认订单
        yConfirmOrderConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ClickUtil.isFastClick()){
                    presenter.submit(confirmBean);
                }
            }
        });


    }

    @Override
    public YConfirmOrderView createView() {
        return this;
    }

    @Override
    public YConfirmOrderPresenter createPresenter() {
        return new YConfirmOrderPresenter(this);
    }

    @Override
    public void noAddress() {
        yConfirmOrderChooseAddress.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null) {
            ShippingAddressBean addressBean = (ShippingAddressBean) data.getSerializableExtra("address");
            presenter.addressBean = addressBean;
            loadAddress(addressBean);
        }
    }

    @Override
    public void loadAddress(ShippingAddressBean addressBean) {
        yConfirmOrderName.setText(addressBean.getAddressName());
        yConfirmOrderPhone.setText(addressBean.getAddressPhone());
        yConfirmOrderDetail.setText(addressBean.getAddressProvince() + addressBean.getAddressCity() + addressBean.getAddressArea() + addressBean.getAddressDetail());

        confirmBean.setReceiverName(addressBean.getAddressName());
        confirmBean.setReceiverPhone(addressBean.getAddressPhone());
        confirmBean.setReceiverProvince(addressBean.getAddressProvince());
        confirmBean.setReceiverCity(addressBean.getAddressCity());
        confirmBean.setReceiverRegion(addressBean.getAddressArea());
        confirmBean.setOrderAddress(addressBean.getAddressDetail());
        yConfirmOrderChooseAddress.setVisibility(View.GONE);

    }
}
