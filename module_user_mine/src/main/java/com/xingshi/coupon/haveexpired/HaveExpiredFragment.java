package com.xingshi.coupon.haveexpired;

import android.annotation.SuppressLint;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.xingshi.common.CommonResource;
import com.xingshi.coupon.adapter.LocalCouponWalletAdapter;
import com.xingshi.module_user_mine.R;
import com.xingshi.module_user_mine.R2;
import com.xingshi.mvp.BaseFragment;
import com.xingshi.utils.SpaceItemDecoration;

import butterknife.BindView;

public class HaveExpiredFragment extends BaseFragment<HaveExpiredView, HaveExpiredPresenter> implements HaveExpiredView {

    @BindView(R2.id.have_expired_rec)
    RecyclerView haveExpiredRec;

    private String from;
    private int flag = 0;

    public HaveExpiredFragment() {
    }

    @SuppressLint("ValidFragment")
    public HaveExpiredFragment(String from) {
        this.from = from;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_have_expired_;
    }

    @Override
    public void initData() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        haveExpiredRec.setLayoutManager(linearLayoutManager);

        if (CommonResource.HISTORY_LOCAL.equals(from)) {
            haveExpiredRec.addItemDecoration(new SpaceItemDecoration(0, 0, 0, (int) getContext().getResources().getDimension(R.dimen.dp_10)));
            presenter.localGuoQiCoupon();
        } else {
            presenter.haveExpiredRec(haveExpiredRec);
            flag = 1;
        }
    }

    @Override
    public void initClick() {

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            if (flag == 1) {
                presenter.haveExpiredRec(haveExpiredRec);
            }
        }
    }


    @Override
    public void loadRv(LocalCouponWalletAdapter adapter) {
        haveExpiredRec.setAdapter(adapter);
    }

    @Override
    public HaveExpiredView createView() {
        return this;
    }

    @Override
    public HaveExpiredPresenter createPresenter() {
        return new HaveExpiredPresenter(getContext());
    }

}
