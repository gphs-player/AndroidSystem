package com.leo.device;

import com.leo.device.report.CrashMsg;
import com.leo.device.report.HookMsg;
import com.leo.device.report.BaseMsg;
import com.leo.device.report.IReport;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

/**
 * <p>Date:2020/5/15.11:39 AM</p>
 * <p>Author:leo</p>
 * <p>Desc:</p>
 */
public class ReportUtil {

    private static final String DD_TOKEN = BuildConfig.DEBUG ?
            "https://oapi.dingtalk.com/robot/send?access_token=b1f29c67cb522adcf022c8aef6cf3aa1028071347405f7eb024b581aea7f17f0"
            : "https://oapi.dingtalk.com/robot/send?access_token=b1f29c67cb522adcf022c8aef6cf3aa1028071347405f7eb024b581aea7f17f0";


    public static void reportThrowable(Throwable throwable) {
        reportActual(CrashMsg.create(throwable));
    }

    public static void reportHookMsg(List<String> list) {
        reportActual(HookMsg.create(list));
    }



    private static void reportActual(IReport msg) {
        System.out.println(msg.toReportString());
        if (true) return;
        HttpsURLConnection conn = null;
        BufferedReader bufferedReader = null;
        PrintWriter printWriter = null;
        try {
            JSONObject ddObj = new JSONObject();
            ddObj.put("msgtype", "markdown");
            JSONObject errMsg = new JSONObject();
            errMsg.put("title", "Warning");
            errMsg.put("text", msg.toReportString());
            ddObj.put("markdown", errMsg.toString());
            String content = ddObj.toString();
            URL url = new URL(DD_TOKEN);
            conn = (HttpsURLConnection) url.openConnection();
            conn.setRequestProperty("Content-Type", "application/json");
//            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setRequestProperty("Charset", "UTF-8");
            conn.setRequestMethod("POST");
            conn.connect();
            printWriter = new PrintWriter(conn.getOutputStream());
            printWriter.write(content);
            printWriter.flush();
            bufferedReader = new BufferedReader(new InputStreamReader(
                    conn.getInputStream(), "UTF-8"));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                conn.disconnect();
            }

            closeQuietly(printWriter);
            closeQuietly(bufferedReader);
        }
    }

    private static void closeQuietly(final Closeable closeable) {
        try {
            if (closeable != null) {
                closeable.close();
            }
        } catch (final IOException ioe) {
            // ignore
        }
    }
}
