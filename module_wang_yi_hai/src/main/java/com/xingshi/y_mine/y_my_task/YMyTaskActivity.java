package com.xingshi.y_mine.y_my_task;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.xingshi.mvp.BaseActivity;
import com.xingshi.y_main.R;
import com.xingshi.y_main.R2;
import com.xingshi.y_mine.y_my_task.adapter.YMyTaskAdapter;
import com.xingshi.y_mine.y_my_task.take_records.TakeRecordsActivity;
import com.scwang.smartrefresh.header.MaterialHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import butterknife.BindView;

/**
 * 我的任务
 */
public class YMyTaskActivity extends BaseActivity<YMyTaskView, YMyTaskPresenter> implements YMyTaskView {


    @BindView(R2.id.y_my_task_back)
    ImageView yMyTaskBack;
    @BindView(R2.id.y_my_task_get_record)
    TextView yMyTaskGetRecord;
    @BindView(R2.id.y_my_task_rec)
    RecyclerView yMyTaskRec;
    @BindView(R2.id.y_my_task_smart)
    SmartRefreshLayout yMyTaskSmart;

    private int page = 1;

    @Override
    public int getLayoutId() {
        return R.layout.activity_ymy_task;
    }

    @Override
    public void initData() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2, LinearLayoutManager.VERTICAL, false);
        yMyTaskRec.setLayoutManager(gridLayoutManager);
        presenter.initData(page);
        
        yMyTaskSmart.setRefreshHeader(new MaterialHeader(this));
        yMyTaskSmart.setRefreshFooter(new ClassicsFooter(this));
        
        yMyTaskSmart.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page = 1;
                presenter.initData(page);
            }
        });

        yMyTaskSmart.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                page++;
                presenter.initData(page);
            }
        });
    }

    @Override
    public void initClick() {
        yMyTaskBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //获取记录
        yMyTaskGetRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(YMyTaskActivity.this, TakeRecordsActivity.class));
            }
        });
    }

    @Override
    public YMyTaskView createView() {
        return this;
    }

    @Override
    public YMyTaskPresenter createPresenter() {
        return new YMyTaskPresenter(this);
    }

    @Override
    public void loadAdapter(YMyTaskAdapter yMyTaskAdapter) {
        yMyTaskRec.setAdapter(yMyTaskAdapter);
    }

    @Override
    public void finishRefresh() {
        yMyTaskSmart.finishRefresh();
        yMyTaskSmart.finishLoadMore();
    }
}
