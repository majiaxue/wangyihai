package com.xingshi.order_info;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.xingshi.bean.LocalOrderBean;
import com.xingshi.bean.LocalTuiKuanBean;
import com.xingshi.module_local.R;
import com.xingshi.module_local.R2;
import com.xingshi.mvp.BaseActivity;
import com.xingshi.order_info.adapter.OrderInfoAdapter;
import com.xingshi.utils.SpaceItemDecoration;

import butterknife.BindView;

@Route(path = "/module_local/OrderInfoActivity")
public class OrderInfoActivity extends BaseActivity<OrderInfoView, OrderInfoPresenter> implements OrderInfoView {
    @BindView(R2.id.include_back)
    ImageView includeBack;
    @BindView(R2.id.include_title)
    TextView includeTitle;
    @BindView(R2.id.order_info_shop_name)
    TextView orderInfoShopName;
    @BindView(R2.id.order_info_rv)
    RecyclerView orderInfoRv;
    @BindView(R2.id.order_info_peisong)
    TextView orderInfoPeisong;
    @BindView(R2.id.order_info_peisong_money)
    TextView orderInfoPeisongMoney;
    @BindView(R2.id.order_info_manjian_money)
    TextView orderInfoManjianMoney;
    @BindView(R2.id.order_info_total_money)
    TextView orderInfoTotalMoney;
    @BindView(R2.id.order_info_address)
    TextView orderInfoAddress;
    @BindView(R2.id.order_info_ordersn)
    TextView orderInfoOrdersn;
    @BindView(R2.id.order_info_time)
    TextView orderInfoTime;
    @BindView(R2.id.order_info_pay_type)
    TextView orderInfoPayType;
    @BindView(R2.id.order_info_coupon_money_txt)
    TextView mRedPackage;
    @BindView(R2.id.order_info_type)
    TextView mType;
    @BindView(R2.id.order_info_linear)
    LinearLayout mLinear;

    @Autowired(name = "bean")
    LocalOrderBean bean;
    @Autowired(name = "tuikuan")
    LocalTuiKuanBean.RecordsBean tuiKuanBean;

    @Override
    public int getLayoutId() {
        return R.layout.activity_order_info;
    }

    @Override
    public void initData() {
        ARouter.getInstance().inject(this);
        includeTitle.setText("订单详情");

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        orderInfoRv.setLayoutManager(layoutManager);
        orderInfoRv.addItemDecoration(new SpaceItemDecoration(0, 0, 0, (int) getResources().getDimension(R.dimen.dp_14)));

        if (tuiKuanBean == null) {
            orderInfoShopName.setText(bean.getSellerInfo().getShop_name());
            orderInfoManjianMoney.setText("-￥" + (bean.getFullReductionAmount() == null ? "0" : bean.getFullReductionAmount()));
            orderInfoTotalMoney.setText("￥" + bean.getTotalMoney());
            orderInfoOrdersn.setText(bean.getOrderSn());
            orderInfoTime.setText(bean.getCreateTime());
            orderInfoPayType.setText("0".equals(bean.getPayWay()) ? "微信" : "支付宝");
            mRedPackage.setText("-￥" + bean.getRedPackedMoney());

            if ("0".equals(bean.getDeliverType())) {
                orderInfoPeisong.setText("上门自提");
                mType.setText("取货码");
                orderInfoAddress.setText(bean.getTakeGoodsCode());
            } else {
                orderInfoPeisong.setText("商家配送");
                orderInfoAddress.setText(bean.getUserAddress() + "\n" + bean.getUserName() + "      " + bean.getUserPhone());
            }

            presenter.loadData(bean.getLocalOrderItemList());
        } else {
            orderInfoManjianMoney.setText("-￥" + (tuiKuanBean.getFullReductionAmount() == null ? "0" : tuiKuanBean.getFullReductionAmount()));
            orderInfoTotalMoney.setText("￥" + tuiKuanBean.getReturnAmount());
            orderInfoAddress.setText(tuiKuanBean.getUserAddress() + "\n" + tuiKuanBean.getReturnName() + "      " + tuiKuanBean.getReturnPhone());
            orderInfoOrdersn.setText(tuiKuanBean.getOrderSn());
            orderInfoTime.setText(tuiKuanBean.getCreateTime());
            if (tuiKuanBean.getRedPackedMoney() == null || "null".equals(tuiKuanBean.getRedPackedMoney())) {
                mRedPackage.setText("-￥0");
            } else {
                mRedPackage.setText(tuiKuanBean.getRedPackedMoney());
            }
            orderInfoPayType.setText("0".equals(tuiKuanBean.getPayWay()) ? "微信" : "支付宝");
            if (tuiKuanBean.getSeller() != null) {
                orderInfoShopName.setText(tuiKuanBean.getSeller().getSellerShopName());
            }
            if ("0".equals(tuiKuanBean.getDeliverType())) {
                orderInfoPeisong.setText("上门自提");
            } else {
                orderInfoPeisong.setText("商家配送");
            }
            mLinear.setVisibility(View.GONE);

            presenter.loadData(tuiKuanBean.getLocalOrderItemList());
        }
    }

    @Override
    public void initClick() {
        includeBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void loadRv(OrderInfoAdapter adapter) {
        orderInfoRv.setAdapter(adapter);
    }

    @Override
    public OrderInfoView createView() {
        return this;
    }

    @Override
    public OrderInfoPresenter createPresenter() {
        return new OrderInfoPresenter(this);
    }
}
