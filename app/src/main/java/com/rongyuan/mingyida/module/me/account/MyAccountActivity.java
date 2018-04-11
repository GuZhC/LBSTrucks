package com.rongyuan.mingyida.module.me.account;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.rongyuan.mingyida.R;
import com.rongyuan.mingyida.base.BaseActivity;
import com.rongyuan.mingyida.module.login.ForgetPswActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class MyAccountActivity extends BaseActivity {

    @BindView(R.id.me_head_image)
    CircleImageView meHeadImage;
    @BindView(R.id.ll_me_change_phone)
    LinearLayout llMeChangePhone;
    @BindView(R.id.ll_me_change_psw)
    LinearLayout llMeChangePsw;
    @BindView(R.id.et_me_change_name)
    EditText etMeChangeName;
    @BindView(R.id.btn_me_account_out)
    Button btnMeAccountOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_account);
        ButterKnife.bind(this);
        setTitle("账户与安全");
    }

    @OnClick({R.id.me_head_image, R.id.ll_me_change_phone, R.id.ll_me_change_psw,R.id.btn_me_account_out})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.me_head_image:
                break;
            case R.id.ll_me_change_phone:
                Intent intent = new Intent(this, ForgetPswActivity.class);
                intent.putExtra("CHANGE_TYPE", ForgetPswActivity.CHANGE_PHONE);
                startActivity(intent);
                break;
            case R.id.ll_me_change_psw:
                startActivity(new Intent(this, ForgetPswActivity.class));
                break;
            case R.id.btn_me_account_out:
               finish();
                break;
        }
    }
}
