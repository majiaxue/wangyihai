package com.xingshi.y_mine.auditing.xiangqing;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.xingshi.bean.CommissionTaskDetailsBean;
import com.xingshi.bean.TaskListDetailsBean;
import com.xingshi.common.CommonResource;
import com.xingshi.mvp.BaseActivity;
import com.xingshi.utils.LogUtil;
import com.xingshi.utils.SPUtil;
import com.xingshi.y_main.R;
import com.xingshi.y_main.R2;
import com.xingshi.y_mine.auditing.adapter.ImgAdapter;
import com.xingshi.y_mine.y_welfare_center.commission_task_details.adapter.CommissionTaskDetailsImageAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

//审核详情界面
@Route(path = "/model_wangyihai/AuditingDetialActivity")
public class AuditingDetialActivity extends BaseActivity<AuditingDetialView, AuditingDetialPresenter> implements AuditingDetialView {

    @BindView(R2.id.commission_task_details_back)
    ImageView includeBack;
    @BindView(R2.id.tv_fabufang)
    TextView tvFabufang;
    @BindView(R2.id.tv_bianhao)
    TextView tvBianhao;
    @BindView(R2.id.tv_huiyuanname)
    TextView tvHuiyuanname;
    @BindView(R2.id.tv_zhanghao)
    TextView tvZhanghao;
    @BindView(R2.id.tv_shengfen)
    TextView tvShengfen;
    @BindView(R2.id.tv_renwuleixin)
    TextView tvRenwuleixin;
    @BindView(R2.id.tv_renwubiaoti)
    TextView tvRenwubiaoti;
    @BindView(R2.id.tv_jiezhishijian)
    TextView tvJiezhishijian;
    @BindView(R2.id.tv_pujiage)
    TextView tvPujiage;
    @BindView(R2.id.tv_gujiage)
    TextView tvGujiage;
    @BindView(R2.id.tv_lianjie)
    TextView tvLianjie;
    @BindView(R2.id.tv_caozuoshuoming)
    TextView tvCaozuoshuoming;
    @BindView(R2.id.tv_wenziyanzheng)
    TextView tvWenziyanzheng;
    @BindView(R2.id.tv_beizhu)
    TextView tvBeizhu;
    @BindView(R2.id.tv_tijiaoshijian)
    TextView tvTijiaoshijian;
    @BindView(R2.id.tv_shenghezhuangtai)
    TextView tvShenghezhuangtai;
    @BindView(R2.id.rec_img)
    RecyclerView rec_img;
    @BindView(R2.id.tv_ok)
    Button tvOk;
    @BindView(R2.id.tv_ref)
    Button tvRef;
    @Autowired(name = "id")
    String id;
    @Autowired(name = "status")
    int status;
    @Override
    public int getLayoutId() {
        return R.layout.activity_auditing_detial;
    }

    @Override
    public void initData() {
        ARouter.getInstance().inject(this);
        LogUtil.e("id---------"+id);
        presenter.initTaskListDetailData(id);
        if (status==0){
            tvOk.setVisibility(View.VISIBLE);
            tvRef.setVisibility(View.VISIBLE);
        }else {
            tvOk.setVisibility(View.GONE);
            tvRef.setVisibility(View.GONE);
        }
    }

    @Override
    public void initClick() {
        includeBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        //通过
        tvOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.popupwindow(id,1);
            }
        });

        //拒绝
        tvRef.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.popupwindow(id,2);
            }
        });
    }

    @Override
    public AuditingDetialView createView() {
        return this;
    }

    @Override
    public AuditingDetialPresenter createPresenter() {
        return new AuditingDetialPresenter(this);
    }

    @Override
    public void loadData(TaskListDetailsBean bean) {
        tvFabufang.setText(bean.getCommissionExamine().getReleaseUserName());
        tvBianhao.setText(bean.getCommissionExamine().getNumber());
        tvHuiyuanname.setText(bean.getCommissionExamine().getUserName());
        tvZhanghao.setText(bean.getCommissionExamine().getPhone());
        String gd = SPUtil.getStringValue(CommonResource.GD);
        LogUtil.e("股东-------"+gd);
        if (gd.equals("0")){
            tvShengfen.setText("普通会员");
        }else {
            tvShengfen.setText("股东");
        }
        tvRenwuleixin.setText(bean.getCommissionExamine().getTaskType());
        tvRenwubiaoti.setText(bean.getCommissionExamine().getTaskName());
        tvJiezhishijian.setText(bean.getCommissionExamine().getEndTime());
        tvPujiage.setText(bean.getCommissionExamine().getOrdinaryPrice()+"");
        tvGujiage.setText(bean.getCommissionExamine().getShareholderPrice()+"");
        tvLianjie.setText(bean.getCommissionExamine().getUrl());
        tvCaozuoshuoming.setText(bean.getCommissionExamine().getExplains());
        tvWenziyanzheng.setText(bean.getCommissionExamine().getVerification());
        tvBeizhu.setText(bean.getCommissionExamine().getBz());
        //tvTijiaoshijian.setText(bean.getCommissionExamine().);

        //审核状态  0--》待审核  1-----》通过  2--》拒绝   3========》超时取消
        int status = bean.getCommissionExamine().getStatus();
        if (status==0){
            tvShenghezhuangtai.setText("待审核");
        }else if (status==1){
            tvShenghezhuangtai.setText("通过");
        }else if (status==2){
            tvShenghezhuangtai.setText("拒绝");
        }else if (status==3){
            tvShenghezhuangtai.setText("超时取消");
        }
        //图片
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rec_img.setLayoutManager(linearLayoutManager);
        ImgAdapter adapter = new ImgAdapter(this, bean.getImg(), R.layout.item_detial_img);
        rec_img.setAdapter(adapter);

    }
}
