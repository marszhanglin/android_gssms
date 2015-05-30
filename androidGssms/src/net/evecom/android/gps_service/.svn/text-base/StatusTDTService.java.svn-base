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
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.PowerManager.WakeLock;
import android.os.Vibrator;
import android.widget.Toast;

import com.tianditu.android.maps.GeoPoint;
import com.tianditu.android.maps.MapView;
import com.tianditu.android.maps.MyLocationOverlay;

/**
 * 
 * 2014-7-22下午4:34:26 类StatusService
 * 
 * @author Mars zhang
 * 
 */
public class StatusTDTService extends IntentService {
    // /** 定位客户端 */
    // LocationClient mLocClient;
    // /** 定位客户端参数 */
    // LocationClientOption option;
    // /** 经纬度 */
    // BDLocation locData = null;
    // // private int i=0;
    // // /***/
    // // private long time_num=1;
    /** MemberVariables */
    private MyLocationOverlay mMyLocation = null;
    /** MemberVariables */
    private Location location_su = null;
    /** MemberVariables */
    private WakeLock wakeLock = null;
    /** MemberVariables */
    private Vibrator mVibrator01 = null;

    /**
     * Intentservice 必须要有一个无参的构造函数
     */
    public StatusTDTService() {

        super("yyyyyyyy");
        // System.out.println("StatusService");

    }

    public StatusTDTService(String name) {

        super("yyyyyyyy");
        // System.out.println("StatusService");

    }

    @Override
    protected void onHandleIntent(Intent intent) {
        // System.out.println("onHandleIntent");
        // value = intent.getStringExtra("a");
        new MessageThread().start();

    }

    @Override
    public IBinder onBind(Intent intent) {
        // System.out.println("onBind");
        return super.onBind(intent);
    }

    @Override
    public void onCreate() {
        /**
         * 开启休眠锁
         */
        // PowerManager pm =
        // (PowerManager)getApplicationContext().getSystemService(Context.POWER_SERVICE);
        // wakeLock =
        // pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK,"net.evecom.android.gps_service.StatusTDTService");
        // wakeLock.acquire();

        // int
        // timeToSleep=Integer.parseInt(ShareUtil.getString(getApplicationContext(),
        // "GPS_TRACK", "TIME", "120000") );
        // timeToSleep=timeToSleep;
        /**
         * 天地图
         */
        mMyLocation = new MyOverlay(this, null);
        // mMyLocation.enableCompass(); //显示指南针
        // mMyLocation.enableMyLocation(); //显示我的位置
        LocationManager m_locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        /**
         * 基站
         */
        // if(m_locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)){
        // //
        // m_locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
        // // timeToSleep, 0, mMyLocation);
        // m_locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
        // timeToSleep, 0, mMyLocation);
        // }
        // else
        /**
         * gps
         */
        if (m_locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            m_locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10000, 0, mMyLocation);
        }

