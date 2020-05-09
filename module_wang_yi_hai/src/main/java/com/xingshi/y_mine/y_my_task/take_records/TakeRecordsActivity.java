package com.xingshi.y_mine.y_my_task.take_records;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.xingshi.mvp.BaseActivity;
import com.xingshi.y_main.R;
import com.xingshi.y_main.R2;
import com.xingshi.y_mine.y_my_task.take_records.adapter.TakeRecordsAdapter;
import com.scwang.smartrefresh.header.MaterialHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import butterknife.BindView;

/**
 * 区块获取记录
 */
public class TakeRecordsActivity extends BaseActivity<TakeRecordsView, TakeRecordsPresenter> implements TakeRecordsView {


    @BindView(R2.id.y_take_records_back)
    ImageView yTakeRecordsBack;
    @BindView(R2.id.y_take_records_rec)
    RecyclerView yTakeRecordsRec;
    @BindView(R2.id.y_take_records_smart)
    SmartRefreshLayout yTakeRecordsSmart;

    private int page = 1;

    @Override
    public int getLayoutId() {
        return R.layout.activity_take_records;
    }

    @Override
    public void initData() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        yTakeRecordsRec.setLayoutManager(linearLayoutManager);
        presenter.initView(page);

        yTakeRecordsSmart.setRefreshHeader(new MaterialHeader(this));
        yTakeRecordsSmart.setRefreshFooter(new ClassicsFooter(this));

        yTakeRecordsSmart.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page = 1;
                presenter.initView(page);
            }
        });

        yTakeRecordsSmart.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                page++;
                presenter.initView(page);
            }
        });
    }

    @Override
    public void initClick() {
        yTakeRecordsBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public TakeRecordsView createView() {
        return this;
    }

    @Override
    public TakeRecordsPresenter createPresenter() {
        return new TakeRecordsPresenter(this);
    }

    @Override
    public void loadAdapter(TakeRecordsAdapter takeRecordsAdapter) {
        yTakeRecordsRec.setAdapter(takeRecordsAdapter);
    }

    @Override
    public void finishRefresh() {
        yTakeRecordsSmart.finishRefresh();
        yTakeRecordsSmart.finishLoadMore();
    }
}
