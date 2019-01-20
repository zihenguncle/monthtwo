package com.example.month;

import android.content.Intent;
import android.media.Image;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.month.activity.HomePageActivity;
import com.example.month.activity.RegisterActivity;
import com.example.month.bean.LoginBean;
import com.example.month.mvp.persenter.IPersenterImpl;
import com.example.month.mvp.view.IView;
import com.example.month.tool.VerifyUtils;
import com.example.month.url.Apis;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareConfig;
import com.umeng.socialize.bean.SHARE_MEDIA;

import org.w3c.dom.Text;

import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.http.Body;

public class MainActivity extends AppCompatActivity implements IView {

    @BindView(R.id.main_edit_phone)
    EditText phone;
    @BindView(R.id.main_edit_pwd)
    EditText pwd;
    @BindView(R.id.text_forget)
    TextView forget;
    @BindView(R.id.text_register)
    TextView register;
    @BindView(R.id.but_login)
    TextView login;
    @BindView(R.id.qq_iamge)
    ImageView qq_image;

    private IPersenterImpl iPersenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        iPersenter = new IPersenterImpl(this);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode,resultCode,data);
    }

    @OnClick({R.id.qq_iamge,R.id.but_login,R.id.text_register})
    public void onClick(View v){
        switch (v.getId()){
            case R.id.text_register:
                startActivity(new Intent(this,RegisterActivity.class));
                break;
            case R.id.qq_iamge:
                UMShareConfig config = new UMShareConfig();
                config.isNeedAuthOnGetUserInfo(true);
                UMShareAPI.get(MainActivity.this).setShareConfig(config);
                UMShareAPI umShareAPI = UMShareAPI.get(MainActivity.this);
                umShareAPI.getPlatformInfo(MainActivity.this, SHARE_MEDIA.QQ, new UMAuthListener() {
                    @Override
                    public void onStart(SHARE_MEDIA share_media) {

                    }

                    @Override
                    public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> map) {
                        startActivity(new Intent(MainActivity.this,HomePageActivity.class));
                    }

                    @Override
                    public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {

                    }

                    @Override
                    public void onCancel(SHARE_MEDIA share_media, int i) {

                    }
                });
                break;
            case R.id.but_login:
                String log_phone = phone.getText().toString();
                String log_pwd = pwd.getText().toString();
                if(TextUtils.isEmpty(log_phone)){
                    VerifyUtils.getInstance().toast("手机号不可为空");
                }else if(TextUtils.isEmpty(log_pwd)){
                    VerifyUtils.getInstance().toast("密码不可为空");
                }else if(!VerifyUtils.getInstance().isPhone(log_phone)){
                    VerifyUtils.getInstance().toast("请输入合法的手机号");
                }else if(pwd.getText().toString().length()<6){
                    VerifyUtils.getInstance().toast("密码必须大于6位数");
                }else{
                    iPersenter.startRequestGet(String.format(Apis.LOGIN_URL,log_phone,log_pwd),LoginBean.class);
                }
                break;
                default:
                    break;
        }
    }

    @Override
    public void success(Object data) {
        if(data instanceof LoginBean){
            if(((LoginBean) data).getCode().equals("0")){
                VerifyUtils.getInstance().toast(((LoginBean) data).getMsg());
                startActivity(new Intent(this,HomePageActivity.class));
            }else {
                VerifyUtils.getInstance().toast(((LoginBean) data).getMsg());
            }
        }
    }

    @Override
    public void failed(String error) {
        VerifyUtils.getInstance().toast(error);
    }
}
