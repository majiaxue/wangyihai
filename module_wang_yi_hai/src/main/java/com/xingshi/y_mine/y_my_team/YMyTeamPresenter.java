package com.xingshi.y_mine.y_my_team;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.xingshi.bean.MyTeamList;
import com.xingshi.bean.YMyTeamBean;
import com.xingshi.common.CommonResource;
import com.xingshi.mvp.BasePresenter;
import com.xingshi.net.OnDataListener;
import com.xingshi.net.OnMyCallBack;
import com.xingshi.net.RetrofitUtil;
import com.xingshi.utils.LogUtil;
import com.xingshi.utils.MapUtil;
import com.xingshi.utils.SPUtil;
import com.xingshi.y_main.R;
import com.xingshi.y_mine.y_my_team.adapter.QuKuaiTeamAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.ResponseBody;

public class YMyTeamPresenter extends BasePresenter<YMyTeamView> {

    private List<MyTeamList> dateList=new ArrayList<>();
    private QuKuaiTeamAdapter adapter;

    public YMyTeamPresenter(Context context) {
        super(context);
    }

    @Override
    protected void onViewDestroy() {

    }

    public void initData() {
        Observable<ResponseBody> headWithout = RetrofitUtil.getInstance().getApi(CommonResource.BASEURL_9006).getHeadWithout(CommonResource.MYTEAM, SPUtil.getToken());
        RetrofitUtil.getInstance().toSubscribe(headWithout, new OnMyCallBack(new OnDataListener() {
            @Override
            public void onSuccess(String result, String msg) {
                LogUtil.e("我的团队" + result);
                YMyTeamBean yMyTeamBean = JSON.parseObject(result, YMyTeamBean.class);
                getView().loadData(yMyTeamBean);
            }

            @Override
            public void onError(String errorCode, String errorMsg) {
                LogUtil.e("我的团队errorMsg" + errorMsg);
            }
        }));
    }

    public void getList(final int page){
        Map map = MapUtil.getInstance().addParms("page", page).build();
        Observable head = RetrofitUtil.getInstance().getApi(CommonResource.BASEURL_9006).getHead(CommonResource.GROUPTEAM, map, SPUtil.getToken());
        RetrofitUtil.getInstance().toSubscribe(head,new OnMyCallBack(new OnDataListener() {
            @Override
            public void onSuccess(String result, String msg) {
                LogUtil.e("我的团队列表------"+result);
                List<MyTeamList> myTeamLists = JSON.parseArray(result, MyTeamList.class);
                if (page==1){
                    dateList.clear();
                }
                dateList.addAll(myTeamLists);
                if (adapter==null){
                    adapter=new QuKuaiTeamAdapter(mContext,dateList, R.layout.item_qukuai_team_list);
                    getView().loadAdapter(adapter);
                    getView().refresh();
                }else {
                    adapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onError(String errorCode, String errorMsg) {
                LogUtil.e("我的团队列表error"+errorCode+"----------------"+errorMsg);
            }
        }));
    }
}
