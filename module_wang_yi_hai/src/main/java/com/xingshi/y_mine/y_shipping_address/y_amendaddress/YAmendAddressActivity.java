package com.xingshi.y_mine.y_shipping_address.y_amendaddress;

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
import com.xingshi.bean.AmendAddressBean;
import com.xingshi.bean.ShippingAddressBean;
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

import java.util.List;

import butterknife.BindView;
import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

/**
 * 区块修改地址
 */
public class YAmendAddressActivity extends BaseActivity<YAmendAddressView, YAmendAddressPresenter> implements YAmendAddressView {

    @BindView(R2.id.y_amend_address_image_back)
    ImageView yAmendAddressImageBack;
    @BindView(R2.id.y_amend_address_name)
    EditText yAmendAddressName;
    @BindView(R2.id.y_amend_address_phone)
    EditText yAmendAddressPhone;
    @BindView(R2.id.y_amend_address_province)
    TextView yAmendAddressProvince;
    @BindView(R2.id.y_amend_address_city)
    TextView yAmendAddressCity;
    @BindView(R2.id.y_amend_address_area)
    TextView yAmendAddressArea;
    @BindView(R2.id.y_amend_address_where)
    LinearLayout yAmendAddressWhere;
    @BindView(R2.id.y_amend_address_detailed)
    EditText yAmendAddressDetailed;
    @BindView(R2.id.y_amend_address_home)
    RadioButton yAmendAddressHome;
    @BindView(R2.id.y_amend_address_company)
    RadioButton yAmendAddressCompany;
    @BindView(R2.id.y_amend_address_school)
    RadioButton yAmendAddressSchool;
    @BindView(R2.id.y_amend_address_radio)
    RadioGroup yAmendAddressRadio;
    @BindView(R2.id.y_amend_address_switch)
    Switch yAmendAddressSwitch;
    @BindView(R2.id.y_amend_address_save)
    TextView yAmendAddressSave;

    private List<ShippingAddressBean> shippingAddressBeanList;
    private int position;

    @Override
    public int getLayoutId() {
        return R.layout.activity_yamend_address;
    }

    @Override
    public void initData() {
        position = getIntent().getIntExtra("position", 0);
        shippingAddressBeanList = (List<ShippingAddressBean>) getIntent().getSerializableExtra("shippingAddressBeanList");


        yAmendAddressName.setText(shippingAddressBeanList.get(position).getAddressName());
        yAmendAddressPhone.setText(shippingAddressBeanList.get(position).getAddressPhone());
        yAmendAddressProvince.setText(shippingAddressBeanList.get(position).getAddressProvince());
        yAmendAddressCity.setText(shippingAddressBeanList.get(position).getAddressCity());
        yAmendAddressArea.setText(shippingAddressBeanList.get(position).getAddressArea());
        yAmendAddressDetailed.setText(shippingAddressBeanList.get(position).getAddressDetail());

        if (shippingAddressBeanList.get(position).getAddressTips() == 1) {
            yAmendAddressHome.setChecked(true);
        } else if (shippingAddressBeanList.get(position).getAddressTips() == 2) {
            yAmendAddressCompany.setChecked(true);
        } else {
            yAmendAddressSchool.setChecked(true);
        }

        if (shippingAddressBeanList.get(position).getAddressDefault() == 1) {
            yAmendAddressSwitch.setChecked(true);
        } else {
            yAmendAddressSwitch.setChecked(false);
        }

    }

    @Override
    public void initClick() {
        yAmendAddressImageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        yAmendAddressWhere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.popupAddressWhere(yAmendAddressProvince, yAmendAddressCity, yAmendAddressArea);
            }
        });

        yAmendAddressSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(yAmendAddressName.getText().toString())) {
                    Toast.makeText(YAmendAddressActivity.this, "请输入姓名", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(yAmendAddressPhone.getText().toString())) {
                    Toast.makeText(YAmendAddressActivity.this, "手机号不能为空", Toast.LENGTH_SHORT).show();
                } else if (!PhoneNumUtil.isMobileNO(yAmendAddressPhone.getText().toString())) {
                    Toast.makeText(YAmendAddressActivity.this, "手机号格式不正确", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(yAmendAddressCity.getText().toString()) && TextUtils.isEmpty(yAmendAddressArea.getText().toString())) {
                    Toast.makeText(YAmendAddressActivity.this, "请选择地址", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(yAmendAddressDetailed.getText().toString())) {
                    Toast.makeText(YAmendAddressActivity.this, "请填写详细地址", Toast.LENGTH_SHORT).show();
                } else {
                    AmendAddressBean amendAddressBean = new AmendAddressBean();
                    amendAddressBean.setId(shippingAddressBeanList.get(position).getId());
                    amendAddressBean.setUserCode(shippingAddressBeanList.get(position).getUserCode());
                    LogUtil.e("userCode------>" + shippingAddressBeanList.get(position).getUserCode());
                    amendAddressBean.setAddressName(yAmendAddressName.getText().toString());
                    amendAddressBean.setAddressPhone(yAmendAddressPhone.getText().toString());
                    amendAddressBean.setAddressProvince(yAmendAddressProvince.getText().toString());
                    amendAddressBean.setAddressCity(yAmendAddressCity.getText().toString());
                    amendAddressBean.setAddressArea(yAmendAddressArea.getText().toString());
                    amendAddressBean.setAddressDetail(yAmendAddressDetailed.getText().toString());
                    if (yAmendAddressHome.isChecked()) {
                        amendAddressBean.setAddressTips(1);
                    } else if (yAmendAddressCompany.isChecked()) {
                        amendAddressBean.setAddressTips(2);
                    } else if (yAmendAddressSchool.isChecked()) {
                        amendAddressBean.setAddressTips(3);
                    }
                    if (yAmendAddressSwitch.isChecked()) {
                        amendAddressBean.setAddressDefault(1);
                    } else {
                        amendAddressBean.setAddressDefault(0);
                    }
                    String amendAddressJson = JSON.toJSONString(amendAddressBean);
                    LogUtil.e("AmendAddressActivityJson----------->" + amendAddressJson);
                    RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), amendAddressJson);
                    Observable<ResponseBody> responseBodyObservable = RetrofitUtil.getInstance().getApi(CommonResource.BASEURL_4001).putDataBody(CommonResource.AMENDADDRESS, body, SPUtil.getToken());
                    RetrofitUtil.getInstance().toSubscribe(responseBodyObservable, new OnMyCallBack(new OnDataListener() {
                        @Override
                        public void onSuccess(String result, String msg) {
                            LogUtil.e("AmendAddressActivityResult----------->" + result);
                            finish();
                        }

                        @Override
                        public void onError(String errorCode, String errorMsg) {
                            LogUtil.e("AmendAddressActivityErrorMsg----------->" + errorMsg);
                        }
                    }));

                }
            }
        });
    }

    @Override
    public YAmendAddressView createView() {
        return this;
    }

    @Override
    public YAmendAddressPresenter createPresenter() {
        return new YAmendAddressPresenter(this);
    }

}
