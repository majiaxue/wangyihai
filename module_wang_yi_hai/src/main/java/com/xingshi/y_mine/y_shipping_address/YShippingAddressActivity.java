package com.xingshi.y_mine.y_shipping_address;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xingshi.mvp.BaseActivity;
import com.xingshi.y_mine.y_shipping_address.y_address.YAddressActivity;
import com.xingshi.y_main.R;
import com.xingshi.y_main.R2;

import butterknife.BindView;

/**
 * 区块收货地址
 */
public class YShippingAddressActivity extends BaseActivity<YShippingAddressView, YShippingAddressPresenter> implements YShippingAddressView {

    @BindView(R2.id.y_shipping_address_back)
    ImageView yShippingAddressBack;
    @BindView(R2.id.y_shipping_address_no_address)
    LinearLayout yShippingAddressNoAddress;
    @BindView(R2.id.y_shipping_address_rec)
    RecyclerView yShippingAddressRec;
    @BindView(R2.id.y_shipping_address_button)
    TextView yShippingAddressButton;
    private String from;

    @Override
    public int getLayoutId() {
        return R.layout.activity_yshipping_address;
    }

    @Override
    public void initData() {
        from = getIntent().getStringExtra("from");
    }

    @Override
    public void initClick() {
        yShippingAddressBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //添加地址
        yShippingAddressButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               startActivity(new Intent(YShippingAddressActivity.this, YAddressActivity.class));
            }
        });
    }

    @Override
    public YShippingAddressView createView() {
        return this;
    }

    @Override
    public YShippingAddressPresenter createPresenter() {
        return new YShippingAddressPresenter(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.setShippingAddressRec(yShippingAddressRec, from);
    }

}
