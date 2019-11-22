package com.example.acer.wuli.Application;

import android.app.Activity;
import android.app.Application;

import com.mob.MobSDK;
import com.mob.tools.proguard.ProtectedMemberKeeper;

import org.litepal.LitePal;

import java.util.LinkedList;
import java.util.List;

public class SysApplication extends Application implements ProtectedMemberKeeper {

    private List<Activity> mList = new LinkedList<Activity>();//存储要退出的activity
    private static SysApplication instance;


    // 验证码模块的注册
    protected String getAppkey() {
        return null;
    }

    protected String getAppSecret() {
        return null;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        LitePal.initialize(this);
        MobSDK.init(this, this.getAppkey(), this.getAppSecret());
    }

    public synchronized static SysApplication getInstance() {
        if (null == instance) {
            instance = new SysApplication();
        }
        return instance;
    }

    /**
     * 添加activity
     * @param activity 要添加的activity
     */
    public void addActivity(Activity activity) {
        mList.add(activity);
    }

    /**
     * 遍历activity退出程序
     */
    public void exit() {
        try {
            for (Activity activity : mList) {
                if (activity != null)
                    activity.finish();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.exit(0);
        }
    }
    public void onLowMemory() {
        super.onLowMemory();
        System.gc();
    }
}
