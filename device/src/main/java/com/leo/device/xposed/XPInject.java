package com.leo.device.xposed;

import android.util.Base64;
import android.util.Log;

import com.leo.device.ReportUtil;
import com.orhanobut.logger.Logger;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <p>Date:2020/5/14.11:11 AM</p>
 * <p>Author:leo</p>
 * <p>Desc:</p>
 */
public class XPInject {

    static byte checkKeyWordInject(Class clazz, String filedName, String keywordReg) {
        byte result = 0;
        Set keySet;
        try {
            Field field = clazz.getDeclaredField(filedName);
            field.setAccessible(true);
            keySet = ((HashMap) field.get(clazz)).keySet();
        } catch (Throwable e) {
            keySet = null;
        }
        final List<String> l = new ArrayList<>();
        if (keySet != null && !keySet.isEmpty()) {
            for (Object next : keySet) {
                if (next == null) {
                    continue;
                }
                String str = next.toString().toLowerCase();
                Pattern pattern = Pattern.compile(keywordReg.toLowerCase());
                Matcher matcher = pattern.matcher(str);
                if (matcher.find()) {
                    Logger.e("发现hook信息 %s" , str);
                    l.add(str);
                    result += 1;
                }

            }
        }

        if (result > 0) {
            Logger.e("被Hook 点 %s" , String.valueOf(result));
            Executors.newSingleThreadExecutor().submit(new Runnable() {
                @Override
                public void run() {
                        ReportUtil.reportHookMsg(l);
                }
            });
        }
        return result;
    }


    /**
     * 反制Xposed
     */
    public void rejectXP() {
        try {
            Field v0_1 = ClassLoader.getSystemClassLoader()
                    .loadClass("de.robv.android.xposed.XposedBridge")
                    .getDeclaredField("disableHooks");
            v0_1.setAccessible(true);
            v0_1.set(null, true);
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
}
