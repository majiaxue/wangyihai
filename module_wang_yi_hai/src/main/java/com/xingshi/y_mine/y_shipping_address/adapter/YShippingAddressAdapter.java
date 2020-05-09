package com.xingshi.y_mine.y_shipping_address.adapter;

import android.content.Context;

import com.xingshi.adapter.MyRecyclerAdapter;
import com.xingshi.adapter.RecyclerViewHolder;
import com.xingshi.bean.ShippingAddressBean;
import com.xingshi.y_main.R;

import java.util.List;

/**
 * Created by cuihaohao on 2019/5/23
 * Describe:
 */
public class YShippingAddressAdapter extends MyRecyclerAdapter<ShippingAddressBean> {

    public YShippingAddressAdapter(Context context, List<ShippingAddressBean> mList, int mLayoutId) {
        super(context, mList, mLayoutId);
    }

    @Override
    public void convert(RecyclerViewHolder holder, ShippingAddressBean data, int position) {
        if (data.getAddressDefault() == 1) {
            holder.setImageResource(R.id.y_shipping_address_check, R.drawable.icon_xuanzhong20);
        } else {
            holder.setImageResource(R.id.y_shipping_address_check, R.drawable.icon_weixuanzhong20);
        }

        if (data.getAddressTips() == 1) {
            holder.setText(R.id.y_shipping_address_biaoqian, "家");
        } else if (data.getAddressTips() == 2) {
            holder.setText(R.id.y_shipping_address_biaoqian, "公司");
        } else {
            holder.setText(R.id.y_shipping_address_biaoqian, "学校");
        }

        holder.setText(R.id.y_shipping_address_name, data.getAddressName());
        holder.setText(R.id.y_shipping_address_phone, data.getAddressPhone());
        holder.setText(R.id.y_shipping_address_site, data.getAddressProvince() + data.getAddressCity() + data.getAddressArea() + data.getAddressDetail());

        viewThreeOnClickListener.ViewThreeOnClick(holder.getView(R.id.y_shipping_address_check), holder.getView(R.id.y_shipping_address_amend), holder.getView(R.id.y_shipping_address_delete), position);
    }
}
