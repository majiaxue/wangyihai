package com.xingshi.y_mine.y_welfare_center.commission_task_details;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alipay.sdk.app.PayTask;
import com.xingshi.bean.AliPayBean;
import com.xingshi.bean.CommissionTaskDetailsBean;
import com.xingshi.bean.TaskListDetailsBean;
import com.xingshi.bean.WeChatPayBean;
import com.xingshi.bean.XSBlockExamineImg;
import com.xingshi.common.CommonResource;
import com.xingshi.mvp.BasePresenter;
import com.xingshi.net.OnDataListener;
import com.xingshi.net.OnMyCallBack;
import com.xingshi.net.OnTripartiteCallBack;
import com.xingshi.net.RetrofitUtil;
import com.xingshi.utils.DisplayUtil;
import com.xingshi.utils.ImageUtil;
import com.xingshi.utils.LogUtil;
import com.xingshi.utils.MapUtil;
import com.xingshi.utils.OnPopListener;
import com.xingshi.utils.PopUtils;
import com.xingshi.utils.SPUtil;
import com.xingshi.y_main.R;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

public class CommissionTaskDetailsPresenter extends BasePresenter<CommissionTaskDetailsView> {

    private CommissionTaskDetailsBean commissionTaskDetailsBean;
    private Uri fileUri;//相册
    private String filePath = Environment.getExternalStorageDirectory() + "/wyh/image";
    private File file;
    private String base64;

