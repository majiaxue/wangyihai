package com.xingshi.y_mine.y_welfare_center.i_released;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.scwang.smartrefresh.header.MaterialHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.xingshi.mvp.BaseActivity;
import com.xingshi.y_main.R;
import com.xingshi.y_main.R2;
import com.xingshi.y_mine.y_welfare_center.i_released.adapter.FuFeiXianBaoAdapter;
import com.xingshi.y_mine.y_welfare_center.i_released.adapter.YongJinTaskAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 我发布的
 */
public class IReleasedActivity extends BaseActivity<IReleasedView, IReleasedPresenter> implements IReleasedView {

    @BindView(R2.id.i_released_back)
    ImageView iReleasedBack;
    @BindView(R2.id.i_released_tab)
    TabLayout iReleasedTab;
    @BindView(R2.id.i_released_rec)
    RecyclerView iReleasedRec;
    @BindView(R2.id.i_released_smart)
    SmartRefreshLayout iReleasedSmart;

    private int page = 1;

    @Override
    public int getLayoutId() {
        return R.layout.activity_ireleased;
    }

    @Override
    public void initData() {
        presenter.initTab(iReleasedTab);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        iReleasedRec.setLayoutManager(linearLayoutManager);

        presenter.yongjin(page);

        iReleasedSmart.setRefreshHeader(new MaterialHeader(this));
        iReleasedSmart.setRefreshFooter(new ClassicsFooter(this));

        iReleasedSmart.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page = 1;
                if (0 == iReleasedTab.getSelectedTabPosition()) {
                    presenter.yongjin(page);
                } else {
                    presenter.fufei(page);
                }
            }
        });

        iReleasedSmart.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                page++;
                if (0 == iReleasedTab.getSelectedTabPosition()) {
                    presenter.yongjin(page);
                } else {
                    presenter.fufei(page);
                }
            }
        });
    }

    @Override
    public void initClick() {
        iReleasedBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public IReleasedView createView() {
        return this;
    }

    @Override
    public IReleasedPresenter createPresenter() {
        return new IReleasedPresenter(this);
    }

    @Override
    public void loadAdapter(YongJinTaskAdapter adapter) {
        iReleasedRec.setAdapter(adapter);
    }

    @Override
    public void loadAdapter(FuFeiXianBaoAdapter adapter) {
        iReleasedRec.setAdapter(adapter);
    }

    @Override
    public void finishRefresh() {
        iReleasedSmart.finishRefresh();
        iReleasedSmart.finishLoadMore();
    }
}
