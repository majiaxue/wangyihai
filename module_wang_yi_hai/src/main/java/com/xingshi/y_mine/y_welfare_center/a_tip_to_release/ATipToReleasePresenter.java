package com.xingshi.y_mine.y_welfare_center.a_tip_to_release;

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
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.ali.auth.third.core.util.StringUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectChangeListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
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

import java.io.File;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

public class ATipToReleasePresenter extends BasePresenter<ATipToReleaseView> {

    private Uri fileUri;//相册
    private String filePath = Environment.getExternalStorageDirectory() + "/wyh/image";
    private TimePickerView pvTime;
    private int flag = 0;
    private File file1;
    private RequestBody imgBody;
    private MultipartBody.Part filePart;
    private Uri uri;    //资源文件-添加图片的uri
    private Uri parse;

    public ATipToReleasePresenter(Context context) {
        super(context);
    }

    @Override
    protected void onViewDestroy() {

    }

    public void initTimePicker(){
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

    public void issue(String title, String pictureUrl, String content, String endTime, String ordinaryPrice, String shareholderPrice) {
        LogUtil.e(pictureUrl);
        if (StringUtil.isBlank(title)) {
            Toast.makeText(mContext, "请输入标题", Toast.LENGTH_SHORT).show();
        }
        else if (StringUtil.isBlank(pictureUrl)) {
            Toast.makeText(mContext, "请上传照片", Toast.LENGTH_SHORT).show();
        } else if (StringUtil.isBlank(content)) {
            Toast.makeText(mContext, "请输入内容", Toast.LENGTH_SHORT).show();
        } else if (StringUtil.isBlank(endTime)) {
            Toast.makeText(mContext, "请选择时间", Toast.LENGTH_SHORT).show();
        } else if (StringUtil.isBlank(ordinaryPrice)) {
            Toast.makeText(mContext, "请输入普通价格", Toast.LENGTH_SHORT).show();
        } else if (StringUtil.isBlank(shareholderPrice)) {
            Toast.makeText(mContext, "请输入股东价", Toast.LENGTH_SHORT).show();
        }
        else {
            Map map = MapUtil.getInstance().addParms("title", title).addParms("pictureUrl", pictureUrl).addParms("content", content).addParms("endTime", endTime).addParms("ordinaryPrice", ordinaryPrice).addParms("shareholderPrice", shareholderPrice).build();
            String jsonString = JSON.toJSONString(map);
            RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonString);
            Observable<ResponseBody> responseBodyObservable = RetrofitUtil.getInstance().getApi(CommonResource.BASEURL_9006).postHeadWithBody(CommonResource.NEWSPAPER, requestBody, SPUtil.getToken());
            RetrofitUtil.getInstance().toSubscribe(responseBodyObservable, new OnMyCallBack(new OnDataListener() {
                @Override
                public void onSuccess(String result, String msg) {
                    LogUtil.e("付费线报发布" + result);
                    Toast.makeText(mContext, "发布成功", Toast.LENGTH_SHORT).show();
                    ((Activity)mContext).finish();
                }

                @Override
                public void onError(String errorCode, String errorMsg) {
                    Toast.makeText(mContext, errorMsg, Toast.LENGTH_SHORT).show();
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
        file1 = new File(filePath, System.currentTimeMillis() + ".jpg");

        Intent captureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            fileUri = FileProvider.getUriForFile(mContext.getApplicationContext(), mContext.getPackageName(), file1);
            captureIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        } else {
            fileUri = Uri.fromFile(file1);
        }
        captureIntent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
        getView().takePhoto(captureIntent);

    }

    public void openPhotoAlbum() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        getView().photoAlbum(intent);
    }

    public void updateList() {
        uploadPictures();
    }

    private void uploadPictures() {
//        uriList.remove(uriList.size() - 1);
        LogUtil.e("图片1111" + fileUri);
        if (1 == flag) {
            imgBody = RequestBody.create(MediaType.parse("multipart/form-data"), file1);
            filePart = MultipartBody.Part.createFormData("file", file1.getName(), imgBody);

        } else if (2 == flag) {
            String realFilePath = getRealFilePath(mContext, fileUri);
            File file = new File(realFilePath);
            //        LogUtil.e("图片2222" + realFilePath);
            //将文件转化为RequestBody对象
            //需要在表单中进行文件上传时，就需要使用该格式：multipart/form-data
            imgBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            //将文件转化为MultipartBody.Part
            //第一个参数：上传文件的key；第二个参数：文件名；第三个参数：RequestBody对象
            filePart = MultipartBody.Part.createFormData("file", file.getName(), imgBody);
        }
        Observable<ResponseBody> responseBodyObservable = RetrofitUtil.getInstance().getApi(CommonResource.BASEURL_4000).postFile(CommonResource.UPLOADORDER, filePart);
        RetrofitUtil.getInstance().toSubscribe(responseBodyObservable, new OnMyCallBack(new OnDataListener() {
            @Override
            public void onSuccess(String result, String msg) {
                LogUtil.e("上传图片" + result);
                JSONObject jsonObject = JSON.parseObject(result);
//                String ngUrl = jsonObject.getString("ngUrl");
                String bucketName = jsonObject.getString("bucketName");
                String fileName = jsonObject.getString("fileName");
                if (getView() != null) {
                    getView().imagePath(CommonResource.BASEURL_8083 + "/" + bucketName + "/" + fileName);
                }
//                uriList.add(CommonResource.BASEURL_8083 + "/" + bucketName + "/" + fileName);
//                //imgList.add(CommonResource.BASEURL_8083 + "/" + bucketName + "/" + fileName);
//                if (uriList.size() < 9) {
//                    uriList.add(String.valueOf(parse));
//                }
                fileUri = null;
            }

            @Override
            public void onError(String errorCode, String errorMsg) {
                LogUtil.e("上传图片" + errorCode + errorMsg);
            }
        }));
    }

    private String getRealFilePath(Context mContext, Uri uri) {
        if (null == uri) return null;
        final String scheme = uri.getScheme();
        String data = null;
        if (scheme == null)
            data = uri.getPath();
        else if (ContentResolver.SCHEME_FILE.equals(scheme)) {
            data = uri.getPath();
        } else if (ContentResolver.SCHEME_CONTENT.equals(scheme)) {
            Cursor cursor = mContext.getContentResolver().query(uri, new String[]{MediaStore.Images.ImageColumns.DATA}, null, null, null);
            if (null != cursor) {
                if (cursor.moveToFirst()) {
                    int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                    if (index > -1) {
                        data = cursor.getString(index);
                    }
                }
                cursor.close();
            }
        }
        return data;
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
            LogUtil.e("图片----------"+fileUri);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        updateList();
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
