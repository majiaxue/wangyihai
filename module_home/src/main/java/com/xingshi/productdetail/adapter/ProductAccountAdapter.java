package com.xingshi.productdetail.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.xingshi.adapter.MyRecyclerAdapter;
import com.xingshi.adapter.RecyclerViewHolder;
import com.xingshi.bean.TestAccountBean;
import com.xingshi.module_home.R;
import com.xingshi.utils.SpaceItemDecoration;

import java.util.List;

public class ProductAccountAdapter extends MyRecyclerAdapter<TestAccountBean> {
    public ProductAccountAdapter(Context context, List<TestAccountBean> mList, int mLayoutId) {
        super(context, mList, mLayoutId);
    }

    @Override
    public void convert(RecyclerViewHolder holder, TestAccountBean data, int position) {
        holder.setText(R.id.rv_product_detail_name, data.getTitle() + "：");

        RecyclerView addressRv = holder.getView(R.id.rv_product_detail_download_rv);
        RecyclerView accountRv = holder.getView(R.id.rv_product_detail_account_rv);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        addressRv.setLayoutManager(linearLayoutManager);
        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        accountRv.setLayoutManager(linearLayoutManager2);


        ProductAddressAdapter addressAdapter = new ProductAddressAdapter(context, data.getAddressList(), R.layout.rv_product_detai_inside);
        addressRv.setAdapter(addressAdapter);
        addressRv.addItemDecoration(new SpaceItemDecoration(0, 0, 0, (int) context.getResources().getDimension(R.dimen.dp_5)));

        ProductAccountInsideAdapter accountAdapter = new ProductAccountInsideAdapter(context, data.getAccountList(), R.layout.rv_product_detail_account);
        accountRv.setAdapter(accountAdapter);
        accountRv.addItemDecoration(new SpaceItemDecoration(0, 0, 0, (int) context.getResources().getDimension(R.dimen.dp_5)));
    }
}
