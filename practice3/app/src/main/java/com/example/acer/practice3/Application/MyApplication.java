package com.example.acer.practice3.Application;

import android.app.Application;

import org.xutils.x;

/**
 * Created by 19216 on 2019/1/10.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);
    }
}
