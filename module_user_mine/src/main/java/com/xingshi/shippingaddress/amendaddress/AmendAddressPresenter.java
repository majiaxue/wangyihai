package com.xingshi.shippingaddress.amendaddress;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.xingshi.bean.CityBean;
import com.xingshi.common.CommonResource;
import com.xingshi.module_user_mine.R;
import com.xingshi.mvp.BasePresenter;
import com.xingshi.net.OnDataListener;
import com.xingshi.net.OnMyCallBack;
import com.xingshi.net.RetrofitUtil;
import com.xingshi.utils.LogUtil;
import com.xingshi.utils.OnPopListener;
import com.xingshi.utils.PopUtils;
import com.xingshi.view.addressselect.AddressSelector;
import com.xingshi.view.addressselect.CityInterface;
import com.xingshi.view.addressselect.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import okhttp3.ResponseBody;

/**
 * Created by cuihaohao on 2019/5/25
 * Describe:
 */
public class AmendAddressPresenter extends BasePresenter<AmendAddressView> {

    private List<CityBean> cities1 = new ArrayList<>();
    private List<CityBean> cities2 = new ArrayList<>();
    private List<CityBean> cities3 = new ArrayList<>();
    private String cityName1 = "";
    private String cityName2 = "";
    private String cityName3 = "";

    public AmendAddressPresenter(Context context) {
        super(context);
    }

    @Override
    protected void onViewDestroy() {

    }

    public void popupAddressWhere(final TextView amendAddressProvince, final TextView amendAddressCity, final TextView amendAddressArea) {

        amendAddressProvince.setText("请选择地址");
        amendAddressCity.setText("");
        amendAddressArea.setText("");

        final View view = LayoutInflater.from(mContext).inflate(R.layout.popup_address_select, null, false);
        final ImageView close = view.findViewById(R.id.address_select_close);
        final AddressSelector addressSelector = view.findViewById(R.id.address_selector);


        PopUtils.createPop(mContext, view, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, new OnPopListener() {
            @Override
            public void setOnPop(final PopupWindow pop) {

                addressSelector.setTabAmount(3);
                city1(addressSelector, 1);

                addressSelector.setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void itemClick(AddressSelector addressSelector, CityInterface city, int tabPosition) {
                        switch (tabPosition) {
                            case 0:
//                                Toast.makeText(mContext, "tabPosition ：" + tabPosition + " " + city.getCityName() + city.getCityId(), Toast.LENGTH_SHORT).show();
                                city2(addressSelector, city.getCityId());
                                cityName1 = city.getCityName();
                                amendAddressProvince.setText(cityName1);
                                break;
                            case 1:
//                                Toast.makeText(mContext, "tabPosition ：" + tabPosition + " " + city.getCityName() + city.getCityId(), Toast.LENGTH_SHORT).show();
                                city3(addressSelector, city.getCityId());
                                cityName2 = city.getCityName();
                                amendAddressCity.setText(cityName2);
                                break;
                            case 2:
//                                Toast.makeText(mContext, "tabPosition ：" + tabPosition + " " + city.getCityName() + city.getCityId(), Toast.LENGTH_SHORT).show();
                                cityName3 = city.getCityName();
                                //关闭赋值
                                amendAddressArea.setText(cityName3);
                                pop.dismiss();
                                break;
                        }
                    }
                });
                addressSelector.setOnTabSelectedListener(new AddressSelector.OnTabSelectedListener() {
                    @Override
                    public void onTabSelected(AddressSelector addressSelector, AddressSelector.Tab tab) {
                        switch (tab.getIndex()) {
                            case 0:
                                addressSelector.setCities(cities1);
                                break;
                            case 1:
                                addressSelector.setCities(cities2);
                                break;
                            case 2:
                                addressSelector.setCities(cities3);
                                break;
                        }
                    }

                    @Override
                    public void onTabReselected(AddressSelector addressSelector, AddressSelector.Tab tab) {

                    }
                });
                close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        PopUtils.setTransparency(mContext, 1f);
                        pop.dismiss();
                    }
                });

            }
        });

        PopUtils.setTransparency(mContext, 0.3f);


    }

    private void city1(final AddressSelector addressSelector, int cityId) {
        Observable<ResponseBody> dataWithout = RetrofitUtil.getInstance().getApi(CommonResource.BASEURL_4001).getDataWithout(CommonResource.ADDRESSSELECT + "/" + cityId);
        RetrofitUtil.getInstance().toSubscribe(dataWithout, new OnMyCallBack(new OnDataListener() {
            @Override
            public void onSuccess(String result, String msg) {
                LogUtil.e("addressResult-------------->" + result);
                List<CityBean> cityBeans = JSON.parseArray(result, CityBean.class);
                cities1.addAll(cityBeans);
                addressSelector.setCities(cities1);

            }

            @Override
            public void onError(String errorCode, String errorMsg) {
                LogUtil.e("addressErrorMsg-------------->" + errorMsg);
            }
        }));

    }

    private void city2(final AddressSelector addressSelector, int cityId) {
        Observable<ResponseBody> dataWithout = RetrofitUtil.getInstance().getApi(CommonResource.BASEURL_4001).getDataWithout(CommonResource.ADDRESSSELECT + "/" + cityId);
        RetrofitUtil.getInstance().toSubscribe(dataWithout, new OnMyCallBack(new OnDataListener() {
            @Override
            public void onSuccess(String result, String msg) {
                List<CityBean> cityBeans = JSON.parseArray(result, CityBean.class);
                cities2.clear();
                cities2.addAll(cityBeans);
                addressSelector.setCities(cities2);
            }

            @Override
            public void onError(String errorCode, String errorMsg) {

            }
        }));
    }

    private void city3(final AddressSelector addressSelector, int cityId) {
        Observable<ResponseBody> dataWithout = RetrofitUtil.getInstance().getApi(CommonResource.BASEURL_4001).getDataWithout(CommonResource.ADDRESSSELECT + "/" + cityId);
        RetrofitUtil.getInstance().toSubscribe(dataWithout, new OnMyCallBack(new OnDataListener() {
            @Override
            public void onSuccess(String result, String msg) {
                List<CityBean> cityBeans = JSON.parseArray(result, CityBean.class);
                cities3.clear();
                cities3.addAll(cityBeans);
                addressSelector.setCities(cities3);
            }

            @Override
            public void onError(String errorCode, String errorMsg) {

            }
        }));
    }
}
