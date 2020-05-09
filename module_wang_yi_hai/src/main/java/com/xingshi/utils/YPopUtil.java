package com.xingshi.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.xingshi.adapter.MyRecyclerAdapter;
import com.xingshi.bean.PopRuleBean;
import com.xingshi.bean.SignInBean;
import com.xingshi.common.CommonResource;
import com.xingshi.net.OnDataListener;
import com.xingshi.net.OnMyCallBack;
import com.xingshi.net.RetrofitUtil;
import com.xingshi.y_main.R;
import com.xingshi.y_mine.y_my_task.adapter.SignInAdapter;
import com.xingshi.y_mine.y_welfare_center.a_tip_to_release.ATipToReleaseActivity;
import com.xingshi.y_mine.y_welfare_center.release_a_task.ReleaseATaskActivity;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;


public class YPopUtil {

    public static void setTransparency(Context context, float value) {
        Activity activity = (Activity) context;
        Window window = activity.getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.alpha = value;
        window.setAttributes(params);
    }

    public static void conversionGoods(final Context context, int contentNum, OnClearCacheListener listener) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.pop_conversion_goods, null);
        TextView cancel = inflate.findViewById(R.id.pop_conversion_goods_cancel);
        TextView affirm = inflate.findViewById(R.id.pop_conversion_goods_affirm);
        TextView content = inflate.findViewById(R.id.pop_conversion_goods_content);
        content.setText("立即支付" + contentNum + "币");
        final PopupWindow popupWindow = new PopupWindow(inflate, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, true);
        popupWindow.setOutsideTouchable(false);
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        popupWindow.setAnimationStyle(com.xingshi.module_base.R.style.animScale);
        popupWindow.showAtLocation(new View(context), Gravity.CENTER, 0, 0);
        setTransparency(context, 0.3f);

        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                setTransparency(context, 1f);
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });

        listener.setOnClearCache(popupWindow, affirm);
    }

    public static void getTheGiftBag(final Context context, String taskLevel) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.pop_get_the_gift_bag, null);
        ImageView cancel = inflate.findViewById(R.id.pop_get_gift_bag_cancel);
        TextView getGiftBagType = inflate.findViewById(R.id.pop_get_gift_bag_type);
