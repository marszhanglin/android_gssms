/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * 
 */
package net.evecom.android.gps_service;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Random;

import net.evecom.android.util.HttpUtil;
import net.evecom.android.util.ShareUtil;

import org.apache.http.client.ClientProtocolException;

import android.app.IntentService;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;

/**
 * 
 * 2014-7-22下午4:34:26 类StatusService
 * 
 * @author Mars zhang
 * 
 */
public class StatusService extends IntentService {
    /** MemberVariables */
    private String value = "";

    /** 定位客户端 */
    LocationClient mLocClient;
    /** 定位客户端参数 */
    LocationClientOption option;
    /** 经纬度 */
    BDLocation locData = null;

    // private int i=0;
    // /***/
    // private long time_num=1;
    /**
     * Intentservice 必须要有一个无参的构造函数
     */
    public StatusService() {

        super("yyyyyyyy");
        System.out.println("StatusService");

    }

    public StatusService(String name) {

        super("yyyyyyyy");
        // System.out.println("StatusService");

    }

    @Override
    protected void onHandleIntent(Intent intent) {
        System.out.println("onHandleIntent");
        // value = intent.getStringExtra("a");
        new MessageThread().start();

    }

    @Override
    public IBinder onBind(Intent intent) {
        System.out.println("onBind");
        return super.onBind(intent);
    }

    @Override
    public void onCreate() {
        int timeToSleep = Integer.parseInt(ShareUtil.getString(getApplicationContext(), "GPS_TRACK", "TIME", "120000"));
        timeToSleep = timeToSleep;
        // System.out.println(timeToSleep);
        mLocClient = new LocationClient(getApplicationContext());
        option = new LocationClientOption();
        option.setScanSpan(2000); // 设置扫描间隔，单位是毫秒
        option.setOpenGps(true);// 打开gps
        option.setCoorType("wgs84"); // 设置坐标类型 gcj02国测局经纬
        // bd09ll表示百度经纬度坐标，bd09mc表示百度墨卡托坐标，gcj02表示经过国测局加密的坐标，wgs84表示gps获取的坐标

        // option.setPoiNumber(5);
        option.setPoiExtraInfo(false);
        mLocClient.setLocOption(option);
        mLocClient.registerLocationListener(new BDLocationListener() {
            @Override
            public void onReceivePoi(BDLocation location) {
            }

            @Override
            public void onReceiveLocation(final BDLocation location) {
                new Thread() {
                    public void run() {
                        locData = location;
                        System.out.println(location.getLatitude() + "------------" + location.getLongitude());
                        // SharedPreferences sp = getApplicationContext()
                        // .getSharedPreferences("GPS_TRACK", MODE_PRIVATE);
                        // // 存入数据
                        // Editor editor = sp.edit();
                        // editor.putString("GPS_latitude",
                        // "" + location.getLatitude());
                        // editor.putString("GPS_longitude",
                        // "" + location.getLongitude());
                        // editor.commit();
                    }
                }.start();

            }
        });
        mLocClient.start();
        System.out.println("onCreate");
        super.onCreate();
    }

    @Override
    public void onStart(Intent intent, int startId) {
        System.out.println("onStart");
        super.onStart(intent, startId);
    }

    @Override
    public void onDestroy() {
        mLocClient.stop();
        super.onDestroy();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        System.out.println("onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void setIntentRedelivery(boolean enabled) {
        super.setIntentRedelivery(enabled);
        System.out.println("setIntentRedelivery");
    }

    /**
     * 开启一个线程每隔 秒向服务器发送一次请求
     * 
     * @author Mars zhang
     * 
     */
    private class MessageThread extends Thread {
        // 运行状态
        /** MemberVariables */
        public boolean isRunning = true;

        @Override
        public void run() {
            try {
                while (true) {
                    long timeToSleep = Long.parseLong(ShareUtil.getString(getApplicationContext(), "GPS_TRACK", "TIME",
                            "120000"));
                    int rand = new Random().nextInt(20000);
                    timeToSleep = timeToSleep + rand;
                    // SharedPreferences
                    // sp=getApplicationContext().getSharedPreferences("GPS_TRACK",
                    // getApplicationContext().MODE_PRIVATE);
                    // Editor editor=sp.edit();
                    // editor.putString("show", timeToSleep+"-----"+(++i));
                    // editor.commit();
                    Thread.sleep(2000);
                    if (null != locData) {
                        if ((locData.getLatitude() + "").contains("E")) {
                            break;
                        }
                        String strURL = HttpUtil.BASE_URL + "teventAndroid/GPSTRACK";
                        String entity_str = "LONGITUDE=" + locData.getLongitude() + "&LATITUDE="
                                + locData.getLatitude() + "&USER_ID="
                                + ShareUtil.getString(getApplicationContext(), "SESSION", "EMPID", "1") + "&USER_NAME="
                                + ShareUtil.getString(getApplicationContext(), "SESSION", "EMPNAME", "系统管理员");
                        // String
                        // entity_str="Locus.LONGITUDE="+locData.getLongitude()
                        // +"&Locus.LATITUDE="+locData.getLatitude()
                        // +"&Locus.USER_ID="+ShareUtil.getString(getApplicationContext(),
                        // "SESSION", "EMPID", "1")
                        // +"&Locus.USER_NAME="+ShareUtil.getString(getApplicationContext(),
                        // "SESSION", "EMPNAME", "系统管理员");
                        String rtn = connServerForResultPost(strURL, entity_str);
                        System.out
                                .println(locData.getLongitude() + "-------" + rtn + "-------" + locData.getLatitude());
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * connServerForResultPost
     * 
     * @param strUrl
     * @return
     * @throws IOException
     * @throws ClientProtocolException
     */
    private String connServerForResultPost(String strUrl, String entity_str) throws ClientProtocolException,
            IOException {
        String strResult = "";
        URL url = new URL(strUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        byte[] entity = entity_str.getBytes();
        conn.setConnectTimeout(5000);
        conn.setDoOutput(true);
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        conn.setRequestProperty("Content-Length", String.valueOf(entity.length));
        conn.getOutputStream().write(entity);
        if (conn.getResponseCode() == 200) {
            InputStream inputstream = conn.getInputStream();
            StringBuffer buffer = new StringBuffer();
            byte[] b = new byte[4096];
            for (int n; (n = inputstream.read(b)) != -1;) {
                buffer.append(new String(b, 0, n));
            }
            strResult = buffer.toString();

        }
        return strResult;
    }

    /** 土司 */
    private void toast(String strMsg, int L1S0) {
        Toast.makeText(getApplicationContext(), strMsg, L1S0).show();
    }
}
