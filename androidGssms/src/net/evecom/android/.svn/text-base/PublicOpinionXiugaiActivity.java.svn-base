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
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.http.client.ClientProtocolException;

import net.evecom.android.bean.FileManageBean;
import net.evecom.android.gps.GPSActivity;
import net.evecom.android.util.HttpUtil;
import net.evecom.android.util.ShareUtil;
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
 * 描述PublicOpinionXiugaiActivity
 * @author Mars zhang
 * @created 2014-11-5 上午11:01:26
 */
public class PublicOpinionXiugaiActivity extends Activity {
    /** 进度条 */
    private ProgressDialog progressDialog;
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
    /** 来源人 */
    private EditText lyrEditText; // public_opinion_add_lyr
    /** 联系电话 */
    private EditText lxdhEditText; // public_opinion_add_lxdh
    /** 信息内容 */
    private EditText xxnrEditText; // public_opinion_add_xxnr
    /** 上传文件 */
    private ListView fileListView; // add_hidden_atdanger_picture_list
    /** 图片适配器 */
    private UploadPictureAdapter uploadPictureAdapter;
    /** MemberVariables */
    private static final int MESSAGETYPE_01 = 0x0001;// 用于判断是否发送成功
    /** MemberVariables */
    private static final int MESSAGETYPE_02 = 0x0002;
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
    /** MemberVariables */
    boolean isprogressDialogCanClose = false;
    /** MemberVariables */
    Message msg_listData2 = null;
    /** MemberVariables */
    Message msg_listData = null;
    /** sss */
    private String sss = "";
    /** PIC */
    private static final String FILE_TYPE_PIC_01 = "1";
    /** VIDEO */
    private static final String FILE_TYPE_VIDEO_02 = "2";
    /** AUDIO */
    private static final String FILE_TYPE_AUDIO_03 = "3";
    /** 数据库 */
    private FinalDb db;
    /** 图片列表 */
    private List<FileManageBean> fileListg;
    /** 传递Id */
    private String Id;
    /** 传递标题 */
    private String eventname;
    /** 传递地点 */
    private String occuraddr;
    /** 传递类型 */
    private String sourceway1;
    /** 传递时间 */
    private String occurtime;
    /** 传递来源人 */
    private String sourcepeople;
    /** 传递联系电话 */
    private String phone;
    /** 传递信息内容 */
    private String news;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.public_opinion_xiugai_activity);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        init();
        initView();
        adapter();
    }

    /**
     * 初始化数据
     */
    private void init() {
        lylxs = new String[] { "短信录入", "网站录入", "热线电话", "审批转入", "走访录入", "手机录入" };
        Intent intent = getIntent();
        Id = intent.getStringExtra("Id");
        eventname = intent.getStringExtra("eventname");
        occuraddr = intent.getStringExtra("occuraddr");
        sourceway1 = intent.getStringExtra("sourceway");
        occurtime = intent.getStringExtra("occurtime");
        sourcepeople = intent.getStringExtra("sourcepeople");
        phone = intent.getStringExtra("phone");
        news = intent.getStringExtra("news");

    }

    /**
     * 初始化界面
     */
    private void initView() {
        ztEditText = (EditText) findViewById(R.id.public_opinion_xiugai_bt);
        fsddEditText = (EditText) findViewById(R.id.public_opinion_xiugai_fsdd);
        lylxButton = (Button) findViewById(R.id.public_opinion_xiugai_lylx);
        lysjButton = (Button) findViewById(R.id.public_opinion_xiugai_btn);
        lyrEditText = (EditText) findViewById(R.id.public_opinion_xiugai_lyr);
        lxdhEditText = (EditText) findViewById(R.id.public_opinion_xiugai_lxdh);
        xxnrEditText = (EditText) findViewById(R.id.public_opinion_xiugai_xxnr);
        fileListView = (ListView) findViewById(R.id.event_add_file_list);
        ztEditText.setText(eventname);
        fsddEditText.setText(occuraddr);
        lylxButton.setText(sourceway1);
        for (int i = 0; i < lylxs.length; i++) {
            if (lylxs[i].equals(sourceway1)) {
                sourceway = i + 1;
            }
        }
        lysjButton.setText(occurtime);
        lyrEditText.setText(sourcepeople);
        lxdhEditText.setText(phone);
        xxnrEditText.setText(news);

        listener();
    }

    /**
     * 监听定义
     */
    private void listener() {

        lysjButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog alertdialog = new AlertDialog.Builder(PublicOpinionXiugaiActivity.this).setTitle("来源时间")
                        .setPositiveButton("日期", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Dialog dialog1 = new DatePickerDialog(PublicOpinionXiugaiActivity.this,
                                        new DatePickerDialog.OnDateSetListener() {

                                            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                                    int dayOfMonth) {
                                                lysjButton.setText("" + year + "-" + (monthOfYear + 1) + "-"
                                                        + dayOfMonth + " " + hours + ":" + minutes + ":"
                                                        + calendar.get(Calendar.SECOND));
                                                years = year;
                                                month = monthOfYear + 1;
                                                day = dayOfMonth;
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
                                Dialog dialog2 = new TimePickerDialog(PublicOpinionXiugaiActivity.this,
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
                Dialog schDia = new AlertDialog.Builder(PublicOpinionXiugaiActivity.this)
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
     * 保存提交数据到服务器
     * 
     * @param v
     */
    public void opinionbc(View v) {
        submit();
    }

    /**
     * 保存
     */
    private String submit() {
        /** 数据校验 */
        if (ztEditText.getText().toString().trim().length() < 1) {
            DialogToast("请填写主题名称！");
            return "";
        } else if (fsddEditText.getText().toString().trim().length() < 1) {
            DialogToast("发生地点不能为空！");
            return "";
        } else if (lylxButton.getText().toString().trim().length() < 1) {
            DialogToast("来源类型不能为空！");
            return "";
        } else if (lyrEditText.getText().toString().trim().length() < 1) {
            DialogToast("来源人不能为空！");
            return "";
        } else if (xxnrEditText.getText().toString().trim().length() < 1) {
            DialogToast("信息内容不能为空！");
            return "";
        }

        final AlertDialog.Builder builder = new AlertDialog.Builder(PublicOpinionXiugaiActivity.this);
        builder.setTitle("提示信息");
        builder.setIcon(R.drawable.qq_dialog_default_icon);// 图标
        builder.setMessage("是否确定要保存？");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            // @Override
            public void onClick(DialogInterface dialog, int which) {
                progressDialog = ProgressDialog.show(PublicOpinionXiugaiActivity.this, "提示", "正在保存，请稍等...");

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

    /** 文件进度条使用 */
    Handler handler4 = new Handler() {
        public void handleMessage(Message message) {
            switch (message.what) {
                case MESSAGETYPE_01:
                    if (null != progressDialog) {
                        progressDialog.dismiss();
                    }
                    PublicOpinionXiugaiActivity.this.finish();

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
    Handler handler1 = new Handler() {
        public void handleMessage(Message message) {
            switch (message.what) {
                case MESSAGETYPE_01:
                    // 刷新UI，显示数据，并关闭进度条
                    String[] s = Pattern.compile("@").split(sss);
                    fileSubmitAfterForm(s[1]);
                    Intent intent = new Intent(getApplicationContext(), PublicOpinionLookActivity.class);
                    intent.putExtra("Id", Id);
                    startActivity(intent);
                    Toast.makeText(getApplicationContext(), "文本保存成功" + sss, 0).show();
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
        Dialog schDia = new AlertDialog.Builder(PublicOpinionXiugaiActivity.this).setIcon(R.drawable.login_error_icon)
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
                            && (isImagePicture(video.getFile_URL()) || isVideoFile(video.getFile_URL())
                                    || isAudioFile(video
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
     * 百度定位
     * 
     * @param v
     */
    public void event_xiugai_gps_btn(View v) {
        Intent intent = new Intent(getApplicationContext(), GPSActivity.class);
        startActivityForResult(intent, 1);
    }

    /**
     * 返回
     * 
     * @param v
     */
    public void opinionfh(View v) {
        finish();
        Intent intent = new Intent(getApplicationContext(), PublicOpinionLookActivity.class);
        intent.putExtra("Id", Id);
        startActivity(intent);
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
        AlertDialog.Builder builder1 = new AlertDialog.Builder(PublicOpinionXiugaiActivity.this);
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
}
