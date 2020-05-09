package com.xingshi.y_mine.auditing;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xingshi.common.CommonResource;
import com.xingshi.mvp.BasePresenter;
import com.xingshi.net.OnDataListener;
import com.xingshi.net.OnMyCallBack;
import com.xingshi.net.RetrofitUtil;
import com.xingshi.utils.LogUtil;
import com.xingshi.utils.MapUtil;
import com.xingshi.utils.ProcessDialogUtil;
import com.xingshi.utils.SPUtil;

import java.lang.reflect.Field;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.ResponseBody;

public class AuditingPresenter extends BasePresenter<AuditingView> {

    private String[] titleArr = {"待审核", "已成功", "已拒绝"};

    public AuditingPresenter(Context context) {
        super(context);
    }

    @Override
    protected void onViewDestroy() {}


    public void getTab(TabLayout tabLayout){

        for (String title : titleArr) {
            tabLayout.addTab(tabLayout.newTab().setText(title));
        }
        initIndicator(tabLayout);

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                //ProcessDialogUtil.showProcessDialog(mContext);
                if (tab.getPosition() == 0) {
                    loadData("0");
                }  else if (tab.getPosition() == 1) {
                    loadData("1");
                }else if (tab.getPosition()==2){
                    loadData("2");
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
    //佣金审核任务列表
    public  void loadData(String status){
        Map map = MapUtil.getInstance().addParms("status",status).build();
        Observable<ResponseBody> headWithout = RetrofitUtil.getInstance().getApi(CommonResource.BASEURL_9006).getHead(CommonResource.YONGJINSHENGHE,map, SPUtil.getToken());
        LogUtil.e("这是token-----------"+SPUtil.getToken());
        RetrofitUtil.getInstance().toSubscribe(headWithout,new OnMyCallBack(new OnDataListener() {
            @Override
            public void onSuccess(String result, String msg) {
                LogUtil.e("审核列表----"+result);
            }

            @Override
            public void onError(String errorCode, String errorMsg) {
                LogUtil.e("审核列表错误信息-----"+errorCode+"--------------"+errorMsg);
            }
        }));
    }

    public void getDetialData(){
        Map id = MapUtil.getInstance().addParms("id", "45").build();
        Observable head = RetrofitUtil.getInstance().getApi(CommonResource.BASEURL_9006).getHead(CommonResource.SHENGHEXIANGQING, id, SPUtil.getToken());
        RetrofitUtil.getInstance().toSubscribe(head,new OnMyCallBack(new OnDataListener() {
            @Override
            public void onSuccess(String result, String msg) {
                LogUtil.e("佣金审核详情--------"+result);
            }

            @Override
            public void onError(String errorCode, String errorMsg) {
                LogUtil.e("审核列表详情错误信息-----"+errorCode+"--------------"+errorMsg);
            }
        }));
    }

    private void initIndicator(final TabLayout managerOrderDetailsTab) {
        managerOrderDetailsTab.post(new Runnable() {
            @Override
            public void run() {
                try {
                    //了解源码得知 线的宽度是根据 tabView的宽度来设置的
                    LinearLayout mTabStrip = (LinearLayout) managerOrderDetailsTab.getChildAt(0);

                    for (int i = 0; i < mTabStrip.getChildCount(); i++) {
                        View tabView = mTabStrip.getChildAt(i);

                        //拿到tabView的mTextView属性  tab的字数不固定一定用反射取mTextView
                        Field mTextViewField =
                                tabView.getClass().getDeclaredField("mTextView");
                        mTextViewField.setAccessible(true);

                        TextView mTextView = (TextView) mTextViewField.get(tabView);

                        tabView.setPadding(0, 0, 0, 0);

                        //因为我想要的效果是   字多宽线就多宽，所以测量mTextView的宽度
                        int width = 0;
                        width = mTextView.getWidth();
                        if (width == 0) {
                            mTextView.measure(0, 0);
                            width = mTextView.getMeasuredWidth();
                        }

                        //设置tab左右间距为10dp  注意这里不能使用Padding
                        // 因为源码中线的宽度是根据 tabView的宽度来设置的
                        LinearLayout.LayoutParams params =
                                (LinearLayout.LayoutParams) tabView.getLayoutParams();
                        params.width = width;
                        tabView.setLayoutParams(params);

                        tabView.invalidate();
                    }

                } catch (Exception e) {

                }
            }
        });
    }

    public void getPanDuan(){
        Map build = MapUtil.getInstance().addParms("status ", "1").addParms("id","45").build();
        Observable head = RetrofitUtil.getInstance().getApi(CommonResource.BASEURL_9006).getHead(CommonResource.TONGYI, build, SPUtil.getToken());
        RetrofitUtil.getInstance().toSubscribe(head,new OnMyCallBack(new OnDataListener() {
            @Override
            public void onSuccess(String result, String msg) {
                LogUtil.e("同意或拒绝--------"+result);
            }

            @Override
            public void onError(String errorCode, String errorMsg) {
                LogUtil.e("同意或拒绝错误信息-----------"+errorCode+"----------"+errorMsg);
            }
        }));
    }
}
