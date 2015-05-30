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
import java.util.regex.Pattern;

import net.evecom.android.bean.EOS_DICT_ENTRY;
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
 * 重点场所检查修改
 * 
 * @author Mars zhang
 * 
 */
public class MianPlaceCheckEditActivity extends Activity {

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
    private String info_sss = "";
    /** MemberVariables */
    private Message messagedelete = null;
    /** MemberVariables */
    private String[] filetypes = null;
    /** MemberVariables */
    private String[] jctypes = null;
    /** MemberVariables */
    private String[] jclxtypes = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mian_place_check_edit_activity);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        // 启动activity时不自动弹出软键盘
        init();
    }

    /**
     * 初始化数据
     */
    private void init() {
        Intent intent = getIntent();
        SharedPreferences sp = getSharedPreferences("MianPlaceCheckEditActivity", MODE_PRIVATE);
        Editor editor = sp.edit();
        editor.putString("SYSID", intent.getStringExtra("SYSID"));
        editor.putString("PersonID", intent.getStringExtra("PersonID"));
        editor.putString("INSPLACEID", intent.getStringExtra("INSPLACEID")); // 场所id
        editor.putString("RECTSTATE", intent.getStringExtra("RECTSTATE")); // 整改状态
        editor.commit();

        csmcTV = (TextView) findViewById(R.id.mian_people_visit_add_csmc_tv);
        csmcTV.setText(intent.getStringExtra("INSPLACE"));
        cslbTV = (TextView) findViewById(R.id.mian_people_visit_add_cslb_tv);
        csdzTV = (TextView) findViewById(R.id.mian_people_visit_add_csdz_tv);

        calendar = Calendar.getInstance();// 获取当前时间
        jc_date_btn = (Button) findViewById(R.id.mian_people_visit_add_jc_date_btn);
        jc_time_btn = (Button) findViewById(R.id.mian_people_visit_add_jc_time_btn);
        // jc_time_btn.setVisibility(View.GONE);
        zg_date_btn = (Button) findViewById(R.id.mian_people_visit_add_zg_date_btn);
        zg_time_btn = (Button) findViewById(R.id.mian_people_visit_add_zg_time_btn);
        // zg_time_btn.setVisibility(View.GONE);
        String INSDATE = intent.getStringExtra("INSDATE");
        if (null != INSDATE && INSDATE.length() > 10) {
            // jc_date_btn.setText(INSDATE);
            jc_date_btn.setText(INSDATE.substring(0, 10));
            jc_time_btn.setText(INSDATE.substring(10, INSDATE.length()));
        }

        /*
         * 时间按钮点击
         */
        // jc_date_btn.setText("" + calendar.get(Calendar.YEAR) + "-"
        // + (calendar.get(Calendar.MONTH) + 1) + "-"
        // + calendar.get(Calendar.DAY_OF_MONTH));
        jc_date_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new DatePickerDialog(MianPlaceCheckEditActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                // Toast.makeText(MianPlaceCheckEditActivity.this,
                                // "" + year + monthOfYear + dayOfMonth, 1)
                                // .show();
                                jc_date_btn.setText("" + year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                            }
                        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar
                                .get(Calendar.DAY_OF_MONTH));// 默认为系统时间
                dialog.show();
            }
        });
        // jc_time_btn.setText("" + calendar.get(Calendar.HOUR_OF_DAY) + ":"
        // + calendar.get(Calendar.MINUTE));
        jc_time_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog time = new TimePickerDialog(MianPlaceCheckEditActivity.this,
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
                Dialog dialog = new DatePickerDialog(MianPlaceCheckEditActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                // Toast.makeText(MianPlaceCheckEditActivity.this,
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
                Dialog time = new TimePickerDialog(MianPlaceCheckEditActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                zg_time_btn.setText("" + hourOfDay + ":" + minute);
                            }
                        }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true);
                time.show();
            }
        });

        jcxgET = (EditText) findViewById(R.id.mian_people_visit_add_jcxg_edit);
        String jcjg = intent.getStringExtra("INSRESULT");
        SharedPreferences sp1 = getSharedPreferences("MianPlaceCheckEditActivity", MODE_PRIVATE);
        Editor editor1 = sp.edit();
        if (null != jcjg && "1".equals(jcjg)) {
            jcxgET.setText("合格");
            editor1.putString("INSRESULT", "1");
            editor1.commit();
        } else if (null != jcjg && "2".equals(jcjg)) {
            jcxgET.setText("不合格");
            editor1.putString("INSRESULT", "2");
            editor1.commit();
        }

        jcxgET.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jctypes = new String[] { "合格", "不合格" };
                Dialog schDia = new AlertDialog.Builder(MianPlaceCheckEditActivity.this)
                        .setIcon(R.drawable.login_error_icon).setTitle("请选择")
                        .setItems(jctypes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dia, int which) {
                                // Intent intent = null;
                                SharedPreferences sp = getSharedPreferences("MianPlaceCheckEditActivity", MODE_PRIVATE);
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
                Dialog schDia = new AlertDialog.Builder(MianPlaceCheckEditActivity.this)
                        .setIcon(R.drawable.login_error_icon).setTitle("请选择")
                        .setItems(filetypes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dia, int which) {
                                // Intent intent = null;
                                SharedPreferences sp = getSharedPreferences("MianPlaceCheckEditActivity", MODE_PRIVATE);
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
        String RECTSTATE = ShareUtil.getString(getApplicationContext(), "MianPlaceCheckEditActivity", "RECTSTATE", "");
        if (null != RECTSTATE && "1".equals(RECTSTATE)) {
            zgztET.setText("未整改");
        } else if (null != RECTSTATE && "2".equals(RECTSTATE)) {
            zgztET.setText("整改中");
        } else if (null != RECTSTATE && "3".equals(RECTSTATE)) {
            zgztET.setText("已整改");
        }
        zgqkET = (EditText) findViewById(R.id.mian_people_visit_add_zgqk_edit);

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

        jclxET = (EditText) findViewById(R.id.mian_people_visit_add_jclx_edit);
        jclxET.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jclxtypes = new String[] { "消防安全管理情况的检查", "建筑防火技术措施的检查" };
                Dialog schDia = new AlertDialog.Builder(MianPlaceCheckEditActivity.this)
                        .setIcon(R.drawable.login_error_icon).setTitle("请选择")
                        .setItems(jclxtypes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dia, int which) {
                                // Intent intent = null;
                                SharedPreferences sp = getSharedPreferences("MianPlaceCheckEditActivity", MODE_PRIVATE);
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
        getInfo();

    }

    /**
     * 获取内容
     */
    private void getInfo() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Message messageInfo = new Message();
                String infoPath_a = HttpUtil.BASE_URL + "teventAndroid/getJCInfo";
                String infoPath_b = "SYSID="
                        + ShareUtil.getString(getApplicationContext(), "MianPlaceCheckEditActivity", "SYSID", "")
                        + "&PersonID="
                        + ShareUtil.getString(getApplicationContext(), "MianPlaceCheckEditActivity", "PersonID", "")
                        + "&INSPLACEID="
                        + ShareUtil.getString(getApplicationContext(), "MianPlaceCheckEditActivity", "INSPLACEID", "");

                // editor.putString("SYSID", intent.getStringExtra("SYSID"));
                // editor.putString("PersonID",
                // intent.getStringExtra("PersonID"));
                // editor.putString("INSPLACEID",
                // intent.getStringExtra("INSPLACEID")); //场所id
                try {
                    info_sss = connServerForResultPost(infoPath_a, infoPath_b);
                    if (null == info_sss || info_sss.contains("failure") || "".equals(info_sss)) {
                        messageInfo.what = MESSAGETYPE_02;
                    } else {
                        messageInfo.what = MESSAGETYPE_01;
                    }
                } catch (ClientProtocolException e) {
                    messageInfo.what = MESSAGETYPE_02;
                    if (null != e) {
                        e.printStackTrace();
                    }
                } catch (IOException e) {
                    messageInfo.what = MESSAGETYPE_02;
                    if (null != e) {
                        e.printStackTrace();
                    }
                } finally {
                    handler2.sendMessage(messageInfo);
                }
            }
        }).start();
    }
    /** MemberVariables */
    Handler handler2 = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MESSAGETYPE_01:
                    String[] s = Pattern.compile("@@@@").split(info_sss);
                    for (int i = 0; i < s.length; i++) {
                        if (null == s[i] || "null".equals(s[i])) {
                            s[i] = "";
                        }
                    }

                    String cslb_s = s[2];
                    if (null != cslb_s && "1".equals(cslb_s)) {
                        cslbTV.setText("安全生产重点");
                    } else if (null != cslb_s && "2".equals(cslb_s)) {
                        cslbTV.setText("消防安全重点");
                    } else if (null != cslb_s && "3".equals(cslb_s)) {
                        cslbTV.setText("治安重点");
                    } else if (null != cslb_s && "4".equals(cslb_s)) {
                        cslbTV.setText("学校");
                    } else if (null != cslb_s && "5".equals(cslb_s)) {
                        cslbTV.setText("医院");
                    } else if (null != cslb_s && "6".equals(cslb_s)) {
                        cslbTV.setText("危险化学品单位");
                    } else if (null != cslb_s && "7".equals(cslb_s)) {
                        cslbTV.setText("网吧");
                    } else if (null != cslb_s && "8".equals(cslb_s)) {
                        cslbTV.setText("公共场所");
                    } else if (null != cslb_s && "9".equals(cslb_s)) {
                        cslbTV.setText("公共复杂场所");
                    } else if (null != cslb_s && "10".equals(cslb_s)) {
                        cslbTV.setText("其他场所");
                    }

                    csdzTV.setText(s[1]);
                    yhslET.setText(s[3]);
                    yhqkET.setText(s[4]);
                    String date_time = s[5];
                    if (null != date_time && date_time.length() > 12) {
                        zg_date_btn.setText(date_time.substring(0, 10));
                        zg_time_btn.setText(date_time.substring(10, date_time.length()));
                    }
                    zgqkET.setText(s[7]);
                    bzET.setText(s[8]);
                    String has_hidden = s[9];
                    if (null != has_hidden && "1".equals(has_hidden)) {
                        has_yhcheckBox.setChecked(true);
                        lin_has_yh.setVisibility(View.VISIBLE);
                    }
                    SharedPreferences sp = getSharedPreferences("MianPlaceCheckEditActivity", MODE_PRIVATE);
                    Editor editor = sp.edit();
                    String jclx = s[10];
                    if (null != has_hidden && "1".equals(jclx)) {
                        editor.putString("INSTYPE", "1");
                        editor.commit();
                        jclxET.setText("消防安全管理情况的检查");
                    } else if (null != has_hidden && "2".equals(jclx)) {
                        editor.putString("INSTYPE", "2");
                        editor.commit();
                        jclxET.setText("建筑防火技术措施的检查");
                    }
                    // idcardTV.setText(s[1]);
                    // addrTV.setText(s[2]);
                    // String date_time=s[3];
                    // if(null!=date_time&&date_time.length()>12){
                    // date_btn.setText(date_time.substring(0, 10));
                    // time_btn.setText(date_time.substring(10,
                    // date_time.length()));
                    // }
                    // zfxsET.setText(s[4]);
                    // zfxgET.setText(s[5]);
                    // jqdtET.setText(s[6]);
                    // zfyyET.setText(s[7]);
                    // jkjsET.setText(s[8]);
                    // cqcsET.setText(s[9]);
                    // jtnrET.setText(s[10]);
                    // bzET.setText(s[11]);
                    break;
                case MESSAGETYPE_02:
                    toast("获取失败");
                    break;
                default:
                    break;
            }
        }

    };

    /**
     * 保存
     * 
     * @param v
     */
    public void eventbc(View v) {
        submit();
    }

    /**
     * 删除
     * 
     * @param v
     */
    public void sc(View v) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(MianPlaceCheckEditActivity.this);
        builder.setTitle("提示信息");
        builder.setIcon(R.drawable.qq_dialog_default_icon);// 图标
        builder.setMessage("是否确定要删除检查信息？");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            // @Override
            public void onClick(DialogInterface dialog, int which) {
                progressDialog = ProgressDialog.show(MianPlaceCheckEditActivity.this, "提示", "正在删除，请稍等...");
                deleteJC();
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
     * 删除
     */
    private void deleteJC() {
        messagedelete = new Message();
        new Thread(new Runnable() {
            @Override
            public void run() {
                // 不能在其他线程中刷新UI
                // "Can't create handler inside thread that has not called Looper.prepare()");
                // progressDialog = ProgressDialog.show(
                // MianPeopleVisitEditActivity.this, "提示", "正在删除，请稍等...");

                String infoPath_a = HttpUtil.BASE_URL + "teventAndroid/deleteJC";
                String infoPath_b = "id="
                        + ShareUtil.getString(getApplicationContext(), "MianPlaceCheckEditActivity", "SYSID", "");
                try {
                    String delete_sss = "";
                    delete_sss = connServerForResultPost(infoPath_a, infoPath_b);
                    if (null != delete_sss && delete_sss.contains("failure")) {
                        messagedelete.what = MESSAGETYPE_02;
                    } else {
                        messagedelete.what = MESSAGETYPE_01;
                    }
                } catch (ClientProtocolException e) {
                    messagedelete.what = MESSAGETYPE_02;
                    if (null != e) {
                        e.printStackTrace();
                    }
                } catch (IOException e) {
                    messagedelete.what = MESSAGETYPE_02;
                    if (null != e) {
                        e.printStackTrace();
                    }
                } finally {
                    handler3.sendMessage(messagedelete);
                }
            }
        }).start();
    }
    /** MemberVariables */
    Handler handler3 = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MESSAGETYPE_01:
                    if (null != progressDialog) {
                        progressDialog.dismiss();
                    }
                    toast("删除成功");
                    MianPlaceCheckEditActivity.this.finish();
                    MianPlaceCheckVisitActivity.instance.finish();
                    Intent intent = new Intent(getApplicationContext(), MianPlaceCheckVisitActivity.class);
                    startActivity(intent);
                    break;
                case MESSAGETYPE_02:
                    if (null != progressDialog) {
                        progressDialog.dismiss();
                    }
                    toast("删除失败");
                    break;
                default:
                    break;
            }
        }

    };

    /**
     * 保存
     */
    private void submit() {
        /** 数据校验 */
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
        final AlertDialog.Builder builder = new AlertDialog.Builder(MianPlaceCheckEditActivity.this);
        builder.setTitle("提示信息");
        builder.setIcon(R.drawable.qq_dialog_default_icon);// 图标
        builder.setMessage("是否确定要保存检查信息？");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            // @Override
            public void onClick(DialogInterface dialog, int which) {
                progressDialog = ProgressDialog.show(MianPlaceCheckEditActivity.this, "提示", "正在保存，请稍等...");
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
                    String a = HttpUtil.BASE_URL + "teventAndroid/editInspection";
                    String b = "inspection.SYSID="
                            + ShareUtil.getString(getApplicationContext(), 
                                    "MianPlaceCheckEditActivity", "SYSID", "") // 检查记录id
                            + "&inspection.AREAID="
                            + ShareUtil.getString(getApplicationContext(), "SESSION", "AREAID", "")// 所属网格ID
                            + "&inspection.PERSONID="
                            + ShareUtil.getString(getApplicationContext(), "SESSION", "EMPID", "")// 检查员ID
                            + "&inspection.PERSONNAME="
                            + ShareUtil.getString(getApplicationContext(), "SESSION", "EMPNAME", "系统管理员")// 检查员姓名
                            + "&INSDATE="
                            + jc_date_btn.getText().toString().trim()
                            + " "
                            + (jc_time_btn.getText().toString().trim().length() > 6 ? (jc_time_btn.getText().toString()
                                    .trim()) : (jc_time_btn.getText().toString().trim() + ":00")) // 检查日期
                            + "&inspection.INSPLACEID="
                            + ShareUtil.getString(getApplicationContext(), "MianPlaceCheckEditActivity", "INSPLACEID",
                                    "") // 场所id
                            + "&inspection.INSPLACE="
                            + csmcTV.getText().toString().trim() // 场所名称
                            + "&inspection.INSPLACEADD="
                            + csdzTV.getText().toString().trim() // 场所地址
                            + "&inspection.INSRESULT="
                            + ShareUtil.getString(getApplicationContext(), "MianPlaceCheckEditActivity", "INSRESULT",
                                    "") // 检查结果
                            + "&inspection.ISHIDDEN="
                            + ((has_yhcheckBox.isChecked()) ? "1" : "0") // 是否存在隐患
                            + "&inspection.HIDDENCOUNT="
                            + yhslET.getText().toString().trim() // 隐患数量
                            + "&inspection.HIDDENDANGER="
                            + yhqkET.getText().toString().trim() // 隐患情况
                            + "&RECTTERM="
                            + zg_date_btn.getText().toString().trim()
                            + " "
                            + (zg_time_btn.getText().toString().trim().length() > 6 ? (zg_time_btn.getText().toString()
                                    .trim()) : (zg_time_btn.getText().toString().trim() + ":00")) // 隐患整改期限
                            + "&inspection.RECTSTATE="
                            + ShareUtil.getString(getApplicationContext(), "MianPlaceCheckEditActivity", "RECTSTATE",
                                    "") // 整改状态
                            + "&inspection.RECTNOTE="
                            + zgqkET.getText().toString().trim() // 整改情况
                            + "&inspection.NOTES="
                            + bzET.getText().toString().trim() // 备注
                            + "&inspection.INSTYPE="
                            + ShareUtil.getString(getApplicationContext(), 
                                    "MianPlaceCheckEditActivity", "INSTYPE", "") // 检查类别
                            // +
                            // "&inspection.INSPLACEID="+ShareUtil.getString(getApplicationContext(),
                            // "MianPlaceCheckEditActivity", "INSPLACEID", "")
                            // //检查记录id
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
                    MianPlaceCheckEditActivity.this.finish();
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
        // /**
        // * 第二轮bug修复
        // */
        // MianPlaceCheckEditActivity.this.finish();
        // MianPlaceCheckVisitActivity.instance.finish();
        // Intent intent=new Intent(getApplicationContext(),
        // MianPlaceCheckVisitActivity.class);
        // startActivity(intent);
        // /**
        // * 第二轮bug修复
        // */
    }

    /**
     * 错误填报提示信息
     * 
     * @param errorMsg
     */
    private void DialogToast(String errorMsg) {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(MianPlaceCheckEditActivity.this);
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