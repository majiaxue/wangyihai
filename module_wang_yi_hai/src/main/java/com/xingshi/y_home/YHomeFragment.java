package com.xingshi.y_home;

import android.support.annotation.NonNull;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import com.xingshi.mvp.BaseFragment;
import com.xingshi.view.CustomHeader;
import com.xingshi.y_home.adapter.YHomeBottomAdapter;
import com.xingshi.y_main.R;
import com.xingshi.y_main.R2;
import com.kongzue.tabbar.TabBarView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.stx.xhb.xbanner.XBanner;

import butterknife.BindView;

/**
 * 区块首页
 */
public class YHomeFragment extends BaseFragment<YHomeView, YHomePresenter> implements YHomeView {

    @BindView(R2.id.y_home_xbanner)
    XBanner yHomeXbanner;
    @BindView(R2.id.y_home_top)
    TabBarView yHomeTop;
    @BindView(R2.id.y_home_advertising_position)
    ImageView yHomeAdvertisingPosition;
    @BindView(R2.id.y_home_bottom)
    RecyclerView yHomeBottom;
    @BindView(R2.id.y_home_nested_scroll)
    NestedScrollView yHomeNestedScroll;
    @BindView(R2.id.y_home_smart_refresh)
    SmartRefreshLayout yHomeSmartRefresh;

    private int page = 1;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_yhome;
    }

    @Override
    public void initData() {
        //轮播图
        presenter.setXBanner(yHomeXbanner);
        //标题栏目
        presenter.setRecTop(yHomeTop);
        GridLayoutManager yHomeBottomManager = new GridLayoutManager(getContext(), 2, LinearLayoutManager.VERTICAL, false);
        yHomeBottom.setLayoutManager(yHomeBottomManager);
        //商品
        presenter.setRecBottom(page);

        //下拉刷新样式
        CustomHeader customHeader = new CustomHeader(getActivity());
        customHeader.setPrimaryColors(getResources().getColor(R.color.colorTransparency));
        yHomeSmartRefresh.setRefreshHeader(customHeader);
        yHomeSmartRefresh.setRefreshFooter(new ClassicsFooter(getContext()));

        //设置上拉刷新下拉加载
        yHomeSmartRefresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page = 1;
                presenter.setRecBottom(page);
            }
        });
        yHomeSmartRefresh.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                page++;
                presenter.setRecBottom(page);
            }
        });
    }

    @Override
    public void initClick() {

    }

    @Override
    public YHomeView createView() {
        return this;
    }

    @Override
    public YHomePresenter createPresenter() {
        return new YHomePresenter(getContext());
    }

    @Override
    public void loadAdapter(YHomeBottomAdapter adapter) {
        yHomeBottom.setAdapter(adapter);
    }

    @Override
    public void finishRefresh() {
        yHomeSmartRefresh.finishLoadMore();
        yHomeSmartRefresh.finishRefresh();
    }
}
