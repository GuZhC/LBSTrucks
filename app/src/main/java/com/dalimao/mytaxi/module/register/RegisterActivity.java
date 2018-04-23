package com.dalimao.mytaxi.module.register;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.request.RequestOptions;
import com.dalimao.mytaxi.App;
import com.dalimao.mytaxi.R;
import com.dalimao.mytaxi.base.BaseActivity;
import com.dalimao.mytaxi.common.http.API;
import com.dalimao.mytaxi.common.http.BaseBizResponse;
import com.dalimao.mytaxi.common.http.IHttpClient;
import com.dalimao.mytaxi.common.http.IRequest;
import com.dalimao.mytaxi.common.http.IResponse;
import com.dalimao.mytaxi.common.http.impl.BaseRequest;
import com.dalimao.mytaxi.common.http.impl.BaseResponse;
import com.dalimao.mytaxi.common.http.impl.OkHttpClientImpl;
import com.dalimao.mytaxi.common.storage.SharedPreferencesDao;
import com.dalimao.mytaxi.model.RegisterResponse;
import com.dalimao.mytaxi.model.RejisterBean;
import com.dalimao.mytaxi.utils.DevUtil;
import com.dalimao.mytaxi.utils.ToastUtils;
import com.dalimao.mytaxi.utils.countDownTimer;
import com.google.gson.Gson;
import com.kyleduo.switchbutton.SwitchButton;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import rx.Subscription;

public class RegisterActivity extends BaseActivity {

    @BindView(R.id.sb_register)
    SwitchButton sbRegister;
    @BindView(R.id.et_register_name)
    EditText etRegisterName;
    @BindView(R.id.et_register_phone)
    EditText etRegisterPhone;
    @BindView(R.id.et_register_code)
    EditText etRegisterCode;
    @BindView(R.id.tv_register_getcode)
    TextView tvRegisterGetcode;
    @BindView(R.id.et_register_psw)
    EditText etRegisterPsw;
    @BindView(R.id.et_register_psw_two)
    EditText etRegisterPswTwo;
    @BindView(R.id.et_register_email)
    EditText etRegisterEmail;
    @BindView(R.id.im_register_image_one)
    ImageView imRegisterImageOne;
    @BindView(R.id.im_register_image_two)
    ImageView imRegisterImageTwo;
    @BindView(R.id.ll_register_is_shops)
    LinearLayout llRegisterIsShops;
    @BindView(R.id.cb_register_look_agreement)
    CheckBox cbRegisterLookAgreement;
    @BindView(R.id.tv_register_agreement)
    TextView tvRegisterAgreement;
    @BindView(R.id.btn_register_ok)
    Button btnRegisterOk;

    File fileA = null;
    File fileB = null;
    boolean isAgree = false;
    boolean isMember = true;
    boolean isChoseImg = false;
    private String Role = "member";
    String truename;
    String mMame;
    String phone;
    String mAuthCode;
    String auth_code_type;
    String mPassword;
    String mPasswordTwo;

    private Subscription mSubscription;

