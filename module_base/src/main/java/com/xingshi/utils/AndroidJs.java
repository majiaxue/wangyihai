package com.xingshi.utils;

import android.content.Context;
import android.text.TextUtils;
import android.webkit.JavascriptInterface;

import com.alibaba.android.arouter.launcher.ARouter;
import com.alibaba.fastjson.JSON;
import com.xingshi.bean.H5DetailBean1;
import com.xingshi.bean.H5DetailBean2;


public class AndroidJs extends Object {
    private Context mContext;

    public AndroidJs(Context context) {
        this.mContext = context;
    }

    @JavascriptInterface
    public void showToast() {
        LogUtil.e("我成功了showToast");
//        Toast.makeText(mContext, "成功了showToast", Toast.LENGTH_SHORT).show();
    }

    @JavascriptInterface
    public void jumpPage(String data, String type) {
        //type = 0 是网页 type = 1本地链接
        LogUtil.e("我成功了jumpPage----------" + data + "type--------" + type);
        if (!TextUtils.isEmpty(SPUtil.getToken())) {
            if (!"0".equals(type)) {
                if ("搜索".equals(data)) {
                    ARouter.getInstance().build("/module_home/SearchActivity").navigation();
                } else if ("消息".equals(data)) {
                    ARouter.getInstance().build("/mine/messagecenter").navigation();
                } else if ("淘宝".equals(data)) {
                    ARouter.getInstance().build("/module_home/SecondaryDetailsActivity")
                            .withString("type", 0 + "")
                            .navigation();
                } else if ("拼多多".equals(data)) {
                    ARouter.getInstance().build("/module_home/SecondaryDetailsActivity")
                            .withString("type", 2 + "")
                            .navigation();
                } else if ("京东".equals(data)) {
                    ARouter.getInstance().build("/module_home/SecondaryDetailsActivity")
                            .withString("type", 4 + "")
                            .navigation();
                } else if ("天猫".equals(data)) {
                    ARouter.getInstance().build("/module_home/SecondaryDetailsActivity")
                            .withString("type", 6 + "")
                            .navigation();
                } else if ("聚划算".equals(data)) {
                    ARouter.getInstance().build("/module_home/UniversalListActivity").withInt("position", 3).navigation();

                } else if ("淘抢购".equals(data)) {
                    ARouter.getInstance().build("/module_home/UniversalListActivity").withInt("position", 1).navigation();

                } else if ("附近小店".equals(data)) {
                    ARouter.getInstance().build("/module_local/LocalMainActivity").navigation();

                } else if ("9.9包邮".equals(data)) {
                    ARouter.getInstance().build("/module_home/UniversalListActivity").withInt("position", 2).navigation();

                } else if ("产品中心".equals(data)) {
                    ARouter.getInstance().build("/module_home/ProductCenterActivity").navigation();

                } else if ("会场".equals(data)) {
                    ARouter.getInstance().build("/mine/invite_friends").navigation();

                } else if ("爆款推荐".equals(data)) {
                    ARouter.getInstance().build("/module_home/UniversalListActivity").withInt("position", 4).withInt("type", 2).navigation();

                } else if ("抖券购买".equals(data)) {
                    ARouter.getInstance().build("/module_home/ShakeStockActivity").navigation();

                } else if ("打卡签到".equals(data)) {
                    ARouter.getInstance().build("/module_home/PunchSignActivity").navigation();

                } else if ("邀请好友".equals(data)) {
                    ARouter.getInstance().build("/mine/invite_friends").navigation();
                }
            } else {
                //网页
                ARouter.getInstance().build("/module_classify/WebViewActivity").withString("url", data).navigation();
            }

        } else {
            ARouter.getInstance().build("/mine/login").navigation();
        }
    }

    @JavascriptInterface
    public void jumpDetail1(String data) {
        LogUtil.e("低价必抢" + data);
        H5DetailBean1 h5DetailBean1 = JSON.parseObject(data, H5DetailBean1.class);
        LogUtil.e("低价必抢" + h5DetailBean1);
        String startTime = MyTimeUtil.date2String(h5DetailBean1.getCouponstarttime() + "000");
        String endTime = MyTimeUtil.date2String(h5DetailBean1.getCouponendtime() + "000");
        if (!TextUtils.isEmpty(SPUtil.getToken())) {
            ARouter.getInstance().build("/module_classify/TBCommodityDetailsActivity")
                    .withString("para", h5DetailBean1.getItemid())
                    .withString("shoptype", "1")
                    .withDouble("youhuiquan", Double.valueOf(h5DetailBean1.getCouponmoney()))
                    .withString("coupon_start_time", startTime)
                    .withString("coupon_end_time", endTime)
                    .withString("commission_rate", h5DetailBean1.getTkrates())
                    .withInt("type", 0)
                    .navigation();
        } else {
            ARouter.getInstance().build("/mine/login").navigation();
        }
    }

    @JavascriptInterface
    public void jumpDetail2(String data) {
        LogUtil.e("猜你喜欢" + data);
        H5DetailBean2 h5DetailBean2 = JSON.parseObject(data, H5DetailBean2.class);
        LogUtil.e("猜你喜欢" + h5DetailBean2);
        String startTime = MyTimeUtil.date2String(h5DetailBean2.getCoupon_start_time() + "000");
        String endTime = MyTimeUtil.date2String(h5DetailBean2.getCoupon_end_time() + "000");
        if (!TextUtils.isEmpty(SPUtil.getToken())) {
            ARouter.getInstance().build("/module_classify/TBCommodityDetailsActivity")
                    .withString("para", h5DetailBean2.getItem_id())
                    .withString("shoptype", h5DetailBean2.getUser_type())
                    .withDouble("youhuiquan", Double.valueOf(h5DetailBean2.getCoupon_amount()))
                    .withString("coupon_start_time", startTime)
                    .withString("coupon_end_time", endTime)
                    .withString("commission_rate", h5DetailBean2.getCommission_rate())
                    .withInt("type", 0)
                    .navigation();
        } else {
            //是否登录
            ARouter.getInstance().build("/mine/login").navigation();
        }
    }
}
