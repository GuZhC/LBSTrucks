package com.dalimao.mytaxi.module.nearby;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.dalimao.mytaxi.App;
import com.dalimao.mytaxi.R;
import com.dalimao.mytaxi.common.http.IHttpClient;
import com.dalimao.mytaxi.common.http.impl.OkHttpClientImpl;
import com.dalimao.mytaxi.common.storage.SharedPreferencesDao;
import com.dalimao.mytaxi.utils.FormatUtil;

/**
 * Created by liuguangli on 17/2/26.
 */

public class PhoneInputDialog extends Dialog {


    private View mRoot;
    private EditText mPhone;
    private Button mButton;
    private Context mainActivity;
    public PhoneInputDialog(Context mainActivity) {
        this(mainActivity, R.style.Dialog);
        this.mainActivity = mainActivity;
    }
    public PhoneInputDialog(Context context, int theme) {
        super(context, theme);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mRoot  = inflater.inflate(R.layout.dialog_phone_input, null);
        setContentView(mRoot);
        initListener();
    }

    private void initListener() {

        mButton = (Button) findViewById(R.id.btn_next);
        mButton.setEnabled(false);
        mPhone = (EditText) findViewById(R.id.phone);
        //  手机号输入框组册监听检查手机号输入是否合法
        mPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                check();
            }
        });
        // 按钮注册监听
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//
                String phone =  mPhone.getText().toString();
//                SmsCodeDialog dialog = new SmsCodeDialog(mainActivity, phone);
//                dialog.show();
//                dialog.setOnDismissListener(new OnDismissListener() {
//                    @Override
//                    public void onDismiss(DialogInterface dialog) {
//                        PhoneInputDialog.this.dismiss();
//                    }
//                });
                IHttpClient httpClient = new OkHttpClientImpl();
                SharedPreferencesDao dao =
                        new SharedPreferencesDao(App.getInstance(),
                                SharedPreferencesDao.FILE_ACCOUNT);
                IAccountManager manager = new AccountManagerImpl(httpClient, dao);
                manager.checkSmsCode(phone, "8888");
            }
        });


        // 关闭按钮注册监听事件

        findViewById(R.id.close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PhoneInputDialog.this.dismiss();
            }
        });
    }

    private void check(){
        String phone =  mPhone.getText().toString();
        boolean legal = FormatUtil.checkMobile(phone);
        mButton.setEnabled(legal);

    }


}
