package com.xingshi.y_mine.promotion_code;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.xingshi.utils.DisplayUtil;
import com.xingshi.utils.QRCode;
import com.xingshi.utils.StatusBarUtils;
import com.xingshi.y_main.R;

/**
 * 推广码
 */
public class PromotionCodeActivity extends AppCompatActivity {
    private ImageView promotionCodeBack;
    private ImageView promotionCodeQr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 设置状态栏
        StatusBarUtils.transparencyBar(this);
        StatusBarUtils.setAndroidNativeLightStatusBar(this, false);
        setContentView(R.layout.activity_promotion_code);

        promotionCodeBack = (ImageView) findViewById(R.id.promotion_code_back);
        promotionCodeQr = (ImageView) findViewById(R.id.promotion_code_qr);

        Bitmap qrImage = QRCode.createQRImage("暂无推广码", DisplayUtil.dip2px(this, 131), DisplayUtil.dip2px(this, 131));
        promotionCodeQr.setImageBitmap(qrImage);

        promotionCodeBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
