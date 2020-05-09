package com.xingshi.productdetail.adapter;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.android.arouter.launcher.ARouter;
import com.xingshi.adapter.MyRecyclerAdapter;
import com.xingshi.adapter.RecyclerViewHolder;
import com.xingshi.module_home.R;

import java.util.List;

public class ProductAddressAdapter extends MyRecyclerAdapter<String> {
    public ProductAddressAdapter(Context context, List<String> mList, int mLayoutId) {
        super(context, mList, mLayoutId);
    }

    @Override
    public void convert(RecyclerViewHolder holder, final String data, int position) {
        TextView txt = holder.getView(R.id.rv_product_detail_account);
        txt.setText(data);
        txt.setTextColor(Color.parseColor("#f74c21"));
        txt.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ARouter.getInstance().build("/module_home/WebDetailActivity").withString("url", data).navigation();
            }
        });

        holder.getView(R.id.rv_product_detail_copy).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setClipboard(data);
            }
        });
    }

    private void setClipboard(String content) {
        //获取剪贴板管理器：
        ClipboardManager cm = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        // 创建普通字符型ClipData
        ClipData mClipData = ClipData.newPlainText("Label", content);
        // 将ClipData内容放到系统剪贴板里。
        cm.setPrimaryClip(mClipData);
        Toast.makeText(context, "复制成功", Toast.LENGTH_SHORT).show();
    }
}
