package com.xingshi.location.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.xingshi.adapter.MyRecyclerAdapter;
import com.xingshi.adapter.RecyclerViewHolder;
import com.xingshi.bean.RegionBean;
import com.xingshi.common.CommonResource;
import com.xingshi.user_store.R;
import com.xingshi.utils.CitySPUtil;

import java.util.List;

public class RegionAdapter extends MyRecyclerAdapter<RegionBean.CityBean> {

    private static int RESULTCODE = 0;

    public RegionAdapter(Context context, List<RegionBean.CityBean> mList, int mLayoutId) {
        super(context, mList, mLayoutId);
    }

    @Override
    public void convert(RecyclerViewHolder holder, final RegionBean.CityBean data, int position) {

        holder.setText(R.id.item_letter, data.getLabel());
        RecyclerView itemRegionList = holder.getView(R.id.item_regionList);
        TextView checkText = holder.getView(R.id.item_check_text);
        if (position == 0) {
            checkText.setVisibility(View.VISIBLE);
//            GridLayoutManager gridLayoutManager = new GridLayoutManager(context, 3, LinearLayoutManager.VERTICAL, false);
//            LocationHotCityAdapter locationHotCityAdapter = new LocationHotCityAdapter(context, data.getRegion(), R.layout.item_location_hot_city_rec);
//            itemRegionList.setLayoutManager(gridLayoutManager);
//            itemRegionList.setAdapter(locationHotCityAdapter);
//            locationHotCityAdapter.setOnItemClick(new OnItemClickListener() {
//                @Override
//                public void onItemClick(RecyclerView parent, View view, int position) {
//
//
//                }
//            });
            if (data.getRegion().size()==0){
                itemRegionList.setVisibility(View.GONE);
            }else{
                itemRegionList.setVisibility(View.VISIBLE);
            }
        } else if (position > 0 && position < 3) {
            GridLayoutManager gridLayoutManager = new GridLayoutManager(context, 3, LinearLayoutManager.VERTICAL, false);
            LocationHotCityAdapter locationHotCityAdapter = new LocationHotCityAdapter(context, data.getRegion(), R.layout.item_location_hot_city_rec);
            itemRegionList.setLayoutManager(gridLayoutManager);
            itemRegionList.setAdapter(locationHotCityAdapter);
            locationHotCityAdapter.setOnItemClick(new OnItemClickListener() {
                @Override
                public void onItemClick(RecyclerView parent, View view, int position) {
                    TextView hotCityName = view.findViewById(R.id.location_hot_city_name);
                    CitySPUtil.addParm(CommonResource.CITY, hotCityName.getText().toString());
                    Intent intent = new Intent();
                    intent.putExtra("cityName", hotCityName.getText().toString());
                    ((Activity) context).setResult(RESULTCODE, intent);
                    ((Activity) context).finish();
                }
            });
        } else {
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
            LocationSelectAdapter locationSelectAdapter = new LocationSelectAdapter(context, data.getRegion(), R.layout.item_location_select_rec);
            itemRegionList.setLayoutManager(linearLayoutManager);
            itemRegionList.setAdapter(locationSelectAdapter);
            locationSelectAdapter.setOnItemClick(new OnItemClickListener() {
                @Override
                public void onItemClick(RecyclerView parent, View view, int position) {
                    TextView itemText = view.findViewById(R.id.item_text);
                    CitySPUtil.addParm(CommonResource.CITY, itemText.getText().toString());
                    Intent intent = new Intent();
                    intent.putExtra("cityName", itemText.getText().toString());
                    ((Activity) context).setResult(RESULTCODE, intent);
                    ((Activity) context).finish();
                }
            });
        }

    }


}
