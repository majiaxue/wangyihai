package com.xingshi.y_mine.y_welfare_center.release_a_task;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ali.auth.third.core.util.StringUtil;
import com.xingshi.mvp.BaseActivity;
import com.xingshi.y_main.R;
import com.xingshi.y_main.R2;
import com.xingshi.y_mine.y_welfare_center.release_a_task.adapter.ReleaseATaskTabAdapter;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 区块发布任务
 */
public class ReleaseATaskActivity extends BaseActivity<ReleaseATaskView, ReleaseATaskPresenter> implements ReleaseATaskView {


    @BindView(R2.id.release_a_task_back)
    ImageView releaseATaskBack;
    @BindView(R2.id.release_a_task_title_rec)
    RecyclerView releaseATaskTitleRec;
    @BindView(R2.id.release_a_task_edit_title)
    EditText releaseATaskEditTitle;
    @BindView(R2.id.release_a_task_time)
    TextView releaseATaskTime;
    @BindView(R2.id.release_a_task_choose)
    RelativeLayout releaseATaskChoose;
    @BindView(R2.id.release_a_task_edit_price)
    EditText releaseATaskEditPrice;
    @BindView(R2.id.release_a_task_edit_member_price)
    EditText releaseATaskEditMemberPrice;
    @BindView(R2.id.release_a_task_edit_shareholder_value)
    EditText releaseATaskEditShareholderValue;
    @BindView(R2.id.release_a_task_edit_num)
    EditText releaseATaskEditNum;
    @BindView(R2.id.release_a_task_edit_again_num)
    EditText releaseATaskEditAgainNum;
    @BindView(R2.id.release_a_task_edit_total)
    TextView releaseATaskEditTotal;
    @BindView(R2.id.release_a_task_review_the_figure_left)
    SimpleDraweeView releaseATaskReviewTheFigureLeft;
    @BindView(R2.id.release_a_task_review_the_figure_right)
    SimpleDraweeView releaseATaskReviewTheFigureRight;
    @BindView(R2.id.release_a_task_edit_details)
    EditText releaseATaskEditDetails;
    @BindView(R2.id.release_a_task_edit_link)
    EditText releaseATaskEditLink;
    @BindView(R2.id.release_a_task_edit_word_verification)
    EditText releaseATaskEditWordVerification;
    @BindView(R2.id.release_a_task_edit_content)
    EditText releaseATaskEditContent;
    @BindView(R2.id.release_a_task_but_affirm)
    RadioButton releaseATaskButAffirm;
    @BindView(R2.id.release_a_task_agreement)
    TextView releaseATaskAgreement;
    @BindView(R2.id.release_a_task_issue)
    TextView releaseATaskIssue;

    private List<String> imgs = new ArrayList<>();
    private int id;
    private int flag = 0;
    private final int TAKE_PHOTO_CODE = 0x115;
    private final int PHOTO_ALBUM_CODE = 0x225;
    private double totalAmount;
    @Override
    public int getLayoutId() {
        return R.layout.activity_release_atask;
    }

    @Override
    public void initData() {
        //初始化时间选择器
        presenter.initTimePicker();
        //任务类型
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 6, LinearLayoutManager.VERTICAL, false);
        releaseATaskTitleRec.setLayoutManager(gridLayoutManager);
        presenter.initTab();
    }

    @Override
    public void initClick() {
        releaseATaskBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //选择时间
        releaseATaskChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.chooseTime();
            }
        });

        releaseATaskIssue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.submit(releaseATaskEditTitle.getText().toString(), releaseATaskTime.getText().toString(), releaseATaskEditPrice.getText().toString()
                        , releaseATaskEditMemberPrice.getText().toString(), releaseATaskEditShareholderValue.getText().toString(), releaseATaskEditNum.getText().toString()
                        , releaseATaskEditAgainNum.getText().toString(), releaseATaskEditTotal.getText().toString(), imgs, releaseATaskEditDetails.getText().toString()
                        , releaseATaskEditLink.getText().toString(), releaseATaskEditWordVerification.getText().toString(), releaseATaskEditContent.getText().toString()
                        , releaseATaskButAffirm, id);
            }
        });

        releaseATaskReviewTheFigureLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.popupWindow();
                flag = 0;
            }
        });

        releaseATaskReviewTheFigureRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.popupWindow();
                flag = 1;
            }
        });

        releaseATaskEditShareholderValue.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().length() > 0) {
                    String num = StringUtil.isBlank(releaseATaskEditNum.getText().toString()) ? "0" : releaseATaskEditNum.getText().toString();
                    //edit内容大于0
                    if (!"0".equals(num)) {
                        totalAmount = Integer.parseInt(num) * Double.valueOf(releaseATaskEditShareholderValue.getText().toString());
                        releaseATaskEditTotal.setText(totalAmount + "");
                    } else {
                        releaseATaskEditTotal.setText("0");
                    }
                } else {
                    //edit内容等于0
                    releaseATaskEditTotal.setText("0");
                }
            }
        });

        releaseATaskEditNum.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().length() > 0) {
                    String num = StringUtil.isBlank(releaseATaskEditShareholderValue.getText().toString()) ? "0" : releaseATaskEditShareholderValue.getText().toString();
                    //edit内容大于0
                    if (!"0".equals(num)) {
                        totalAmount = Integer.parseInt(num) * Double.valueOf(releaseATaskEditNum.getText().toString());
                        releaseATaskEditTotal.setText(totalAmount + "");
                    } else {
                        releaseATaskEditTotal.setText("0");
                    }
                } else {
                    //edit内容等于0
                    releaseATaskEditTotal.setText("0");
                }
            }
        });

    }

    @Override
    public ReleaseATaskView createView() {
        return this;
    }

    @Override
    public ReleaseATaskPresenter createPresenter() {
        return new ReleaseATaskPresenter(this);
    }

    @Override
    public void chooseTime(String time) {
        releaseATaskTime.setText(time);
    }

    @Override
    public void getType(int id) {
        this.id = id;
    }

    @Override
    public void loadAdapter(ReleaseATaskTabAdapter releaseATaskTabAdapter) {
        releaseATaskTitleRec.setAdapter(releaseATaskTabAdapter);
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
        if (0 == flag) {
            imgs.add(bitmap);
            releaseATaskReviewTheFigureLeft.setImageURI(uri);
        } else {
            imgs.add(bitmap);
            releaseATaskReviewTheFigureRight.setImageURI(uri);
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
