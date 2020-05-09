package com.xingshi.main;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.bumptech.glide.Glide;
import com.xingshi.entity.TopBannerBean;
import com.xingshi.module_home.R;
import com.xingshi.utils.StatusBarUtils;
import com.stx.xhb.xbanner.XBanner;
import com.stx.xhb.xbanner.transformers.Transformer;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

public class GuideActivity extends Activity {

    private TextView guideYhxy;
    private TextView guideYsxy;
    private XBanner guideBanner;
    private TextView guideSkip;
    private List<TopBannerBean> images = new ArrayList<>();
//    private CountDownTimer start;

    private final String[] perms = {Manifest.permission.READ_PHONE_STATE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA,
            Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};
    private final int REQUEST_CODE = 0xa321;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        ButterKnife.bind(this);
        changeStatus();
        initView();

        initData();

        initPermission();

    }

    private void changeStatus() {
        // 设置状态栏
        StatusBarUtils.transparencyBar(this);
        StatusBarUtils.setStatusTheme(this, true, true);
    }

    private void initView() {
        guideBanner = findViewById(R.id.guide_banner);
        guideSkip = findViewById(R.id.guide_skip);
        guideYhxy = findViewById(R.id.guide_yhxy);
        guideYsxy = findViewById(R.id.guide_ysxy);
    }


    private void initData() {

//        start = new CountDownTimer(5000, 1000) {
//            @Override
//            public void onTick(long millisUntilFinished) {
//                guideSkip.setText("跳转(" + Math.round((double) millisUntilFinished / 1000) + "s)");
//            }
//
//            @Override
//            public void onFinish() {
//
//            }
//        }.start();

        images.add(new TopBannerBean(R.drawable.frist111));
        images.add(new TopBannerBean(R.drawable.jgfhfhdfhdf));
        images.add(new TopBannerBean(R.drawable.hfcgxhdhd));

//        guideBanner.setData(images,null);
        guideBanner.setBannerData(images);
        guideBanner.loadImage(new XBanner.XBannerAdapter() {
            @Override
            public void loadBanner(XBanner banner, Object model, View view, int position) {

                //1、此处使用的Glide加载图片，可自行替换自己项目中的图片加载框架
                //2、返回的图片路径为Object类型，你只需要强转成你传输的类型就行，切记不要胡乱强转！
                Glide.with(GuideActivity.this).load(images.get(position).getXBannerUrl()).into((ImageView) view);
            }
        });
        // 设置XBanner的页面切换特效
        guideBanner.setPageTransformer(Transformer.Default);
        // 设置XBanner页面切换的时间，即动画时长
        guideBanner.setPageChangeDuration(1000);

        guideBanner.setOnItemClickListener(new XBanner.OnItemClickListener() {
            @Override
            public void onItemClick(XBanner banner, Object model, View view, int position) {
                if (position == 2) {
                    guideBanner.stopAutoPlay();
                    startActivity(new Intent(GuideActivity.this, MainActivity.class));
                    finish();
                }
            }

        });

        guideSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guideBanner.stopAutoPlay();
                startActivity(new Intent(GuideActivity.this, MainActivity.class));
                finish();
            }
        });

        guideYhxy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ARouter.getInstance().build("/mine/agreement").withString("type", "zcxy").navigation();
            }
        });

        guideYsxy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ARouter.getInstance().build("/mine/agreement").withString("type", "ysxy").navigation();
            }
        });

    }

    private void initPermission() {
        for (String perm : perms) {
            if (ContextCompat.checkSelfPermission(this, perm) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, perms, REQUEST_CODE);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == REQUEST_CODE) {
            for (int result : grantResults) {
                if (result == PackageManager.PERMISSION_GRANTED) {

                } else {
                    finish();
                }
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    /**
     * 为了更好的体验效果建议在下面两个生命周期中调用下面的方法
     **/
    @Override
    protected void onResume() {
        super.onResume();
        guideBanner.startAutoPlay();
    }

    @Override
    protected void onStop() {
        super.onStop();
        guideBanner.stopAutoPlay();
    }

}