    private String info = "";
    private final int ALI_CODE = 0x1234;

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            if (msg.what == ALI_CODE) {
                Map<String, String> map = (Map<String, String>) msg.obj;
                String resultStatus = map.get("resultStatus");
                String result = map.get("result");
                String memo = map.get("memo");
                if ("9000".equals(resultStatus)) {
                    Toast.makeText(mContext, "支付成功", Toast.LENGTH_SHORT).show();
                    ((Activity) mContext).finish();
                } else {
                    Toast.makeText(mContext, "支付失败", Toast.LENGTH_SHORT).show();
                }
            }
        }

    };

    public CommissionTaskDetailsPresenter(Context context) {
        super(context);
    }

    @Override
    protected void onViewDestroy() {

    }

    public void initData(int id) {
        Map map = MapUtil.getInstance().addParms("id", id).build();
        Observable data = RetrofitUtil.getInstance().getApi(CommonResource.BASEURL_9006).getData(CommonResource.COMMISSIONTASKVIEW, map);
        RetrofitUtil.getInstance().toSubscribe(data, new OnMyCallBack(new OnDataListener() {
            @Override
            public void onSuccess(String result, String msg) {
                LogUtil.e("任务详情" + result);
                commissionTaskDetailsBean = JSON.parseObject(result, CommissionTaskDetailsBean.class);
                if (getView() != null) {
                    getView().loadData(commissionTaskDetailsBean);
                }
            }

            @Override
            public void onError(String errorCode, String errorMsg) {
                LogUtil.e("任务详情errorMsg" + errorMsg);
            }
        }));
    }

    public void getTime() {
        Observable<ResponseBody> dataWithout = RetrofitUtil.getInstance().getApi(CommonResource.BASEURL_9006).getDataWithout(CommonResource.COMMISSIONTASKTIMEPARAM);
        RetrofitUtil.getInstance().toSubscribe(dataWithout, new OnMyCallBack(new OnDataListener() {
            @Override
            public void onSuccess(String result, String msg) {
                LogUtil.e("时间" + result);
                JSONObject jsonObject = JSON.parseObject(result);
                String submitTime = jsonObject.getString("submit");
                getView().loadTime(submitTime);
            }

            @Override
            public void onError(String errorCode, String errorMsg) {
                LogUtil.e("时间errorMsg" + errorMsg);

            }
        }));
    }

    public void initTaskListDetailData(int id) {
        Map map = MapUtil.getInstance().addParms("id", id).build();
        Observable data = RetrofitUtil.getInstance().getApi(CommonResource.BASEURL_9006).getData(CommonResource.MYCOMMISSIONTASKVIEW, map);
        RetrofitUtil.getInstance().toSubscribe(data, new OnMyCallBack(new OnDataListener() {
            @Override
            public void onSuccess(String result, String msg) {
                LogUtil.e("我的任务详情" + result);
                TaskListDetailsBean taskListDetailsBean = JSON.parseObject(result, TaskListDetailsBean.class);

                if (getView() != null) {
                    getView().loadData(taskListDetailsBean);
                }
            }

            @Override
            public void onError(String errorCode, String errorMsg) {
                LogUtil.e("我的任务详情errorMsg" + errorMsg);
            }
        }));
    }

    public void payment(final int id) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.pop_payment, null);
        final TextView popHeaderCancel = view.findViewById(R.id.pop_payment_cancel);
        final TextView popHeaderCamera = view.findViewById(R.id.pop_payment_wechat);
        final TextView popHeaderXiangce = view.findViewById(R.id.pop_payment_ali);
        final TextView popPaymentBalance = view.findViewById(R.id.pop_payment_balance);
        PopUtils.setTransparency(mContext, 0.3f);
        PopUtils.createPop(mContext, view, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, new OnPopListener() {
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
                        pickUpTask(0,id);
                        pop.dismiss();
                    }
                });
                popHeaderXiangce.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        pickUpTask(1,id);
                        pop.dismiss();
                    }
                });
                popPaymentBalance.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        pickUpTask(2,id);
                        pop.dismiss();
                    }
                });
            }
        });
    }

    private void pickUpTask(final int type,int id) {
        LogUtil.e("这是id--------"+id);
        Map map = MapUtil.getInstance().addParms("id",id).addParms("num", commissionTaskDetailsBean.getCommission().getNumber()).addParms("verification", commissionTaskDetailsBean.getCommission().getVerification()).build();
        Observable head = RetrofitUtil.getInstance().getApi(CommonResource.BASEURL_9006).getHead(CommonResource.COMMISSIONTASKPICKUP, map, SPUtil.getToken());
        RetrofitUtil.getInstance().toSubscribe(head, new OnMyCallBack(new OnDataListener() {
            @Override
            public void onSuccess(String result, String msg) {
                LogUtil.e("接取任务" + result);
                JSONObject jsonObject = JSON.parseObject(result);
                String orderSn = jsonObject.getString("orderSn");
                String price = jsonObject.getString("price");
                String userCode = jsonObject.getString("userCode");
                if (0 == type) {
                    //微信
                    weChat(orderSn, price, userCode);
                } else if (1 == type) {
                    //支付宝
                    aliPay(orderSn, price, userCode);
                } else {
                    //余额
                    balance(orderSn, price);
                }
            }

            @Override
            public void onError(String errorCode, String errorMsg) {
                LogUtil.e("接取任务errorMsg" + errorMsg);
            }
        }));
    }

    private void balance(String orderSn, String price) {
        LogUtil.e("ordersn-----"+orderSn);
        LogUtil.e("price-----"+price);
        Map map = MapUtil.getInstance().addParms("orderSn", orderSn).addParms("totalAmount", price).build();
        Observable head = RetrofitUtil.getInstance().getApi(CommonResource.BASEURL_9006).getHead(CommonResource.COMMISSIONBALANCEPAYMENT, map, SPUtil.getToken());
        RetrofitUtil.getInstance().toSubscribe(head, new OnMyCallBack(new OnDataListener() {
            @Override
            public void onSuccess(String result, String msg) {
                LogUtil.e("佣金任务" + result);
                ((Activity) mContext).finish();
            }

            @Override
            public void onError(String errorCode, String errorMsg) {
                LogUtil.e("佣金任务" + errorMsg);
                Toast.makeText(mContext, "" + errorMsg, Toast.LENGTH_SHORT).show();
            }
        }));
    }

    //微信支付
    private void weChat(String orderSn, String price, String userCode) {
        final IWXAPI api = WXAPIFactory.createWXAPI(mContext, CommonResource.WXAPPID, false);

        Map map = MapUtil.getInstance().addParms("orderSn", orderSn).addParms("totalAmount", price).addParms("userCode", userCode).addParms("type", 2).build();
        String jsonString = JSON.toJSONString(map);
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonString);
        Observable<ResponseBody> responseBodyObservable = RetrofitUtil.getInstance().getApi(CommonResource.BASEURL_9006).postDataWithBody(CommonResource.WXPAYWELFARECENTRE, body);
        RetrofitUtil.getInstance().toSubscribe(responseBodyObservable, new OnTripartiteCallBack(new OnDataListener() {
            @Override
            public void onSuccess(String result, String msg) {
                LogUtil.e("微信支付-------------->" + result);
                try {

                    WeChatPayBean payBean = JSON.parseObject(result, WeChatPayBean.class);

                    PayReq request = new PayReq();
                    request.appId = payBean.getAppid();
                    request.partnerId = payBean.getPartnerid();
                    request.prepayId = payBean.getPrepayid();
                    request.packageValue = "Sign=WXPay";
                    request.nonceStr = payBean.getNoncestr();
                    request.timeStamp = payBean.getTimestamp();
                    request.sign = payBean.getSign();

                    api.sendReq(request);
                    SPUtil.addParm("wxpay", "14");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(String errorCode, String errorMsg) {

            }
        }));
    }

    //支付宝支付
    private void aliPay(String orderSn, String price, String userCode) {
        Map map = MapUtil.getInstance().addParms("orderSn", orderSn).addParms("totalAmount", price).addParms("userCode", userCode).addParms("type", 2).build();
        String jsonString = JSON.toJSONString(map);
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonString);
        Observable observable = RetrofitUtil.getInstance().getApi(CommonResource.BASEURL_9006).postDataWithBody(CommonResource.ALIPAYWELFARECENTRE, body);
        RetrofitUtil.getInstance().toSubscribe(observable, new OnMyCallBack(new OnDataListener() {
            @Override
            public void onSuccess(String result, String msg) {
                LogUtil.e("支付宝：" + result);
                AliPayBean aliPayBean = JSON.parseObject(result, AliPayBean.class);
                info = aliPayBean.getBody();
                Thread thread = new Thread(payRunnable);
                thread.start();
            }

            @Override
            public void onError(String errorCode, String errorMsg) {
            }
        }));
    }

    private Runnable payRunnable = new Runnable() {

        @Override
        public void run() {
            PayTask alipay = new PayTask((Activity) mContext);
            Map<String, String> result = alipay.payV2(info, true);

            Message msg = new Message();
            msg.what = ALI_CODE;
            msg.obj = result;
            mHandler.sendMessage(msg);
        }
    };


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
        getView().showHeader(fileUri);
        try {
            Bitmap bitmap = BitmapFactory.decodeStream(mContext.getContentResolver().openInputStream(fileUri));
            base64 = ImageUtil.bitmapToBase64(bitmap);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void takePhoto() {
        getView().showHeader(fileUri);
        try {
            Bitmap bitmap = BitmapFactory.decodeStream(mContext.getContentResolver().openInputStream(fileUri));
            base64 = ImageUtil.bitmapToBase64(bitmap);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void commit(int id, List<XSBlockExamineImg.ImgBean> imgs) {
        Map build = MapUtil.getInstance().addParms("id", id).addParms("img", imgs).build();
        String jsonString = JSON.toJSONString(build);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonString);
        Observable<ResponseBody> responseBodyObservable = RetrofitUtil.getInstance().getApi(CommonResource.BASEURL_9006).postDataWithBody(CommonResource.SAVECOMMISSIONEXAMINE, requestBody);
        RetrofitUtil.getInstance().toSubscribe(responseBodyObservable, new OnMyCallBack(new OnDataListener() {
            @Override
            public void onSuccess(String result, String msg) {
                LogUtil.e("提交审核" + result);
                Toast.makeText(mContext, "提交成功", Toast.LENGTH_SHORT).show();
                ((Activity)mContext).finish();
            }
            @Override
            public void onError(String errorCode, String errorMsg) {
                LogUtil.e("提交审核errorCode" + errorCode);
                Toast.makeText(mContext, errorMsg, Toast.LENGTH_SHORT).show();

            }
        }));
    }
}
