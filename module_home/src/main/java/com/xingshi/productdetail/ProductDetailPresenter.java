package com.xingshi.productdetail;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.xingshi.bean.BannerImageBean;
import com.xingshi.bean.ProductCenterBean;
import com.xingshi.bean.ProductLiuYanBean;
import com.xingshi.bean.TestAccountBean;
import com.xingshi.common.CommonResource;
import com.xingshi.module_home.R;
import com.xingshi.mvp.BasePresenter;
import com.xingshi.net.OnDataListener;
import com.xingshi.net.OnMyCallBack;
import com.xingshi.net.RetrofitUtil;
import com.xingshi.productdetail.adapter.ProductAccountAdapter;
import com.xingshi.utils.LogUtil;
import com.xingshi.utils.MacUtil;
import com.xingshi.utils.MapUtil;
import com.xingshi.utils.OnPopListener;
import com.xingshi.utils.PhoneNumUtil;
import com.xingshi.utils.PopUtils;
import com.xingshi.utils.ProcessDialogUtil;
import com.xingshi.utils.SPUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

public class ProductDetailPresenter extends BasePresenter<ProductDetailView> {
    public ProductDetailPresenter(Context context) {
        super(context);
    }

    @Override
    protected void onViewDestroy() {

    }

    public void loadData(ProductCenterBean.RecordsBean data) {
        final List<TestAccountBean> list = new ArrayList<>();
        for (int i = 0; i < data.getTestNameList().size(); i++) {
            TestAccountBean testAccountBean = new TestAccountBean();
            testAccountBean.setTitle(data.getTestNameList().get(i).get(0));
            testAccountBean.setAddressList(data.getTestAddressList().get(i));

            List<TestAccountBean.Account> accountList = new ArrayList<>();
            for (int j = 0; j < data.getTestAccountList().get(i).size(); j++) {
                TestAccountBean.Account account = new TestAccountBean.Account();
                account.setAccount(data.getTestAccountList().get(i).get(j));
                account.setPassword(data.getTestPasswordList().get(i).get(j));
                accountList.add(account);
            }
            testAccountBean.setAccountList(accountList);
            list.add(testAccountBean);
        }
        ProductAccountAdapter productAccountAdapter = new ProductAccountAdapter(mContext, list, R.layout.rv_product_detail);
        if (getView() != null) {
            getView().loadRv(productAccountAdapter);
        }

//        productAccountAdapter.setViewThreeOnClickListener(new MyRecyclerAdapter.ViewThreeOnClickListener() {
//            @Override
//            public void ViewThreeOnClick(View view1, View view2, View view3, final int position) {
//                view1.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        setClipboard(list.get(position).getTestAccount());
//                    }
//                });
//
//                view2.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        setClipboard(list.get(position).getTestPassword());
//                    }
//                });
//
//                view3.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        ARouter.getInstance().build("/module_home/WebDetailActivity").withString("url", list.get(position).getTestAddress()).navigation();
//                    }
//                });
//            }
//        });

        String[] pics = data.getPic().split(",");
        List<BannerImageBean> imgList = new ArrayList<>();
        for (int i = 0; i < pics.length; i++) {
            imgList.add(new BannerImageBean(pics[i]));
        }
        if (getView() != null) {
            getView().loadBanner(imgList);
        }
    }

    public void loadPhone() {
        Observable<ResponseBody> observable = RetrofitUtil.getInstance().getApi(CommonResource.BASEURL_9001).getHeadWithout(CommonResource.PRODUCT_GETPHONE, SPUtil.getToken());
        RetrofitUtil.getInstance().toSubscribe(observable, new OnMyCallBack(new OnDataListener() {
            @Override
            public void onSuccess(String result, String msg) {
                LogUtil.e("咨询电话：" + result);
                Map map = JSON.parseObject(result, Map.class);
                String phone = (String) map.get("phone");
                String name = (String) map.get("name");
                if (getView() != null) {
                    getView().updatePhone(0, phone);
                }
            }

            @Override
            public void onError(String errorCode, String errorMsg) {
                LogUtil.e(errorCode + "---------------" + errorMsg);
                if (getView() != null) {
                    getView().updatePhone(1, "");
                }
            }
        }));
    }

