package com.example.month.fragment;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.month.R;
import com.example.month.base.BaseFragment;
import com.example.month.bean.HeadBean;
import com.example.month.mvp.persenter.IPersenterImpl;
import com.example.month.mvp.view.IView;
import com.example.month.tool.VerifyUtils;
import com.example.month.url.Apis;
import com.facebook.drawee.view.SimpleDraweeView;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.app.Activity.RESULT_OK;

public class MineFragment extends BaseFragment implements IView {

    @BindView(R.id.image_pic)
    SimpleDraweeView head_image;
    @BindView(R.id.text_nickName)
    TextView name;

    IPersenterImpl iPersenter;
    @Override
    protected int getLayout() {
        return R.layout.mine_fragment;
    }

    @Override
    protected void initData(View view) {
        ButterKnife.bind(this,view);
        iPersenter = new IPersenterImpl(this);

    }

    @OnClick({R.id.image_pic})
    public void setonClick(View view){
        switch (view.getId()){
            case R.id.image_pic:if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
                String[] mStatenetwork = new String[]{
                        //写的权限
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        //读的权限
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        //入网权限
                        Manifest.permission.ACCESS_NETWORK_STATE,
                        //WIFI权限
                        Manifest.permission.ACCESS_WIFI_STATE,
                        //读手机权限
                        Manifest.permission.READ_PHONE_STATE,
                        //网络权限
                        Manifest.permission.INTERNET,
                        //相机
                        Manifest.permission.CAMERA,
                        Manifest.permission.WRITE_APN_SETTINGS,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.ACCESS_NETWORK_STATE,
                };
                ActivityCompat.requestPermissions(getActivity(),mStatenetwork,100);
            }else {
                getAlert();
            }


                break;
                default:
                    break;
        }
    }

    private final int REQUEST_PICK = 100;
    private final int REQUEST_CAMEAR = 200;
    private final int REQUEST_PICTRUE = 300;
    private static final String PATH_FILE = Environment.getExternalStorageDirectory()+"/file.png";
    private static final String path = Environment.getExternalStorageDirectory()+"/image.png";
    private void getAlert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view1 = View.inflate(getActivity(),R.layout.builder_item,null);
        TextView xiangji = view1.findViewById(R.id.xiangji);
        TextView xiangce = view1.findViewById(R.id.xiangce);
        builder.setView(view1);
        builder.show();
        xiangce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent,REQUEST_PICK);
            }
        });
        xiangji.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT,Uri.fromFile(new File(path)));
                startActivityForResult(intent,REQUEST_CAMEAR);
            }
        });
    }


    //没有权限的话负责打开权限
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        boolean hasPermission  = false;
        if(requestCode == 100){
            for (int i = 0;i<grantResults.length;i++){
                if(grantResults[i] == -1){
                    hasPermission = true;
                }
            }
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CAMEAR && resultCode == getActivity().RESULT_OK){
            /*//打开裁剪
            Intent intent = new Intent("com.android.camera.action.CROP");
            intent.setDataAndType(Uri.fromFile(new File(path)), "image/*");
            intent.putExtra("CROP",true);
            //设置宽高比
            intent.putExtra("aspectX",1);
            intent.putExtra("aspectY",1);
            //设置输出图片大小
            intent.putExtra("outputX",100);
            intent.putExtra("outputY",100);
            //返回到data
            intent.putExtra("reture-data",true);*/
            //打开裁剪
            Intent intent = new Intent("com.android.camera.action.CROP");
            //将图片设置给裁剪
            intent.setDataAndType(Uri.fromFile(new File(path)), "image/*");
            //设置是否支持裁剪
            intent.putExtra("CROP", true);
            //设置宽高比
            intent.putExtra("aspectX", 1);
            intent.putExtra("aspectY", 1);
            //设置输出后图片大小
            intent.putExtra("outputX", 100);
            intent.putExtra("outputY", 100);
            //返回到data
            intent.putExtra("return-data", true);
            //启动
            startActivityForResult(intent,REQUEST_PICTRUE);
        }
        if(requestCode == REQUEST_PICK && resultCode == getActivity().RESULT_OK){
            /*//打开裁剪
            Intent intent = new Intent("com.android.camera.action.CROP");
            Uri uri = data.getData();
            //将图片设置给裁剪
            intent.setDataAndType(uri,"image/*");
            //设置是否支持
            intent.putExtra("CROP",true);
            //设置宽高比
            intent.putExtra("aspectX",1);
            intent.putExtra("aspectY",1);
            //设置输出
            intent.putExtra("outputX",100);
            intent.putExtra("outputY",100);
            //返回data
            intent.putExtra("reture-data",true);*/
            //打开裁剪
            Intent intent = new Intent("com.android.camera.action.CROP");
            Uri uri = data.getData();
            //将图片设置给裁剪
            intent.setDataAndType(uri, "image/*");
            //设置是否可裁剪
            intent.putExtra("CROP", true);
            //设置宽高比
            intent.putExtra("aspectX", 1);
            intent.putExtra("aspectY", 1);
            //设置输出
            intent.putExtra("outputX", 100);
            intent.putExtra("outputY", 100);
            //返回data
            intent.putExtra("return-data", true);
            startActivityForResult(intent,REQUEST_PICTRUE);
        }
        //获取之后拿到的数据
        //获取剪切完的图片数据 bitmap
        if (requestCode == REQUEST_PICTRUE && resultCode == RESULT_OK) {
            Bitmap bitmap =data.getParcelableExtra("data");
            try {
                //我的工具类里的一个方法
                VerifyUtils.getInstance().saveBitmap(bitmap,PATH_FILE,50);
            } catch (IOException e) {
                e.printStackTrace();
                Log.i("TAG",e.getMessage());
            }
            //我下面的是请求网络，你们可以拿到上面的birmap直接设置给图片
            Map<String,String> map = new HashMap<>();
            map.put("uid",23051+"");
            map.put("file",PATH_FILE);
            iPersenter.startRequestFile(Apis.HEAD_IAMGE,map,HeadBean.class);
        }
        /*if(requestCode == REQUEST_PICTRUE && resultCode == getActivity().RESULT_OK){
            Bitmap bitmap = data.getParcelableExtra("data");
            //VerifyUtils.getInstance().saveBitmap(bitmap,PATH_FILE,50);
            try {
                VerifyUtils.getInstance().saveBitmap(bitmap,PATH_FILE,50);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Map<String,String> map = new HashMap<>();
            map.put("uid",26522+"");
            map.put("file",PATH_FILE);
            iPersenter.startRequestFile(Apis.HEAD_IAMGE,map,HeadBean.class);
        }*/
    }

    @Override
    public void success(Object data) {
        if(data instanceof HeadBean){
            VerifyUtils.getInstance().toast(((HeadBean) data).getMsg());
        }
    }

    @Override
    public void failed(String error) {
        VerifyUtils.getInstance().toast(error);
    }
}
