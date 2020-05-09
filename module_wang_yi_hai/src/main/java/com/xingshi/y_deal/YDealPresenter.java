package com.xingshi.y_deal;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.drawable.Drawable;
import android.support.design.widget.TabLayout;
import android.view.View;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.xingshi.adapter.MyRecyclerAdapter;
import com.xingshi.bean.TradingCenterTopBean;
import com.xingshi.bean.YDealBean;
import com.xingshi.common.CommonResource;
import com.xingshi.entity.EventBusBean;
import com.xingshi.mvp.BasePresenter;
import com.xingshi.net.OnDataListener;
import com.xingshi.net.OnMyCallBack;
import com.xingshi.net.RetrofitUtil;
import com.xingshi.utils.LogUtil;
import com.xingshi.utils.MapUtil;
import com.xingshi.utils.SPUtil;
import com.xingshi.view.MyMarkerView;
import com.xingshi.y_deal.adapter.YDealAdapter;
import com.xingshi.y_deal.my_pay_order.MyPayOrderActivity;
import com.xingshi.y_deal.my_sell_order.MySellOrderActivity;
import com.xingshi.y_deal.release_order.ReleaseOrderActivity;
import com.xingshi.y_deal.trading_center.TradingCenterActivity;
import com.xingshi.y_main.R;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IFillFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.dataprovider.LineDataProvider;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.Utils;
import com.kongzue.tabbar.Tab;
import com.kongzue.tabbar.TabBarView;
import com.kongzue.tabbar.interfaces.OnTabChangeListener;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.ResponseBody;

public class YDealPresenter extends BasePresenter<YDealView> {

    private String[] attr = {"买单列表", "卖单列表", "平台回购"};

    //    private chart chart;
    private XAxis xAxis;                //X轴
    private YAxis leftYAxis;            //左侧Y轴
    private YAxis rightYaxis;           //右侧Y轴

    private final List<String> historyBeanList = new ArrayList<>();
    private List<YDealBean.RecordsBean> yDealBeanList = new ArrayList<>();
    private TradingCenterTopBean tradingCenterTopBean;

    public YDealPresenter(Context context) {
        super(context);
    }

    @Override
    protected void onViewDestroy() {

    }

    public void initTop() {
        Observable<ResponseBody> dataWithout = RetrofitUtil.getInstance().getApi(CommonResource.BASEURL_9006).getDataWithout(CommonResource.CURRENCYDISCOUNT);
        RetrofitUtil.getInstance().toSubscribe(dataWithout, new OnMyCallBack(new OnDataListener() {
            @Override
            public void onSuccess(String result, String msg) {
                LogUtil.e("交易中心" + result);
                tradingCenterTopBean = JSON.parseObject(result, TradingCenterTopBean.class);
                for (int i = 0; i < tradingCenterTopBean.getDiscount().size(); i++) {
                    String time = tradingCenterTopBean.getDiscount().get(i).getCreateTime();
                    LogUtil.e("截取的时间" + time.substring(5, time.indexOf(" ")));
                    historyBeanList.add(time.substring(5, time.indexOf(" ")));//2019-12-18 15:52:34
                }
                getView().loadData(tradingCenterTopBean.getTotalPrice(), tradingCenterTopBean.getTotalServiceCharge());
            }

            @Override
            public void onError(String errorCode, String errorMsg) {
                LogUtil.e("交易中心errorMsg" + errorMsg);
            }
        }));
    }

    public void setPalette(TabBarView palette) {
        List<Tab> tabs = new ArrayList<>();
        tabs.add(new Tab(mContext, "发布买卖单", R.drawable.renwu));
        tabs.add(new Tab(mContext, "兑换商品", R.drawable.shangjia));
        tabs.add(new Tab(mContext, "我的买单", R.drawable.fuli));
        tabs.add(new Tab(mContext, "我的卖单", R.drawable.zhangdan));
        palette.setTab(tabs);

        palette.setOnTabChangeListener(new OnTabChangeListener() {
            @Override
            public void onTabChanged(View v, int index) {
                switch (index) {
                    case 0:
                        mContext.startActivity(new Intent(mContext, ReleaseOrderActivity.class));
                        break;
                    case 1:
                        EventBus.getDefault().post(new EventBusBean(CommonResource.TASK));
                        break;
                    case 2:
                        mContext.startActivity(new Intent(mContext, MyPayOrderActivity.class));
                        break;
                    case 3:
                        mContext.startActivity(new Intent(mContext, MySellOrderActivity.class));
                        break;
                    default:
                        break;
                }
            }
        });
    }

