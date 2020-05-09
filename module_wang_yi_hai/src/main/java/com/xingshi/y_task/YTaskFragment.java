package com.xingshi.y_task;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.xingshi.mvp.BaseFragment;
import com.xingshi.y_main.R;
import com.xingshi.y_main.R2;
import com.xingshi.y_task.adapter.YTaskAdapter;

import butterknife.BindView;

/**
 * 区块任务
 */
public class YTaskFragment extends BaseFragment<YTaskView, YTaskPresenter> implements YTaskView {

    @BindView(R2.id.y_task_rec)
    RecyclerView yTaskRec;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_ytask;
    }

    @Override
    public void initData() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2, LinearLayoutManager.VERTICAL, false);
        yTaskRec.setLayoutManager(gridLayoutManager);
        presenter.initData();
    }

    @Override
    public void initClick() {

    }

    @Override
    public YTaskView createView() {
        return this;
    }

    @Override
    public YTaskPresenter createPresenter() {
        return new YTaskPresenter(getContext());
    }

    @Override
    public void loadAdapter(YTaskAdapter taskAdapter) {
        yTaskRec.setAdapter(taskAdapter);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden) {
            //不可见
        } else {
            presenter.initData();
        }
    }
}
