package com.rongyuan.mingyida.module.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.rongyuan.mingyida.R;
import com.rongyuan.mingyida.base.BaseActivity;
import com.rongyuan.mingyida.module.register.RegisterActivity;
import com.rongyuan.mingyida.ui.MyLoader;
import com.rongyuan.mingyida.utils.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity implements LoginContract.ILoginView {

    @BindView(R.id.tv_login_chose_member)
    TextView tvLoginChoseMember;
    @BindView(R.id.tv_login_chose_merchant)
    TextView tvLoginChoseMerchant;
    @BindView(R.id.et_login_phone)
    EditText etLoginPhone;
    @BindView(R.id.et_login_psw)
    EditText etLoginPsw;
    @BindView(R.id.cb_login_remenberpsd)
    CheckBox cbLoginRemenberpsd;
    @BindView(R.id.tv_login_forget_psd)
    TextView tvLoginForgetPsd;
    @BindView(R.id.btn_login)
    Button btnLogin;
    @BindView(R.id.tv_login_register)
    TextView tvLoginRegister;

    private LoginPresenter mLoginPresenter;
    private String name = "";
    private String psw = "";
    private boolean isRemenberPsw = true;
    private String Role = "member";
    private boolean IsMember = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        hideToolbar();
        mLoginPresenter = new LoginPresenter(LoginActivity.this, this);
        InitNamePsw();
    }

    @OnClick({R.id.tv_login_chose_member, R.id.tv_login_chose_merchant,
            R.id.btn_login, R.id.tv_login_forget_psd, R.id.tv_login_register, R.id.cb_login_remenberpsd})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_login_chose_member:
                Role = "member";
                IsMember = true;
                tvLoginChoseMember.setTextSize(22);
                tvLoginChoseMerchant.setTextSize(14);
                break;
            case R.id.tv_login_chose_merchant:
                choseMerchant();
                break;
            case R.id.btn_login:
                LoginClick();
                break;
            case R.id.tv_login_forget_psd:
                startActivity(new Intent(LoginActivity.this, ForgetPswActivity.class));
                break;
            case R.id.tv_login_register:
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                break;
            case R.id.cb_login_remenberpsd:
                if (cbLoginRemenberpsd.isChecked()) {
                    isRemenberPsw = true;
                } else {
                    isRemenberPsw = false;
                }
                break;
        }
    }

    @Override
    public void InitNamePsw() {
        etLoginPhone.setText(mLoginPresenter.getName());
        etLoginPhone.setSelection(etLoginPhone.getText().length());
        etLoginPsw.setText(mLoginPresenter.getPsw());
        cbLoginRemenberpsd.setChecked(mLoginPresenter.isRemener());
        if (!mLoginPresenter.isMember()) {
            choseMerchant();
        }
    }

    @Override
    public void ShowLoading() {
        MyLoader.showLoading(LoginActivity.this);
    }

    @Override
    public void StopLoading() {
        MyLoader.stopLoading();
    }

    public void choseMerchant() {
        IsMember = false;
        Role = "merchant";
        tvLoginChoseMember.setTextSize(14);
        tvLoginChoseMerchant.setTextSize(22);
    }

    public void LoginClick() {
        name = etLoginPhone.getText().toString();
        psw = etLoginPsw.getText().toString();
        if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(psw))
            mLoginPresenter.Login(Role, name, psw);
        else
            ToastUtils.showError(LoginActivity.this, "请输入用户名或密码");

        try {
            mLoginPresenter.RememberPsw(isRemenberPsw, name, psw, IsMember);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onBackPressed() {
        name = etLoginPhone.getText().toString();
        psw = etLoginPsw.getText().toString();
        if (!TextUtils.isEmpty(name) || !TextUtils.isEmpty(psw)) {
            try {
                mLoginPresenter.RememberPsw(isRemenberPsw, name, psw, IsMember);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mLoginPresenter.unSubscribe();
    }
}
