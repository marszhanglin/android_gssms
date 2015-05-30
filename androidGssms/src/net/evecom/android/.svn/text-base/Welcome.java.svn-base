/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * 
 */
package net.evecom.android;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.regex.Pattern;

import net.evecom.android.bean.EOS_DICT_ENTRY;
import net.evecom.android.util.AppInfoUtil;
import net.evecom.android.util.HttpUtil;
import net.evecom.android.util.PhoneUtil;
import net.evecom.android.util.UpdateInfo;
import net.evecom.android.util.utilbean.XmlParser;
import net.tsz.afinal.FinalDb;
import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.HttpHandler;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.util.EntityUtils;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.PendingIntent.CanceledException;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Resources.NotFoundException;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Xml;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.LocationData;
import com.google.gson.stream.JsonReader;
import com.tianditu.android.maps.MapView;
import com.tianditu.android.maps.MyLocationOverlay;

/**
 * 列出所有隐患（有检查记录的）进行编辑 2014-7-22下午3:32:29 类Welcome
 * 
 * @author Mars zhang
 * 
 */
public class Welcome extends Activity {
    /** 成员变量 */
    private static final int MESSAGETYPE_01 = 0x0001;// 用于判断是否发送成功
    /** 成员变量 */
    private static final int MESSAGETYPE_02 = 0x0002;
    /** 成员变量 */
    protected static final int LOAD_MAIN = 1;
    /** 成员变量 */
    protected static final int SHOW_UPDATA_DIALOG = 2;
    /** 成员变量 */
    protected static final int SERVER_URL_ERROR = 3;
    /** 成员变量 */
    protected static final int NETWORK_ERROR = 4;
    /** 成员变量 */
    protected static final int XML_PARSE_ERROR = 5;
    /** 成员变量 */
    protected static final int SERVER_ERROR = 6;
    /** 成员变量 */
    private ProgressDialog progressDialog = null;
    /** 进度提示框 */
    private AlertDialog dialogPress;
    /** 成员变量 */
    String temp = "";
    /** 用户名 */
    private EditText userNmaeEditText;
    /** 密码 */
    private EditText passwordEditText;// welcome_password_edit
    // 定位相关
    /** 定位客户端 */
    LocationClient mLocClient;
    /** 定位客户端参数 */
    LocationClientOption option;
    /** 经纬度 */
    LocationData locData = null;
    /** 定位客户端 */
    MyLocationOverlay mMyLocation = null;
    /** 经纬度 */
    Location location_su = null;
    /** 记住密码 */
    private CheckBox jzmmCheckBox;
    /** 自动登入 */
    private CheckBox zddrCheckBox;
    /** 记住密码1 0 */
    private String isJZMM;
    /** 自动登入1 0 */
    private String isZDDR;
    /** xml */
    private SharedPreferences spPhone;
    /** updateInfo */
    private UpdateInfo updateInfo;
    /** 事件类型 */
    private String eventType_json = "";
    /** 数据库 */
    FinalDb db = null;
    /** 字典类 */
    private List<EOS_DICT_ENTRY> dict_ENTRYs = new ArrayList<EOS_DICT_ENTRY>();
    /** MemberVariables */
    private String sss = "";
    /** MemberVariables */
    private Calendar calendar = null;
    /** MemberVariables */
    private LocationManager m_locationManager = null;
    /** MemberVariables */
    private String s[] = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome);
        initView();
        askForOpenGPS();
        // toggleGPS();
        // /**
        // * 判断gps是否已打开
        // */
        // gpsEnabled= Settings.Secure.isLocationProviderEnabled(
        // getContentResolver(), LocationManager.GPS_PROVIDER );
        // if(!gpsEnabled){
        // Settings.Secure.setLocationProviderEnabled( getContentResolver(),
        // LocationManager.GPS_PROVIDER, true);
        // }
        // Settings.Secure.setLocationProviderEnabled( getContentResolver(),
        // LocationManager.GPS_PROVIDER, false );
        // initLocation();//BaiDu
        initLocationTDT();// TMAP
        isAutoLogin();
        getGetGPSTime();
        getEventTypeFromDictAndParse2ListAndSave2DataBase();
        flow();
    }

    /**
     * 请求打开gps
     */
    private void askForOpenGPS() {
        boolean gpsEnabled = Settings.Secure.isLocationProviderEnabled(getContentResolver(),
                LocationManager.GPS_PROVIDER);
        if (!gpsEnabled) {// ACTION_SECURITY_SETTINGS
            toast("请点击定位服务，并打开GPS卫星选项", 1);
            Intent intent = new Intent(Settings.ACTION_SETTINGS);
            // new Intent(Settings.)
            startActivityForResult(intent, 0);
            // Intent intent = new Intent("/");
            // ComponentName cm = new
            // ComponentName("com.android.settings","com.android.settings.SettingsSafetyLegalActivity");
            // intent.setComponent(cm);
            // intent.setAction("android.intent.action.VIEW");
            // startActivityForResult( intent , 0);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 0:
                if (resultCode == RESULT_OK) {
                    toast("GPS已打开", 1);
                }
                break;
            case 1:
                if (resultCode == RESULT_OK) {
                }
                break;
            case 2:
                if (resultCode == RESULT_OK) {
                }
                break;
            case 3:
                if (resultCode == RESULT_OK) {
                }
                break;
            default:
                break;
        }
    }

    /**
     * 强制打开GPS
     */
    private void toggleGPS() {
        Intent gpsIntent = new Intent();
        gpsIntent.setClassName("com.android.settings", "com.android.settings.widget.SettingsAppWidgetProvider");
        gpsIntent.addCategory("android.intent.category.ALTERNATIVE");
        gpsIntent.setData(Uri.parse("custom:3"));
        try {
            PendingIntent.getBroadcast(Welcome.this, 0, gpsIntent, 0).send();
        } catch (CanceledException e) {
            e.printStackTrace();
        }
    }

    /**
     * 统计
     */
    private void flow() {
        calendar = Calendar.getInstance();
        SharedPreferences sp = getSharedPreferences("FLOW", MODE_PRIVATE);
        if (null != calendar && Integer.parseInt(sp.getString("Date", "31")) > calendar.get(Calendar.DAY_OF_MONTH)) {
            Editor editor = sp.edit();
            editor.putLong("LJWG", 0);
            editor.commit();
        }
    }

    /**
     * 持久化事件类型到数据库
     */
    private void getEventTypeFromDictAndParse2ListAndSave2DataBase() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String strUrl = HttpUtil.BASE_URL + "teventAndroid/addItem";
                Message msg = Message.obtain();
                try {
                    eventType_json = connServerForResult(strUrl);
                } catch (Exception e) {
                    msg.what = MESSAGETYPE_02;
                    handler1.sendMessage(msg);

                    if (null != e) {
                        e.printStackTrace();
                    }
                    return;
                }
                if (null == eventType_json || "".equals(eventType_json)) {
                    msg.what = MESSAGETYPE_02;
                    handler1.sendMessage(msg);
                    return;
                }
                StringReader reader = new StringReader(eventType_json);
                JsonReader jsonReader = new JsonReader(reader);
                try {
                    jsonReader.beginArray();
                    while (jsonReader.hasNext()) {
                        jsonReader.beginObject();
                        EOS_DICT_ENTRY dict = new EOS_DICT_ENTRY();
                        while (jsonReader.hasNext()) {
                            String nextName = jsonReader.nextName();
                            String nextValue = "";
                            if (nextName.equals("DICTID")) {
                                nextValue = jsonReader.nextString();
                                dict.setDICTID(nextValue);
                            } else if (nextName.equals("PARENTID")) {
                                nextValue = jsonReader.nextString();
                                dict.setPARENTID(nextValue);
                            } else if (nextName.equals("DICTTYPEID")) {
                                nextValue = jsonReader.nextString();
                                dict.setDICTTYPEID(nextValue);
                            } else if (nextName.equals("DICTNAME")) {
                                nextValue = jsonReader.nextString();
                                dict.setDICTNAME(nextValue);
                            }
                        }
                        dict_ENTRYs.add(dict);
                        dict = null;
                        jsonReader.endObject();

                    }
                    jsonReader.endArray();
                } catch (IOException e) {
                    msg.what = MESSAGETYPE_02;
                    handler1.sendMessage(msg);
                    if (null != e) {
                        e.printStackTrace();
                    }
                    return;
                }
                if (null == dict_ENTRYs || dict_ENTRYs.size() < 1) {
                    msg.what = MESSAGETYPE_02;
                } else {
                    msg.what = MESSAGETYPE_01;
                }
                handler1.sendMessage(msg);
                return;
            }
        }).start();
    }

    /**
     * 持久化事件类型到数据库
     */
    private Handler handler1 = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MESSAGETYPE_01:
                    db = FinalDb.create(getApplicationContext(), true);
                    db.deleteAll(EOS_DICT_ENTRY.class);
                    for (EOS_DICT_ENTRY entity : dict_ENTRYs) {
                        db.save(entity);
                    }
                    break;
                case MESSAGETYPE_02:
                    toast("请检查网络是否可用！", 0);
                    break;
                default:
                    break;
            }
            super.handleMessage(msg);
        }
    };

    /** 判断是否自动登录，没自动登入时检查更新 */
    private void isAutoLogin() {
        if (jzmmCheckBox.isChecked()) {
            if (zddrCheckBox.isChecked()) {
                submit("");
            } else {
                getVersionInfo();
            }
        } else {
            getVersionInfo();
        }
    }

    /** 初始化视图 */
    private void initView() {
        spPhone = getApplicationContext().getSharedPreferences("Phone", MODE_PRIVATE);

        // 获取imei
        TelephonyManager telephonyManager = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
        String imei = telephonyManager.getDeviceId();
        // toast(imei);
        Editor editor = spPhone.edit();
        editor.putString("imei", imei);
        editor.commit();

        userNmaeEditText = (EditText) findViewById(R.id.welcome_user_edit);
        userNmaeEditText.setText(spPhone.getString("number", ""));
        passwordEditText = (EditText) findViewById(R.id.welcome_password_edit);
        isJZMM = spPhone.getString("isJZMM", "0");
        isZDDR = spPhone.getString("isZDDR", "0");
        jzmmCheckBox = (CheckBox) findViewById(R.id.welcom_checkbox_jzmm);
        zddrCheckBox = (CheckBox) findViewById(R.id.welcom_checkbox_zddr);
        jzmmCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Editor editor = spPhone.edit();
                if (isChecked) {
                    editor.putString("isJZMM", "1");
                } else {
                    editor.putString("isJZMM", "0");
                }
                editor.commit();
            }
        });
        zddrCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Editor editor = spPhone.edit();
                if (isChecked) {
                    editor.putString("isZDDR", "1");
                } else {
                    editor.putString("isZDDR", "0");
                }
                editor.commit();
            }
        });
        if ("1".equals(isJZMM)) {
            jzmmCheckBox.setChecked(true);
            passwordEditText.setText(spPhone.getString("password", ""));
        } else {
            jzmmCheckBox.setChecked(false);
        }
        if ("1".equals(isZDDR)) {
            zddrCheckBox.setChecked(true);
        } else {
            zddrCheckBox.setChecked(false);
        }

        dialogPress = new AlertDialog.Builder(this).setTitle("正在下载apk文件").setMessage("下载进度:0/0")
                .setPositiveButton("确定", new OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).create();
    }

    /**
     * 天
     */
    private void initLocationTDT() {
        mMyLocation = new MyOverlay(this, null);
        // mMyLocation.enableCompass(); //显示指南针
        // mMyLocation.enableMyLocation(); //显示我的位置
        m_locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (m_locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            m_locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 2000, 0, mMyLocation);
        }
        // else
        // if(m_locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
        // m_locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
        // 2000, 0, mMyLocation);
        // }
        // Settings.Secure.isLocationProviderEnabled( getContentResolver(),
        // LocationManager.GPS_PROVIDER )

    }

    /**
     * 系统定位监听 位置监听器LocationListener 的设置，当位置发生变化是触发onLocationChanged( )
     */
    LocationListener onLocationChange = new LocationListener() {
        public void onLocationChanged(Location location) {
            System.out.println("Location Changed : (" + location.getLongitude() + "," + location.getLatitude() + ")");
            SharedPreferences sp = getApplicationContext().getSharedPreferences("GPS", MODE_PRIVATE);
            // 存入数据
            Editor editor = sp.edit();
            editor.putString("GPS_latitude", "" + location.getLatitude());
            editor.putString("GPS_longitude", "" + location.getLongitude());
            editor.commit();
            String strLog = String.format("您当前的位置:\r\n" + "纬度:%f\r\n" + "经度:%f", location.getLongitude(),
                    location.getLatitude());
            // Toast.makeText(mCon, strLog, Toast.LENGTH_SHORT).show();
            System.out.println(strLog);
        }

        public void onProviderDisabled(String arg0) {
            System.out.println("onProviderDisabled");
        }

        public void onProviderEnabled(String arg0) {
            System.out.println("onProviderEnabled");
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            // TODO Auto-generated method stub
            System.out.println(provider + status);

        }
    };
    /**
     * 
     * 描述MyOverlay
     * @author Mars zhang
     * @created 2014-11-5 上午11:15:28
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
            System.out.println("dispatchTap");
            // Toast.makeText(mCon, "您点击了我的位置", Toast.LENGTH_SHORT).show();
            return true;
        }

        @Override
        public void onLocationChanged(Location location) {
            // TODO Auto-generated method stub
            super.onLocationChanged(location);
            if (location != null) {
                location_su = location;
                String strLog = String.format("您当前的位置:\r\n" + "纬度:%f\r\n" + "经度:%f", location.getLongitude(),
                        location.getLatitude());
                // Toast.makeText(mCon, strLog, Toast.LENGTH_SHORT).show();
                System.out.println(strLog);
                SharedPreferences sp = getApplicationContext().getSharedPreferences("GPS", MODE_PRIVATE);
                // 存入数据
                Editor editor = sp.edit();
                editor.putString("GPS_latitude", "" + location.getLatitude());
                editor.putString("GPS_longitude", "" + location.getLongitude());
                editor.commit();
            }
        }
    }

    // @Override
    // public boolean onKeyUp(int keyCode, KeyEvent event) {
    // if(keyCode == KeyEvent.KEYCODE_BACK)
    // System.exit(0);
    // return super.onKeyUp(keyCode, event);
    // }
    /** 定位初始化监听3 */
    private void initLocation() {
        mLocClient = new LocationClient(getApplicationContext());
        option = new LocationClientOption();
        option.setScanSpan(2000); // 设置扫描间隔，单位是毫秒
        option.setOpenGps(true);// 打开gps
        option.setCoorType("wgs84"); // 设置坐标类型
        // option.setPoiNumber(5);
        option.setPoiExtraInfo(true);
        mLocClient.setLocOption(option);
        mLocClient.registerLocationListener(new BDLocationListener() {
            @Override
            public void onReceivePoi(BDLocation location) {
            }

            @Override
            public void onReceiveLocation(final BDLocation location) {
                new Thread() {
                    public void run() {
                        SharedPreferences sp = getApplicationContext().getSharedPreferences("GPS", MODE_PRIVATE);
                        // 存入数据
                        Editor editor = sp.edit();
                        editor.putString("GPS_latitude", "" + location.getLatitude());
                        editor.putString("GPS_longitude", "" + location.getLongitude());
                        editor.commit();
                    }
                }.start();

            }
        });
        mLocClient.start();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        // 2 mLocClient.stop();
        if (null != mMyLocation) {
            m_locationManager.removeUpdates(mMyLocation);
        }
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        // 1 mLocClient.stop();
        super.onDestroy();
    }

    // /**
    // * test
    // * */
    // public void test(View v) {
    // Intent intent = new Intent();
    // intent.setClass(Welcome.this, AfnailPictureActivity.class);
    // startActivity(intent);
    // Welcome.this.finish();
    // }

    /** 登入按钮点击 */
    public void welcome_login(View v) {
        // Intent intent = new Intent();
        // intent.setClass(Welcome.this, AfnailPictureActivity.class);
        // startActivity(intent);
        // Welcome.this.finish();
        submit("");
        // Intent intent = new Intent();
        // intent.setClass(Welcome.this, MainOneActivity.class);
        // startActivity(intent);
        // Welcome.this.finish();
    }

    /**
     * 登入请求
     */
    private String submit(final String filename) {
        TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);

        System.out.println(tm.getSimState());
        switch (tm.getSimState()) {
            case TelephonyManager.SIM_STATE_UNKNOWN: // 未知
                toast("请检查SIM卡是否可用！", 1);
                return "";
            case TelephonyManager.SIM_STATE_ABSENT: // 无
                toast("请插入SIM卡！", 1);
                return "";
                // break ;
            case TelephonyManager.SIM_STATE_PIN_REQUIRED: // 未知
                toast("需要PIN解锁！", 1); //
                return "";
            case TelephonyManager.SIM_STATE_PUK_REQUIRED: // 未知
                toast("请插入SIM卡！", 1);
                return "";
            case TelephonyManager.SIM_STATE_NETWORK_LOCKED: // 未知
                toast("需要NetworkPIN解锁！", 1);
                return "";
            case TelephonyManager.SIM_STATE_READY: // 460024591083344
                                                   // 460016070609025 02 00 01
                                                   // 03
                // toast("sim卡可用"+tm.getSubscriberId(), 1);
                String imsi = tm.getSubscriberId();
                if (null != imsi && imsi.length() > 6
                        && ("46002".equals(imsi.substring(0, 5)) || "46000".equals(imsi.substring(0, 5)))) {
                    // toast("移动用户", 1);
                    break;
                } else if (null != imsi && imsi.length() > 6 && "46001".equals(imsi.substring(0, 5))) {
                    toast("联通用户", 1);
                    return "";
                } else if (null != imsi && imsi.length() > 6 && "46003".equals(imsi.substring(0, 5))) {
                    toast("电信用户", 1);
                    return "";
                    // break ;
                }
                break;
            default:
                break;
        }

        // if(null!=tm){
        // // imsi = tm.getSubscriberId();
        // }
        //
        // System.out.println(imsi);
        // if(null!=imsi&&imsi){
        //
        // }

        Editor editor = spPhone.edit();
        editor.putString("number", userNmaeEditText.getText().toString());
        if (jzmmCheckBox.isChecked()) {
            editor.putString("isJZMM", "1");
            editor.putString("password", passwordEditText.getText().toString());
        } else {
            editor.putString("isJZMM", "0");
        }
        if (jzmmCheckBox.isChecked() && zddrCheckBox.isChecked()) {
            editor.putString("isZDDR", "1");
        } else {
            editor.putString("isZDDR", "0");
        }
        editor.commit();
        progressDialog = ProgressDialog.show(Welcome.this, "提示", "正在登入，请稍等...");
        progressDialog.setCancelable(true);
        new Thread() {
            public void run() {
                try {
                    Message msg_listData = new Message();
                    String furl = HttpUtil.BASE_URL + "teventAndroid/LoginFromAndroid";
                    // HashMap fhm = new HashMap();
                    // Map sma = new HashMap();
                    // sma.put("user", userNmaeEditText.getText().toString()
                    // .trim());
                    // sma.put("password", passwordEditText.getText().toString()
                    // .trim());
                    String enttity_str = "user=" + userNmaeEditText.getText().toString().trim() + "&password="
                            + passwordEditText.getText().toString().trim();
                    // String
                    // enttity_str="user="+userNmaeEditText.getText().toString()
                    // +"&password="+passwordEditText.getText().toString();
                    // //第一轮bug修改
                    try {
                        // 调用上传工具上传文件
                        sss = connServerForResultPost(furl, enttity_str);
                    } catch (Exception e) {
                        System.out.println("a  " + sss);
                    }
                    System.out.println(sss + " ");
                    temp = sss;
                    if (sss == null || sss.equals("")) {// 登入失败
                        msg_listData.what = MESSAGETYPE_02;
                        System.out.println("b  " + sss);
                    } else if (sss.contains("success@")) { // 登入成功
                    // success@0@连江县@15059177847@系统管理员@1@
                        msg_listData.what = MESSAGETYPE_01;
                        System.out.println("c  " + sss);
                    } else if ("failure".equals(sss)) {
                        msg_listData.what = 3;
                    }
                    handler.sendMessage(msg_listData);
                } catch (Exception e) {

                }
            }
        }.start();
        return "1";
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

    /** 进度条使用 */
    private Handler handler = new Handler() {
        public void handleMessage(Message message) {
            switch (message.what) {
                case MESSAGETYPE_01:
                    if (null != progressDialog) {
                        progressDialog.dismiss();
                    }
                    SharedPreferences sp = getSharedPreferences("SESSION", MODE_PRIVATE);
                    Editor editor = sp.edit();
                    // success@0@连江县@15059177847@系统管理员@1@
                    s = Pattern.compile("@").split(sss);
                    editor.putString("AREAID", s[1]);
                    editor.putString("AREANAME", s[2]);
                    editor.putString("PHONE", s[3]);
                    editor.putString("EMPNAME", s[4]);
                    editor.putString("EMPID", s[5]);
                    editor.putString("ISZNBM", s[6]);
                    editor.putString("AREALEV", s[7]);
                    editor.putString("USERNAME", userNmaeEditText.getText().toString().trim());
                    editor.putString("PASSWORD", passwordEditText.getText().toString().trim());
                    editor.commit();

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            String url = HttpUtil.BASE_URL + "teventAndroid/FlowCount";
                            String entity = "USERID=" + s[5] + "&AREAID=" + s[1] + "&LJWGFLOW="
                                    + PhoneUtil.getFlowByUid(getApplicationContext(), android.os.Process.myUid())
                                    + "&ALLFLOW=" + PhoneUtil.getAllFlow(getApplicationContext()) + "&IMEI="
                                    + PhoneUtil.getImei(getApplicationContext()) + "&IMSI="
                                    + PhoneUtil.getImsi(getApplicationContext());
                            try {
                                connServerForResultPost(url, entity);
                            } catch (Exception e) {
                                System.out.println("流量提交报错！");
                            }
                        }
                    }).start();
                    // toast(PhoneUtil.getAllFlow(getApplicationContext())
                    // + ""+PhoneUtil.getImei(getApplicationContext())
                    // + "   "+PhoneUtil.getImsi(getApplicationContext())
                    // +"   "
                    // + PhoneUtil.getFlowByUid(getApplicationContext(),
                    // android.os.Process.myUid()), 1);

                    Intent intent = new Intent();
                    intent.setClass(Welcome.this, MainOneActivity.class);
                    startActivity(intent);
                    Welcome.this.finish();
                    break;
                case MESSAGETYPE_02:
                    progressDialog.dismiss();
                    DialogToast("登录失败，请检查网络是否可用");
                    break;
                case 3: // 用户名密码出错
                    if (null != progressDialog) {
                        progressDialog.dismiss();
                        DialogToast("登录失败，用户名密码有误！");
                    }
                    break;
                default:
                    break;
            }
        }
    };

    /**
     * 获取gps提交时间
     */
    private void getGetGPSTime() {
        new Thread() {
            @Override
            public void run() {
                Message msg = Message.obtain(); // 比new一个对象好 相当于创建消息对象
                try {
                    URL url = new URL(HttpUtil.BASE_URL + HttpUtil.UPDATE_VERSION_XML);
                    HttpURLConnection conn;
                    conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("GET");
                    conn.setConnectTimeout(5000);
                    int code = conn.getResponseCode();
                    if (code == 200) {// 请求成功
                        InputStream is = conn.getInputStream();// 成功就获取返回的输入流
                        XmlPullParser parser = Xml.newPullParser();
                        parser.setInput(is, "utf-8");
                        SharedPreferences sp_gps_time = getSharedPreferences("GPS_TRACK", MODE_PRIVATE);
                        Editor editor = sp_gps_time.edit();
                        int type = parser.getEventType(); // 得到事件类型
                        while (type != XmlPullParser.END_DOCUMENT) {
                            type = parser.next();
                            switch (type) {
                                case XmlPullParser.START_TAG:
                                    if ("GPS_REQUEST_TIME".equals(parser.getName())) {
                                        editor.putString("TIME", parser.nextText());
                                        editor.commit();
                                        msg.what = MESSAGETYPE_01;
                                    } else if ("GPS_WORK_TIME".equals(parser.getName())) {
                                        editor.putString("GPS_WORK_TIME", parser.nextText());
                                        editor.commit();
                                        // toast(ShareUtil.getString(getApplicationContext(),
                                        // "GPS_TRACK", "GPS_WORK_TIME", "18"),
                                        // 1);
                                    }
                                    // else if
                                    // ("description".equals(parser.getName()))
                                    // {
                                    // updateInfo.setDescription(parser.nextText());
                                    // } else if
                                    // ("apkurl".equals(parser.getName()))
                                    // {
                                    // updateInfo.setApkurl(parser.nextText());
                                    // }
                                    break;
                                default:
                                    break;
                            }
                        }
                        is.close();
                    } else {
                        msg.what = SERVER_ERROR;// 服务器失败
                    }
                } catch (MalformedURLException e) {
                    msg.what = SERVER_URL_ERROR;
                    if (null != e) {
                        e.printStackTrace();
                    }
                } catch (NotFoundException e) {
                    msg.what = SERVER_URL_ERROR;
                    if (null != e) {
                        e.printStackTrace();
                    }
                } catch (IOException e) {
                    msg.what = NETWORK_ERROR;
                    if (null != e) {
                        e.printStackTrace();
                    }
                } catch (XmlPullParserException e) {
                    msg.what = XML_PARSE_ERROR;
                    if (null != e) {
                        e.printStackTrace();
                    }
                } finally { // 不管如何都要执行的操作
                    System.out.println(msg.what);
                    handler3.sendMessage(msg);
                }
                super.run();
            }
        }.start();
    }

    /**
     * 获取软件版本信息
     */
    private void getVersionInfo() {
        new Thread() {
            @Override
            public void run() {
                Message msg = Message.obtain(); // 比new一个对象好 相当于创建消息对象
                final long startTime = System.currentTimeMillis(); // 开始时间
                try {
                    // URL url=new
                    // URL(getResources().getString(R.string.url)+"update.xml");
                    URL url = new URL(HttpUtil.BASE_URL + HttpUtil.UPDATE_VERSION_XML);
                    HttpURLConnection conn;
                    conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("GET");
                    conn.setConnectTimeout(5000);
                    int code = conn.getResponseCode();
                    if (code == 200) {// 请求成功
                        InputStream is = conn.getInputStream();// 成功就获取返回的输入流
                        updateInfo = XmlParser.getUpdateInfo(is);
                        if (new AppInfoUtil().getAppversion(Welcome.this).equals(updateInfo.getVersion())) {
                            msg.what = LOAD_MAIN; // 版本一致
                        } else {
                            msg.what = SHOW_UPDATA_DIALOG;// 版本不一致
                        }
                    } else {
                        msg.what = SERVER_ERROR;// 服务器失败
                    }
                } catch (MalformedURLException e) {
                    msg.what = SERVER_URL_ERROR;
                    if (null != e) {
                        e.printStackTrace();
                    }
                } catch (NotFoundException e) {
                    msg.what = SERVER_URL_ERROR;
                    if (null != e) {
                        e.printStackTrace();
                    }
                } catch (IOException e) {
                    msg.what = NETWORK_ERROR;
                    if (null != e) {
                        e.printStackTrace();
                    }
                } catch (XmlPullParserException e) {
                    msg.what = XML_PARSE_ERROR;
                    if (null != e) {
                        e.printStackTrace();
                    }
                } finally { // 不管如何都要执行的操作
                    System.out.println(msg.what);
                    handler2.sendMessage(msg);
                }
                super.run();
            }
        }.start();
    }

    /**
     * 新建一个消息处理器
     */
    private Handler handler2 = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            System.out.println("msg.what" + msg.what);
            switch (msg.what) {
                case LOAD_MAIN: // 版本已经是最新的了
                    break;
                case SHOW_UPDATA_DIALOG: // 提示更新
                    newDialog();
                    break;
                case SERVER_URL_ERROR: // 网络路径错误
                    toast("请检查网络是否可用", 0);
                    break;
                case NETWORK_ERROR: // 网络连接错误
                    toast("请检查网络是否可用", 0);
                    break;
                case XML_PARSE_ERROR: // XML解析错误
                    toast("请检查网络是否可用", 0);
                    break;
                case SERVER_ERROR: // 服务器出错
                    toast("请检查网络是否可用", 0);
                    break;
                default:
                    break;
            }
            super.handleMessage(msg);
        }
    };
    /**
     * 新建一个消息处理器
     */
    private Handler handler3 = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            System.out.println("msg.what" + msg.what);
            switch (msg.what) {
                case MESSAGETYPE_01: // 正确获取消息获取
                    // toast(ShareUtil.getString(getApplicationContext(),
                    // "GPS_TRACK", "TIME", "GPS_GET_TIME_error"),1);
                    break;
                case SERVER_URL_ERROR: // 网络路径错误
                    toast("请检查网络是否可用", 0);
                    break;
                case NETWORK_ERROR: // 网络连接错误
                    toast("请检查网络是否可用", 0);
                    break;
                case XML_PARSE_ERROR: // XML解析错误
                    toast("请检查网络是否可用", 0);
                    break;
                case SERVER_ERROR: // 服务器出错
                    toast("请检查网络是否可用", 0);
                    break;
                default:
                    break;
            }
            super.handleMessage(msg);
        }
    };

    /** 弹出更新对话框 */
    private void newDialog() {
        Dialog dialog = new AlertDialog.Builder(this).setTitle("提示更新").setMessage(updateInfo.getDescription())
                .setPositiveButton("立即更新", new OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        downLoadApk();
                        dialog.dismiss();
                    }
                }).setNegativeButton("下次再说", new OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).create();
        dialog.show();
    }

    /** 判断并下载apk安装文件 */
    private void downLoadApk() {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {// 判断是否有SD卡
            FinalHttp fh = new FinalHttp();
            // 创建一个临时文件
            File clear_temp = new File(Environment.getExternalStorageDirectory(), "temp.apk");
            deleteFile(clear_temp);
            File temp = new File(Environment.getExternalStorageDirectory(), "temp.apk");
            dialogPress.show();
            // 调用download方法开始下载
            HttpHandler handler1 = fh.download(updateInfo.getApkurl(), // 这里是下载的路径
                    temp.getAbsolutePath(), // 这是保存到本地的路径
                    true, // true:断点续传 false:不断点续传（全新下载）
                    new AjaxCallBack<File>() {
                        @Override
                        public void onLoading(long count, long current) {// 每秒回调一次
                            System.out.println(current + "/" + count);
                            dialogPress.setMessage("下载进度:" + current / 1024 + "k/" + count / 1024 + "k");
                            super.onLoading(count, current);
                        }

                        @Override
                        public void onFailure(Throwable t, int errorNo, String strMsg) {
                            dialogPress.dismiss();
                            toast("下载更新失败，请检查网络是否可用！", 0);
                            super.onFailure(t, errorNo, strMsg);
                        }

                        @Override
                        public void onSuccess(File t) {
                            System.out.println(t == null ? "null" : t.getAbsoluteFile().toString() + "下载成功");
                            dialogPress.dismiss();
                            toast("下载成功...替换安装...", 1);
                            replaceAPK(t);
                        }

                        /**
                         * 替换安装APK
                         */
                        private void replaceAPK(File t) {
                            // <activity
                            // android:name=".PackageInstallerActivity"
                            // android:configChanges="orientation|keyboardHidden"
                            // android:theme="@style/TallTitleBarTheme">
                            // <intent-filter>
                            // <action android:name="android.intent.action.VIEW"
                            // />
                            // <category
                            // android:name="android.intent.category.DEFAULT" />
                            // <data android:scheme="content" />
                            // <data android:scheme="file" />
                            // <data
                            // android:mimeType="application/vnd.android.package-archive"
                            // />
                            // </intent-filter>
                            // </activity>
                            Intent intent = new Intent();
                            intent.setAction("android.intent.action.VIEW");
                            intent.addCategory("android.intent.category.DEFAULT");
                            // intent.setType("application/vnd.android.package-archive");
                            // intent.setData(Uri.fromFile(t));
                            intent.setDataAndType(Uri.fromFile(t), "application/vnd.android.package-archive");
                            startActivity(intent);
                        }
                    });
        } else {
            toast("没有SD卡更新失败", 1);
        }
    }

    /** 删除文件 */
    public void deleteFile(File file) {
        if (file.exists()) { // 判断文件是否存在
            if (file.isFile()) { // 判断是否是文件
                file.delete(); // delete()方法 你应该知道 是删除的意思;
                System.out.println("file.delete();");
            } else if (file.isDirectory()) { // 否则如果它是一个目录
                File files[] = file.listFiles(); // 声明目录下所有的文件 files[];
                for (int i = 0; i < files.length; i++) { // 遍历目录下所有的文件
                    this.deleteFile(files[i]); // 把每个文件 用这个方法进行迭代
                }
            }
            file.delete();
            System.out.println("file.delete();");
        } else {
            System.out.println("文件不存在。。");
        }
    }

    /** 土司 */
    private void toast(String strMsg, int L1S0) {
        Toast.makeText(getApplicationContext(), strMsg, L1S0).show();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            // webView.goBack();
            if (progressDialog != null) {
                progressDialog.dismiss();
            }
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 
     * @param strUrl
     * @return
     * @throws IOException
     * @throws ClientProtocolException
     */
    private String connServerForResult(String strUrl) throws Exception {
        // HttpGet对象
        HttpGet httpRequest = new HttpGet(strUrl);
        String strResult = "";
        // HttpClient对象
        BasicHttpParams httpParams = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(httpParams, 5000);// 设置超时时间
        HttpClient httpClient = new DefaultHttpClient(httpParams);
        HttpResponse httpResponse = httpClient.execute(httpRequest);
        if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            // 取得返回的数据
            strResult = EntityUtils.toString(httpResponse.getEntity());
        }

        return strResult;
    }

    /**
     * 错误填报提示信息
     * 
     * @param errorMsg
     */
    private void DialogToast(String errorMsg) {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(Welcome.this);
        builder1.setTitle("提示信息");
        builder1.setIcon(R.drawable.qq_dialog_default_icon);// 图标
        builder1.setMessage("" + errorMsg);
        builder1.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            // @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder1.show();
    }

}
