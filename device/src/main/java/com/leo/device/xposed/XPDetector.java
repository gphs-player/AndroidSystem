package com.leo.device.xposed;

import android.content.Context;
import android.util.Base64;
import android.util.Log;


import com.orhanobut.logger.Logger;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import dalvik.system.PathClassLoader;

/**
 * <p>Date:2020/5/13.11:28 AM</p>
 * <p>Author:leo</p>
 * <p>Desc:
 *
 *
 *
 * </p>
 */
public class XPDetector {

    private static final String TAG = "Hook Detection:";


    /**
     * 略过PackageManager检测
     *
     * @return Xposed是否安装
     */
    public static boolean isXposedInstalled() {
        try {
            throw new Exception("hook");
        } catch (Exception localException) {
            localException.printStackTrace();
            StackTraceElement[] arrayOfStackTraceElement = localException.getStackTrace();
            for (StackTraceElement stackTraceElement : arrayOfStackTraceElement) {
                if (stackTraceElement.getClassName().contains("de.robv.android.xposed")) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 能加载到XposedHelpers类证明xposed安装了
     */
    private static boolean isXposedExists() {
        String XPOSED_HELPERS = "de.robv.android.xposed.XposedHelpers";
        try {
            ClassLoader
                    .getSystemClassLoader()
                    .loadClass(XPOSED_HELPERS)
                    .newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
            System.out.println("checkXposed:" + true);
            return true;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            System.out.println("checkXposed:" + true);
            return true;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("checkXposed:" + false);
            return false;
        }
        return false;
    }


    /**
     * 检查APP下加载到内存的so文件以及jar文件
     * <br>
     * 三大Hook框架的检测
     *   <ul>
     *       <li>Xposed</li>
     *       <li>Frida(TODO)</li>
     *       <li>Substrate</li>
     *   </ul>
     * <br>
     *   检查步骤
     * <ul>
     *     <li>XposedBridge.jar在新版本已经被替换为implementation的引用方式</li>
     *     <li>Substrate还是需要导入libsubstrate.so库的</li>
     *     <li>带有敏感词的so文件</li>
     * </ul>
     */
    public static boolean isSoLibIllegal() {
        Set<String> libraries = new HashSet<>();
        String mapsFilename = "/proc/" + android.os.Process.myPid() + "/maps";
        try {
            BufferedReader reader = new BufferedReader(new FileReader(mapsFilename));
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.endsWith(".so") || line.endsWith(".jar")) {
                    int n = line.lastIndexOf(" ");
                    libraries.add(line.substring(n + 1));
                }
            }
            for (String library : libraries) {
                if (library.contains("com.saurik.substrate")) {
                    return true;
                }
                //老版本xposed
                if (library.contains("XposedBridge.jar")) {
                    return true;
                }
                if (sensitive(library)) {
                    return true;
                }
            }
            reader.close();
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }
        return false;
    }

    // hook|hack|inject
    private static boolean sensitive(String library) {
        String soNames = new String(Base64.decode("aG9va3xoYWNrfGluamVjdA==", 0));
        Pattern pattern = Pattern.compile(soNames);
        Matcher matcher = pattern.matcher(library);
        return matcher.find();
    }


    /**
     * XposedHelpers内部有三个关键字段methodCache，fieldCache和constructorCache，都是HashMap类型
     * 尝试反射加载XposedHelpers类，如果加载不到说明Xpose未安装或者没有生效module
     * 如果加载成功则反射相关字段，遍历敏感信息判断是否被hook
     */
    public static boolean hkSelf(Context context,String target) {
        int check;
        PathClassLoader pathClassLoader = new PathClassLoader(context.getPackageCodePath(), ClassLoader.getSystemClassLoader());
        try {
            //de.robv.android.xposed.XposedHelpers
            Class<?> loadClass = pathClassLoader.loadClass(new String(Base64.decode("ZGUucm9idi5hbmRyb2lkLnhwb3NlZC5YcG9zZWRIZWxwZXJz", 0)));

            //methodCache
            check = XPInject.checkKeyWordInject(loadClass, new String(Base64.decode("bWV0aG9kQ2FjaGU=", 0)), target);
            if (check > 0) return true;

            //fieldCache
            check = XPInject.checkKeyWordInject(loadClass, new String(Base64.decode("ZmllbGRDYWNoZQ==", 0)), target);
            if (check > 0) return true;

            //constructorCache
            check = XPInject.checkKeyWordInject(loadClass, new String(Base64.decode("Y29uc3RydWN0b3JDYWNoZQ==", 0)), target);
            if (check > 0) return true;
        } catch (Throwable e) {
            Logger.e(e,"ERROR","");
        }
        Logger.d("未发现hook信息");
        return false;
    }

}
