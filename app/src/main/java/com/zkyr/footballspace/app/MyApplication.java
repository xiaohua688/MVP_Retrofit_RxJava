package com.zkyr.footballspace.app;

import android.app.Activity;
import android.app.Application;
import android.support.multidex.MultiDex;

import java.util.Stack;

/**
 * Created by xiaohua on 2017/10/11.
 */

public class MyApplication extends Application {

    private Stack<Activity> activityStack;

    public static MyApplication instance;


    @Override
    public void onCreate() {
        super.onCreate();


        instance = this;
        // dex突破65535的限制
        MultiDex.install(this);
    }




    public static synchronized MyApplication getInstance() {
        return instance;
    }

    /**
     * 保存Activity的引用
     */
    public void addActivity(Activity act) {
        if (activityStack == null)
            activityStack = new Stack<>();
        activityStack.add(act);
    }

    /**
     * 关闭指定Activity的引用
     */
    public void removeActivity(Activity act) {
        if (activityStack.contains(act))
            activityStack.remove(act);
    }

    /**
     * 获取当前Activity(堆栈中的最后一个)
     */
    public Activity currentActivity() {
        Activity activity = activityStack.lastElement();
        return activity;
    }

    /**
     * 退出App
     */
    public void exitApp() {
        if (activityStack != null) {
            synchronized (activityStack) {
                for (Activity act : activityStack) {
                    act.finish();
                }
            }
        }
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);
    }
}
