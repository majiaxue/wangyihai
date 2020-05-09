package com.xingshi.mineorder.stayappraise;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.xingshi.adapter.MyRecyclerAdapter;
import com.xingshi.common.CommonResource;
import com.xingshi.bean.MineOrderBean;
import com.xingshi.mineorder.stayappraise.adapter.StayAppraiseParentAdapter;
import com.xingshi.module_user_mine.R;
import com.xingshi.mvp.BasePresenter;
import com.xingshi.net.OnDataListener;
import com.xingshi.net.OnMyCallBack;
import com.xingshi.net.RetrofitUtil;
import com.xingshi.utils.LogUtil;
import com.xingshi.utils.MapUtil;
import com.xingshi.utils.ProcessDialogUtil;
import com.xingshi.utils.SPUtil;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.ResponseBody;

/**
 * Created by cuihaohao on 2019/5/27
 * Describe:待评价
 */
public class StayAppraisePresenter extends BasePresenter<StayAppraiseView> {

    private List<MineOrderBean.OrderListBean> listBeans = new ArrayList<>();
    private StayAppraiseParentAdapter stayAppraiseParentAdapter;

    public StayAppraisePresenter(Context context) {
        super(context);
    }

    @Override
    protected void onViewDestroy() {

    }

    public void stayAppraiseRec() {
        ProcessDialogUtil.showProcessDialog(mContext);
//        WaitDialog.show((AppCompatActivity)mContext,null);

        Map map = MapUtil.getInstance().addParms("status", 3).build();
        Observable<ResponseBody> headWithout = RetrofitUtil.getInstance().getApi(CommonResource.BASEURL_4001).getHead(CommonResource.ORDERSTATUS, map, SPUtil.getToken());
        RetrofitUtil.getInstance().toSubscribe(headWithout, new OnMyCallBack(new OnDataListener() {
            @Override
            public void onSuccess(String result, String msg) {
//                ProcessDialogUtil.dismissDialog();
                LogUtil.e("stayAppraiseRec" + result);
//                MineOrderBean mineOrderBean = JSON.parseObject(result, new TypeReference<MineOrderBean>() {
//                }.getType());
//                LogUtil.e("MineOrderBean" + mineOrderBean.getOrderList());
                MineOrderBean mineOrderBean = new Gson().fromJson(result, MineOrderBean.class);
                if (mineOrderBean != null) {
                    listBeans.clear();
                    listBeans.addAll(mineOrderBean.getOrderList());
                    if (stayAppraiseParentAdapter == null){
                        stayAppraiseParentAdapter = new StayAppraiseParentAdapter(mContext, listBeans, R.layout.item_stay_appraise_parent);

                    }else {
                        stayAppraiseParentAdapter.notifyDataSetChanged();
                    }
                    if (getView()!=null){
                        getView().load(stayAppraiseParentAdapter);
                    }

                    stayAppraiseParentAdapter.setViewOnClickListener(new MyRecyclerAdapter.ViewOnClickListener() {
                        @Override
                        public void ViewOnClick(View view, int index) {

                        }
                    });

                    stayAppraiseParentAdapter.setOnItemClick(new MyRecyclerAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(RecyclerView parent, View view, int position) {

                        }
                    });
                }
            }

            @Override
            public void onError(String errorCode, String errorMsg) {
//                ProcessDialogUtil.dismissDialog();
                LogUtil.e("stayAppraiseErrorMsg------->" + errorMsg);
            }
        }));
    }
}
