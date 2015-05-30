/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * 
 */
package net.evecom.android;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

import net.evecom.android.bean.FileManageBean;
import net.evecom.android.bean.FileManageBean1;
import net.evecom.android.bean.OpinionPerson;
import net.evecom.android.gps.GPSActivity;
import net.evecom.android.gps.tdt.TDTLocation;
import net.evecom.android.util.HttpUtil;
import net.evecom.android.util.UiUtil;
import net.tsz.afinal.FinalDb;
import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
/**
 * 
 * 描述PublicOpinionLookActivity
 * @author Mars zhang
 * @created 2014-11-5 上午11:01:14
 */
public class PublicOpinionLookActivity extends Activity {
    /** MemberVariables */
    private static final int MESSAGETYPE_01 = 0x0001;// 用于判断是否发送成功
    /** MemberVariables */
    private static final int MESSAGETYPE_02 = 0x0002;
    /** 主题 */
    private EditText ztEditText; // public_opinion_add_bt
    /** 发生地点 */
    private EditText fsddEditText; // public_opinion_add_fsdd
    /** 来源类型 */
    private Button lylxButton; // public_opinion_add_lylx
    /** 来源类型数组 */
    private String[] lylxs;
    /** 来源类型 */
    private int sourceway;
    /** 来源时间 */
    private Button lysjButton; // public_opinion_add_lysj_btn
    /** 状态 */
    private Button ztButton; // public_opinion_look_zt
    /** 状态数组 */
    private String[] zt;
    /** 状态 */
    private String zt1 = "";
    /** 来源人 */
    private EditText lyrEditText; // public_opinion_add_lyr
    /** 联系电话 */
    private EditText lxdhEditText; // public_opinion_add_lxdh
    /** 信息内容 */
    private EditText xxnrEditText; // public_opinion_add_xxnr
    /** sss */
    private String sss = "";
    /** MemberVariables */
    String listopinionPerson = "";
    /** 数据库 */
    private FinalDb db;
    /** 图片列表 */
    private List<FileManageBean> fileListg;
    /** 上传文件 */
    private ListView fileListView; // add_hidden_atdanger_picture_list
    /** 上传文件 */
    private ListView fileListView1; // add_hidden_atdanger_picture_list
    /** 图片适配器 */
    private UploadPictureAdapter uploadPictureAdapter;
    /** PIC */
    private static final String FILE_TYPE_PIC_01 = "1";
    /** VIDEO */
    private static final String FILE_TYPE_VIDEO_02 = "2";
    /** AUDIO */
    private static final String FILE_TYPE_AUDIO_03 = "3";
    /** MemberVariables */
    boolean isprogressDialogCanClose = false;
    /** MemberVariables */
    Message msg_listData2 = null;
    /** MemberVariables */
    Message msg_listData = null;
    /** 传递Id */
    private String Id;
    /** handler */
    private Handler handler = null;
    /** 成员变量 */
    private List<OpinionPerson> opinionPerson = new ArrayList<OpinionPerson>();
    /** 成员变量 */
    private List<FileManageBean1> fileManageBean1 = new ArrayList<FileManageBean1>();
    /** 进度条 */
    private ProgressDialog progressDialog;
    /** uaid */
    private String uaid = "";
    /** 成员变量 */
    private FileManageBeanAdapter fileManageBeanAdapter = null;

