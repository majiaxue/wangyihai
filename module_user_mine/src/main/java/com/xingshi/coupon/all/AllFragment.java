package com.xingshi.coupon.all;

import android.annotation.SuppressLint;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xingshi.common.CommonResource;
import com.xingshi.coupon.adapter.LocalCouponWalletAdapter;
import com.xingshi.module_user_mine.R;
import com.xingshi.module_user_mine.R2;
import com.xingshi.mvp.BaseFragment;
import com.xingshi.utils.SpaceItemDecoration;

import butterknife.BindView;

public class AllFragment extends BaseFragment<AllView, AllPresenter> implements AllView {

    @BindView(R2.id.all_rec)
    RecyclerView allRec;
    @BindView(R2.id.all_go)
    TextView allGo;
    @BindView(R2.id.all_hide)
    LinearLayout allHide;

    private String from;
    private int flag = 0;

    public AllFragment() {
    }

    @SuppressLint("ValidFragment")
    public AllFragment(String from) {
        this.from = from;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_all;
    }

    @Override
    public void initData() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        allRec.setLayoutManager(linearLayoutManager);
        if (CommonResource.HISTORY_LOCAL.equals(from)) {
            allRec.addItemDecoration(new SpaceItemDecoration(0, 0, 0, (int) getContext().getResources().getDimension(R.dimen.dp_10)));
            presenter.localMyCoupon();
        } else {
            presenter.allRec(allRec);
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
                presenter.allRec(allRec);
            }
        }
    }

    @Override
    public void loadRv(LocalCouponWalletAdapter adapter) {
        allRec.setAdapter(adapter);
    }

    @Override
    public AllView createView() {
        return this;
    }

    @Override
    public AllPresenter createPresenter() {
        return new AllPresenter(getContext());
    }

}