    private int mLayoutHeight = 0;  //动画执行的padding高度
    boolean mSwitchButtonChick = false;
    RejisterBean rejisterBean;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.arg1) {
                case 0:
                    ToastUtils.showSuccess(RegisterActivity.this, "发送成功，请注意查收");
                    handler.removeMessages(0);
                    break;
                case 1:
                    ToastUtils.showError(RegisterActivity.this, "发送失败，稍后再试");
                    handler.removeMessages(1);
                    break;
                case 2:
//                    ToastUtils.showSuccess(RegisterActivity.this, "验证对了");
                    handler.removeMessages(2);
                    doRejister(isMember);
                    break;
                case 3:
                    ToastUtils.showWarning(RegisterActivity.this, "验证码获取错误");
                    handler.removeMessages(3);
                    break;
                case 4:
                    if (rejisterBean!=null){
                        if (rejisterBean.getData() ==1){
                            ToastUtils.showSuccess(RegisterActivity.this, "注册成功");
                            finish();
                        }else
                            ToastUtils.showWarning(RegisterActivity.this, "注册失败");
                    }else
                        ToastUtils.showWarning(RegisterActivity.this, "注册失败");
                    handler.removeMessages(4);
                    break;
            }
        }
    };


    private boolean havaCode = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        setBackBtn();
        setTitle("注册");
        initShowHide();
    }

    public void Register() {
        truename = etRegisterEmail.getText().toString();
        mMame = etRegisterName.getText().toString();
        mPassword = etRegisterPsw.getText().toString();
        mPasswordTwo = etRegisterPswTwo.getText().toString();
        phone = etRegisterPhone.getText().toString();
        mAuthCode = etRegisterCode.getText().toString();

        if (TextUtils.isEmpty(mMame) || TextUtils.isEmpty(mPassword) || TextUtils.isEmpty(phone)
                || TextUtils.isEmpty(mAuthCode) || TextUtils.isEmpty(mPasswordTwo)) {
            ToastUtils.showError(RegisterActivity.this, "请填写完整信息");
            return;
        } else {
            if (isAgree) {
                if (mPassword.equals(mPasswordTwo)) {
                    if (isMember) {
//                        doRejister(isMember);
                        doPoast();
                    } else {
                        if (TextUtils.isEmpty(truename) || !isChoseImg) {
                            ToastUtils.showError(RegisterActivity.this, "信息不完整");
                        } else {
//                            doRejister(isMember);
                            doPoast();
                        }
                    }
                } else {
                    ToastUtils.showError(RegisterActivity.this, "两次密码不一致");
                }
            } else {
                ToastUtils.showError(RegisterActivity.this, "请勾选注册协议");
            }
        }
    }

    private void doPoast() {
        if (havaCode) {
            SMSSDK.submitVerificationCode("86", phone, mAuthCode);
        } else {
            ToastUtils.showWarning(RegisterActivity.this, "验证码错误");
        }
    }

    private void doRejister(final boolean isMember) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                IHttpClient mHttpClient = new OkHttpClientImpl();


                String url = API.Config.getDomain() + API.REGISTER;
                IRequest requesta = new BaseRequest(url);
                requesta.setBody("phone", phone);
                requesta.setBody("password", mPassword);
                requesta.setBody("uid", DevUtil.UUID(App.getInstance()));

                IResponse responsea = mHttpClient.post(requesta, false);
                Log.d(TAG, responsea.getData());

                RegisterResponse registerResponse = new RegisterResponse();
                if (responsea.getCode() == BaseResponse.STATE_OK) {
                    BaseBizResponse bizRes =
                            new Gson().fromJson(responsea.getData(),
                                    BaseBizResponse.class);
                    if (bizRes.getCode() == BaseBizResponse.STATE_OK) {
                        SharedPreferencesDao dao =
                                new SharedPreferencesDao(App.getInstance().getInstance(),
                                        SharedPreferencesDao.FILE_ACCOUNT);
                        dao.save(phone,mPassword);
                    } else {
                    }
                } else {
                }


                IRequest request = new BaseRequest(API.TEST_DOMAIN);
                if (isMember)
                    request.setBody("class", "user");
                else
                    request.setBody("class", "driver");
                request.setBody("mark", "register");
                request.setBody("name", mMame);
                request.setBody("phone_number", phone);
                request.setBody("account", phone);
                request.setBody("password", mPassword);
                IResponse response = mHttpClient.post(request, false);

                if (response.getCode() == 200) {
                    try{
                        rejisterBean = new Gson().fromJson(response.getData(), RejisterBean.class);
                    }catch (RuntimeException e){
                        rejisterBean = null;
                    }

                } else {
                    rejisterBean = new RejisterBean();
                    rejisterBean.setCode(500);
                    rejisterBean.setData(0);
                }
                Message message = new Message();
                message.arg1 = 4;
                handler.sendMessage(message);
            }
        }).start();

//        RxBus.getInstance().chainProcess(new Func1() {
//
//            @Override
//            public Object call(Object o) {
//
//            }
//        });
    }

