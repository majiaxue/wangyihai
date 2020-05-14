package com.xingshi.y_mine.y_welfare_center.commission_task_details;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.CountDownTimer;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.xingshi.bean.CommissionTaskDetailsBean;
import com.xingshi.bean.TaskListDetailsBean;
import com.xingshi.bean.XSBlockExamineImg;
import com.xingshi.common.CommonResource;
import com.xingshi.entity.EventBusBean;
import com.xingshi.mvp.BaseActivity;
import com.xingshi.utils.ImageUtil;
import com.xingshi.utils.LogUtil;
import com.xingshi.utils.MyTimeUtil;
import com.xingshi.y_main.R;
import com.xingshi.y_main.R2;
import com.xingshi.y_mine.y_welfare_center.commission_task_details.adapter.CommissionTaskDetailsImageAdapter;
import com.xingshi.y_mine.y_welfare_center.task_list.adapter.TaskDetailsImageAdapter;
import com.facebook.drawee.view.SimpleDraweeView;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import butterknife.BindView;

/**
 * 任务详情
 */
public class CommissionTaskDetailsActivity extends BaseActivity<CommissionTaskDetailsView, CommissionTaskDetailsPresenter> implements CommissionTaskDetailsView {


    @BindView(R2.id.commission_task_details_back)
    ImageView commissionTaskDetailsBack;
    @BindView(R2.id.commission_task_details_title)
    TextView commissionTaskDetailsTitle;
    @BindView(R2.id.commission_task_details_publisher)
    TextView commissionTaskDetailsPublisher;
    @BindView(R2.id.commission_task_details_serial_number)
    TextView commissionTaskDetailsSerialNumber;
    @BindView(R2.id.commission_task_details_type)
    TextView commissionTaskDetailsType;
    @BindView(R2.id.commission_task_details_price)
    TextView commissionTaskDetailsPrice;
    @BindView(R2.id.commission_task_details_link)
    TextView commissionTaskDetailsLink;
    @BindView(R2.id.commission_task_details_copy)
    TextView commissionTaskDetailsCopy;
    @BindView(R2.id.commission_task_details_word_verification)
    TextView commissionTaskDetailsWordVerification;
    @BindView(R2.id.commission_task_details_remark)
    TextView commissionTaskDetailsRemark;
    @BindView(R2.id.commission_task_details_set_top_due)
    TextView commissionTaskDetailsSetTopDue;
    @BindView(R2.id.commission_task_details_deadline)
    TextView commissionTaskDetailsDeadline;
    @BindView(R2.id.commission_task_details_image_rec)
    RecyclerView commissionTaskDetailsImageRec;
    @BindView(R2.id.commission_task_details_time_remaining)
    TextView commissionTaskDetailsTimeRemaining;
    @BindView(R2.id.commission_task_details_affirm)
    TextView commissionTaskDetailsAffirm;
    @BindView(R2.id.commission_task_details_pic_linear)
    LinearLayout commissionTaskDetailsPicLinear;
    @BindView(R2.id.commission_task_details_upload_pictures)
    SimpleDraweeView commissionTaskDetailsUploadPictures;

    private int id;
    private String link;
    private int status;//0未提交  1已提交  -1立即报名
    private final int TAKE_PHOTO_CODE = 0x116;
    private final int PHOTO_ALBUM_CODE = 0x226;
    private List<String> imgs = new ArrayList<>();
    private CountDownTimer countDownTimer;
    private TaskListDetailsBean bean;
    private String loadTime;

    @Override
    public int getLayoutId() {
        return R.layout.activity_commission_task_details;
    }

    @Override
    public void initData() {
        Intent intent = getIntent();
        id = intent.getIntExtra("id", -1);
        status = intent.getIntExtra("status", -1);
        if (0 == status) {
            commissionTaskDetailsPicLinear.setVisibility(View.VISIBLE);
            commissionTaskDetailsImageRec.setVisibility(View.GONE);
            commissionTaskDetailsTimeRemaining.setVisibility(View.VISIBLE);
            commissionTaskDetailsAffirm.setText("立即提交");
        } else if (1 == status) {
            commissionTaskDetailsPicLinear.setVisibility(View.GONE);
            commissionTaskDetailsImageRec.setVisibility(View.VISIBLE);
            commissionTaskDetailsAffirm.setVisibility(View.GONE);
        } else {
            commissionTaskDetailsPicLinear.setVisibility(View.GONE);
            commissionTaskDetailsImageRec.setVisibility(View.GONE);
        }

        presenter.getTime();

        if (-1 == status) {
            presenter.initData(id);
        } else {
            presenter.initTaskListDetailData(id);
        }
    }

