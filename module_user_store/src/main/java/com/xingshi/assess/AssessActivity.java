package com.xingshi.assess;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xingshi.assess.adapter.AssessAdapter;
import com.xingshi.assess.adapter.AssessTitleAdapter;
import com.xingshi.mvp.BaseActivity;
import com.xingshi.user_store.R;
import com.xingshi.user_store.R2;
import com.xingshi.utils.SpaceItemDecoration;
import com.xingshi.view.CustomHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import butterknife.BindView;

public class AssessActivity extends BaseActivity<AssessView, AssessPresenter> implements AssessView {
    @BindView(R2.id.include_back)
    ImageView includeBack;
    @BindView(R2.id.include_title)
    TextView includeTitle;
    @BindView(R2.id.include_right)
    ImageView includeRight;
    @BindView(R2.id.assess_title)
    RecyclerView assessTitle;
    @BindView(R2.id.assess_shop)
    LinearLayout assessShop;
    @BindView(R2.id.assess_serve)
    LinearLayout assessServe;
    @BindView(R2.id.assess_cart)
    LinearLayout assessCart;
    @BindView(R2.id.assess_add_cart)
    TextView assessAddCart;
    @BindView(R2.id.assess_buy)
    TextView assessBuy;
    @BindView(R2.id.assess_rv)
    RecyclerView assessRv;
    @BindView(R2.id.assess_refresh)
    SmartRefreshLayout mRefresh;

    private int page = 1;
    private String id;

    @Override
    public int getLayoutId() {
        return R.layout.activity_assess;
    }

    @Override
    public void initData() {
        includeTitle.setText("宝贝评价");
        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        includeRight.setImageResource(R.drawable.icon_fenxiang11);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 4);
        assessTitle.setLayoutManager(gridLayoutManager);
        assessTitle.addItemDecoration(new SpaceItemDecoration(0, (int) getResources().getDimension(R.dimen.dp_20), 0, 0));

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        assessRv.setLayoutManager(linearLayoutManager);

        //下拉刷新样式
        CustomHeader customHeader = new CustomHeader(this);
        customHeader.setPrimaryColors(getResources().getColor(R.color.colorTransparency));
        mRefresh.setRefreshHeader(customHeader);

        presenter.loadData(page, id);
    }

    @Override
    public void initClick() {
        includeBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mRefresh.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                page++;
                presenter.loadData(page, id);
            }
        });

        mRefresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page = 1;
                presenter.loadData(page, id);
            }
        });
    }

    @Override
    public void loadFinish() {
        mRefresh.finishLoadMore();
        mRefresh.finishRefresh();
    }

    @Override
    public void loadTitle(AssessTitleAdapter adapter) {
        assessTitle.setAdapter(adapter);
    }

    @Override
    public void loadAssess(AssessAdapter adapter) {
        assessRv.setAdapter(adapter);
    }

    @Override
    public AssessView createView() {
        return this;
    }

    @Override
    public AssessPresenter createPresenter() {
        return new AssessPresenter(this);
    }
}