//    @RegisterBus
//    public void isOk(RejisterBean rejisterBean) {
//        if (rejisterBean != null) {
//            if (rejisterBean.getData() == 1)
//                ToastUtils.showInfo(RegisterActivity.this, "注册成功");
//            else
//                ToastUtils.showInfo(RegisterActivity.this, "失败" + rejisterBean.getMes());
//        } else
//            ToastUtils.showInfo(RegisterActivity.this, "注册失败");
//    }


    public void getRegisterCode() {
        havaCode = true;
// 请求验证码，其中country表示国家代码，如“86”；phone表示手机号码，如“13800138000”
        // 注册一个事件回调，用于处理发送验证码操作的结果
        SMSSDK.registerEventHandler(new EventHandler() {
            public void afterEvent(int event, int result, Object data) {
                if (result == SMSSDK.RESULT_COMPLETE) {
                    if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                        //提交验证码成功
                        sendMsg(2);
                    } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                        sendMsg(0);
                    }
                } else {
                    sendMsg(3);
                }
            }
        });
        // 触发操作
        SMSSDK.getVerificationCode("86", phone);
    }

    private void sendMsg(int i) {
        if (handler.hasMessages(i)) {
            handler.removeMessages(i);
        }
        Message message = new Message();
        message.arg1 = i;
        handler.sendMessage(message);
    }

    private void initShowHide() {
        //布局完成
        llRegisterIsShops.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                //移除所有监听
                llRegisterIsShops.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                mLayoutHeight = llRegisterIsShops.getHeight();
                //隐藏当前控件
                llRegisterIsShops.setPadding(0, -mLayoutHeight, 0, 0);
            }
        });
        sbRegister.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mSwitchButtonChick = isChecked;
                ValueAnimator valueAnimator = new ValueAnimator();
                if (mSwitchButtonChick) {
                    valueAnimator.setIntValues(-mLayoutHeight, 0);
                    isMember = false;
                    Role = "merchant";
                } else {
                    isMember = true;
                    Role = "member";
                    valueAnimator.setIntValues(0, -mLayoutHeight);
                }
                //设置监听的值
                valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animator) {
                        int value = (int) animator.getAnimatedValue();
                        llRegisterIsShops.setPadding(0, value, 0, 0);
                    }
                });
                //动画执行中监听
                valueAnimator.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animator) {
                        //动画开始，不能点击
                        imRegisterImageOne.setClickable(false);
                        imRegisterImageTwo.setClickable(false);
                        etRegisterEmail.setClickable(false);
                    }

                    @Override
                    public void onAnimationEnd(Animator animator) {
                        imRegisterImageOne.setClickable(true);
                        imRegisterImageTwo.setClickable(true);
                        etRegisterEmail.setClickable(true);
                    }

                    @Override
                    public void onAnimationCancel(Animator animator) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animator) {

                    }
                });
                valueAnimator.setDuration(500);
                valueAnimator.start();
            }
        });
    }

    @OnClick({R.id.tv_register_getcode, R.id.im_register_image_one, R.id.im_register_image_two, R.id.cb_register_look_agreement, R.id.btn_register_ok})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_register_getcode:
                phone = etRegisterPhone.getText().toString();
                if (TextUtils.isEmpty(phone)) {
                    ToastUtils.showWarning(RegisterActivity.this, "请输入电话号码");
                } else {
                    new countDownTimer<TextView>(tvRegisterGetcode);
                    getRegisterCode();
                }

                break;
            case R.id.im_register_image_one:
                selectImage();
                break;
            case R.id.im_register_image_two:
                selectImage();
                break;
            case R.id.cb_register_look_agreement:
                if (cbRegisterLookAgreement.isChecked())
                    isAgree = true;
                else
                    isAgree = false;
                break;
            case R.id.btn_register_ok:
                Register();
                break;
        }
    }

    public void selectImage() {
        PictureSelector.create(RegisterActivity.this)
                .openGallery(PictureMimeType.ofImage())
                .compress(true)
                .maxSelectNum(2)// 最大图片选择数量 int
                .minSelectNum(2)// 最小选择数量 int
                .forResult(PictureConfig.CHOOSE_REQUEST);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    // 图片选择结果回调
                    List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
                    // 例如 LocalMedia 里面返回三种path
                    // 1.media.getPath(); 为原图path
                    // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true
                    // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true
                    // 如果裁剪并压缩了，以取压缩路径为准，因为是先裁剪后压缩的
                    if (selectList != null && selectList.size() > 1) {
                        isChoseImg = true;
                        RequestOptions options = new RequestOptions()
                                .error(R.drawable.errorview)
                                .priority(Priority.HIGH);
                        Glide.with(RegisterActivity.this)
                                .load(selectList.get(0).getPath())
                                .apply(options)
                                .into(imRegisterImageOne);
                        Glide.with(RegisterActivity.this)
                                .load(selectList.get(1).getPath())
                                .apply(options)
                                .into(imRegisterImageTwo);
                        fileA = new File(selectList.get(0).getCompressPath());
                        fileB = new File(selectList.get(1).getCompressPath());
                    }
                    break;
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mSubscription != null && !mSubscription.isUnsubscribed()) {
            mSubscription.unsubscribe();
        }
        SMSSDK.unregisterAllEventHandler();
    }
}
