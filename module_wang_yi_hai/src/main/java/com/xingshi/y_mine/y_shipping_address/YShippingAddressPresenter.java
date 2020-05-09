package com.xingshi.y_mine.y_shipping_address;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.xingshi.adapter.MyRecyclerAdapter;
import com.xingshi.bean.ShippingAddressBean;
import com.xingshi.common.CommonResource;
import com.xingshi.mvp.BasePresenter;
import com.xingshi.net.OnDataListener;
import com.xingshi.net.OnMyCallBack;
import com.xingshi.net.RetrofitUtil;
import com.xingshi.utils.LogUtil;
import com.xingshi.utils.PopUtils;
import com.xingshi.utils.SPUtil;
import com.xingshi.view.SelfDialog;
import com.xingshi.y_main.R;
import com.xingshi.y_mine.y_shipping_address.adapter.YShippingAddressAdapter;
import com.xingshi.y_mine.y_shipping_address.y_amendaddress.YAmendAddressActivity;

import java.io.Serializable;
import java.util.List;

import io.reactivex.Observable;
import okhttp3.ResponseBody;

public class YShippingAddressPresenter extends BasePresenter<YShippingAddressView> {

    private YShippingAddressAdapter shippingAddressAdapter;
    private List<ShippingAddressBean> shippingAddressBeanList;

    public YShippingAddressPresenter(Context context) {
        super(context);
    }

    @Override
    protected void onViewDestroy() {

    }

    public void setShippingAddressRec(final RecyclerView shippingAddressRec, final String from) {
        Observable<ResponseBody> dataWithout = RetrofitUtil.getInstance().getApi(CommonResource.BASEURL_4001).getHeadWithout(CommonResource.ADDRESSSHOW, SPUtil.getToken());
        RetrofitUtil.getInstance().toSubscribe(dataWithout, new OnMyCallBack(new OnDataListener() {
            @Override
            public void onSuccess(String result, String msg) {
                LogUtil.e("ShippingAddressResult----->" + result);
                shippingAddressBeanList = JSON.parseArray(result, ShippingAddressBean.class);

                LogUtil.e("ShippingAddressResult----->" + shippingAddressBeanList);

                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
                shippingAddressRec.setLayoutManager(linearLayoutManager);
                shippingAddressAdapter = new YShippingAddressAdapter(mContext, shippingAddressBeanList, R.layout.item_y_shipping_address_rec);
                shippingAddressRec.setAdapter(shippingAddressAdapter);

                shippingAddressAdapter.setOnItemClick(new MyRecyclerAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(RecyclerView parent, View view, int position) {
                        if (!TextUtils.isEmpty(from)) {
                            Intent intent = new Intent();
                            intent.putExtra("address", shippingAddressBeanList.get(position));
                            ((Activity) mContext).setResult(Activity.RESULT_OK, intent);
                            ((Activity) mContext).finish();
                        }
                    }
                });

                shippingAddressAdapter.setViewThreeOnClickListener(new MyRecyclerAdapter.ViewThreeOnClickListener() {
                    @Override
                    public void ViewThreeOnClick(View view1, View view2, View view3, final int position) {
                        //点击选中
                        view1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Observable observable = RetrofitUtil.getInstance().getApi(CommonResource.BASEURL_4001).getHeadWithout(CommonResource.ADDRESSDEFAULT + "/" + shippingAddressBeanList.get(position).getId(), SPUtil.getToken());
                                RetrofitUtil.getInstance().toSubscribe(observable, new OnMyCallBack(new OnDataListener() {
                                    @Override
                                    public void onSuccess(String result, String msg) {
                                        LogUtil.e("shippingAddressResult默认地址------------->" + msg);
                                        for (int i = 0; i < shippingAddressBeanList.size(); i++) {
                                            if (i != position) {
                                                shippingAddressBeanList.get(i).setAddressDefault(0);
                                            }
                                            shippingAddressBeanList.get(position).setAddressDefault(1);
                                            shippingAddressAdapter.notifyDataSetChanged();
                                        }

                                    }

                                    @Override
                                    public void onError(String errorCode, String errorMsg) {
                                        LogUtil.e("shippingAddressErrorMsg默认地址------>" + errorMsg);
                                    }
                                }));

                            }
                        });
                        //修改
                        view2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(mContext, YAmendAddressActivity.class);
                                intent.putExtra("shippingAddressBeanList", (Serializable) shippingAddressBeanList);
                                intent.putExtra("position", position);
                                mContext.startActivity(intent);
                            }
                        });
                        //删除
                        view3.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                //删除地址
                                final SelfDialog selfDialog = new SelfDialog(mContext);
                                selfDialog.setTitle("提示");
                                selfDialog.setMessage("您确定要删除此地址吗？");
                                selfDialog.setYesOnclickListener("确定", new SelfDialog.onYesOnclickListener() {
                                    @Override
                                    public void onYesClick() {
                                        Observable observable = RetrofitUtil.getInstance().getApi(CommonResource.BASEURL_4001).deleteDataWithout(CommonResource.DELETEADDRESS + "/" + shippingAddressBeanList.get(position).getId(), SPUtil.getToken());
                                        RetrofitUtil.getInstance().toSubscribe(observable, new OnMyCallBack(new OnDataListener() {
                                            @Override
                                            public void onSuccess(String result, String msg) {
                                                LogUtil.e("shippingAddressResult删除地址------------->" + msg);
//                                                for (int i = shippingAddressBeanList.size() - 1; i >= 0; i--) {
                                                shippingAddressBeanList.remove(position);
//                                                }

                                                shippingAddressAdapter.notifyDataSetChanged();
                                                selfDialog.dismiss();
                                                PopUtils.setTransparency(mContext, 1f);
                                            }

                                            @Override
                                            public void onError(String errorCode, String errorMsg) {
                                                LogUtil.e("shippingAddressErrorMsg删除地址------>" + errorMsg);
                                            }
                                        }));

                                    }
                                });
                                selfDialog.setNoOnclickListener("取消", new SelfDialog.onNoOnclickListener() {
                                    @Override
                                    public void onNoClick() {
                                        selfDialog.dismiss();
                                        PopUtils.setTransparency(mContext, 1f);
                                    }
                                });
                                PopUtils.setTransparency(mContext, 0.3f);
                                selfDialog.show();
                            }
                        });

                    }
                });
            }

            @Override
            public void onError(String errorCode, String errorMsg) {
                LogUtil.e("ShippingAddressMsg--------->" + errorMsg);
            }
        }));

    }

}
