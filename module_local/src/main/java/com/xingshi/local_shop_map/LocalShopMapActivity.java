package com.xingshi.local_shop_map;

import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BaiduMapOptions;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.xingshi.bean.LocalShopBean;
import com.xingshi.module_local.R;
import com.xingshi.module_local.R2;
import com.xingshi.mvp.BaseActivity;

import butterknife.BindView;

@Route(path = "/module_local/LocalShopMapActivity")
public class LocalShopMapActivity extends BaseActivity<LocalShopMapView, LocalShopMapPresenter> implements LocalShopMapView {
    @BindView(R2.id.local_shop_map_mapview)
    MapView localShopMapMapview;
    @BindView(R2.id.local_shop_map_btn)
    Button localShopMapBtn;
    @BindView(R2.id.include_back)
    ImageView includeBack;
    @BindView(R2.id.include_title)
    TextView includeTitle;

    @Autowired(name = "bean")
    LocalShopBean bean;


    private BaiduMap baiduMap;

    @Override
    public int getLayoutId() {
        return R.layout.activity_local_shop_map;
    }

    @Override
    public void initData() {
        ARouter.getInstance().inject(this);
        includeTitle.setText(bean.getSeller_shop_name());
        try {

            baiduMap = localShopMapMapview.getMap();

            LatLng latLng = new LatLng(Double.valueOf(bean.getSeller_lat()), Double.valueOf(bean.getSeller_lon()));
            MapStatusUpdate statusUpdate = MapStatusUpdateFactory.newLatLng(latLng);

            BaiduMapOptions options = new BaiduMapOptions();
            options.mapType(BaiduMap.MAP_TYPE_SATELLITE);

            baiduMap.setMapStatus(statusUpdate);

            //设置地图缩放级别
            MapStatus.Builder builder = new MapStatus.Builder();
            builder.zoom(18.0f);
            baiduMap.setMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));

            //构建Marker图标
            BitmapDescriptor bitmap = BitmapDescriptorFactory
                    .fromResource(R.drawable.icon_weizhi);

            //构建MarkerOption，用于在地图上添加Marker
            OverlayOptions option = new MarkerOptions()
                    .position(latLng)
                    .perspective(true)
                    .draggable(false)
                    .icon(bitmap);

            //在地图上添加Marker，并显示
            baiduMap.addOverlay(option);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initClick() {
        includeBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        localShopMapBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //唤起百度地图并标记地点
                Intent intent = new Intent();
                intent.setData(Uri.parse("baidumap://map/marker?location=" + bean.getSeller_lat() + "," + bean.getSeller_lon() + "&title=" + bean.getSeller_shop_name() + "&src=andr.xingshi.wyh"));
                startActivity(intent);

            }
        });
    }

    @Override
    public LocalShopMapView createView() {
        return this;
    }

    @Override
    public LocalShopMapPresenter createPresenter() {
        return new LocalShopMapPresenter(this);
    }
}
