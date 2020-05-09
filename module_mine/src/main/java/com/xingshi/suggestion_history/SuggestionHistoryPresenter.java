package com.xingshi.suggestion_history;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.xingshi.bean.FeedBackHistoryBean;
import com.xingshi.common.CommonResource;
import com.xingshi.module_mine.R;
import com.xingshi.mvp.BasePresenter;
import com.xingshi.net.OnDataListener;
import com.xingshi.net.OnMyCallBack;
import com.xingshi.net.RetrofitUtil;
import com.xingshi.suggestion_history.adapter.SuggestionHistoryAdapter;
import com.xingshi.utils.LogUtil;
import com.xingshi.utils.ProcessDialogUtil;
import com.xingshi.utils.SPUtil;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.ResponseBody;

public class SuggestionHistoryPresenter extends BasePresenter<SuggestionHistoryView> {
    public SuggestionHistoryPresenter(Context context) {
        super(context);
    }

    @Override
    protected void onViewDestroy() {

    }

    public void loadData() {
        ProcessDialogUtil.showProcessDialog(mContext);
//        WaitDialog.show((AppCompatActivity)mContext,null);

        Observable<ResponseBody> observable = RetrofitUtil.getInstance().getApi(CommonResource.BASEURL_4001).getHeadWithout(CommonResource.QUERYSUGGESTION, SPUtil.getToken());
        RetrofitUtil.getInstance().toSubscribe(observable, new OnMyCallBack(new OnDataListener() {
            @Override
            public void onSuccess(String result, String msg) {
                LogUtil.e("反馈历史：" + result);
                List<FeedBackHistoryBean> dataList = JSON.parseArray(result, FeedBackHistoryBean.class);
                SuggestionHistoryAdapter historyAdapter = new SuggestionHistoryAdapter(mContext, dataList, R.layout.rv_suggestion_history);
                if (getView() != null) {
                    getView().loadRv(historyAdapter);
                }
            }

            @Override
            public void onError(String errorCode, String errorMsg) {

            }
        }));
    }
}
