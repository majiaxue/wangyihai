package com.xingshi.y_mine.y_personal_details;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.xingshi.bean.YPersonalDetailsBean;
import com.xingshi.mvp.BaseActivity;
import com.xingshi.utils.ImageUtil;
import com.xingshi.y_main.R;
import com.xingshi.y_main.R2;
import com.xingshi.y_mine.y_personal_details.y_change_phone.YChangePhoneActivity;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;

/**
 * 区块个人信息
 */
@Route(path = "/module_wang_yi_hai/y_personal_details/YPersonalDetailsActivity")
public class YPersonalDetailsActivity extends BaseActivity<YPersonalDetailsView, YPersonalDetailsPresenter> implements YPersonalDetailsView {

    @BindView(R2.id.y_personal_details_back)
    ImageView yPersonalDetailsBack;
    @BindView(R2.id.y_personal_details_phone)
    TextView yPersonalDetailsPhone;
    @BindView(R2.id.y_personal_details_phone_linear)
    LinearLayout yPersonalDetailsPhoneLinear;
    @BindView(R2.id.y_personal_details_name)
    TextView yPersonalDetailsName;
    @BindView(R2.id.y_personal_details_identity_card)
    TextView yPersonalDetailsIdentityCard;
    @BindView(R2.id.y_personal_details_referrer)
    TextView yPersonalDetailsReferrer;
    @BindView(R2.id.y_personal_details_WeChat_account)
    EditText yPersonalDetailsWeChatAccount;
    @BindView(R2.id.y_personal_details_weChat_qr)
    ImageView yPersonalDetailsWeChatQr;
    @BindView(R2.id.y_personal_details_aliPay_account)
    EditText yPersonalDetailsAliPayAccount;
    @BindView(R2.id.y_personal_details_aliPay_qr)
    ImageView yPersonalDetailsAliPayQr;
    @BindView(R2.id.y_personal_details_commit)
    TextView yPersonalDetailsCommit;

    private final int TAKE_PHOTO_CODE = 0x112;
    private final int PHOTO_ALBUM_CODE = 0x223;
    private int type;
    private Map<String, Object> map = new HashMap<>();
    private String base64;

    @Override
    public int getLayoutId() {
        return R.layout.activity_ypersonal_details;
    }

    @Override
    public void initData() {
        presenter.initData();
    }

    @Override
    public void initClick() {
        yPersonalDetailsBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //修改手机号
        yPersonalDetailsPhoneLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(YPersonalDetailsActivity.this, YChangePhoneActivity.class));
            }
        });
        //微信二维码
        yPersonalDetailsWeChatQr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.popupWindow();
                type = 0;
            }
        });

        //支付宝二维码
        yPersonalDetailsAliPayQr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.popupWindow();
                type = 1;
            }
        });
        //提交
        yPersonalDetailsCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(yPersonalDetailsWeChatAccount.getText().toString())) {
                    Toast.makeText(YPersonalDetailsActivity.this, "请填写微信账号", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty((String) map.get("0"))) {
                    Toast.makeText(YPersonalDetailsActivity.this, "请上传微信二维码", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(yPersonalDetailsAliPayAccount.getText().toString())) {
                    Toast.makeText(YPersonalDetailsActivity.this, "请填写支付宝账号", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty((String) map.get("1"))) {
                    Toast.makeText(YPersonalDetailsActivity.this, "请上传支付宝二维码", Toast.LENGTH_SHORT).show();
                } else {
                    presenter.commit(yPersonalDetailsPhone.getText().toString(), yPersonalDetailsWeChatAccount.getText().toString(), (String) map.get("0")
                            , yPersonalDetailsAliPayAccount.getText().toString(), (String) map.get("1"));
                }
            }
        });

    }

    @Override
    public YPersonalDetailsView createView() {
        return this;
    }

    @Override
    public YPersonalDetailsPresenter createPresenter() {
        return new YPersonalDetailsPresenter(this);
    }

    @Override
    public void loadData(YPersonalDetailsBean yPersonalDetailsBean) {
        if (1 == yPersonalDetailsBean.getEditStatus()) {
            yPersonalDetailsCommit.setVisibility(View.GONE);
        } else {
            yPersonalDetailsCommit.setVisibility(View.VISIBLE);
        }
        yPersonalDetailsPhone.setText(yPersonalDetailsBean.getPhone());
        yPersonalDetailsName.setText(yPersonalDetailsBean.getUserName());
        yPersonalDetailsIdentityCard.setText(yPersonalDetailsBean.getIdNum());
        yPersonalDetailsReferrer.setText(yPersonalDetailsBean.getRecommendName());
        yPersonalDetailsWeChatAccount.setText(yPersonalDetailsBean.getWeixinOpenid());

        if (yPersonalDetailsBean.getWeixinPaymentCode() != null) {
            yPersonalDetailsWeChatQr.setImageURI(Uri.parse(yPersonalDetailsBean.getWeixinPaymentCode() == null ? "" : yPersonalDetailsBean.getWeixinPaymentCode()));
        }

        yPersonalDetailsAliPayAccount.setText(yPersonalDetailsBean.getAliOpenid());
        if (yPersonalDetailsBean.getAliPaymentCode() != null) {
            yPersonalDetailsAliPayQr.setImageURI(Uri.parse(yPersonalDetailsBean.getAliPaymentCode() == null ? "" : yPersonalDetailsBean.getAliPaymentCode()));
        }
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
    public void imageUri(Uri uri) {
        try {
            Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(uri));
            base64 = ImageUtil.bitmapToBase64(bitmap);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        if (0 == type) {
            yPersonalDetailsWeChatQr.setImageURI(uri);
            map.put("0", base64 + "");
        } else {
            yPersonalDetailsAliPayQr.setImageURI(uri);
            map.put("1", base64 + "");
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
}
