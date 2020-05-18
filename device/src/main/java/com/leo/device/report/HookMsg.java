package com.leo.device.report;

import java.util.List;

/**
 * <p>Date:2020/5/15.2:15 PM</p>
 * <p>Author:leo</p>
 * <p>Desc:</p>
 */
public class HookMsg extends BaseMsg<List<String>> {
    private HookMsg(List<String> strings) {
        super(strings);
    }
    public static HookMsg create(List<String> list){
        return new HookMsg(list);
    }

    @Override
    public String titleContent() {
        return "HOOK WARNING";
    }

    @Override
    public String createBody(List<String> list) {
        if (list == null || list.isEmpty()) return "";
        StringBuilder sb = new StringBuilder();
        sb.append("Hook Spot:\r\n");
        for (String item : list) {
            sb.append("\r\n");
            sb.append(item);
        }
        return sb.toString();
    }
}
