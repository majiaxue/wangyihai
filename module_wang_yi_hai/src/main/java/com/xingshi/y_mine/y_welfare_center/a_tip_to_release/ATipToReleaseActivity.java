package com.xingshi.y_mine.y_welfare_center.a_tip_to_release;

import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xingshi.mvp.BaseActivity;
import com.xingshi.utils.LogUtil;
import com.xingshi.view.CustomHtml;
import com.xingshi.view.FontStyle;
import com.xingshi.view.FontStylePanel;
import com.xingshi.view.RichEditText;
import com.xingshi.y_main.R;
import com.xingshi.y_main.R2;
import com.facebook.drawee.view.SimpleDraweeView;

import butterknife.BindView;

/**
 * 线报发布
 */
public class ATipToReleaseActivity extends BaseActivity<ATipToReleaseView, ATipToReleasePresenter> implements ATipToReleaseView, FontStylePanel.OnFontPanelListener
        ,RichEditText.OnSelectChangeListener {


    @BindView(R2.id.a_tip_to_release_image_back)
    ImageView aTipToReleaseImageBack;
    @BindView(R2.id.a_tip_to_release_relative)
    RelativeLayout aTipToReleaseRelative;
    @BindView(R2.id.a_tip_to_release_edit_title)
    EditText aTipToReleaseEditTitle;
    @BindView(R2.id.a_tip_to_release_pic)
    SimpleDraweeView aTipToReleasePic;
    @BindView(R2.id.a_tip_to_release_edit_content)
    EditText aTipToReleaseEditContent;
    @BindView(R2.id.a_tip_to_release_time)
    TextView aTipToReleaseTime;
    @BindView(R2.id.a_tip_to_release_choose_time)
    LinearLayout aTipToReleaseChooseTime;
    @BindView(R2.id.a_tip_to_release_edit_price)
    EditText aTipToReleaseEditPrice;
    @BindView(R2.id.a_tip_to_release_edit_stockholder_price)
    EditText aTipToReleaseEditStockholderPrice;
    @BindView(R2.id.a_tip_to_release_immediately_release)
    TextView aTipToReleaseImmediatelyRelease;
    @BindView(R2.id.rich_text)
    RichEditText rich_text;
    @BindView(R2.id.bt)
    Button bt;
    private final int TAKE_PHOTO_CODE = 0x114;
    private final int PHOTO_ALBUM_CODE = 0x224;
    private String base64;

    @Override
    public int getLayoutId() {
        return R.layout.activity_atip_to_release;
    }

    @Override
    public void initData() {
        presenter.initTimePicker();
    }

    @Override
    public void initClick() {
        aTipToReleaseImageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        aTipToReleaseImmediatelyRelease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.issue(aTipToReleaseEditTitle.getText().toString(), base64, aTipToReleaseEditContent.getText().toString(), aTipToReleaseTime.getText().toString(), aTipToReleaseEditPrice.getText().toString(), aTipToReleaseEditStockholderPrice.getText().toString());
            }
        });

        aTipToReleaseChooseTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.chooseTime();
            }
        });

        aTipToReleasePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.popupWindow();
            }
        });
    }

    @Override
    public ATipToReleaseView createView() {
        return this;
    }

    @Override
    public ATipToReleasePresenter createPresenter() {
        return new ATipToReleasePresenter(this);
    }

    @Override
    public void chooseTime(String time) {
        aTipToReleaseTime.setText(time);
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
    public void showHeader(String bitmap, Uri uri) {
        this.base64 = bitmap;
        aTipToReleasePic.setImageURI(uri);
    }

    @Override
    public void imagePath(String s) {
        rich_text.setImg(s);
        String content = CustomHtml.toHtml(rich_text.getEditableText(), CustomHtml.TO_HTML_PARAGRAPH_LINES_CONSECUTIVE);
        LogUtil.e("这是内容"+content);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
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
    public void onFontStyleChang(FontStyle fontStyle) {

    }

    @Override
    public void onSelect(int start, int end) {

    }

    @Override
    public void setBold(boolean isBold) {

    }

    @Override
    public void setItalic(boolean isItalic) {

    }

    @Override
    public void insertImg() {

    }
}