    /** Calendar */
    private Calendar calendar = null;
    /** 日期时间 */
    private String lysj = "";
    /** 年 */
    private int years;
    /** 月 */
    private int month;
    /** 日 */
    private int day;
    /** 时 */
    private int hours;
    /** 分 */
    private int minutes;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.public_opinion_look_activity);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        Intent intent = getIntent();
        Id = intent.getStringExtra("Id");
        init();
        init1();
        initView();
        getXmlAndSetList();
        // adapter();

    }

    /**
     * 初始化数据
     */
    private void init() {
        lylxs = new String[] { "短信录入", "网站录入", "热线电话", "审批转入", "走访录入", "手机录入" };
        zt = new String[] { "未处理", "转入事件", "已完成", "已关闭" };
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
        ztEditText = (EditText) findViewById(R.id.public_opinion_look_bt);
        fsddEditText = (EditText) findViewById(R.id.public_opinion_look_fsdd);
        lylxButton = (Button) findViewById(R.id.public_opinion_look_lylx);
        lysjButton = (Button) findViewById(R.id.public_opinion_look_lysj_btn);
        lyrEditText = (EditText) findViewById(R.id.public_opinion_look_lyr);
        lxdhEditText = (EditText) findViewById(R.id.public_opinion_look_lxdh);
        xxnrEditText = (EditText) findViewById(R.id.public_opinion_look_xxnr);
        ztButton = (Button) findViewById(R.id.public_opinion_look_zt);
        // fileListView = (ListView) findViewById(R.id.event_add_file_list);
        fileListView1 = (ListView) findViewById(R.id.event_add_file_list1);
        calendar = Calendar.getInstance();// 获取当前时间
        listener();
    }

    /**
     * 监听定义
     */
    private void listener() {

        lysjButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new AlertDialog.Builder(PublicOpinionLookActivity.this).setTitle("来源时间")
                        .setPositiveButton("日期", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Dialog dialog1 = new DatePickerDialog(PublicOpinionLookActivity.this,
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
                                                lysjButton.setText("" + year + "-" + MonthOfYear + "-" + DayOfMonth
                                                        + " " + hours + ":" + minutes + ":"
                                                        + calendar.get(Calendar.SECOND));
                                                // lysjButton.setText("" + years
                                                // + "-"
                                                // + month
                                                // + "-" + day +" "
                                                // + hours +":"
                                                // + minutes+":"
                                                // +
                                                // calendar.get(Calendar.SECOND));
                                                lysj = years + "-" + month + "-" + day + "+" + hours + "%3A" + minutes
                                                        + "%3A" + calendar.get(Calendar.SECOND);
                                            }
                                        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar
                                                .get(Calendar.DAY_OF_MONTH));// 默认为系统时间
                                dialog1.show();
                            }
                        }).setNeutralButton("时间", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Dialog dialog2 = new TimePickerDialog(PublicOpinionLookActivity.this,
                                        new TimePickerDialog.OnTimeSetListener() {
                                            public void onTimeSet(TimePicker view, int hour, int minute) {
                                                lysjButton.setText("" + years + "-" + month + "-" + day + " " + hour
                                                        + ":" + minute + ":" + calendar.get(Calendar.SECOND));
                                                hours = hour;
                                                minutes = minute;
                                                lysj = years + "-" + month + "-" + day + "+" + hours + "%3A" + minutes
                                                        + "%3A" + calendar.get(Calendar.SECOND);
                                            }
                                        }, calendar.get(Calendar.HOUR_OF_DAY), 
                                        calendar.get(Calendar.MINUTE), true);// 默认为系统时间
                                dialog2.show();
                            }
                        }).setNegativeButton("清空", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                lysjButton.setText("");

                                lysj = "";

                            }
                        }).show();
            }
        });
        // 来源类型按钮点击
        lylxButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Dialog schDia = new AlertDialog.Builder(PublicOpinionLookActivity.this)
                        .setIcon(R.drawable.login_error_icon).setTitle("请选择")
                        .setItems(lylxs, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dia, int which) {
                                lylxButton.setText(lylxs[which]);
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
     * 操作适配器
     */
    private void adapter() {
        fileListg = new ArrayList<FileManageBean>();
        uploadPictureAdapter = new UploadPictureAdapter(getApplicationContext(), fileListg);
        fileListView.setAdapter(uploadPictureAdapter);
    }

    /**
     * 开一条子线程加载网络数据
     */
    private void getXmlAndSetList() {
        Runnable runnable = new Runnable() {
            public void run() {
                // Message msg_listData3 = new Message();
                String strUrl = HttpUtil.BASE_URL + "teventAndroid/detailSocialAndroid?" + "Id=" + Id;
                try {
                    sss = connServerForResult(strUrl);
                } catch (ClientProtocolException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                listopinionPerson = sss;
                handler.sendMessage(handler.obtainMessage(0, listopinionPerson));
            }
        };
        try {
            // 开启线程
            new Thread(runnable).start();
            // handler与线程之间的通信及数据处理
            handler = new Handler() {
                public void handleMessage(Message msg) {
                    if (msg.what == 0) {
                        BinderListData(listopinionPerson);

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
                ztEditText.setText(jsonObject2.getString("eventname"));
                fsddEditText.setText(jsonObject2.getString("occuraddr"));
                if ("1".equals(jsonObject2.getString("sourceway"))) {
                    lylxButton.setText(lylxs[0]);
                    sourceway = 1;
                } else if ("2".equals(jsonObject2.getString("sourceway"))) {
                    lylxButton.setText(lylxs[1]);
                    sourceway = 2;
                } else if ("3".equals(jsonObject2.getString("sourceway"))) {
                    lylxButton.setText(lylxs[2]);
                    sourceway = 3;
                } else if ("4".equals(jsonObject2.getString("sourceway"))) {
                    lylxButton.setText(lylxs[3]);
                    sourceway = 4;
                } else if ("5".equals(jsonObject2.getString("sourceway"))) {
                    lylxButton.setText(lylxs[4]);
                    sourceway = 5;
                } else if ("6".equals(jsonObject2.getString("sourceway"))) {
                    lylxButton.setText(lylxs[5]);
                    sourceway = 6;
                }
                zt1 = jsonObject2.getString("eventstatus");
                if ("1".equals(jsonObject2.getString("eventstatus"))) {
                    ztButton.setText(zt[0]);

                } else if ("2".equals(jsonObject2.getString("eventstatus"))) {
                    ztButton.setText(zt[1]);
                } else if ("3".equals(jsonObject2.getString("eventstatus"))) {
                    ztButton.setText(zt[2]);
                } else if ("4".equals(jsonObject2.getString("eventstatus"))) {
                    ztButton.setText(zt[3]);
                }
                lysjButton.setText(jsonObject2.getString("occurtime"));
                lyrEditText.setText(jsonObject2.getString("sourcepeople"));
                lxdhEditText.setText(jsonObject2.getString("phone"));
                xxnrEditText.setText(jsonObject2.getString("news"));
                if (lysjButton.getText().length() > 1) {
                    String[] s = Pattern.compile(":").split(lysjButton.getText().toString().trim());
                    lysj = s[0] + "%3A" + s[1] + "%3A" + s[2];
                } else {
                    lysj = "";
                }
                uaid = jsonObject2.getString("photourl");
                getURI(uaid);

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /** 获取附件的地址 */
    private void getURI(final String Uaid) {
        // 开一条子线程加载网络数据
        Runnable runnable = new Runnable() {
            public void run() {
                String path = HttpUtil.BASE_URL + "teventAndroid/fileList?" + "ids=" + Uaid;
                try {
                    sss = connServerForResult(path);
                } catch (ClientProtocolException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                String listfileManageBean = sss;
                try {
                    fileManageBean1 = getPersons(listfileManageBean);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                handler.sendMessage(handler.obtainMessage(0, opinionPerson));
            }
        };
        try {
            // 开启线程
            new Thread(runnable).start();
            // handler与线程之间的通信及数据处理
            handler = new Handler() {
                public void handleMessage(Message msg) {
                    if (msg.what == 0) {
                        BinderListData1(fileManageBean1);
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
     * @param list
     */
    public void BinderListData1(List<FileManageBean1> list) {
        fileManageBeanAdapter = new FileManageBeanAdapter(getApplicationContext(), list);
        fileListView1.setAdapter(fileManageBeanAdapter);
        if (null != fileListView1) {
            UiUtil.setListViewHeightBasedOnChildren(fileListView1);
        }
    }

    /**
     * 解析json数据
     * 
     */
    public static List<FileManageBean1> getPersons(String jsonString) throws JSONException {
        List<FileManageBean1> list = new ArrayList<FileManageBean1>();

        JSONArray jsonArray = null;

        jsonArray = new JSONArray(jsonString);

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject2 = jsonArray.getJSONObject(i);
            FileManageBean1 fileManageBean1 = new FileManageBean1();

            if (isImagePicture(jsonObject2.getString("UA_TYPE"))) {
                fileManageBean1.setFile_ID(jsonObject2.getInt("UA_ID"));
                // fileManageBean.setFile_DESCRIP(jsonObject2.getString("eventname"));
                // fileManageBean.setFile_Time(jsonObject2.getString("occurtime"));
                fileManageBean1.setFile_URL(jsonObject2.getString("UA_PATH"));
                fileManageBean1.setFile_Flag(jsonObject2.getString("UA_TYPE"));
                // fileManageBean.setFile_by1(jsonObject2.getString("sourcepeople"));
                fileManageBean1.setFile_Name(jsonObject2.getString("UA_OLDNAME"));
                fileManageBean1.setName(jsonObject2.getString("UA_NAME"));

                list.add(fileManageBean1);
            }

        }
        return list;
    }

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
     * 修改社情民意
     * 
     * @param v
     */
    // public void opinionxg(View v) {
    // finish();
    // Intent intent = new Intent(getApplicationContext(),
    // PublicOpinionXiugaiActivity.class);
    // intent.putExtra("Id", Id);
    // intent.putExtra("eventname", ztEditText.getText().toString());
    // intent.putExtra("occuraddr", fsddEditText.getText().toString());
    // intent.putExtra("sourceway", lylxButton.getText().toString());
    // intent.putExtra("occurtime", lysjButton.getText().toString());
    // intent.putExtra("sourcepeople", lyrEditText.getText().toString());
    // intent.putExtra("phone", lxdhEditText.getText().toString());
    // intent.putExtra("news", xxnrEditText.getText().toString());
    //
    // startActivity(intent);
    // }
    //
    // /**
    // * 保存提交数据到服务器
    // *
    // * @param v
    // */
    public void opinionxg(View v) {
        submit();
    }

    /**
     * 保存
     */
    private String submit() {
        /** 数据校验 */
        if (ztEditText.getText().toString().trim().length() < 1) {
            DialogToast("请填写标题！");
            return "";
        } else if (fsddEditText.getText().toString().trim().length() < 1) {
            DialogToast("发生地点不能为空！");
            return "";
        } else if (lylxButton.getText().toString().trim().length() < 1) {
            DialogToast("来源方式不能为空！");
            return "";
        } else if (years == 0 || month == 0 || day == 0) {
            DialogToast("请设置日期！");
            return "";
        } else if (lyrEditText.getText().toString().trim().length() < 1) {
            DialogToast("来源人不能为空！");
            return "";
        } else if (xxnrEditText.getText().toString().trim().length() < 1) {
            DialogToast("消息内容不能为空！");
            return "";
        } else if (lxdhEditText.getText().toString().trim().length() >= 1) {
            if (isCellphone(lxdhEditText.getText().toString().trim()) == false) {
                DialogToast("联系电话不正确！");
                return "";
            }
        }
        if ("4".equals(zt1)) {
            DialogToast("请选择状态为未处理的记录！");
            return "";
        } else if ("2".equals(zt1)) {
            DialogToast("请选择状态为未处理的记录！");
            return "";
        } else if ("3".equals(zt1)) {
            DialogToast("请选择状态为未处理的记录！");
            return "";
        }
        final AlertDialog.Builder builder = new AlertDialog.Builder(PublicOpinionLookActivity.this);
        builder.setTitle("提示信息");
        builder.setIcon(R.drawable.qq_dialog_default_icon);// 图标
        builder.setMessage("是否确定要保存？");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            // @Override
            public void onClick(DialogInterface dialog, int which) {
                progressDialog = ProgressDialog.show(PublicOpinionLookActivity.this, "提示", "正在保存，请稍等...");

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
        return "1";
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
                    String a = HttpUtil.BASE_URL + "teventAndroid/updateInfoAndroid";
                    String b = "Id="
                            + Id
                            + "&social.EVENTNM="
                            + ztEditText.getText().toString().trim()
                            // +"&social.LATITUDE="+ShareUtil.getString(getApplicationContext(),"GPS",
                            // "GPS_longitude", "0.0")
                            // +"&social.LONGITUDE="+ShareUtil.getString(getApplicationContext(),"GPS",
                            // "GPS_latitude", "0.0")
                            + "&social.OCCURADDR=" + fsddEditText.getText().toString().trim() + "&social.SOURCEWAY="
                            + sourceway + "&social.OCCURTIME=" + lysj + "&social.SOURCEPEOPLE="
                            + lyrEditText.getText().toString().trim() + "&social.PHONE="
                            + lxdhEditText.getText().toString().trim() + "&social.NEWS="
                            + xxnrEditText.getText().toString().trim() + "&uploadFile=";
                    // +"&attachId_=";
                    // System.out.println(furl);
                    try {
                        sss = connServerForResultPost(a, b);
                        // sss = connServerForResult(furl);

                    } catch (Exception e) {
                        msg_listData.what = MESSAGETYPE_02;
                    }
                    if (sss == null || "".equals(sss) || sss.contains("failure")) {
                        msg_listData.what = MESSAGETYPE_02;
                    } else {
                        msg_listData.what = MESSAGETYPE_01;
                    }
                    handler2.sendMessage(msg_listData);
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
                    PublicOpinionLookActivity.this.finish();

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
    /** 进度条使用 */
    Handler handler2 = new Handler() {
        public void handleMessage(Message message) {
            switch (message.what) {
                case MESSAGETYPE_01:
                    // 刷新UI，显示数据，并关闭进度条
                    String[] s = Pattern.compile("@").split(sss);
                    // fileSubmitAfterForm(s[1]);
                    Intent intent = new Intent(getApplicationContext(), PublicOpinionlistActivity.class);
                    intent.putExtra("Id", Id);
                    startActivity(intent);
                    Toast.makeText(getApplicationContext(), "文本保存成功", 0).show();
                    finish();
                    break;
                case MESSAGETYPE_02:
                    if (null != progressDialog) {
                        progressDialog.dismiss();
                    }
                    Toast.makeText(getApplicationContext(), "文本保存失败，请检查网络是否可用", 0).show();
                    break;
                default:
                    break;
            }
        }
    };

    /**
     * 上传文件
     */
    private void fileSubmitAfterForm(String table_id) {
        msg_listData2 = new Message();
        if (fileListg.size() == 0) {
            if (null != progressDialog) {
                progressDialog.dismiss(); // 关闭进度条
            }
            return;
        }
        for (int i = 0; i < fileListg.size(); i++) {
            if (i == fileListg.size() - 1) {
                isprogressDialogCanClose = true;
            }
            AjaxParams params = new AjaxParams();
            try {
                params.put("profile_picture", new File(fileListg.get(i).getFile_URL()));
                params.put("uatable_id", table_id);
            } catch (FileNotFoundException e) {
                if (null != e) {
                    e.printStackTrace();
                }
            } // 上传文件
            FinalHttp fh = new FinalHttp();
            String furl = HttpUtil.BASE_URL + "teventAndroid/uploadSocial";
            fh.post(furl, params, new AjaxCallBack<String>() {
                @Override
                public void onLoading(long count, long current) {
                    System.out.println(current + "/" + count);
                }

                @Override
                public void onFailure(Throwable t, int errorNo, String strMsg) {
                    msg_listData2.what = MESSAGETYPE_02;
                    handler4.sendMessage(msg_listData2);
                    super.onFailure(t, errorNo, strMsg);
                    Toast.makeText(getApplicationContext(), "文件保存失败，请检查网络是否可用", 0).show();
                }

                @Override
                public void onSuccess(String t) {
                    super.onSuccess(t);
                    toast("上传成功！" + t);
                    if (isprogressDialogCanClose) {
                        msg_listData2.what = MESSAGETYPE_01;
                        handler4.sendMessage(msg_listData2);
                    }
                }
            });
        }
    }

    /**
     * 查找文件
     * 
     * @param v
     */
    public void findpicture_onclick(View v) {
        String[] filetypes = new String[] { "添加图片", "添加视频", "添加音频" };
        Dialog schDia = new AlertDialog.Builder(PublicOpinionLookActivity.this).setIcon(R.drawable.login_error_icon)
                .setTitle("请选择").setItems(filetypes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dia, int which) {
                        Intent intent = null;
                        switch (which) {
                            case 0:
                                intent = new Intent(getApplicationContext(), UploadPictureActivity.class);
                                startActivityForResult(intent, 2);
                                break;
                            case 1:
                                intent = new Intent(getApplicationContext(), UploadVideoActivity.class);
                                startActivityForResult(intent, 3);
                                break;
                            case 2:
                                intent = new Intent(getApplicationContext(), UploadAudioActivity.class);
                                startActivityForResult(intent, 5);
                                break;

                            default:
                                break;
                        }
                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dia, int which) {
                        dia.dismiss();
                    }
                }).create();
        schDia.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 1: // gps跳转回来
                if (resultCode == RESULT_OK) {
                    SharedPreferences sp = getApplicationContext().getSharedPreferences("GPS", MODE_PRIVATE);
                    String GPS_latitude = sp.getString("GPS_latitude", "0.0");
                    String GPS_longitude = sp.getString("GPS_longitude", "0.0");
                    // DialogToast("GPS_latitude="+GPS_latitude+"\nGPS_latitude"+GPS_longitude);
                    Toast.makeText(getApplicationContext(), "GPS_latitude=" + GPS_latitude + "\nGPS_latitude"
                            + GPS_longitude, 1);
                }
                break;
            case 2:
                String filePath = data.getStringExtra("filePath");
                db = FinalDb.create(this);
                FileManageBean bean = new FileManageBean();
                bean.setFile_URL(filePath);
                bean.setFile_Flag(FILE_TYPE_PIC_01);
                /** 重复添加文件 */
                List<FileManageBean> temp_pictures = db.findAllByWhere(FileManageBean.class, "File_URL=\"" + filePath
                        + "\"");
                toast(filePath);
                if ("".equals(bean.getFile_URL()) 
                        || "none".equals(bean.getFile_URL()) || null == bean.getFile_URL()) {// 没有选择
                    toast("请选择文件");
                    return;
                } else if (null == temp_pictures || temp_pictures.size() == 0) {// 没有重复
                    db.save(bean);
                } else {
                    DialogToast("该图片已经添加");
                }
                fileListg.removeAll(fileListg);
                List<FileManageBean> pictures_temp = db.findAll(FileManageBean.class);// 刷新文件List
                for (FileManageBean picture : pictures_temp) {
                    if (null != picture.getFile_URL()
                            && picture.getFile_URL().length() > 4
                            && (isImagePicture(picture.getFile_URL()) 
                                    || isVideoFile(picture.getFile_URL()) || isAudioFile(picture
                                    .getFile_URL()))) {
                        fileListg.add(picture);
                    }
                }
                uploadPictureAdapter.notifyDataSetChanged();
                if (null != fileListView) {
                    UiUtil.setListViewHeightBasedOnChildren(fileListView);
                }
                break;
            case 3:
                String videoPath = data.getStringExtra("filePath");
                db = FinalDb.create(this);
                FileManageBean videoBean = new FileManageBean();
                videoBean.setFile_URL(videoPath);
                videoBean.setFile_Flag(FILE_TYPE_VIDEO_02);
                /** 重复添加文件 */
                List<FileManageBean> temp_videos = db.findAllByWhere(FileManageBean.class, "File_URL=\"" + videoPath
                        + "\"");
                toast(videoPath);
                if ("".equals(videoBean.getFile_URL()) || "none".equals(videoBean.getFile_URL())
                        || null == videoBean.getFile_URL()) {// 没有选择
                    toast("请选择文件");
                    return;
                } else if (null == temp_videos || temp_videos.size() == 0) {// 没有重复
                    db.save(videoBean);
                } else {
                    DialogToast("该视频已经添加");
                }
                fileListg.removeAll(fileListg);
                List<FileManageBean> videos_temp = db.findAll(FileManageBean.class);// 刷新文件List
                for (FileManageBean video : videos_temp) {
                    if (null != video.getFile_URL()
                            && video.getFile_URL().length() > 4
                            && (isImagePicture(video.getFile_URL()) 
                                    || isVideoFile(video.getFile_URL()) || isAudioFile(video
                                    .getFile_URL()))) {
                        fileListg.add(video);
                    }
                }
                uploadPictureAdapter.notifyDataSetChanged();
                if (null != fileListView) {
                    UiUtil.setListViewHeightBasedOnChildren(fileListView);
                }
                break;
            case 4: // 刷新List 预览时可能有删除
                db = FinalDb.create(this);
                fileListg.removeAll(fileListg);
                List<FileManageBean> files_temp = db.findAll(FileManageBean.class);// 刷新文件List
                for (FileManageBean file : files_temp) {
                    if (null != file.getFile_URL()
                            && file.getFile_URL().length() > 4
                            && (isImagePicture(file.getFile_URL()) 
                                    || isVideoFile(file.getFile_URL()) || isAudioFile(file
                                    .getFile_URL()))) {
                        fileListg.add(file);
                    }
                }
                uploadPictureAdapter.notifyDataSetChanged();
                if (null != fileListView) {
                    UiUtil.setListViewHeightBasedOnChildren(fileListView);
                }
                break;
            case 5: // 录音
                String audioPath = data.getStringExtra("filePath");
                db = FinalDb.create(this);
                FileManageBean audioBean = new FileManageBean();
                audioBean.setFile_URL(audioPath);
                audioBean.setFile_Flag(FILE_TYPE_AUDIO_03);
                /** 重复添加文件 */
                List<FileManageBean> temp_audios = db.findAllByWhere(FileManageBean.class, "File_URL=\"" + audioPath
                        + "\"");
                toast(audioPath);
                if (null == audioBean.getFile_URL() || "".equals(audioBean.getFile_URL())
                        || "none".equals(audioBean.getFile_URL())) {// 没有选择
                    toast("请选择文件");
                    return;
                } else if (null == temp_audios || temp_audios.size() == 0) {// 没有重复
                    db.save(audioBean);
                } else {
                    DialogToast("该视频已经添加");
                }
                fileListg.removeAll(fileListg);
                List<FileManageBean> audios_temp = db.findAll(FileManageBean.class);// 刷新文件List
                for (FileManageBean audio : audios_temp) {
                    if (null != audio.getFile_URL()
                            && audio.getFile_URL().length() > 4
                            && (isImagePicture(audio.getFile_URL()) 
                                    || isVideoFile(audio.getFile_URL()) || isAudioFile(audio
                                    .getFile_URL()))) {
                        fileListg.add(audio);
                    }
                }
                uploadPictureAdapter.notifyDataSetChanged();
                if (null != fileListView) {
                    UiUtil.setListViewHeightBasedOnChildren(fileListView);
                }
                break;

            default:
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 删除社情民意
     * 
     * @param v
     */
    public void opinion_more_delete(View v) {

        final AlertDialog.Builder builder = new AlertDialog.Builder(PublicOpinionLookActivity.this);
        builder.setTitle("提示信息");
        builder.setIcon(R.drawable.qq_dialog_default_icon);// 图标
        builder.setMessage("是否确定要删除？");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            // @Override
            public void onClick(DialogInterface dialog, int which) {
                progressDialog = ProgressDialog.show(PublicOpinionLookActivity.this, "提示", "正在删除，请稍等...");

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
                    String a = HttpUtil.BASE_URL + "teventAndroid/deleteAndroid";
                    String b = "Id=" + Id;

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
                    Intent intent = new Intent(getApplicationContext(), PublicOpinionlistActivity.class);
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
     * 百度定位
     * 
     * @param v
     */
    public void event_look_gps_btn(View v) {
        // Intent intent = new Intent(getApplicationContext(),
        // GPSActivity.class);
        // startActivityForResult(intent, 1);
        Intent intent = new Intent(getApplicationContext(), TDTLocation.class);
        startActivityForResult(intent, 1);
    }

    /**
     * 返回
     * 
     * @param v
     */
    public void opinionfh(View v) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(PublicOpinionLookActivity.this);
        builder.setTitle("提示信息");
        builder.setIcon(R.drawable.qq_dialog_default_icon);// 图标
        builder.setMessage("修改内容如未保存，数据将会丢失，是否确定退出？");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            // @Override
            public void onClick(DialogInterface dialog, int which) {

                finish();
                Intent intent = new Intent(getApplicationContext(), PublicOpinionlistActivity.class);
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
     * 匿名适ListView配器类
     * 
     * @author SLQ shen
     */
    public class FileManageBeanAdapter extends BaseAdapter implements ListAdapter {
        /** MemberVariables */
        private Context context;
        /** MemberVariables */
        private LayoutInflater inflater;
        /** MemberVariables */
        private List<FileManageBean1> list;

        public FileManageBeanAdapter(Context context, List<FileManageBean1> list) {
            this.context = context;
            inflater = LayoutInflater.from(context);
            this.list = list;
        }

        @Override
        public int getCount() {
            return list == null ? 0 : list.size();

        }

        @Override
        public Object getItem(int item) {
            return this.list.get(item);
        }

        @Override
        public long getItemId(int itemId) {
            return itemId;
        }

        @Override
        public View getView(final int i, View view, ViewGroup viewGroup) {
            if (null == view) {
                view = inflater.inflate(R.layout.file_list_item, null);
            }
            TextView textViewTitle = (TextView) view.findViewById(R.id.file_list_item_tv);

            textViewTitle.setText(list.get(i).getFile_Name());

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String file_URL = list.get(i).getFile_URL();
                    String Name = list.get(i).getName();
                    // int file_ID = list.get(i).getFile_ID();

                    downLoadDialog(Name);
                }
            });
            return view;
        }
    }

    /** 弹出下载对话框 */
    private void downLoadDialog(final String Name) {
        // Dialog dialog = new AlertDialog.Builder(this).setTitle("提示")
        // .setMessage("是否下载该附件？")
        // .setPositiveButton("下载", new OnClickListener() {
        // @Override
        // public void onClick(DialogInterface dialog, int which) {
        // getdownLoadFileURI(file_URL,file_Name,file_ID);
        // dialog.dismiss();
        //
        // }
        // }).setNegativeButton("取消", new OnClickListener() {
        // @Override
        // public void onClick(DialogInterface dialog, int which) {
        // dialog.dismiss();
        // }
        // }).create();
        // dialog.show();
        // String p="\\";
        // String[] s=Pattern.compile(p).split(Name);
        String urlDownloadAll = HttpUtil.BASE_URL + "upload/social/" + Name;
        Intent intent = new Intent(getApplicationContext(), AfnailPictureFromWebActivity.class);
        intent.putExtra("URI", urlDownloadAll);
        startActivity(intent);

    }

    /** 获取附件的地址 */
    private void getdownLoadFileURI(String file_URL, String file_Name, final int file_ID) {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {// 判断是否有SD卡
            FinalHttp fh = new FinalHttp();
            String newFilename = Environment.getExternalStorageDirectory() + "/" + file_Name;
            File temp = new File(newFilename);
            if (temp.exists()) {
                temp.delete();
            }
            String urlDownloadAll = HttpUtil.BASE_URL + "upload/social/" + file_Name;
            try {
                // 构造URL
                URL url = new URL(urlDownloadAll);
                // 打开连接
                URLConnection con = url.openConnection();
                // 获得文件的长度
                // int contentLength = con.getContentLength();
                // System.out.println("长度 :"+contentLength);
                // 输入流
                InputStream is = con.getInputStream();
                // 1K的数据缓冲
                byte[] bs = new byte[1024];
                // 读取到的数据长度
                int len;
                // 输出的文件流
                OutputStream os = new FileOutputStream(newFilename);
                // 开始读取
                while ((len = is.read(bs)) != -1) {
                    os.write(bs, 0, len);
                }
                // 完毕，关闭所有链接
                os.close();
                is.close();

            } catch (Exception e) {
                e.printStackTrace();
            }

            replace(newFilename, file_ID);

        } else {
            toast("没有SD卡更新失败");
        }
    }

    /**
     * 打开附件
     */

    private void replace(String newFilename, int file_ID) {
        Intent intent = new Intent(getApplicationContext(), AfnailPictureActivity.class);
        intent.putExtra("URI", newFilename);
        intent.putExtra("File_Id", file_ID);
        startActivityForResult(intent, 4);

    }

    /** 土司 */
    private void toast(String strMsg) {
        Toast.makeText(getApplicationContext(), strMsg, 0).show();
    }

    /**
     * 错误填报提示信息
     * 
     * @param errorMsg
     */
    private void DialogToast(String errorMsg) {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(PublicOpinionLookActivity.this);
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
     * 2014-4-1 判断文件路径是否为"jpg","png","gif","jpeg"后缀
     * 
     * @param filePath
     *            文件路径
     * @return Boolean 是否为图片路径
     */
    private static Boolean isImagePicture(String filePath) {
        if (null == filePath || filePath.length() <= 3) {
            return false;
        }
        String[] imageFormatSet = new String[] { "jpg", "png", "gif", "peg" };
        String endPath = filePath.substring(filePath.length() - 3, filePath.length());
        for (String item : imageFormatSet) {
            if (endPath.equals(item)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 2014-4-1 判断文件路径是否为 .asf .mp4 .3gp .rm .rmvb .avi后缀
     * 
     * @param filePath
     *            文件路径
     * @return Boolean 是否为视频路径
     */
    private Boolean isVideoFile(String filePath) {
        // String[] imageFormatSet = new String[] { "mp4","asf", "3gp", ".rm"
        // ,"mvb","avi"};
        if (null == filePath || filePath.length() < 4) {
            return false;
        }
        String[] imageFormatSet = new String[] { "mp4", "3gp" };
        String endPath = filePath.substring(filePath.length() - 3, filePath.length());
        System.out.println(endPath);
        for (String item : imageFormatSet) {
            if (endPath.equals(item)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 2014-4-1 判断文件路径是否为 .mp3后缀
     * 
     * @param filePath
     *            文件路径
     * @return Boolean 是否为视频路径
     */
    private Boolean isAudioFile(String filePath) {
        // String[] imageFormatSet = new String[] { "mp3","asf", "3gp", ".rm"
        // ,"mvb","avi"};
        if (null == filePath || filePath.length() < 4) {
            return false;
        }
        String[] imageFormatSet = new String[] { "3gp" };
        String endPath = filePath.substring(filePath.length() - 3, filePath.length());
        System.out.println(endPath);
        for (String item : imageFormatSet) {
            if (endPath.equals(item)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 
     * 2014-7-22下午5:13:01 类UploadPictureAdapter
     * 
     * @author Mars zhang
     * 
     */
    public class UploadPictureAdapter extends BaseAdapter implements ListAdapter {
        /** MemberVariables */
        private Context context;
        /** MemberVariables */
        private LayoutInflater inflater;
        /** MemberVariables */
        private List<FileManageBean> list;

        public UploadPictureAdapter(Context context, List<FileManageBean> list) {
            this.context = context;
            inflater = LayoutInflater.from(context);
            this.list = list;
        }

        @Override
        public int getCount() {
            return list == null ? 0 : list.size();

        }

        @Override
        public Object getItem(int item) {
            return this.list.get(item);
        }

        @Override
        public long getItemId(int itemId) {
            return itemId;
        }

        @Override
        public View getView(final int i, View view, ViewGroup viewGroup) {
            if (null == view) {
                view = inflater.inflate(R.layout.file_list_item, null);
            }
            TextView textViewTitle = (TextView) view.findViewById(R.id.file_list_item_tv);
            String s[] = Pattern.compile("/").split(list.get(i).getFile_URL());
            textViewTitle.setText("" + s[s.length - 1]);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View arg0) {
                    toast(list.get(i).getFile_URL() + "类型" + list.get(i).getFile_Flag());
                    if (FILE_TYPE_PIC_01.equals(list.get(i).getFile_Flag())) {
                        Intent intent = new Intent(getApplicationContext(), AfnailPictureActivity.class);
                        intent.putExtra("URI", list.get(i).getFile_URL());
                        intent.putExtra("File_Id", list.get(i).getFile_ID());
                        startActivityForResult(intent, 4);
                    } else if (FILE_TYPE_VIDEO_02.equals(list.get(i).getFile_Flag())) {
                        Intent intent = new Intent(getApplicationContext(), AfinalVideoActivity.class);
                        intent.putExtra("URI", list.get(i).getFile_URL());
                        intent.putExtra("File_Id", list.get(i).getFile_ID());
                        startActivityForResult(intent, 4);
                    } else if (FILE_TYPE_AUDIO_03.equals(list.get(i).getFile_Flag())) {
                        Intent intent = new Intent(getApplicationContext(), AfinalAudioActivity.class);
                        intent.putExtra("URI", list.get(i).getFile_URL());
                        intent.putExtra("File_Id", list.get(i).getFile_ID());
                        startActivityForResult(intent, 4);
                    }

                }
            });
            return view;
        }
    }

    /** * 判断手机号码 */
    public static boolean isCellphone(String str) {
        Pattern pattern = Pattern.compile("^0?(13[0-9]|15[012356789]|18[01256789]|14[7])[0-9]{8}$");
        Matcher matcher = pattern.matcher(str);
        if (matcher.matches()) {
            return true;
        } else {
            return false;
        }
    }
}
