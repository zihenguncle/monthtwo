package com.example.month.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.month.R;
import com.example.month.bean.ResBean;
import com.example.month.mvp.persenter.IPersenterImpl;
import com.example.month.mvp.view.IView;
import com.example.month.tool.VerifyUtils;
import com.example.month.url.Apis;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisterActivity extends AppCompatActivity implements IView {


    @BindView(R.id.reg_phone)
    EditText phone;
    @BindView(R.id.reg_pass)
    EditText pwd;
    @BindView(R.id.reg_confirm_pass)
    EditText confirm_pwd;
    @BindView(R.id.reg_email)
    EditText email;
    @BindView(R.id.but_register)
    Button register;

    IPersenterImpl iPersenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        iPersenter = new IPersenterImpl(this);
    }

    @OnClick({R.id.but_register})
    public void onClick(View v){
        switch (v.getId()){
            case R.id.but_register:
                String reg_phone = phone.getText().toString();
                String reg_pwd = pwd.getText().toString();
                String reg_con_pwd = confirm_pwd.getText().toString();
                String reg_email = email.getText().toString();
                if(TextUtils.isEmpty(reg_phone)){
                    VerifyUtils.getInstance().toast("手机号不可为空");
                }else if(TextUtils.isEmpty(reg_pwd)){
                    VerifyUtils.getInstance().toast("密码不可为空");
                }else if(!VerifyUtils.getInstance().isPhone(reg_phone)){
                    VerifyUtils.getInstance().toast("请输入合法的手机号");
                }else if(reg_pwd.length()<6){
                    VerifyUtils.getInstance().toast("请输入大于六位的密码");
                }else if(TextUtils.isEmpty(reg_con_pwd)){
                    VerifyUtils.getInstance().toast("请再次输入密码");
                }else if(!reg_pwd.equals(reg_con_pwd)){
                    VerifyUtils.getInstance().toast("两次密码不一样");
                }else if(TextUtils.isEmpty(reg_email)){
                    VerifyUtils.getInstance().toast("邮箱不可为空");
                }else {
                    iPersenter.startRequestGet(String.format(Apis.RES_URL,reg_phone,reg_pwd),ResBean.class);
                }
                break;
                default:
                    break;
        }
    }



    @Override
    public void success(Object data) {
        if(data instanceof ResBean){
            if (((ResBean) data).getCode().equals("0")){
                VerifyUtils.getInstance().toast(((ResBean) data).getMsg());
            }else {
                VerifyUtils.getInstance().toast(((ResBean) data).getMsg());
            }
        }
    }

    @Override
    public void failed(String error) {
        VerifyUtils.getInstance().toast(error);
    }
}
