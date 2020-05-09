package com.xingshi.y_deal.release_order;

import android.support.design.widget.TabLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ali.auth.third.core.util.StringUtil;
import com.xingshi.mvp.BaseActivity;
import com.xingshi.y_main.R;
import com.xingshi.y_main.R2;

import butterknife.BindView;

/**
 * 发布买卖单
 */
public class ReleaseOrderActivity extends BaseActivity<ReleaseOrderView, ReleaseOrderPresenter> implements ReleaseOrderView {


    @BindView(R2.id.release_order_back)
    ImageView releaseOrderBack;
    @BindView(R2.id.release_order_tab)
    TabLayout releaseOrderTab;
    @BindView(R2.id.release_order_release_the_check_price)
    EditText releaseOrderReleaseTheCheckPrice;
    @BindView(R2.id.release_order_release_the_check_num)
    EditText releaseOrderReleaseTheCheckNum;
    @BindView(R2.id.release_order_release_the_check_amount)
    TextView releaseOrderReleaseTheCheckAmount;
    @BindView(R2.id.release_order_release_the_check_submit)
    TextView releaseOrderReleaseTheCheckSubmit;
    @BindView(R2.id.release_order_release_the_check_linear)
    LinearLayout releaseOrderReleaseTheCheckLinear;
    @BindView(R2.id.release_order_release_sale_price)
    EditText releaseOrderReleaseSalePrice;
    @BindView(R2.id.release_order_release_sale_num)
    EditText releaseOrderReleaseSaleNum;
    @BindView(R2.id.release_order_release_sale_sell_amount)
    TextView releaseOrderReleaseSaleSellAmount;
    @BindView(R2.id.release_order_release_sale_service_charge)
    TextView releaseOrderReleaseSaleServiceCharge;
    @BindView(R2.id.release_order_release_sale_submit)
    TextView releaseOrderReleaseSaleSubmit;
    @BindView(R2.id.release_order_release_sale_linear)
    LinearLayout releaseOrderReleaseSaleLinear;
    @BindView(R2.id.release_order_release_the_check_rule)
    TextView releaseOrderReleaseTheCheckRule;
    @BindView(R2.id.release_order_release_sale_rule)
    TextView releaseOrderReleaseSaleRule;

    private int totalAmount;
    private double serviceCharge;

    @Override
    public int getLayoutId() {
        return R.layout.activity_release_order;
    }

    @Override
    public void initData() {
        presenter.initTab(releaseOrderTab);

        presenter.serviceCharge();

        presenter.buyRule();
    }

    @Override
    public void initClick() {
        releaseOrderBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        releaseOrderReleaseTheCheckSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.releasePayTheBill(releaseOrderReleaseTheCheckPrice.getText().toString(), releaseOrderReleaseTheCheckNum.getText().toString(), totalAmount);
            }
        });

        releaseOrderReleaseSaleSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.releaseSellOrders(releaseOrderReleaseSalePrice.getText().toString(), releaseOrderReleaseSaleNum.getText().toString(), totalAmount);
            }
        });

        releaseOrderReleaseTheCheckPrice.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().length() > 0) {
                    String num = StringUtil.isBlank(releaseOrderReleaseTheCheckNum.getText().toString()) ? "0" : releaseOrderReleaseTheCheckNum.getText().toString();
                    //edit内容大于0
                    if (!"0".equals(num)) {
                        totalAmount = Integer.parseInt(num) * Integer.parseInt(releaseOrderReleaseTheCheckPrice.getText().toString());
                        releaseOrderReleaseTheCheckAmount.setText(totalAmount + "");
                    } else {
                        releaseOrderReleaseTheCheckAmount.setText("0");
                    }
                } else {
                    //edit内容等于0
                    releaseOrderReleaseTheCheckAmount.setText("0");
                }
            }
        });

        releaseOrderReleaseTheCheckNum.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().length() > 0) {
                    String price = StringUtil.isBlank(releaseOrderReleaseTheCheckPrice.getText().toString()) ? "0" : releaseOrderReleaseTheCheckPrice.getText().toString();
                    //edit内容大于0
                    if (!"0".equals(price)) {
                        totalAmount = Integer.parseInt(releaseOrderReleaseTheCheckNum.getText().toString()) * Integer.parseInt(price);
                        releaseOrderReleaseTheCheckAmount.setText(totalAmount + "");
                    } else {
                        releaseOrderReleaseTheCheckAmount.setText("0");
                    }
                } else {
                    //edit内容等于0
                    releaseOrderReleaseTheCheckAmount.setText("0");
                }
            }
        });

        releaseOrderReleaseSalePrice.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().length() > 0) {
                    //edit内容大于0
                    String num = StringUtil.isBlank(releaseOrderReleaseSaleNum.getText().toString()) ? "0" : releaseOrderReleaseSaleNum.getText().toString();
                    if (!"0".equals(num)) {
                        totalAmount = Integer.parseInt(num) * Integer.parseInt(releaseOrderReleaseSalePrice.getText().toString());
                        releaseOrderReleaseSaleSellAmount.setText(totalAmount + "");
                        releaseOrderReleaseSaleServiceCharge.setText((serviceCharge * totalAmount) + "");
                    } else {
                        releaseOrderReleaseSaleSellAmount.setText("0");
                        releaseOrderReleaseSaleServiceCharge.setText("0");
                    }
                } else {
                    //edit内容等于0
                    releaseOrderReleaseSaleSellAmount.setText("0");
                    releaseOrderReleaseSaleServiceCharge.setText("0");
                }
            }
        });

        releaseOrderReleaseSaleNum.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().length() > 0) {
                    //edit内容大于0
                    String price = StringUtil.isBlank(releaseOrderReleaseSalePrice.getText().toString()) ? "0" : releaseOrderReleaseSalePrice.getText().toString();
                    if (!"0".equals(price)) {
                        totalAmount = Integer.parseInt(price) * Integer.parseInt(releaseOrderReleaseSaleNum.getText().toString());
                        releaseOrderReleaseSaleSellAmount.setText(totalAmount + "");
                        releaseOrderReleaseSaleServiceCharge.setText((serviceCharge * totalAmount) + "");
                    } else {
                        releaseOrderReleaseSaleSellAmount.setText("0");
                        releaseOrderReleaseSaleServiceCharge.setText("0");
                    }
                } else {
                    //edit内容等于0
                    releaseOrderReleaseSaleSellAmount.setText("0");
                    releaseOrderReleaseSaleServiceCharge.setText("0");
                }
            }
        });


    }

    @Override
    public ReleaseOrderView createView() {
        return this;
    }

    @Override
    public ReleaseOrderPresenter createPresenter() {
        return new ReleaseOrderPresenter(this);
    }

    @Override
    public void checkoutLinear(boolean checkout) {
        if (checkout) {
            releaseOrderReleaseTheCheckLinear.setVisibility(View.VISIBLE);
            releaseOrderReleaseSaleLinear.setVisibility(View.GONE);
        } else {
            releaseOrderReleaseSaleLinear.setVisibility(View.VISIBLE);
            releaseOrderReleaseTheCheckLinear.setVisibility(View.GONE);
        }
    }

    @Override
    public void serviceCharge(double serviceCharge, String max, String less) {
        this.serviceCharge = serviceCharge;
        releaseOrderReleaseSaleRule.setText("卖单最低限制" + less + "个币\n" + "买单最高限制" + max + "个币,并扣除相应手续费");
    }

    @Override
    public void serviceCharge(String max, String less) {
        releaseOrderReleaseTheCheckRule.setText("买单最低限制" + less + "个币\n" + "买单最高限制" + max + "个币");
    }
}
