package com.xingshi.y_mine.y_welfare_center.release_a_task;

import android.app.Activity;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.ali.auth.third.core.util.StringUtil;
import com.alibaba.fastjson.JSON;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectChangeListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.xingshi.adapter.MyRecyclerAdapter;
import com.xingshi.bean.ReleaseATaskTabBean;
import com.xingshi.common.CommonResource;
import com.xingshi.mvp.BasePresenter;
import com.xingshi.net.OnDataListener;
import com.xingshi.net.OnMyCallBack;
import com.xingshi.net.RetrofitUtil;
import com.xingshi.utils.DisplayUtil;
import com.xingshi.utils.ImageUtil;
import com.xingshi.utils.LogUtil;
import com.xingshi.utils.MapUtil;
import com.xingshi.utils.OnPopListener;
import com.xingshi.utils.PopUtils;
import com.xingshi.utils.SPUtil;
import com.xingshi.y_main.R;
import com.xingshi.y_mine.y_welfare_center.release_a_task.adapter.ReleaseATaskTabAdapter;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

public class ReleaseATaskPresenter extends BasePresenter<ReleaseATaskView> {

    private TimePickerView pvTime;
    private List<ReleaseATaskTabBean> tabBeanList;
    private Uri fileUri;//相册
    private String filePath = Environment.getExternalStorageDirectory() + "/wyh/image";
    private File file;

    public ReleaseATaskPresenter(Context context) {
        super(context);
    }

    @Override
    protected void onViewDestroy() {

    }

    public void initTimePicker() {
        pvTime = new TimePickerBuilder(mContext, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
//                Toast.makeText(mContext, getTime(date), Toast.LENGTH_SHORT).show();
                LogUtil.e("时间选择onTimeSelect" + getTime(date));
                getView().chooseTime(getTime(date));
            }
        }).setTimeSelectChangeListener(new OnTimeSelectChangeListener() {
            @Override
            public void onTimeSelectChanged(Date date) {
                LogUtil.e("时间选择onTimeSelectChanged" + getTime(date));
            }
        }).setType(new boolean[]{true, true, true, true, true, true})
                .isDialog(true) //默认设置false ，内部实现将DecorView 作为它的父控件。
                .addOnCancelClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
