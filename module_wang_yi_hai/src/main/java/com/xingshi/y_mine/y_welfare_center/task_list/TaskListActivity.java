package com.xingshi.y_mine.y_welfare_center.task_list;

import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.xingshi.common.CommonResource;
import com.xingshi.entity.EventBusBean;
import com.xingshi.mvp.BaseActivity;
import com.xingshi.y_main.R;
import com.xingshi.y_main.R2;
import com.xingshi.y_mine.y_welfare_center.task_list.adapter.TaskListAdapter;
import com.scwang.smartrefresh.header.MaterialHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;

/**
 * 区块福利中心任务列表
 */
public class TaskListActivity extends BaseActivity<TaskListView, TaskListPresenter> implements TaskListView {


    @BindView(R2.id.y_task_list_back)
    ImageView yTaskListBack;
    @BindView(R2.id.y_task_list_tab)
    TabLayout yTaskListTab;
    @BindView(R2.id.y_task_list_rec)
    RecyclerView yTaskListRec;
    @BindView(R2.id.y_task_list_smart)
    SmartRefreshLayout yTaskListSmart;

    private int page = 1;
    private int id;

    @Override
    public int getLayoutId() {
        return R.layout.activity_task_list;
    }

    @Override
    public void initData() {

        presenter.initTab(yTaskListTab);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        yTaskListRec.setLayoutManager(linearLayoutManager);
        presenter.taskList(page, yTaskListTab.getSelectedTabPosition());

        yTaskListSmart.setRefreshHeader(new MaterialHeader(this));
        yTaskListSmart.setRefreshFooter(new ClassicsFooter(this));

        yTaskListSmart.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page = 1;
                presenter.taskList(page,yTaskListTab.getSelectedTabPosition());
            }
        });

        yTaskListSmart.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                page++;
                presenter.taskList(page,yTaskListTab.getSelectedTabPosition());
            }
        });
    }

    @Override
    public void initClick() {
        yTaskListBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(EventBusBean eventBusBean) {
        if (CommonResource.QUKUAI.equals(eventBusBean.getMsg())) {
            //微信支付成功回调
            presenter.isPay(id);
        }
    }

    @Override
    public TaskListView createView() {
        return this;
    }

    @Override
    public TaskListPresenter createPresenter() {
        return new TaskListPresenter(this);
    }

    @Override
    public void loadAdapter(TaskListAdapter taskListAdapter) {
        yTaskListRec.setAdapter(taskListAdapter);
    }

    @Override
    public void id(int id) {
        this.id = id;
    }

    @Override
    public void finishRefresh() {
        yTaskListSmart.finishRefresh();
        yTaskListSmart.finishLoadMore();
    }
}
