package com.xingshi.y_mine.y_welfare_center.i_released;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.view.View;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.xingshi.adapter.MyRecyclerAdapter;
import com.xingshi.bean.FuFeiXianBaoBean;
import com.xingshi.bean.YongJinTaskBean;
import com.xingshi.common.CommonResource;
import com.xingshi.mvp.BasePresenter;
import com.xingshi.net.OnDataListener;
import com.xingshi.net.OnMyCallBack;
import com.xingshi.net.RetrofitUtil;
import com.xingshi.utils.LogUtil;
import com.xingshi.utils.MapUtil;
import com.xingshi.utils.ProcessDialogUtil;
import com.xingshi.utils.SPUtil;
import com.xingshi.y_main.R;
import com.xingshi.y_mine.y_welfare_center.i_released.adapter.FuFeiXianBaoAdapter;
import com.xingshi.y_mine.y_welfare_center.i_released.adapter.YongJinTaskAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;

public class IReleasedPresenter extends BasePresenter<IReleasedView> {

    private String[] titleArr = {"佣金任务", "付费线报"};
    private List<YongJinTaskBean.RecordsBean> yongJinList = new ArrayList<>();
    private List<FuFeiXianBaoBean.RecordsBean> fuFeiList = new ArrayList<>();
    private YongJinTaskAdapter yongJinTaskAdapter;
    private FuFeiXianBaoAdapter fuFeiXianBaoAdapter;

    public IReleasedPresenter(Context context) {
        super(context);
    }

    @Override
    protected void onViewDestroy() {

    }

    public void initTab(TabLayout yTaskListTab) {
        for (String title : titleArr) {
            yTaskListTab.addTab(yTaskListTab.newTab().setText(title));
        }

        yTaskListTab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                ProcessDialogUtil.showProcessDialog(mContext);
                if (0 == tab.getPosition()) {
                    yongjin(1);
                } else {
                    fufei(1);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    public void yongjin(final int page) {
        Map map = MapUtil.getInstance().addParms("pageSize", 10).addParms("pageNum", 1).build();
        Observable head = RetrofitUtil.getInstance().getApi(CommonResource.BASEURL_9006).getHead(CommonResource.MYRELEASECOMMISSIONTASKLIST, map, SPUtil.getToken());
        RetrofitUtil.getInstance().toSubscribe(head, new OnMyCallBack(new OnDataListener() {
            @Override
            public void onSuccess(String result, String msg) {
                LogUtil.e("我发布的佣金任务" + result);
                getView().finishRefresh();
                YongJinTaskBean yongJinTaskBean = JSON.parseObject(result, YongJinTaskBean.class);
                if (1 == page) {
                    yongJinList.clear();
                }
                yongJinList.addAll(yongJinTaskBean.getRecords());
                yongJinTaskAdapter = new YongJinTaskAdapter(mContext, yongJinList, R.layout.item_yongjin_task_rec);
                getView().loadAdapter(yongJinTaskAdapter);
                yongJinTaskAdapter.setViewOnClickListener(new MyRecyclerAdapter.ViewOnClickListener() {
                    @Override
                    public void ViewOnClick(View view, final int index) {
                        view.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                cancelTask(yongJinList.get(index).getId());
                            }
                        });
                    }
                });
            }

            @Override
            public void onError(String errorCode, String errorMsg) {
                LogUtil.e("我发布的佣金任务" + errorMsg);
                getView().finishRefresh();
            }
        }));
    }

    public void fufei(final int page) {
        Map map = MapUtil.getInstance().addParms("pageSize", 10).addParms("pageNum", page).build();
        Observable head = RetrofitUtil.getInstance().getApi(CommonResource.BASEURL_9006).getHead(CommonResource.MYNEWSPAPERLIST, map, SPUtil.getToken());
        RetrofitUtil.getInstance().toSubscribe(head, new OnMyCallBack(new OnDataListener() {
            @Override
            public void onSuccess(String result, String msg) {
                LogUtil.e("我发布的付费线报" + result);
                getView().finishRefresh();
                FuFeiXianBaoBean fuFeiXianBaoBean = JSON.parseObject(result, FuFeiXianBaoBean.class);
                if (1 == page) {
                    fuFeiList.clear();
                }
                fuFeiList.addAll(fuFeiXianBaoBean.getRecords());
                fuFeiXianBaoAdapter = new FuFeiXianBaoAdapter(mContext, fuFeiList, R.layout.item_fufeixianbao_task_rec);
                getView().loadAdapter(fuFeiXianBaoAdapter);
                fuFeiXianBaoAdapter.setViewOnClickListener(new MyRecyclerAdapter.ViewOnClickListener() {
                    @Override
                    public void ViewOnClick(View view, final int index) {
                        view.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                cancelXianBao(fuFeiList.get(index).getId());
                            }
                        });
                    }
                });
            }

            @Override
            public void onError(String errorCode, String errorMsg) {
                LogUtil.e("我发布的付费线报" + errorMsg);
                getView().finishRefresh();
            }
        }));
    }

    private void cancelTask(String id) {
        Map map = MapUtil.getInstance().addParms("id", id).build();
        Observable head = RetrofitUtil.getInstance().getApi(CommonResource.BASEURL_9006).getHead(CommonResource.COMMISSIONTASKLOWERSHELF, map, SPUtil.getToken());
        RetrofitUtil.getInstance().toSubscribe(head, new OnMyCallBack(new OnDataListener() {
            @Override
            public void onSuccess(String result, String msg) {
                LogUtil.e("佣金任务下架" + result);
                yongjin(1);
            }

            @Override
            public void onError(String errorCode, String errorMsg) {
                LogUtil.e("佣金任务下架" + errorMsg);
                Toast.makeText(mContext, "" + errorMsg, Toast.LENGTH_SHORT).show();

            }
        }));
    }

    private void cancelXianBao(String id) {
        Map map = MapUtil.getInstance().addParms("id", id).build();
        Observable head = RetrofitUtil.getInstance().getApi(CommonResource.BASEURL_9006).getHead(CommonResource.NEWSPAPERLOWERSHELF, map, SPUtil.getToken());
        RetrofitUtil.getInstance().toSubscribe(head, new OnMyCallBack(new OnDataListener() {
            @Override
            public void onSuccess(String result, String msg) {
                LogUtil.e("付费线报下架" + result);
                fufei(1);
            }

            @Override
            public void onError(String errorCode, String errorMsg) {
                LogUtil.e("付费线报下架" + errorMsg);
                Toast.makeText(mContext, "" + errorMsg, Toast.LENGTH_SHORT).show();
            }
        }));
    }
}
