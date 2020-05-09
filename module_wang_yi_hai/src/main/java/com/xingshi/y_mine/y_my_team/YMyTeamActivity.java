package com.xingshi.y_mine.y_my_team;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.scwang.smartrefresh.header.MaterialHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.xingshi.bean.YMyTeamBean;
import com.xingshi.mvp.BaseActivity;
import com.xingshi.utils.DisplayUtil;
import com.xingshi.utils.QRCode;
import com.xingshi.view.CustomHeader;
import com.xingshi.y_main.R;
import com.xingshi.y_main.R2;
import com.facebook.drawee.view.SimpleDraweeView;
import com.xingshi.y_mine.y_my_team.adapter.QuKuaiTeamAdapter;

import butterknife.BindView;

/**
 * 区块我的团队
 */
public class YMyTeamActivity extends BaseActivity<YMyTeamView, YMyTeamPresenter> implements YMyTeamView {

    @BindView(R2.id.y_my_team_back)
    ImageView yMyTeamBack;
    @BindView(R2.id.y_my_team_team_returns)
    TextView yMyTeamTeamReturns;
    @BindView(R2.id.y_my_team_head)
    SimpleDraweeView yMyTeamHead;
    @BindView(R2.id.y_my_team_name)
    TextView yMyTeamName;
    @BindView(R2.id.y_my_team_superior_id)
    TextView yMyTeamSuperiorId;
    @BindView(R2.id.y_my_team_superior_rank)
    TextView yMyTeamSuperiorRank;
    @BindView(R2.id.y_my_team_weChat_qr)
    ImageView yMyTeamWeChatQr;
    @BindView(R2.id.y_my_team_my_id)
    TextView yMyTeamMyId;
    @BindView(R2.id.y_my_team_my_rank)
    TextView yMyTeamMyRank;
    @BindView(R2.id.y_my_team_new_number)
    TextView yMyTeamNewNumber;
    @BindView(R2.id.y_my_team_keep_pushing_number)
    TextView yMyTeamKeepPushingNumber;
    @BindView(R2.id.y_my_team_push_number)
    TextView yMyTeamPushNumber;
    @BindView(R2.id.y_my_team_team_size)
    TextView yMyTeamTeamSize;
    @BindView(R2.id.y_my_team_time)
    TextView yMyTeamtime;
    @BindView(R2.id.rec_team)
    RecyclerView recTeam;
    @BindView(R2.id.smart)
    SmartRefreshLayout smart;
    int page=1;

    @Override
    public int getLayoutId() {
        return R.layout.activity_ymy_team;
    }

    @Override
    public void initData() {
        presenter.initData();
        presenter.getList(page);
        LinearLayoutManager linearLayout=new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recTeam.setLayoutManager(linearLayout);
        //下拉刷新样式
        CustomHeader customHeader = new CustomHeader(this);
        customHeader.setPrimaryColors(getResources().getColor(R.color.colorTransparency));
        smart.setRefreshHeader(customHeader);
        smart.setRefreshFooter(new ClassicsFooter(this));
    }

    @Override
    public void initClick() {
        yMyTeamBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        smart.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page = 1;
                presenter.getList(page);
            }
        });

        smart.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                page++;
                presenter.getList(page);
            }
        });
    }

    @Override
    public YMyTeamView createView() {
        return this;
    }

    @Override
    public YMyTeamPresenter createPresenter() {
        return new YMyTeamPresenter(this);
    }

    @Override
    public void loadData(YMyTeamBean bean) {
        yMyTeamHead.setImageURI(bean.getIcon() == null ? "" : bean.getIcon());
        yMyTeamName.setText("买家: " + bean.getUserName());
        yMyTeamSuperiorId.setText("上级ID: " + bean.getParentId());
        yMyTeamSuperiorRank.setText(bean.getParentMemberLevel());
        yMyTeamWeChatQr.setImageBitmap(QRCode.createQRImage(bean.getWeiXin(), DisplayUtil.dip2px(this, 55), DisplayUtil.dip2px(this, 55)));
        yMyTeamMyId.setText(bean.getUserCode());
        yMyTeamMyRank.setText(bean.getMemberLevel());
        yMyTeamNewNumber.setText(bean.getTodayAddNumber() + "");
        yMyTeamKeepPushingNumber.setText(bean.getDirectPush() + "");
        yMyTeamPushNumber.setText(bean.getPushBetween() + "");
        yMyTeamTeamSize.setText(bean.getFigureAndy() + "");
        yMyTeamtime.setText(bean.getDate());
        yMyTeamTeamReturns.setText(bean.getEstimatedRevenue());
    }

    @Override
    public void refresh() {
        smart.finishRefresh();
        smart.finishLoadMore();
    }

    @Override
    public void loadAdapter(QuKuaiTeamAdapter adapter) {
        recTeam.setAdapter(adapter);
    }
}
