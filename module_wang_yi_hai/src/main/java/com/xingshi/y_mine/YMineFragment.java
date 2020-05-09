package com.xingshi.y_mine;

import android.content.Intent;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.xingshi.bean.YMineBean;
import com.xingshi.mvp.BaseFragment;
import com.xingshi.utils.SPUtil;
import com.xingshi.y_main.R;
import com.xingshi.y_main.R2;
import com.xingshi.y_mine.y_commission.YCommissionActivity;
import com.xingshi.y_mine.y_contribution_value.YContributionValueActivity;
import com.xingshi.y_mine.y_currency_balance.YCurrencyBalanceActivity;
import com.xingshi.y_mine.y_personal_details.YPersonalDetailsActivity;
import com.xingshi.y_mine.y_setting.YSettingActivity;
import com.facebook.drawee.view.SimpleDraweeView;
import com.kongzue.tabbar.TabBarView;

import butterknife.BindView;

/**
 * 区块我的
 */
public class YMineFragment extends BaseFragment<YMineView, YMinePresenter> implements YMineView {

    @BindView(R2.id.y_mine_head_portrait)
    SimpleDraweeView yMineHeadPortrait;
    @BindView(R2.id.y_mine_setting)
    ImageView yMineSetting;
    @BindView(R2.id.y_mine_nick_name)
    TextView yMineNickName;
    @BindView(R2.id.y_mine_level)
    TextView yMineLevel;
    @BindView(R2.id.y_mine_id)
    TextView yMineId;
    @BindView(R2.id.y_mine_bi)
    TextView yMineBi;
    @BindView(R2.id.y_mine_gong_xian_zhi)
    TextView yMineGongXianZhi;
    @BindView(R2.id.y_mine_yong_jin)
    TextView yMineYongJin;
    @BindView(R2.id.y_mine_yue)
    TextView yMineYue;
    @BindView(R2.id.y_mine_income_amount)
    TextView yMineIncomeAmount;
    @BindView(R2.id.y_mine_my_application)
    TabBarView yMineMyApplication;
    @BindView(R2.id.y_mine_platinum_gift_bag)
    TextView yMinePlatinumGiftBag;
    @BindView(R2.id.y_mine_platinum_view)
    CardView yMinePlatinumView;
    @BindView(R2.id.y_mine_red_packet)
    TextView yMineRedPacket;
    @BindView(R2.id.y_mine_red_packet_view)
    CardView yMineRedPacketView;
    @BindView(R2.id.y_mine_service_aid1)
    TabBarView yMineServiceAid1;
    @BindView(R2.id.y_mine_service_aid2)
    TabBarView yMineServiceAid2;
    @BindView(R2.id.y_mine_bi_linear)
    LinearLayout yMineBiLinear;
    @BindView(R2.id.y_mine_gong_xian_zhi_linear)
    LinearLayout yMineGongXianZhiLinear;
    @BindView(R2.id.y_mine_yong_jin_linear)
    LinearLayout yMineYongJinLinear;
    @BindView(R2.id.y_mine_yue_linear)
    LinearLayout yMineYueLinear;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_ymine;
    }

    @Override
    public void initData() {
        presenter.initData();
        //我的应用
        presenter.setMyApplication(yMineMyApplication);
        //服务工具
        presenter.setServiceAid(yMineServiceAid1, yMineServiceAid2);
    }

    @Override
    public void initClick() {
        yMineHeadPortrait.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(SPUtil.getToken())) {
                    startActivity(new Intent(getContext(), YPersonalDetailsActivity.class));
                } else {
                    Toast.makeText(getContext(), "请先登录", Toast.LENGTH_SHORT).show();
                }
            }
        });

        yMineSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(SPUtil.getToken())) {
                    startActivity(new Intent(getContext(), YSettingActivity.class));
                } else {
                    Toast.makeText(getContext(), "请先登录", Toast.LENGTH_SHORT).show();
                }
            }
        });
        //币
        yMineBiLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), YCurrencyBalanceActivity.class));
            }
        });
        //贡献值
        yMineGongXianZhiLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), YContributionValueActivity.class));
            }
        });
        //佣金
        yMineYongJinLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), YCommissionActivity.class));
            }
        });
//        //余额
//        yMineYueLinear.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(getContext(), YBalanceAccountActivity.class));
//            }
//        });
    }

    @Override
    public YMineView createView() {
        return this;
    }

    @Override
    public YMinePresenter createPresenter() {
        return new YMinePresenter(getContext());
    }

    @Override
    public void initData(YMineBean yMineBean) {
        yMineHeadPortrait.setImageURI(yMineBean.getIcon());
        yMineNickName.setText(yMineBean.getUserName().replace("\n",""));
        yMineId.setText("ID:" + yMineBean.getUserCode());
        yMineLevel.setText(yMineBean.getMemberLevelName());
        yMineBi.setText(yMineBean.getCurrencyBalance());
        yMineGongXianZhi.setText(yMineBean.getContribution());
        yMineYongJin.setText(yMineBean.getBlance());
        yMineYue.setText(yMineBean.getTotalBackMoney());
        yMineIncomeAmount.setText(yMineBean.getBusinessBalance());
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden) {
            //不可见
        } else {
            presenter.initData();
        }
    }
}
