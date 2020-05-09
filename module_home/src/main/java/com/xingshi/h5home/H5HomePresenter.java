package com.xingshi.h5home;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.telephony.TelephonyManager;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.xingshi.common.CommonResource;
import com.xingshi.mvp.BasePresenter;
import com.xingshi.utils.AndroidJs;
import com.xingshi.utils.LogUtil;
import com.xingshi.utils.SPUtil;
import com.xingshi.utils.net_change_util.NetworkType;
import com.xingshi.utils.net_change_util.NetworkUtil;

import org.greenrobot.eventbus.EventBus;

import java.io.File;

import static android.content.Context.TELEPHONY_SERVICE;

public class H5HomePresenter extends BasePresenter<H5HomeView> {

    private static String imei;
    private static final String APP_CACAHE_DIRNAME = "/webcache";

    public H5HomePresenter(Context context) {
        super(context);
    }

    @Override
    protected void onViewDestroy() {
        EventBus.getDefault().unregister(this);
    }

    public void initWebView(WebView homeH5Web) {
        WebSettings webSettings = homeH5Web.getSettings();
        // 设置与Js交互的权限
        webSettings.setJavaScriptEnabled(true);

        homeH5Web.addJavascriptInterface(new AndroidJs(mContext), "android");

        //设置自适应屏幕，两者合用
        webSettings.setUseWideViewPort(true); //将图片调整到适合webview的大小
        webSettings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小
        //缩放操作
        webSettings.setSupportZoom(false); //支持缩放，默认为true。是下面那个的前提。
        webSettings.setBuiltInZoomControls(false); //设置内置的缩放控件。若为false，则该WebView不可缩放
        webSettings.setDisplayZoomControls(false); //隐藏原生的缩放控件
        webSettings.setAllowFileAccessFromFileURLs(true);
        webSettings.setRenderPriority(WebSettings.RenderPriority.HIGH);
        webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);  //设置 缓存模式
        // 开启 DOM storage API 功能
        webSettings.setDomStorageEnabled(true);
        //开启 database storage API 功能
        webSettings.setDatabaseEnabled(true);
        String cacheDirPath = mContext.getFilesDir().getAbsolutePath() + APP_CACAHE_DIRNAME;
//      String cacheDirPath = getCacheDir().getAbsolutePath()+Constant.APP_DB_DIRNAME;
        LogUtil.e("cacheDirPath=" + cacheDirPath);
        //设置数据库缓存路径
        webSettings.setDatabasePath(cacheDirPath);
        //设置  Application Caches 缓存目录
        webSettings.setAppCachePath(cacheDirPath);
        //开启 Application Caches 功能
        webSettings.setAppCacheEnabled(true);
//        webSettings.setAppCacheMaxSize(1024 * 1024 * 1024); //缓存最多可以有8M

        //其他细节操作
        webSettings.setAllowFileAccess(true); //设置可以访问文件
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true); //支持通过JS打开新窗口
        webSettings.setLoadsImagesAutomatically(true); //支持自动加载图片
        webSettings.setDefaultTextEncodingName("utf-8");//设置编码格式

        NetworkType networkType = NetworkUtil.getNetworkType(mContext);
        LogUtil.e("当前网络：" + networkType);
        if (networkType == NetworkType.NETWORK_NO) {
            webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK); //没有网络时加载缓存
        } else {
            webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
        }

        homeH5Web.setWebViewClient(new WebViewClient() {
            @Override
            public void onLoadResource(WebView view, String url) {
                super.onLoadResource(view, url);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView webview, String url) {
                webview.loadUrl(url);
                return true;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
            }

            @Override
            public void onPageFinished(WebView view, String url) {
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            }

        });

        homeH5Web.setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
                result.confirm();
                return true;
            }

            @Override
            public boolean onJsConfirm(WebView view, String url, String message, JsResult result) {
                return super.onJsConfirm(view, url, message, result);
            }

            @Override
            public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, JsPromptResult result) {
                return super.onJsPrompt(view, url, message, defaultValue, result);
            }
        });
        //加载url地址
        loadUrl(homeH5Web);
//        LogUtil.e("getIMEi===" + getIMEINew(mContext));
    }

    public void loadUrl(WebView homeH5Web) {
        homeH5Web.loadUrl(CommonResource.BASEURL_8085+"/?tenantId=" + CommonResource.TENANT_ID + "&" + "preProfit=" + SPUtil.getFloatValue(CommonResource.BACKBL) + "&" + "imei=" + imei);
    }

    /**
     * Pseudo-Unique ID, 这个在任何Android手机中都有效 解决手机中IMEI获取不到情况，兼容所有手机
     */
