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
 * 重点场所检查
 * 
 * @author Mars zhang
 * 
 */
public class MianPlaceCheckAddActivity extends Activity {

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
        // 启动activity时不自动弹出软键盘
        init();
    }

    /**
     * 初始化数据
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

        calendar = Calendar.getInstance();// 获取当前时间
        jc_date_btn = (Button) findViewById(R.id.mian_people_visit_add_jc_date_btn);
        jc_time_btn = (Button) findViewById(R.id.mian_people_visit_add_jc_time_btn);
        zg_date_btn = (Button) findViewById(R.id.mian_people_visit_add_zg_date_btn);
        zg_time_btn = (Button) findViewById(R.id.mian_people_visit_add_zg_time_btn);
        /*
         * 时间按钮点击
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
                                .get(Calendar.DAY_OF_MONTH));// 默认为系统时间
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
                                .get(Calendar.DAY_OF_MONTH));// 默认为系统时间
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
         * 检查结果
         */
        jcxgET = (EditText) findViewById(R.id.mian_people_visit_add_jcxg_edit);
        jcxgET.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jctypes = new String[] { "合格", "不合格" };
                Dialog schDia = new AlertDialog.Builder(MianPlaceCheckAddActivity.this)
                        .setIcon(R.drawable.login_error_icon).setTitle("请选择")
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
                        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
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
                filetypes = new String[] { "未整改", "整改中", "已整改" };
                Dialog schDia = new AlertDialog.Builder(MianPlaceCheckAddActivity.this)
                        .setIcon(R.drawable.login_error_icon).setTitle("请选择")
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
                        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
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
                jclxtypes = new String[] { "消防安全管理情况的检查", "建筑防火技术措施的检查" };
                Dialog schDia = new AlertDialog.Builder(MianPlaceCheckAddActivity.this)
                        .setIcon(R.drawable.login_error_icon).setTitle("请选择")
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
                        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
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
        if (jclxET.getText().toString().trim().length() < 1) {
            DialogToast("请选择检查类型！");
            return;
        } else if (jcxgET.getText().toString().trim().length() < 1) {
            DialogToast("请选择检查结果！");
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
        final AlertDialog.Builder builder = new AlertDialog.Builder(MianPlaceCheckAddActivity.this);
        builder.setTitle("提示信息");
        builder.setIcon(R.drawable.qq_dialog_default_icon);// 图标
        builder.setMessage("是否确定要保存检查信息？");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            // @Override
            public void onClick(DialogInterface dialog, int which) {
                progressDialog = ProgressDialog.show(MianPlaceCheckAddActivity.this, "提示", "正在保存，请稍等...");
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
                    String a = HttpUtil.BASE_URL + "teventAndroid/saveinspection";
                    String b = "inspection.AREAID="
                            + ShareUtil.getString(getApplicationContext(), "SESSION", "AREAID", "")// 所属网格ID
                            + "&inspection.PERSONID="
                            + ShareUtil.getString(getApplicationContext(), "SESSION", "EMPID", "")// 检查员ID
                            + "&inspection.PERSONNAME="
                            + ShareUtil.getString(getApplicationContext(), "SESSION", "EMPNAME", "系统管理员")// 检查员姓名
                            + "&INSDATE="
                            + jc_date_btn.getText().toString().trim()
                            + " "
                            + jc_time_btn.getText().toString().trim()
                            + ":00" // 检查日期
                            + "&inspection.INSPLACEID="
                            + ShareUtil.getString(getApplicationContext(),
                                    "MianPlaceCheckAddActivity", "PLACEID", "")  
                            + "&inspection.INSPLACE="
                            + csmcTV.getText().toString().trim() // 场所名称
                            + "&inspection.INSPLACEADD="
                            + csdzTV.getText().toString().trim() // 场所地址
                            + "&inspection.INSRESULT="
                            + ShareUtil
                                    .getString(getApplicationContext(),
                                            "MianPlaceCheckAddActivity", "INSRESULT", "") 
                            + "&inspection.ISHIDDEN="
                            + ((has_yhcheckBox.isChecked()) ? "1" : "0") // 是否存在隐患
                            + "&inspection.HIDDENCOUNT="
                            + yhslET.getText().toString().trim() // 隐患数量
                            + "&inspection.HIDDENDANGER="
                            + yhqkET.getText().toString().trim() // 隐患情况
                            + "&RECTTERM="
                            + jc_date_btn.getText().toString().trim()
                            + " "
                            + jc_time_btn.getText().toString().trim()
                            + ":00" // 隐患整改期限
                            + "&inspection.RECTSTATE="
                            + ShareUtil
                                    .getString(getApplicationContext(),
                                            "MianPlaceCheckAddActivity", "RECTSTATE", "") // 整改状态
                            + "&inspection.RECTNOTE="
                            + zgqkET.getText().toString().trim() // 整改情况
                            + "&inspection.NOTES="
                            + bzET.getText().toString().trim() // 备注
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

    /** 进度条使用 */
    private Handler handler = new Handler() {
        public void handleMessage(Message message) {
            switch (message.what) {
                case MESSAGETYPE_01:
                    if (null != progressDialog) {
                        progressDialog.dismiss();
                    }
                    toast("保存成功");
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
        AlertDialog.Builder builder1 = new AlertDialog.Builder(MianPlaceCheckAddActivity.this);
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