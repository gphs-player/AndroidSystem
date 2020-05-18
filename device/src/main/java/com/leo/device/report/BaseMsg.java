package com.leo.device.report;

import android.os.Build;
import android.text.TextUtils;

import com.leo.device.BuildConfig;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * <p>Date:2020/5/15.2:13 PM</p>
 * <p>Author:leo</p>
 * <p>Desc:</p>
 */
public abstract class BaseMsg<T> implements IReport {

    private T mReportValue;


    BaseMsg(T t) {
        mReportValue = t;
    }

    public String getTitle() {
        return "### <font color=#FF0000 size=4 face=\"黑体\">" + titleContent() + " </font>  \n";
    }

    protected abstract String titleContent();

    protected abstract String createBody(T t);


    private String getBaseInfo() {
        StringBuilder sb = new StringBuilder();
        //上报时间
        sb.append(appendPair("时间 : ", SimpleDateFormat.getDateTimeInstance().format(new Date())));
        //APP版本名及版本号
        sb.append(appendPair("APP版本 : ", BuildConfig.VERSION_NAME + "--" + BuildConfig.VERSION_CODE));
        //Android版本号
        sb.append(appendPair("OS 版本 : ", Build.VERSION.RELEASE + "--Api: " + Build.VERSION.SDK_INT));
        //手机制造商
        sb.append(appendPair("品牌 : ", nullOr(Build.MANUFACTURER)));
        //手机型号
        sb.append(appendPair("机型 : ", nullOr(Build.MODEL)));
        //CPU框架
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            sb.append(appendPair("CPU ABI : ", Build.SUPPORTED_ABIS[0]));
        } else {
            sb.append(appendPair("CPU ABI : ", Build.CPU_ABI));
        }
        sb.append(appendPair("appId : ", ""));
        sb.append(appendPair("appBuild : ", ""));
        sb.append(appendPair("appVersion : ", ""));
        sb.append(appendPair("deviceId : ", ""));
        sb.append(appendPair("phoneType : ", ""));
        sb.append(appendPair("phoneRelease : ", ""));
        sb.append(appendPair("phoneBrand : ", ""));
        sb.append(appendPair("phoneModel : ", ""));
        sb.append(appendPair("uid : ", ""));
        sb.append(appendPair("token : ", "")).append("\n\n");
        return sb.toString();
    }

    private String appendPair(String key, String value) {
        return subtitle(key) + value + "\n";
    }

    private String subtitle(String txt) {
        return  ">##### <font color=#000000 size=3 face=\"楷体\">" + txt +"</font> ";
    }

    private String nullOr(String txt){
        if (TextUtils.isEmpty(txt)){
            return "获取失败";
        }else {
            return txt;
        }
    }

    @Override
    public String toReportString() {
        String title = getTitle();
        String baseInfo = getBaseInfo();
        String body = createBody(mReportValue);
        return title + baseInfo + body;
    }
}
