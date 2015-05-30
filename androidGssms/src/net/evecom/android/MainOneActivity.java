/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * 
 */
package net.evecom.android;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import net.evecom.android.bean.HandlerView;
import net.evecom.android.bean.Picture;
import net.evecom.android.bean.SysBaseDict;
import net.evecom.android.gps_service.StatusService;
import net.evecom.android.gps_service.StatusTDTService;
import net.evecom.android.util.HttpUtil;
import net.evecom.android.util.ShareUtil;
import net.evecom.android.view.MyImageButton;
import net.evecom.android.view.wheel.OnWheelChangedListener;
import net.evecom.android.view.wheel.OnWheelScrollListener;
import net.evecom.android.view.wheel.WheelView;
import net.evecom.android.view.wheel.adapter.AbstractWheelTextAdapter;
import net.evecom.android.web.Web0Activity;
import net.evecom.android.web.Web2Activity;
import net.evecom.android.web.Web3Activity;
import net.evecom.android.web.Web5Activity;
import net.evecom.android.web.WebdbActivity;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.util.EntityUtils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.stream.JsonReader;

/**
 * MainOneActivity
 * 
 * @author Mars zhang
 * 
 */
public class MainOneActivity extends Activity {
    /** MemberVariables */
    private boolean menu_display = false;
    /** MemberVariables */
    private GridView gridView;
    /** MemberVariables */
    private View layout;
    /** MemberVariables */
    private LayoutInflater inflater;
    /** MemberVariables */
    private PopupWindow menuWindow;
    /** MemberVariables */
    private LinearLayout mClose;
    /** MemberVariables */
    private LinearLayout mCloseBtn;
    /** MemberVariables */
    private TextView messageTextView = null;
    /** MemberVariables */
    public static MainOneActivity instance = null;
    /** 进度圈 */
    private ProgressDialog progressDialog_getInfo = null;
    /** 成员变量 */
    private static final int MESSAGETYPE_01 = 0x0001;// 用于判断是否发送成功
    /** 成员变量 */
    private static final int MESSAGETYPE_02 = 0x0002;
    /** 街道测试列表 */
    private List<SysBaseDict> list_jd = new ArrayList<SysBaseDict>();
    // private List<String> list = new ArrayList<String>();
    /** 被选择的隐患类别 */
    private SysBaseDict baseDict = new SysBaseDict();
    /** Sysindex */
    private int Sysindex;
    /** MemberVariables */
    private MyImageButton myImageButton;
    /** msg_listData */
    private Message msg_listData = null;
    /** MemberVariables */
    private String isGPS_Thread = "1";
    /** MemberVariables */
    private String dbsj_Num = "";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_tab_weixin);
        // 启动activity时不自动弹出软键盘
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        messageTextView = (TextView) findViewById(R.id.message);
        new Thread(new Runnable() {
            @Override
            public void run() {
                msg_listData = new Message();
                try {
                    String url = HttpUtil.BASE_URL + "teventAndroid/gridList" + "?EMPID="
                            + ShareUtil.getString(getApplicationContext(), "SESSION", "EMPID", "0") + "&AREAID="
                            + ShareUtil.getString(getApplicationContext(), "SESSION", "AREAID", "0");
                    dbsj_Num = connServerForResult(url);
                    if (null != dbsj_Num && dbsj_Num.contains("success")) {
                        msg_listData.what = MESSAGETYPE_01;
                    } else {
                        msg_listData.what = MESSAGETYPE_02;
                    }
                } catch (Exception e) {
                    msg_listData.what = MESSAGETYPE_02;
                    handler_for_dbsj.sendMessage(msg_listData);
                    if (null != e) {
                        e.printStackTrace();
                    }
                }
                handler_for_dbsj.sendMessage(msg_listData);
            }
        }).start();

        myImageButton = (MyImageButton) findViewById(R.id.main_tab_weixin_myimagebutton);
        myImageButton.setText(ShareUtil.getString(getApplicationContext(), "SESSION", "AREANAME", "连江县"));
        SharedPreferences sp = getSharedPreferences("GPS_TRACK", MODE_PRIVATE);
        Editor editor = sp.edit();
        editor.putString("GPS_IS_ON", "1");
        editor.commit();
        instance = this;
        // 换成gridView
        gridView = (GridView) findViewById(R.id.main_tab_weixin_gridview);
        int[] images = new int[] { R.drawable.wgh_main_ptxx2, R.drawable.wgh_main_sjdj2, R.drawable.wgh_main_rcbg2,
                R.drawable.wgh_main_jcsj2, R.drawable.wgh_main_zygz3, R.drawable.wgh_main_xtfx2,
                R.drawable.wgh_main_xtsz2, R.drawable.wgh_main_tcxt2 };
        // 平台消息、事件登记、日常办公、基础数据、专业工作、统计分析、系统设置、退出系统
        String[] itemtitles = new String[] { "消息公告", "预警提醒", "隐患排查", "执法检查"
                , "资源检索", "应急管理", "定位管理", "系统设置", "退出系统" };
        PictureAdapter pictureAdapter = new PictureAdapter(itemtitles, images, MainOneActivity.this);
        gridView.setAdapter(pictureAdapter);
        gridView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                Intent intent = null;
                switch (arg2) {
                    case 0: // 消息公告
                        intent = new Intent(MainOneActivity.this, Web0Activity.class);
                        startActivity(intent);
                        break;
                    case 1: // 事件登记
                        intent = new Intent(MainOneActivity.this, EventAddActivity.class);
                        startActivity(intent);
                        break;
                    case 2: // 隐患排查
                        intent = new Intent(MainOneActivity.this, Web2Activity.class);
                        startActivity(intent);
                        break;
                    case 3: //
                        intent = new Intent(MainOneActivity.this, Web3Activity.class);
                        startActivity(intent);
                        break;
                    case 4:
                        intent = new Intent(MainOneActivity.this, DailyWorkActivity.class);
                        startActivity(intent);
                        break;
                    case 5:
                        intent = new Intent(MainOneActivity.this, Web5Activity.class);
                        startActivity(intent);
                        break;
                    case 6:// 系统设置
                        intent = new Intent(MainOneActivity.this, SystemSetingActivity.class);
                        startActivity(intent);
                        break;
                    case 7:
                        if (menu_display) { // 如果 Menu已经打开 ，先关闭Menu
                            menuWindow.dismiss();
                            menu_display = false;
                        } else {
                            SharedPreferences sp = getSharedPreferences("GPS_TRACK", MODE_PRIVATE);
                            Editor editor = sp.edit();
                            editor.putString("GPS_IS_ON", "0");
                            editor.commit();
                            instance = MainOneActivity.this;
                            intent = new Intent(MainOneActivity.this, Exit.class);
                            startActivity(intent);
                        }
                        break;
                    // case 8: // 退出系统
                    // if (menu_display) { // 如果 Menu已经打开 ，先关闭Menu
                    // menuWindow.dismiss();
                    // menu_display = false;
                    // } else {
                    // intent = new Intent(MainOneActivity.this,
                    // Exit.class);
                    // startActivity(intent);
                    // }
                    // break;
                    default:
                        break;
                }
            }
        });

        // Intent startServiceIntent = new
        // Intent(MainOneActivity.this,StatusService.class);
        // startServiceIntent.putExtra("a", "11");
        // startService(startServiceIntent);
        GPS_Thread.start();

    }

    /**
     * gps
     */
    private Thread GPS_Thread = new Thread(new Runnable() {
        @Override
        public void run() {
            // Intent startServiceIntent = new Intent(MainOneActivity.this,
            // StatusService.class);
            // startService(startServiceIntent);

            Intent startServiceIntent = new Intent(MainOneActivity.this, StatusTDTService.class);
            startService(startServiceIntent);
        }
    });

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) { // 获取
                                                                               // back键

            if (menu_display) { // 如果 Menu已经打开 ，先关闭Menu
                menuWindow.dismiss();
                menu_display = false;
            } else {
                SharedPreferences sp = getSharedPreferences("GPS_TRACK", MODE_PRIVATE);
                Editor editor = sp.edit();
                editor.putString("GPS_IS_ON", "0");
                editor.commit();
                Intent intent = new Intent();
                intent.setClass(MainOneActivity.this, Exit.class);
                startActivity(intent);

            }
        }
        return false;
    }

    /**
     * 退出按钮
     * 
     * @param v
     */
    public void exit_settings(View v) { // 退出 伪“对话框”，其实是一个activity
        Intent intent = new Intent(MainOneActivity.this, ExitFromSettings.class);
        startActivity(intent);
    }

    /**
     * 代办事件点击
     * 
     * @param v
     */
    public void toweb2(View v) {
        Intent intent = new Intent(getApplicationContext(), WebdbActivity.class);
        startActivity(intent);
    }

    /**
     * 返回按钮点击事件
     * 
     * @param v
     */
    public void login_back(View v) { // 标题栏 返回按钮

        this.finish();
    }

    /**
     * 注册按钮事件
     * 
     * @param v
     */
    public void login_pw(View v) { // 忘记密码按钮

    }

    /**
     * 
     * 2014-7-22下午4:52:37 类PictureAdapter
     * 
     * @author Mars zhang
     * 
     */
    public class PictureAdapter extends BaseAdapter {
        /** MemberVariables */
        private LayoutInflater inflater;
        /** MemberVariables */
        private List<Picture> pictures;

        /** MemberVariables */
        public PictureAdapter(String[] titles, int[] images, Context context) {
            super();
            pictures = new ArrayList<Picture>();
            inflater = LayoutInflater.from(context);
            for (int i = 0; i < images.length; i++) {
                Picture picture = new Picture(titles[i], images[i]);
                pictures.add(picture);
            }
        }

        @Override
        public int getCount() {
            if (pictures != null) {
                return pictures.size();
            } else {
                return 0;
            }
        }

        @Override
        public Object getItem(int position) {

            return pictures.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            HandlerView handlerView;
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.gvitem, null);
                handlerView = new HandlerView();
                // handlerView.textView=(TextView)convertView.findViewById(R.id.gvitem_textview);
                handlerView.imageView = (ImageView) convertView.findViewById(R.id.gvitem_imageview);
                convertView.setTag(handlerView);
            } else {

                handlerView = (HandlerView) convertView.getTag();
            }
            // handlerView.textView.setText(pictures.get(position).getTitle());
            handlerView.imageView.setImageResource(pictures.get(position).getImageld());
            return convertView;
        }
    }

    /**
     * 街道选择
     * 
     * @param v
     */
    @SuppressLint("NewApi")
    public void jd_click(View v) { //
        // String pid = "0";
        // getTreeOne("");
        getTree();
    }

    // /**
    // *
    // * @param pid
    // * @throws IOException
    // * @throws ClientProtocolException
    // */
    // private void getAreaJsonReader(String pid) throws
    // ClientProtocolException, IOException {
    // String strUrl = HttpUtil.BASE_URL + "teventAndroid/queryChildTree?pid="
    // + pid;
    // final String strResult = connServerForResult(strUrl);
    // // DialogToast(strResult);
    // System.out.println(strResult);
    // new Thread(new Runnable() {
    // @Override
    // public void run() {
    // StringReader reader = new StringReader(strResult);
    // JsonReader jsonReader = new JsonReader(reader);
    // try {
    // jsonReader.beginArray();
    // while (jsonReader.hasNext()) {
    // jsonReader.beginObject();
    // SysBaseDict dict = new SysBaseDict();
    // while (jsonReader.hasNext()) {
    // String nextName = jsonReader.nextName();
    // String nextValue = "";
    // if (nextName.equals("id")) {
    // nextValue = jsonReader.nextString();
    // dict.setSbdCode(nextValue);
    // } else if (nextName.equals("name")) {
    // nextValue = jsonReader.nextString();
    // dict.setSbdName(nextValue);
    // } else if (nextName.equals("isparent")) {
    // nextValue = jsonReader.nextString();
    // dict.setSbdType(nextValue);
    // } else if (nextName.equals("pid")) {
    // nextValue = jsonReader.nextString();
    // dict.setSbdPid(nextValue);
    // } else if (nextName.equals("type")) {
    // nextValue = jsonReader.nextString();
    // dict.setSbdType(nextValue);
    // }
    //
    // // System.out.println(nextName+"="+nextValue);
    // }
    // list.add(dict);
    // dict = null;
    // jsonReader.endObject();
    // }
    // jsonReader.endArray();
    // } catch (IOException e) {
    // e.printStackTrace();
    // }
    // }
    // }).start();
    // }
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
        AlertDialog.Builder builder1 = new AlertDialog.Builder(MainOneActivity.this);
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

    /**
     * 连江县
     */
    /**
     * getTreeOne
     */
    private void getTree() {

        list_jd.removeAll(list_jd);
        SysBaseDict dict = new SysBaseDict();
        dict.setSbdName("连江县");
        list_jd.add(dict);
        View messageView = LayoutInflater.from(MainOneActivity.this).inflate(R.layout.base_dialog_info, null);
        // 分页设置
        final WheelView country = (WheelView) messageView.findViewById(R.id.country);
        country.setVisibleItems(3);
        country.setViewAdapter(new CountryAdapter(MainOneActivity.this, list_jd));
        country.addScrollingListener(new OnWheelScrollListener() {
            public void onScrollingStarted(WheelView wheel) {

            }

            public void onScrollingFinished(WheelView wheel) {
                baseDict = list_jd.get(Sysindex);
            }
        });
        country.addChangingListener(new OnWheelChangedListener() {
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                Sysindex = newValue;
            }
        });
        country.setCurrentItem(0);
        baseDict = list_jd.get(0);
        Sysindex = 0;
        Dialog delDia = new AlertDialog.Builder(MainOneActivity.this).setIcon(R.drawable.qq_dialog_default_icon)
                .setTitle("连江县！").setView(messageView).setPositiveButton("确认",
                        new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dia, int which) {
                        myImageButton.setText("连江县");
                        dia.dismiss();
                    }
                }).setNegativeButton("选择城镇", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dia, int which) {
                        dia.dismiss();
                        getTreeOne("");
                    }
                }).create();
        delDia.show();

    }

    /**
     * getTreeOne
     */
    private void getTreeOne(final String sbd_id) {
        progressDialog_getInfo = ProgressDialog.show(MainOneActivity.this, "提示", "正在获取数据，请稍等...");

        list_jd.removeAll(list_jd);

        // getAreaJsonReader("0");

        new Thread(new Runnable() {
            @Override
            public void run() {
                msg_listData = new Message();
                String strUrl = HttpUtil.BASE_URL + "teventAndroid/queryChildTree?pid=" + "0";
                String strResult = null;
                try {
                    strResult = connServerForResult(strUrl);
                } catch (Exception e) {
                    msg_listData.what = MESSAGETYPE_02;
                    handler1.sendMessage(msg_listData);
                    if (null != e) {
                        e.printStackTrace();
                    }
                    return;
                }
                // DialogToast(strResult);
                // System.out.println(strResult);
                if (null == strResult || "".equals(strResult)) {
                    msg_listData.what = MESSAGETYPE_02;
                    handler1.sendMessage(msg_listData);
                    return;
                }
                StringReader reader = new StringReader(strResult);
                JsonReader jsonReader = new JsonReader(reader);
                try {
                    jsonReader.beginArray();
                    while (jsonReader.hasNext()) {
                        jsonReader.beginObject();
                        SysBaseDict dict = new SysBaseDict();
                        while (jsonReader.hasNext()) {
                            String nextName = jsonReader.nextName();
                            String nextValue = "";
                            if (nextName.equals("id")) {
                                nextValue = jsonReader.nextString();
                                dict.setSbdCode(nextValue);
                            } else if (nextName.equals("name")) {
                                nextValue = jsonReader.nextString();
                                dict.setSbdName(nextValue);
                            } else if (nextName.equals("isparent")) {
                                nextValue = jsonReader.nextString();
                                dict.setSbdType(nextValue);
                            } else if (nextName.equals("pid")) {
                                nextValue = jsonReader.nextString();
                                dict.setSbdPid(nextValue);
                            } else if (nextName.equals("type")) {
                                nextValue = jsonReader.nextString();
                                dict.setSbdType(nextValue);
                            }

                            // System.out.println(nextName+"="+nextValue);
                        }
                        list_jd.add(dict);
                        dict = null;
                        jsonReader.endObject();

                    }
                    jsonReader.endArray();
                } catch (IOException e) {
                    if (null != e) {
                        e.printStackTrace();
                    }
                }
                if (null == list_jd || list_jd.size() < 1) {
                    msg_listData.what = MESSAGETYPE_02;
                } else {
                    msg_listData.what = MESSAGETYPE_01;
                }
                handler1.sendMessage(msg_listData);
            }
        }).start();

    }

    /** handler_for_dbsj */
    private Handler handler_for_dbsj = new Handler() {
        public void handleMessage(Message message) {
            switch (message.what) {
                case MESSAGETYPE_01:
                    String s[] = Pattern.compile("@").split(dbsj_Num);
                    if (null != s && s.length > 1) {
                        messageTextView.setText("有" + s[1] + "个代办事项");
                    }
                    break;
                case MESSAGETYPE_02:
                    // Toast.makeText(getApplicationContext(), "请检查网络是否可用！",
                    // 0).show();
                    // myImageButton.setText("");
                    break;
                default:
                    break;
            }
        }
    };
    /** handler1 */
    private Handler handler1 = new Handler() {
        public void handleMessage(Message message) {
            switch (message.what) {
                case MESSAGETYPE_01:
                    if (null != progressDialog_getInfo) {
                        progressDialog_getInfo.dismiss();
                    }
                    View messageView = LayoutInflater.from(MainOneActivity.this).inflate(R.layout.base_dialog_info,
                            null);
                    // 分页设置
                    final WheelView country = (WheelView) messageView.findViewById(R.id.country);
                    country.setVisibleItems(3);
                    country.setViewAdapter(new CountryAdapter(MainOneActivity.this, list_jd));
                    country.addScrollingListener(new OnWheelScrollListener() {
                        public void onScrollingStarted(WheelView wheel) {

                        }

                        public void onScrollingFinished(WheelView wheel) {
                            baseDict = list_jd.get(Sysindex);
                        }
                    });
                    country.addChangingListener(new OnWheelChangedListener() {
                        public void onChanged(WheelView wheel, int oldValue, int newValue) {
                            Sysindex = newValue;
                        }
                    });
                    country.setCurrentItem(0);
                    baseDict = list_jd.get(0);
                    Sysindex = 0;
                    Dialog delDia = new AlertDialog.Builder(MainOneActivity.this)
                            .setIcon(R.drawable.qq_dialog_default_icon).setTitle("请选择城镇！").setView(messageView)
                            .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dia, int which) {
                                    myImageButton.setText(baseDict.getSbdName());
                                    dia.dismiss();
                                }
                            }).setNegativeButton("选择社区", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dia, int which) {
                                    dia.dismiss();
                                    getTreeTwo(baseDict.getSbdCode());
                                }
                            }).create();
                    delDia.show();
                    break;
                case MESSAGETYPE_02:
                    if (null != progressDialog_getInfo) {
                        progressDialog_getInfo.dismiss();
                    }
                    Toast.makeText(getApplicationContext(), "请检查网络是否可用！", 0).show();
                    // myImageButton.setText("");
                    break;
                default:
                    break;
            }
        }
    };

    /**
     * getTreeTwo
     */
    private void getTreeTwo(final String oneStr) {
        progressDialog_getInfo = ProgressDialog.show(MainOneActivity.this, "提示", "正在获取数据，请稍等...");
        list_jd.removeAll(list_jd);
        msg_listData = new Message();

        new Thread(new Runnable() {
            @Override
            public void run() {
                String strUrl = HttpUtil.BASE_URL + "teventAndroid/queryChildTree?pid=" + oneStr;
                String strResult = null;
                try {
                    strResult = connServerForResult(strUrl);
                } catch (Exception e) {
                    msg_listData.what = MESSAGETYPE_02;
                    handler2.sendMessage(msg_listData);
                    if (null != e) {
                        e.printStackTrace();
                    }
                    return;
                }
                // DialogToast(strResult);
                // System.out.println(strResult);
                if (null == strResult || "".equals(strResult)) {
                    return;
                }
                StringReader reader = new StringReader(strResult);
                JsonReader jsonReader = new JsonReader(reader);

                try {
                    jsonReader.beginArray();
                    while (jsonReader.hasNext()) {
                        jsonReader.beginObject();
                        SysBaseDict dict = new SysBaseDict();
                        while (jsonReader.hasNext()) {
                            String nextName = jsonReader.nextName();
                            String nextValue = "";
                            if (nextName.equals("id")) {
                                nextValue = jsonReader.nextString();
                                dict.setSbdCode(nextValue);
                            } else if (nextName.equals("name")) {
                                nextValue = jsonReader.nextString();
                                dict.setSbdName(nextValue);
                            } else if (nextName.equals("isparent")) {
                                nextValue = jsonReader.nextString();
                                dict.setSbdType(nextValue);
                            } else if (nextName.equals("pid")) {
                                nextValue = jsonReader.nextString();
                                dict.setSbdPid(nextValue);
                            } else if (nextName.equals("type")) {
                                nextValue = jsonReader.nextString();
                                dict.setSbdType(nextValue);
                            }
                            // System.out.println(nextName+"="+nextValue);
                        }
                        list_jd.add(dict);
                        dict = null;
                        jsonReader.endObject();

                    }
                    jsonReader.endArray();
                } catch (IOException e) {
                    if (null != e) {
                        e.printStackTrace();
                    }
                }
                if (null == list_jd || list_jd.size() < 1) {
                    msg_listData.what = MESSAGETYPE_02;
                } else {
                    msg_listData.what = MESSAGETYPE_01;
                }
                handler2.sendMessage(msg_listData);
            }
        }).start();

    }

    /** handler2 */
    private Handler handler2 = new Handler() {
        public void handleMessage(Message message) {
            switch (message.what) {
                case MESSAGETYPE_01:
                    if (null != progressDialog_getInfo) {
                        progressDialog_getInfo.dismiss();
                    }
                    View messageView = LayoutInflater.from(MainOneActivity.this).inflate(R.layout.base_dialog_info,
                            null);
                    // 分页设置
                    final WheelView country = (WheelView) messageView.findViewById(R.id.country);
                    country.setVisibleItems(3);
                    country.setViewAdapter(new CountryAdapter(MainOneActivity.this, list_jd));
                    country.addScrollingListener(new OnWheelScrollListener() {
                        public void onScrollingStarted(WheelView wheel) {

                        }

                        public void onScrollingFinished(WheelView wheel) {
                            baseDict = list_jd.get(Sysindex);
                        }
                    });
                    country.addChangingListener(new OnWheelChangedListener() {
                        public void onChanged(WheelView wheel, int oldValue, int newValue) {
                            Sysindex = newValue;
                        }
                    });
                    country.setCurrentItem(0);
                    baseDict = list_jd.get(0);
                    Sysindex = 0;
                    Dialog delDia = new AlertDialog.Builder(MainOneActivity.this)
                            .setIcon(R.drawable.qq_dialog_default_icon).setTitle("请选择社区！").setView(messageView)
                            .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dia, int which) {
                                    myImageButton.setText(baseDict.getSbdName());
                                    dia.dismiss();
                                }
                            }).setNegativeButton("选择网格", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dia, int which) {
                                    dia.dismiss();
                                    getTreeThree(baseDict.getSbdCode());
                                }
                            }).create();
                    delDia.show();
                    break;
                case MESSAGETYPE_02:
                    if (null != progressDialog_getInfo) {
                        progressDialog_getInfo.dismiss();
                    }
                    Toast.makeText(getApplicationContext(), "请检查网络是否可用！", 0).show();
                    // myImageButton.setText("");
                    break;
                default:
                    break;
            }
        }
    };

    /**
     * getTreeThree
     */
    private void getTreeThree(final String sbd_id) {
        progressDialog_getInfo = ProgressDialog.show(MainOneActivity.this, "提示", "正在获取数据，请稍等...");
        list_jd.removeAll(list_jd);
        msg_listData = new Message();

        new Thread(new Runnable() {
            @Override
            public void run() {
                String strUrl = HttpUtil.BASE_URL + "teventAndroid/queryChildTree?pid=" + sbd_id;
                String strResult = null;
                try {
                    strResult = connServerForResult(strUrl);
                } catch (Exception e) {
                    msg_listData.what = MESSAGETYPE_02;
                    handler3.sendMessage(msg_listData);
                    if (null != e) {
                        e.printStackTrace();
                    }
                    return;
                }
                if (null == strResult || "".equals(strResult)) {
                    return;
                }
                // System.out.println(strResult);
                StringReader reader = new StringReader(strResult);
                JsonReader jsonReader = new JsonReader(reader);

                try {
                    jsonReader.beginArray();
                    while (jsonReader.hasNext()) {
                        jsonReader.beginObject();
                        SysBaseDict dict = new SysBaseDict();
                        while (jsonReader.hasNext()) {
                            String nextName = jsonReader.nextName();
                            String nextValue = "";
                            if (nextName.equals("id")) {
                                nextValue = jsonReader.nextString();
                                dict.setSbdCode(nextValue);
                            } else if (nextName.equals("name")) {
                                nextValue = jsonReader.nextString();
                                dict.setSbdName(nextValue);
                            } else if (nextName.equals("isparent")) {
                                nextValue = jsonReader.nextString();
                                dict.setSbdType(nextValue);
                            } else if (nextName.equals("pid")) {
                                nextValue = jsonReader.nextString();
                                dict.setSbdPid(nextValue);
                            } else if (nextName.equals("type")) {
                                nextValue = jsonReader.nextString();
                                dict.setSbdType(nextValue);
                            }
                        }
                        list_jd.add(dict);
                        dict = null;
                        jsonReader.endObject();
                    }
                    jsonReader.endArray();
                } catch (IOException e) {
                    if (null != e) {
                        e.printStackTrace();
                    }
                }
                if (null == list_jd || list_jd.size() < 1) {
                    msg_listData.what = MESSAGETYPE_02;
                } else {
                    msg_listData.what = MESSAGETYPE_01;
                }
                handler3.sendMessage(msg_listData);
            }
        }).start();

    }

    /** handler3 */
    private Handler handler3 = new Handler() {
        public void handleMessage(Message message) {
            switch (message.what) {
                case MESSAGETYPE_01:
                    if (null != progressDialog_getInfo) {
                        progressDialog_getInfo.dismiss();
                    }
                    View messageView = LayoutInflater.from(MainOneActivity.this).inflate(R.layout.base_dialog_info,
                            null);
                    // 分页设置
                    final WheelView country = (WheelView) messageView.findViewById(R.id.country);
                    country.setVisibleItems(1);
                    country.setViewAdapter(new CountryAdapter(MainOneActivity.this, list_jd));
                    country.addScrollingListener(new OnWheelScrollListener() {
                        public void onScrollingStarted(WheelView wheel) {
                        }

                        public void onScrollingFinished(WheelView wheel) {
                            if (null != list_jd.get(Sysindex)) {
                                baseDict = list_jd.get(Sysindex);
                            }

                        }
                    });
                    country.addChangingListener(new OnWheelChangedListener() {
                        public void onChanged(WheelView wheel, int oldValue, int newValue) {
                            Sysindex = newValue;
                        }
                    });
                    country.setCurrentItem(0);
                    baseDict = list_jd.get(0);
                    Sysindex = 0;
                    Dialog delDia = new AlertDialog.Builder(MainOneActivity.this)
                            .setIcon(R.drawable.qq_dialog_default_icon).setTitle("请选择网格！").setView(messageView)
                            .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dia, int which) {
                                    myImageButton.setText(baseDict.getSbdName());
                                    dia.dismiss();
                                }
                            }).create();
                    delDia.show();
                    break;
                case MESSAGETYPE_02:
                    if (null != progressDialog_getInfo) {
                        progressDialog_getInfo.dismiss();
                    }
                    Toast.makeText(getApplicationContext(), "请检查网络是否可用！", 0).show();
                    // myImageButton.setText("");
                    break;
                default:
                    break;
            }
        }
    };

    /**
     * 分页适配器 2014-7-22下午5:56:27 类CountryAdapter
     * 
     * @author Mars zhang
     * 
     */
    private class CountryAdapter extends AbstractWheelTextAdapter {
        // Countries names
        /** MemberVariables */
        List<SysBaseDict> list;
        /** MemberVariables */
        int i = 0;

        protected CountryAdapter(Context context, List list) {
            super(context, R.layout.tempitem, NO_RESOURCE);
            this.list = list;
            setItemTextResource(R.id.tempValue);
        }

        // Countries flags
        // private int flags =R.drawable.tem_unit_dialog;

        @Override
        public View getItem(int index, View cachedView, ViewGroup parent) {
            View view = super.getItem(index, cachedView, parent);
            // ImageView img = (ImageView) view.findViewById(R.id.tempImag);
            // img.setImageResource(flags);
            return view;
        }

        @Override
        public int getItemsCount() {
            return list.size();
        }

        @Override
        protected CharSequence getItemText(int index) {
            return (null != list.get(index) ? list.get(index).getSbdName() + "" : "");
        }

    }

    // /**
    // *
    // * 2014-7-22下午5:12:00 类XmlHiddenDangerGetDangersInfoIdsData
    // *
    // * @author Mars zhang
    // *
    // */
    // public class XmlHiddenDangerGetDangersInfoIdsData {
    // /** MemberVariables */
    // private ArrayList<SysBaseDict> list = null;
    //
    // /**
    // *
    // * @param path
    // * @return
    // */
    // public ArrayList<SysBaseDict> getData(final String path) {
    // try {
    // URL url = new URL(path);
    // SysBaseDict baseDict = null;
    // HttpURLConnection conn = (HttpURLConnection) url
    // .openConnection();
    // conn.setRequestMethod("GET");
    // conn.setConnectTimeout(5000);
    // if (conn.getResponseCode() == 200) {
    // InputStream inputstream = conn.getInputStream();
    // XmlPullParser xml = Xml.newPullParser();
    // xml.setInput(inputstream, "UTF-8");
    // int event = xml.getEventType();
    // while (event != XmlPullParser.END_DOCUMENT) {
    //
    // switch (event) {
    // // 开始解析文档
    // case XmlPullParser.START_DOCUMENT:
    // list = new ArrayList<SysBaseDict>();
    // break;
    // case XmlPullParser.START_TAG:
    //
    // String value = xml.getName();
    // if (value.equals("SysBaseDict")) {
    // baseDict = new SysBaseDict();
    // } else if (value.equals("SbdId")) {
    // baseDict.setSbdId(xml.nextText());
    // } else if (value.equals("SbdName")) {
    // baseDict.setSbdName(xml.nextText());
    // }
    // break;
    // case XmlPullParser.END_TAG:
    // if (xml.getName().equals("SysBaseDict")) {
    // list.add(baseDict);
    // baseDict = null;
    // }
    // break;
    // default:
    // break;
    // }
    // // 解析下一个对象
    // event = xml.next();
    // }
    // return list;
    // }
    // } catch (Exception e) {
    // e.printStackTrace();
    // }
    // return null;
    //
    // }

    // /**
    // * getTreeOne
    // */
    // private void getTreeOne(String str) {
    // progressDialog_getInfo = ProgressDialog.show(MainOneActivity.this,
    // "提示", "正在获取数据，请稍等...");
    // new Thread() {
    // public void run() {
    // Message msg_listData = new Message();
    // list.removeAll(list);
    // list.add("城镇1");
    // list.add("城镇2");
    // list.add("城镇3");
    // list.add("城镇4");
    // list.add("城镇5");
    // list.add("城镇6");
    // msg_listData.what = MESSAGETYPE_01;
    // handler1.sendMessage(msg_listData);
    // }
    // }.start();
    // }
    //
    // /** handler1 */
    // private Handler handler1 = new Handler() {
    // public void handleMessage(Message message) {
    // switch (message.what) {
    // case MESSAGETYPE_01:
    // progressDialog_getInfo.dismiss();
    // View messageView = LayoutInflater.from(MainOneActivity.this)
    // .inflate(R.layout.base_dialog_info, null);
    // // 分页设置
    // final WheelView country = (WheelView) messageView
    // .findViewById(R.id.country);
    // country.setVisibleItems(3);
    // country.setViewAdapter(new CountryAdapter(MainOneActivity.this,
    // list));
    // country.addScrollingListener(new OnWheelScrollListener() {
    // public void onScrollingStarted(WheelView wheel) {
    // }
    //
    // public void onScrollingFinished(WheelView wheel) {
    // }
    // });
    // country.addChangingListener(new OnWheelChangedListener() {
    // public void onChanged(WheelView wheel, int oldValue,
    // int newValue) {
    // }
    // });
    // country.setCurrentItem(1);
    //
    // Dialog delDia = new AlertDialog.Builder(MainOneActivity.this)
    // .setIcon(R.drawable.qq_dialog_default_icon)
    // .setTitle("请选择乡镇！")
    // .setView(messageView)
    // .setPositiveButton("确认",
    // new DialogInterface.OnClickListener() {
    // @Override
    // public void onClick(DialogInterface dia,
    // int which) {
    // dia.dismiss();
    // }
    // })
    // .setNegativeButton("下一级",
    // new DialogInterface.OnClickListener() {
    // @Override
    // public void onClick(DialogInterface dia,
    // int which) {
    // dia.dismiss();
    // getTreeTwo("");
    // }
    // }).create();
    // delDia.show();
    // break;
    // case MESSAGETYPE_02:
    // progressDialog_getInfo.dismiss();
    // Toast.makeText(getApplicationContext(), "请检查网络是否可用", 0).show();
    //
    // break;
    // default:
    // break;
    // }
    // }
    // };
    //
    // /**
    // * getTreeTwo
    // */
    // private void getTreeTwo(String oneStr) {
    // progressDialog_getInfo = ProgressDialog.show(MainOneActivity.this,
    // "提示", "正在获取数据，请稍等...");
    // new Thread() {
    // public void run() {
    // Message msg_listData = new Message();
    // list.removeAll(list);
    // list.add("社区1");
    // list.add("社区2");
    // list.add("社区3");
    // list.add("社区4");
    // list.add("社区5");
    // list.add("社区6");
    // msg_listData.what = MESSAGETYPE_01;
    // handler2.sendMessage(msg_listData);
    // }
    // }.start();
    // }
    //
    // /** handler2 */
    // private Handler handler2 = new Handler() {
    // public void handleMessage(Message message) {
    // switch (message.what) {
    // case MESSAGETYPE_01:
    // progressDialog_getInfo.dismiss();
    // View messageView = LayoutInflater.from(MainOneActivity.this)
    // .inflate(R.layout.base_dialog_info, null);
    // // 分页设置
    // final WheelView country = (WheelView) messageView
    // .findViewById(R.id.country);
    // country.setVisibleItems(3);
    // country.setViewAdapter(new CountryAdapter(MainOneActivity.this,
    // list));
    // country.addScrollingListener(new OnWheelScrollListener() {
    // public void onScrollingStarted(WheelView wheel) {
    // }
    //
    // public void onScrollingFinished(WheelView wheel) {
    // }
    // });
    // country.addChangingListener(new OnWheelChangedListener() {
    // public void onChanged(WheelView wheel, int oldValue,
    // int newValue) {
    // }
    // });
    // country.setCurrentItem(1);
    //
    // Dialog delDia = new AlertDialog.Builder(MainOneActivity.this)
    // .setIcon(R.drawable.qq_dialog_default_icon)
    // .setTitle("请选择社区！")
    // .setView(messageView)
    // .setPositiveButton("确认",
    // new DialogInterface.OnClickListener() {
    // @Override
    // public void onClick(DialogInterface dia,
    // int which) {
    // dia.dismiss();
    // }
    // })
    // .setNegativeButton("下一级",
    // new DialogInterface.OnClickListener() {
    // @Override
    // public void onClick(DialogInterface dia,
    // int which) {
    // dia.dismiss();
    // getTreeThree("");
    // }
    // }).create();
    // delDia.show();
    // break;
    // case MESSAGETYPE_02:
    // progressDialog_getInfo.dismiss();
    // Toast.makeText(getApplicationContext(), "请检查网络是否可用", 0).show();
    //
    // break;
    // default:
    // break;
    // }
    // }
    // };
    //
    // /**
    // * getTreeTwo
    // */
    // private void getTreeThree(String oneStr) {
    // progressDialog_getInfo = ProgressDialog.show(MainOneActivity.this,
    // "提示", "正在获取数据，请稍等...");
    // new Thread() {
    // public void run() {
    // Message msg_listData = new Message();
    // list.removeAll(list);
    // list.add("网格1");
    // list.add("网格2");
    // list.add("网格3");
    // list.add("网格4");
    // list.add("网格5");
    // list.add("网格6");
    // msg_listData.what = MESSAGETYPE_01;
    // handler3.sendMessage(msg_listData);
    // }
    // }.start();
    // }
    //
    // /** handler3 */
    // private Handler handler3 = new Handler() {
    // public void handleMessage(Message message) {
    // switch (message.what) {
    // case MESSAGETYPE_01:
    // progressDialog_getInfo.dismiss();
    // View messageView = LayoutInflater.from(MainOneActivity.this)
    // .inflate(R.layout.base_dialog_info, null);
    // // 分页设置
    // final WheelView country = (WheelView) messageView
    // .findViewById(R.id.country);
    // country.setVisibleItems(3);
    // country.setViewAdapter(new CountryAdapter(MainOneActivity.this,
    // list));
    // country.addScrollingListener(new OnWheelScrollListener() {
    // public void onScrollingStarted(WheelView wheel) {
    // }
    //
    // public void onScrollingFinished(WheelView wheel) {
    // }
    // });
    // country.addChangingListener(new OnWheelChangedListener() {
    // public void onChanged(WheelView wheel, int oldValue,
    // int newValue) {
    // }
    // });
    // country.setCurrentItem(1);
    //
    // Dialog delDia = new AlertDialog.Builder(MainOneActivity.this)
    // .setIcon(R.drawable.qq_dialog_default_icon)
    // .setTitle("请选择网格！")
    // .setView(messageView)
    // .setPositiveButton("确认",
    // new DialogInterface.OnClickListener() {
    // @Override
    // public void onClick(DialogInterface dia,
    // int which) {
    // dia.dismiss();
    // }
    // }).create();
    // delDia.show();
    // break;
    // case MESSAGETYPE_02:
    // progressDialog_getInfo.dismiss();
    // Toast.makeText(getApplicationContext(), "请检查网络是否可用", 0).show();
    //
    // break;
    // default:
    // break;
    // }
    // }
    // };
    //
    // /**
    // * 分页适配器 2014-7-22下午5:56:27 类CountryAdapter
    // *
    // * @author Mars zhang
    // *
    // */
    // private class CountryAdapter extends AbstractWheelTextAdapter {
    // // Countries names
    // /** MemberVariables */
    // List list;
    // /** MemberVariables */
    // int i = 0;
    //
    // protected CountryAdapter(Context context, List list) {
    // super(context, R.layout.tempitem, NO_RESOURCE);
    // this.list = list;
    // setItemTextResource(R.id.tempValue);
    // }
    //
    // // Countries flags
    // // private int flags =R.drawable.tem_unit_dialog;
    //
    // @Override
    // public View getItem(int index, View cachedView, ViewGroup parent) {
    // View view = super.getItem(index, cachedView, parent);
    // // ImageView img = (ImageView) view.findViewById(R.id.tempImag);
    // // img.setImageResource(flags);
    // return view;
    // }
    //
    // @Override
    // public int getItemsCount() {
    // return list.size();
    // }
    //
    // @Override
    // protected CharSequence getItemText(int index) {
    // return list.get(index) + "";
    // }
    //
    // }
}
// }
