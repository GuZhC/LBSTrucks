package com.rongyuan.mingyida.module.register;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
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
import com.kyleduo.switchbutton.SwitchButton;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.rongyuan.mingyida.R;
import com.rongyuan.mingyida.base.BaseActivity;
import com.rongyuan.mingyida.model.BaseModel;
import com.rongyuan.mingyida.model.RegisterModel;
import com.rongyuan.mingyida.net.NetWork;
import com.rongyuan.mingyida.ui.MyLoader;
import com.rongyuan.mingyida.utils.Common;
import com.rongyuan.mingyida.utils.ToastUtils;
import com.rongyuan.mingyida.utils.countDownTimer;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

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
                        doPoast(true);
                    } else {
                        if (TextUtils.isEmpty(truename) || !isChoseImg) {
                            ToastUtils.showError(RegisterActivity.this, "信息不完整");
                        } else {
                            doPoast(false);
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

    private void doPoast(boolean isMember) {
        List<MultipartBody.Part> params = new ArrayList<>();
        RequestBody requestBodyA = null;
        RequestBody requestBodyB = null;
        MultipartBody.Part which = MultipartBody.Part.createFormData("which", Role);
        MultipartBody.Part trueneme = MultipartBody.Part.createFormData("truename", truename);
        MultipartBody.Part username = MultipartBody.Part.createFormData("username", phone);
        MultipartBody.Part nice = MultipartBody.Part.createFormData("nice", mMame);
        MultipartBody.Part anth_code = MultipartBody.Part.createFormData("auth_code", mAuthCode);
        MultipartBody.Part anth_code_type = MultipartBody.Part.createFormData("auth_code_type", "register");
        MultipartBody.Part password = MultipartBody.Part.createFormData("password", mPassword);
        params.add(which);

        if (!isMember) {
            if ((fileA != null && fileA.exists()) && (fileB != null && fileB.exists())) {
                requestBodyA = RequestBody.create(MediaType.parse("image/*"), fileA);
                requestBodyB = RequestBody.create(MediaType.parse("image/*"), fileB);
            }
            params.add(trueneme);
            ToastUtils.showInfo(RegisterActivity.this, "图片上传，可能比较耗时");
        }
        params.add(username);
        params.add(nice);
        params.add(anth_code);
        params.add(anth_code_type);
        params.add(password);

        MyLoader.showLoading(RegisterActivity.this);
        mSubscription = NetWork.getRegisterApi()
                .Regisetr(requestBodyA, requestBodyB, params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<RegisterModel>() {
                    @Override
                    public void onCompleted() {
                        MyLoader.stopLoading();
                    }

                    @Override
                    public void onError(Throwable e) {
                        MyLoader.stopLoading();
                        Common.ShouwError(RegisterActivity.this);
                    }

                    @Override
                    public void onNext(RegisterModel registerModel) {
                        MyLoader.stopLoading();
                        if (registerModel != null) {
                            if (registerModel.getCode() == 0) {
                                ToastUtils.showSuccess(RegisterActivity.this, "注册成功");
                            } else {
                                ToastUtils.showError(RegisterActivity.this, registerModel.getHint());
                            }
                        } else {
                            Common.ShouwError(RegisterActivity.this);
                        }
                    }
                });

    }

    public void getRegisterCode() {
        mSubscription = NetWork.getCodeApi()
                .getCode(phone, "register")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseModel>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        Common.ShouwError(RegisterActivity.this);
                    }

                    @Override
                    public void onNext(BaseModel baseModel) {
                        if (baseModel != null) {
                            ToastUtils.showInfo(RegisterActivity.this, baseModel.code + " " + baseModel.hint + " ");
                        } else {
                            ToastUtils.showInfo(RegisterActivity.this, baseModel.code + " " + baseModel.hint + " ");
                        }
                    }
                });
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
    }
}
