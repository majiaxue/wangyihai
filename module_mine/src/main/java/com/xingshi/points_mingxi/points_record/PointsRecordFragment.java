package com.xingshi.points_mingxi.points_record;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.xingshi.module_mine.R;
import com.xingshi.module_mine.R2;
import com.xingshi.mvp.BaseFragment;
import com.xingshi.points_mingxi.adapter.PointsRecordAdapter;
import com.xingshi.utils.SpaceItemDecoration;

import butterknife.BindView;

public class PointsRecordFragment extends BaseFragment<PointsRecordView, PointsRecordPresenter> implements PointsRecordView {
    @BindView(R2.id.points_record_rv)
    RecyclerView pointsRecordRv;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_points_record;
    }

    @Override
    public void initData() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        pointsRecordRv.setLayoutManager(layoutManager);
        pointsRecordRv.addItemDecoration(new SpaceItemDecoration(0, 0, 0, (int) getContext().getResources().getDimension(R.dimen.dp_8)));
        presenter.loadData();
    }

    @Override
    public void initClick() {

    }

    @Override
    public void loadRv(PointsRecordAdapter adapter) {
        pointsRecordRv.setAdapter(adapter);
    }

    @Override
    public PointsRecordView createView() {
        return this;
    }

    @Override
    public PointsRecordPresenter createPresenter() {
        return new PointsRecordPresenter(getContext());
    }
}
