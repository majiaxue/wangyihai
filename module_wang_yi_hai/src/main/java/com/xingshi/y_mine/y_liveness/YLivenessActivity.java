package com.xingshi.y_mine.y_liveness;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.xingshi.utils.StatusBarUtils;
import com.xingshi.y_main.R;

/**
 * 区块活跃度
 */
public class YLivenessActivity extends AppCompatActivity {
    private ImageView yLivenessBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 设置状态栏
        StatusBarUtils.transparencyBar(this);
        StatusBarUtils.setAndroidNativeLightStatusBar(this, false);
        setContentView(R.layout.activity_yliveness);

        yLivenessBack = findViewById(R.id.y_liveness_back);

        yLivenessBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
