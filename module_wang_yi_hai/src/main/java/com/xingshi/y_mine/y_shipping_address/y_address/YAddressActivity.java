package com.xingshi.y_mine.y_shipping_address.y_address;

import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.xingshi.bean.AddressInfo;
import com.xingshi.common.CommonResource;
import com.xingshi.mvp.BaseActivity;
import com.xingshi.net.OnDataListener;
import com.xingshi.net.OnMyCallBack;
import com.xingshi.net.RetrofitUtil;
import com.xingshi.utils.LogUtil;
import com.xingshi.utils.PhoneNumUtil;
import com.xingshi.utils.SPUtil;
import com.xingshi.y_main.R;
import com.xingshi.y_main.R2;

import butterknife.BindView;
import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

/**
 * 区块新建收货地址
 */
public class YAddressActivity extends BaseActivity<YAddressView, YAddressPresenter> implements YAddressView {


    @BindView(R2.id.y_address_back)
    ImageView yAddressBack;
    @BindView(R2.id.y_address_name)
    EditText yAddressName;
    @BindView(R2.id.y_address_phone)
    EditText yAddressPhone;
    @BindView(R2.id.y_address_province)
    TextView yAddressProvince;
    @BindView(R2.id.y_address_city)
    TextView yAddressCity;
    @BindView(R2.id.y_address_area)
    TextView yAddressArea;
    @BindView(R2.id.y_address_where)
    LinearLayout yAddressWhere;
    @BindView(R2.id.y_address_detailed)
    EditText yAddressDetailed;
    @BindView(R2.id.y_address_home)
    RadioButton yAddressHome;
    @BindView(R2.id.y_address_company)
    RadioButton yAddressCompany;
    @BindView(R2.id.y_address_school)
    RadioButton yAddressSchool;
    @BindView(R2.id.y_address_radio)
    RadioGroup yAddressRadio;
    @BindView(R2.id.y_address_switch)
    Switch yAddressSwitch;
    @BindView(R2.id.y_address_save)
    TextView yAddressSave;

    @Override
    public int getLayoutId() {
        return R.layout.activity_yaddress;
    }

    @Override
    public void initData() {
    }

    @Override
    public void initClick() {
        yAddressBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //选择地址
        yAddressWhere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.popupAddressWhere(yAddressProvince, yAddressCity, yAddressArea);
            }
        });


        //保存
        yAddressSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(yAddressName.getText().toString())) {
                    Toast.makeText(YAddressActivity.this, "请输入姓名", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(yAddressPhone.getText().toString())) {
                    Toast.makeText(YAddressActivity.this, "手机号不能为空", Toast.LENGTH_SHORT).show();
                } else if (!PhoneNumUtil.isMobileNO(yAddressPhone.getText().toString())) {
                    Toast.makeText(YAddressActivity.this, "手机号格式不正确", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(yAddressCity.getText().toString()) && TextUtils.isEmpty(yAddressArea.getText().toString())) {
                    Toast.makeText(YAddressActivity.this, "请选择地址", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(yAddressDetailed.getText().toString())) {
                    Toast.makeText(YAddressActivity.this, "请填写详细地址", Toast.LENGTH_SHORT).show();
                } else {
                    AddressInfo addressInfo = new AddressInfo();
                    addressInfo.setAddressName(yAddressName.getText().toString());
                    addressInfo.setAddressPhone(yAddressPhone.getText().toString());
                    addressInfo.setAddressProvince(yAddressProvince.getText().toString());
                    addressInfo.setAddressCity(yAddressCity.getText().toString());
                    addressInfo.setAddressArea(yAddressArea.getText().toString());
                    addressInfo.setAddressDetail(yAddressDetailed.getText().toString());
                    if (yAddressHome.isChecked()) {
                        addressInfo.setAddressTips("1");
                    } else if (yAddressCompany.isChecked()) {
                        addressInfo.setAddressTips("2");
                    } else if (yAddressSchool.isChecked()) {
                        addressInfo.setAddressTips("3");
                    } else {
                        addressInfo.setAddressTips("0");
                    }
                    if (yAddressSwitch.isChecked()) {
                        addressInfo.setAddressDefault("1");
                    } else {
                        addressInfo.setAddressDefault("0");
                    }
                    String jsonString = JSON.toJSONString(addressInfo);
                    LogUtil.e("SecondaryDetailsJson----------->" + jsonString);
                    RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonString);
                    final Observable<ResponseBody> responseBodyObservable = RetrofitUtil.getInstance().getApi(CommonResource.BASEURL_4001).postHeadWithBody(CommonResource.ADDRESSADD, body, SPUtil.getToken());
                    RetrofitUtil.getInstance().toSubscribe(responseBodyObservable, new OnMyCallBack(new OnDataListener() {
                        @Override
                        public void onSuccess(String result, String msg) {
                            LogUtil.e("AddressResult---------------->" + result);
                            if (result.equals("true")) {
                                finish();
                            }
                        }

                        @Override
                        public void onError(String errorCode, String errorMsg) {
                            LogUtil.e("AddressErrorMsg---------------->" + errorMsg);
                        }
                    }));
                }
            }
        });
    }

    @Override
    public YAddressView createView() {
        return this;
    }

    @Override
    public YAddressPresenter createPresenter() {
        return new YAddressPresenter(this);
    }

}
