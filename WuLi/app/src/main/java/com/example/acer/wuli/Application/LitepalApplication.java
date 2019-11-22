package com.example.acer.wuli.Application;

import android.app.Application;

import org.litepal.LitePal;

/**
 * 初始化Litepal数据库
 */

public class LitepalApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        LitePal.initialize(this);
    }
}
