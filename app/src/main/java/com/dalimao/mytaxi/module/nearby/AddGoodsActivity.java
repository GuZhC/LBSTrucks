package com.dalimao.mytaxi.module.nearby;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;

import com.dalimao.mytaxi.R;
import com.dalimao.mytaxi.base.BaseActivity;
import com.dalimao.mytaxi.model.AddGoodsBean;
import com.dalimao.mytaxi.module.mains.MainActivity;
import com.dalimao.mytaxi.utils.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddGoodsActivity extends BaseActivity {

    @BindView(R.id.et_addgoods_type)
    EditText etAddgoodsType;
    @BindView(R.id.et_addgoods_num)
    EditText etAddgoodsNum;
    @BindView(R.id.et_addgoods_weight)
    EditText etAddgoodsWeight;
    @BindView(R.id.et_addgoods_baozhuangtype)
    EditText etAddgoodsBaozhuangtype;
    @BindView(R.id.et_addgoods_goodssize)
    EditText etAddgoodsGoodssize;
    @BindView(R.id.et_addgoods_time)
    EditText etAddgoodsTime;
    @BindView(R.id.et_addgoods_money)
    EditText etAddgoodsMoney;
    @BindView(R.id.et_addgoods_location)
    EditText etAddgoodsLocation;
    @BindView(R.id.et_addgoods_locationtwo)
    EditText etAddgoodsLocationtwo;
    @BindView(R.id.btn_addgoods)
    Button btnAddgoods;

    AddGoodsBean addGoodsBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_goods);
        ButterKnife.bind(this);
        setBackBtn();
        setTitle("填写信息");
        addGoodsBean =  AddGoodsBean.getInstance();

        if(addGoodsBean.isHasGoods()){
            etAddgoodsNum.setText(addGoodsBean.getEtAddgoodsNum());
            etAddgoodsWeight.setText(addGoodsBean.getEtAddgoodsWeight());
            etAddgoodsBaozhuangtype.setText(addGoodsBean.getEtAddgoodsBaozhuangtype());
            etAddgoodsGoodssize.setText(addGoodsBean.getEtAddgoodsGoodssize());
            etAddgoodsLocation.setText(addGoodsBean.getEtAddgoodsLocation());
            etAddgoodsLocationtwo.setText(addGoodsBean.getEtAddgoodsMoney());
            etAddgoodsMoney.setText(addGoodsBean.getEtAddgoodsMoney());
            etAddgoodsTime.setText(addGoodsBean.getEtAddgoodsTime());
            etAddgoodsType.setText(addGoodsBean.getEtAddgoodsType());
        }
    }
    @OnClick(R.id.btn_addgoods)
    public void onViewClicked() {
        if (TextUtils.isEmpty(etAddgoodsType.getText().toString())||TextUtils.isEmpty(etAddgoodsNum.getText().toString())||
                TextUtils.isEmpty(etAddgoodsWeight.getText().toString())||TextUtils.isEmpty(etAddgoodsBaozhuangtype.getText().toString())||
                TextUtils.isEmpty(etAddgoodsGoodssize.getText().toString())||TextUtils.isEmpty(etAddgoodsLocation.getText().toString())||
                TextUtils.isEmpty(etAddgoodsLocationtwo.getText().toString())||TextUtils.isEmpty(etAddgoodsMoney.getText().toString())||
                TextUtils.isEmpty(etAddgoodsTime.getText().toString())){
            ToastUtils.showInfo(this,"请填写完整信息");
            return;
        }else {
            addGoodsBean.setEtAddgoodsNum(etAddgoodsNum.getText().toString());
            addGoodsBean.setEtAddgoodsWeight(etAddgoodsWeight.getText().toString());
            addGoodsBean.setEtAddgoodsBaozhuangtype(etAddgoodsBaozhuangtype.getText().toString());
            addGoodsBean.setEtAddgoodsGoodssize(etAddgoodsGoodssize.getText().toString());
            addGoodsBean.setEtAddgoodsLocation(etAddgoodsLocation.getText().toString());
            addGoodsBean.setEtAddgoodsLocationtwo(etAddgoodsLocationtwo.getText().toString());
            addGoodsBean.setEtAddgoodsMoney(etAddgoodsMoney.getText().toString());
            addGoodsBean.setEtAddgoodsTime(etAddgoodsTime.getText().toString());
            addGoodsBean.setEtAddgoodsType(etAddgoodsType.getText().toString());
            addGoodsBean.setHasGoods(true);
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }
}
