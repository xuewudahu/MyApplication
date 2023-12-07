package com.kuaidihelp.myapplication;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class MainActivity extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent serviceIntent = new Intent(this, MyService.class);
        startForegroundService(serviceIntent);
//服务器提交的代码

     //本地写的需要提交的代码
     //   Log.e("wxw","---------"+ encodeForURL("http://192.168.0.101:8080/chukuyi/device/updateDeviceInfo.do?token=cc1323a2efe0422db65f28e532938cb61693360937367&device={\"userId\": \"00309d73c43d4b3fb628168e7680646f\",\"sn\":\"123456\",\"availableDeviceCapacity\":\"20G\",\"bluetoothMAC\":\"33:44:55:52:74\",\"deviceCapacity\":\"32G\",\"iccid\":\"\", \"imei\": \"8651245622500001\",\"isActivationLockEnabled\":true,\"isDeviceLocatorServiceEnabled\":true,\"meid\":\"A65124562250001\",\"model\":\"m1\",\"modelName\":\"W201\",\"wifiMac\":\"33:44:55:34:57\",\"location\":\"浙江省杭州市西湖区 万塘路252号计量大厦409\"}"));

    }



    public  String encodeForURL(String input) {
        try {
            Log.e("wxw", "----" + input);
            String encoded = URLEncoder.encode(input, "UTF-8");
            encoded = encoded.replace("+", "%20");
            return encoded;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return input;
        }
    }

}
