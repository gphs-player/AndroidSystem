package com.leo.system;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.leo.device.DeviceUtil;
import com.leo.device.ReportUtil;
import com.leo.device.xposed.XPDetector;
import com.leo.device.emulator.EmulatorCheckCallback;
import com.leo.device.emulator.EmulatorCheckUtil;
import com.leo.ui.ConstraintLayoutActivity;
import com.leo.ui.LayoutInflateActivity;
import com.leo.ui.RecyclerActivity;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Logger.addLogAdapter(new AndroidLogAdapter());
        if (DeviceUtil.isRooted(getApplicationContext())) {
            Logger.d("RootChecker Device Root");
        } else {
            Logger.d("RootChecker Device Not Root");
        }
    }

    public void constraint(View view) {
        Intent intent = new Intent(this, ConstraintLayoutActivity.class);
        startActivity(intent);
    }

    public void recycler(View view) {
        Intent intent = new Intent(this, RecyclerActivity.class);
        startActivity(intent);
    }

    public void inflate(View view) {
        Intent intent = new Intent(this, LayoutInflateActivity.class);
        startActivity(intent);
    }

    public void root(View view) {
        if (DeviceUtil.isRooted(this)) {
            Logger.d("isRooted");
        } else {
            Logger.d("not Rooted");
        }
        Toast.makeText(this, getMsg(), Toast.LENGTH_SHORT).show();
    }

    private String getMsg() {
        return "Hello";
    }


    public void xpose(View view) {
        Logger.d("Install Xposed:" + XPDetector.isXposedInstalled());
    }

    public void monitor(View view) {
        boolean isEmulator = EmulatorCheckUtil.getSingleInstance().readSysProperty(this, new EmulatorCheckCallback() {
            @Override
            public void findEmulator(String emulatorInfo) {
                System.out.println(emulatorInfo);
            }
        });
        Logger.d("isEmulator: " + isEmulator);
    }

    public void hook(View view) {
        boolean getmsg = XPDetector.hkSelf(this, "leo|getMsg|stm");
    }

    public void ddmsg(View view) {
        int x = 5/0;

    }
}