        /**
         * 百度地图
         */
        // System.out.println(timeToSleep);
        // mLocClient = new LocationClient(getApplicationContext());
        // option = new LocationClientOption();
        // option.setScanSpan(2000); // 设置扫描间隔，单位是毫秒
        // option.setOpenGps(true);// 打开gps
        // option.setCoorType("wgs84"); // 设置坐标类型 gcj02国测局经纬
        // // bd09ll表示百度经纬度坐标，bd09mc表示百度墨卡托坐标，gcj02表示经过国测局加密的坐标，wgs84表示gps获取的坐标
        //
        // // option.setPoiNumber(5);
        // option.setPoiExtraInfo(false);
        // mLocClient.setLocOption(option);
        // mLocClient.registerLocationListener(new BDLocationListener() {
        // @Override
        // public void onReceivePoi(BDLocation location) {
        // }
        //
        // @Override
        // public void onReceiveLocation(final BDLocation location) {
        // new Thread() {
        // public void run() {
        // locData=location;
        // System.out.println(location.getLatitude()+"------------"+location.getLongitude());
        // }
        // }.start();
        //
        // }
        // });
        // mLocClient.start();
        super.onCreate();
    }

    /**
     * 系统定位
     */
    /**
     * 系统定位监听 位置监听器LocationListener 的设置，当位置发生变化是触发onLocationChanged( )
     */
    // LocationListener onLocationChange = new LocationListener() {
    // public void onLocationChanged(Location location) {
    // System.out.println("Location Changed : (" + location.getLongitude()
    // + "," + location.getLatitude() + ")");
    // SharedPreferences sp = getApplicationContext()
    // .getSharedPreferences("GPS", MODE_PRIVATE);
    // // 存入数据
    // Editor editor = sp.edit();
    // editor.putString("GPS_latitude", "" + location.getLatitude());
    // editor.putString("GPS_longitude", "" + location.getLongitude());
    // editor.commit();
    // String strLog = String.format("onLocationChange:\r\n" +
    // "纬度:%f\r\n" +
    // "经度:%f",
    // location.getLongitude(), location.getLatitude());
    // // Toast.makeText(mCon, strLog, Toast.LENGTH_SHORT).show();
    // System.out.println(strLog);
    // }
    //
    // public void onProviderDisabled(String arg0) {
    // System.out.println("onProviderDisabled");
    // }
    //
    // public void onProviderEnabled(String arg0) {
    // System.out.println("onProviderEnabled");
    // }
    //
    // @Override
    // public void onStatusChanged(String provider, int status, Bundle extras) {
    // // TODO Auto-generated method stub
    //
    // }
    // };
    @Override
    public void onStart(Intent intent, int startId) {
        // System.out.println("onStart");
        super.onStart(intent, startId);
    }

    @Override
    public void onDestroy() {
        // mLocClient.stop();
        /**
         * 不让gps后台关闭
         */
        // Intent startServiceIntent = new Intent(MainOneActivity.this,
        // StatusTDTService.class);
        // startService(startServiceIntent);

        /**
         * 关闭休眠锁
         */
        // if (wakeLock != null) {
        // wakeLock.release();
        // wakeLock = null;
        // }
        super.onDestroy();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // System.out.println("onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void setIntentRedelivery(boolean enabled) {
        super.setIntentRedelivery(enabled);
        // System.out.println("setIntentRedelivery");
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
                // Thread.sleep(2000);
                // if(null!=locData){
                // if((locData.getLatitude()+"").contains("E")){
                // break ;
                // }
                // String strURL=HttpUtil.BASE_URL+ "teventAndroid/GPSTRACK";
                // String entity_str="LONGITUDE="+locData.getLongitude()
                // +"&LATITUDE="+locData.getLatitude()
                // +"&USER_ID="+ShareUtil.getString(getApplicationContext(),
                // "SESSION", "EMPID", "1")
                // +"&USER_NAME="+ShareUtil.getString(getApplicationContext(),
                // "SESSION", "EMPNAME", "系统管理员");
                // // String
                // entity_str="Locus.LONGITUDE="+locData.getLongitude()
                // // +"&Locus.LATITUDE="+locData.getLatitude()
                // //
                // +"&Locus.USER_ID="+ShareUtil.getString(getApplicationContext(),
                // "SESSION", "EMPID", "1")
                // //
                // +"&Locus.USER_NAME="+ShareUtil.getString(getApplicationContext(),
                // "SESSION", "EMPNAME", "系统管理员");
                // String rtn=connServerForResultPost(strURL,entity_str);
                // System.out.println(locData.getLongitude()+"-------"+rtn+"-------"+locData.getLatitude());
                // }
                // Message message = new Message();
                // message.what = 1;
                // handler1.sendMessage(message);
                /**
                 * 天地图
                 */
                try {
                    Thread.sleep(timeToSleep);
                    if (null != location_su && null != mMyLocation 
                            && !(location_su.getLongitude() + "").contains("E")) {
                        String strURL = HttpUtil.BASE_URL + "teventAndroid/GPSTRACK";
                        String entity_str = "LONGITUDE=" + location_su.getLongitude() + "&LATITUDE="
                                + location_su.getLatitude() + "&USER_ID="
                                + ShareUtil.getString(getApplicationContext(), "SESSION", "EMPID", "1") + "&USER_NAME="
                                + ShareUtil.getString(getApplicationContext(), "SESSION", "EMPNAME", "系统管理员");
                        String rtn = connServerForResultPost(strURL, entity_str);
                    }
                } catch (Exception e) {
                    Message message2 = new Message();
                    message2.what = 2;
                    handler1.sendMessage(message2);
                }

            }
        }
    }

    /**
     * handler1
     */
    private Handler handler1 = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    // mVibrator01
                    // =(Vibrator)getApplication().getSystemService(Service.VIBRATOR_SERVICE);
                    // mVibrator01.vibrate(1000);
                    toast("GPS芯片定位"
                            + "------"
                            + (null == location_su ? ("位置没变化或室内")
                                    : ("数据更新:" + location_su.getLatitude() + "--" + location_su.getLongitude())), 0);
                    break;
                case 2:
                    // mVibrator01
                    // =(Vibrator)getApplication().getSystemService(Service.VIBRATOR_SERVICE);
                    // long[] longs= new
                    // long[]{1000l,100l,1000l,100l,1000l,100l};
                    // mVibrator01.vibrate(longs, -1);
                    toast("处于离线状态 ,无法上传gps数据，请链接网络", 0);
                    break;
                default:
                    break;
            }
            super.handleMessage(msg);
        }
    };

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

    /**
     * 天地图
     */
    class MyOverlay extends MyLocationOverlay {
        public MyOverlay(Context context, MapView mapView) {
            super(context, mapView);
            // TODO Auto-generated constructor stub
        }

        /*
         * 处理在"我的位置"上的点击事件
         */
        protected boolean dispatchTap() {
            // System.out.println("dispatchTap");
            // Toast.makeText(mCon, "您点击了我的位置", Toast.LENGTH_SHORT).show();
            return true;
        }

        @Override
        public void onLocationChanged(Location location) {
            // TODO Auto-generated method stub
            super.onLocationChanged(location);
            if (location != null) {
                location_su = location;
                // String strLog = String.format("您当前的位置:\r\n" +
                // "纬度:%f\r\n" +
                // "经度:%f",
                // location.getLongitude(), location.getLatitude());
                // // Toast.makeText(mCon, strLog, Toast.LENGTH_SHORT).show();
                // System.out.println(strLog);
            }
            GeoPoint point = mMyLocation.getMyLocation();
            // if(point != null)
            // mMapView.getController().animateTo(point);
        }

    }
}