//    public static String getIMEINew(Context context) {
//        //we make this look like a valid IMEI
//        String m_szDevIDShort = "35" +
//                Build.BOARD.length() % 10 +
//                Build.BRAND.length() % 10 +
//                Build.CPU_ABI.length() % 10 +
//                Build.DEVICE.length() % 10 +
//                Build.DISPLAY.length() % 10 +
//                Build.HOST.length() % 10 +
//                Build.ID.length() % 10 +
//                Build.MANUFACTURER.length() % 10 +
//                Build.MODEL.length() % 10 +
//                Build.PRODUCT.length() % 10 +
//                Build.TAGS.length() % 10 +
//                Build.TYPE.length() % 10 +
//                Build.USER.length() % 10; //13 digits
//        return m_szDevIDShort;
//    }


    /**
     * Author: liuqiang
     * Time: 2017-08-14 15:28
     * Description:
     * <p>
     * IMEI 与你的手机是绑定关系 用于区别移动终端设备
     * IMSI 与你的手机卡是绑定关系 用于区别移动用户的有效信息 IMSI是用户的标识。
     * ICCID ICCID是卡的标识，由20位数字组成
     * ICCID只是用来区别SIM卡，不作接入网络的鉴权认证。而IMSI在接入网络的时候，会到运营商的服务器中进行验证。
     * https://github.com/android/platform_frameworks_base/blob/master/telephony/java/android/telephony/TelephonyManager.java
     */
    @SuppressLint("MissingPermission")
    public void check() {

        TelephonyManager telephonyManager = (TelephonyManager) mContext.getSystemService(TELEPHONY_SERVICE);// 取得相关系统服务

        String simOperatorName = telephonyManager.getSimOperatorName();
        //取出 IMEI
        imei = telephonyManager.getDeviceId();
        String imeiAPI26 = null;    //取出 IMEI 需要 api26以上
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            imeiAPI26 = telephonyManager.getImei();
        }
        String tel = telephonyManager.getLine1Number();   //取出 MSISDN，很可能为空
        String imsi = telephonyManager.getSubscriberId();   //取出 IMSI
        String icc = telephonyManager.getSimSerialNumber(); //取出 ICCID

        LogUtil.e("Q_M 运行商名字--" + simOperatorName);
        LogUtil.e("Q_M IMEI--" + imei);
        LogUtil.e("Q_M IMEI_API26--" + imeiAPI26);
        LogUtil.e("Q_M IMSI--" + imsi);
        LogUtil.e("Q_M ICCID--" + icc);//353515234688244
    }


    /**
     * 清除WebView缓存
     */
    public void clearWebViewCache() {

        //清理Webview缓存数据库
        try {
            mContext.deleteDatabase("webview.db");
            mContext.deleteDatabase("webviewCache.db");
        } catch (Exception e) {
            e.printStackTrace();
        }

        //WebView 缓存文件
        File appCacheDir = new File(mContext.getFilesDir().getAbsolutePath() + APP_CACAHE_DIRNAME);
        LogUtil.e("appCacheDir path=" + appCacheDir.getAbsolutePath());

        File webviewCacheDir = new File(mContext.getCacheDir().getAbsolutePath() + "/webviewCache");
        LogUtil.e("webviewCacheDir path=" + webviewCacheDir.getAbsolutePath());

        //删除webview 缓存目录
        if (webviewCacheDir.exists()) {
            deleteFile(webviewCacheDir);
        }
        //删除webview 缓存 缓存目录
        if (appCacheDir.exists()) {
            deleteFile(appCacheDir);
        }
    }

    /**
     * 递归删除 文件/文件夹
     *
     * @param file
     */
    public void deleteFile(File file) {

        LogUtil.e("delete file path=" + file.getAbsolutePath());

        if (file.exists()) {
            if (file.isFile()) {
                file.delete();
            } else if (file.isDirectory()) {
                File files[] = file.listFiles();
                for (int i = 0; i < files.length; i++) {
                    deleteFile(files[i]);
                }
            }
            file.delete();
        } else {
            LogUtil.e("delete file no exists " + file.getAbsolutePath());
        }
    }
}
