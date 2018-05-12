package com.dalimao.mytaxi.module.me;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.dalimao.mytaxi.App;
import com.dalimao.mytaxi.R;
import com.dalimao.mytaxi.base.BaseFragment;
import com.dalimao.mytaxi.common.http.API;
import com.dalimao.mytaxi.common.http.IHttpClient;
import com.dalimao.mytaxi.common.http.IRequest;
import com.dalimao.mytaxi.common.http.IResponse;
import com.dalimao.mytaxi.common.http.impl.BaseRequest;
import com.dalimao.mytaxi.common.http.impl.OkHttpClientImpl;
import com.dalimao.mytaxi.common.storage.SharedPreferencesDao;
import com.dalimao.mytaxi.model.USerBean;
import com.dalimao.mytaxi.module.login.LoginActivity;
import com.dalimao.mytaxi.module.me.account.MyAccountActivity;
import com.dalimao.mytaxi.utils.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by guZhongC on 2018/1/5.
 * describe:
 */

public class MeFragment extends BaseFragment {
    @BindView(R.id.me_toolbar_set)
    ImageView meToolbarSet;
    @BindView(R.id.me_head_image)
    CircleImageView meHeadImage;
    @BindView(R.id.me_user_name)
    TextView meUserName;
    @BindView(R.id.me_cart_info)
    TextView meCartInfo;
    @BindView(R.id.me_me_code)
    TextView meMeCode;
    @BindView(R.id.tv_me_lookall_order)
    TextView tvMeLookallOrder;
    @BindView(R.id.me_nopay_order)
    TextView meNopayOrder;
    @BindView(R.id.me_noserve_order)
    TextView meNoserveOrder;
    @BindView(R.id.me_complete_order)
    TextView meCompleteOrder;
    @BindView(R.id.me_backmoney_order)
    TextView meBackmoneyOrder;
    @BindView(R.id.l_me_location)
    LinearLayout lMeLocation;
    @BindView(R.id.l_me_safety)
    LinearLayout lMeSafety;
    @BindView(R.id.l_me_news)
    LinearLayout lMeNews;
    @BindView(R.id.l_me_aboutme)
    LinearLayout lMeAboutme;
    Unbinder unbinder;
    private USerBean userBean;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Log.e("meinfo",userBean.toString());
            if (msg.what==1){
             if (userBean!=null){
                 if (userBean.getCode()==200){
                     if (userBean.getData()!=null){
                         Glide.with(getContext())
                                 .load(userBean.getData().getPhoto().toString().trim())
//                .placeholder(R.drawable.lodingview)
                                 .into(meHeadImage);
                         meUserName.setText(userBean.getData().getName());
                     }else {
                         Glide.with(getContext())
                                 .load("https://ws1.sinaimg.cn/large/610dc034ly1fiednrydq8j20u011itfz.jpg")
//                .placeholder(R.drawable.lodingview)
                                 .into(meHeadImage);
                         meUserName.setText("");
                     }

                 }else {
//                     ToastUtils.showInfo(getContext(),"获取用户信息失败");
                 }
             }
            }
        }
    };

    @Override
    protected int getContentViewLayoutID() {
        return R.layout. fragment_me;
    }

    @Override
    protected void init(Bundle savedInstanc) {
    }

    @Override
    public void onResume() {
        super.onResume();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    IHttpClient mHttpClient = new OkHttpClientImpl();
                    IRequest request = new BaseRequest(API.TEST_DOMAIN);
                    SharedPreferencesDao sharedPreferencesDao = new SharedPreferencesDao(App.getInstance(),SharedPreferencesDao.FILE_ACCOUNT);

                    request.setBody("class",  sharedPreferencesDao.get("class"));
                    request.setBody("mark", "get_basic_info");
                    request.setBody("user_id",  sharedPreferencesDao.get("id"));
                    IResponse response = mHttpClient.post(request, false);
                    if (response.getCode() == 200) {
                        try{
                            userBean = new Gson().fromJson(response.getData(), USerBean.class);
                        }catch (RuntimeException e){
                            userBean = null;
                        }

                    } else {
                        userBean = new USerBean();
                        userBean.setCode(500);
                    }
                    Message message = new Message();
                    message.what = 1;
                    handler.sendMessage(message);
                }
            }).start();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.me_cart_info, R.id.me_me_code, R.id.tv_me_lookall_order, R.id.me_nopay_order,
            R.id.me_noserve_order, R.id.me_complete_order, R.id.me_backmoney_order, R.id.l_me_location,
            R.id.l_me_safety, R.id.l_me_news, R.id.l_me_aboutme,R.id.me_toolbar_set})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.me_toolbar_set:
                startActivity(new Intent(getContext(),LoginActivity.class));
                getActivity().finish();
                break;
                case R.id.me_cart_info:
                    ToastUtils.showInfo(getContext(), "click");
//               startActivity(new Intent(getContext(),MyCartInfoActivity.class));
                break;
            case R.id.me_me_code:
                ToastUtils.showInfo(getContext(), "click");
                break;
            case R.id.tv_me_lookall_order:
                ToastUtils.showInfo(getContext(), "click");
                break;
            case R.id.me_nopay_order:
                ToastUtils.showInfo(getContext(), "click");
                break;
            case R.id.me_noserve_order:
                ToastUtils.showInfo(getContext(), "click");
                break;
            case R.id.me_complete_order:
                ToastUtils.showInfo(getContext(), "click");
                break;
            case R.id.me_backmoney_order:
                ToastUtils.showInfo(getContext(), "click");
                break;
            case R.id.l_me_location:
                ToastUtils.showInfo(getContext(), "click");
                break;
            case R.id.l_me_safety:
                Intent intent = new Intent(getContext(), MyAccountActivity.class);
                if (userBean!=null){
                    if (userBean.getData()!=null){
                        intent.putExtra("imageUrl",userBean.getData().getPhoto().toString().trim());
                        intent.putExtra("name",userBean.getData().getName());
                    }else {
                        intent.putExtra("imageUrl","error");
                        intent.putExtra("name","name");
                    }
                }
              startActivity(intent );
                break;
            case R.id.l_me_news:
                ToastUtils.showInfo(getContext(), "click");
                break;
            case R.id.l_me_aboutme:
                ToastUtils.showInfo(getContext(), "click");
                break;
        }
    }
}
