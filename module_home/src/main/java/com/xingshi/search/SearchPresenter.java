package com.xingshi.search;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.xingshi.common.CommonResource;
import com.xingshi.dbflow.DBflowUtil;
import com.xingshi.dbflow.SearchHistoryBean;
import com.xingshi.module_home.R;
import com.xingshi.mvp.BasePresenter;
import com.xingshi.view.FlowLayout;

import java.util.List;

/**
 * Created by cuihaohao on 2019/5/22
 * Describe:
 */
public class SearchPresenter extends BasePresenter<SearchView> {
    public int position = 0;

    public SearchPresenter(Context context) {
        super(context);
    }

    @Override
    protected void onViewDestroy() {

    }

    public void getHistory(FlowLayout searchFlowLayout) {
        final List<SearchHistoryBean> list = DBflowUtil.getInstance().query(CommonResource.HISTORY_TBK);
        for (int i = 0; i < list.size(); i++) {
            TextView searchTextView = (TextView) LayoutInflater.from(mContext).inflate(R.layout.search_text_view, searchFlowLayout, false);
            searchTextView.setText(list.get(i).getContent());
            final int finalI = i;
            searchTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    searchEdit(list.get(finalI).getContent());
                }
            });
            searchFlowLayout.addView(searchTextView);
        }
    }

    public void searchEdit(String content) {
        if (content != null && !"".equals(content.trim())) {
            DBflowUtil.getInstance().insert(content, CommonResource.HISTORY_TBK);
        }

        ARouter.getInstance().build("/module_classify/ClassificationDetailsActivity").withInt("position", position).withString("searchContent", content).navigation();
    }
}
