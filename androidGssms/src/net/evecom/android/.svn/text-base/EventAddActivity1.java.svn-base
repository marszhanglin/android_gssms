/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * 
 */
package net.evecom.android;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.regex.Pattern;

import net.evecom.android.bean.EOS_DICT_ENTRY;
import net.evecom.android.bean.FileManageBean;
import net.evecom.android.bean.MianPerson;
import net.evecom.android.bean.SysBaseDict;
import net.evecom.android.gps.GPSActivity;
import net.evecom.android.util.HttpMultipartPost;
import net.evecom.android.util.HttpUtil;
import net.evecom.android.util.ShareUtil;
import net.evecom.android.util.UiUtil;
import net.evecom.android.view.wheel.OnWheelChangedListener;
import net.evecom.android.view.wheel.OnWheelScrollListener;
import net.evecom.android.view.wheel.WheelView;
import net.evecom.android.view.wheel.adapter.AbstractWheelTextAdapter;
import net.tsz.afinal.FinalDb;
import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.stream.JsonReader;

/**
 * 事件登记界面
 * 
 * @author Mars zhang
 * 
 */
public class EventAddActivity1 extends Activity {
    /** 数据库 */
    private FinalDb db;
    /** MemberVariables */
    private static final int MESSAGETYPE_01 = 0x0001;// 用于判断是否发送成功
    /** MemberVariables */
    private static final int MESSAGETYPE_02 = 0x0002;
    /** PIC */
    private static final String FILE_TYPE_PIC_01 = "1";
    /** VIDEO */
    private static final String FILE_TYPE_VIDEO_02 = "2";
    /** AUDIO */
    private static final String FILE_TYPE_AUDIO_03 = "3";
    /** 事件名称 */
    private EditText sjmcEditText; // event_add_sjmc
    /** 发生时间 */
    // private Button fssjButton; // event_add_sfsj_btn
    /** 发生网络 */
    private TextView fswlTextView; // event_add_fswl
    /** 发生地点 */
    private EditText fsddEditText; // event_add_fsdd
    /** 事发规模 */
    private TextView event_add_sfgm;// event_add_sfgm
    // private WheelView sfgm_WheelView; // event_add_sfgm_country
    /** 是否紧急事件 */
    private CheckBox jjsjCheckBox; // event_add_sfjjsj_chcek
    /** 是否重点场所 */
    // private CheckBox zdcsCheckBox; // event_add_sfzdcs_chcek
    /** 是否重大事件 */
    private CheckBox zdsjCheckBox; // event_add_sfzdsj_chcek
    /** 是否其他人员 */
    // private CheckBox event_add_sfqtry_chcek;//event_add_sfqtry_chcek
    /** 涉及人数 */
    private EditText sjrsEditText; // event_add_sjrs
    /** 事件类型 */
    private TextView sjlxTextView; // event_add_sjlx
    /** 事件描述 */
    private EditText sjmsEditText; // event_add_sjms
    /** 主要当事人TextView */
    private TextView event_add_zydsr;// event_add_zydsr
    /** 主要当事人 EditText */
    private EditText event_add_zydsr_edit;// event_add_zydsr_edit
    /** 主要当事人 */
    // private LinearLayout zydsr_lin_layout;//zydsr_lin_layout
    /** 是否其他人员 */
    private String isOtherPerson = "0";
    /** 上传文件 */
    private ListView fileListView; // add_hidden_atdanger_picture_list
    /** 图片适配器 */
    private UploadPictureAdapter uploadPictureAdapter;
    /** 图片列表 */
    private List<FileManageBean> fileListg; //
    /** 主要当事人列表 */
    private List<MianPerson> mainpersons = new ArrayList<MianPerson>();
    /** MemberVariables */
    private String sjmceditText;
    /** Calendar */
    private Calendar calendar = null;
    /** 进度条 */
    private ProgressDialog progressDialog;
    /** MemberVariables */
    private int jjsj = 0;
    /** MemberVariables */
    private int zdcs = 0;
    /** MemberVariables */
    private int zdsj = 0;
    /** MemberVariables */
    boolean isprogressDialogCanClose = false;
    /** MemberVariables */
    private String[] sfgms = null;
    /** 事发类型 */
    private String[] sflx = null;
    /** 事发类型 */
    private String[] sflx_chiled = null;
    /** isChkeds */
    private boolean[] isChkeds = null;
    /** dict_ENTRYs */
    private List<EOS_DICT_ENTRY> dict_ENTRYs = null;
    /** dict_ENTRYs1 */
    List<EOS_DICT_ENTRY> dict_ENTRYs1 = null;
    /** 进度圈 */
    private ProgressDialog progressDialog_getInfo = null;
    /** 街道测试列表 */
    private List<SysBaseDict> list = new ArrayList<SysBaseDict>();
    /** 被选择的隐患类别 */
    private SysBaseDict baseDict = new SysBaseDict();
    /** Sysindex */
    private int Sysindex;
    /** Sysindex */
    private Boolean isFirstTime = true;
    /** sss */
    private String sss = "";
    /** sjlxbuffer */
    private StringBuffer sjlxbuffer = null;
    /** zydsrbuffer */
    private StringBuffer zydsrbuffer = null;
    /** MemberVariables */
    private HttpMultipartPost post;

