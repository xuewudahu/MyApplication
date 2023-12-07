package com.kuaidihelp.myapplication;

import android.app.Application;
import android.os.Build;

import androidx.annotation.RequiresApi;




public class MyApplication extends Application {
    @RequiresApi(api = Build.VERSION_CODES.Q)
    public void onCreate() {
        super.onCreate();



    }
}
