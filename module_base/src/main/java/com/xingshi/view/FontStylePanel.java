package com.xingshi.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.LinearLayout;

import com.xingshi.module_base.R;
import com.xingshi.module_base.R2;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FontStylePanel extends LinearLayout {
    private Context mContext;
    private OnFontPanelListener onFontPanelListener;

    @BindView(R2.id.btn_img)
    Button btn_img;

    private FontStyle fontStyle;
    public FontStylePanel(Context context) {
        super(context);
        initView(context);
    }

    public FontStylePanel(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public FontStylePanel(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context){
        this.mContext = context;
        LayoutInflater.from(context).inflate(R.layout.view_font_style_panel,this);
        ButterKnife.bind(this);
        fontStyle = new FontStyle();
    }

    //拍照
    @OnClick(R2.id.btn_img)
    protected void btn_img_onClick(){
        if(onFontPanelListener!=null) {
            onFontPanelListener.insertImg();
        }
    }

    /**
     * 字体样式 设置
     */
//    private FontStyleSelectView.OnFontStyleSelectListener onFontStyleSelectListener = new FontStyleSelectView.OnFontStyleSelectListener() {
//        @Override
//        public void setBold(boolean isBold) {
//            if(onFontPanelListener!=null) {
//                onFontPanelListener.setBold(isBold);
//            }
//        }
//        @Override
//        public void setItalic(boolean isItalic) {
//            if(onFontPanelListener!=null) {
//                onFontPanelListener.setItalic(isItalic);
//            }
//        }
//        @Override
//        public void setUnderline(boolean isUnderline) {
//            if(onFontPanelListener!=null) {
//                onFontPanelListener.setUnderline(isUnderline);
//            }
//        }
//        @Override
//        public void setStreak(boolean isStreak) {
//            if(onFontPanelListener!=null) {
//                onFontPanelListener.setStreak(isStreak);
//            }
//        }
//    };

    public interface OnFontPanelListener{
        void setBold(boolean isBold);
        void setItalic(boolean isItalic);
        void insertImg();
    }

    public void setOnFontPanelListener(OnFontPanelListener onFontPanelListener) {
        this.onFontPanelListener = onFontPanelListener;
    }
}
