/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * 
 */
package net.evecom.android.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;

/**
 * 
 * 2014-7-22下午4:31:41 类MyHttpRequest
 * 
 * @author Mars zhang
 * 
 */
public class MyHttpRequest {
    /**
     * 以get方式向用户发送请求
     */
    public boolean sendGetRequest(String requestAddress, Map<String, String> params) throws Exception {
        StringBuilder sb = new StringBuilder(requestAddress);
        // 迭代map
        sb.append('?');
        System.out.println(params.size());
        for (Map.Entry<String, String> entry : params.entrySet()) { // URLEncoder.encode(entry.getValue(),
                                                                    // "UTF-8")
            sb.append(entry.getKey()).append("=").append("'").append(URLEncoder.encode(entry.getValue(), "UTF-8"))
                    .append("'").append("&");
        }
        sb.deleteCharAt(sb.length() - 1);
        System.out.println(sb);

        URL url = new URL(sb.toString());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");// 请求方式
        conn.setConnectTimeout(10 * 1000);// 请求的超时时间 10秒
        System.out.println(conn.getResponseCode());
        if (conn.getResponseCode() == 200) {
            System.out.println("请求成功");
            InputStream is = conn.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            System.out.println(reader.readLine());
            if ("hava".equals(reader.readLine().trim())) {
                return true;
            } else {
                return false;
            }

        }
        return false;
    }
}
