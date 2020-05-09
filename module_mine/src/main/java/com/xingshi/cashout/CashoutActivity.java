package com.xingshi.cashout;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.xingshi.module_mine.R;
import com.xingshi.module_mine.R2;
import com.xingshi.mvp.BaseActivity;

import butterknife.BindView;

public class CashoutActivity extends BaseActivity<CashoutView, CashoutPresenter> implements CashoutView {
    @BindView(R2.id.include_back)
    ImageView includeBack;
    @BindView(R2.id.include_title)
    TextView includeTitle;
    @BindView(R2.id.cashout_edit)
    EditText cashoutEdit;
    @BindView(R2.id.cashout_balance)
    TextView cashoutBalance;
    @BindView(R2.id.cashout_all)
    TextView cashoutAll;
    @BindView(R2.id.cashout_btn)
    TextView cashoutBtn;
    @BindView(R2.id.cashout_zfb)
    EditText cashoutZFB;
    @BindView(R2.id.cashout_name)
    EditText cashoutName;

    private String balance;

    @Override
    public int getLayoutId() {
        return R.layout.activity_cashout;
    }

    @Override
    public void initData() {
        includeTitle.setText("提现");
        presenter.loadData();
    }

    @Override
    public void initClick() {
        includeBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        cashoutAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cashoutEdit.setText(balance);
                cashoutEdit.setSelection(balance.length());
            }
        });

        cashoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(cashoutEdit.getText())) {
                    Toast.makeText(CashoutActivity.this, "请输入金额", Toast.LENGTH_SHORT).show();
                } else {
                    if (Double.valueOf(cashoutEdit.getText().toString()) > Double.valueOf(balance)) {
                        Toast.makeText(CashoutActivity.this, "余额不足", Toast.LENGTH_SHORT).show();
                    } else {
                        presenter.tixian(cashoutEdit.getText().toString(), cashoutZFB.getText().toString(), cashoutName.getText().toString());
                    }
                }
            }
        });

        cashoutEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s != null && s.toString().equals("00")) {
                    cashoutEdit.setText("0");
                    cashoutEdit.setSelection(cashoutEdit.getText().length());
                }
            }
        });
    }

    @Override
    public void loadBalance(String balance) {
        this.balance = balance;
        cashoutBalance.setText("余额￥" + balance + "，");
    }

    @Override
    public void loadInfo(String name, String aliAcount) {
        cashoutZFB.setText(aliAcount);
        cashoutName.setText(name);
    }

    @Override
    public CashoutView createView() {
        return this;
    }

    @Override
    public CashoutPresenter createPresenter() {
        return new CashoutPresenter(this);
    }
}
