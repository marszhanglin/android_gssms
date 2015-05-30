/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * 
 */
package net.evecom.android.log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Calendar;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import net.evecom.android.R;
import net.evecom.android.bean.FileManageBean;
import net.evecom.android.util.HttpUtil;
import net.evecom.android.util.ShareUtil;
import net.tsz.afinal.FinalDb;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;
/**
 * 
 * 描述DailyLogLookActivity
 * @author Mars zhang
 * @created 2014-11-5 上午11:02:40
 */
public class DailyLogLookActivity extends Activity {
    /** MemberVariables */
    private static final int MESSAGETYPE_01 = 0x0001;// 用于判断是否发送成功
    /** MemberVariables */
    private static final int MESSAGETYPE_02 = 0x0002;
    /** 标题 */
    private EditText btEditText;
    /** 所属网格 */
    private Button sswgButton;
    /** 日志类型 */
    private Button rzlxButton;
    /** 日志类型数组 */
    private String[] rzlxs;
    /** 来源类型 */
    private int sourceway;
    /** 工作时间 */
    private Button gzsjButton;
    /** 日志内容 */
    private EditText rznrEditText;
    /** 备注 */
    private EditText bzEditText;
    /** handler */
    private Handler handler = null;

    /** Calendar */
    private Calendar calendar = null;
    /** 进度条 */
    private ProgressDialog progressDialog;
    /** 日期时间 */
    private String gzsj = "";
    /** 年 */
    private int years;
    /** 月 */
    private int month;
    /** 日 */
    private int day;
    /** MemberVariables */
    boolean isprogressDialogCanClose = false;
    /** MemberVariables */
    Message msg_listData2 = null;
    /** MemberVariables */
    Message msg_listData = null;
    /** sss */
    private String sss = "";
    /** listopinionPerson */
    String listlogPerson = "";
    /** 数据库 */
    private FinalDb db;
    /** sysid */
    String sysid = "";
    /** areaname */
    String areaname = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.daily_log_look_activity);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        // init1();
        Intent intent = getIntent();
        sysid = intent.getStringExtra("sysid");
        areaname = intent.getStringExtra("areaname");
        init();
        initView();
        getXmlAndSetList();
    }

    /**
     * 初始化数据
     */
    private void init() {
        rzlxs = new String[] { "日常工作", "消防宣传" };
    }

    /**
     * 初始化数据
     */
    private void init1() {
        /** 清空数据 */
        db = FinalDb.create(this);
        db.deleteAll(FileManageBean.class);
    }

    /**
     * 初始化界面
     */
    private void initView() {
        btEditText = (EditText) findViewById(R.id.daily_log_look_bt);
        sswgButton = (Button) findViewById(R.id.daily_log_look_sswg);
        rzlxButton = (Button) findViewById(R.id.daily_log_look_rzlx);
        gzsjButton = (Button) findViewById(R.id.daily_log_look_gzsj_btn);
        rznrEditText = (EditText) findViewById(R.id.daily_log_look_rznr);
        bzEditText = (EditText) findViewById(R.id.daily_log_look_bz);

        calendar = Calendar.getInstance();// 获取当前时间
        listener();
    }

    /**
     * 监听定义
     */
    private void listener() {

        gzsjButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog alertdialog = new AlertDialog.Builder(DailyLogLookActivity.this).setTitle("工作时间")
                        .setPositiveButton("日期", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Dialog dialog1 = new DatePickerDialog(DailyLogLookActivity.this,
                                        new DatePickerDialog.OnDateSetListener() {

                                            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                                    int dayOfMonth) {
                                                String MonthOfYear = "";
                                                String DayOfMonth = "";
                                                years = year;
                                                month = monthOfYear + 1;
                                                day = dayOfMonth;
                                                if (monthOfYear < 9) {

                                                    MonthOfYear = "0" + month;
                                                } else {
                                                    MonthOfYear = "" + month;
                                                }
                                                if (dayOfMonth < 9) {
                                                    DayOfMonth = "0" + dayOfMonth;
                                                } else {
                                                    DayOfMonth = "" + dayOfMonth;
                                                }
                                                gzsjButton.setText("" + year + "-" + MonthOfYear + "-" + DayOfMonth);

                                                gzsj = years + "-" + month + "-" + day;
                                            }
                                        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar
                                                .get(Calendar.DAY_OF_MONTH));// 默认为系统时间
                                dialog1.show();
                            }
                        })
                        // .setNeutralButton("时间", new
                        // DialogInterface.OnClickListener() {
                        // @Override
                        // public void onClick(DialogInterface dialog,int which)
                        // {
                        // Dialog dialog2 = new TimePickerDialog(
                        // DailyLogLookActivity.this,
                        // new TimePickerDialog.OnTimeSetListener() {
                        // public void onTimeSet(TimePicker view, int hour, int
                        // minute) {
                        // gzsjButton.setText("" + years + "-"
                        // + month
                        // + "-" + day +" "
                        // + hour +":"
                        // + minute+":"
                        // + calendar.get(Calendar.SECOND));
                        // hours= hour;
                        // minutes = minute;
                        // gzsj= years + "-"+ month + "-" + day
                        // +"+" + hours + "%3A" + minutes + "%3A" +
                        // calendar.get(Calendar.SECOND);
                        // }
                        // }, calendar.get(Calendar.HOUR_OF_DAY), calendar
                        // .get(Calendar.MINUTE), true);// 默认为系统时间
                        // dialog2.show();
                        // }
                        // })
                        .setNegativeButton("清空", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                gzsjButton.setText("");

                                gzsj = "";

                            }
                        }).show();
            }
        });
        // 日志类型按钮点击
        rzlxButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Dialog schDia = new AlertDialog.Builder(DailyLogLookActivity.this).setIcon(R.drawable.login_error_icon)
                        .setTitle("请选择").setItems(rzlxs, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dia, int which) {
                                rzlxButton.setText(rzlxs[which]);
                                sourceway = which + 1;
                            }
                        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dia, int which) {
                                dia.dismiss();
                            }
                        }).create();
                schDia.show();
            }
        });
    }

    /**
     * 开一条子线程加载网络数据
     */
    private void getXmlAndSetList() {
        Runnable runnable = new Runnable() {
            public void run() {
                // Message msg_listData3 = new Message();
                String strUrl = HttpUtil.BASE_URL + "teventAndroid/detailDailyLogAndroid?" + "sysid=" + sysid;
                try {
                    sss = connServerForResult(strUrl);
                } catch (ClientProtocolException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                listlogPerson = sss;
                handler.sendMessage(handler.obtainMessage(0, listlogPerson));
            }
        };
        try {
            // 开启线程
            new Thread(runnable).start();
            // handler与线程之间的通信及数据处理
            handler = new Handler() {
                public void handleMessage(Message msg) {
                    if (msg.what == 0) {
                        BinderListData(listlogPerson);

                    }

                }
            };

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 绑定数据
     * 
     * @param listopinionPerson2
     * @throws JSONException
     */
    public void BinderListData(String listopinionPerson2) {
        try {

            JSONArray jsonArray = null;

            jsonArray = new JSONArray(listopinionPerson2);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject2 = jsonArray.getJSONObject(i);
                btEditText.setText(jsonObject2.getString("atta1"));
                sswgButton.setText(areaname);
                if ("1".equals(jsonObject2.getString("logtype"))) {
                    rzlxButton.setText(rzlxs[0]);
                    sourceway = 1;
                } else if ("2".equals(jsonObject2.getString("logtype"))) {
                    rzlxButton.setText(rzlxs[1]);
                    sourceway = 2;
                }
                // else if("3".equals(jsonObject2.getString("logtype"))){
                // rzlxButton.setText(rzlxs[2] );
                // sourceway = 3;
                // }
                // else if("4".equals(jsonObject2.getString("logtype"))){
                // rzlxButton.setText(rzlxs[3] );
                // sourceway = 4;
                // }
                // else if("5".equals(jsonObject2.getString("logtype"))){
                // rzlxButton.setText(rzlxs[4] );
                // sourceway = 5;
                // }
                // else if("6".equals(jsonObject2.getString("logtype"))){
                // rzlxButton.setText(rzlxs[5] );
                // sourceway = 6;
                // }
                gzsjButton.setText(jsonObject2.getString("logdate"));
                rznrEditText.setText(jsonObject2.getString("logwork"));
                bzEditText.setText(jsonObject2.getString("notes"));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * 修改提交数据到服务器
     * 
     * @param v
     */
    public void daily_log_xg(View v) {
        submit();
    }

    /**
     * 修改
     */
    private String submit() {
        /** 数据校验 */
        if (btEditText.getText().toString().trim().length() < 1) {
            DialogToast("请填写摘要！");
            return "";
        } else if (gzsjButton.getText().toString().trim().length() < 1) {
            DialogToast("工作时间不能为空！");
            return "";
        } else if (rzlxButton.getText().toString().trim().length() < 1) {
            DialogToast("工作类型不能为空！");
            return "";
        } else if (rznrEditText.getText().toString().trim().length() < 1) {
            DialogToast("日志内容不能为空！");
            return "";
        } else if (years > calendar.get(Calendar.YEAR)) {
            DialogToast("工作时间不能超过当前时间！");
            return "";
        } else if (years == calendar.get(Calendar.YEAR)) {
            if (month > calendar.get(Calendar.MONTH) + 1) {
                DialogToast("工作时间不能超过当前时间！");
                return "";
            } else if (month == calendar.get(Calendar.MONTH) + 1) {
                if (day > calendar.get(Calendar.DAY_OF_MONTH)) {
                    DialogToast("工作时间不能超过当前时间！");
                    return "";
                }
            }
        }
        final AlertDialog.Builder builder = new AlertDialog.Builder(DailyLogLookActivity.this);
        builder.setTitle("提示信息");
        builder.setIcon(R.drawable.qq_dialog_default_icon);// 图标
        builder.setMessage("是否确定要提交？");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            // @Override
            public void onClick(DialogInterface dialog, int which) {
                progressDialog = ProgressDialog.show(DailyLogLookActivity.this, "提示", "正在提交，请稍等...");

                formSubmit();

            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            // @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
        return "1";
    }

    /**
     * 文本提交
     */
    private void formSubmit() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Message msg_listData = new Message();
                    String a = HttpUtil.BASE_URL + "teventAndroid/updateIndfoDailyLogAndroid";
                    String b = "sysid=" + sysid + "&personLoggin.ATTA1=" + btEditText.getText().toString().trim()
                            + "&personLoggin.LOGTYPE=" + sourceway + "&personLoggin.LOGDATE="
                            + gzsjButton.getText().toString().trim() + "&personLoggin.LOGWORK="
                            + rznrEditText.getText().toString().trim() + "&personLoggin.NOTES="
                            + bzEditText.getText().toString().trim();
                    // +"&areaName=连江县"
                    // +"&eventId="
                    // +"&social.EVENTNM="+
                    // ztEditText.getText().toString().trim()
                    // +"&social.LATITUDE="+ShareUtil.getString(getApplicationContext(),"GPS",
                    // "GPS_longitude", "0.0")
                    // +"&social.LONGITUDE="+ShareUtil.getString(getApplicationContext(),"GPS",
                    // "GPS_latitude", "0.0")
                    // +"&social.OCCURADDR="+fsddEditText.getText().toString().trim()
                    // +"&social.SOURCEWAY="+sourceway
                    // +"&social.OCCURTIME="+lysj
                    // +"&social.SOURCEPEOPLE="+lyrEditText.getText().toString().trim()
                    // +"&social.PHONE="+lxdhEditText.getText().toString().trim()
                    // +"&social.NEWS="+xxnrEditText.getText().toString().trim()
                    // +"&uploadFile=";
                    // +"&attachId_=";
                    // System.out.println(furl);
                    try {
                        sss = connServerForResultPost(a, b);
                    } catch (Exception e) {
                        msg_listData.what = MESSAGETYPE_02;
                    }
                    if (sss == null || "".equals(sss) || sss.contains("failure")) {
                        msg_listData.what = MESSAGETYPE_02;
                    } else {
                        msg_listData.what = MESSAGETYPE_01;
                    }
                    handler4.sendMessage(msg_listData);
                } catch (Exception e) {
                    if (null != e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    /** 文件进度条使用 */
    Handler handler4 = new Handler() {
        public void handleMessage(Message message) {
            switch (message.what) {
                case MESSAGETYPE_01:
                    if (null != progressDialog) {
                        progressDialog.dismiss();
                    }
                    DailyLogLookActivity.this.finish();
                    Intent intent = new Intent(getApplicationContext(), DailyLogListActivity.class);
                    startActivity(intent);
                    Toast.makeText(getApplicationContext(), "提交成功", 0).show();
                    break;
                case MESSAGETYPE_02:
                    if (null != progressDialog) {
                        progressDialog.dismiss();
                    }
                    Toast.makeText(getApplicationContext(), "提交失败，请检查网络是否可用", 0).show();
                    break;
                default:
                    break;
            }
        }
    };

    /**
     * 
     * @param strUrl
     * @return
     * @throws IOException
     * @throws ClientProtocolException
     */
    private String connServerForResult(String strUrl) throws ClientProtocolException, IOException {
        // HttpGet对象
        HttpGet httpRequest = new HttpGet(strUrl);
        String strResult = "";
        // HttpClient对象
        BasicHttpParams httpParams = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(httpParams, 5000);// 设置超时时间
        HttpClient httpClient = new DefaultHttpClient(httpParams);
        // 获得HttpResponse对象
        HttpResponse httpResponse = httpClient.execute(httpRequest);
        if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            // 取得返回的数据
            strResult = EntityUtils.toString(httpResponse.getEntity());
        }
        return strResult;
    }

    /**
     * 删除社情民意
     * 
     * @param v
     */
    public void daily_log_more_delete(View v) {

        final AlertDialog.Builder builder = new AlertDialog.Builder(DailyLogLookActivity.this);
        builder.setTitle("提示信息");
        builder.setIcon(R.drawable.qq_dialog_default_icon);// 图标
        builder.setMessage("是否确定要删除？");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            // @Override
            public void onClick(DialogInterface dialog, int which) {
                progressDialog = ProgressDialog.show(DailyLogLookActivity.this, "提示", "正在删除，请稍等...");

                formSubmit1();

            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            // @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();

    }

    /**
     * 文本提交
     */
    private void formSubmit1() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Message msg_listData = new Message();
                    String a = HttpUtil.BASE_URL + "teventAndroid/deleteDailyLogAndroid";
                    String b = "sysid=" + sysid;

                    try {
                        sss = connServerForResultPost(a, b);
                    } catch (Exception e) {
                        msg_listData.what = MESSAGETYPE_02;
                    }
                    if (sss == null || "".equals(sss) || sss.contains("failure")) {
                        msg_listData.what = MESSAGETYPE_02;
                    } else {
                        msg_listData.what = MESSAGETYPE_01;
                    }
                    handler1.sendMessage(msg_listData);
                } catch (Exception e) {
                    if (null != e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
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
    Handler handler1 = new Handler() {
        public void handleMessage(Message message) {
            switch (message.what) {
                case MESSAGETYPE_01:
                    // 刷新UI，显示数据，并关闭进度条
                    finish();
                    Intent intent = new Intent(getApplicationContext(), DailyLogListActivity.class);
                    startActivity(intent);
                    Toast.makeText(getApplicationContext(), "删除成功", 0).show();

                    break;
                case MESSAGETYPE_02:
                    if (null != progressDialog) {
                        progressDialog.dismiss();
                    }
                    Toast.makeText(getApplicationContext(), "文本删除失败，请检查网络是否可用", 0).show();
                    break;
                default:
                    break;
            }
        }
    };

    /**
     * fh
     * 
     * @param v
     */
    public void logfh(View v) {

        final AlertDialog.Builder builder = new AlertDialog.Builder(DailyLogLookActivity.this);
        builder.setTitle("提示信息");
        builder.setIcon(R.drawable.qq_dialog_default_icon);// 图标
        builder.setMessage("修改内容如未保存，数据将会丢失，是否确定退出？");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            // @Override
            public void onClick(DialogInterface dialog, int which) {
                DailyLogLookActivity.this.finish();
                Intent intent = new Intent(getApplicationContext(), DailyLogListActivity.class);
                startActivity(intent);

            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            // @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();

    }

    /**
     * 错误填报提示信息
     * 
     * @param errorMsg
     */
    private void DialogToast(String errorMsg) {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(DailyLogLookActivity.this);
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