    public void initTab(TabLayout yDealTab) {
        for (String title : attr) {
            yDealTab.addTab(yDealTab.newTab().setText(title));
        }

        yDealTab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Intent intent = new Intent(mContext, TradingCenterActivity.class);
                switch (tab.getPosition()) {
                    case 0:
                        intent.putExtra("type", 0);
                        mContext.startActivity(intent);
                        break;
                    case 1:
                        intent.putExtra("type", 1);
                        mContext.startActivity(intent);
                        break;
                    case 2:
                        intent.putExtra("type", 2);
                        mContext.startActivity(intent);
                        break;
                    default:
                        break;
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

    public void setDealRec() {
        Map map = MapUtil.getInstance().addParms("pageNum", 1).addParms("pageSize", 20).build();
        Observable head = RetrofitUtil.getInstance().getApi(CommonResource.BASEURL_9006).getHead(CommonResource.BUYCURRENCYLIST, map, SPUtil.getToken());
        RetrofitUtil.getInstance().toSubscribe(head, new OnMyCallBack(new OnDataListener() {
            @Override
            public void onSuccess(String result, String msg) {
                LogUtil.e("买单列表" + result);
                YDealBean yDealBean = JSON.parseObject(result, new TypeReference<YDealBean>() {
                }.getType());
                if (yDealBean.getRecords().size() != 0) {
                    yDealBeanList.addAll(yDealBean.getRecords());
                    final YDealAdapter yDealAdapter = new YDealAdapter(mContext, yDealBeanList, R.layout.item_y_deal_rec);
                    getView().loadAdapter(yDealAdapter);

                    yDealAdapter.setViewOnClickListener(new MyRecyclerAdapter.ViewOnClickListener() {
                        @Override
                        public void ViewOnClick(View view, final int index) {
                            view.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Map build = MapUtil.getInstance().addParms("id", yDealBeanList.get(index).getId()).addParms("type", 0).build();
                                    Observable head = RetrofitUtil.getInstance().getApi(CommonResource.BASEURL_9006).getHead(CommonResource.TOCURRENCY, build, SPUtil.getToken());
                                    RetrofitUtil.getInstance().toSubscribe(head, new OnMyCallBack(new OnDataListener() {
                                        @Override
                                        public void onSuccess(String result, String msg) {
                                            LogUtil.e("形成交易" + result);
                                            for (int i = 0; i < yDealBeanList.size(); i++) {
                                                yDealBeanList.remove(index);
                                            }
                                            yDealAdapter.notifyDataSetChanged();
                                        }

                                        @Override
                                        public void onError(String errorCode, String errorMsg) {
                                            LogUtil.e("形成交易errorMsg" + errorMsg);
                                            Toast.makeText(mContext, errorMsg, Toast.LENGTH_SHORT).show();
                                        }
                                    }));
                                }
                            });
                        }
                    });
                }
            }

            @Override
            public void onError(String errorCode, String errorMsg) {
                LogUtil.e("买单列表errorMsg" + errorMsg);

            }
        }));
    }

    public void initTuBiao(final LineChart chart) {
//        {
//            // disable description text
//            chart.getDescription().setEnabled(false);
//
//            // enable touch gestures
//            chart.setTouchEnabled(true);
//
//            chart.setDrawGridBackground(false);
//
//            // create marker to display box when values are selected
//            MyMarkerView mv = new MyMarkerView(mContext, R.layout.custom_marker_view);
//
//            // Set the marker to the chart
//            mv.setChartView(chart);
//            chart.setMarker(mv);
//
//            // enable scaling and dragging
//            chart.setDragEnabled(false);
//            chart.setScaleEnabled(false);
//            // chart.setScaleXEnabled(true);
//            // chart.setScaleYEnabled(true);
//
//            // force pinch zoom along both axis
//            chart.setPinchZoom(true);
//// draw points over time
//            chart.animateX(1500);
//
//            // get the legend (only possible after setting data)
//            Legend l = chart.getLegend();
//
//            // draw legend entries as lines
//            l.setForm(Legend.LegendForm.LINE);
//        }
//
//        {   // // X-Axis Style // //
//            xAxis = chart.getXAxis();
//
////             vertical grid lines
//            xAxis.enableGridDashedLine(0f, 0f, 0f);
//        }
//
//        YAxis yAxis;
//        {   // // Y-Axis Style // //
//            yAxis = chart.getAxisLeft();
//
//            // disable dual axis (only use LEFT axis)
//            chart.getAxisRight().setEnabled(true);
//
//            // horizontal grid lines
//            yAxis.enableGridDashedLine(0f, 0f, 0f);
//
//            // axis range
//            yAxis.setAxisMaximum(200f);
//            yAxis.setAxisMinimum(0f);
//        }

        {//初始化图表
//            historyBeanList.add("1");
//            historyBeanList.add("2");
//            historyBeanList.add("3");
//            historyBeanList.add("4");
//            historyBeanList.add("5");
//            historyBeanList.add("6");
//            historyBeanList.add("7");
            //总体设置
            chart.setDrawGridBackground(false);
            chart.setBackgroundColor(Color.WHITE);
            chart.setDrawBorders(false);
            chart.setDragEnabled(false);
            chart.setTouchEnabled(true);

//        chart.animateY(2500);
//        chart.animateX(1500);
            chart.animateXY(500, 500);
            chart.setDragDecelerationEnabled(true);
            //设置是否可以拖拽
            chart.setDragEnabled(false);
            //设置是否可以缩放            setOnChartValueSelectedListener
            chart.setScaleEnabled(false);
            chart.setDrawGridBackground(false);
            chart.setHighlightPerDragEnabled(true);
            chart.setPinchZoom(true);
//        //X轴描述
//        Description description = new Description();
//        description.setText(mContext.getResources().getString(R.string.time));
//        description.setTextColor(mContext.getResources().getColor(R.color.green_02BE7E));
            chart.setDescription(null);
            // create marker to display box when values are selected
            MyMarkerView mv = new MyMarkerView(mContext, R.layout.custom_marker_view);

            // Set the marker to the chart
            mv.setChartView(chart);
            chart.setMarker(mv);

            //X轴设置
            xAxis = chart.getXAxis();
            xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
            xAxis.setAxisMinimum(0f);
            xAxis.setGranularity(1f);
            xAxis.setDrawGridLines(false);
            xAxis.setDrawLabels(true);
            xAxis.setLabelRotationAngle(0);
            xAxis.setValueFormatter(new ValueFormatter() {
                @Override
                public String getFormattedValue(float value) {
                    LogUtil.e("format:" + value + ",size:" + value % historyBeanList.size());
                    return historyBeanList.get((int) value);
                }
            });

            xAxis.setAxisMaximum(historyBeanList.size() - 1);
            chart.setVisibleXRange(0, historyBeanList.size() - 1);

            leftYAxis = chart.getAxisLeft();
            leftYAxis.setDrawGridLines(false);

            leftYAxis.setAxisMinimum(0f);
            leftYAxis.enableGridDashedLine(10f, 10f, 0f);
            //右边Y轴设置
            rightYaxis = chart.getAxisRight();
            rightYaxis.setAxisMinimum(0f);
            rightYaxis.setDrawGridLines(false);
            rightYaxis.setEnabled(false);//显示设置
        }

        ArrayList<Entry> values = new ArrayList<>();

        for (int i = 0; i < historyBeanList.size(); i++) {

//            float val = (float) (Math.random() * 180);
            values.add(new Entry(i, Float.valueOf(tradingCenterTopBean.getDiscount().get(i).getPrice() + ""), mContext.getResources().getDrawable(R.drawable.yuandian)));
        }

        LineDataSet set1;

        if (chart.getData() != null &&
                chart.getData().getDataSetCount() > 0) {
            set1 = (LineDataSet) chart.getData().getDataSetByIndex(0);
            set1.setValues(values);
            set1.notifyDataSetChanged();
            chart.getData().notifyDataChanged();
            chart.notifyDataSetChanged();
        } else {
            // create a dataset and give it a type
            set1 = new LineDataSet(values, tradingCenterTopBean.getDiscount().get(0).getCreateTime().substring(0, tradingCenterTopBean.getDiscount().get(0).getCreateTime().indexOf("-")) + "年");
            set1.setDrawIcons(true);
            // 画虚线
//            set1.enableDashedLine(10f, 5f, 0f);
            // 黑色线条和点
            set1.setColor(Color.parseColor("#FF0977FF"));
            set1.setCircleColor(Color.parseColor("#FF1F46ED"));
            // 线厚度和大小
            set1.setLineWidth(1f);
            set1.setCircleRadius(3f);
            // 画点固体圆
            set1.setDrawCircleHole(true);
            // 定制条目
            set1.setFormLineWidth(1f);
            set1.setFormLineDashEffect(new DashPathEffect(new float[]{10f, 5f}, 0f));
            set1.setFormSize(15.f);

            // 文本值的大小
            set1.setValueTextSize(9f);

            // draw selection line as dashed
//            set1.enableDashedHighlightLine(10f, 5f, 0f);

            set1.setMode(LineDataSet.Mode.CUBIC_BEZIER);

            // 设置折线图填充
            set1.setDrawFilled(true);
            set1.setFillFormatter(new IFillFormatter() {
                @Override
                public float getFillLinePosition(ILineDataSet dataSet, LineDataProvider dataProvider) {
                    return chart.getAxisLeft().getAxisMinimum();
                }
            });

            // 设置的颜色填充区域
            if (Utils.getSDKInt() >= 18) {
                // 画板上只支持api 18岁及以上水平
                Drawable drawable = mContext.getResources().getDrawable(R.drawable.fade_red);
                set1.setFillDrawable(drawable);
            } else {
                set1.setFillColor(Color.BLACK);
            }

            ArrayList<ILineDataSet> dataSets = new ArrayList<>();
            dataSets.add(set1);
            LineData data = new LineData(dataSets);

            // set data
            chart.setData(data);
        }
    }

}
