package com.leo.device;

import android.content.Context;


import java.lang.Thread.UncaughtExceptionHandler;
import java.util.concurrent.Executors;


public class AppCrashHandlerTest implements UncaughtExceptionHandler {
    public final String TAG = this.getClass().getSimpleName();


    // 系统默认的UncaughtException处理类
    private Thread.UncaughtExceptionHandler mDefaultHandler;


    // 程序的Context对象
    private Context mContext;

    /**
     * 保证只有一个CrashHandler实例
     */
    private AppCrashHandlerTest() {

    }

    static class H {
        static AppCrashHandlerTest INSTANCE = new AppCrashHandlerTest();
    }

    /**
     * 获取CrashHandler实例 ,单例模式
     */
    public static AppCrashHandlerTest getInstance() {
        return H.INSTANCE;
    }

    /**
     * 初始化
     *
     * @param context
     */
    public void init(Context context) {
        mContext = context;
        // 获取系统默认的UncaughtException处理器
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        // 设置该CrashHandler为程序的默认处理器
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    @Override
    public void uncaughtException(Thread thread, final Throwable ex) {
        Executors.newSingleThreadExecutor().submit(new Runnable() {
            @Override
            public void run() {
                ReportUtil.reportThrowable(ex);
            }
        });

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        mDefaultHandler.uncaughtException(thread, ex);
    }

}