package com.xingshi.points_mingxi.cashout_record;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.xingshi.module_mine.R;
import com.xingshi.module_mine.R2;
import com.xingshi.mvp.BaseFragment;
import com.xingshi.points_mingxi.adapter.CashoutRecordAdapter;
import com.xingshi.utils.SpaceItemDecoration;

import butterknife.BindView;

public class CashoutRecordFragment extends BaseFragment<CashoutRecordView, CashoutRecordPresenter> implements CashoutRecordView {
    @BindView(R2.id.cashout_record_rv)
    RecyclerView cashoutRecordRv;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_cashout_record;
    }

    @Override
    public void initData() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        cashoutRecordRv.setLayoutManager(layoutManager);
        cashoutRecordRv.addItemDecoration(new SpaceItemDecoration(0, 0, 0, (int) getContext().getResources().getDimension(R.dimen.dp_8)));
        presenter.loadData();
    }

    @Override
    public void initClick() {

    }

    @Override
    public void loadRv(CashoutRecordAdapter adapter) {
        cashoutRecordRv.setAdapter(adapter);
    }

    @Override
    public CashoutRecordView createView() {
        return this;
    }

    @Override
    public CashoutRecordPresenter createPresenter() {
        return new CashoutRecordPresenter(getContext());
    }
}
