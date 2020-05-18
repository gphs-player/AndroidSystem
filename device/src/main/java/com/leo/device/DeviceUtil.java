package com.leo.device;

import android.content.Context;

import com.leo.device.root.RootChecker;
import com.leo.device.xposed.XPDetector;

/**
 * <p>Date:2020/5/14.4:12 PM</p>
 * <p>Author:leo</p>
 *
 *
 * <p>Desc:</p>
 */
public class DeviceUtil {

//    static class Holder{
//        static DeviceUtil INSTANCE = new DeviceUtil();
//    }

//    public static DeviceUtil getInstance(){
//        return Holder.INSTANCE;
//    }

    public static boolean isRooted(Context ctx){
        return RootChecker.getInstance(ctx).isRooted();
    }



    public static  boolean isXPInstalled(){
        return XPDetector.isXposedInstalled();
    }

    public static  boolean checkSOLib(){
        return XPDetector.isSoLibIllegal();
    }



}
