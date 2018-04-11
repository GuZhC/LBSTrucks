package com.rongyuan.mingyida.module.login;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.rongyuan.mingyida.R;
import com.rongyuan.mingyida.base.BaseActivity;
import com.rongyuan.mingyida.utils.ToastUtils;
import com.rongyuan.mingyida.utils.countDownTimer;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ForgetPswActivity extends BaseActivity {

    @BindView(R.id.et_fogetpsw_phone)
    EditText etFogetpswPhone;
    @BindView(R.id.et_fogetpsw_code)
    EditText etFogetpswCode;
    @BindView(R.id.tv_fogetpsw_getcode)
    TextView tvFogetpswGetcode;
    @BindView(R.id.et_fogetpsw_psw)
    EditText etFogetpswPsw;
    @BindView(R.id.et_fogetpsw_pswtwo)
    EditText etFogetpswPswtwo;
    @BindView(R.id.btn_fogetpsw_ok)
    Button btnFogetpswOk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_psw);
        ButterKnife.bind(this);
        setBackBtn();
        setTitle("忘记密码");
    }

    @OnClick({R.id.tv_fogetpsw_getcode, R.id.btn_fogetpsw_ok})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_fogetpsw_getcode:
                if (!TextUtils.isEmpty(etFogetpswPhone.getText().toString())) {
                    new countDownTimer<TextView>(tvFogetpswGetcode);
                    getCode();
                } else {
                    ToastUtils.showWarning(this, "请输入手机号");
                }
                break;
            case R.id.btn_fogetpsw_ok:
                if (TextUtils.isEmpty(etFogetpswPhone.getText().toString()) || TextUtils.isEmpty(etFogetpswCode.getText().toString())
                        || TextUtils.isEmpty(etFogetpswPsw.getText().toString()) || TextUtils.isEmpty(etFogetpswPswtwo.getText().toString())) {
                    ToastUtils.showWarning(this, "请完整填写信息");
                } else {
                    if (etFogetpswPsw.getText().toString().equals(etFogetpswPswtwo.getText().toString())) {
                        postNewPsw();
                    } else {
                        ToastUtils.showWarning(this, "两次密码不一致");
                    }
                }
                break;
        }
    }

    private void postNewPsw() {

    }

    private void getCode() {

    }
}
