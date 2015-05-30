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
 * ����DailyLogLookActivity
 * @author Mars zhang
 * @created 2014-11-5 ����11:02:40
 */
public class DailyLogLookActivity extends Activity {
    /** MemberVariables */
    private static final int MESSAGETYPE_01 = 0x0001;// �����ж��Ƿ��ͳɹ�
    /** MemberVariables */
    private static final int MESSAGETYPE_02 = 0x0002;
    /** ���� */
    private EditText btEditText;
    /** �������� */
    private Button sswgButton;
    /** ��־���� */
    private Button rzlxButton;
    /** ��־�������� */
    private String[] rzlxs;
    /** ��Դ���� */
    private int sourceway;
    /** ����ʱ�� */
    private Button gzsjButton;
    /** ��־���� */
    private EditText rznrEditText;
    /** ��ע */
    private EditText bzEditText;
    /** handler */
    private Handler handler = null;

    /** Calendar */
    private Calendar calendar = null;
    /** ������ */
    private ProgressDialog progressDialog;
    /** ����ʱ�� */
    private String gzsj = "";
    /** �� */
    private int years;
    /** �� */
    private int month;
    /** �� */
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
    /** ���ݿ� */
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
     * ��ʼ������
     */
    private void init() {
        rzlxs = new String[] { "�ճ�����", "��������" };
    }

    /**
     * ��ʼ������
     */
    private void init1() {
        /** ������� */
        db = FinalDb.create(this);
        db.deleteAll(FileManageBean.class);
    }

    /**
     * ��ʼ������
     */
    private void initView() {
        btEditText = (EditText) findViewById(R.id.daily_log_look_bt);
        sswgButton = (Button) findViewById(R.id.daily_log_look_sswg);
        rzlxButton = (Button) findViewById(R.id.daily_log_look_rzlx);
        gzsjButton = (Button) findViewById(R.id.daily_log_look_gzsj_btn);
        rznrEditText = (EditText) findViewById(R.id.daily_log_look_rznr);
        bzEditText = (EditText) findViewById(R.id.daily_log_look_bz);

        calendar = Calendar.getInstance();// ��ȡ��ǰʱ��
        listener();
    }

