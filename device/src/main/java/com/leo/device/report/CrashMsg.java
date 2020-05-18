package com.leo.device.report;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

/**
 * <p>Date:2020/5/15.2:15 PM</p>
 * <p>Author:leo</p>
 * <p>Desc:</p>
 */
public class CrashMsg extends BaseMsg<Throwable> {


    private CrashMsg(Throwable throwable) {
        super(throwable);
    }

    public static CrashMsg create(Throwable t){
        return new CrashMsg(t);
    }

    @Override
    public String titleContent() {
        return "CRASH ERROR";
    }

    @Override
    public String createBody(Throwable throwable) {
        Writer writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);
        printWriter.write("\r\n");
        printWriter.write("Crash Stack Trace\r\n");
        throwable.printStackTrace(printWriter);
        Throwable cause = throwable.getCause();
        while (cause != null) {
            printWriter.write("\r\n");
            cause.printStackTrace(printWriter);
            cause = cause.getCause();
        }
        printWriter.close();
        return writer.toString();
    }
}
