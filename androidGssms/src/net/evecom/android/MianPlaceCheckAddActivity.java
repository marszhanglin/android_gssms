/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * 
 */
package net.evecom.android;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Calendar;

import net.evecom.android.util.HttpUtil;
import net.evecom.android.util.ShareUtil;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.util.EntityUtils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

/**
 * �ص㳡�����
 * 
 * @author Mars zhang
 * 
 */
public class MianPlaceCheckAddActivity extends Activity {

    /** MemberVariables */
    private static final int MESSAGETYPE_01 = 0x0001;// �����ж��Ƿ��ͳɹ�
    /** MemberVariables */
    private static final int MESSAGETYPE_02 = 0x0002;
    /** Calendar */
    private Calendar calendar = null;
    /** ������ */
    private ProgressDialog progressDialog;
    /** MemberVariables */
    private String sss = "";
    /** MemberVariables */
    private TextView csmcTV = null; // mian_people_visit_add_csmc_tv
    /** MemberVariables */
    private TextView cslbTV = null; // mian_people_visit_add_cslb_tv
    /** MemberVariables */
    private TextView csdzTV = null; // mian_people_visit_add_csdz_tv
    /** MemberVariables */
    private Button jc_date_btn = null; // mian_people_visit_add_jc_date_btn
    /** MemberVariables */
    private Button jc_time_btn = null; // mian_people_visit_add_jc_time_btn
    /** MemberVariables */
    private Button zg_date_btn = null; // mian_people_visit_add_zg_date_btn
    /** MemberVariables */
    private Button zg_time_btn = null; // mian_people_visit_add_zg_time_btn
    /** MemberVariables */
    private EditText jcxgET = null; // mian_people_visit_add_jcxg_edit
    /** MemberVariables */
    private CheckBox has_yhcheckBox = null; // mian_people_visit_add_has_yh_cb
    /** MemberVariables */
    private EditText yhslET = null; // mian_people_visit_add_yhsl_edit
    /** MemberVariables */
    private EditText yhqkET = null; // mian_people_visit_add_yhqk_edit
    /** MemberVariables */
    private EditText bzET = null; // mian_people_visit_add_bz_edit
    /** MemberVariables */
    private EditText zgztET = null; // mian_people_visit_add_zgzt_edit
    /** MemberVariables */
    private EditText zgqkET = null; // mian_people_visit_add_zgqk_edit
    /** MemberVariables */
    private EditText jclxET = null; // mian_people_visit_add_jclx_edit
    /** MemberVariables */
    private LinearLayout lin_has_yh = null;// lin_has_yh
    /** MemberVariables */
    private String[] filetypes = null;
    /** MemberVariables */
    private String[] jctypes = null;
    /** MemberVariables */
    private String[] jclxtypes = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mian_place_check_add_activity);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        // ����activityʱ���Զ����������
        init();
    }

    /**
     * ��ʼ������
     */
    private void init() {
        Intent intent = getIntent();
        SharedPreferences sp = getSharedPreferences("MianPlaceCheckAddActivity", MODE_PRIVATE);
        Editor editor = sp.edit();
        editor.putString("PLACEID", intent.getStringExtra("PLACEID"));
        editor.commit();

        csmcTV = (TextView) findViewById(R.id.mian_people_visit_add_csmc_tv);
        csmcTV.setText(intent.getStringExtra("csmcTV"));
        cslbTV = (TextView) findViewById(R.id.mian_people_visit_add_cslb_tv);
        cslbTV.setText(intent.getStringExtra("cslbTV"));
        csdzTV = (TextView) findViewById(R.id.mian_people_visit_add_csdz_tv);
        csdzTV.setText(intent.getStringExtra("csdzTV"));

        calendar = Calendar.getInstance();// ��ȡ��ǰʱ��
        jc_date_btn = (Button) findViewById(R.id.mian_people_visit_add_jc_date_btn);
        jc_time_btn = (Button) findViewById(R.id.mian_people_visit_add_jc_time_btn);
        zg_date_btn = (Button) findViewById(R.id.mian_people_visit_add_zg_date_btn);
        zg_time_btn = (Button) findViewById(R.id.mian_people_visit_add_zg_time_btn);
        /*
         * ʱ�䰴ť���
         */
        jc_date_btn.setText("" + calendar.get(Calendar.YEAR) + "-" + (calendar.get(Calendar.MONTH) + 1) + "-"
                + calendar.get(Calendar.DAY_OF_MONTH));
        jc_date_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new DatePickerDialog(MianPlaceCheckAddActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                // Toast.makeText(MianPlaceCheckAddActivity.this,
                                // "" + year + monthOfYear + dayOfMonth, 1)
                                // .show();
                                jc_date_btn.setText("" + year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                            }
                        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar
                                .get(Calendar.DAY_OF_MONTH));// Ĭ��Ϊϵͳʱ��
                dialog.show();
            }
        });
        jc_time_btn.setText("" + calendar.get(Calendar.HOUR_OF_DAY) + ":" + calendar.get(Calendar.MINUTE));
        jc_time_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog time = new TimePickerDialog(MianPlaceCheckAddActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                jc_time_btn.setText("" + hourOfDay + ":" + minute);
                            }
                        }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true);
                time.show();
            }
        });
        // zg_date_btn.setText("" + calendar.get(Calendar.YEAR) + "-"
        // + (calendar.get(Calendar.MONTH) + 1) + "-"
        // + calendar.get(Calendar.DAY_OF_MONTH));
        zg_date_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new DatePickerDialog(MianPlaceCheckAddActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                // Toast.makeText(MianPlaceCheckAddActivity.this,
                                // "" + year + monthOfYear + dayOfMonth, 1)
                                // .show();
                                zg_date_btn.setText("" + year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                            }
                        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar
                                .get(Calendar.DAY_OF_MONTH));// Ĭ��Ϊϵͳʱ��
                dialog.show();
            }
        });
        // zg_time_btn.setText("" + calendar.get(Calendar.HOUR_OF_DAY) + ":"
        // + calendar.get(Calendar.MINUTE));
        zg_time_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog time = new TimePickerDialog(MianPlaceCheckAddActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                zg_time_btn.setText("" + hourOfDay + ":" + minute);
                            }
                        }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true);
                time.show();
            }
        });

        /**
         * �����
         */
        jcxgET = (EditText) findViewById(R.id.mian_people_visit_add_jcxg_edit);
        jcxgET.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jctypes = new String[] { "�ϸ�", "���ϸ�" };
                Dialog schDia = new AlertDialog.Builder(MianPlaceCheckAddActivity.this)
                        .setIcon(R.drawable.login_error_icon).setTitle("��ѡ��")
                        .setItems(jctypes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dia, int which) {
                                // Intent intent = null;
                                SharedPreferences sp = getSharedPreferences("MianPlaceCheckAddActivity", MODE_PRIVATE);
                                Editor editor = sp.edit();
                                switch (which) {
                                    case 0:
                                        editor.putString("INSRESULT", which + 1 + "");
                                        editor.commit();
                                        jcxgET.setText(jctypes[which]);
                                        break;
                                    case 1:
                                        editor.putString("INSRESULT", which + 1 + "");
                                        editor.commit();
                                        jcxgET.setText(jctypes[which]);
                                        break;
                                    default:
                                        break;
                                }
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
        yhslET = (EditText) findViewById(R.id.mian_people_visit_add_yhsl_edit);
        yhqkET = (EditText) findViewById(R.id.mian_people_visit_add_yhqk_edit);
        bzET = (EditText) findViewById(R.id.mian_people_visit_add_bz_edit);
        zgztET = (EditText) findViewById(R.id.mian_people_visit_add_zgzt_edit);
        zgztET.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filetypes = new String[] { "δ����", "������", "������" };
                Dialog schDia = new AlertDialog.Builder(MianPlaceCheckAddActivity.this)
                        .setIcon(R.drawable.login_error_icon).setTitle("��ѡ��")
                        .setItems(filetypes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dia, int which) {
                                // Intent intent = null;
                                SharedPreferences sp = getSharedPreferences("MianPlaceCheckAddActivity", MODE_PRIVATE);
                                Editor editor = sp.edit();
                                switch (which) {
                                    case 0:
                                        editor.putString("RECTSTATE", which + 1 + "");
                                        editor.commit();
                                        zgztET.setText(filetypes[which]);
                                        break;
                                    case 1:
                                        editor.putString("RECTSTATE", which + 1 + "");
                                        editor.commit();
                                        zgztET.setText(filetypes[which]);
                                        break;
                                    case 2:
                                        editor.putString("RECTSTATE", which + 1 + "");
                                        editor.commit();
                                        zgztET.setText(filetypes[which]);
                                        break;
                                    default:
                                        break;
                                }
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
        zgqkET = (EditText) findViewById(R.id.mian_people_visit_add_zgqk_edit);

        jclxET = (EditText) findViewById(R.id.mian_people_visit_add_jclx_edit);
        jclxET.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jclxtypes = new String[] { "������ȫ��������ļ��", "������������ʩ�ļ��" };
                Dialog schDia = new AlertDialog.Builder(MianPlaceCheckAddActivity.this)
                        .setIcon(R.drawable.login_error_icon).setTitle("��ѡ��")
                        .setItems(jclxtypes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dia, int which) {
                                // Intent intent = null;
                                SharedPreferences sp = getSharedPreferences("MianPlaceCheckAddActivity", MODE_PRIVATE);
                                Editor editor = sp.edit();
                                switch (which) {
                                    case 0:
                                        editor.putString("INSTYPE", which + 1 + "");
                                        editor.commit();
                                        jclxET.setText(jclxtypes[which]);
                                        break;
                                    case 1:
                                        editor.putString("INSTYPE", which + 1 + "");
                                        editor.commit();
                                        jclxET.setText(jclxtypes[which]);
                                        break;
                                    default:
                                        break;
                                }
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

        lin_has_yh = (LinearLayout) findViewById(R.id.lin_has_yh);

        has_yhcheckBox = (CheckBox) findViewById(R.id.mian_people_visit_add_has_yh_cb);
        has_yhcheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    lin_has_yh.setVisibility(View.VISIBLE);
                } else {
                    lin_has_yh.setVisibility(View.GONE);
                }

            }
        });
    }

    /**
     * �����ύ���ݵ�������
     * 
     * @param v
     */
    public void eventbc(View v) {
        submit();
    }

    /**
     * ����
     */
    private void submit() {
        // /** ����У�� */
        if (jclxET.getText().toString().trim().length() < 1) {
            DialogToast("��ѡ�������ͣ�");
            return;
        } else if (jcxgET.getText().toString().trim().length() < 1) {
            DialogToast("��ѡ��������");
            return;
        }
        // else if(sjrsEditText.getText().toString().trim().length()<1){
        // DialogToast("����д�漰������");
        // return ;
        // }
        // else if(sjlxTextView.getText().toString().trim().length()<1){
        // DialogToast("��ѡ���¼����ͣ�");
        // return ;
        // }
        // else if(sjlxTextView.getText().toString().trim().length()<1){
        // DialogToast("����д�¼�������");
        // return ;
        // }
        //
        //
        //
        //
        final AlertDialog.Builder builder = new AlertDialog.Builder(MianPlaceCheckAddActivity.this);
        builder.setTitle("��ʾ��Ϣ");
        builder.setIcon(R.drawable.qq_dialog_default_icon);// ͼ��
        builder.setMessage("�Ƿ�ȷ��Ҫ��������Ϣ��");
        builder.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
            // @Override
            public void onClick(DialogInterface dialog, int which) {
                progressDialog = ProgressDialog.show(MianPlaceCheckAddActivity.this, "��ʾ", "���ڱ��棬���Ե�...");
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
        return;
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
                    String a = HttpUtil.BASE_URL + "teventAndroid/saveinspection";
                    String b = "inspection.AREAID="
                            + ShareUtil.getString(getApplicationContext(), "SESSION", "AREAID", "")// ��������ID
                            + "&inspection.PERSONID="
                            + ShareUtil.getString(getApplicationContext(), "SESSION", "EMPID", "")// ���ԱID
                            + "&inspection.PERSONNAME="
                            + ShareUtil.getString(getApplicationContext(), "SESSION", "EMPNAME", "ϵͳ����Ա")// ���Ա����
                            + "&INSDATE="
                            + jc_date_btn.getText().toString().trim()
                            + " "
                            + jc_time_btn.getText().toString().trim()
                            + ":00" // �������
                            + "&inspection.INSPLACEID="
                            + ShareUtil.getString(getApplicationContext(),
                                    "MianPlaceCheckAddActivity", "PLACEID", "")  
                            + "&inspection.INSPLACE="
                            + csmcTV.getText().toString().trim() // ��������
                            + "&inspection.INSPLACEADD="
                            + csdzTV.getText().toString().trim() // ������ַ
                            + "&inspection.INSRESULT="
                            + ShareUtil
                                    .getString(getApplicationContext(),
                                            "MianPlaceCheckAddActivity", "INSRESULT", "") 
                            + "&inspection.ISHIDDEN="
                            + ((has_yhcheckBox.isChecked()) ? "1" : "0") // �Ƿ��������
                            + "&inspection.HIDDENCOUNT="
                            + yhslET.getText().toString().trim() // ��������
                            + "&inspection.HIDDENDANGER="
                            + yhqkET.getText().toString().trim() // �������
                            + "&RECTTERM="
                            + jc_date_btn.getText().toString().trim()
                            + " "
                            + jc_time_btn.getText().toString().trim()
                            + ":00" // ������������
                            + "&inspection.RECTSTATE="
                            + ShareUtil
                                    .getString(getApplicationContext(),
                                            "MianPlaceCheckAddActivity", "RECTSTATE", "") // ����״̬
                            + "&inspection.RECTNOTE="
                            + zgqkET.getText().toString().trim() // �������
                            + "&inspection.NOTES="
                            + bzET.getText().toString().trim() // ��ע
                            + "&inspection.INSTYPE="
                            + ShareUtil.getString(getApplicationContext(),
                                    "MianPlaceCheckAddActivity", "INSTYPE", "")  
                    ;
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
                    handler.sendMessage(msg_listData);
                } catch (Exception e) {
                    if (null != e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    /** ������ʹ�� */
    private Handler handler = new Handler() {
        public void handleMessage(Message message) {
            switch (message.what) {
                case MESSAGETYPE_01:
                    if (null != progressDialog) {
                        progressDialog.dismiss();
                    }
                    toast("����ɹ�");
                    MianPlaceCheckAddActivity.this.finish();
                    MianPlaceCheckSearchForMianPlaceActivity.instance.finish();
                    MianPlaceCheckVisitActivity.instance.finish();
                    Intent intent = new Intent(getApplicationContext(), MianPlaceCheckVisitActivity.class);
                    startActivity(intent);
                    break;
                case MESSAGETYPE_02:
                    if (null != progressDialog) {
                        progressDialog.dismiss();
                    }
                    Toast.makeText(getApplicationContext(), "����ʧ�ܣ����������Ƿ����", 0).show();
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

    /**
     * fh
     * 
     * @param v
     */
    public void eventfh(View v) {
        finish();
    }

    /**
     * �������ʾ��Ϣ
     * 
     * @param errorMsg
     */
    private void DialogToast(String errorMsg) {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(MianPlaceCheckAddActivity.this);
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

    /** ��˾ */
    private void toast(String strMsg) {
        Toast.makeText(getApplicationContext(), strMsg, 0).show();
    }

}