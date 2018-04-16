package com.dalimao.mytaxi.module.me.account;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.request.RequestOptions;
import com.dalimao.mytaxi.module.login.ForgetPswActivity;
import com.google.gson.Gson;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.dalimao.mytaxi.App;
import com.dalimao.mytaxi.R;
import com.dalimao.mytaxi.base.BaseActivity;
import com.dalimao.mytaxi.common.http.API;
import com.dalimao.mytaxi.common.http.IHttpClient;
import com.dalimao.mytaxi.common.http.IRequest;
import com.dalimao.mytaxi.common.http.IResponse;
import com.dalimao.mytaxi.common.http.impl.BaseRequest;
import com.dalimao.mytaxi.common.http.impl.OkHttpClientImpl;
import com.dalimao.mytaxi.common.storage.SharedPreferencesDao;
import com.dalimao.mytaxi.model.Basebean;
import com.dalimao.mytaxi.ui.MyLoader;
import com.dalimao.mytaxi.utils.ToastUtils;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class MyAccountActivity extends BaseActivity {

    @BindView(R.id.image_me_head_image)
    CircleImageView meHeadImage;
    @BindView(R.id.ll_me_change_phone)
    LinearLayout llMeChangePhone;
    @BindView(R.id.ll_me_change_psw)
    LinearLayout llMeChangePsw;
    @BindView(R.id.et_me_change_name)
    EditText etMeChangeName;
    @BindView(R.id.btn_me_account_out)
    Button btnMeAccountOut;
    @BindView(R.id.et_me_change_phone)
    EditText etMeChangePhone;
    private Basebean baseBean;
    String fileA = null;
    String name = null;
    File fileB = null;
    boolean isChoseImg = false;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            MyLoader.stopLoading();
            if (msg.what == 1) {
                if (baseBean != null) {
                    if (baseBean.getCode() == 200)
                        if (baseBean.getData() == 1)
                            ToastUtils.showInfo(MyAccountActivity.this, "修改成功");
                        else
                            ToastUtils.showInfo(MyAccountActivity.this, "修改失败");
                    else
                        ToastUtils.showInfo(MyAccountActivity.this, "修改失败");
                } else
                    ToastUtils.showInfo(MyAccountActivity.this, "修改失败");
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_account);
        ButterKnife.bind(this);
        setTitle("账户与安全");
        Intent intent = getIntent();
        fileA =   intent.getStringExtra("imageUrl");
        name = intent.getStringExtra("name");
        if (fileA!= null){
            RequestOptions options = new RequestOptions()
                    .error(R.drawable.errorview)
                    .priority(Priority.HIGH);
            Glide.with(MyAccountActivity.this)
                    .load(fileA)
                    .apply(options)
                    .into(meHeadImage);
        }
        if (name!=null)
            etMeChangeName.setText(name);
    }

    @OnClick({R.id.image_me_head_image, R.id.ll_me_change_phone, R.id.ll_me_change_psw, R.id.btn_me_account_out})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.image_me_head_image:
                PictureSelector.create(MyAccountActivity.this)
                        .openGallery(PictureMimeType.ofImage())
                        .compress(true)
                        .maxSelectNum(1)// 最大图片选择数量 int
                        .minSelectNum(1)// 最小选择数量 int
                        .forResult(PictureConfig.CHOOSE_REQUEST);
                break;
            case R.id.ll_me_change_phone:
//                Intent intent = new Intent(this, ForgetPswActivity.class);
//                intent.putExtra("CHANGE_TYPE", ForgetPswActivity.CHANGE_PHONE);
//                startActivity(intent);

                break;
            case R.id.ll_me_change_psw:
                startActivity(new Intent(this, ForgetPswActivity.class));
                break;
            case R.id.btn_me_account_out:
                doPost();
                break;
        }
    }

    private void doPost() {
        MyLoader.showLoading(MyAccountActivity.this);
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
                request.setBody("mark", "update_basic_info");
                request.setBody("phone_number", etMeChangePhone.getText().toString().trim());
                request.setBody("photo", fileA);
                request.setBody("name", etMeChangeName.getText().toString());
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
                    if (selectList != null && selectList.size() > 0) {
                        isChoseImg = true;
                        RequestOptions options = new RequestOptions()
                                .error(R.drawable.errorview)
                                .priority(Priority.HIGH);
                        Glide.with(MyAccountActivity.this)
                                .load(selectList.get(0).getPath())
                                .apply(options)
                                .into(meHeadImage);

//                        fileA = new File(selectList.get(0).getCompressPath());
                        fileA = selectList.get(0).getPath();
                    }
                    break;
            }
        }
    }

}