    // private Message msg_listData2=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_add_activity);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        // 启动activity时不自动弹出软键盘
        init();
        initView();
        adapter();
    }

    /**
     * 初始化数据
     */
    private void init() {
        /** 清空数据 */
        db = FinalDb.create(this);
        db.deleteAll(FileManageBean.class);
        db.deleteAll(MianPerson.class);
        List<EOS_DICT_ENTRY> eos_DICT_ENTRYs_init = db.findAll(EOS_DICT_ENTRY.class);
        db.deleteAll(EOS_DICT_ENTRY.class);
        if (null != eos_DICT_ENTRYs_init) {
            for (EOS_DICT_ENTRY eos_DICT_ENTRY : eos_DICT_ENTRYs_init) {
                eos_DICT_ENTRY.setIsSelected("0");
                db.save(eos_DICT_ENTRY);
            }
        }
        SharedPreferences sp = EventAddActivity1.this.getSharedPreferences("T_EVENT", MODE_PRIVATE);
        Editor editor = sp.edit();
        editor.putString("EVENTINITGRID",
                ShareUtil.getString(getApplicationContext(), "SESSION", "AREAID", "0")); // 默认是连江县
        editor.commit();
    }

    /**
     * 初始化界面
     */
    private void initView() {
        sjmcEditText = (EditText) findViewById(R.id.event_add_sjmc);
        // fssjButton = (Button) findViewById(R.id.event_add_sfsj_btn);
        fswlTextView = (TextView) findViewById(R.id.event_add_fswl);
        fswlTextView.setText(ShareUtil.getString(getApplicationContext(), "SESSION", "AREANAME", "连江县"));
        fsddEditText = (EditText) findViewById(R.id.event_add_fsdd);
        event_add_zydsr = (TextView) findViewById(R.id.event_add_zydsr);
        event_add_zydsr_edit = (EditText) findViewById(R.id.event_add_zydsr_edit);
        // zydsr_lin_layout= (LinearLayout) findViewById(R.id.zydsr_lin_layout);

        event_add_sfgm = (TextView) findViewById(R.id.event_add_sfgm);

        jjsjCheckBox = (CheckBox) findViewById(R.id.event_add_sfjjsj_chcek);
        // zdcsCheckBox = (CheckBox) findViewById(R.id.event_add_sfzdcs_chcek);
        zdsjCheckBox = (CheckBox) findViewById(R.id.event_add_sfzdsj_chcek);
        // event_add_sfqtry_chcek= (CheckBox)
        // findViewById(R.id.event_add_sfqtry_chcek);

        sjrsEditText = (EditText) findViewById(R.id.event_add_sjrs);
        sjlxTextView = (TextView) findViewById(R.id.event_add_sjlx);
        sjmsEditText = (EditText) findViewById(R.id.event_add_sjms);

        fileListView = (ListView) findViewById(R.id.event_add_file_list);
        calendar = Calendar.getInstance();// 获取当前时间
        listener();
    }

    /**
     * 监听定义
     */
    private void listener() {
        jjsjCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    jjsj = 1;
                } else {
                    jjsj = 0;
                }
            }
        });
        // zdcsCheckBox
        // .setOnCheckedChangeListener(new
        // CompoundButton.OnCheckedChangeListener() {
        // @Override
        // public void onCheckedChanged(CompoundButton buttonView,
        // boolean isChecked) {
        // if (isChecked) {
        // zdcs = 1;
        // } else {
        // zdcs = 0;
        // }
        //
        // }
        // });
        zdsjCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    zdsj = 1;
                } else {
                    zdsj = 0;
                }
            }
        });
        // /**是否其他人员点击事件*/
        // event_add_sfqtry_chcek
        // .setOnCheckedChangeListener(new
        // CompoundButton.OnCheckedChangeListener() {
        // @Override
        // public void onCheckedChanged(CompoundButton buttonView,
        // boolean isChecked) {
        // if (isChecked) { // 其他可编辑 勾起锁住
        // zydsr_lin_layout.setClickable(false);
        // event_add_zydsr.setVisibility(View.GONE);
        // event_add_zydsr_edit.setVisibility(View.VISIBLE);
        // } else {
        // zydsr_lin_layout.setClickable(true);
        // event_add_zydsr.setVisibility(View.VISIBLE);
        // event_add_zydsr_edit.setVisibility(View.GONE);
        // }
        // }
        // });
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
     * 查找文件
     * 
     * @param v
     */
    public void findpicture_onclick(View v) {
        String[] filetypes = new String[] { "添加图片", "添加视频", "添加音频" };
        Dialog schDia = new AlertDialog.Builder(EventAddActivity1.this).setIcon(R.drawable.login_error_icon)
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

    /**
     * 保存提交数据到服务器
     * 
     * @param v
     */
    public void eventbc(View v) {
        submit();
    }

    /**
     * 事件类型选择
     * 
     * @param v
     */
    public void sjlx(View v) {
        db = FinalDb.create(getApplicationContext(), true);
        String strSQL = "";
        dict_ENTRYs = db.findAllByWhere(EOS_DICT_ENTRY.class, "PARENTID='0'");
        if (null == dict_ENTRYs || dict_ENTRYs.size() < 1) {
            return;
        }
        sflx = new String[dict_ENTRYs.size()];
        for (int i = 0; i < dict_ENTRYs.size(); i++) {
            sflx[i] = dict_ENTRYs.get(i).getDICTNAME();
        }
        Dialog schDia = new AlertDialog.Builder(EventAddActivity1.this).setIcon(R.drawable.login_error_icon)
                .setTitle("请选择").setItems(sflx, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dia, final int which_p) {
                        db = FinalDb.create(getApplicationContext(), true);
                        dict_ENTRYs1 = db.findAllByWhere(EOS_DICT_ENTRY.class, "PARENTID='"
                                + dict_ENTRYs.get(which_p).getDICTID() + "'");
                        isChkeds = new boolean[dict_ENTRYs1.size()];
                        sflx_chiled = new String[dict_ENTRYs1.size()];
                        for (int i = 0; i < dict_ENTRYs1.size(); i++) {
                            sflx_chiled[i] = dict_ENTRYs1.get(i).getDICTNAME();
                            if ("1".equals(dict_ENTRYs1.get(i).getIsSelected())) {
                                isChkeds[i] = true;
                            } else {
                                isChkeds[i] = false;
                            }

                        }
                        Dialog mchDia = new AlertDialog.Builder(EventAddActivity1.this)
                                .setIcon(R.drawable.login_error_icon)
                                .setTitle("请选择")
                                .setMultiChoiceItems(sflx_chiled, isChkeds,
                                        new DialogInterface.OnMultiChoiceClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dia, int which_c, boolean isChecked) {
                                                isChkeds[which_c] = isChecked;
                                            }
                                        }).setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dia, int which) {
                                        db = FinalDb.create(getApplicationContext(), true);
                                        // 父树
                                        dict_ENTRYs.get(which_p).setIsSelected("0");
                                        // 子树
                                        for (int i = 0; i < dict_ENTRYs1.size(); i++) {
                                            if (isChkeds[i]) {
                                                // 父树
                                                dict_ENTRYs.get(which_p).setIsSelected("1");
                                                dict_ENTRYs1.get(i).setIsSelected("1");
                                            } else {
                                                dict_ENTRYs1.get(i).setIsSelected("0");
                                            }
                                            db.update(dict_ENTRYs1.get(i));
                                            db.update(dict_ENTRYs.get(which_p));
                                        }
                                        // 刷新sjlxTextView
                                        db = FinalDb.create(getApplicationContext(), true);
                                        dict_ENTRYs = db.findAllByWhere(EOS_DICT_ENTRY.class, "PARENTID='0'");
                                        StringBuffer buffer = new StringBuffer();
                                        for (EOS_DICT_ENTRY entry : dict_ENTRYs) {// 父树
                                            if ("1".equals(entry.getIsSelected())) {
                                                buffer.append(entry.getDICTNAME() + ":\n");
                                                dict_ENTRYs1 = db.findAllByWhere(EOS_DICT_ENTRY.class, "PARENTID='"
                                                        + entry.getDICTID() + "'");
                                                for (EOS_DICT_ENTRY entry1 : dict_ENTRYs1) {// 子树
                                                    if ("1".equals(entry1.getIsSelected())) {
                                                        buffer.append("          " + entry1.getDICTNAME() + "\n");
                                                    }
                                                }
                                            }
                                        }
                                        sjlxTextView.setText(buffer.toString());

                                    }
                                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dia, int which) {
                                        dia.dismiss();
                                    }
                                }).create();
                        mchDia.show();
                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dia, int which) {
                        dia.dismiss();
                    }
                }).create();
        schDia.show();
    }

    /**
     * 发生网络选择
     * 
     * @param v
     */
    public void fswl(View v) {
        getTreeOne("");

    }

    /**
     * 主要当事人选择
     * 
     * @param v
     */
    public void zydsr(View v) {
        Intent intent = new Intent(getApplicationContext(), MainPersonListActivity.class);
        intent.putExtra("isFirstTime", isFirstTime);
        isFirstTime = false;
        startActivityForResult(intent, 6);
    }

    /**
     * 事发规模
     * 
     * @param v
     */
    public void sfgm(View v) {
        sfgms = new String[] { "个体性事件", "群体性事件" };
        Dialog schDia = new AlertDialog.Builder(EventAddActivity1.this).setIcon(R.drawable.login_error_icon)
                .setTitle("请选择").setItems(sfgms, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dia, int which) {
                        event_add_sfgm.setText(sfgms[which]);
                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dia, int which) {
                        dia.dismiss();
                    }
                }).create();
        schDia.show();
    }

    /**
     * 保存
     */
    private void submit() {
        /** 数据校验 */
        if (sjmcEditText.getText().toString().trim().length() < 1) {
            DialogToast("请填写事件名称！");
            return;
        }
        // else if(View.VISIBLE==event_add_zydsr.getVisibility()
        // &&event_add_zydsr.getText().toString().trim().length()<1){
        // DialogToast("请添加主要当事人！");
        // return ;
        // }
        // else if(View.VISIBLE==event_add_zydsr_edit.getVisibility()
        // &&event_add_zydsr_edit.getText().toString().trim().length()<1){
        // DialogToast("请填写主要当事人！");
        // return ;
        // }
        else if (event_add_zydsr_edit.getText().toString().trim().length() < 1
                && event_add_zydsr.getText().toString().trim().length() < 1) {
            DialogToast("请填写当事人！");
            return;
        } else if (event_add_sfgm.getText().toString().trim().length() < 1) {
            DialogToast("请选择事发规模！");
            return;
        } else if (sjrsEditText.getText().toString().trim().length() < 1) {
            DialogToast("请填写涉及人数！");
            return;
        } else if (sjlxTextView.getText().toString().trim().length() < 1) {
            DialogToast("请选择事件类型！");
            return;
        } else if (sjlxTextView.getText().toString().trim().length() < 1) {
            DialogToast("请填写事件描述！");
            return;
        }

        final AlertDialog.Builder builder = new AlertDialog.Builder(EventAddActivity1.this);
        builder.setTitle("提示信息");
        builder.setIcon(R.drawable.qq_dialog_default_icon);// 图标
        builder.setMessage("是否确定要保存事件信息？");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            // @Override
            public void onClick(DialogInterface dialog, int which) {
                progressDialog = ProgressDialog.show(EventAddActivity1.this, "提示", "正在保存，请稍等...");
                formSubmit();
                // fileSubmitAfterForm();
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
        db = FinalDb.create(getApplicationContext(), true);
        // 事件类型
        List<EOS_DICT_ENTRY> dict_ENTRYs_f_p = db.findAllByWhere(EOS_DICT_ENTRY.class,
                "PARENTID='0' And isSelected='1'");
        sjlxbuffer = new StringBuffer();
        if (null != dict_ENTRYs_f_p) {
            for (int i = 0; i < dict_ENTRYs_f_p.size(); i++) {
                EOS_DICT_ENTRY entry = dict_ENTRYs_f_p.get(i);
                if (i != dict_ENTRYs_f_p.size() - 1) {
                    sjlxbuffer.append(entry.getDICTID() + ",");
                } else if (i == dict_ENTRYs_f_p.size() - 1) {
                    sjlxbuffer.append(entry.getDICTID());
                }
            }
        }
        sjlxbuffer.append("@");
        List<EOS_DICT_ENTRY> dict_ENTRYs_f_c = db.findAllByWhere(EOS_DICT_ENTRY.class,
                "PARENTID!='0' And isSelected='1'");
        if (null != dict_ENTRYs_f_c) {
            for (int i = 0; i < dict_ENTRYs_f_c.size(); i++) {
                EOS_DICT_ENTRY entry = dict_ENTRYs_f_c.get(i);
                if (i != dict_ENTRYs_f_c.size() - 1) {
                    sjlxbuffer.append(entry.getDICTID() + ",");
                } else if (i == dict_ENTRYs_f_c.size() - 1) {
                    sjlxbuffer.append(entry.getDICTID());
                }
            }
        }
        // 主要当事人
        zydsrbuffer = new StringBuffer();
        List<MianPerson> mianPersons = db.findAll(MianPerson.class);
        if (null != mianPersons) {
            for (int i = 0; i < mianPersons.size(); i++) {
                if (i != mianPersons.size() - 1) {
                    zydsrbuffer.append(mianPersons.get(i).getPERSONID() + ",");
                } else if (i == mianPersons.size() - 1) {
                    zydsrbuffer.append(mianPersons.get(i).getPERSONID());
                }
            }
        }
        // if(zydsr_lin_layout.isClickable()){
        // isOtherPerson="0";
        // }else {
        // isOtherPerson="1";
        // }

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Message msg_listData = new Message();
                    // String furl = HttpUtil.BASE_URL
                    // + "teventAndroid/saveEventAndroidT?"
                    // + "EVENTNAME="+sjmcEditText.getText().toString().trim()
                    // //事件名称
                    // +
                    // "&EVENTINITGRID="+ShareUtil.getString(getApplicationContext(),
                    // "T_EVENT", "EVENTINITGRID", "0") //事件发生网格
                    // + "&EVENTDESC="+ sjmsEditText.getText().toString().trim()
                    // //事件描述
                    // + "&SCENE="+fsddEditText.getText().toString().trim()
                    // //发生地点
                    // +
                    // "&EVENTSCALE="+event_add_sfgm.getText().toString().trim()
                    // //事件规模字典ID
                    // +
                    // "&INVOLVEPERSONNUM="+sjrsEditText.getText().toString().trim()
                    // //涉及人数
                    // + "&ISEXIGENCY="+jjsj //是否紧急事件0-否1-是
                    // + "&ISKEYPLACE="+zdcs //是否重点场所0-否1-是
                    // + "&ISIMPORTANTEVENT="+zdsj //是否重大事件0-否1-是
                    // +
                    // "&MAINPARTY="+event_add_zydsr_edit.getText().toString().trim()
                    // //主要当事人（手动输入）
                    // + "&EVENTTYPEONE="+sjlxbuffer.toString() //事件类型大项||事件类型小项
                    // +
                    // "&LONGITUDE="+ShareUtil.getString(getApplicationContext(),"GPS",
                    // "GPS_longitude", "0.0") //经度
                    // +
                    // "&LATITUDE="+ShareUtil.getString(getApplicationContext(),"GPS",
                    // "GPS_latitude", "0.0") //纬度
                    // +
                    // "&MAINPERSON="+event_add_sfgm.getText().toString().trim()
                    // //主要当事人(存基础人员id)
                    // + "&EVENTINIT=1";//事件发起人
                    String a = HttpUtil.BASE_URL + "teventAndroid/saveEventAndroidT";
                    String b = "EVENTNAME="
                            + sjmcEditText.getText().toString().trim() // 事件名称
                            + "&EVENTINITGRID="
                            + ShareUtil.getString(getApplicationContext(), 
                                    "T_EVENT", "EVENTINITGRID", "0") // 事件发生网格
                            + "&EVENTDESC="
                            + sjmsEditText.getText().toString().trim() // 事件描述
                            + "&SCENE="
                            + fsddEditText.getText().toString().trim() // 发生地点
                            + "&EVENTSCALE="
                            + event_add_sfgm.getText().toString().trim() // 事件规模字典ID
                            + "&INVOLVEPERSONNUM="
                            + sjrsEditText.getText().toString().trim() // 涉及人数
                            + "&ISEXIGENCY="
                            + jjsj // 是否紧急事件0-否1-是
                            // + "&ISKEYPLACE="+zdcs //是否重点场所0-否1-是
                            + "&ISIMPORTANTEVENT="
                            + zdsj // 是否重大事件0-否1-是
                            + "&MAINPARTY="
                            + event_add_zydsr_edit.getText().toString().trim() // 主要当事人（手动输入）
                            + "&EVENTTYPEONE="
                            + sjlxbuffer.toString() // 事件类型大项||事件类型小项
                            + "&LONGITUDE="
                            + ShareUtil.getString(getApplicationContext(), "GPS", "GPS_longitude", "0.0") // 经度
                            + "&LATITUDE=" + ShareUtil.getString(getApplicationContext()
                                    , "GPS", "GPS_latitude", "0.0") // 纬度
                            + "&MAINPERSON=" + zydsrbuffer.toString() // 主要当事人(存基础人员id)
                            + "&EVENTINIT=" + ShareUtil.getString(getApplicationContext()
                                    , "SESSION", "EMPID", "1")// 事件发起人
                            + "&EMPNAME=" + ShareUtil.getString(getApplicationContext()
                                    , "SESSION", "EMPNAME", "系统管理员") //
                            + "&PHONE=" + ShareUtil.getString(getApplicationContext(), "SESSION", "PHONE", "") //
                            + "&ISOTHERPERSON=" + isOtherPerson // 是否其他人
                            + "&OPERATORGRID=" + ShareUtil.getString(getApplicationContext(), "SESSION", "AREAID", "");
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
                    handler.sendMessage(msg_listData);
                } catch (Exception e) {
                    if (null != e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
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

    // public void fileSubmitAfterFormXUtil(String table_id) {
    // final Message msg_listData2 = new Message();
    // if (fileListg.size() == 0) {
    // if (null != progressDialog) {
    // progressDialog.dismiss(); // 关闭进度条
    // }
    // return;
    // }
    // RequestParams params = new RequestParams();
    // for (int i = 0; i < fileListg.size(); i++) {
    // if (i == fileListg.size() - 1) {
    // isprogressDialogCanClose = true;
    // }
    // params.addBodyParameter("profile_picture", new File(fileListg
    // .get(i).getFile_URL()));
    // params.addHeader("uatable_id", table_id);
    // // params.addBodyParameter("uatable_id", table_id);
    // // params.setBodyEntity(new BodyParamsEntity());
    // // 上传文件
    // HttpUtils http = new HttpUtils();
    // String furl = HttpUtil.BASE_URL + "teventAndroid/uploadPic";
    // http.send(HttpRequest.HttpMethod.POST,
    // furl,
    // params,
    // new RequestCallBack<String>() {
    // @Override
    // public void onStart() {
    // // toast("conn...");
    // }
    // @Override
    // public void onLoading(long total, long current, boolean isUploading) {
    // if (isUploading) {
    // toast("upload: " + current + "/" + total);
    // System.out.println("upload: " + current + "/" + total);
    // // resultText.setText("upload: " + current + "/" + total);
    // } else {
    // toast("reply: " + current + "/" + total);
    // System.out.println("reply: " + current + "/" + total);
    // // resultText.setText("reply: " + current + "/" + total);
    // }
    // }
    //
    // @Override
    // public void onSuccess(ResponseInfo<String> responseInfo) {
    // toast("reply: " + responseInfo.result);
    // System.out.println("reply: " + responseInfo.result);
    // // resultText.setText("reply: " + responseInfo.result);
    // if (isprogressDialogCanClose) {
    // msg_listData2.what = MESSAGETYPE_01;
    // handler4.sendMessage(msg_listData2);
    // }
    // }
    //
    //
    // @Override
    // public void onFailure(HttpException error, String msg) {
    // msg_listData2.what = MESSAGETYPE_02;
    // handler4.sendMessage(msg_listData2);
    // toast(msg+""+error.toString());
    // System.out.println(msg+""+error.toString());
    // // resultText.setText(msg);
    //
    // }
    // });
    // }
    // }

    /**
     * 上传文件
     */
    private void fileSubmitAfterForm(String table_id) {
        // Message msg_listData2 = new Message();
        if (null != progressDialog) {
            progressDialog.dismiss(); // 关闭进度条
        }

        if (fileListg.size() == 0) {
            return;
        }
        String furl = HttpUtil.BASE_URL + "teventAndroid/uploadPic";
        for (int i = 0; i < fileListg.size(); i++) {
            post = new HttpMultipartPost(getApplicationContext(), fileListg.get(i).getFile_URL(), furl);
            post.execute();
        }

        // for (int i = 0; i < fileListg.size(); i++) {
        // if (i == fileListg.size() - 1) {
        // isprogressDialogCanClose = true;
        // }
        // AjaxParams params = new AjaxParams();
        // try {
        // params.put("profile_picture", new File(fileListg.get(i)
        // .getFile_URL()));
        // params.put("uatable_id", table_id);
        // } catch (FileNotFoundException e) {
        // if(null!=e){
        // e.printStackTrace();
        // }
        // } // 上传文件
        // FinalHttp fh = new FinalHttp();
        // String furl = HttpUtil.BASE_URL + "teventAndroid/uploadPic";
        // fh.post(furl, params, new AjaxCallBack<String>() {
        // @Override
        // public void onLoading(long count, long current) {
        // System.out.println(current + "/" + count);
        // }
        //
        // @Override
        // public void onFailure(Throwable t, int errorNo, String strMsg) {
        // if (null != progressDialog) {
        // progressDialog.dismiss();
        // }
        // Toast.makeText(getApplicationContext(), "事件保存失败，请检查网络是否可用", 0)
        // .show();
        // // msg_listData2.what = MESSAGETYPE_02;
        // // handler4.sendMessage(msg_listData2);
        // super.onFailure(t, errorNo, strMsg);
        // // Toast.makeText(getApplicationContext(), "文件保存失败，请检查网络是否可用",
        // // 0).show();
        // }
        //
        // @Override
        // public void onSuccess(String t) {
        // super.onSuccess(t);
        // // toast("上传成功！"+t);
        // if (isprogressDialogCanClose) {
        // if (null != progressDialog) {
        // progressDialog.dismiss();
        // }
        // EventAddActivity1.this.finish();
        // Toast.makeText(getApplicationContext(), "文件上传成功", 0).show();
        // // msg_listData2.what = MESSAGETYPE_01;
        // // handler4.sendMessage(msg_listData2);
        // }
        // }
        // });
        // }
    }

    /** 进度条使用 */
    Handler handler = new Handler() {
        public void handleMessage(Message message) {
            switch (message.what) {
                case MESSAGETYPE_01:
                    // 刷新UI，显示数据，并关闭进度条
                    String[] s = Pattern.compile("@").split(sss);
                    fileSubmitAfterForm(s[1]);
                    // fileSubmitAfterFormXUtil(s[1]);
                    // Toast.makeText(getApplicationContext(), "文本保存成功"+sss,
                    // 0).show();
                    Toast.makeText(getApplicationContext(), "事件保存成功", 0).show();
                    break;
                case MESSAGETYPE_02:
                    if (null != progressDialog) {
                        progressDialog.dismiss();
                    }
                    Toast.makeText(getApplicationContext(), "事件保存失败，请检查网络是否可用", 0).show();
                    break;
                default:
                    break;
            }
        }
    };

    // /** 文件进度条使用 */
    // Handler handler4 = new Handler() {
    // public void handleMessage(Message message) {
    // switch (message.what) {
    // case MESSAGETYPE_01:
    // if (null != progressDialog) {
    // progressDialog.dismiss();
    // }
    // EventAddActivity.this.finish();
    // Toast.makeText(getApplicationContext(), "文件上传成功", 0).show();
    // break;
    // case MESSAGETYPE_02:
    // if (null != progressDialog) {
    // progressDialog.dismiss();
    // }
    // Toast.makeText(getApplicationContext(), "文件上传失败，请检查网络是否可用", 0)
    // .show();
    // break;
    // default:
    // break;
    // }
    // }
    // };

    /**
     * fh
     * 
     * @param v
     */
    public void eventfh(View v) {
        finish();
    }

    /**
     * 百度定位
     * 
     * @param v
     */
    public void event_add_gps_btn(View v) {
        Intent intent = new Intent(getApplicationContext(), GPSActivity.class);
        startActivityForResult(intent, 1);
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
                    // Toast.makeText(getApplicationContext(), "GPS_latitude="
                    // + GPS_latitude + "\nGPS_latitude" + GPS_longitude, 1);
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
                // toast(filePath);
                if ("".equals(bean.getFile_URL()) || "none".equals(bean.getFile_URL())
                        || null == bean.getFile_URL()) {// 没有选择
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
                                    || isVideoFile(picture.getFile_URL()) 
                                    || isAudioFile(picture
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
                // toast(videoPath);
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
                            && (isImagePicture(file.getFile_URL()) || 
                                    isVideoFile(file.getFile_URL()) || isAudioFile(file
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
                // toast(audioPath);
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
                            && (isImagePicture(audio.getFile_URL()) || isVideoFile(audio.getFile_URL())
                                    || isAudioFile(audio
                                    .getFile_URL()))) {
                        fileListg.add(audio);
                    }
                }
                uploadPictureAdapter.notifyDataSetChanged();
                if (null != fileListView) {
                    UiUtil.setListViewHeightBasedOnChildren(fileListView);
                }
                break;
            case 6: // 刷新主要当事人
                db = FinalDb.create(this);
                mainpersons.removeAll(mainpersons);
                mainpersons = db.findAll(MianPerson.class);// 刷新文件List
                StringBuffer buffer = new StringBuffer();
                if (null != mainpersons) {
                    for (MianPerson person : mainpersons) {
                        buffer.append(person.getPERSONNAME() + "  ");
                    }
                }
                event_add_zydsr.setText(buffer.toString());
                break;
            default:
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
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
                    // toast(list.get(i).getFile_URL() + "类型"
                    // + list.get(i).getFile_Flag());
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

    /**
     * 错误填报提示信息
     * 
     * @param errorMsg
     */
    private void DialogToast(String errorMsg) {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(EventAddActivity1.this);
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

    /**
     * 2014-4-1 判断文件路径是否为"jpg","png","gif","jpeg"后缀
     * 
     * @param filePath
     *            文件路径
     * @return Boolean 是否为图片路径
     */
    private Boolean isImagePicture(String filePath) {
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
        String[] imageFormatSet = new String[] { "mp4" };
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
     * getTreeOne
     */
    private void getTreeOne(final String sbd_id) {
        progressDialog_getInfo = ProgressDialog.show(EventAddActivity1.this, "提示", "正在获取数据，请稍等...");
        list.removeAll(list);
        new Thread(new Runnable() {
            @Override
            public void run() {
                Message msg_listData = new Message();
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
                        list.add(dict);
                        dict = null;
                        jsonReader.endObject();

                    }
                    jsonReader.endArray();
                } catch (IOException e) {
                    if (null != e) {
                        e.printStackTrace();
                    }
                }
                if (null == list || list.size() < 1) {
                    msg_listData.what = MESSAGETYPE_02;
                } else {
                    msg_listData.what = MESSAGETYPE_01;
                }
                handler1.sendMessage(msg_listData);
            }
        }).start();

    }

    /** handler1 */
    private Handler handler1 = new Handler() {
        public void handleMessage(Message message) {
            switch (message.what) {
                case MESSAGETYPE_01:
                    if (null != progressDialog_getInfo) {
                        progressDialog_getInfo.dismiss();
                    }
                    View messageView = LayoutInflater.from(EventAddActivity1.this).inflate(R.layout.base_dialog_info,
                            null);
                    // 分页设置
                    final WheelView country = (WheelView) messageView.findViewById(R.id.country);
                    country.setVisibleItems(3);
                    country.setViewAdapter(new CountryAdapter(EventAddActivity1.this, list));
                    country.addScrollingListener(new OnWheelScrollListener() {
                        public void onScrollingStarted(WheelView wheel) {

                        }

                        public void onScrollingFinished(WheelView wheel) {
                            baseDict = list.get(Sysindex);
                        }
                    });
                    country.addChangingListener(new OnWheelChangedListener() {
                        public void onChanged(WheelView wheel, int oldValue, int newValue) {
                            Sysindex = newValue;
                        }
                    });
                    country.setCurrentItem(0);
                    baseDict = list.get(0);
                    Sysindex = 0;
                    Dialog delDia = new AlertDialog.Builder(EventAddActivity1.this)
                            .setIcon(R.drawable.qq_dialog_default_icon).setTitle("请选择城镇！").setView(messageView)
                            .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dia, int which) {
                                    SharedPreferences sp = EventAddActivity1.this.getSharedPreferences("T_EVENT",
                                            MODE_PRIVATE);
                                    Editor editor = sp.edit();
                                    editor.putString("EVENTINITGRID", baseDict.getSbdCode());
                                    editor.commit();
                                    fswlTextView.setText(baseDict.getSbdName());
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
        progressDialog_getInfo = ProgressDialog.show(EventAddActivity1.this, "提示", "正在获取数据，请稍等...");
        list.removeAll(list);
        final Message msg_listData = new Message();

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
                    msg_listData.what = MESSAGETYPE_02;
                    handler2.sendMessage(msg_listData);
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
                        list.add(dict);
                        dict = null;
                        jsonReader.endObject();

                    }
                    jsonReader.endArray();
                } catch (IOException e) {
                    if (null != e) {
                        e.printStackTrace();
                    }
                }
                if (null == list || list.size() < 1) {
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
                    View messageView = LayoutInflater.from(EventAddActivity1.this).inflate(R.layout.base_dialog_info,
                            null);
                    // 分页设置
                    final WheelView country = (WheelView) messageView.findViewById(R.id.country);
                    country.setVisibleItems(3);
                    country.setViewAdapter(new CountryAdapter(EventAddActivity1.this, list));
                    country.addScrollingListener(new OnWheelScrollListener() {
                        public void onScrollingStarted(WheelView wheel) {

                        }

                        public void onScrollingFinished(WheelView wheel) {
                            baseDict = list.get(Sysindex);
                        }
                    });
                    country.addChangingListener(new OnWheelChangedListener() {
                        public void onChanged(WheelView wheel, int oldValue, int newValue) {
                            Sysindex = newValue;
                        }
                    });
                    country.setCurrentItem(0);
                    baseDict = list.get(0);
                    Sysindex = 0;
                    Dialog delDia = new AlertDialog.Builder(EventAddActivity1.this)
                            .setIcon(R.drawable.qq_dialog_default_icon).setTitle("请选择社区！").setView(messageView)
                            .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dia, int which) {
                                    SharedPreferences sp = EventAddActivity1.this.getSharedPreferences("T_EVENT",
                                            MODE_PRIVATE);
                                    Editor editor = sp.edit();
                                    editor.putString("EVENTINITGRID", baseDict.getSbdCode());
                                    editor.commit();
                                    fswlTextView.setText(baseDict.getSbdName());
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
        progressDialog_getInfo = ProgressDialog.show(EventAddActivity1.this, "提示", "正在获取数据，请稍等...");
        list.removeAll(list);
        final Message msg_listData = new Message();

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
                    msg_listData.what = MESSAGETYPE_02;
                    handler3.sendMessage(msg_listData);
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
                        list.add(dict);
                        dict = null;
                        jsonReader.endObject();
                    }
                    jsonReader.endArray();
                } catch (IOException e) {
                    if (null != e) {
                        e.printStackTrace();
                    }
                }
                if (null == list || list.size() < 1) {
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
                    View messageView = LayoutInflater.from(EventAddActivity1.this).inflate(R.layout.base_dialog_info,
                            null);
                    // 分页设置
                    final WheelView country = (WheelView) messageView.findViewById(R.id.country);
                    country.setVisibleItems(3);
                    country.setViewAdapter(new CountryAdapter(EventAddActivity1.this, list));
                    country.addScrollingListener(new OnWheelScrollListener() {
                        public void onScrollingStarted(WheelView wheel) {
                        }

                        public void onScrollingFinished(WheelView wheel) {
                            baseDict = list.get(Sysindex);
                        }
                    });
                    country.addChangingListener(new OnWheelChangedListener() {
                        public void onChanged(WheelView wheel, int oldValue, int newValue) {
                            Sysindex = newValue;
                        }
                    });
                    country.setCurrentItem(0);
                    baseDict = list.get(0);
                    Sysindex = 0;
                    Dialog delDia = new AlertDialog.Builder(EventAddActivity1.this)
                            .setIcon(R.drawable.qq_dialog_default_icon).setTitle("请选择网格！").setView(messageView)
                            .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dia, int which) {
                                    SharedPreferences sp = EventAddActivity1.this.getSharedPreferences("T_EVENT",
                                            MODE_PRIVATE);
                                    Editor editor = sp.edit();
                                    editor.putString("EVENTINITGRID", baseDict.getSbdCode());
                                    editor.commit();
                                    fswlTextView.setText(baseDict.getSbdName());
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
     * wheel适配器 2014-7-22下午5:56:27 类CountryAdapter 查找网格使用的
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
            return list.get(index).getSbdName() + "";
        }

    }

    /**
     * Wheel 2014-7-22下午5:56:27 类CountryAdapter 事发规模使用
     * 
     * @author Mars zhang
     * 
     */
    private class SFGMCountryAdapter extends AbstractWheelTextAdapter {
        // Countries names
        /** MemberVariables */
        ArrayList list;
        /** MemberVariables */
        int i = 0;

        protected SFGMCountryAdapter(Context context, ArrayList list) {
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
            return list.get(index) + "";
        }

    }
}