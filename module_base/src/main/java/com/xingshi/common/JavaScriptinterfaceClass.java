package com.xingshi.common;

import android.content.Context;
import android.webkit.JavascriptInterface;

import com.alibaba.android.arouter.launcher.ARouter;

public class JavaScriptinterfaceClass {
    private Context mContext;


    public JavaScriptinterfaceClass(Context c) {
        mContext = c;
    }

    @JavascriptInterface
    public void invitation() {
        ARouter.getInstance().build("/mine/invite_friends").navigation();
    }

    /**
     * 与js交互时用到的方法，在js里直接调用的
     */
    @JavascriptInterface
    public void jump(String data) {

    }


}