    @Override
    public void initClick() {
        commissionTaskDetailsBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //复制链接
        commissionTaskDetailsCopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //获取剪贴板管理器：
                ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                //创建普通字符型ClipData
                ClipData mClipData = ClipData.newPlainText("Label", link);
                // 将ClipData内容放到系统剪贴板里。
                cm.setPrimaryClip(mClipData);
                Toast.makeText(CommissionTaskDetailsActivity.this, "已复制到剪贴板", Toast.LENGTH_SHORT).show();
            }
        });
        //立即报名
        commissionTaskDetailsAffirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (0 == status) {
                    if (imgs.size() != 0) {
                        LogUtil.e("这是图--------"+imgs.toString());
                        List<XSBlockExamineImg.ImgBean> list=new ArrayList<>();
                        for (int i = 0; i <imgs.size() ; i++) {
                            list.add(new XSBlockExamineImg.ImgBean(imgs.get(i)));
                            LogUtil.e("xsdelist========="+list.toString());
                        }
                        presenter.commit(id, list);
                    } else {
                        Toast.makeText(CommissionTaskDetailsActivity.this, "请上传截图", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    presenter.payment(id);
                }
            }
        });
        //上传照片
        commissionTaskDetailsUploadPictures.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.popupWindow();
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(EventBusBean eventBusBean) {
        if (CommonResource.QUKUAI.equals(eventBusBean.getMsg())) {
            //微信支付成功回调
            finish();
        }
    }

    @Override
    public CommissionTaskDetailsView createView() {
        return this;
    }

    @Override
    public CommissionTaskDetailsPresenter createPresenter() {
        return new CommissionTaskDetailsPresenter(this);
    }

    @Override
    public void loadData(CommissionTaskDetailsBean bean) {
        this.link = bean.getCommission().getUrl() == null ? "" : bean.getCommission().getUrl();
        commissionTaskDetailsTitle.setText(bean.getCommission().getTitle());
        commissionTaskDetailsPublisher.setText(bean.getCommission().getUserName());
        commissionTaskDetailsSerialNumber.setText(bean.getCommission().getId() + "");
        commissionTaskDetailsType.setText(bean.getCommission().getTypeName());
        commissionTaskDetailsPrice.setText(bean.getCommission().getPrice() + "元");
        commissionTaskDetailsLink.setText(bean.getCommission().getUrl());
        commissionTaskDetailsWordVerification.setText(bean.getCommission().getVerification());
        commissionTaskDetailsRemark.setText(bean.getCommission().getBz());
        commissionTaskDetailsSetTopDue.setText(bean.getCommission().getCreateTime());
        commissionTaskDetailsDeadline.setText(bean.getCommission().getEndTime());
        if (-1 == status) {
            commissionTaskDetailsAffirm.setText(bean.getCommission().getPrice() + "立即报名");
        }
        //图片
        List<CommissionTaskDetailsBean.ImgBean> img = bean.getImg();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        commissionTaskDetailsImageRec.setLayoutManager(linearLayoutManager);
        CommissionTaskDetailsImageAdapter adapter = new CommissionTaskDetailsImageAdapter(this, img, R.layout.item_image);
        commissionTaskDetailsImageRec.setAdapter(adapter);

        if (-1 == status) {
            long nowTime = System.currentTimeMillis();
            //得到订单创建时间戳
            long timeStamp = MyTimeUtil.getTimeStamp(bean.getCommission().getCreateTime(), "yyyy-MM-dd HH:mm:ss");
            long endTime = timeStamp + Long.parseLong(loadTime) * 1000;
            long consumeTime = endTime - nowTime;
            countDownTimer = new CountDownTimer(consumeTime, 1000) {//第一个参数表示总时间，第二个参数表示间隔时间。
                @Override
                public void onTick(long millisUntilFinished) {
                    SimpleDateFormat formatter = new SimpleDateFormat("HH小时mm分ss秒", Locale.CHINA);
                    formatter.setTimeZone(TimeZone.getTimeZone("GMT+00:00"));
                    String dateString = formatter.format(new Date(millisUntilFinished));
                    commissionTaskDetailsTimeRemaining.setText("剩余时间:" + dateString);
                }

                @Override
                public void onFinish() {
                    LogUtil.e("结束");
                }
            }.start();
        }
    }

    @Override
    public void loadData(TaskListDetailsBean bean) {
        this.link = bean.getCommissionExamine().getUrl() == null ? "" : bean.getCommissionExamine().getUrl();
        commissionTaskDetailsTitle.setText(bean.getCommissionExamine().getTaskName());
        commissionTaskDetailsPublisher.setText(bean.getCommissionExamine().getUserName());
        commissionTaskDetailsSerialNumber.setText(bean.getCommissionExamine().getId() + "");
        commissionTaskDetailsType.setText(bean.getCommissionExamine().getTaskType());
        commissionTaskDetailsPrice.setText(bean.getCommissionExamine().getTaskUnitPrice() + "元");
        commissionTaskDetailsLink.setText(bean.getCommissionExamine().getUrl());
        commissionTaskDetailsWordVerification.setText(bean.getCommissionExamine().getVerification());
        commissionTaskDetailsRemark.setText(bean.getCommissionExamine().getBz());
        commissionTaskDetailsSetTopDue.setText(bean.getCommissionExamine().getCreateTime());
        commissionTaskDetailsDeadline.setText(bean.getCommissionExamine().getEndTime());
        //图片
        List<TaskListDetailsBean.ImgBean> img = bean.getImg();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        commissionTaskDetailsImageRec.setLayoutManager(linearLayoutManager);
        TaskDetailsImageAdapter adapter = new TaskDetailsImageAdapter(this, img, R.layout.item_image);
        commissionTaskDetailsImageRec.setAdapter(adapter);

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
    public void showHeader(Uri uri) {
        try {
            Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(uri));
            String base64 = ImageUtil.bitmapToBase64(bitmap);
            imgs.add(base64);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        commissionTaskDetailsUploadPictures.setImageURI(uri);
    }

    @Override
    public void loadTime(String loadTime) {
        this.loadTime = loadTime;
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

    @Override
    protected void onStop() {
        super.onStop();
        if (countDownTimer != null) {
            countDownTimer.cancel();
            countDownTimer.onFinish();
        }
    }
}
