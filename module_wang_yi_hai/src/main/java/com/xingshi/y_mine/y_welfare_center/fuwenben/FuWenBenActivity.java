package com.xingshi.y_mine.y_welfare_center.fuwenben;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.xingshi.mvp.BaseActivity;
import com.xingshi.utils.LogUtil;
import com.xingshi.view.RichEditText;
import com.xingshi.y_main.R;
import com.xingshi.y_main.R2;

import butterknife.BindView;
import butterknife.ButterKnife;

@Route(path = "/model_wangyihai/FuWenBenActivity")
public class FuWenBenActivity extends BaseActivity<FuWenBenView, FuWenBenPresenter> implements FuWenBenView {
    @BindView(R2.id.rich_text)
    RichEditText richText;
    @BindView(R2.id.bt)
    Button bt;
    private final int PHOTO_ALBUM_CODE = 0x222;
    private final int TAKE_PHOTO_CODE = 0x111;

    @Override
    public int getLayoutId() {
        return R.layout.activity_fuwenben;
    }

    @Override
    public void initData() {
    }

    @Override
    public void initClick() {
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.addPic();
            }
        });
    }

    @Override
    public FuWenBenView createView() {
        return this;
    }

    @Override
    public FuWenBenPresenter createPresenter() {
        return new FuWenBenPresenter(this);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {
            return;
        }
        switch (requestCode) {
            case TAKE_PHOTO_CODE:
                presenter.updateList();
                break;
            case PHOTO_ALBUM_CODE:
                presenter.parseUri(data);
                break;
        }
    }

    @Override
    public void photoAlbum(Intent intent) {
        startActivityForResult(intent, PHOTO_ALBUM_CODE);
    }

    @Override
    public void takePhoto(Intent captureIntent) {
        startActivityForResult(captureIntent, TAKE_PHOTO_CODE);
    }

    @Override
    public void imagePath(String s) {
        LogUtil.e("图片path----------"+s);
    }
}
