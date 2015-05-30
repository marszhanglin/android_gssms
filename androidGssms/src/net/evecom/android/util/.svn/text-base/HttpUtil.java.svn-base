/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * 
 */
package net.evecom.android.util;

import java.io.IOException;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * 2014-5-28 ÉÏÎç9:05:27 HttpUtil¹¤¾ßÀà
 * 
 * @author Mars zhang
 * 
 */
public class HttpUtil {
    /** BASE_URL */
    // public static final String BASE_URL = "http://192.168.3.15:8080/gssms/";
    // public static final String BASE_URL = "http://112.5.137.194/gssms/";
    /* 110.85.58.153:7321 192.168.200.160:8080 110.85.58.153:7321 */
    // public static final String BASE_URL =
    // "http://192.168.200.160:8080/gssms/";
    /**BASE_URL*/
    public static final String BASE_URL = "http://110.85.58.153:7321/gssms/";
    // public static final String BASE_URL = "http://112.5.137.194/gssms/";
    /** BASE_PC_URL */
    // public static final String BASE_PC_URL =
    // "http://192.168.200.160:8080/gssms/";
    // public static final String BASE_PC_URL = "http://112.5.137.194/gssms/";
    public static final String BASE_PC_URL = "http://110.85.58.153:7321/gssms/";
    /** UPDATE_VERSION_XML */
    public static final String UPDATE_VERSION_XML = "gssms_update_android_version.xml";
    // http://localhost/gssms/mobile/buildingController/login?loginname=sysadmin&pwd=888888
    /** ·Ö¸ô·û */
    public static String DELIMITER = "@_2_";

    public static HttpGet getHttpGet(String url) {
        HttpGet request = new HttpGet(url);
        return request;
    }

    public static HttpPost getHttpPost(String url) {
        HttpPost request = new HttpPost(url);
        return request;
    }

    public static String getPageSize(Context context) {
        if (null == context) {
            return "15";
        }
        SharedPreferences sp = context.getSharedPreferences("PageSize", context.MODE_PRIVATE);
        sp.getString("pagesize", "15");
        return sp.getString("pagesize", "15");
    }

    public static HttpResponse getHttpResponse(HttpGet request) throws ClientProtocolException, IOException {
        HttpResponse response = new DefaultHttpClient().execute(request);
        return response;
    }

    public static HttpResponse getHttpResponse(HttpPost request) throws ClientProtocolException, IOException {
        HttpResponse response = new DefaultHttpClient().execute(request);
        return response;
    }

    public static String queryStringForPost(String url) {
        HttpPost request = HttpUtil.getHttpPost(url);// HttpPost request = new
                                                     // HttpPost(url);return
                                                     // request;
        String result = null;
        try {
            HttpResponse response = HttpUtil.getHttpResponse(request);// ·¢ËÍpostÇëÇó
                                                                      // HttpResponse
                                                                      // response
                                                                      // = new
                                                                      // DefaultHttpClient().execute(request);
            if (response.getStatusLine().getStatusCode() == 200) {
                result = EntityUtils.toString(response.getEntity());
                return result;
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
            result = "ÍøÂçÒì³££¡";
            return result;
        } catch (IOException e) {
            e.printStackTrace();
            result = "ÍøÂçÒì³££¡";
            return result;
        }
        return null;
    }

    public static String queryStringForPost(HttpPost request) {
        String result = null;
        try {
            HttpResponse response = HttpUtil.getHttpResponse(request);
            if (response.getStatusLine().getStatusCode() == 200) {
                result = EntityUtils.toString(response.getEntity());
                return result;
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
            result = "ÍøÂçÒì³££¡";
            return result;
        } catch (IOException e) {
            e.printStackTrace();
            result = "ÍøÂçÒì³££¡";
            return result;
        }
        return null;
    }

    public static String queryStringForGet(String url) {
        HttpGet request = HttpUtil.getHttpGet(url);
        String result = null;
        try {
            HttpResponse response = HttpUtil.getHttpResponse(request);
            if (response.getStatusLine().getStatusCode() == 200) {
                result = EntityUtils.toString(response.getEntity());
                return result;
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
            result = "ÍøÂçÒì³££¡";
            return result;
        } catch (IOException e) {
            e.printStackTrace();
            result = "ÍøÂçÒì³££¡";
            return result;
        }
        return null;
    }
}
