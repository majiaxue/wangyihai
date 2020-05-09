package com.xingshi.y_mine.y_welfare_center.commission_task;

import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.xingshi.mvp.BaseActivity;
import com.xingshi.y_main.R;
import com.xingshi.y_main.R2;
import com.xingshi.y_mine.y_welfare_center.commission_task.adapter.CommissionTaskAdapter;
import com.xingshi.y_mine.y_welfare_center.release_a_task.adapter.ReleaseATaskTabAdapter;
import com.scwang.smartrefresh.header.MaterialHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import butterknife.BindView;

/**
 * 区块佣金任务
 */
public class CommissionTaskActivity extends BaseActivity<CommissionTaskView, CommissionTaskPresenter> implements CommissionTaskView {


    @BindView(R2.id.commission_task_back)
    ImageView commissionTaskBack;
    @BindView(R2.id.commission_task_rec_title)
    RecyclerView commissionTaskRecTitle;
    @BindView(R2.id.commission_task_linear)
    LinearLayout commissionTaskLinear;
    @BindView(R2.id.commission_task_rec_bottom)
    RecyclerView commissionTaskRecBottom;
    @BindView(R2.id.commission_task_rec_smart)
    SmartRefreshLayout commissionTaskRecSmart;

    private int page = 1;
    private int id;

    @Override
    public int getLayoutId() {
        return R.layout.activity_commission_task;
    }

    @Override
    public void initData() {
        //任务类型
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 6, LinearLayoutManager.VERTICAL, false);
        commissionTaskRecTitle.setLayoutManager(gridLayoutManager);
        presenter.initTab();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        commissionTaskRecBottom.setLayoutManager(linearLayoutManager);

        commissionTaskRecSmart.setRefreshHeader(new MaterialHeader(this));
        commissionTaskRecSmart.setRefreshFooter(new ClassicsFooter(this));

        commissionTaskRecSmart.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page = 1;
                presenter.initData(id, page);
            }
        });

        commissionTaskRecSmart.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                page++;
                presenter.initData(id, page);
            }
        });
    }

    @Override
    public void initClick() {

    }

    @Override
    public CommissionTaskView createView() {
        return this;
    }

    @Override
    public CommissionTaskPresenter createPresenter() {
        return new CommissionTaskPresenter(this);
    }

    @Override
    public void getType(int id) {
        this.id = id;
        presenter.initData(id, page);
    }

    @Override
    public void loadAdapter(ReleaseATaskTabAdapter releaseATaskTabAdapter) {
        commissionTaskRecTitle.setAdapter(releaseATaskTabAdapter);
    }

    @Override
    public void loadAdapter(CommissionTaskAdapter commissionTaskAdapter) {
        commissionTaskRecBottom.setAdapter(commissionTaskAdapter);
    }

    @Override
    public void finishRefresh() {
        commissionTaskRecSmart.finishRefresh();
        commissionTaskRecSmart.finishLoadMore();
    }
}
