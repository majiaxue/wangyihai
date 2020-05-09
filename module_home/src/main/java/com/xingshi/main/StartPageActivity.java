package com.xingshi.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.xingshi.module_home.R;
import com.xingshi.utils.StatusBarUtils;

/**
 * 启动页
 */
public class StartPageActivity extends Activity {
    private int i = 1;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0) {
                startActivity(new Intent(StartPageActivity.this, MainActivity.class));
                finish();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_page);
        changeStatus();
        new Thread() {
            @Override
            public void run() {
                while (i > 0) {
                    try {
                        i--;
                        sleep(1000);
                        handler.sendEmptyMessage(i);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }

    private void changeStatus() {
        // 设置状态栏
        StatusBarUtils.transparencyBar(this);
        StatusBarUtils.setStatusTheme(this, true, true);
    }
}
