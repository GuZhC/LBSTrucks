package com.dalimao.mytaxi.module.login;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.dalimao.mytaxi.App;
import com.dalimao.mytaxi.common.http.API;
import com.dalimao.mytaxi.common.http.BaseBizResponse;
import com.dalimao.mytaxi.common.http.IHttpClient;
import com.dalimao.mytaxi.common.http.IRequest;
import com.dalimao.mytaxi.common.http.IResponse;
import com.dalimao.mytaxi.common.http.impl.BaseRequest;
import com.dalimao.mytaxi.common.http.impl.BaseResponse;
import com.dalimao.mytaxi.common.http.impl.OkHttpClientImpl;
import com.dalimao.mytaxi.common.storage.SharedPreferencesDao;
import com.dalimao.mytaxi.model.Account;
import com.dalimao.mytaxi.model.LoginBean;
import com.dalimao.mytaxi.model.LoginResponse;
import com.dalimao.mytaxi.module.mains.MainActivity;
import com.dalimao.mytaxi.utils.Eds;
import com.dalimao.mytaxi.utils.ToastUtils;
import com.google.gson.Gson;

import rx.Subscription;

/**
 * Created by guZhongC on 2018/2/5.
 * describe:
 */

public class LoginPresenter implements LoginContract.ILoginPresentr {
    private final LoginContract.ILoginView mLoginView;
    private Subscription mSubscription;
    private SharedPreferences sp;
    Context context;
    private LoginBean loginBean;

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            mLoginView.StopLoading();
            if (msg.what == 1) {
                if (loginBean != null) {
                    if (loginBean.getData().getIs_login() == 1) {
                        doMyRegisterLogin();
                        ToastUtils.showSuccess(context, "登录成功");
                        Intent intent = new Intent(context, MainActivity.class);
                        SharedPreferencesDao sharedPreferencesDao =new SharedPreferencesDao(App.getInstance(),SharedPreferencesDao.FILE_ACCOUNT);
                        sharedPreferencesDao.save("id",loginBean.getData().getId());
                        sharedPreferencesDao.save("class",mclass);
                        context.startActivity(intent);
                        mLoginView.activityFinish();
                    } else
                        ToastUtils.showWarning(context, "账号或者密码错误");
                } else
                    ToastUtils.showWarning(context, "登录失败");
            }
        }
    };

    private void doMyRegisterLogin() {


    }

    private String mclass = "user";

    LoginPresenter(LoginContract.ILoginView loginView, Context context) {
        this.mLoginView = loginView;
        this.context = context;
        sp = context.getSharedPreferences("User", Context.MODE_PRIVATE);
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unSubscribe() {
        if (mSubscription != null && !mSubscription.isUnsubscribed()) {
            mSubscription.unsubscribe();
        }
    }

    @Override
    public String getName() {
        try {
            return Eds.getDESOri(sp.getString("name", ""));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    @Override
    public String getPsw() {
        try {
            return Eds.getDESOri(sp.getString("psw", ""));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    @Override
    public boolean isRemener() {
        return sp.getBoolean("isRemember", false);
    }

    @Override
    public boolean isMember() {
        return sp.getBoolean("isMember", true);
    }

    @Override
    public void Login(final String type, final String name, final String psw) {
        mLoginView.ShowLoading();
        mclass = type;
        String newPsd;
        new Thread(new Runnable() {
            @Override
            public void run() {
                IHttpClient mHttpClient = new OkHttpClientImpl();
                IRequest request = new BaseRequest(API.TEST_DOMAIN);
                request.setBody("class", type);
                request.setBody("mark", "check_login");
                request.setBody("account", name);
                request.setBody("password", psw);
                IResponse response = mHttpClient.post(request, false);

                if (response.getCode() == 200) {
                    try {
                        loginBean = new Gson().fromJson(response.getData(), LoginBean.class);
                    } catch (RuntimeException e) {
                        loginBean = null;
                    }

                } else {
                    loginBean = new LoginBean();
                    loginBean.setCode(500);
                    loginBean.getData().setIs_login(0);
                }
                Message message = new Message();
                message.what = 1;
                handler.sendMessage(message);


                //-----------------------------------------
                SharedPreferencesDao dao =
                        new SharedPreferencesDao(App.getInstance().getInstance(),
                                SharedPreferencesDao.FILE_ACCOUNT);
                String url = API.Config.getDomain() + API.LOGIN;
                IRequest requesta = new BaseRequest(url);
                if (dao.get(name)!=null){
                    requesta.setBody("password", dao.get(name));
                }else {
                    requesta.setBody("password", psw);
                }
                requesta.setBody("phone", name);


                IResponse responsea = mHttpClient.post(requesta, false);
//                Log.d(TAG, response.getData());

                LoginResponse bizRes = new LoginResponse();
                if (responsea.getCode() == BaseResponse.STATE_OK) {
                    bizRes = new Gson().fromJson(responsea.getData(), LoginResponse.class);
                    if (bizRes.getCode() == BaseBizResponse.STATE_OK) {
                        // 保存登录信息
                        Account account = bizRes.getData();

                        dao.save(SharedPreferencesDao.KEY_ACCOUNT, account);
                        // 通知 UI
                    } else if (bizRes.getCode() == BaseBizResponse.STATE_PW_ERR) {
                    } else {
                    }
                } else {
                }
            }
        }).start();
//        mSubscription = NetWork.getLoginApi()
//                .getLoginData(type, name, psw)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Observer<BaseModel<LoginModel>>() {
//                    @Override
//                    public void onCompleted() {
//                        mLoginView.StopLoading();
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        mLoginView.StopLoading();
//                        Common.ShouwError(context);
//                    }
//
//                    @Override
//                    public void onNext(BaseModel<LoginModel> loginModel) {
//                        mLoginView.StopLoading();
//                        if (loginModel != null && loginModel.getData() != null) {
//                            ToastUtils.showInfo(context, String.valueOf(loginModel.hint));
//                            ToastUtils.showInfo(context, loginModel.getData().getApi_secret());
//                        } else {
//                            ToastUtils.showInfo(context, String.valueOf(loginModel.hint));
//                        }
//                    }
//                });
    }

    @Override
    public void RememberPsw(boolean isRemember, String name, String psw, boolean isMember) throws Exception {
        if (name == null || psw == null)
            return;
        if (isRemember) {
            SharedPreferences.Editor edit = sp.edit();
            edit.putString("name", Eds.getDES(name));
            edit.putString("psw", Eds.getDES(psw));
            edit.putBoolean("isRemember", isRemember);
            edit.putBoolean("isMember", isMember);
            edit.commit();
        } else {
            SharedPreferences.Editor edit = sp.edit();
            edit.putString("name", "");
            edit.putString("psw", "");
            edit.putBoolean("isRemember", isRemember);
            edit.putBoolean("isMember", true);
            edit.commit();
        }
    }
}