    public void liuyan(ProductLiuYanBean liuYanBean, final PopupWindow pop) {
        ProcessDialogUtil.showProcessDialog(mContext);
        String jsonString = JSON.toJSONString(liuYanBean);
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonString);
        Observable<ResponseBody> observable = RetrofitUtil.getInstance().getApi(CommonResource.BASEURL_9001).postDataWithBody(CommonResource.PRODUCT_LIUYAN, body);
        RetrofitUtil.getInstance().toSubscribe(observable, new OnMyCallBack(new OnDataListener() {
            @Override
            public void onSuccess(String result, String msg) {
                LogUtil.e("留言：" + result);
                pop.dismiss();
                Toast.makeText(mContext, "留言成功", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(String errorCode, String errorMsg) {
                pop.dismiss();
                LogUtil.e(errorCode + "----------" + errorMsg);
            }
        }));
    }

    public void liuYanPop() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.pop_product_liuyan, null);
        final EditText name = view.findViewById(R.id.pop_product_liuyan_name);
        final EditText phone = view.findViewById(R.id.pop_product_liuyan_phone);
        final EditText content = view.findViewById(R.id.pop_product_liuyan_content);
        final TextView btn = view.findViewById(R.id.pop_product_liuyan_btn);
        PopUtils.createPopCenter(mContext, view, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, new OnPopListener() {
            @Override
            public void setOnPop(final PopupWindow pop) {
                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (TextUtils.isEmpty(name.getText().toString())) {
                            Toast.makeText(mContext, "请输入姓名", Toast.LENGTH_SHORT).show();
                        } else if (!PhoneNumUtil.isMobileNO(phone.getText().toString())) {
                            Toast.makeText(mContext, "手机号格式不正确", Toast.LENGTH_SHORT).show();
                        } else if (TextUtils.isEmpty(content.getText().toString())) {
                            Toast.makeText(mContext, "请输入留言内容", Toast.LENGTH_SHORT).show();
                        } else {
                            ProductLiuYanBean liuYanBean = new ProductLiuYanBean(name.getText().toString(), phone.getText().toString(), content.getText().toString());
                            liuyan(liuYanBean, pop);
                        }
                    }
                });
            }
        });
    }

    public void callPhone(String phoneNum) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        Uri data = Uri.parse("tel:" + phoneNum);
        intent.setData(data);
        mContext.startActivity(intent);
    }

    public void setClipboard(String content) {
        //获取剪贴板管理器：
        ClipboardManager cm = (ClipboardManager) mContext.getSystemService(Context.CLIPBOARD_SERVICE);
        // 创建普通字符型ClipData
        ClipData mClipData = ClipData.newPlainText("Label", content);
        // 将ClipData内容放到系统剪贴板里。
        cm.setPrimaryClip(mClipData);
        Toast.makeText(mContext, "复制成功", Toast.LENGTH_SHORT).show();
    }

    public void loading(String id) {
        Map map = MapUtil.getInstance().addParms("type", "0").addParms("phoneId", MacUtil.getMac(mContext)).addParms("productId", id).build();
        Observable observable = RetrofitUtil.getInstance().getApi(CommonResource.BASEURL_9001).getData(CommonResource.PRODUCT_COUNTS, map);
        RetrofitUtil.getInstance().toSubscribe(observable, new OnMyCallBack(new OnDataListener() {
            @Override
            public void onSuccess(String result, String msg) {
                LogUtil.e("计数：" + result);
            }

            @Override
            public void onError(String errorCode, String errorMsg) {
                LogUtil.e(errorCode + "--------------" + errorMsg);
            }
        }));
    }
}
