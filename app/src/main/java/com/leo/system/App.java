package com.leo.system;

import android.app.Application;

import com.leo.device.AppCrashHandlerTest;

/**
 * <p>Date:2020/5/15.2:20 PM</p>
 * <p>Author:leo</p>
 * <p>Desc:</p>
 */
public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        AppCrashHandlerTest.getInstance().init(getApplicationContext());
    }
}