//                        Log.i("pvTime", "onCancelClickListener");
                        LogUtil.e("时间选择onCancelClickListener" + "取消");
                    }
                })
                .setItemVisibleCount(5) //若设置偶数，实际值会加1（比如设置6，则最大可见条目为7）
                .setLineSpacingMultiplier(2.0f)
                .isAlphaGradient(true)
                .build();

        Dialog mDialog = pvTime.getDialog();
        if (mDialog != null) {

            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    Gravity.BOTTOM);

            params.leftMargin = 0;
            params.rightMargin = 0;
            pvTime.getDialogContainerLayout().setLayoutParams(params);

            Window dialogWindow = mDialog.getWindow();
            if (dialogWindow != null) {
                dialogWindow.setWindowAnimations(com.bigkoo.pickerview.R.style.picker_view_slide_anim);//修改动画样式
                dialogWindow.setGravity(Gravity.BOTTOM);//改成Bottom,底部显示
                dialogWindow.setDimAmount(0.3f);
            }
        }
    }

    public void chooseTime() {
        pvTime.show();
    }

    private String getTime(Date date) {//可根据需要自行截取数据显示
//        Log.d("getTime()", "choice date millis: " + date.getTime());
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(date);
    }

    public void initTab() {
        Observable<ResponseBody> dataWithout = RetrofitUtil.getInstance().getApi(CommonResource.BASEURL_9006).getDataWithout(CommonResource.COMMISSIONTASKTYPE);
        RetrofitUtil.getInstance().toSubscribe(dataWithout, new OnMyCallBack(new OnDataListener() {
            @Override
            public void onSuccess(String result, String msg) {
                LogUtil.e("任务类型" + result);
                tabBeanList = JSON.parseArray(result, ReleaseATaskTabBean.class);
                tabBeanList.get(0).setClick(true);
                getView().getType(tabBeanList.get(0).getId());
                final ReleaseATaskTabAdapter releaseATaskTabAdapter = new ReleaseATaskTabAdapter(mContext, tabBeanList, R.layout.item_text);
                if (getView() != null) {
                    getView().loadAdapter(releaseATaskTabAdapter);
                }

                releaseATaskTabAdapter.setOnItemClick(new MyRecyclerAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(RecyclerView parent, View view, int position) {
                        tabBeanList.get(position).setClick(true);
                        getView().getType(tabBeanList.get(position).getId());
                        for (int i = 0; i < tabBeanList.size(); i++) {
                            if (position != i) {
                                tabBeanList.get(i).setClick(false);
                            }
                        }
                        releaseATaskTabAdapter.notifyDataSetChanged();
                    }
                });
            }

            @Override
            public void onError(String errorCode, String errorMsg) {
                LogUtil.e("任务类型errorMsg" + errorMsg);
            }
        }));
    }

    public void submit(String title, String endTime, String price
            , String ordinaryPrice, String shareholderPrice, String number
            , String maxNumber, String priceTotal, List<String> imgs
            , String operation, String url, String verification
            , String bz, RadioButton xz, int type) {
        if (StringUtil.isBlank(title)) {
            Toast.makeText(mContext, "请输入标题", Toast.LENGTH_SHORT).show();
        } else if (StringUtil.isBlank(endTime)) {
            Toast.makeText(mContext, "请选择时间", Toast.LENGTH_SHORT).show();
        } else if (StringUtil.isBlank(price)) {
            Toast.makeText(mContext, "请输入单价", Toast.LENGTH_SHORT).show();
        } else if ("0".equals(price)) {
            Toast.makeText(mContext, "单价不能为0", Toast.LENGTH_SHORT).show();
        } else if ("0.".equals(price) || ".".equals(price) || "0.0".equals(price)) {
            Toast.makeText(mContext, "单价输入不正确", Toast.LENGTH_SHORT).show();
        } else if (StringUtil.isBlank(ordinaryPrice)) {
            Toast.makeText(mContext, "请输入会员价", Toast.LENGTH_SHORT).show();
        } else if ("0".equals(ordinaryPrice)) {
            Toast.makeText(mContext, "会员价不能为0", Toast.LENGTH_SHORT).show();
        } else if ("0.".equals(ordinaryPrice) || ".".equals(ordinaryPrice) || "0.0".equals(ordinaryPrice)) {
            Toast.makeText(mContext, "会员价输入不正确", Toast.LENGTH_SHORT).show();
        } else if (StringUtil.isBlank(shareholderPrice)) {
            Toast.makeText(mContext, "请输入股东价", Toast.LENGTH_SHORT).show();
        } else if ("0".equals(shareholderPrice)) {
            Toast.makeText(mContext, "股东价不能为0", Toast.LENGTH_SHORT).show();
        } else if ("0.".equals(shareholderPrice) || ".".equals(shareholderPrice) || "0.0".equals(shareholderPrice)) {
            Toast.makeText(mContext, "股东价输入不正确", Toast.LENGTH_SHORT).show();
        } else if (StringUtil.isBlank(number)) {
            Toast.makeText(mContext, "请输入数量", Toast.LENGTH_SHORT).show();
        } else if ("0".equals(number)) {
            Toast.makeText(mContext, "数量不能为0", Toast.LENGTH_SHORT).show();
        } else if (StringUtil.isBlank(maxNumber)) {
            Toast.makeText(mContext, "请输入重复数量", Toast.LENGTH_SHORT).show();
        } else if ("0".equals(maxNumber)) {
            Toast.makeText(mContext, "重复数量不能为0", Toast.LENGTH_SHORT).show();
        } else if (imgs.size() == 0) {
            Toast.makeText(mContext, "请上传审核样图", Toast.LENGTH_SHORT).show();
        } else if (StringUtil.isBlank(operation)) {
            Toast.makeText(mContext, "请输入操作说明", Toast.LENGTH_SHORT).show();
        } else if (StringUtil.isBlank(url)) {
            Toast.makeText(mContext, "请输入链接", Toast.LENGTH_SHORT).show();
        } else if (!xz.isChecked()) {
            Toast.makeText(mContext, "请选中发布协议", Toast.LENGTH_SHORT).show();
        } else {
            LogUtil.e("任务type------"+type);
            Map map = MapUtil.getInstance().addParms("title", title).addParms("endTime", endTime).addParms("price", price).addParms("ordinaryPrice", ordinaryPrice)
                    .addParms("shareholderPrice", shareholderPrice).addParms("number", number).addParms("maxNumber", maxNumber)
                    .addParms("priceTotal", priceTotal).addParms("XsBlockCommissionImg", imgs).addParms("operation", operation).addParms("url", url)
                    .addParms("verification", verification).addParms("bz", bz).addParms("type", type).build();
            String jsonString = JSON.toJSONString(map);
            RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonString);
            Observable<ResponseBody> responseBodyObservable = RetrofitUtil.getInstance().getApi(CommonResource.BASEURL_9006).postHeadWithBody(CommonResource.COMMISSIONTASKSAVE, requestBody, SPUtil.getToken());
            RetrofitUtil.getInstance().toSubscribe(responseBodyObservable, new OnMyCallBack(new OnDataListener() {
                @Override
                public void onSuccess(String result, String msg) {
                    Toast.makeText(mContext, "发布成功", Toast.LENGTH_SHORT).show();
                    ((Activity)mContext).finish();
                    LogUtil.e("发布任务" + result);
                }

                @Override
                public void onError(String errorCode, String errorMsg) {
                    LogUtil.e("发布任务errorMsg" + errorMsg);
                    Toast.makeText(mContext, errorMsg, Toast.LENGTH_SHORT).show();
                    ((Activity)mContext).finish();
                }
            }));

        }

    }

    public void popupWindow() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.pop_bottom, null);
        final TextView popHeaderCancel = view.findViewById(R.id.pop_header_cancel);
        final TextView popHeaderCamera = view.findViewById(R.id.pop_header_camera);
        final TextView popHeaderXiangce = view.findViewById(R.id.pop_header_xiangce);
        PopUtils.setTransparency(mContext, 0.3f);
        PopUtils.createPop(mContext, view, LinearLayout.LayoutParams.MATCH_PARENT, DisplayUtil.dip2px(mContext, 146), new OnPopListener() {
            @Override
            public void setOnPop(final PopupWindow pop) {
                popHeaderCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        pop.dismiss();
                    }
                });
                popHeaderCamera.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        openCamera();
                        pop.dismiss();
                    }
                });
                popHeaderXiangce.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        openPhotoAlbum();
                        pop.dismiss();
                    }
                });
            }
        });
    }

    private void openCamera() {

        File file0 = new File(filePath);
        if (!file0.exists()) {
            file0.mkdirs();
        }
        file = new File(filePath, System.currentTimeMillis() + ".jpg");

        Intent captureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            fileUri = FileProvider.getUriForFile(mContext.getApplicationContext(), mContext.getPackageName(), file);
            captureIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        } else {
            fileUri = Uri.fromFile(file);
        }
        captureIntent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
        getView().takePhoto(captureIntent);

    }

    public void openPhotoAlbum() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        getView().photoAlbum(intent);
    }

    public void parseUri(Intent intent) {
        fileUri = intent.getData();
        String type = intent.getType();
        if (fileUri.getScheme().equals("file") && (type.contains("image/"))) {
            String path = fileUri.getEncodedPath();
            if (path != null) {
                path = Uri.decode(path);
                ContentResolver cr = mContext.getContentResolver();
                StringBuffer buff = new StringBuffer();
                buff.append("(").append(MediaStore.Images.ImageColumns.DATA).append("=")
                        .append("'" + path + "'").append(")");
                Cursor cur = cr.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, new String[]{MediaStore.Images.ImageColumns._ID}, buff.toString(), null, null);
                int index = 0;
                for (cur.moveToFirst(); !cur.isAfterLast(); cur.moveToNext()) {
                    index = cur.getColumnIndex(MediaStore.Images.ImageColumns._ID);
                    // set _id value
                    index = cur.getInt(index);
                }
                if (index == 0) {
                    // do nothing
                } else {
                    Uri uri_temp = Uri.parse("content://media/external/images/media/" + index);
                    if (uri_temp != null) {
                        fileUri = uri_temp;
                    }
                }
            }
        }
        try {
            Bitmap bitmap = BitmapFactory.decodeStream(mContext.getContentResolver().openInputStream(fileUri));
            String base64 = ImageUtil.bitmapToBase64(bitmap);
            getView().showHeader(base64, fileUri);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void takePhoto() {
        try {
            Bitmap bitmap = BitmapFactory.decodeStream(mContext.getContentResolver().openInputStream(fileUri));
            String base64 = ImageUtil.bitmapToBase64(bitmap);
            getView().showHeader(base64, fileUri);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

}
