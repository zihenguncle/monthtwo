package com.example.month.tool;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;

import com.example.month.app.MyApplication;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class VerifyUtils {

    private static VerifyUtils instance;
    public static VerifyUtils getInstance(){
        if(instance == null){
            synchronized (VerifyUtils.class){
                instance = new VerifyUtils();
            }
        }
        return instance;
    }

    //判断是否有网络
    public boolean isNetworkConnected() {
        if (MyApplication.getApplicationContent() != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) MyApplication.getApplicationContent()
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable();
            }
        }
        return false;
    }

    public boolean isPhone(String phone){
        Pattern pattern=Pattern.compile("^[1][3,4,5,7,8][0-9]{9}$");
        Matcher matcher = pattern.matcher(phone);
        return matcher.matches();
    }
    public void toast(String s){
        Toast.makeText(MyApplication.getApplicationContent(),s,Toast.LENGTH_LONG).show();
    }

    public void saveBitmap(Bitmap bitmap,String path,int quality) throws IOException{
        /*String dir = path.substring(0, path.lastIndexOf("/"));
        File dirFile = new File(dir);
        if(!dirFile.exists() || !dirFile.isDirectory()){
            try {
                if(!dirFile.mkdirs()){
                    return;
                }
            }catch (Exception e){

            }
        }
        FileOutputStream out;
        try {
            out = new FileOutputStream(path);
            if(bitmap.compress(Bitmap.CompressFormat.PNG,quality,out)){
                out.flush();
                out.close();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/
        String dir = path.substring(0, path.lastIndexOf("/"));
        File dirFile = new File(dir);
        if (!dirFile.exists() || !dirFile.isDirectory()) {
            try {
                if (!dirFile.mkdirs()) {
                    return;
                }
            } catch (Exception e) {
                Log.e("TAG", e.getMessage());
            }

        }
        FileOutputStream out;
        try {
            out = new FileOutputStream(path);
            if (bitmap.compress(Bitmap.CompressFormat.PNG, quality, out)) {
                out.flush();
                out.close();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Log.e("TAG", e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
