package com.rongyuan.mingyida.module.login;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.rongyuan.mingyida.App;
import com.rongyuan.mingyida.R;
import com.rongyuan.mingyida.base.BaseActivity;
import com.rongyuan.mingyida.common.http.API;
import com.rongyuan.mingyida.common.http.IHttpClient;
import com.rongyuan.mingyida.common.http.IRequest;
import com.rongyuan.mingyida.common.http.IResponse;
import com.rongyuan.mingyida.common.http.impl.BaseRequest;
import com.rongyuan.mingyida.common.http.impl.OkHttpClientImpl;
import com.rongyuan.mingyida.common.storage.SharedPreferencesDao;
import com.rongyuan.mingyida.model.Basebean;
import com.rongyuan.mingyida.module.me.account.MyAccountActivity;
import com.rongyuan.mingyida.ui.MyLoader;
import com.rongyuan.mingyida.utils.ToastUtils;
import com.rongyuan.mingyida.utils.countDownTimer;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ForgetPswActivity extends BaseActivity {

    public static final int CHANGE_PHONE = 1;
    public static final int CHANGE_PSW = 0;

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
    @BindView(R.id.ll_me_new_psw)
    LinearLayout llMeNewPsw;
    @BindView(R.id.ll_me_new_psw_again)
    LinearLayout llMeNewPswAgain;
    @BindView(R.id.et_old_psw)
    EditText etOldPsw;
    @BindView(R.id.ll_me_old_psw)
    LinearLayout llMeOldPsw;
    @BindView(R.id.ll_get_code)
    LinearLayout llGetCode;

    private boolean isForget =false;
    private Basebean baseBean;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            MyLoader.stopLoading();
            super.handleMessage(msg);
            if (msg.what ==1){
                if (baseBean!=null){
                    if (baseBean.getData()==1){
                        ToastUtils.showInfo(ForgetPswActivity.this,"修改成功");
                    }else
                        ToastUtils.showWarning(ForgetPswActivity.this,"修改失败,检查旧密码");
                }else
                    ToastUtils.showWarning(ForgetPswActivity.this,"修改失败");
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_psw);
        ButterKnife.bind(this);
        setBackBtn();
        if (getIntent().getIntExtra("CHANGE_TYPE", CHANGE_PSW) == CHANGE_PHONE) {
            isForget =true;
            setTitle("换绑手机");
            llMeNewPsw.setVisibility(View.GONE);
            llMeNewPswAgain.setVisibility(View.GONE);
        } else {
            isForget =false;
            llMeOldPsw.setVisibility(View.VISIBLE);
            llGetCode.setVisibility(View.GONE);
            setTitle("修改密码");
            llMeNewPsw.setVisibility(View.VISIBLE);
            llMeNewPswAgain.setVisibility(View.VISIBLE);
        }

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
                if (TextUtils.isEmpty(etFogetpswPhone.getText().toString())
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
        if (isForget){
            ToastUtils.showInfo(ForgetPswActivity.this,"暂未开通");
            return;

        }
        MyLoader.showLoading(ForgetPswActivity.this);
        new Thread(new Runnable() {
            @Override
            public void run() {
                IHttpClient mHttpClient = new OkHttpClientImpl();
                IRequest request = new BaseRequest(API.TEST_DOMAIN);
                SharedPreferencesDao sharedPreferencesDao = new SharedPreferencesDao(App.getInstance(), SharedPreferencesDao.FILE_ACCOUNT);

                request.setBody("class", sharedPreferencesDao.get("class"));
                if (sharedPreferencesDao.get("class").equals("user")) {
                    request.setBody("user_id", sharedPreferencesDao.get("id"));
                } else {
                    request.setBody("driver_id", sharedPreferencesDao.get("id"));
                }
                request.setBody("mark", "modify_password");
                request.setBody("old_password", etOldPsw.getText().toString().trim());
                request.setBody("new_password", etFogetpswPsw.getText().toString().trim());
                IResponse response = mHttpClient.post(request, false);
                if (response.getCode() == 200) {
                    try {
                        baseBean = new Gson().fromJson(response.getData(), Basebean.class);
                    } catch (RuntimeException e) {
                        baseBean = null;
                    }

                } else {
                    baseBean = new Basebean();
                    baseBean.setCode(500);
                }
                Message message = new Message();
                message.what = 1;
                handler.sendMessage(message);
            }
        }).start();
    }

    private void getCode() {

    }
}