//            getGiftBagType.setText(type == 1 ? "初级礼包" : type == 2 ? "中级礼包" : type == 3 ? "高级礼包" : type == 4 ? "超级礼包" : "铂金礼包");
        getGiftBagType.setText(taskLevel);
        final PopupWindow popupWindow = new PopupWindow(inflate, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, true);
        popupWindow.setOutsideTouchable(false);
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        popupWindow.setAnimationStyle(com.xingshi.module_base.R.style.animScale);
        popupWindow.showAtLocation(new View(context), Gravity.CENTER, 0, 0);
        setTransparency(context, 0.3f);

        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                setTransparency(context, 1f);
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
    }

    public static void confirmPayment(final Context context, OnConfirmPaymentListener listener) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.pop_confirm_payment, null);
        ImageView cancel = inflate.findViewById(R.id.pop_confirm_payment_cancel);
        EditText password = inflate.findViewById(R.id.pop_confirm_payment_password);
        TextView affirm = inflate.findViewById(R.id.pop_confirm_payment_affirm);
        final PopupWindow popupWindow = new PopupWindow(inflate, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, true);
        popupWindow.setOutsideTouchable(false);
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        popupWindow.setAnimationStyle(com.xingshi.module_base.R.style.animScale);
        popupWindow.showAtLocation(new View(context), Gravity.CENTER, 0, 0);
        setTransparency(context, 0.3f);

        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                setTransparency(context, 1f);
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });

        listener.setConfirmPayment(popupWindow, password, affirm);
    }

    public static void commissionWithdraw(final Context context, final String balance, List<PopRuleBean> popRuleBeanList, OnClearCacheListener listener) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.pop_commission_withdraw, null);
        TextView cancel = inflate.findViewById(R.id.pop_commission_withdraw_cancel);
        final RadioButton butWeChat = inflate.findViewById(R.id.pop_commission_withdraw_but_weChat);
        final RadioButton butAliPay = inflate.findViewById(R.id.pop_commission_withdraw_but_aliPay);
        final EditText edit = inflate.findViewById(R.id.pop_commission_withdraw_edit);
        TextView maxBalance = inflate.findViewById(R.id.pop_commission_withdraw_max_balance);
        final RadioButton butBalance = inflate.findViewById(R.id.pop_commission_withdraw_but_balance);
        final RadioButton butCommission = inflate.findViewById(R.id.pop_commission_withdraw_but_commission);
        TextView intro = inflate.findViewById(R.id.pop_commission_withdraw_intro);
        intro.setText(popRuleBeanList.get(0).getParamName() + popRuleBeanList.get(0).getParamValue() + "\n" +
                popRuleBeanList.get(1).getParamName() + popRuleBeanList.get(1).getParamValue() + "\n" +
                popRuleBeanList.get(2).getParamName() + popRuleBeanList.get(2).getParamValue() + "\n" +
                popRuleBeanList.get(3).getParamName() + popRuleBeanList.get(3).getParamValue() + "\n");
        String source = "<font color='#cccccc'>余额$" + balance + "</font><font color='#0048d3'>  最大提现</font>";
        maxBalance.setText(Html.fromHtml(source));

        maxBalance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edit.setText(balance);
            }
        });

        butWeChat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    butAliPay.setChecked(false);
                }
            }
        });

        butAliPay.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    butWeChat.setChecked(false);
                }
            }
        });

        butBalance.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    butCommission.setChecked(false);
                }
            }
        });

        butCommission.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    butBalance.setChecked(false);
                }
            }
        });

        final PopupWindow popupWindow = new PopupWindow(inflate, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, true);
        popupWindow.setOutsideTouchable(false);
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        popupWindow.setAnimationStyle(com.xingshi.module_base.R.style.animScale);
        popupWindow.showAtLocation(new View(context), Gravity.CENTER, 0, 0);
        setTransparency(context, 0.3f);

        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                setTransparency(context, 1f);
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });

        listener.setOnClearCache(popupWindow, inflate);

    }

    public static void topUp(final Context context, OnClearCacheListener listener) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.pop_top_up, null);
        TextView cancel = inflate.findViewById(R.id.pop_top_up_cancel);
        final RadioButton butWeChat = inflate.findViewById(R.id.pop_top_up_but_weChat);
        final RadioButton butAliPay = inflate.findViewById(R.id.pop_top_up_but_aliPay);
        TextView topUp = inflate.findViewById(R.id.pop_top_up_top_up);

        butWeChat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    butAliPay.setChecked(false);
                }
            }
        });

        butAliPay.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    butWeChat.setChecked(false);
                }
            }
        });

        final PopupWindow popupWindow = new PopupWindow(inflate, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, true);
        popupWindow.setOutsideTouchable(false);
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        popupWindow.setAnimationStyle(com.xingshi.module_base.R.style.animScale);
        popupWindow.showAtLocation(new View(context), Gravity.CENTER, 0, 0);
        setTransparency(context, 0.3f);

        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                setTransparency(context, 1f);
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });

        listener.setOnClearCache(popupWindow, inflate);
    }

    public static void fulitankuang(final Context context) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.pop_fulizhongxin, null);
        final ImageView cancel = inflate.findViewById(R.id.pop_fuli_cancel);
        TextView releaseATask = inflate.findViewById(R.id.pop_fuli_release_a_task);
        TextView releaseATip = inflate.findViewById(R.id.pop_fuli_release_a_tip);

        final PopupWindow popupWindow = new PopupWindow(inflate, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, true);
        popupWindow.setOutsideTouchable(false);
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        popupWindow.setAnimationStyle(com.xingshi.module_base.R.style.animScale);
        popupWindow.showAtLocation(new View(context), Gravity.CENTER, 0, 0);
        setTransparency(context, 0.3f);

        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                setTransparency(context, 1f);
            }
        });

        releaseATask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, ReleaseATaskActivity.class));
                popupWindow.dismiss();
            }
        });

        releaseATip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, ATipToReleaseActivity.class));
                popupWindow.dismiss();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });

    }

    public static void payOrderComplain(final Context context, OnClearCacheListener listener) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.pop_pay_order_complain, null);
        final TextView cancel = inflate.findViewById(R.id.pop_pay_order_complaint_cancel);

        final PopupWindow popupWindow = new PopupWindow(inflate, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, true);
        popupWindow.setOutsideTouchable(false);
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        popupWindow.setAnimationStyle(com.xingshi.module_base.R.style.animScale);
        popupWindow.showAtLocation(new View(context), Gravity.CENTER, 0, 0);
        setTransparency(context, 0.3f);

        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                setTransparency(context, 1f);
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });

        listener.setOnClearCache(popupWindow, inflate);
    }

    public static void sign(final Context context, final List<SignInBean> signInBeanList) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.pop_sign_in, null);
        final TextView cancel = inflate.findViewById(R.id.sign_in_cancel);
        final RecyclerView rec = inflate.findViewById(R.id.sign_in_rec);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(context, 6, LinearLayoutManager.VERTICAL, false);
        rec.setLayoutManager(gridLayoutManager);
        final SignInAdapter signInAdapter = new SignInAdapter(context, signInBeanList, R.layout.item_sign_in_rec);
        rec.setAdapter(signInAdapter);

        final PopupWindow popupWindow = new PopupWindow(inflate, DisplayUtil.dip2px(context, 330), LinearLayout.LayoutParams.WRAP_CONTENT, true);
        popupWindow.setOutsideTouchable(false);
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        popupWindow.setAnimationStyle(com.xingshi.module_base.R.style.animScale);
        popupWindow.showAtLocation(new View(context), Gravity.CENTER, 0, 0);
        setTransparency(context, 0.3f);

        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                setTransparency(context, 1f);
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });

        signInAdapter.setOnItemClick(new MyRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerView parent, final View view, int position) {
                long timeMillis = System.currentTimeMillis();
                LogUtil.e("返回时间" + signInBeanList.get(position).getDateTime() + "系统时间" + System.currentTimeMillis());
                if (Long.parseLong(signInBeanList.get(position).getDateTime()) < timeMillis) {
                    Map id = MapUtil.getInstance().addParms("id", signInBeanList.get(position).getId()).build();
                    Observable data = RetrofitUtil.getInstance().getApi(CommonResource.BASEURL_9006).getHead(CommonResource.NEWREMEDYTASK, id, SPUtil.getToken());
                    RetrofitUtil.getInstance().toSubscribe(data, new OnMyCallBack(new OnDataListener() {
                        @Override
                        public void onSuccess(String result, String msg) {
                            LogUtil.e("补签成功" + result);
                            popupWindow.dismiss();
                            signStatus(context, 1);
                        }

                        @Override
                        public void onError(String errorCode, String errorMsg) {
                            LogUtil.e("补签errorMsg" + errorMsg);
                            signStatus(context, 0);
                        }
                    }));
                }
            }
        });
    }

    private static void signStatus(final Context context, int type) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.pop_sign_status, null);
        final TextView cancel = inflate.findViewById(R.id.sign_status_cancel);
        final ImageView image = inflate.findViewById(R.id.sign_status_image);
        final TextView text = inflate.findViewById(R.id.sign_status_text);
        if (1 == type) {
            image.setImageDrawable(context.getResources().getDrawable(R.drawable.qiandaochenggong));
            text.setText("签到成功!");
        } else {
            image.setImageDrawable(context.getResources().getDrawable(R.drawable.qiandaoshibai));
            text.setText("签到失败!");
        }
        final PopupWindow popupWindow = new PopupWindow(inflate, DisplayUtil.dip2px(context, 330), LinearLayout.LayoutParams.WRAP_CONTENT, true);
        popupWindow.setOutsideTouchable(false);
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        popupWindow.setAnimationStyle(com.xingshi.module_base.R.style.animScale);
        popupWindow.showAtLocation(new View(context), Gravity.CENTER, 0, 0);
        setTransparency(context, 0.3f);

        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                setTransparency(context, 1f);
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
    }
}