    /**
     * ��������
     */
    private void listener() {

        gzsjButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog alertdialog = new AlertDialog.Builder(DailyLogLookActivity.this).setTitle("����ʱ��")
                        .setPositiveButton("����", new DialogInterface.OnClickListener() {

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
                                                .get(Calendar.DAY_OF_MONTH));// Ĭ��Ϊϵͳʱ��
                                dialog1.show();
                            }
                        })
                        // .setNeutralButton("ʱ��", new
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
                        // .get(Calendar.MINUTE), true);// Ĭ��Ϊϵͳʱ��
                        // dialog2.show();
                        // }
                        // })
                        .setNegativeButton("���", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                gzsjButton.setText("");

                                gzsj = "";

                            }
                        }).show();
            }
        });
        // ��־���Ͱ�ť���
        rzlxButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Dialog schDia = new AlertDialog.Builder(DailyLogLookActivity.this).setIcon(R.drawable.login_error_icon)
                        .setTitle("��ѡ��").setItems(rzlxs, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dia, int which) {
                                rzlxButton.setText(rzlxs[which]);
                                sourceway = which + 1;
                            }
                        }).setNegativeButton("ȡ��", new DialogInterface.OnClickListener() {
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
     * ��һ�����̼߳�����������
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
            // �����߳�
            new Thread(runnable).start();
            // handler���߳�֮���ͨ�ż����ݴ���
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
     * ������
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
     * �޸��ύ���ݵ�������
     * 
     * @param v
     */
    public void daily_log_xg(View v) {
        submit();
    }

    /**
     * �޸�
     */
    private String submit() {
        /** ����У�� */
        if (btEditText.getText().toString().trim().length() < 1) {
            DialogToast("����дժҪ��");
            return "";
        } else if (gzsjButton.getText().toString().trim().length() < 1) {
            DialogToast("����ʱ�䲻��Ϊ�գ�");
            return "";
        } else if (rzlxButton.getText().toString().trim().length() < 1) {
            DialogToast("�������Ͳ���Ϊ�գ�");
            return "";
        } else if (rznrEditText.getText().toString().trim().length() < 1) {
            DialogToast("��־���ݲ���Ϊ�գ�");
            return "";
        } else if (years > calendar.get(Calendar.YEAR)) {
            DialogToast("����ʱ�䲻�ܳ�����ǰʱ�䣡");
            return "";
        } else if (years == calendar.get(Calendar.YEAR)) {
            if (month > calendar.get(Calendar.MONTH) + 1) {
                DialogToast("����ʱ�䲻�ܳ�����ǰʱ�䣡");
                return "";
            } else if (month == calendar.get(Calendar.MONTH) + 1) {
                if (day > calendar.get(Calendar.DAY_OF_MONTH)) {
                    DialogToast("����ʱ�䲻�ܳ�����ǰʱ�䣡");
                    return "";
                }
            }
        }
        final AlertDialog.Builder builder = new AlertDialog.Builder(DailyLogLookActivity.this);
        builder.setTitle("��ʾ��Ϣ");
        builder.setIcon(R.drawable.qq_dialog_default_icon);// ͼ��
        builder.setMessage("�Ƿ�ȷ��Ҫ�ύ��");
        builder.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
            // @Override
            public void onClick(DialogInterface dialog, int which) {
                progressDialog = ProgressDialog.show(DailyLogLookActivity.this, "��ʾ", "�����ύ�����Ե�...");

                formSubmit();

            }
        });
        builder.setNegativeButton("ȡ��", new DialogInterface.OnClickListener() {
            // @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
        return "1";
    }

    /**
     * �ı��ύ
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
                    // +"&areaName=������"
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

    /** �ļ�������ʹ�� */
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
                    Toast.makeText(getApplicationContext(), "�ύ�ɹ�", 0).show();
                    break;
                case MESSAGETYPE_02:
                    if (null != progressDialog) {
                        progressDialog.dismiss();
                    }
                    Toast.makeText(getApplicationContext(), "�ύʧ�ܣ����������Ƿ����", 0).show();
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
        // HttpGet����
        HttpGet httpRequest = new HttpGet(strUrl);
        String strResult = "";
        // HttpClient����
        BasicHttpParams httpParams = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(httpParams, 5000);// ���ó�ʱʱ��
        HttpClient httpClient = new DefaultHttpClient(httpParams);
        // ���HttpResponse����
        HttpResponse httpResponse = httpClient.execute(httpRequest);
        if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            // ȡ�÷��ص�����
            strResult = EntityUtils.toString(httpResponse.getEntity());
        }
        return strResult;
    }

    /**
     * ɾ����������
     * 
     * @param v
     */
    public void daily_log_more_delete(View v) {

        final AlertDialog.Builder builder = new AlertDialog.Builder(DailyLogLookActivity.this);
        builder.setTitle("��ʾ��Ϣ");
        builder.setIcon(R.drawable.qq_dialog_default_icon);// ͼ��
        builder.setMessage("�Ƿ�ȷ��Ҫɾ����");
        builder.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
            // @Override
            public void onClick(DialogInterface dialog, int which) {
                progressDialog = ProgressDialog.show(DailyLogLookActivity.this, "��ʾ", "����ɾ�������Ե�...");

                formSubmit1();

            }
        });
        builder.setNegativeButton("ȡ��", new DialogInterface.OnClickListener() {
            // @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();

    }

    /**
     * �ı��ύ
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

    /** ������ʹ�� */
    Handler handler1 = new Handler() {
        public void handleMessage(Message message) {
            switch (message.what) {
                case MESSAGETYPE_01:
                    // ˢ��UI����ʾ���ݣ����رս�����
                    finish();
                    Intent intent = new Intent(getApplicationContext(), DailyLogListActivity.class);
                    startActivity(intent);
                    Toast.makeText(getApplicationContext(), "ɾ���ɹ�", 0).show();

                    break;
                case MESSAGETYPE_02:
                    if (null != progressDialog) {
                        progressDialog.dismiss();
                    }
                    Toast.makeText(getApplicationContext(), "�ı�ɾ��ʧ�ܣ����������Ƿ����", 0).show();
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
        builder.setTitle("��ʾ��Ϣ");
        builder.setIcon(R.drawable.qq_dialog_default_icon);// ͼ��
        builder.setMessage("�޸�������δ���棬���ݽ��ᶪʧ���Ƿ�ȷ���˳���");
        builder.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
            // @Override
            public void onClick(DialogInterface dialog, int which) {
                DailyLogLookActivity.this.finish();
                Intent intent = new Intent(getApplicationContext(), DailyLogListActivity.class);
                startActivity(intent);

            }
        });
        builder.setNegativeButton("ȡ��", new DialogInterface.OnClickListener() {
            // @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();

    }

    /**
     * �������ʾ��Ϣ
     * 
     * @param errorMsg
     */
    private void DialogToast(String errorMsg) {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(DailyLogLookActivity.this);
        builder1.setTitle("��ʾ��Ϣ");
        builder1.setIcon(R.drawable.qq_dialog_default_icon);// ͼ��
        builder1.setMessage("" + errorMsg);
        builder1.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
            // @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        builder1.show();
    }

}
