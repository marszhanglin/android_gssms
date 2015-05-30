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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

/**
 * 新增走访界面
 * 
 * @author Mars zhang
 * 
 */
public class MianPeopleVisitAddActivity extends Activity {

    /** MemberVariables */
    private static final int MESSAGETYPE_01 = 0x0001;// 用于判断是否发送成功
    /** MemberVariables */
    private static final int MESSAGETYPE_02 = 0x0002;
    /** Calendar */
    private Calendar calendar = null;
    /** 进度条 */
    private ProgressDialog progressDialog;
    /** MemberVariables */
    private String sss = "";
    /** MemberVariables */
    private TextView xmTV = null; // mian_people_visit_add_xm_tv
    /** MemberVariables */
    private TextView sexTV = null; // mian_people_visit_add_sex_tv
    /** MemberVariables */
    private TextView idcardTV = null; // mian_people_visit_add_idcard_tv
    /** MemberVariables */
    private TextView addrTV = null; // mian_people_visit_add_addr_tv
    /** MemberVariables */
    private TextView phoneTV = null; // mian_people_visit_add_phone_tv
    /** MemberVariables */
    private Button date_btn = null; // mian_people_visit_add_date_btn
    /** MemberVariables */
    private Button time_btn = null; // mian_people_visit_add_time_btn
    /** MemberVariables */
    private EditText zfxsET = null; // mian_people_visit_add_zfxs_edit
    /** MemberVariables */
    private EditText zfxgET = null; // mian_people_visit_add_zfxg_edit
    /** MemberVariables */
    private EditText jqdtET = null; // mian_people_visit_add_jqdt_edit
    /** MemberVariables */
    private EditText zfyyET = null; // mian_people_visit_add_zfyy_edit
    /** MemberVariables */
    private EditText jkjsET = null; // mian_people_visit_add_jkjs_edit
    /** MemberVariables */
    private EditText cqcsET = null; // mian_people_visit_add_cqcs_edit
    /** MemberVariables */
    private EditText jtnrET = null; // mian_people_visit_add_jtnr_edit
    /** MemberVariables */
    private EditText bzET = null; // mian_people_visit_add_bz_edit

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mian_people_visit_add_activity);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        // 启动activity时不自动弹出软键盘
        init();
    }

    /**
     * 初始化数据
     */
    private void init() {
        Intent intent = getIntent();
        // intent.getStringExtra("");
        SharedPreferences sp = getSharedPreferences("MianPeopleVisitAddActivity", MODE_PRIVATE);
        Editor editor = sp.edit();
        editor.putString("PersonID", intent.getStringExtra("PersonID"));
        editor.commit();

        xmTV = (TextView) findViewById(R.id.mian_people_visit_add_xm_tv);
        xmTV.setText(intent.getStringExtra("xmTV"));
        sexTV = (TextView) findViewById(R.id.mian_people_visit_add_sex_tv);
        sexTV.setText(intent.getStringExtra("sexTV"));
        idcardTV = (TextView) findViewById(R.id.mian_people_visit_add_idcard_tv);
        idcardTV.setText(intent.getStringExtra("idcardTV"));
        addrTV = (TextView) findViewById(R.id.mian_people_visit_add_addr_tv);
        addrTV.setText(intent.getStringExtra("addrTV"));
        phoneTV = (TextView) findViewById(R.id.mian_people_visit_add_phone_tv);
        phoneTV.setText(intent.getStringExtra("phoneTV"));

        calendar = Calendar.getInstance();// 获取当前时间
        date_btn = (Button) findViewById(R.id.mian_people_visit_add_date_btn);
        time_btn = (Button) findViewById(R.id.mian_people_visit_add_time_btn);
        /*
         * 时间按钮点击
         */
        date_btn.setText("" + calendar.get(Calendar.YEAR) + "-" + (calendar.get(Calendar.MONTH) + 1) + "-"
                + calendar.get(Calendar.DAY_OF_MONTH));
        date_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new DatePickerDialog(MianPeopleVisitAddActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                // Toast.makeText(MianPeopleVisitAddActivity.this,
                                // "" + year + monthOfYear + dayOfMonth, 1)
                                // .show();
                                date_btn.setText("" + year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                            }
                        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar
                                .get(Calendar.DAY_OF_MONTH));// 默认为系统时间
                dialog.show();
            }
        });
        time_btn.setText("" + calendar.get(Calendar.HOUR_OF_DAY) + ":" + calendar.get(Calendar.MINUTE));
        time_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog time = new TimePickerDialog(MianPeopleVisitAddActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                time_btn.setText("" + hourOfDay + ":" + minute);
                            }
                        }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true);
                time.show();
            }
        });

        zfxsET = (EditText) findViewById(R.id.mian_people_visit_add_zfxs_edit);
        zfxgET = (EditText) findViewById(R.id.mian_people_visit_add_zfxg_edit);
        jqdtET = (EditText) findViewById(R.id.mian_people_visit_add_jqdt_edit);
        zfyyET = (EditText) findViewById(R.id.mian_people_visit_add_zfyy_edit);
        jkjsET = (EditText) findViewById(R.id.mian_people_visit_add_jkjs_edit);
        cqcsET = (EditText) findViewById(R.id.mian_people_visit_add_cqcs_edit);
        jtnrET = (EditText) findViewById(R.id.mian_people_visit_add_jtnr_edit);
        bzET = (EditText) findViewById(R.id.mian_people_visit_add_bz_edit);

    }

    /**
     * 保存提交数据到服务器
     * 
     * @param v
     */
    public void eventbc(View v) {
        submit();
    }

    /**
     * 保存
     */
    private void submit() {
        // /** 数据校验 */
        if (zfxsET.getText().toString().trim().length() < 1) {
            DialogToast("请填写走访形式！");
            return;
        } else if (zfxgET.getText().toString().trim().length() < 1) {
            DialogToast("请填写走访效果！");
            return;
        }
        // else if(sjrsEditText.getText().toString().trim().length()<1){
        // DialogToast("请填写涉及人数！");
        // return ;
        // }
        // else if(sjlxTextView.getText().toString().trim().length()<1){
        // DialogToast("请选择事件类型！");
        // return ;
        // }
        // else if(sjlxTextView.getText().toString().trim().length()<1){
        // DialogToast("请填写事件描述！");
        // return ;
        // }
        //
        //
        //
        //
        final AlertDialog.Builder builder = new AlertDialog.Builder(MianPeopleVisitAddActivity.this);
        builder.setTitle("提示信息");
        builder.setIcon(R.drawable.qq_dialog_default_icon);// 图标
        builder.setMessage("是否确定要保存 走访信息？");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            // @Override
            public void onClick(DialogInterface dialog, int which) {
                progressDialog = ProgressDialog.show(MianPeopleVisitAddActivity.this, "提示", "正在保存，请稍等...");
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
        return;
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
                    String a = HttpUtil.BASE_URL + "teventAndroid/saveInterview";
                    String b = "uid="
                            + ShareUtil.getString(getApplicationContext(), "SESSION", "EMPID", "1")// 走访人
                            + "&userName="
                            + ShareUtil.getString(getApplicationContext(), "SESSION", "EMPNAME", "系统管理员")// 走访人姓名
                            + "&person.personId="
                            + ShareUtil.getString(getApplicationContext(), "MianPeopleVisitAddActivity", "PersonID",
                                    "1") // 被走访人id
                            + "&INTERVIEWTIME=" + date_btn.getText().toString().trim() + " "
                            + time_btn.getText().toString().trim() + ":00" // 走访时间
                            + "&interview.INTERVIEWTYPE=" + zfxsET.getText().toString().trim() // 走访形式
                            + "&interview.INTERVIEWEFFECT=" + zfxgET.getText().toString().trim() // 走访效果
                            + "&interview.LATESTNEWS=" + jqdtET.getText().toString().trim() // 近期动态
                            + "&interview.INTERVIEWREASON=" + zfyyET.getText().toString().trim() // 走访原因
                            + "&interview.RECENTSKETCH=" + jkjsET.getText().toString().trim() // 近况简述
                            + "&interview.TAKESTEP=" + cqcsET.getText().toString().trim() // 采取措施
                            + "&interview.PAYLOAD=" + jtnrET.getText().toString().trim() // 交谈内容
                            + "&interview.INTERVIEWMARK=" + bzET.getText().toString().trim() // 备注
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

    /** 进度条使用 */
    private Handler handler = new Handler() {
        public void handleMessage(Message message) {
            switch (message.what) {
                case MESSAGETYPE_01:
                    if (null != progressDialog) {
                        progressDialog.dismiss();
                    }
                    toast("保存成功");
                    MianPeopleVisitAddActivity.this.finish();
                    MianPeopleVisitSearchForMianPersonActivity.instance.finish();
                    MianPeopleVisitActivity.instance.finish();
                    Intent intent = new Intent(getApplicationContext(), MianPeopleVisitActivity.class);
                    startActivity(intent);
                    break;
                case MESSAGETYPE_02:
                    if (null != progressDialog) {
                        progressDialog.dismiss();
                    }
                    Toast.makeText(getApplicationContext(), "保存失败，请检查网络是否可用", 0).show();
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
     * 错误填报提示信息
     * 
     * @param errorMsg
     */
    private void DialogToast(String errorMsg) {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(MianPeopleVisitAddActivity.this);
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

    /** 土司 */
    private void toast(String strMsg) {
        Toast.makeText(getApplicationContext(), strMsg, 0).show();
    }

}